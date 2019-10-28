package com.jackop.exchangerate.mapper;

import com.jackop.exchangerate.models.Values;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValueMapper {

  public Values mapValues(String[] veluesFromCsvFile) {
    Values values = new Values();
    Set<String> additives = new HashSet<>();

    String effectiveDate = veluesFromCsvFile[0];
    float bid = Float.parseFloat(veluesFromCsvFile[1]);
    float ask = Float.parseFloat(veluesFromCsvFile[2]);
    float mid = Float.parseFloat(veluesFromCsvFile[3]);
    if (veluesFromCsvFile.length > 4) {
      additives = new HashSet<>(Arrays.asList(veluesFromCsvFile[4]));
    }
    values.setEffectiveDate(effectiveDate);
    values.setAsk(ask);
    values.setBid(bid);
    values.setMid(mid);
    values.setAdditives(additives);

    return values;
  }

}
