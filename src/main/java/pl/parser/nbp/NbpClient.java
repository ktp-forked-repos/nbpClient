package pl.parser.nbp;

import pl.parser.nbp.model.ExchangeRatesTable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Created by eadalac on 2017-02-16.
 */
public class NbpClient {

    private final String filenamesAdressTemplate = "http://www.nbp.pl/kursy/xml/dir%YEAR%.txt";
    private final String exchangeRatesTableAdressTemplate = "http://www.nbp.pl/kursy/xml/%FILENAME%";

    public String getExchangeRatesFilenameForReleaseDate(LocalDate publicationDate) {
        Integer givenYear = new Integer(publicationDate.getYear());
        Stream<String> linesStream = getXmlNamesAsStream(givenYear);
        String exchangeRatefFilenameWithoutFiletype = linesStream
                .filter(fileName -> fileName.contains(formatIntoNbpFormat(publicationDate)) )
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("filename for " + publicationDate + " not found"));
        String exchangeRatefFilenameWithFiletype = exchangeRatefFilenameWithoutFiletype.trim() + ".xml";
        return exchangeRatefFilenameWithFiletype;
    }

    public ExchangeRatesTable getExchangeRatesTable(String nbpTableFilename){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRatesTable.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            InputStream xmlExchangeRates = getXmlWithExchangeRatesTable(nbpTableFilename);
            ExchangeRatesTable exchangeRatesTable = (ExchangeRatesTable) jaxbUnmarshaller.unmarshal(xmlExchangeRates);

            return exchangeRatesTable;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private URL createURLForGivenYear(Integer givenYear) {
        try {
            if (givenYear == LocalDate.now().getYear()) {
                return new URL(filenamesAdressTemplate.replace("%YEAR%", ""));
            } else {
                return new URL(filenamesAdressTemplate.replace("%YEAR%", givenYear.toString()));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<String> getXmlNamesAsStream(Integer givenYear) {
        try {
            URL adressOfExchangeRatesFileListForGivenYear = createURLForGivenYear(givenYear);
            BufferedReader exchangeRatesFileReader =
                    new BufferedReader(
                            new InputStreamReader(adressOfExchangeRatesFileListForGivenYear.openStream(), "UTF-8"));
            Stream linesStream = exchangeRatesFileReader.lines()
                    .map(s -> s.replaceAll("[^a-z0-9]",""));
            return linesStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getXmlWithExchangeRatesTable(String filename){
        try {
            URL url = new URL(exchangeRatesTableAdressTemplate.replace("%FILENAME%", filename));
            InputStream initialStream =  url.openStream();
            return initialStream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CharSequence formatIntoNbpFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        return date.format(formatter);
    }
}
