package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class CsvBeanReaderFactory {


    public CsvBeanReader build(String csvFileName) {
        CsvBeanReader csvBeanReader = null;
        try {
            csvBeanReader = new CsvBeanReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return csvBeanReader;
    }
}
