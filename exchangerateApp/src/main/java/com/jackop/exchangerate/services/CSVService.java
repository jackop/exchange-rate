package com.jackop.exchangerate.services;

import com.jackop.exchangerate.mapper.ListValueMapper;
import com.jackop.exchangerate.mapper.ValueMapper;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.models.Values;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());
  private static final String LINE_SEPARATOR = "line.separator";
  private static final String FILE_EXTENSION = ".csv";
  private static final ValueMapper valueMapper = new ValueMapper();
  private static final ListValueMapper listValueMapper = new ListValueMapper();
  private static final Map<String, Object> lockers = new ConcurrentHashMap<>();

  /**
   * Method to parse object and save to CSV file
   */
  static void parseObjectForCsv(String code, List<Table> table) {
    String values = null;
    Map<String, Values> map = new ConcurrentHashMap<>();

    // when file not exist
    if (!Paths.get(code + FILE_EXTENSION).toFile().exists()) {
      values = table.stream()
        .filter(Objects::nonNull)
        .distinct()
        .map(tab -> tab.getRates().stream().filter(rate -> rate.getCode().equalsIgnoreCase(code))
          .map(rate -> {
            StringBuilder row = new StringBuilder();
            row.append(tab.getEffectiveDate());
            row.append(",");
            row.append(rate.getMid());
            row.append(",");
            row.append(rate.getAsk());
            row.append(",");
            row.append(rate.getBid());
            row.append(System.getProperty(LINE_SEPARATOR));
            return row.toString();
          })
          .collect(Collectors.joining()))
        .collect(Collectors.joining());

      // when file exist
    } else {
      // values from NBP Api
      List<Values> tableStream = listValueMapper.map(code, table);

      // values from exist file with `code`
      List<Values> valuesStream = readTableFromCSV(code);

      // add to Map, which agrregate data
      valuesStream.stream().distinct().forEach(vs -> map.put(vs.getEffectiveDate(),
        new Values(vs.getEffectiveDate(), vs.getMid(), vs.getAsk(), vs.getBid(),
          vs.getAdditives())));

      // add from table NBP Api
      tableStream.stream().distinct().forEach(ts -> {
        Set<String> tsSet = ts.getAdditives().stream().filter(Objects::nonNull)
          .collect(Collectors.toSet());

        // when date exist put ask, mid, bid
        if (map.containsKey(ts.getEffectiveDate())) {

          if (map.get(ts.getEffectiveDate()).getAsk() != ts.getAsk()) {
            tsSet.add(Float.toString(ts.getAsk()));
          } else if (map.get(ts.getEffectiveDate()).getMid() != ts.getMid()) {
            tsSet.add(Float.toString(ts.getMid()));
          } else if (map.get(ts.getEffectiveDate()).getBid() != ts.getBid()) {
            tsSet.add(Float.toString(ts.getBid()));
          }

          map.put(ts.getEffectiveDate(),
            new Values(ts.getEffectiveDate(), ts.getMid(), ts.getAsk(), ts.getBid(), tsSet));

          // when not exist add new value from NBP Api
        } else {

          map.put(ts.getEffectiveDate(),
            new Values(ts.getEffectiveDate(), ts.getMid(), ts.getAsk(), ts.getBid(),
              ts.getAdditives()));

        }
      });

      // aggregation data from Map
      values = map.entrySet().stream()
        .filter(Objects::nonNull)
        .map(v -> {
          StringBuilder row = new StringBuilder();
          row.append(v.getKey());
          row.append(",");
          row.append(v.getValue().getMid());
          row.append(",");
          row.append(v.getValue().getAsk());
          row.append(",");
          row.append(v.getValue().getBid());
          v.getValue().getAdditives().parallelStream()
            .forEach(ad -> {
              row.append(",");
              row.append(ad);
            });
          row.append(System.getProperty(LINE_SEPARATOR));
          return row.toString();
        }).collect(Collectors.joining());
    }

    // save to CSV file
    saveCsv(code, values);
  }

  /**
   * Method save to CSV file with synchronization
   */
  private static void saveCsv(String code, String text) {
    String fileName = code + FILE_EXTENSION;
    FileWriter fileWriter = null;
    if (!lockers.containsKey(code)) {
      lockers.put(code, new Object());
    }

    // synchronize when file with specific code is save
    synchronized (lockers.get(code)) {
      LOGGER.info("readTableFromCSV | Started Thread: " + Thread.currentThread().getName());
      try {
        fileWriter = new FileWriter(fileName);
        fileWriter.append(text);

      } catch (IOException e) {
        LOGGER.warning("saveCsv | Exeption durin write to file: " + e.getMessage());
      } finally {
        try {
          fileWriter.flush();
          fileWriter.close();
        } catch (IOException e) {
          LOGGER.warning("saveCsv | Exeption durin close & flush Writer: " + e.getMessage());
        }
        lockers.remove(code);
      }
      LOGGER.info("readTableFromCSV | Finished Thread: " + Thread.currentThread().getName());
    }
  }

  /**
   * Method which read from CSV file with synchronization
   */
  private static List<Values> readTableFromCSV(String code) {
    List<Values> values = new ArrayList<>();
    Path pathToFile = Paths.get(code + FILE_EXTENSION);
    String[] attributes = null;
    if (!lockers.containsKey(code)) {
      lockers.put(code, new Object());
    }

    // synchronize when file with specific code is read
    synchronized (lockers.get(code)) {
      LOGGER.info("readTableFromCSV | Started Thread: " + Thread.currentThread().getName());
      try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
        String line = br.readLine();
        while (line != null) {
          attributes = line.split(",");
          Values valueItems = valueMapper.mapValues(attributes);
          values.add(valueItems);
          line = br.readLine();
        }
      } catch (IOException ioe) {
        LOGGER.warning("readTableFromCSV | Excetion: " + ioe.getMessage());
      } finally {
        lockers.remove(code);
      }
      LOGGER.info("readTableFromCSV | Finished Thread: " + Thread.currentThread().getName());
    }

    return values;
  }
}
