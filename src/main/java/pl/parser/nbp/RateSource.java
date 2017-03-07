package pl.parser.nbp;

import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRate;
import pl.parser.nbp.model.ExchangeRatesTable;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by eadalac on 2017-02-14.
 */
public class RateSource {

    NbpClient nbpClient;

    public RateSource(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    public Optional<ExchangeRate> getExchangeRate(CurrencyCode currencyCode, LocalDate date){
        String xmlFilename;
        try{
            xmlFilename = nbpClient.getExchangeRatesFilenameForReleaseDate(date);
        } catch (IllegalArgumentException e){
            return Optional.empty();
        }
        ExchangeRatesTable exchangeRatesTable = nbpClient.getExchangeRatesTable(xmlFilename);
        Optional<ExchangeRate> exchangeRate = Optional.of(exchangeRatesTable.getExchangeRate(currencyCode));
        return exchangeRate;
    }
}
