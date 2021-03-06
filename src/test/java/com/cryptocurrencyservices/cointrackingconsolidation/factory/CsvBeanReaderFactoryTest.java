package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.supercsv.io.CsvBeanReader;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CsvBeanReaderFactoryTest {

    @InjectMocks
    private CsvBeanReaderFactory csvBeanReaderFactory;

    private String csvFileName = "/tmp/csvFileName";

    @Test
    public void build_returnsReader() throws IOException {

        File file = new File(csvFileName);

        try{
            file.createNewFile();

            CsvBeanReader csvBeanReader = csvBeanReaderFactory.build(csvFileName);

            assertNotNull(csvBeanReader);
        }

        finally{
            file.delete();
        }

    }

}
