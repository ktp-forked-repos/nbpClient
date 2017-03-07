package pl.parser.nbp.model.adapters;

/**
 * Created by eadalac on 2017-02-14.
 */

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter  extends XmlAdapter<String, LocalDate>{

    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }

}
