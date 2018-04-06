package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.service.ConsolidatorService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertNotNull;

public class ConsolidatorStepDefs extends StepDefs {

    @Autowired
    private ConsolidatorService consolidatorService;

    String sourceCsvFileName = null;
    private String destinatinCsvFileName = null;

    private CointrackingTransaction cointrackingTransaction = new CointrackingTransaction();

    @Given("^I have access to the Poloniex Consolidator Service$")
    public void i_have_access_to_the_Poloniex_Consolidator_Service() throws Throwable {

        assertNotNull(consolidatorService);

        sourceCsvFileName = System.getenv("SOURCE_POLONIEX_CSV_FILE_NAME");
        destinatinCsvFileName = System.getenv("DESTINATION_CSV_FILE_NAME");

        cointrackingTransaction.setType("Buy");
    }

    @When("^I use the Poloniex Consolidator Service to consoliate$")
    public void i_use_the_Poloniex_Consolidator_Service_to_consoliate() throws Throwable {

        consolidatorService.consolidate(
                sourceCsvFileName,
                PoloniexTransaction.class,
                destinatinCsvFileName,
//                cointrackingTransaction.getClass()
                CointrackingTransaction.class
        );
    }

    @Then("^I see the poloniex transactions consolidated into a csv file$")
    public void i_see_the_poloniex_transactions_consolidated_into_a_csv_file() throws Throwable {
    }

}