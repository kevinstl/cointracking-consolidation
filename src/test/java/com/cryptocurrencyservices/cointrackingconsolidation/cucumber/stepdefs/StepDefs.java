package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs;

import com.cryptocurrencyservices.cointrackingconsolidation.cucumber.config.CucumberConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = CucumberConfiguration.class)
public class StepDefs {
}
