package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanWriterFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ConsolidatorServiceTest {

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

    private String line1;
    private String line2;
    private String sourcePolonexFileName;
    private String destinationCsvFileName;
    private String[] poloniexHeader = {""};
    private String[] cointrackingHeader = {""};
    private Class sourceClassType = PoloniexTransaction.class;
    private Class destinationClassType = CointrackingTransaction.class;

    private PoloniexTransaction poloniexTransaction1 = new PoloniexTransaction();
    private PoloniexTransaction poloniexTransaction2 = new PoloniexTransaction();


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
        verify(csvBeanWriter).write(cointrackingHeader);
        verify(csvBeanWriter, times(2)).write(any(CointrackingTransaction.class));

    }

}
