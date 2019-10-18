package com.jackop.exchangerate.models;

import java.util.Date;
import java.util.List;

public class Table {
  String table;
  String no;
  Date tradingDate;
  Date effectiveDate;
  List<Rate> rates;


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

  public Date getTradingDate() {
    return tradingDate;
  }

  public void setTradingDate(Date tradingDate) {
    this.tradingDate = tradingDate;
  }

  public Date getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(Date effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public List<Rate> getRates() {
    return rates;
  }

  public void setRates(List<Rate> rates) {
    this.rates = rates;
  }
}
