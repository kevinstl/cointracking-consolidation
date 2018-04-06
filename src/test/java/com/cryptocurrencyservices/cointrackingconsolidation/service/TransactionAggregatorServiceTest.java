package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TransactionAggregatorServiceTest {

    @InjectMocks
    private TransactionAggregatorService transactionAggregatorService;

    private CointrackingTransaction cointrackingTransaction1;
    private CointrackingTransaction cointrackingTransaction2;

    @BeforeEach
    public void setup(){
        cointrackingTransaction1 = new CointrackingTransaction();
        cointrackingTransaction1.setBuyamount("1");
        cointrackingTransaction2 = new CointrackingTransaction();
        cointrackingTransaction2.setBuyamount("1");
    }

    @Test
    public void aggregate_returnsPassedInTransaction(){
                transactionAggregatorService.aggregate(cointrackingTransaction1);

        transactionAggregatorService.init();

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
    }

    @Test
    public void aggregate_sumsBuyAmountFromExistingTransaction(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1);
        transactionAggregatorService.aggregate(cointrackingTransaction2);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertEquals("2", aggregatedCointrackingTransactions.get("1").getBuyamount());
    }

    @Test
    public void init_createsEmptyAggregatedCointrackingTransactions(){

        transactionAggregatorService.aggregate(cointrackingTransaction1);
        transactionAggregatorService.aggregate(cointrackingTransaction2);

        transactionAggregatorService.init();

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertTrue(aggregatedCointrackingTransactions.isEmpty());


    }
}
