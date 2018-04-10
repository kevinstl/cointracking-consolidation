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

    private String sourceCsvFileDir = null;
//    private String sourceCsvFileName = null;
    private String destinationCsvFileDir = null;
    private String destinationCsvFileName = null;
    private String destinationCsvFilePrefix = null;
//    private String destinationCsvFileNameFull = null;

    public void start() throws IOException {

        sourceCsvFileDir = System.getenv("SOURCE_CSV_FILE_DIR");

        destinationCsvFileDir = System.getenv("DESTINATION_CSV_FILE_DIR");
        destinationCsvFilePrefix = System.getenv("DESTINATION_CSV_FILE_PREFIX");
        destinationCsvFileName = System.getenv("DESTINATION_CSV_FILE_NAME");

//        processPoloniex("POLONIEX");
        processBittrex("BITTREX");
//        processBitfinex("BITFINEX");

    }

    private void processPoloniex(String exchange) throws IOException {
        String sourceCsvFileName = buildSourceCsvFileName(exchange);
        String destinationCsvFileNameFull = buildDestinationCsvFileNameFull(sourceCsvFileName);
        consolidatorService.consolidatePoloniex(
                sourceCsvFileDir + sourceCsvFileName,
                destinationCsvFileNameFull
        );
    }

    private void processBittrex(String exchange) throws IOException {
        String sourceCsvFileName = buildSourceCsvFileName(exchange);
        String destinationCsvFileNameFull = buildDestinationCsvFileNameFull(sourceCsvFileName);
        consolidatorService.consolidateBittrex(
                sourceCsvFileDir + sourceCsvFileName,
                destinationCsvFileNameFull
        );
    }

    private void processBitfinex(String exchange) throws IOException {
        String sourceCsvFileName = buildSourceCsvFileName(exchange);
        String destinationCsvFileNameFull = buildDestinationCsvFileNameFull(sourceCsvFileName);
        consolidatorService.consolidateBitfinex(
                sourceCsvFileDir + sourceCsvFileName,
                destinationCsvFileNameFull
        );
    }


    private String buildDestinationCsvFileNameFull(String sourceCsvFileName) {
        return destinationCsvFileDir + destinationCsvFilePrefix + sourceCsvFileName;
    }

    private String buildSourceCsvFileName(String exchange) {
        return System.getenv("SOURCE_" + exchange + "_CSV_FILE_NAME");
    }

}