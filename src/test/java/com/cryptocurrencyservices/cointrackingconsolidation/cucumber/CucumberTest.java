package com.cryptocurrencyservices.cointrackingconsolidation.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(plugin = "pretty", features = "src/test/features")
//@CucumberOptions(plugin = "pretty", features = "src/test/features", glue = {"com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs"})
@CucumberOptions(plugin = "pretty",
        monochrome = true,
        tags = {"@ALL_TESTS"}
        ,
        features = {"src/test/java/com/cryptocurrencyservices/cointrackingconsolidation/cucumber/features"}
        ,
        glue = {"com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs"}
//        ,
//        glue = {"com/cryptocurrencyservices/cointrackingconsolidation/cucumber/stepdefs"}
)
public class CucumberTest  {



}
