package com.jackop.exchangerate.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  public static String parseDate(Date date) {
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    return format.format(date);
  }
}
