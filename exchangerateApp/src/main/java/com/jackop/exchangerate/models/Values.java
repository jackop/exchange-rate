package com.jackop.exchangerate.models;

public class Values {

  private String effectiveDate;
  private float bid;
  private float ask;
  private float mid;

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
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
