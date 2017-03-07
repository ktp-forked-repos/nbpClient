package pl.parser.nbp;

import org.testng.annotations.Test;
import pl.parser.nbp.model.adapters.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;



/**
 * Created by eadalac on 2017-02-14.
 */
public class LocalDateAdapterTest {

    @Test
    public void shouldMarshalLocalDate() throws Exception {
        XmlAdapter<String, LocalDate> localDateAdapter = new LocalDateAdapter();
        String date = localDateAdapter.marshal(LocalDate.parse("2016-01-01"));
        assertThat(date).isEqualTo("2016-01-01");
    }

    @Test
    public void shouldUnmarshalStrings() throws Exception {
        XmlAdapter<String, LocalDate> localDateAdapter = new LocalDateAdapter();
        LocalDate date = localDateAdapter.unmarshal("2016-01-01");
        assertThat(date).isEqualTo(LocalDate.parse("2016-01-01"));
    }
}