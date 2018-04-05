package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.ICsvBeanWriterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.io.ICsvBeanWriter;

import java.io.IOException;

@Component
public class CsvWriterService {

    @Autowired
    private ICsvBeanWriterFactory iCsvBeanWriterFactory;

    @Autowired
    private CsvHeaderFactory csvHeaderFactory;

    public void toCsvString(Class aClass, Object recordObject, String destinationCsvFileName) throws IOException {
        ICsvBeanWriter beanWriter = iCsvBeanWriterFactory.build(destinationCsvFileName);
        String[] header = csvHeaderFactory.build(aClass);

        beanWriter.writeHeader(header);
        beanWriter.write(recordObject, header);

        beanWriter.close();

    }

}
