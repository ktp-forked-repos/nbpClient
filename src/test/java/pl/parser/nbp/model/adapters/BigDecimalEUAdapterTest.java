package pl.parser.nbp.model.adapters;

import org.testng.annotations.Test;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eadalac on 2017-02-14.
 */
public class BigDecimalEUAdapterTest {

    @Test
    public void shouldUnmarshalStringsWithEUSeparation() throws Exception {
        XmlAdapter<String, BigDecimal> bigDecimalAdapter = new BigDecimalEUAdapter();
        BigDecimal number = bigDecimalAdapter.unmarshal("12,34");
        assertThat(number).isEqualTo(new BigDecimal("12.34"));
    }

    @Test
    public void shouldMarshalBigDecimal() throws Exception {
        XmlAdapter<String, BigDecimal> bigDecimalAdapter = new BigDecimalEUAdapter();
        String number = bigDecimalAdapter.marshal(new BigDecimal("12.34"));
        assertThat(number).isEqualTo("12,34");
    }

}