package com.jackop.exchangerate.utils;

import com.jackop.exchangerate.services.FetchService;

public class StringUtils {

  public static String prepareJsonResponse(String url) {
    StringUtils stringUtils = new StringUtils();
    FetchService fetchService = new FetchService();
    String removeFirstSign = stringUtils.replaceFirst(fetchService.fetch(url), "\\[", "");
    return stringUtils.replaceLast(removeFirstSign, "\\]", "");
  }

  public String replaceFirst(String string, String from, String to) {
    return string.replaceFirst(from, to);
  }

  public String replaceLast(String string, String from, String to) {
    int lastIndex = string.lastIndexOf(from);
    if (lastIndex < 0) {
      return string;
    }
    String tail = string.substring(lastIndex).replaceFirst(from, to);
    return string.substring(0, lastIndex) + tail;
  }
}
