package com.jackop.exchangerate.models;

import java.util.List;
import lombok.Data;

@Data
public class Table {

  private String table;
  private String no;
  private String tradingDate;
  private String effectiveDate;
  private List<Rate> rates;
}
