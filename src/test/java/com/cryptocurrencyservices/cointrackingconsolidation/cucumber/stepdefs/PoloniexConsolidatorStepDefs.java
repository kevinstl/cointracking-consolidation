package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs;

import com.cryptocurrencyservices.cointrackingconsolidation.service.ConsolidatorService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertNotNull;

public class PoloniexConsolidatorStepDefs extends StepDefs {

    @Autowired
    private ConsolidatorService consolidatorService;

    @Given("^I have access to the Poloniex Consolidator Service$")
    public void i_have_access_to_the_Poloniex_Consolidator_Service() throws Throwable {

        assertNotNull(consolidatorService);
    }

    @When("^I use the Poloniex Consolidator Service to consoliate$")
    public void i_use_the_Poloniex_Consolidator_Service_to_consoliate() throws Throwable {

        String line = "2017-05-31 23:57:24,OMNI/BTC,Exchange,Buy,0.02730000,1.06458074,0.02906305,0.24%,20515343179,-0.02906305,1.06202575";

//        poloniexConsolidatorService.consolidate(line, );
    }

    @Then("^I see the poloniex transactions consolidated into a csv file$")
    public void i_see_the_poloniex_transactions_consolidated_into_a_csv_file() throws Throwable {
    }

}
