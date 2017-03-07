package pl.parser.nbp;

import pl.parser.nbp.model.CurrencyCode;

import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class MainClass
{
    public static void main( String[] args ) {

        NbpClient nbpClient = new NbpClient();
        RateSource rateSource = new RateSource(nbpClient);
        ExchangeRateHistory exchangeRateHistory = new ExchangeRateHistory(rateSource);
        AppController appController = new AppController(exchangeRateHistory);

        CurrencyCode currencyCode =  CurrencyCode.valueOf(args[0]);
        LocalDate start = LocalDate.parse(args[1]);
        LocalDate end = LocalDate.parse(args[2]);

        appController.printStatistics(currencyCode, start, end);

    }
}
