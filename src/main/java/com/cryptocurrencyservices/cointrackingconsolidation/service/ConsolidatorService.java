package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.BitfinexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.BittrexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanWriterFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.marshall.BitfinexToCointrackingMarshaller;
import com.cryptocurrencyservices.cointrackingconsolidation.marshall.BittrexToCointrackingMarshaller;
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
    private BittrexToCointrackingMarshaller bittrexToCointrackingMarshaller;

    @Autowired
    private BitfinexToCointrackingMarshaller bitfinexToCointrackingMarshaller;

    @Autowired
    private TransactionAggregatorService transactionAggregatorService;

    public void consolidatePoloniex
            (String sourcePolonexFileName, String destinationCsvFileName) throws IOException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourcePolonexFileName);
        final String[] sourceHeader = csvHeaderFactory.build(PoloniexTransaction.class);

        transactionAggregatorService.init();

        PoloniexTransaction sourceRecordObject = null;

        int row = 0;
        while((sourceRecordObject = csvBeanReader.read(PoloniexTransaction.class, sourceHeader) ) != null){
//            if(sourceRecordObject.getMarket().equals("XEM/BTC")) {
                if (row > 0) {
                    CointrackingTransaction destinationRecordObject = poloniexToCointrackingMarshaller.marshall(sourceRecordObject);
                    transactionAggregatorService.aggregate(destinationRecordObject);
                }
//            }
            row ++;
        }

        CsvBeanWriter csvBeanWriter = writeDestinationCsv(destinationCsvFileName);
        csvBeanReader.close();
        csvBeanWriter.close();
    }


    public void consolidateBittrex(String sourceCsvFileName, String destinationCsvFileName) throws IOException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourceCsvFileName);
        final String[] sourceHeader = csvHeaderFactory.build(BittrexTransaction.class);

        transactionAggregatorService.init();

        BittrexTransaction sourceRecordObject = null;

        int row = 0;
        while((sourceRecordObject = csvBeanReader.read(BittrexTransaction.class, sourceHeader) ) != null){
//            System.out.println(sourceRecordObject);

//            if(sourceRecordObject.getExchange().equals("BTC-XEM")) {
                if (row > 0) {
                    CointrackingTransaction destinationRecordObject = bittrexToCointrackingMarshaller.marshall(sourceRecordObject);

                    transactionAggregatorService.aggregate(destinationRecordObject);

                }
//            }
            row ++;
        }

        CsvBeanWriter csvBeanWriter = writeDestinationCsv(destinationCsvFileName);

        csvBeanReader.close();
        csvBeanWriter.close();
    }


    public void consolidateBitfinex(String sourceCsvFileName, String destinationCsvFileName) throws IOException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourceCsvFileName);
        final String[] sourceHeader = csvHeaderFactory.build(BitfinexTransaction.class);

        transactionAggregatorService.init();

        BitfinexTransaction sourceRecordObject = null;

        int row = 0;
        while((sourceRecordObject = csvBeanReader.read(BitfinexTransaction.class, sourceHeader) ) != null){
//            System.out.println(sourceRecordObject);

//            if(sourceRecordObject.getExchange().equals("BTC-XEM")) {
                if (row > 0) {
                    CointrackingTransaction destinationRecordObject = bitfinexToCointrackingMarshaller.marshall(sourceRecordObject);

                    transactionAggregatorService.aggregate(destinationRecordObject);

                }
//            }
            row ++;
        }

        CsvBeanWriter csvBeanWriter = writeDestinationCsv(destinationCsvFileName);

        csvBeanReader.close();
        csvBeanWriter.close();
    }


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
//            System.out.println(sourceRecordObject);
            DestinationT destinationRecordObject = destinationClassType.getDeclaredConstructor().newInstance();
//            DestinationT destinationRecordObject = sourceRecordObject.marshall();
            csvBeanWriter.write(destinationRecordObject);
        }

        csvBeanReader.close();
        csvBeanWriter.close();

    }


    private CsvBeanWriter writeDestinationCsv(String destinationCsvFileName) throws IOException {
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
        return csvBeanWriter;
    }

    public List<CointrackingTransaction> consolidateToList(String sourcePolonexFileName, String destinationCsvFileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(sourcePolonexFileName);
        final String[] sourceHeader = csvHeaderFactory.build(PoloniexTransaction.class);

        PoloniexTransaction sourceRecordObject = null;

        ArrayList<CointrackingTransaction> cointrackingTransactions = new ArrayList<>();

        int row = 0;
        while((sourceRecordObject = csvBeanReader.read(PoloniexTransaction.class, sourceHeader) ) != null){
//            System.out.println(sourceRecordObject);
            if(row > 0){
                cointrackingTransactions.add(poloniexToCointrackingMarshaller.marshall(sourceRecordObject));
            }
            row ++;
        }

        csvBeanReader.close();
        return cointrackingTransactions;
    }


}
