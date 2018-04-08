package com.cryptocurrencyservices.cointrackingconsolidation.config;


import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.service.ConsolidatorService;
import com.cryptocurrencyservices.cointrackingconsolidation.service.CsvReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.cryptocurrencyservices.cointrackingconsolidation")
public class CointrackingConsolidationConfig {

    @Autowired
    private ConsolidatorService consolidatorService;

    private String sourceCsvFileName = null;
    private String destinatinCsvFileName = null;

    public void start() throws IOException {

        sourceCsvFileName = System.getenv("SOURCE_POLONIEX_CSV_FILE_NAME");
        destinatinCsvFileName = System.getenv("DESTINATION_CSV_FILE_NAME");

        consolidatorService.consolidatePoloniex(
                sourceCsvFileName,
                destinatinCsvFileName
        );

    }

}