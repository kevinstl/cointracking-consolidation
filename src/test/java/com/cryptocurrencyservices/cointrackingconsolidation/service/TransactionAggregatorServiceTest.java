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
    public static final String EXCHANGE_2 = "exchange2";
    public static final String CUR_1_1 = "cur1";
    public static final String CUR_1_2 = "cur2";
    public static final String TRADE_DATE_1_1 = "2017-05-31 23:57:24";
    public static final String TRADE_DATE_1_2 = "2017-05-31 23:40:52";
    public static final String TRADE_DATE_2_1 = "2017-06-31 23:40:52";


    @InjectMocks
    private TransactionAggregatorService transactionAggregatorService;

    private CointrackingTransaction cointrackingTransaction1_1;
    private CointrackingTransaction cointrackingTransaction1_2;
    private String key1;
    private CointrackingTransaction cointrackingTransaction2_1;
    private CointrackingTransaction cointrackingTransaction2_2;
    private CointrackingTransaction cointrackingTransaction2_3;
    private String key2;
    private CointrackingTransaction cointrackingTransaction3_1;
    private CointrackingTransaction cointrackingTransaction3_2;
    private String key3;
    private CointrackingTransaction cointrackingTransaction4_1;
    private String key4;



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


        cointrackingTransaction2_1 = new CointrackingTransaction();
        cointrackingTransaction2_1.setExchange(EXCHANGE_1);
        cointrackingTransaction2_1.setBuyamount("1");
        cointrackingTransaction2_1.setBuycur(CUR_1_1);
        cointrackingTransaction2_1.setSellcur(CUR_1_2);
        cointrackingTransaction2_1.setTradedate(TRADE_DATE_2_1);

        cointrackingTransaction2_2 = new CointrackingTransaction();
        cointrackingTransaction2_2.setExchange(EXCHANGE_1);
        cointrackingTransaction2_2.setBuyamount("1");
        cointrackingTransaction2_2.setBuycur(CUR_1_1);
        cointrackingTransaction2_2.setSellcur(CUR_1_2);
        cointrackingTransaction2_2.setTradedate(TRADE_DATE_2_1);

        cointrackingTransaction2_3 = new CointrackingTransaction();
        cointrackingTransaction2_3.setExchange(EXCHANGE_1);
        cointrackingTransaction2_3.setBuyamount("1");
        cointrackingTransaction2_3.setBuycur(CUR_1_1);
        cointrackingTransaction2_3.setSellcur(CUR_1_2);
        cointrackingTransaction2_3.setTradedate(TRADE_DATE_2_1);
        key2 = cointrackingTransaction2_3.getKey();


        cointrackingTransaction3_1 = new CointrackingTransaction();
        cointrackingTransaction3_1.setExchange(EXCHANGE_1);
        cointrackingTransaction3_1.setBuyamount("1");
        cointrackingTransaction3_1.setBuycur(CUR_1_2);
        cointrackingTransaction3_1.setSellcur(CUR_1_1);
        cointrackingTransaction3_1.setTradedate(TRADE_DATE_1_1);

        cointrackingTransaction3_2 = new CointrackingTransaction();
        cointrackingTransaction3_2.setExchange(EXCHANGE_1);
        cointrackingTransaction3_2.setBuyamount("1");
        cointrackingTransaction3_2.setBuycur(CUR_1_2);
        cointrackingTransaction3_2.setSellcur(CUR_1_1);
        cointrackingTransaction3_2.setTradedate(TRADE_DATE_1_2);
        key3 = cointrackingTransaction3_1.getKey();


        cointrackingTransaction4_1 = new CointrackingTransaction();
        cointrackingTransaction4_1.setExchange(EXCHANGE_2);
        cointrackingTransaction4_1.setBuyamount("1");
        cointrackingTransaction4_1.setBuycur(CUR_1_2);
        cointrackingTransaction4_1.setSellcur(CUR_1_1);
        cointrackingTransaction4_1.setTradedate(TRADE_DATE_1_2);
        key4 = cointrackingTransaction4_1.getKey();
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
        transactionAggregatorService.aggregate(cointrackingTransaction2_1);
        transactionAggregatorService.aggregate(cointrackingTransaction2_2);
        transactionAggregatorService.aggregate(cointrackingTransaction2_3);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertTrue(aggregatedCointrackingTransactions.containsKey(key1));
        assertEquals("2", aggregatedCointrackingTransactions.get(key1).getBuyamount());
        assertTrue(aggregatedCointrackingTransactions.containsKey(key2));
        assertEquals("3", aggregatedCointrackingTransactions.get(key2).getBuyamount());
    }

    @Test
    public void aggregate_groupsByBuyCurencySellCurrency(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);
        transactionAggregatorService.aggregate(cointrackingTransaction1_2);
        transactionAggregatorService.aggregate(cointrackingTransaction2_1);
        transactionAggregatorService.aggregate(cointrackingTransaction2_2);
        transactionAggregatorService.aggregate(cointrackingTransaction2_3);
        transactionAggregatorService.aggregate(cointrackingTransaction3_1);
        transactionAggregatorService.aggregate(cointrackingTransaction3_2);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertTrue(aggregatedCointrackingTransactions.containsKey(key1));
        assertEquals("2", aggregatedCointrackingTransactions.get(key1).getBuyamount());
        assertTrue(aggregatedCointrackingTransactions.containsKey(key2));
        assertEquals("3", aggregatedCointrackingTransactions.get(key2).getBuyamount());
        assertTrue(aggregatedCointrackingTransactions.containsKey(key3));
        assertEquals("2", aggregatedCointrackingTransactions.get(key3).getBuyamount());
    }

    @Test
    public void aggregate_groupsByExchange(){

        transactionAggregatorService.init();

        transactionAggregatorService.aggregate(cointrackingTransaction1_1);
        transactionAggregatorService.aggregate(cointrackingTransaction1_2);
        transactionAggregatorService.aggregate(cointrackingTransaction2_1);
        transactionAggregatorService.aggregate(cointrackingTransaction2_2);
        transactionAggregatorService.aggregate(cointrackingTransaction2_3);
        transactionAggregatorService.aggregate(cointrackingTransaction3_1);
        transactionAggregatorService.aggregate(cointrackingTransaction3_2);
        transactionAggregatorService.aggregate(cointrackingTransaction4_1);

        Map<String, CointrackingTransaction> aggregatedCointrackingTransactions =
                transactionAggregatorService.getaggregatedCointrackingTransactions();

        assertNotNull(aggregatedCointrackingTransactions);
        assertTrue(aggregatedCointrackingTransactions.containsKey(key1));
        assertEquals("2", aggregatedCointrackingTransactions.get(key1).getBuyamount());
        assertTrue(aggregatedCointrackingTransactions.containsKey(key2));
        assertEquals("3", aggregatedCointrackingTransactions.get(key2).getBuyamount());
        assertTrue(aggregatedCointrackingTransactions.containsKey(key3));
        assertEquals("2", aggregatedCointrackingTransactions.get(key3).getBuyamount());
        assertTrue(aggregatedCointrackingTransactions.containsKey(key4));
        assertEquals("1", aggregatedCointrackingTransactions.get(key4).getBuyamount());
    }
}
