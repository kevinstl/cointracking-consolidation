package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.service.ConsolidatorService;
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

public class ConsolidatorStepDefs extends StepDefs {

    @Autowired
    private ConsolidatorService consolidatorService;

    private String sourceCsvFileDir = null;
    private String sourceCsvFileName = null;
    private String destinationCsvFileDir = null;
    private String destinationCsvFileName = null;
    private String destinationCsvFilePrefix = null;
    private String destinationCsvFileNameFull = null;

    private CointrackingTransaction cointrackingTransaction = new CointrackingTransaction();

    @Given("^I have access to the \"([^\"]*)\" Consolidator Service$")
    public void i_have_access_to_the_Consolidator_Service(String exchange) throws Throwable {

        assertNotNull(consolidatorService);

        sourceCsvFileDir = System.getenv("SOURCE_CSV_FILE_DIR");
        sourceCsvFileName = System.getenv("SOURCE_" + exchange + "_CSV_FILE_NAME");

        destinationCsvFileDir = System.getenv("DESTINATION_CSV_FILE_DIR");
        destinationCsvFilePrefix = System.getenv("DESTINATION_CSV_FILE_PREFIX");
        destinationCsvFileName = System.getenv("DESTINATION_CSV_FILE_NAME");

        destinationCsvFileNameFull = destinationCsvFileDir + destinationCsvFilePrefix + sourceCsvFileName;

        cointrackingTransaction.setType("Buy");
    }

    @When("^I use the \"([^\"]*)\" Consolidator Service to consoliate$")
    public void i_use_the_Consolidator_Service_to_consoliate(String exchange) throws Throwable {

        if(exchange.equals(POLONIEX)) {
            consolidatorService.consolidatePoloniex(
                    sourceCsvFileDir + sourceCsvFileName,
                    destinationCsvFileNameFull
            );
        }
        else if(exchange.equals(BITTREX)) {
            consolidatorService.consolidateBittrex(
                    sourceCsvFileDir + sourceCsvFileName,
                    destinationCsvFileNameFull
            );
        }
    }

    @Then("^I see the \"([^\"]*)\" transactions consolidated into a csv file$")
    public void i_see_the_transactions_consolidated_into_a_csv_file(String exchange) throws Throwable {

        String st = new String(Files.readAllBytes(Paths.get(destinationCsvFileNameFull)));
        if(exchange.equals(POLONIEX)) {
            assertThat(st, containsString("OMNI"));
        } else if(exchange.equals(BITTREX)) {
            assertThat(st, containsString("EDG"));
        }
    }

}
