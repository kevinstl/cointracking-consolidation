package com.cryptocurrencyservices.cointrackingconsolidation.config;


import com.cryptocurrencyservices.cointrackingconsolidation.service.CsvProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.cryptocurrencyservices.cointrackingconsolidation")
public class CointrackingConsolidationConfig {

    @Autowired
    CsvProcessorService csvProcessorService;

    public void start() throws IOException {
        String csvFileName = System.getenv("CSV_FILE");

        csvProcessorService.process(csvFileName);

        System.out.println("hello");
    }

}