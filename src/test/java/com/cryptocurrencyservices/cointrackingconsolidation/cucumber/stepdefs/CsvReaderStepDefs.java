package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.service.CsvReaderService;
import com.cryptocurrencyservices.cointrackingconsolidation.service.CsvWriterService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CsvReaderStepDefs extends StepDefs {

    @Autowired
    private CsvReaderService csvReaderService;

    String csvSourceFile = null;
    PoloniexTransaction poloniexTransaction = null;

    @Given("^I have a csv file and an object type$")
    public void i_have_a_csv_file_and_an_object_type() throws Throwable {
        csvSourceFile = System.getenv("SOURCE_POLONIEX_CSV_FILE_NAME");
    }

    @When("^I use the CSV Reader Service to read the csv into an object record$")
    public void i_use_the_CSV_Reader_Service_to_read_the_csv_into_an_object_record() throws Throwable {
        poloniexTransaction = csvReaderService.process(csvSourceFile, PoloniexTransaction.class);
    }

    @Then("^I see the expected values exist in the object record$")
    public void i_see_the_expected_values_exist_in_the_object_record() throws Throwable {
        assertNotNull(poloniexTransaction);
    }

}
