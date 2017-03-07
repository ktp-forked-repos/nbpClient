package pl.parser.nbp;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eadalac on 2017-02-18.
 */
public class ExchangeRateHistoryTest {

    @Test
    public void shouldCollectExchangeRateForEachDay() throws Exception {
        RateSource rateSourceMock = Mockito.mock(RateSource.class);

        ExchangeRate exRate1 = Mockito.mock(ExchangeRate.class);
        ExchangeRate exRate2 = Mockito.mock(ExchangeRate.class);
        Optional<ExchangeRate> exRate1op = Optional.of(exRate1);
        Optional<ExchangeRate> exRate2op = Optional.of(exRate2);
        Mockito.doReturn(exRate1op).when(rateSourceMock).getExchangeRate(Mockito.any(), Mockito.eq(LocalDate.parse("2017-01-02")));
        Mockito.doReturn(exRate2op).when(rateSourceMock).getExchangeRate(Mockito.any(), Mockito.eq(LocalDate.parse("2017-01-03")));

        ExchangeRateHistory exchangeRateHistory = new ExchangeRateHistory(rateSourceMock);

        List<ExchangeRate> returnedRates = exchangeRateHistory.getExchangeRates(
                CurrencyCode.EUR,
                Arrays.asList(LocalDate.parse("2017-01-02"),LocalDate.parse("2017-01-03")));

        assertThat(returnedRates).hasSize(2);
    }

    @Test
    public void shouldReturnExchangeRatesForEachDateFromStartToEnd() throws Exception {

        RateSource rateSourceMock = Mockito.mock(RateSource.class);
        ExchangeRateHistory exchangeRateHistory = new ExchangeRateHistory(rateSourceMock);
        ExchangeRateHistory exchangeRateHistorySpy = Mockito.spy(exchangeRateHistory);
        Mockito.doReturn(new ArrayList<ExchangeRate>()).when(exchangeRateHistorySpy).getExchangeRates(Mockito.eq(CurrencyCode.EUR), Mockito.anyList());
        ArgumentCaptor<List<LocalDate>> datesCaptor = ArgumentCaptor.forClass((Class) List.class);

        exchangeRateHistorySpy.getExchangeRates(CurrencyCode.EUR, LocalDate.parse("2017-01-02"), LocalDate.parse("2017-01-05"));

        Mockito.verify(exchangeRateHistorySpy, Mockito.times(1)).getExchangeRates(Mockito.any(), datesCaptor.capture());
        datesCaptor.getValue().contains(LocalDate.parse("2017-01-02"));
        datesCaptor.getValue().contains(LocalDate.parse("2017-01-03"));
        datesCaptor.getValue().contains(LocalDate.parse("2017-01-04"));
        datesCaptor.getValue().contains(LocalDate.parse("2017-01-05"));

    }

}