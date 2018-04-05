package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.factory.CsvReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CsvProcessorService {

    @Autowired
    private CsvReaderFactory csvReaderFactory;

    public void process(String csvFileName) {



//        CSVReader reader = null;
//        try {
////            reader = new CSVReader(new FileReader(csvFileName));
//            reader = csvReaderFactory.build(csvFileName);
//            String[] line;
//            while ((line = reader.readNext()) != null) {
//
//                System.out.println("line[0]: " + line[0]);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
