package com.cryptocurrencyservices.cointrackingconsolidation.config;


import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.service.CsvReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.cryptocurrencyservices.cointrackingconsolidation")
public class CointrackingConsolidationConfig {

    @Autowired
    CsvReaderService csvReaderService;

    public void start() throws IOException {
        String csvFileName = System.getenv("CSV_FILE");

        csvReaderService.process(csvFileName, new PoloniexTransaction().getClass());

        System.out.println("hello");
    }

}