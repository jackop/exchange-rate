package com.jackop.exchangerate.mapper;

import com.jackop.exchangerate.models.Values;

public class ValueMapper {

  public Values mapValues(String[] veluesFromCsvFile) {
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
