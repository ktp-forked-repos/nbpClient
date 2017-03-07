package pl.parser.nbp.model;


import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by eadalac on 2017-02-14.
 */

public class ExchangeRatesTableTest {

    @Test
    private void souldBeCreatedFromXML() throws JAXBException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("c073z070413.xml").getFile());
        JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRatesTable.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ExchangeRatesTable exchangeRatesTable = (ExchangeRatesTable) jaxbUnmarshaller.unmarshal(file);

        assertThat(exchangeRatesTable.getType()).isEqualTo(TableType.C);
        assertThat(exchangeRatesTable.getNumber()).isEqualTo("73/C/NBP/2007");
        assertThat(exchangeRatesTable.getQuotationDate().toString()).isEqualTo("2007-04-12");
        assertThat(exchangeRatesTable.getReleaseDate().toString()).isEqualTo("2007-04-13");
        assertThat(exchangeRatesTable.getExchangeRates()).hasSize(14);
        assertThat(exchangeRatesTable.getExchangeRate(CurrencyCode.AUD).getBuyRate()).isEqualTo("2.3292");
    }

}