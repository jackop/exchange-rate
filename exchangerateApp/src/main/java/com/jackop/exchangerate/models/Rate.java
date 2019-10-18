package com.jackop.exchangerate.models;

public class Rate {

  private String currency;
  private String code;
  private float bid;
  private float ask;
  private float mid;

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
