package utils;

import com.jackop.exchangerate.models.Rate;
import com.jackop.exchangerate.models.Table;
import java.util.ArrayList;
import java.util.List;

public class MocksGenerate {

  public List<Rate> createRates() {
    Rate rateNOK = new Rate();
    Rate rateGBP = new Rate();
    List<Rate> rateList = new ArrayList<>();

    rateNOK.setCurrency("NOK");
    rateNOK.setAsk(new Float(4.1112));
    rateNOK.setBid(new Float(4.1233));
    rateNOK.setMid(new Float(0.00));
    rateNOK.setCurrency("Nordic Krone");

    return rateList;
  }
  public Table createTable() {
    Table table = new Table();
    table.setEffectiveDate("2019-10-05");
    table.setNo("1");
    table.setTable("table1");
    table.setTradingDate("2019-10-06");

    return table;
  }
}
