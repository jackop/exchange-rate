package com.jackop.exchangerate;

import com.jackop.exchangerate.services.GetExchangeRate;

public class Application {

  public static void main(String[] args) {

    // TODO: It's needed to fix behave when we will write data to existed file related to wrong first save data.
    // my own examples
    Thread t1 = new GetExchangeRate("USD", "2019-10-15", "2019-10-20");
    Thread t2 = new GetExchangeRate("NOK", "2019-10-05", "2019-10-10");
    Thread t3 = new GetExchangeRate("EUR", "2019-10-05", "2019-10-15");
    Thread t4 = new GetExchangeRate("CHF", "2016-04-04", "2016-05-04");

    // examples from task
    Thread t5 = new GetExchangeRate("GBP", "2016-04-04", "2016-05-04");
    Thread t6 = new GetExchangeRate("GBP", "2016-04-10", "2016-05-15");
    Thread t7 = new GetExchangeRate("GBP", "2016-04-10", "2016-05-15");

    t1.start();
    t2.start();
    t3.start();
    t4.start();
    t5.start();
    t6.start();
    t7.start();
  }
}
