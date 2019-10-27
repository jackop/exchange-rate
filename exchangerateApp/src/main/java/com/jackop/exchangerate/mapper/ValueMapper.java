package com.jackop.exchangerate.mapper;

import com.jackop.exchangerate.models.Values;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValueMapper {

  public Values mapValues(String[] veluesFromCsvFile) {
    Values values = new Values();
    List<String> additives = new ArrayList<>();

    String effectiveDate = veluesFromCsvFile[0];
    float bid = Float.parseFloat(veluesFromCsvFile[1]);
    float ask = Float.parseFloat(veluesFromCsvFile[2]);
    float mid = Float.parseFloat(veluesFromCsvFile[3]);
    if (veluesFromCsvFile.length > 4) {
      additives = Arrays.asList(veluesFromCsvFile[4]);
    }
    values.setEffectiveDate(effectiveDate);
    values.setAsk(ask);
    values.setBid(bid);
    values.setMid(mid);
    values.setAdditives(additives);

    return values;
  }

}
