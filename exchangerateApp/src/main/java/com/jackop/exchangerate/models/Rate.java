package com.jackop.exchangerate.models;

public class Rate {
  String currency;
  String code;
  float bid;
  float ask;
  float mid;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public float getBid() {
    return bid;
  }

  public void setBid(float bid) {
    this.bid = bid;
  }

  public float getAsk() {
    return ask;
  }

  public void setAsk(float ask) {
    this.ask = ask;
  }

  public float getMid() {
    return mid;
  }

  public void setMid(float mid) {
    this.mid = mid;
  }
}
