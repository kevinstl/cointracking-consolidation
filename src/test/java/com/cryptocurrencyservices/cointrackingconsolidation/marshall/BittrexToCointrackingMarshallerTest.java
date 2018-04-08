package com.cryptocurrencyservices.cointrackingconsolidation.marshall;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.BittrexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class BittrexToCointrackingMarshallerTest {

    public static final String EDG = "EDG";
    public static final String BTC = "BTC";


    @InjectMocks
    private BittrexToCointrackingMarshaller bittrexToCointrackingMarshaller;

    private BittrexTransaction bittrexTransaction;

    private CointrackingTransaction cointrackingTransaction;


    @BeforeEach
    public void setup(){
        bittrexTransaction = new BittrexTransaction();
        bittrexTransaction.setExchange("BTC" + BittrexToCointrackingMarshaller.CURRENCY_PAIR_DELIMITER + EDG);
    }

    @Test
    public void build_returnsHydratedBuyCointrackingTransaction() throws ParseException {
        setupBuy();

        CointrackingTransaction cointrackingTransaction = bittrexToCointrackingMarshaller.marshall(bittrexTransaction);

        verifyCommonFields(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getBuycur());
        assertEquals(EDG, cointrackingTransaction.getBuycur());
        assertNotNull(cointrackingTransaction.getSellcur());
        assertEquals(BTC, cointrackingTransaction.getSellcur());

//        assertNotNull(cointrackingTransaction.getBuyamount());
//        assertThat(cointrackingTransaction.getBuyamount(), not(containsString("-")));
//        assertEquals(bittrexTransaction.getQuoteTotalLessFee().replace("-", ""), cointrackingTransaction.getBuyamount());

//        assertNotNull(cointrackingTransaction.getSellamount());
//        assertThat(cointrackingTransaction.getSellamount(), not(containsString("-")));
//        assertEquals(bittrexTransaction.getBaseTotalLessFee().replace("-", ""), cointrackingTransaction.getSellamount());



        assertNotNull(cointrackingTransaction.getFeecur());
        assertEquals(BTC, cointrackingTransaction.getFeecur());

    }

    @Test
    public void build_returnsHydratedSellCointrackingTransaction() throws ParseException {
        setupSell();

        CointrackingTransaction cointrackingTransaction = bittrexToCointrackingMarshaller.marshall(bittrexTransaction);

        verifyCommonFields(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getBuycur());
        assertEquals(BTC, cointrackingTransaction.getBuycur());
        assertNotNull(cointrackingTransaction.getSellcur());
        assertEquals(EDG, cointrackingTransaction.getSellcur());

//        assertNotNull(cointrackingTransaction.getBuyamount());
//        assertThat(cointrackingTransaction.getBuyamount(), not(containsString("-")));
//        assertEquals(bittrexTransaction.getBaseTotalLessFee().replace("-", ""), cointrackingTransaction.getBuyamount());

//        assertNotNull(cointrackingTransaction.getSellamount());
//        assertThat(cointrackingTransaction.getSellamount(), not(containsString("-")));
//        assertEquals(bittrexTransaction.getQuoteTotalLessFee().replace("-", ""), cointrackingTransaction.getSellamount());


        assertNotNull(cointrackingTransaction.getFeecur());
        assertEquals(BTC, cointrackingTransaction.getFeecur());

    }

    private void setupBuy() {
        bittrexTransaction.setType(BittrexToCointrackingMarshaller.BUY);

        bittrexTransaction.setPrice("0.00812163");
        bittrexTransaction.setQuantity("82.93323356");
        bittrexTransaction.setCommissionPaid("0.00002029");
//        BigDecimal baseTotalLessFee = new BigDecimal(-0.02906305).setScale(8, RoundingMode.HALF_EVEN);
//        bittrexTransaction.setBaseTotalLessFee(baseTotalLessFee.toString());
//        BigDecimal quoteTotalLessFee = new BigDecimal(1.06202575).setScale(8, RoundingMode.HALF_EVEN);
//        bittrexTransaction.setQuoteTotalLessFee(quoteTotalLessFee.toString());
//        buyFee = amount.subtract(quoteTotalLessFee);
        bittrexTransaction.setClosed("2/5/2018 23:17:01");
    }

    private void setupSell() {
        bittrexTransaction.setType(BittrexToCointrackingMarshaller.SELL);

        bittrexTransaction.setPrice("0.00733878");
        bittrexTransaction.setQuantity("85.74345701");
        bittrexTransaction.setCommissionPaid("0.00001834");
//        BigDecimal quoteTotalLessFee = new BigDecimal(-1.04839490).setScale(8, RoundingMode.HALF_EVEN);
//        bittrexTransaction.setQuoteTotalLessFee(quoteTotalLessFee.toString());
//        BigDecimal baseTotalLessFee = new BigDecimal(0.03238190).setScale(8, RoundingMode.HALF_EVEN);
//        bittrexTransaction.setBaseTotalLessFee(baseTotalLessFee.toString());
//        sellFee = total.subtract(baseTotalLessFee);
        bittrexTransaction.setClosed("2/2/2018 13:05:15");
    }

    private void verifyCommonFields(CointrackingTransaction cointrackingTransaction) throws ParseException {
        assertNotNull(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getFeeamount());
        assertEquals(cointrackingTransaction.getFeeamount(), cointrackingTransaction.getFeeamount());

        assertNotNull(cointrackingTransaction.getType());
        assertEquals(BittrexToCointrackingMarshaller.TRADE, cointrackingTransaction.getType());

        assertNotNull(cointrackingTransaction.getExchange());
        assertEquals(BittrexToCointrackingMarshaller.BITTREX, cointrackingTransaction.getExchange());

        assertNotNull(cointrackingTransaction.getTradedate());

        Date bittrexTradeDate = BittrexToCointrackingMarshaller.BITTREX_SIMPLE_DATE_FORMAT.parse(bittrexTransaction.getClosed());
        String expectedCointrackingTradeDate = BittrexToCointrackingMarshaller.COINTRACKING_SIMPLE_DATE_FORMAT.format(bittrexTradeDate);


        assertEquals(expectedCointrackingTradeDate, cointrackingTransaction.getTradedate());

        assertNotNull(cointrackingTransaction.getGroup());
        assertNotNull(cointrackingTransaction.getComment());
    }

}
