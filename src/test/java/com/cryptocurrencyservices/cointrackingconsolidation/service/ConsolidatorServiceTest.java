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
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ConsolidatorServiceTest {

    public static final String KEY_1 = "key1";
    @InjectMocks
    private ConsolidatorService classUnderTest;

    @Mock
    private CsvBeanReaderFactory csvBeanReaderFactory;

    @Mock
    private CsvBeanReader csvBeanReader;

    @Mock
    private CsvBeanWriter csvBeanWriter;

    @Mock
    private CsvBeanWriterFactory csvBeanWriterFactory;

    @Mock
    private CsvHeaderFactory csvHeaderFactory;

    @Mock
    private PoloniexToCointrackingMarshaller poloniexToCointrackingMarshaller;

    @Mock
    private BittrexToCointrackingMarshaller bittrexToCointrackingMarshaller;

    @Mock
    private BitfinexToCointrackingMarshaller bitfinexToCointrackingMarshaller;

    @Mock
    private TransactionAggregatorService transactionAggregatorService;

    private String line2;
    private String sourceFileName;
    private String destinationCsvFileName;
    private String[] header = {""};
    private String[] cointrackingHeader = {""};
    private Class poloniexSourceClassType = PoloniexTransaction.class;
    private Class bittrexSourceClassType = BittrexTransaction.class;
    private Class bitfinexSourceClassType = BitfinexTransaction.class;
    private Class destinationClassType = CointrackingTransaction.class;

    private PoloniexTransaction poloniexHeaderRecord = new PoloniexTransaction();
    private PoloniexTransaction poloniexTransaction1 = new PoloniexTransaction();
    private PoloniexTransaction poloniexTransaction2 = new PoloniexTransaction();

    private BittrexTransaction bittrexHeaderRecord = new BittrexTransaction();
    private BittrexTransaction bittrexTransaction1 = new BittrexTransaction();
    private BittrexTransaction bittrexTransaction2 = new BittrexTransaction();

    private BitfinexTransaction bitfinexHeaderRecord = new BitfinexTransaction();
    private BitfinexTransaction bitfinexTransaction1 = new BitfinexTransaction();
    private BitfinexTransaction bitfinexTransaction2 = new BitfinexTransaction();

    private CointrackingTransaction cointrackingTransaction1 = new CointrackingTransaction();
    private CointrackingTransaction cointrackingTransaction2 = new CointrackingTransaction();

    private CointrackingTransaction aggregateCointrackingTransaction1 = new CointrackingTransaction();

    private Map<String, CointrackingTransaction> aggregateCointrackingTransactions1 = new LinkedHashMap<>();

    @BeforeEach
    public void setup(){
        aggregateCointrackingTransactions1.put(KEY_1, aggregateCointrackingTransaction1);
    }



    @Test
    public void consolidatePoloniex_consolidatesPoloniex() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(poloniexSourceClassType)).thenReturn(header);
        when(csvBeanReaderFactory.build(sourceFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(poloniexSourceClassType, header)).thenReturn(poloniexHeaderRecord, poloniexTransaction1, poloniexTransaction2, null);

        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction1)).thenReturn(cointrackingTransaction1);
        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction2)).thenReturn(cointrackingTransaction2);

        when(transactionAggregatorService.getaggregatedCointrackingTransactions()).thenReturn(aggregateCointrackingTransactions1);

        when(csvHeaderFactory.build(destinationClassType)).thenReturn(cointrackingHeader);
        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);


        classUnderTest.consolidatePoloniex(sourceFileName, destinationCsvFileName);


        verify(csvBeanReaderFactory).build(sourceFileName);
        verify(csvHeaderFactory).build(poloniexSourceClassType);

        verify(transactionAggregatorService).init();

        verify(csvBeanReader, times(4)).read(poloniexSourceClassType, header);

        verify(transactionAggregatorService).aggregate(cointrackingTransaction1);
        verify(transactionAggregatorService).aggregate(cointrackingTransaction2);

        verify(poloniexToCointrackingMarshaller, times(0)).marshall(poloniexHeaderRecord);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction1);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction2);

        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvHeaderFactory).build(destinationClassType);
        verify(csvBeanWriter).writeHeader(cointrackingHeader);

        verify(csvBeanWriter, times(1)).write(aggregateCointrackingTransaction1, cointrackingHeader);

        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction1);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction2);
    }


    @Test
    public void consolidateBittrex_consolidatesBittrex() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(bittrexSourceClassType)).thenReturn(header);
        when(csvBeanReaderFactory.build(sourceFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(bittrexSourceClassType, header)).thenReturn(bittrexHeaderRecord, bittrexTransaction1, bittrexTransaction2, null);

        when(bittrexToCointrackingMarshaller.marshall(bittrexTransaction1)).thenReturn(cointrackingTransaction1);
        when(bittrexToCointrackingMarshaller.marshall(bittrexTransaction2)).thenReturn(cointrackingTransaction2);

        when(transactionAggregatorService.getaggregatedCointrackingTransactions()).thenReturn(aggregateCointrackingTransactions1);

        when(csvHeaderFactory.build(destinationClassType)).thenReturn(cointrackingHeader);
        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);


        classUnderTest.consolidateBittrex(sourceFileName, destinationCsvFileName);


        verify(csvBeanReaderFactory).build(sourceFileName);
        verify(csvHeaderFactory).build(bittrexSourceClassType);

        verify(transactionAggregatorService).init();

        verify(csvBeanReader, times(4)).read(bittrexSourceClassType, header);

        verify(transactionAggregatorService).aggregate(cointrackingTransaction1);
        verify(transactionAggregatorService).aggregate(cointrackingTransaction2);

        verify(bittrexToCointrackingMarshaller, times(0)).marshall(bittrexHeaderRecord);
        verify(bittrexToCointrackingMarshaller).marshall(bittrexTransaction1);
        verify(bittrexToCointrackingMarshaller).marshall(bittrexTransaction2);

        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvHeaderFactory).build(destinationClassType);
        verify(csvBeanWriter).writeHeader(cointrackingHeader);

        verify(csvBeanWriter, times(1)).write(aggregateCointrackingTransaction1, cointrackingHeader);

        verify(bittrexToCointrackingMarshaller).marshall(bittrexTransaction1);
        verify(bittrexToCointrackingMarshaller).marshall(bittrexTransaction2);
    }


    @Test
    public void consolidateBitfinex_consolidatesBitfinex() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(bitfinexSourceClassType)).thenReturn(header);
        when(csvBeanReaderFactory.build(sourceFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(bitfinexSourceClassType, header)).thenReturn(bitfinexHeaderRecord, bitfinexTransaction1, bitfinexTransaction2, null);

        when(bitfinexToCointrackingMarshaller.marshall(bitfinexTransaction1)).thenReturn(cointrackingTransaction1);
        when(bitfinexToCointrackingMarshaller.marshall(bitfinexTransaction2)).thenReturn(cointrackingTransaction2);

        when(transactionAggregatorService.getaggregatedCointrackingTransactions()).thenReturn(aggregateCointrackingTransactions1);

        when(csvHeaderFactory.build(destinationClassType)).thenReturn(cointrackingHeader);
        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);


        classUnderTest.consolidateBitfinex(sourceFileName, destinationCsvFileName);


        verify(csvBeanReaderFactory).build(sourceFileName);
        verify(csvHeaderFactory).build(bitfinexSourceClassType);

        verify(transactionAggregatorService).init();

        verify(csvBeanReader, times(4)).read(bitfinexSourceClassType, header);

        verify(transactionAggregatorService).aggregate(cointrackingTransaction1);
        verify(transactionAggregatorService).aggregate(cointrackingTransaction2);

        verify(bitfinexToCointrackingMarshaller, times(0)).marshall(bitfinexHeaderRecord);
        verify(bitfinexToCointrackingMarshaller).marshall(bitfinexTransaction1);
        verify(bitfinexToCointrackingMarshaller).marshall(bitfinexTransaction2);

        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvHeaderFactory).build(destinationClassType);
        verify(csvBeanWriter).writeHeader(cointrackingHeader);

        verify(csvBeanWriter, times(1)).write(aggregateCointrackingTransaction1, cointrackingHeader);

        verify(bitfinexToCointrackingMarshaller).marshall(bitfinexTransaction1);
        verify(bitfinexToCointrackingMarshaller).marshall(bitfinexTransaction2);
    }


    @Test
    public void consolidate_consolidates() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(poloniexSourceClassType)).thenReturn(header);
        when(csvBeanReaderFactory.build(sourceFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(poloniexSourceClassType, header)).thenReturn(poloniexTransaction1, poloniexTransaction2, null);

        when(csvHeaderFactory.build(destinationClassType)).thenReturn(cointrackingHeader);
        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);


        classUnderTest.consolidate(sourceFileName, poloniexSourceClassType, destinationCsvFileName, destinationClassType);


        verify(csvBeanReaderFactory).build(sourceFileName);
        verify(csvHeaderFactory).build(poloniexSourceClassType);

        verify(csvBeanReader, times(3)).read(poloniexSourceClassType, header);

        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvHeaderFactory).build(destinationClassType);
        verify(csvBeanWriter).writeHeader(cointrackingHeader);
        verify(csvBeanWriter, times(2)).write(any(CointrackingTransaction.class));



    }



    @Test
    public void consolidateToList_consolidatesToList() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(poloniexSourceClassType)).thenReturn(header);
        when(csvBeanReaderFactory.build(sourceFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(poloniexSourceClassType, header)).thenReturn(poloniexHeaderRecord, poloniexTransaction1, poloniexTransaction2, null);


        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction1)).thenReturn(cointrackingTransaction1);
        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction2)).thenReturn(cointrackingTransaction2);


        List<CointrackingTransaction> cointrackingTransactions =
                classUnderTest.consolidateToList(sourceFileName, destinationCsvFileName);


        assertNotNull(cointrackingTransactions);
        assertEquals(cointrackingTransaction1, cointrackingTransactions.get(0));
        assertEquals(cointrackingTransaction2, cointrackingTransactions.get(1));

        verify(csvBeanReaderFactory).build(sourceFileName);
        verify(csvHeaderFactory).build(poloniexSourceClassType);

        verify(csvBeanReader, times(4)).read(poloniexSourceClassType, header);


        verify(poloniexToCointrackingMarshaller, times(0)).marshall(poloniexHeaderRecord);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction1);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction2);

    }


}
