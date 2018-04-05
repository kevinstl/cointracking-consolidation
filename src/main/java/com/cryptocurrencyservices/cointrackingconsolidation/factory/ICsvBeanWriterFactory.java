package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class ICsvBeanWriterFactory {

    public ICsvBeanWriter build(String destinationCsvFileName) {

        CsvPreference ALWAYS_QUOTE =
                new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useQuoteMode(new AlwaysQuoteMode()).build();

        ICsvBeanWriter iCsvBeanWriter = null;
        try {
            iCsvBeanWriter = new CsvBeanWriter(new FileWriter(destinationCsvFileName),
                    ALWAYS_QUOTE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iCsvBeanWriter;
    }

}
