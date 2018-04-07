package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanWriterFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.marshall.PoloniexToCointrackingMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ConsolidatorService {

    @Autowired
    private CsvBeanReaderFactory csvBeanReaderFactory;

    @Autowired
    private CsvBeanWriterFactory csvBeanWriterFactory;

    @Autowired
    private CsvHeaderFactory csvHeaderFactory;

    @Autowired
    private PoloniexToCointrackingMarshaller poloniexToCointrackingMarshaller;

    @Autowired
    private TransactionAggregatorService transactionAggregatorService;

    public <SourceT, DestinationT> void consolidate(String sourcePolonexFileName,
                                                     Class<SourceT> sourceClassType,
                                                     String destinationCsvFileName,
                                                     Class<DestinationT> destinationClassType) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourcePolonexFileName);
        final String[] sourceHeader = csvHeaderFactory.build(sourceClassType);

        CsvBeanWriter csvBeanWriter = csvBeanWriterFactory.build(destinationCsvFileName);
        final String[] destinatinHeader = csvHeaderFactory.build(destinationClassType);
        csvBeanWriter.writeHeader(destinatinHeader);

        SourceT sourceRecordObject = null;
        while( (sourceRecordObject = csvBeanReader.read(sourceClassType, sourceHeader) ) != null){
            System.out.println(sourceRecordObject);
            DestinationT destinationRecordObject = destinationClassType.getDeclaredConstructor().newInstance();
//            DestinationT destinationRecordObject = sourceRecordObject.marshall();
            csvBeanWriter.write(destinationRecordObject);
        }

        csvBeanReader.close();
        csvBeanWriter.close();

    }

    public void consolidatePoloniex(String sourcePolonexFileName, String destinationCsvFileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourcePolonexFileName);
        final String[] sourceHeader = csvHeaderFactory.build(PoloniexTransaction.class);
//        final String[] sourceHeader = csvBeanReader.getHeader(true);

        transactionAggregatorService.init();

        PoloniexTransaction sourceRecordObject = null;

        int row = 0;
        while((sourceRecordObject = csvBeanReader.read(PoloniexTransaction.class, sourceHeader) ) != null){
            System.out.println(sourceRecordObject);
            if(row > 0){
                CointrackingTransaction destinationRecordObject = poloniexToCointrackingMarshaller.marshall(sourceRecordObject);

                transactionAggregatorService.aggregate(destinationRecordObject);

            }
            row ++;
        }

        Map<String, CointrackingTransaction> aggregateCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();
        CsvBeanWriter csvBeanWriter = csvBeanWriterFactory.build(destinationCsvFileName);
        final String[] destinatinHeader = csvHeaderFactory.build(CointrackingTransaction.class);
        csvBeanWriter.writeHeader(destinatinHeader);
        aggregateCointrackingTransactions.forEach((k, v) -> {
            try {
                csvBeanWriter.write(v, destinatinHeader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//                csvBeanWriter.write(destinationRecordObject, destinatinHeader);

        csvBeanReader.close();
        csvBeanWriter.close();
    }

    public List<CointrackingTransaction> consolidateToList(String sourcePolonexFileName, String destinationCsvFileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourcePolonexFileName);
        final String[] sourceHeader = csvHeaderFactory.build(PoloniexTransaction.class);

        PoloniexTransaction sourceRecordObject = null;

        ArrayList<CointrackingTransaction> cointrackingTransactions = new ArrayList<>();

        int row = 0;
        while((sourceRecordObject = csvBeanReader.read(PoloniexTransaction.class, sourceHeader) ) != null){
            System.out.println(sourceRecordObject);
            if(row > 0){
                cointrackingTransactions.add(poloniexToCointrackingMarshaller.marshall(sourceRecordObject));
            }
            row ++;
        }

        csvBeanReader.close();
        return cointrackingTransactions;
    }
}
