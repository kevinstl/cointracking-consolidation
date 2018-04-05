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

import java.io.IOException;
import java.io.Writer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvWriterServiceTest {

    @InjectMocks
    private CsvWriterService classUnderTest;

    @Mock
    private StatefulBeanToCsvFactory statefulBeanToCsvFactory;

    @Mock
    private StatefulBeanToCsv statefulBeanToCsv;

    @Mock
    private CointrackingTransaction cointrackingTransaction;

    @Mock
    private Writer writer;

    private String destinationCsvFileName = "destinationCsvFileName";


    @Test
    public void toCsvString_returnsCsvString() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {

        when(statefulBeanToCsvFactory.build(writer)).thenReturn(statefulBeanToCsv);


//        classUnderTest.toCsvString(cointrackingTransaction, destinationCsvFileName);
        classUnderTest.toCsvString(new CointrackingTransaction(), destinationCsvFileName);


        verify(statefulBeanToCsvFactory).build(writer);
        verify(statefulBeanToCsv).write(cointrackingTransaction);
    }
}
