package com.jackop.exchangerate.services;

import com.jackop.exchangerate.models.Rate;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());
  private static final String LINE_SEPARATOR = "line.separator";
  private static final String FILE_EXTENSION = ".csv";

  static synchronized void parseObjectForCsv(String code, List<Table> table) {
    String values = table.stream()
      .filter(Objects::nonNull)
      .map(tab -> tab.getRates().stream().filter(rate -> rate.getCode().equalsIgnoreCase(code))
        .map(rate -> {
          String row = null;
          row = DateUtils.parseDate(tab.getEffectiveDate()) + "," +
            rate.getMid() + "," +
            rate.getAsk() + "," +
            rate.getBid() + System.getProperty(LINE_SEPARATOR);
          return row;
        })
        .collect(Collectors.joining()))
      .collect(Collectors.joining());

//    Stream<Values> tableStream = getValueFromTable(table);
//    Stream<Values> valuesStream = readTableFromCSV(code).stream();
//
//    String val = Stream.concat(tableStream, valuesStream).distinct()
//      .map(values1 -> values1.getEffectiveDate() + " " + values1.getBid())
//      .collect(Collectors.joining());
//    System.out.println(val);
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

  private static synchronized Stream<Values> getValueFromTable(List<Table> table) {
    List<Values> valuesList = new ArrayList<>();
    Values values = new Values();

    table.stream().forEach(tab -> tab.getRates().stream().forEach(rate -> {
      values.setEffectiveDate(tab.getEffectiveDate().toString());
      values.setMid(rate.getMid());
      values.setBid(rate.getBid());
      values.setAsk(rate.getAsk());
      valuesList.add(values);
    }));

    return valuesList.stream();
  }

  private static synchronized String updateRecord(String code, String date, Rate rate) {
    return readTableFromCSV(code).stream()
//      .filter(value -> value.getEffectiveDate().equalsIgnoreCase(date))
      .map(value ->
        date + "," +
          (value.getMid() == 0.0 ? Float.toString(value.getMid()) : rate.getMid() + "," +
            (value.getAsk() == 0.0 ? Float.toString(value.getAsk()) : Float.toString(rate.getAsk()))
            + "," +
            (value.getBid() == 0.0 ? Float.toString(value.getBid())
              : Float.toString(rate.getBid())))
          + "," + System.getProperty(LINE_SEPARATOR))
      .collect(Collectors.joining());
  }

}
