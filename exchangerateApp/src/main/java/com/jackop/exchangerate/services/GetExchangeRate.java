package com.jackop.exchangerate.services;

import static java.util.Optional.ofNullable;
import com.jackop.exchangerate.mapper.TableMapper;
import com.jackop.exchangerate.models.Table;
import java.util.List;
import java.util.Optional;

public class GetExchangeRate extends Thread {

  private static final FetchService fetchService = new FetchService();
  private static final TableMapper tableMapper = new TableMapper();

  private String code;
  private String startDate;
  private String endDate;

  public GetExchangeRate(String code, String startDate, String endDate) {
    this.code = code;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  private static synchronized void getExchangeRateData(String code, String from, String to) {
    String url = URLService.buildUrl(from, to);
    Optional<String> response = ofNullable(fetchService.fetch(url));
    if (response.isPresent()) {
      List<Table> currencyTable = tableMapper.map(response.get());
      CSVService.parseObjectForCsv(code, currencyTable);
    }
  }

  @Override
  public void run() {
    getExchangeRateData(this.code, this.startDate, this.endDate);
  }
}
