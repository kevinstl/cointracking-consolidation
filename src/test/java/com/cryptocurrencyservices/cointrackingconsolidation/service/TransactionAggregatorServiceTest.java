package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class TransactionAggregatorServiceTest {

    public static final String EXCHANGE_1 = "exchange1";
    public static final String CUR_1_1 = "cur1";
    public static final String CUR_1_2 = "cur2";
    public static final String TRADE_DATE_1_1 = "2017-05-31 23:57:24";
    public static final String TRADE_DATE_1_2 = "2017-05-31 23:40:52";


    @InjectMocks
    private TransactionAggregatorService transactionAggregatorService;

    private CointrackingTransaction cointrackingTransaction1_1;
    private CointrackingTransaction cointrackingTransaction1_2;
    private String key1;
    private CointrackingTransaction cointrackingTransaction2_1;
    private String key2;



    @BeforeEach
    public void setup(){
        cointrackingTransaction1_1 = new CointrackingTransaction();
        cointrackingTransaction1_1.setExchange(EXCHANGE_1);
        cointrackingTransaction1_1.setBuyamount("1");
        cointrackingTransaction1_1.setBuycur(CUR_1_1);
        cointrackingTransaction1_1.setSellcur(CUR_1_2);
        cointrackingTransaction1_1.setTradedate(TRADE_DATE_1_1);
        cointrackingTransaction1_2 = new CointrackingTransaction();
        cointrackingTransaction1_2.setExchange(EXCHANGE_1);
        cointrackingTransaction1_2.setBuyamount("1");
        cointrackingTransaction1_2.setBuycur(CUR_1_1);
        cointrackingTransaction1_2.setSellcur(CUR_1_2);
        cointrackingTransaction1_2.setTradedate(TRADE_DATE_1_2);
        key1 = cointrackingTransaction1_1.getKey();

//        cointrackingTransaction2_1 = new CointrackingTransaction();
//        cointrackingTransaction2_1.setExchange(EXCHANGE_1);
//        cointrackingTransaction2_1.setBuyamount("1");
//        cointrackingTransaction2_1.setBuycur(CUR_1_1);
//        cointrackingTransaction2_1.setSellcur(CUR_1_2);
//        cointrackingTransaction2_1.setTradedate(TRADE_DATE_1_1);
//        key2 = cointrackingTransaction2_1.getKey();
    }

    @Test
    public void aggregate_returnsPassedInTransaction(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);


        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
    }

    @Test
    public void aggregate_sumsBuyAmountFromExistingTransaction(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);
        transactionAggregatorService.aggregate(cointrackingTransaction1_2);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertEquals("2", aggregatedCointrackingTransactions.get(key1).getBuyamount());
    }

    @Test
    public void init_createsEmptyAggregatedCointrackingTransactions(){
        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);
        transactionAggregatorService.aggregate(cointrackingTransaction1_2);

        transactionAggregatorService.init();

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertTrue(aggregatedCointrackingTransactions.isEmpty());
    }

    @Test
    public void aggregate_groupsByExchangeBuyCurSelCurTradeDate(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);
        transactionAggregatorService.aggregate(cointrackingTransaction1_2);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertTrue(aggregatedCointrackingTransactions.containsKey(key1));
        assertEquals("2", aggregatedCointrackingTransactions.get(key1).getBuyamount());
    }

    @Test
    public void aggregate_groupsByTradeDateDay(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);
        transactionAggregatorService.aggregate(cointrackingTransaction1_2);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertTrue(aggregatedCointrackingTransactions.containsKey(key1));
        assertEquals("2", aggregatedCointrackingTransactions.get(key1).getBuyamount());
    }
}
