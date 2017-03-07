package pl.parser.nbp;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRate;
import pl.parser.nbp.model.ExchangeRatesTable;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * Created by eadalac on 2017-02-21.
 */
public class RateSourceTest {
    @Test
    public void shouldReturnExchangeRateIfSourceHasRateForRequestedDay() throws Exception {
        NbpClient nbpClientMock = Mockito.mock(NbpClient.class);
        ExchangeRatesTable exchangeRateTable = Mockito.mock(ExchangeRatesTable.class);
        doReturn("test").when(nbpClientMock).getExchangeRatesFilenameForReleaseDate(Mockito.any());
        doReturn( exchangeRateTable).when(nbpClientMock).getExchangeRatesTable("test");
        ExchangeRate exchangeRate = Mockito.mock(ExchangeRate.class);
        doReturn( exchangeRate).when(exchangeRateTable).getExchangeRate(CurrencyCode.AUD);
        RateSource rateSource = new RateSource(nbpClientMock);

        Optional<ExchangeRate> optional = rateSource.getExchangeRate(CurrencyCode.AUD, LocalDate.parse("2017-01-01"));

        assertThat(optional.isPresent()).isTrue();
    }

    @Test
    public void shouldReturnEmptyOptionalIfSourceHasntRateForRequestedDay() throws Exception {
        NbpClient nbpClientMock = Mockito.mock(NbpClient.class);
        ExchangeRatesTable exchangeRateTable = Mockito.mock(ExchangeRatesTable.class);
        doThrow(new IllegalArgumentException()).when(nbpClientMock).getExchangeRatesFilenameForReleaseDate(Mockito.any());

        RateSource rateSource = new RateSource(nbpClientMock);

        Optional<ExchangeRate> optional = rateSource.getExchangeRate(CurrencyCode.AUD, LocalDate.parse("2017-01-01"));

        assertThat(optional.isPresent()).isFalse();
    }

}