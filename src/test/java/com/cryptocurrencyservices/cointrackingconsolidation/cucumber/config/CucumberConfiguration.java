package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.config;

import com.cryptocurrencyservices.cointrackingconsolidation.config.CointrackingConsolidationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by kevin on 10/11/15.
 */
@Configuration
@ComponentScan("com.cryptocurrencyservices.cointrackingconsolidation.cucumber")
@Import({
        CointrackingConsolidationConfig.class
})
public class CucumberConfiguration {

    public CucumberConfiguration() {
        System.out.println("test");
    }
}
