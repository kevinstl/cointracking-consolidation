package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanReader;

import java.io.IOException;

@Component
public class CsvReaderService {

    @Autowired
    private CsvReaderFactory csvReaderFactory;

    @Autowired
    private CsvHeaderFactory csvHeaderFactory;

    public <T>T process(String csvFileName, Class<T> classType) throws IOException {

        CsvBeanReader csvBeanReader = csvReaderFactory.build(csvFileName);

//        final String[] header = csvBeanReader.getHeader(true);
        final String[] header = csvHeaderFactory.build(classType);

        T cvsRecordObject = csvBeanReader.read(classType, header);

        return cvsRecordObject;
    }
}
