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
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
      values = table.stream()
        .filter(Objects::nonNull)
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
      values += readTableFromCSV(code).parallelStream().map(v -> {
        String row = null;
        row = v.getEffectiveDate() + "," +
          v.getMid() + "," +
          v.getAsk() + "," +
          v.getBid() + System.getProperty(LINE_SEPARATOR);
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
}
