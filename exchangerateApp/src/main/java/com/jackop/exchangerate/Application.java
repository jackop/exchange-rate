package com.jackop.exchangerate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.services.FetchService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Application {

  private static final String EXHANGE_RATE_URL = "http://api.nbp.pl/api/exchangerates/tables/";
  private static final String[] tables = {"A", "C"};
  private static final String CURRENCY_CODE = "HKD";
  private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final FetchService fetchService = new FetchService();

  public static void main(String[] args) throws IOException {
    String selectedTableUrl = pickUpTableRandomly();
    String url = EXHANGE_RATE_URL + selectedTableUrl + "/2019-10-10/2019-10-15";
    System.out.println(url);
    fetch(url);
  }

  private static void fetch(String url) throws IOException {
    String response = fetchService.fetch(url);
    System.out.println(response);
    JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Table.class);
    List<Table> currencyTable = mapper.readValue(response, type);
    currencyTable.stream().forEach(table -> System.out.println(table.getEffectiveDate()));
//    System.out.println(currencyTable);
//    currencyTable.getRates().stream()
//      .filter(Objects::nonNull)
//      .filter(rate -> rate.getCode().equals(CURRENCY_CODE))
//      .forEach(rate -> CSVService.parseObjectForCsv(CURRENCY_CODE, currencyTable, rate));
  }

  private static String pickUpTableRandomly() {
    Random rand = new Random();
    int randomNumber = rand.nextInt(Application.tables.length);

    return Application.tables[randomNumber];
  }
}
