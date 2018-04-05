package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.supercsv.io.CsvBeanReader;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvReaderServiceTest {

    @InjectMocks
    private CsvReaderService classUnderTest;

    @Mock
    private CsvBeanReaderFactory csvBeanReaderFactory;

    private String csvFileName;

    @Mock
    private CsvBeanReader csvBeanReader;

    private String[] header = {""};


    @Mock
    private PoloniexTransaction expectedPoloniexTransaction;

    @Mock
    private CsvHeaderFactory csvHeaderFactory;


    @BeforeEach
    public void setup(){

    }


    @Test
    public void process_readsCsv() throws IOException {
        when(csvBeanReaderFactory.build(csvFileName)).thenReturn(csvBeanReader);
        when(csvBeanReader.read(PoloniexTransaction.class, header)).thenReturn(expectedPoloniexTransaction);
        when(csvHeaderFactory.build(PoloniexTransaction.class)).thenReturn(header);


        PoloniexTransaction poloniexTransaction = classUnderTest.process(csvFileName, PoloniexTransaction.class);


        assertNotNull(poloniexTransaction);
        assertEquals(expectedPoloniexTransaction, poloniexTransaction);
        verify(csvBeanReaderFactory).build(csvFileName);
        verify(csvHeaderFactory).build(any(Class.class));
        verify(csvBeanReader).read(PoloniexTransaction.class, header);
    }
}
