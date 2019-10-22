package com.jackop.exchangerate;

import com.jackop.exchangerate.services.GetExchangeRate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

  private static final int MYTHREADS = 40;

  public static void main(String[] args) {

    ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

    // TODO: It's needed to fix behave when we will write data to existed file related to wrong first save data.
    // my own examples
    executor.submit(() -> {
      Runnable t1 = new GetExchangeRate("USD", "2019-10-15", "2019-10-20");
      Runnable t2 = new GetExchangeRate("NOK", "2019-10-05", "2019-10-10");
      Runnable t3 = new GetExchangeRate("EUR", "2019-10-05", "2019-10-15");
      Runnable t4 = new GetExchangeRate("CHF", "2016-04-04", "2016-05-04");
      Runnable t5 = new GetExchangeRate("EUR", "2016-03-01", "2016-04-01");

      // examples from task
      Runnable t6 = new GetExchangeRate("GBP", "2016-04-04", "2016-05-04");
      Runnable t7 = new GetExchangeRate("GBP", "2016-04-10", "2016-05-15");
      Runnable t8 = new GetExchangeRate("GBP", "2016-04-07", "2016-05-13");

      executor.execute(t1);
      executor.execute(t2);
      executor.execute(t3);
      executor.execute(t4);
      executor.execute(t5);
      executor.execute(t6);
      executor.execute(t7);
    });
  }
}
