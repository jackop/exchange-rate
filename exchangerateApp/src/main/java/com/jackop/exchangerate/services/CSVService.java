package com.jackop.exchangerate.services;

import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.models.Values;
import com.jackop.exchangerate.utils.DateUtils;
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

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());
  private static final String LINE_SEPARATOR = "line.separator";
  private static final String FILE_EXTENSION = ".csv";

  static synchronized void parseObjectForCsv(String code, List<Table> table) {
    StringBuilder linesOfValue = new StringBuilder();
    table.stream()
      .filter(Objects::nonNull)
      .filter(fileExist -> !Paths.get(code + FILE_EXTENSION).toFile().exists())
      .forEach(currencyTable -> {
        linesOfValue.append(DateUtils.parseDate(currencyTable.getEffectiveDate()));
        currencyTable.getRates().stream()
          .filter(Objects::nonNull)
          .filter(rate -> rate.getCode().equalsIgnoreCase(code))
          .forEach(rate -> {
            linesOfValue.append(",");
            linesOfValue.append(rate.getBid());
            linesOfValue.append(",");
            linesOfValue.append(rate.getAsk());
            linesOfValue.append(",");
            linesOfValue.append(rate.getMid());
            linesOfValue.append(System.getProperty(LINE_SEPARATOR));
          });
      });
    table.stream()
      .filter(Objects::nonNull)
      .filter(fileExist -> Paths.get(code + FILE_EXTENSION).toFile().exists())
      .forEach(currencyTable -> {
        linesOfValue.append(DateUtils.parseDate(currencyTable.getEffectiveDate()));
        currencyTable.getRates().stream()
          .filter(Objects::nonNull)
          .filter(rate -> rate.getCode().equalsIgnoreCase(code))
          .forEach(
            rate -> readTableFromCSV(code).stream().filter(values -> values.getEffectiveDate()
              .equalsIgnoreCase(DateUtils.parseDate(currencyTable.getEffectiveDate())))
              .forEach(values -> {
                linesOfValue.append(",");
                linesOfValue.append(values.getBid() != 0.0 ? values.getBid() : rate.getBid());
                linesOfValue.append(",");
                linesOfValue.append(values.getAsk() != 0.0 ? values.getAsk() : rate.getAsk());
                linesOfValue.append(",");
                linesOfValue.append(values.getMid() != 0.0 ? values.getMid() : rate.getMid());
                linesOfValue.append(System.getProperty(LINE_SEPARATOR));
              }));
      });

    saveCsv(code, linesOfValue.toString());
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
        Values valueItems = getValues(attributes);
        values.add(valueItems);
        line = br.readLine();
      }
    } catch (IOException ioe) {
      LOGGER.warning("readTableFromCSV | Excetion: " + ioe.getMessage());
    }

    return values;
  }

  private static synchronized Values getValues(String[] veluesFromCsvFile) {
    Values values = new Values();

    String effectiveDate = veluesFromCsvFile[0];
    float bid = Float.parseFloat(veluesFromCsvFile[1]);
    float ask = Float.parseFloat(veluesFromCsvFile[2]);
    float mid = Float.parseFloat(veluesFromCsvFile[3]);
    values.setEffectiveDate(effectiveDate);
    values.setAsk(ask);
    values.setBid(bid);
    values.setMid(mid);

    return values;
  }
}
