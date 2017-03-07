package pl.parser.nbp;

import org.testng.annotations.Test;
import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRatesTable;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eadalac on 2017-02-20.
 */
public class NbpClientTest {

    @Test
    public void shouldResolveFilenamesForGivenDate2017Year() throws Exception {
        NbpClient nbpClient = new NbpClient();

        String foundFilename = nbpClient.getExchangeRatesFilenameForReleaseDate(LocalDate.parse("2017-02-20"));

        assertThat(foundFilename).isEqualTo("c035z170220.xml");
    }

    @Test
    public void shouldResolveFilenamesForGivenDateBefore2017Year() throws Exception {
        NbpClient nbpClient = new NbpClient();

        String foundFilename = nbpClient.getExchangeRatesFilenameForReleaseDate(LocalDate.parse("2016-02-19"));

        assertThat(foundFilename).isEqualTo("c034z160219.xml");
    }

    @Test
    public void shouldFetchDataFromNbpForGivenXmlName() throws Exception {
        NbpClient nbpClient = new NbpClient();

        ExchangeRatesTable ratesTable = nbpClient.getExchangeRatesTable("c035z170220.xml");

        assertThat(ratesTable.getExchangeRates()).hasSize(13);
        assertThat(ratesTable.getExchangeRate(CurrencyCode.EUR).getBuyRate()).isEqualTo(BigDecimal.valueOf(4.2862));
    }

}