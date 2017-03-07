package pl.parser.nbp.model;

import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eadalac on 2017-02-14.
 */
public class ExchangeRateTest {

    @Test
    private void souldBeCreatedFromXML() throws JAXBException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("pozycja.xml").getFile());
        JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRate.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ExchangeRate exchangeRate = (ExchangeRate) jaxbUnmarshaller.unmarshal(file);

        assertThat(exchangeRate.getName()).isEqualTo("dolar amerykański");
        assertThat(exchangeRate.getCurrencyCode()).isEqualTo(CurrencyCode.USD);
        assertThat(exchangeRate.getConverter()).isEqualTo(1);
        assertThat(exchangeRate.getBuyRate()).isEqualTo("2.8210");
        assertThat(exchangeRate.getSellRate()).isEqualTo("2.8780");

    }


}