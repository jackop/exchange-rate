package com.jackop.exchangerate.models;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Values {

  private String effectiveDate;
  private float bid;
  private float ask;
  private float mid;
  private Set<String> additives;
}
