package com.jackop.exchangerate.models;

import lombok.Data;

@Data
public class Rate {

  private String currency;
  private String code;
  private float bid;
  private float ask;
  private float mid;
}
