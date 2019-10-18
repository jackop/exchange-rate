package com.jackop.exchangerate.services;

import com.jackop.exchangerate.models.Rate;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.utils.DateUtils;
import java.util.logging.Logger;

public class CSVService {

  private static final Logger LOGGER = Logger.getLogger(CSVService.class.getName());

  public static String parseObjectForCsv(Table staff, Rate rate) {
    StringBuilder sb = new StringBuilder();
    sb.append(DateUtils.parseDate(staff.getEffectiveDate()));
    sb.append(",");
    sb.append(rate.getAsk());
    sb.append(",");
    sb.append(rate.getBid());
    sb.append(",");
    sb.append(rate.getMid());

    LOGGER.info(sb.toString());

    return sb.toString();
  }
}
