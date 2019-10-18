package com.jackop.exchangerate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.services.CSVService;
import com.jackop.exchangerate.utils.StringUtils;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

public class Application {

  private static final String TABLE_A = "http://api.nbp.pl/api/exchangerates/tables/A";
  private static final String TABLE_C = "http://api.nbp.pl/api/exchangerates/tables/C";
  private static final String[] tables = {TABLE_A, TABLE_C};
  private static final String CURRENCY_CODE = "USD";
  private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
  private static final ObjectMapper mapper = new ObjectMapper();
  private static Random rand;

  static {
    try {
      rand = SecureRandom.getInstanceStrong();
    } catch (NoSuchAlgorithmException e) {
      LOGGER.warning(e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException {
    String selectedTableUrl = pickUpTableRandomly();
    fetch(selectedTableUrl);
  }

  private static void fetch(String url) throws IOException {
    String response = StringUtils.prepareJsonResponse(url);
    Table staff = mapper.readValue(response, Table.class);
    staff.getRates().stream()
      .filter(Objects::nonNull)
      .filter(rate -> rate.getCode().equals(CURRENCY_CODE))
      .forEach(rate -> CSVService.parseObjectForCsv(staff, rate));
  }

  private static String pickUpTableRandomly() {
    int randomNumber = rand.nextInt(Application.tables.length);

    return Application.tables[randomNumber];
  }
}
