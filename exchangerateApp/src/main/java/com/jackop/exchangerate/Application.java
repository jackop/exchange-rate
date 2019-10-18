package com.jackop.exchangerate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackop.exchangerate.models.Rate;
import com.jackop.exchangerate.models.Table;
import com.jackop.exchangerate.services.CSVService;
import com.jackop.exchangerate.utils.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

  private static final String TABLE_A = "http://api.nbp.pl/api/exchangerates/tables/A";
  private static final String TABLE_C = "http://api.nbp.pl/api/exchangerates/tables/C";
  private static final String[] tables = {TABLE_A, TABLE_C};
  private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

  public static void main(String[] args) throws IOException {

    String selectedTableUrl = pickUpTableRandomly(tables);
    CSVService csvService = new CSVService();
    List<Rate> rates = fetch(selectedTableUrl);
  }

  public static List<Rate> fetch(String url) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String response = StringUtils.prepareJsonResponse(url);
    Table staff = mapper.readValue(response, Table.class);

    LOGGER.log(Level.INFO, "Output from Server .... \n");
    LOGGER.log(Level.INFO, staff.getTable());
    LOGGER.log(Level.INFO, staff.getNo());
    staff.getRates().stream().forEach(rate -> LOGGER.log(Level.INFO, rate.getCode()));

    return staff.getRates();
  }

  public static String pickUpTableRandomly(String[] tables) {
    Random r = new Random();
    int randomNumber = r.nextInt(tables.length);

    return tables[randomNumber];
  }
}
