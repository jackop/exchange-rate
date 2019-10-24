# Exchange Rate Raport App 

Build exvhange rate for choosen currency from some Date to another Date.

```$xslt
Runnable t6 = new GetExchangeRate("GBP", "2016-04-04", "2016-05-04");
Runnable t7 = new GetExchangeRate("GBP", "2016-04-10", "2016-05-15");      
```

Output format: `.CSV `

##### Issues

- Update mid, bid, ask for unique date (In progress)

##### Improvements plan

1. Add Lomobok for handle model
2. Write test for testing some pices of code

##### Link to API

- [NBP Exchange Rate API](http://api.nbp.pl/)

