package com.jackop.exchangerate.services;

import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.utils.DateUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());
  private static final StringBuilder linesOfValue = new StringBuilder();
  private static final String LINE_SEPARATOR = "line.separator";

  public static void parseObjectForCsv(String code, List<Table> table) {
    table.stream()
      .filter(Objects::nonNull)
      .forEach(currencyTable -> {
        linesOfValue.append(DateUtils.parseDate(currencyTable.getEffectiveDate()));
        currencyTable.getRates().stream()
          .filter(Objects::nonNull)
          .filter(rate -> rate.getCode().equalsIgnoreCase(code))
          .forEach(rate -> {
            linesOfValue.append(",");
            linesOfValue.append(rate.getAsk());
            linesOfValue.append(",");
            linesOfValue.append(rate.getBid());
            linesOfValue.append(",");
            linesOfValue.append(rate.getMid());
            linesOfValue.append(System.getProperty(LINE_SEPARATOR));
          });
      });
    saveCsv(code, linesOfValue.toString());
  }

  synchronized static void saveCsv(String code, String text) {
    String fileName = code + ".csv";
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
}
