package pl.parser.nbp.model;

import pl.parser.nbp.model.adapters.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by eadalac on 2017-02-14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="tabela_kursow")
public class ExchangeRatesTable {

    @XmlAttribute(name="typ")
    private TableType type;

    @XmlElement(name="numer_tabeli")
    private String number;

    @XmlJavaTypeAdapter(type=LocalDate.class, value=LocalDateAdapter.class)
    @XmlElement(name="data_notowania")
    private LocalDate quotationDate;

    @XmlJavaTypeAdapter(type=LocalDate.class, value=LocalDateAdapter.class)
    @XmlElement(name="data_publikacji")
    private LocalDate releaseDate;

    @XmlElement(name="pozycja")
    private List<ExchangeRate> exchangeRates;

    public TableType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getQuotationDate() {
        return quotationDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public ExchangeRate getExchangeRate(CurrencyCode currencyCode) {
        ExchangeRate exchangeRate = exchangeRates.stream()
                .filter(exRate -> exRate.isTheSameCurrency(currencyCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("currency: " + currencyCode + " not found"));
        return exchangeRate;
    }
}
