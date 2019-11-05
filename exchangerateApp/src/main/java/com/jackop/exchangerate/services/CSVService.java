package com.jackop.exchangerate.services;

import com.jackop.exchangerate.mapper.ListValueMapper;
import com.jackop.exchangerate.mapper.ValueMapper;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.models.Values;
import com.jackop.exchangerate.utils.CsvRowBuilder;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());
  private static final String FILE_EXTENSION = ".csv";
  private static final ValueMapper valueMapper = new ValueMapper();
  private static final ListValueMapper listValueMapper = new ListValueMapper();
  private static final Map<String, Object> lockers = new ConcurrentHashMap<>();
  private static final CsvRowBuilder csvRowBuilder = new CsvRowBuilder();

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
          .map(rate -> csvRowBuilder.buildRowFroFirstCsv(tab, rate)).collect(Collectors.joining()))
        .collect(Collectors.joining());

      // when file exist
    } else {
      List<Values> tableStream = listValueMapper.map(code, table);
      List<Values> valuesStream = readTableFromCSV(code);

      // add to Map, which agrregate data
      valuesStream.stream().distinct().forEach(vs -> map.put(vs.getEffectiveDate(),
        new Values(vs.getEffectiveDate(), vs.getMid(), vs.getAsk(), vs.getBid(),
          vs.getAdditives())));

      // add from table NBP Api
      tableStream.stream().distinct().forEach(ts -> map.put(ts.getEffectiveDate(),
        new Values(ts.getEffectiveDate(), ts.getMid(), ts.getAsk(), ts.getBid(),
          ts.getAdditives())));

      values = map.entrySet().stream()
        .filter(Objects::nonNull).map(csvRowBuilder::buildRowFroExistedCsv)
        .collect(Collectors.joining());
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
      LOGGER.log(Level.INFO, "saveCsv | Started Thread: {0}", Thread.currentThread().getName());
      try {
        fileWriter = new FileWriter(fileName);
        fileWriter.append(text);

      } catch (IOException e) {
        LOGGER.log(Level.WARNING, "saveCsv | Exeption durin write to file: {0}", e.getMessage());
      } finally {
        try {
          fileWriter.flush();
          fileWriter.close();
        } catch (IOException e) {
          LOGGER.log(Level.WARNING, "saveCsv | Exeption durin close & flush Writer: {0}",
            e.getMessage());
        }
        lockers.remove(code);
      }
      LOGGER.log(Level.INFO, "saveCsv | Finished Thread: {0}", Thread.currentThread().getName());
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
      LOGGER.log(Level.INFO, "readTableFromCSV | Started Thread: {0}",
        Thread.currentThread().getName());
      try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
        String line = br.readLine();
        while (line != null) {
          attributes = line.split(",");
          Values valueItems = valueMapper.mapValues(attributes);
          values.add(valueItems);
          line = br.readLine();
        }
      } catch (IOException ioe) {
        LOGGER.log(Level.WARNING, "readTableFromCSV | Excetion: {0}", ioe.getMessage());
      } finally {
        lockers.remove(code);
      }
      LOGGER.log(Level.INFO, "readTableFromCSV | Finished Thread: {0}",
        Thread.currentThread().getName());
    }

    return values;
  }
}
