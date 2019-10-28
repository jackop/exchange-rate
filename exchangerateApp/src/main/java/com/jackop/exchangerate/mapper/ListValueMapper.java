package com.jackop.exchangerate.mapper;

import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.models.Values;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListValueMapper {

  public List<Values> map(String code, List<Table> table) {
    List<Values> valuesList = new ArrayList<>();

    table.stream().forEach(
      tab -> tab.getRates().stream().filter(rate -> rate.getCode().equalsIgnoreCase(code))
        .forEach(rate -> {
          Values values = new Values();
          Set<String> stringSet = new HashSet<>();

          values.setEffectiveDate(tab.getEffectiveDate());
          values.setMid(rate.getMid());
          values.setBid(rate.getBid());
          values.setAsk(rate.getAsk());
          values.setAdditives(stringSet);

          valuesList.add(values);
        }));

    return valuesList;
  }
}
