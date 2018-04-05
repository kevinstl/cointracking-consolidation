package com.cryptocurrencyservices.cointrackingconsolidation;

import com.cryptocurrencyservices.cointrackingconsolidation.config.CointrackingConsolidationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class ContrackingConsolidationMain {

    public static void main(String[] args) throws IOException {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(CointrackingConsolidationConfig.class);

        CointrackingConsolidationConfig cointrackingConsolidationConfig = context.getBean(CointrackingConsolidationConfig.class);
        cointrackingConsolidationConfig.start();

    }
}