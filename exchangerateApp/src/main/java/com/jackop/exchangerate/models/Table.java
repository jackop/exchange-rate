package com.jackop.exchangerate.models;

import java.util.List;

public class Table {

  private String table;
  private String no;
  private String tradingDate;
  private String effectiveDate;
  private List<Rate> rates;


  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public String getTradingDate() {
    return tradingDate;
  }

  public void setTradingDate(String tradingDate) {
    this.tradingDate = tradingDate;
  }

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public List<Rate> getRates() {
    return rates;
  }

  public void setRates(List<Rate> rates) {
    this.rates = rates;
  }
}
