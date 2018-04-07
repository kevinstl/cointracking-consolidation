package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanWriterFactory;
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
import java.util.ArrayList;
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
    private TransactionAggregatorService transactionAggregatorService;

    private String line2;
    private String sourcePolonexFileName;
    private String destinationCsvFileName;
    private String[] poloniexHeader = {""};
    private String[] cointrackingHeader = {""};
    private Class sourceClassType = PoloniexTransaction.class;
    private Class destinationClassType = CointrackingTransaction.class;

    private PoloniexTransaction poloniexHeaderRecord = new PoloniexTransaction();
    private PoloniexTransaction poloniexTransaction1 = new PoloniexTransaction();
    private PoloniexTransaction poloniexTransaction2 = new PoloniexTransaction();

    private CointrackingTransaction cointrackingTransaction1 = new CointrackingTransaction();
    private CointrackingTransaction cointrackingTransaction2 = new CointrackingTransaction();

    private CointrackingTransaction aggregateCointrackingTransaction1 = new CointrackingTransaction();

    private Map<String, CointrackingTransaction> aggregateCointrackingTransactions1 = new LinkedHashMap<>();

    @BeforeEach
    public void setup(){
        aggregateCointrackingTransactions1.put(KEY_1, aggregateCointrackingTransaction1);
    }


    @Test
    public void consolidate_consolidates() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(sourceClassType)).thenReturn(poloniexHeader);
        when(csvBeanReaderFactory.build(sourcePolonexFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(sourceClassType, poloniexHeader)).thenReturn(poloniexTransaction1, poloniexTransaction2, null);

        when(csvHeaderFactory.build(destinationClassType)).thenReturn(cointrackingHeader);
        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);


        classUnderTest.consolidate(sourcePolonexFileName, sourceClassType, destinationCsvFileName, destinationClassType);


        verify(csvBeanReaderFactory).build(sourcePolonexFileName);
        verify(csvHeaderFactory).build(sourceClassType);

        verify(csvBeanReader, times(3)).read(sourceClassType, poloniexHeader);

        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvHeaderFactory).build(destinationClassType);
        verify(csvBeanWriter).writeHeader(cointrackingHeader);
        verify(csvBeanWriter, times(2)).write(any(CointrackingTransaction.class));

//        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction1);
//        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction2);

    }


    @Test
    public void consolidatePoloniex_consolidatesPoloniex() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(sourceClassType)).thenReturn(poloniexHeader);
        when(csvBeanReaderFactory.build(sourcePolonexFileName)).thenReturn(csvBeanReader);
//        when(csvBeanReader.read(sourceClassType, poloniexHeader)).thenReturn(poloniexHeaderRecord, poloniexTransaction1, poloniexTransaction2, null);
        when(csvBeanReader.read(sourceClassType, poloniexHeader)).thenReturn(poloniexHeaderRecord, poloniexTransaction1, poloniexTransaction2, null);

        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction1)).thenReturn(cointrackingTransaction1);
        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction2)).thenReturn(cointrackingTransaction2);

        when(transactionAggregatorService.getaggregatedCointrackingTransactions()).thenReturn(aggregateCointrackingTransactions1);

        when(csvHeaderFactory.build(destinationClassType)).thenReturn(cointrackingHeader);
        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);


        classUnderTest.consolidatePoloniex(sourcePolonexFileName, destinationCsvFileName);


        verify(csvBeanReaderFactory).build(sourcePolonexFileName);
        verify(csvHeaderFactory).build(sourceClassType);

        verify(transactionAggregatorService).init();

        verify(csvBeanReader, times(4)).read(sourceClassType, poloniexHeader);

        verify(transactionAggregatorService).aggregate(cointrackingTransaction1);
        verify(transactionAggregatorService).aggregate(cointrackingTransaction2);

        verify(poloniexToCointrackingMarshaller, times(0)).marshall(poloniexHeaderRecord);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction1);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction2);

        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvHeaderFactory).build(destinationClassType);
        verify(csvBeanWriter).writeHeader(cointrackingHeader);

        verify(csvBeanWriter, times(1)).write(aggregateCointrackingTransaction1, cointrackingHeader);



    }

    @Test
    public void consolidateToList_consolidatesToList() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        when(csvHeaderFactory.build(sourceClassType)).thenReturn(poloniexHeader);
        when(csvBeanReaderFactory.build(sourcePolonexFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(sourceClassType, poloniexHeader)).thenReturn(poloniexHeaderRecord, poloniexTransaction1, poloniexTransaction2, null);


        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction1)).thenReturn(cointrackingTransaction1);
        when(poloniexToCointrackingMarshaller.marshall(poloniexTransaction2)).thenReturn(cointrackingTransaction2);


        List<CointrackingTransaction> cointrackingTransactions =
                classUnderTest.consolidateToList(sourcePolonexFileName, destinationCsvFileName);


        assertNotNull(cointrackingTransactions);
        assertEquals(cointrackingTransaction1, cointrackingTransactions.get(0));
        assertEquals(cointrackingTransaction2, cointrackingTransactions.get(1));

        verify(csvBeanReaderFactory).build(sourcePolonexFileName);
        verify(csvHeaderFactory).build(sourceClassType);

        verify(csvBeanReader, times(4)).read(sourceClassType, poloniexHeader);


        verify(poloniexToCointrackingMarshaller, times(0)).marshall(poloniexHeaderRecord);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction1);
        verify(poloniexToCointrackingMarshaller).marshall(poloniexTransaction2);

    }


}
