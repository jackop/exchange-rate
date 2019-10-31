package com.jackop.exchangerate.utils;

import com.jackop.exchangerate.models.Rate;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.models.Values;
import java.util.Map;

public class CsvRowBuilder {

  private static final String LINE_SEPARATOR = "line.separator";

  public String buildRowFroFirstCsv(Table tab, Rate rate) {
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
  }

  public String buildRowFroExistedCsv(Map.Entry<String, Values> v) {
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
  }
}
