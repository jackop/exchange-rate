package com.jackop.exchangerate.services;

import com.jackop.exchangerate.models.Rate;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.utils.DateUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());

  public static void parseObjectForCsv(String code, Table staff, Rate rate) {
    StringBuilder sb = new StringBuilder();
    sb.append(DateUtils.parseDate(staff.getEffectiveDate()));
    sb.append(",");
    sb.append(rate.getAsk());
    sb.append(",");
    sb.append(rate.getBid());
    sb.append(",");
    sb.append(rate.getMid());

    saveCsv(code, sb.toString());
  }

  public static void saveCsv(String code, String text) {
    String fileName = code + ".csv";
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(fileName);
      fileWriter.append(text);
    } catch (IOException e) {
      LOGGER.warning(e.getMessage());
    } finally {
      try {
        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        LOGGER.warning(e.getMessage());
      }
    }
  }
}
