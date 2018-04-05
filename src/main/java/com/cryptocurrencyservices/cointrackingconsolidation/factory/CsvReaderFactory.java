package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CsvReaderFactory {

    @Autowired
    private FileReaderFactory fileReaderFactory;

//    public CSVReader build(String csfFileName) {
//        return new CSVReader(fileReaderFactory.build(csfFileName));
//    }
}
