package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.StatefulBeanToCsvFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PoloniexConsolidatorServiceTest {

    @InjectMocks
    private PoloniexConsolidatorService classUnderTest;

    private String line1;
    private String line2;
    private String destinationCsvFileName;


    @Test
    public void consolidate_consolidates(){
//        classUnderTest.consolidate(line1, destinationCsvFileName);

//        assertNotNull(consolidatedLine);
    }

//    @Test
//    public void consolidate_re
}
