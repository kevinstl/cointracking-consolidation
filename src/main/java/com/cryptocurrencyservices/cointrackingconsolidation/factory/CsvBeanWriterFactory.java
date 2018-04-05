package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class CsvBeanWriterFactory {

    public CsvBeanWriter build(String destinationCsvFileName) {

        CsvPreference ALWAYS_QUOTE =
                new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useQuoteMode(new AlwaysQuoteMode()).build();

        CsvBeanWriter csvBeanWriter = null;
        try {
            csvBeanWriter = new CsvBeanWriter(new FileWriter(destinationCsvFileName),
                    ALWAYS_QUOTE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvBeanWriter;
    }

}
