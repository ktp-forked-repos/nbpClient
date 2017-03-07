package pl.parser.nbp.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by eadalac on 2017-02-14.
 */
public class BigDecimalEUAdapter extends XmlAdapter<String, BigDecimal> {


    @Override
    public BigDecimal unmarshal(String s) throws Exception {
        return new BigDecimal(s.replaceAll(",", "."));
    }

    @Override
    public String marshal(BigDecimal value) throws Exception {
        return value.toString().replaceAll("[.]",",");
    }
}
