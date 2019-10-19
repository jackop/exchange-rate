package com.jackop.exchangerate;

import static java.util.Optional.ofNullable;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.services.CSVService;
import com.jackop.exchangerate.services.FetchService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

public class Application {

  private static final String EXCHANGE_RATE_URL = "http://api.nbp.pl/api/exchangerates/tables/";
  private static final String[] tables = {"A", "C"};
  private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final CSVService CSVService = new CSVService();
  private static final FetchService fetchService = new FetchService();

  public static void main(String[] args) throws IOException {
    fetch("EUR", "2019-10-10", "2019-10-16");
  }

  private static void fetch(String code, String from, String to) throws IOException {
    String url = buildUrl(from, to);
    Optional<String> response = ofNullable(fetchService.fetch(url));
    if (response.isPresent()) {
      JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Table.class);
      List<Table> currencyTable = mapper.readValue(response.get(), type);
      CSVService.parseObjectForCsv(code, currencyTable);
    } else {
      Optional.empty();
    }
  }

  private static String pickUpTableRandomly() {
    Random rand = new Random();
    int randomNumber = rand.nextInt(Application.tables.length);

    return Application.tables[randomNumber];
  }

  private static String buildUrl(String from, String to) {
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
      Optional.empty();
    }

    return buildUrl.toString();
  }
}
