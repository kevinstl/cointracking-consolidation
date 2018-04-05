package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvBeanWriterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;

import java.io.IOException;

@Component
public class CsvWriterService {

    @Autowired
    private CsvBeanWriterFactory csvBeanWriterFactory;

    @Autowired
    private CsvHeaderFactory csvHeaderFactory;

    public void toCsvString(Class aClass, Object recordObject, String destinationCsvFileName) throws IOException {
        CsvBeanWriter beanWriter = csvBeanWriterFactory.build(destinationCsvFileName);
        String[] header = csvHeaderFactory.build(aClass);

        beanWriter.writeHeader(header);
        beanWriter.write(recordObject, header);

        beanWriter.close();

    }

}
