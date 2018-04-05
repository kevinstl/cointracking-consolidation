package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvProcessorServiceTest {

    @InjectMocks
    private CsvProcessorService classUnderTest;

    @Mock
    private CsvReaderFactory csvReaderFactory;

    private String csfFileName;

    @Mock
    private CSVReader csvReader;

    @Test
    public void process_readsCsv() throws IOException {
        when(csvReaderFactory.build(csfFileName)).thenReturn(csvReader);

        classUnderTest.process(csfFileName);

        verify(csvReaderFactory).build(csfFileName);
        verify(csvReader).readNext();
    }
}
