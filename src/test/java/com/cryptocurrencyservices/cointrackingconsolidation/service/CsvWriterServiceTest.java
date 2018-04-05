package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.ICsvBeanWriterFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.supercsv.io.ICsvBeanWriter;

import java.io.IOException;
import java.io.Writer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvWriterServiceTest {

    @InjectMocks
    private CsvWriterService classUnderTest;

    @Mock
    private ICsvBeanWriterFactory iCsvBeanWriterFactory;

    @Mock
    private ICsvBeanWriter ICsvBeanWriter;

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

        when(iCsvBeanWriterFactory.build(destinationCsvFileName)).thenReturn(ICsvBeanWriter);
        when(csvHeaderFactory.build(aClass)).thenReturn(header);


        classUnderTest.toCsvString(aClass, cointrackingTransaction, destinationCsvFileName);


        verify(iCsvBeanWriterFactory).build(destinationCsvFileName);
        verify(ICsvBeanWriter).writeHeader(header);
        verify(ICsvBeanWriter).write(cointrackingTransaction, header);
    }
}
