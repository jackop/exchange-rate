package com.jackop.exchangerate.services;

import com.jackop.exchangerate.exceptions.HttpMethodException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchService {

  private static final String METHOD_GET = "GET";
  private static final String ACCEPT = "Accept";
  private static final String APPLICATION_JSON = "application/json";

  public String fetch(String urlSite) {
    BufferedReader br = null;
    String brString = null;
    HttpURLConnection conn = null;
    try {
      URL url = new URL(urlSite);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod(METHOD_GET);
      conn.setRequestProperty(ACCEPT, APPLICATION_JSON);
      if (conn.getResponseCode() != 200) {
        throw new HttpMethodException(
          "fetch() | Failed : HTTP error code : " + conn.getResponseCode());
      }
      br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      brString = br.readLine();
    } catch (IOException e) {
      e.getMessage();
    } finally {
      conn.disconnect();
    }
    return brString;
  }
}
