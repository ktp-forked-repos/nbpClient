package pl.parser.nbp;

import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by eadalac on 2017-02-14.
 */
public class ExchangeRateHistory {

    RateSource rateSource;

    public ExchangeRateHistory(RateSource rateSource) {
        this.rateSource = rateSource;
    }

    public List<ExchangeRate> getExchangeRates(CurrencyCode currencyCode, List<LocalDate> dates){
        List<Optional<ExchangeRate>> exchangeRatesForAllCalendarDays = new ArrayList<>();
        dates.forEach(d -> exchangeRatesForAllCalendarDays.add(rateSource.getExchangeRate(currencyCode, d)));
        List<ExchangeRate> exchangeRatesForAllWorkingDays = new ArrayList<>();
        exchangeRatesForAllCalendarDays.forEach(o -> {if(o.isPresent()) exchangeRatesForAllWorkingDays.add(o.get());});
        return exchangeRatesForAllWorkingDays;
    }

    public List<ExchangeRate> getExchangeRates(CurrencyCode currencyCode, LocalDate start, LocalDate end){
        List<LocalDate> dates = new ArrayList<LocalDate>();
        while (!start.isAfter(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }
        return getExchangeRates(currencyCode, dates);
    }
}
