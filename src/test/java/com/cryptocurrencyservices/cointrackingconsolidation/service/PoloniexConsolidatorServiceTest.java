package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.ICsvBeanWriterFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PoloniexConsolidatorServiceTest {

    @InjectMocks
    private PoloniexConsolidatorService classUnderTest;

    @Mock
    private CsvReaderFactory csvReaderFactory;

    @Mock
    private ICsvBeanWriterFactory iCsvBeanWriterFactory;

    @Mock
    private CsvHeaderFactory csvHeaderFactory;

    private String line1;
    private String line2;
    private String sourcePolonexFileName;
    private String destinationCsvFileName;


    @Test
    public void consolidate_consolidates(){

        classUnderTest.consolidate(sourcePolonexFileName, destinationCsvFileName);

    }

}
