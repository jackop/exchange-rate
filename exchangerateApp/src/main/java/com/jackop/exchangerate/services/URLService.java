package com.jackop.exchangerate.services;

import static java.util.Optional.ofNullable;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

public class URLService {

  private static final String EXCHANGE_RATE_URL = "http://api.nbp.pl/api/exchangerates/tables/";
  private static final String[] tables = {"A", "C"};
  private static final Logger LOGGER = Logger.getLogger(URLService.class.getName());

  private static synchronized String pickUpTableRandomly() {
    Random rand = new Random();
    int randomNumber = rand.nextInt(tables.length);

    return tables[randomNumber];
  }

  static synchronized String buildUrl(String from, String to) {
    Optional<String> selectedTableUrl = ofNullable(pickUpTableRandomly());
    StringBuilder buildUrl = new StringBuilder();
    if (selectedTableUrl.isPresent()) {
      buildUrl.append(EXCHANGE_RATE_URL);
      buildUrl.append(selectedTableUrl.get());
      buildUrl.append("/");
      buildUrl.append(from);
      buildUrl.append("/");
      buildUrl.append(to);
    } else {
      LOGGER.info("Selected table in url is null.");
    }
    
    return buildUrl.toString();
  }
}
