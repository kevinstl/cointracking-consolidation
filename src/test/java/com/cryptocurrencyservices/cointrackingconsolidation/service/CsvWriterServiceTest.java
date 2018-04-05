package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanWriterFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.supercsv.io.CsvBeanWriter;

import java.io.IOException;
import java.io.Writer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvWriterServiceTest {

    @InjectMocks
    private CsvWriterService classUnderTest;

    @Mock
    private CsvBeanWriterFactory csvBeanWriterFactory;

    @Mock
    private CsvBeanWriter csvBeanWriter;

    @Mock
    private CointrackingTransaction cointrackingTransaction;

    @Mock
    private Writer writer;

    @Mock
    private CsvHeaderFactory csvHeaderFactory;

    private String destinationCsvFileName = "destinationCsvFileName";

    private String[] header;


    @Test
    public void toCsvString_returnsCsvString() throws IOException {

        Class<? extends CointrackingTransaction> aClass = cointrackingTransaction.getClass();

        when(csvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(csvBeanWriter);
        when(csvHeaderFactory.build(aClass)).thenReturn(header);


        classUnderTest.toCsvString(aClass, cointrackingTransaction, destinationCsvFileName);


        verify(csvBeanWriterFactory).build(destinationCsvFileName);
        verify(csvBeanWriter).writeHeader(header);
        verify(csvBeanWriter).write(cointrackingTransaction, header);
    }
}
