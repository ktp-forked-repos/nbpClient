package pl.parser.nbp;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.moment.Variance;
import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eadalac on 2017-02-14.
 */
public class AppController {

    ExchangeRateHistory exchangeRateHistory;

    public AppController(ExchangeRateHistory exchangeRateHistory) {
        this.exchangeRateHistory = exchangeRateHistory;
    }

    public void printStatistics(CurrencyCode currencyCode, LocalDate start, LocalDate end) {
        List<ExchangeRate> ratesForStatistics = exchangeRateHistory.getExchangeRates(currencyCode, start, end);

        List<Double> buyRates = ratesForStatistics.stream()
                .map(o -> o.getBuyRate().doubleValue())
                .collect(Collectors.toList());
        List<Double> sellRates = ratesForStatistics.stream()
                .map(o -> o.getSellRate().doubleValue())
                .collect(Collectors.toList());

        DescriptiveStatistics buyStats = getDescriptiveStatistics(buyRates);
        DescriptiveStatistics sellStats = getDescriptiveStatistics(sellRates);
        sellStats.setVarianceImpl(new Variance(false));

        printStats(buyStats.getMean(), sellStats.getStandardDeviation());
    }

    private DescriptiveStatistics getDescriptiveStatistics(List<Double> doubles) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        doubles.forEach(d -> stats.addValue(d));
        return stats;
    }

    private void printStats(Double buyMean, Double sellStdDev) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        System.out.println(df.format(buyMean));
        System.out.println(df.format(sellStdDev));
    }
}
