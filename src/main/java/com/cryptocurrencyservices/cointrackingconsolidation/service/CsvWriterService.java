package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.StatefulBeanToCsvFactory;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvWriterService {

    @Autowired
    private StatefulBeanToCsvFactory statefulBeanToCsvFactory;

    public void toCsvString(CointrackingTransaction cointrackingTransaction, String destinationCsvFileName) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        CsvPreference ALWAYS_QUOTE =
                new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useQuoteMode(new AlwaysQuoteMode()).build();

        ICsvBeanWriter beanWriter = new CsvBeanWriter(new FileWriter(destinationCsvFileName),
                ALWAYS_QUOTE);

        Field[] fields = CointrackingTransaction.class.getDeclaredFields();
        List<String> header = new ArrayList<>();

        Arrays.stream(fields).forEach(field -> header.add(field.getName()));

        // the header elements are used to map the bean values to each column (names must match)
//        final String[] header = new String[] { "customerNo", "firstName", "lastName", "birthDate",
//                "mailingAddress", "married", "numberOfKids", "favouriteQuote", "email", "loyaltyPoints" };

        // write the header
        String[] headerArray = header.toArray(new String[header.size()]);
        beanWriter.writeHeader(headerArray);
        beanWriter.write(cointrackingTransaction, headerArray);

        beanWriter.close();


//        Writer writer = new FileWriter(destinationCsvFileName);
//
//        StatefulBeanToCsv<CointrackingTransaction> statefulBeanToCsv = statefulBeanToCsvFactory.build(writer);
//        statefulBeanToCsv.write(cointrackingTransaction);
//        writer.close();



//        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
//        FileWriter fileWriter = new FileWriter(destinationCsvFileName);
//        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
//
//        List<CointrackingTransaction> cointrackingTransactions = new ArrayList<>();
//        cointrackingTransactions.add(cointrackingTransaction);
//        csvFilePrinter.printRecord(cointrackingTransactions);
//
//        fileWriter.flush();
//        fileWriter.close();
//        csvFilePrinter.close();




    }

}
