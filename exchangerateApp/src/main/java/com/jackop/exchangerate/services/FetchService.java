package com.jackop.exchangerate.services;

import com.jackop.exchangerate.exceptions.HttpMethodException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class FetchService {

  private static final Logger LOGGER = Logger.getLogger(FetchService.class.getName());
  private static final String METHOD_GET = "GET";
  private static final String ACCEPT = "Accept";
  private static final String APPLICATION_JSON = "application/json";

  public String fetch(String urlSite) {
    BufferedReader br;
    String brString = null;
    HttpURLConnection connection = null;
    try {
      URL url = new URL(urlSite);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(METHOD_GET);
      connection.setRequestProperty(ACCEPT, APPLICATION_JSON);

      if (connection.getResponseCode() != 200) {
        throw new HttpMethodException(
          "fetch() | Failed : HTTP error code : " + connection.getResponseCode());
      }
      br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      brString = br.readLine();
    } catch (IOException e) {
      LOGGER.warning("fetch() | Exception " + e.getMessage());
    } finally {
      connection.disconnect();
    }
    return brString;
  }
}
