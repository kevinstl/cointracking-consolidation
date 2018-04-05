package com.cryptocurrencyservices.cointrackingconsolidation.cucumber.stepdefs;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
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

public class CsvWriterStepDefs extends StepDefs {

    @Autowired
    private CsvWriterService csvWriterService;
    
    private CointrackingTransaction cointrackingTransaction;
    private String destinatinCsvFileName = System.getenv("DESTINATION_CSV_FILE_NAME");

    private String type = "Buy";
    private Double buyAmount = 5.1;
    private String buyCur = "";
    private Double sellAmount = 0.0;
    private String sellCur;
    private Double feeAmount;
    private String feeCur;
    private String exchange;
    private String group;
    private String comment;
    private String tradeDate;

    @Given("^I have an individual cointracking transaction bean$")
    public void i_have_an_individual_transaction_record() throws Throwable {
        cointrackingTransaction = new CointrackingTransaction();
        cointrackingTransaction.setType(type);
        cointrackingTransaction.setBuyamount(buyAmount);
        cointrackingTransaction.setBuycur(buyCur);
        cointrackingTransaction.setSellamount(sellAmount);

    }

    @When("^I use the CSV Writer Service to save the cointracking transaction bean as a CSV record$")
    public void i_use_the_CSV_Writer_Service_to_save_the_transaction_record_as_a_CSV_record() throws Throwable {
        csvWriterService.toCsvString(cointrackingTransaction.getClass(), cointrackingTransaction, destinatinCsvFileName);
    }

    @Then("^I see the cointracking transaction bean contents are written to a csv file$")
    public void i_see_the_transaction_record_contents_are_written_to_a_csv_file() throws Throwable {
        File destinationCointrackingTransactionFile = new File(destinatinCsvFileName);

        BufferedReader br = new BufferedReader(new FileReader(destinationCointrackingTransactionFile));

        String st = new String(Files.readAllBytes(Paths.get(destinatinCsvFileName)));
        assertThat(st, containsString(Double.toString(buyAmount)));
    }

}
