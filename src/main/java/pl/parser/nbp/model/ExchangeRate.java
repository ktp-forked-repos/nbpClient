package pl.parser.nbp.model;

import pl.parser.nbp.model.adapters.BigDecimalEUAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

/**
 * Created by eadalac on 2017-02-14.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="pozycja")
public class ExchangeRate {

    @XmlElement(name="nazwa_waluty")
    private String name;

    @XmlElement(name="przelicznik")
    private Integer converter;

    @XmlElement(name="kod_waluty")
    private CurrencyCode currencyCode;

    @XmlJavaTypeAdapter(BigDecimalEUAdapter.class)
    @XmlElement(name="kurs_kupna")
    private BigDecimal buyRate;

    @XmlJavaTypeAdapter(BigDecimalEUAdapter.class)
    @XmlElement(name="kurs_sprzedazy")
    private BigDecimal sellRate;

    public String getName() {
        return name;
    }

    public Integer getConverter() {
        return converter;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public Boolean isTheSameCurrency(CurrencyCode currencyCode){
        return this.getCurrencyCode().equals(currencyCode);
    }
}
