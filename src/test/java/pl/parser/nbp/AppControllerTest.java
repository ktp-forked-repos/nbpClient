package pl.parser.nbp;

import org.testng.annotations.Test;
import pl.parser.nbp.AppController;
import pl.parser.nbp.ExchangeRateHistory;
import pl.parser.nbp.NbpClient;
import pl.parser.nbp.RateSource;
import pl.parser.nbp.model.CurrencyCode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eadalac on 2017-02-19.
 */
public class AppControllerTest {

    @Test
    public void integrationTest(){


        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        NbpClient nbpClient = new NbpClient();
        RateSource rateSource = new RateSource(nbpClient);
        ExchangeRateHistory exchangeRateHistory = new ExchangeRateHistory(rateSource);
        AppController appController = new AppController(exchangeRateHistory);

        appController.printStatistics(CurrencyCode.EUR, LocalDate.parse("2013-01-28"), LocalDate.parse("2013-01-31"));

        assertThat(outContent.toString()).contains("4,1505");
        assertThat(outContent.toString()).contains("0,0125");
    }



}