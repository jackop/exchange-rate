package com.jackop.exchangerate.services;

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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());
  private static final String LINE_SEPARATOR = "line.separator";
  private static final String FILE_EXTENSION = ".csv";
  private static final ValueMapper valueMapper = new ValueMapper();

  static synchronized void parseObjectForCsv(String code, List<Table> table) {
    String values = null;
    if (!Paths.get(code + FILE_EXTENSION).toFile().exists()) {
      values = table.stream()
        .filter(Objects::nonNull)
        .distinct()
        .map(tab -> tab.getRates().stream().filter(rate -> rate.getCode().equalsIgnoreCase(code))
          .map(rate -> {
            String row = null;
            row = tab.getEffectiveDate() + "," +
              rate.getMid() + "," +
              rate.getAsk() + "," +
              rate.getBid() + System.getProperty(LINE_SEPARATOR);
            return row;
          })
          .collect(Collectors.joining()))
        .collect(Collectors.joining());
    } else {
      List<Values> tableStream = Collections.synchronizedList(getValueFromTable(table));
      List<Values> valuesStream = Collections.synchronizedList(readTableFromCSV(code));
      List<Values> valuesList = new ArrayList<>(Stream.of(tableStream, valuesStream)
        .flatMap(List::stream)
        .collect(Collectors.toMap(Values::getEffectiveDate,
          d -> d,
          (Values x, Values y) -> x == null ? y : x))
        .values());
      values = valuesList.stream().map(d -> {
        String row = null;
        row = d.getEffectiveDate() + "," +
          d.getMid() + "," +
          d.getAsk() + "," +
          d.getBid() + System.getProperty(LINE_SEPARATOR);
        return row;
      }).collect(Collectors.joining());
    }

    saveCsv(code, values);
  }

  private static synchronized void saveCsv(String code, String text) {
    String fileName = code + FILE_EXTENSION;
    FileWriter fileWriter = null;
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
    }
  }

  private static synchronized List<Values> readTableFromCSV(String code) {
    List<Values> values = new ArrayList<>();
    Path pathToFile = Paths.get(code + FILE_EXTENSION);

    try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
      String line = br.readLine();
      while (line != null) {
        String[] attributes = line.split(",");
        Values valueItems = valueMapper.mapValues(attributes);
        values.add(valueItems);
        line = br.readLine();
      }
    } catch (IOException ioe) {
      LOGGER.warning("readTableFromCSV | Excetion: " + ioe.getMessage());
    }

    return values;
  }

  private static synchronized List<Values> getValueFromTable(List<Table> table) {
    List<Values> valuesList = new ArrayList<>();
    Values values = new Values();

    table.stream().forEach(tab -> tab.getRates().stream().forEach(rate -> {
      values.setEffectiveDate(tab.getEffectiveDate());
      values.setMid(rate.getMid());
      values.setBid(rate.getBid());
      values.setAsk(rate.getAsk());
      valuesList.add(values);
    }));

    return valuesList;
  }
}
