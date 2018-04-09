package com.cryptocurrencyservices.cointrackingconsolidation.marshall;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.BitfinexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.text.ParseException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BitfinexToCointrackingMarshallerTest {

    public static final String USD = "USD";
    public static final String BTC = "BTC";


    @InjectMocks
    private BitfinexToCointrackingMarshaller bitfinexToCointrackingMarshaller;

    private BitfinexTransaction bitfinexTransaction;

    private CointrackingTransaction cointrackingTransaction;


    @BeforeEach
    public void setup(){
        bitfinexTransaction = new BitfinexTransaction();
        bitfinexTransaction.setPair("BTC" + BitfinexToCointrackingMarshaller.CURRENCY_PAIR_DELIMITER + USD);
    }

    @Test
    public void build_returnsHydratedBuyCointrackingTransaction() throws ParseException {
        setupBuy();

        CointrackingTransaction cointrackingTransaction = bitfinexToCointrackingMarshaller.marshall(bitfinexTransaction);

        verifyCommonFields(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getBuycur());
        assertEquals(BTC, cointrackingTransaction.getBuycur());
        assertNotNull(cointrackingTransaction.getSellcur());
        assertEquals(USD, cointrackingTransaction.getSellcur());

//        assertNotNull(cointrackingTransaction.getBuyamount());
//        assertThat(cointrackingTransaction.getBuyamount(), not(containsString("-")));
//        assertEquals(bitfinexTransaction.getQuoteTotalLessFee().replace("-", ""), cointrackingTransaction.getBuyamount());

//        assertNotNull(cointrackingTransaction.getSellamount());
//        assertThat(cointrackingTransaction.getSellamount(), not(containsString("-")));
//        assertEquals(bitfinexTransaction.getBaseTotalLessFee().replace("-", ""), cointrackingTransaction.getSellamount());



        assertNotNull(cointrackingTransaction.getFeecur());
        assertEquals(BTC, cointrackingTransaction.getFeecur());

    }

    @Test
    public void build_returnsHydratedSellCointrackingTransaction() throws ParseException {
        setupSell();

        CointrackingTransaction cointrackingTransaction = bitfinexToCointrackingMarshaller.marshall(bitfinexTransaction);

        verifyCommonFields(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getBuycur());
        assertEquals(BTC, cointrackingTransaction.getBuycur());
        assertNotNull(cointrackingTransaction.getSellcur());
        assertEquals(USD, cointrackingTransaction.getSellcur());

//        assertNotNull(cointrackingTransaction.getBuyamount());
//        assertThat(cointrackingTransaction.getBuyamount(), not(containsString("-")));
//        assertEquals(bitfinexTransaction.getBaseTotalLessFee().replace("-", ""), cointrackingTransaction.getBuyamount());

//        assertNotNull(cointrackingTransaction.getSellamount());
//        assertThat(cointrackingTransaction.getSellamount(), not(containsString("-")));
//        assertEquals(bitfinexTransaction.getQuoteTotalLessFee().replace("-", ""), cointrackingTransaction.getSellamount());


        assertNotNull(cointrackingTransaction.getFeecur());
        assertEquals(BTC, cointrackingTransaction.getFeecur());

    }

    private void setupBuy() {

        bitfinexTransaction.setAmount("1.98447977");
        bitfinexTransaction.setPrice("439.0");
        bitfinexTransaction.setFee("-1.74237324");
        bitfinexTransaction.setDate("2015-11-04 05:56:47");
    }

    private void setupSell() {
        bitfinexTransaction.setPrice("-1.98447977");
        bitfinexTransaction.setAmount("447.9");
        bitfinexTransaction.setFee("-1.77769698");
        bitfinexTransaction.setDate("2015-11-04 05:49:06");
    }

    private void verifyCommonFields(CointrackingTransaction cointrackingTransaction) throws ParseException {
        assertNotNull(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getFeeamount());
        assertEquals(bitfinexTransaction.getFee(), cointrackingTransaction.getFeeamount());

        assertNotNull(cointrackingTransaction.getType());
        assertEquals(BitfinexToCointrackingMarshaller.TRADE, cointrackingTransaction.getType());

        assertNotNull(cointrackingTransaction.getExchange());
        assertEquals(BitfinexToCointrackingMarshaller.BITTREX, cointrackingTransaction.getExchange());

        assertNotNull(cointrackingTransaction.getTradedate());
        assertEquals(bitfinexTransaction.getDate(), cointrackingTransaction.getTradedate());

        assertNotNull(cointrackingTransaction.getGroup());
        assertNotNull(cointrackingTransaction.getComment());
    }

}
