package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@Component
public class FileReaderFactory {
    public Reader build(String csfFileName) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(csfFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fileReader;
    }
}
