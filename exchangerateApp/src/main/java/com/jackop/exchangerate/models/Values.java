package com.jackop.exchangerate.models;

import java.util.List;
import java.util.Set;

public class Values {

  private String effectiveDate;
  private float bid;
  private float ask;
  private float mid;
  private Set<String> additives;

  public Values(String effectiveDate, float bid, float ask, float mid,
    Set<String> additives) {
    this.effectiveDate = effectiveDate;
    this.bid = bid;
    this.ask = ask;
    this.mid = mid;
    this.additives = additives;
  }

  public Values() {}

  public Set<String> getAdditives() {
    return additives;
  }

  public void setAdditives(Set<String> additives) {
    this.additives = additives;
  }

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
