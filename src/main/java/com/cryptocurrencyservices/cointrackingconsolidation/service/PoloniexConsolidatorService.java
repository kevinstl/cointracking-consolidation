package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvHeaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvReaderFactory;
import com.cryptocurrencyservices.cointrackingconsolidation.factory.ICsvBeanWriterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PoloniexConsolidatorService {

    @Autowired
    private CsvReaderFactory csvReaderFactory;

    @Autowired
    private ICsvBeanWriterFactory iCsvBeanWriterFactory;

    @Autowired
    private CsvHeaderFactory csvHeaderFactory;

    public void consolidate(String sourcePolonexFileName, String destinationCsvFileName) {

    }
}
