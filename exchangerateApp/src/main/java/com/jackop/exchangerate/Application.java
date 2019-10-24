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
    Thread t5 = new GetExchangeRate("EUR", "2016-03-01", "2016-04-01");

    // examples from task
    Thread t6 = new GetExchangeRate("GBP", "2016-04-04", "2016-05-04");
    Thread t7 = new GetExchangeRate("GBP", "2016-04-10", "2016-05-15");
    Thread t8 = new GetExchangeRate("GBP", "2016-04-07", "2016-05-13");

    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t1.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t2.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t3.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t4.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t5.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t6.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t7.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    t8.start();
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

    try {
      t1.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t2.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t3.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t4.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t5.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t6.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t7.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);

      t8.join();
      monitoring(t1, t2, t3, t4, t5, t6, t7, t8);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    monitoring(t1, t2, t3, t4, t5, t6, t7, t8);
  }

  private static void monitoring(Thread t1, Thread t2, Thread t3, Thread t4, Thread t5, Thread t6,
    Thread t7, Thread t8) {
    System.out.println(
      "MONITORING: Thread with name: " + t1.getName() + " is in state: " + t1.getState()
        + " is alive: " + t1.isAlive() + " is deamon: " + t1.isDaemon() + " is interrupted: " + t1
        .isInterrupted());
    System.out.println(
      "MONITORING: Thread with name: " + t2.getName() + " is in state: " + t2.getState()
        + " is alive: " + t2.isAlive() + " is deamon: " + t2.isDaemon() + " is interrupted: " + t2
        .isInterrupted());
    System.out
      .println("MONITORING: Thread with name: " + t3.getName() + " is in state: " + t3.getState()
        + " is alive: " + t3.isAlive() + " is deamon: " + t3.isDaemon() + " is interrupted: " + t3
        .isInterrupted());
    System.out
      .println("MONITORING: Thread with name: " + t4.getName() + " is in state: " + t4.getState()
        + " is alive: " + t4.isAlive() + " is deamon: " + t4.isDaemon() + " is interrupted: " + t4
        .isInterrupted());
    System.out
      .println("MONITORING: Thread with name: " + t5.getName() + " is in state: " + t5.getState()
        + " is alive: " + t5.isAlive() + " is deamon: " + t5.isDaemon() + " is interrupted: " + t5
        .isInterrupted());
    System.out
      .println("MONITORING: Thread with name: " + t6.getName() + " is in state: " + t6.getState()
        + " is alive: " + t6.isAlive() + " is deamon: " + t6.isDaemon() + " is interrupted: " + t6
        .isInterrupted());
    System.out
      .println("MONITORING: Thread with name: " + t7.getName() + " is in state: " + t7.getState()
        + " is alive: " + t7.isAlive() + " is deamon: " + t7.isDaemon() + " is interrupted: " + t7
        .isInterrupted());
    System.out
      .println("MONITORING: Thread with name: " + t8.getName() + " is in state: " + t8.getState()
        + " is alive: " + t8.isAlive() + " is deamon: " + t8.isDaemon() + " is interrupted: " + t8
        .isInterrupted());
  }
}
