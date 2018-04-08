package com.cryptocurrencyservices.cointrackingconsolidation.marshall;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class PoloniexToCointrackingMarshallerTest {

    public static final String OMNI = "EDG";
    public static final String BTC = "BTC";
    @InjectMocks
    private PoloniexToCointrackingMarshaller poloniexToCointrackingMarshaller;

    private PoloniexTransaction poloniexTransaction;

    private CointrackingTransaction cointrackingTransaction;

    private BigDecimal buyFee;
    private BigDecimal sellFee;

    @BeforeEach
    public void setup(){
        poloniexTransaction = new PoloniexTransaction();
        poloniexTransaction.setMarket(OMNI + "/BTC");
    }

    @Test
    public void build_returnsHydratedBuyCointrackingTransaction(){
        setupBuy();

        CointrackingTransaction cointrackingTransaction = poloniexToCointrackingMarshaller.marshall(poloniexTransaction);

        verifyCommonFields(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getBuycur());
        assertEquals(OMNI, cointrackingTransaction.getBuycur());
        assertNotNull(cointrackingTransaction.getSellcur());
        assertEquals(BTC, cointrackingTransaction.getSellcur());

        assertNotNull(cointrackingTransaction.getBuyamount());
        assertThat(cointrackingTransaction.getBuyamount(), not(containsString("-")));
        assertEquals(poloniexTransaction.getQuoteTotalLessFee().replace("-", ""), cointrackingTransaction.getBuyamount());

        assertNotNull(cointrackingTransaction.getSellamount());
        assertThat(cointrackingTransaction.getSellamount(), not(containsString("-")));
        assertEquals(poloniexTransaction.getBaseTotalLessFee().replace("-", ""), cointrackingTransaction.getSellamount());

        assertNotNull(cointrackingTransaction.getFeeamount());
        assertEquals(buyFee.toString(), cointrackingTransaction.getFeeamount());

        assertNotNull(cointrackingTransaction.getFeecur());
        assertEquals(OMNI, cointrackingTransaction.getFeecur());

    }

    @Test
    public void build_returnsHydratedSellCointrackingTransaction(){
        setupSell();

        CointrackingTransaction cointrackingTransaction = poloniexToCointrackingMarshaller.marshall(poloniexTransaction);

        verifyCommonFields(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getBuycur());
        assertEquals(BTC, cointrackingTransaction.getBuycur());
        assertNotNull(cointrackingTransaction.getSellcur());
        assertEquals(OMNI, cointrackingTransaction.getSellcur());

        assertNotNull(cointrackingTransaction.getBuyamount());
        assertThat(cointrackingTransaction.getBuyamount(), not(containsString("-")));
        assertEquals(poloniexTransaction.getBaseTotalLessFee().replace("-", ""), cointrackingTransaction.getBuyamount());

        assertNotNull(cointrackingTransaction.getSellamount());
        assertThat(cointrackingTransaction.getSellamount(), not(containsString("-")));
        assertEquals(poloniexTransaction.getQuoteTotalLessFee().replace("-", ""), cointrackingTransaction.getSellamount());

        assertNotNull(cointrackingTransaction.getFeeamount());
        assertEquals(sellFee.toString(), cointrackingTransaction.getFeeamount());

        assertNotNull(cointrackingTransaction.getFeecur());
        assertEquals(BTC, cointrackingTransaction.getFeecur());

    }

    private void setupBuy() {
        poloniexTransaction.setType(PoloniexToCointrackingMarshaller.BUY);

        poloniexTransaction.setPrice("0.02730000");
        BigDecimal amount = new BigDecimal(1.06458074).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setAmount(amount.toString());
        BigDecimal total = new BigDecimal(0.02906305).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setTotal(total.toString());
        poloniexTransaction.setFee("0.24%");
        BigDecimal baseTotalLessFee = new BigDecimal(-0.02906305).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setBaseTotalLessFee(baseTotalLessFee.toString());
        BigDecimal quoteTotalLessFee = new BigDecimal(1.06202575).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setQuoteTotalLessFee(quoteTotalLessFee.toString());
        buyFee = amount.subtract(quoteTotalLessFee);
        poloniexTransaction.setDate("2017-05-31 23:57:24");
    }

    private void setupSell() {
        poloniexTransaction.setType(PoloniexToCointrackingMarshaller.SELL);

        poloniexTransaction.setPrice("0.03093042");
        BigDecimal amount = new BigDecimal(1.04839490).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setAmount(amount.toString());
        BigDecimal total = new BigDecimal(0.03242729).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setTotal(total.toString());
        poloniexTransaction.setFee("0.14%");
        BigDecimal quoteTotalLessFee = new BigDecimal(-1.04839490).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setQuoteTotalLessFee(quoteTotalLessFee.toString());
        BigDecimal baseTotalLessFee = new BigDecimal(0.03238190).setScale(8, RoundingMode.HALF_EVEN);
        poloniexTransaction.setBaseTotalLessFee(baseTotalLessFee.toString());
        sellFee = total.subtract(baseTotalLessFee);
        poloniexTransaction.setDate("2017-05-31 23:57:24");
    }

    private void verifyCommonFields(CointrackingTransaction cointrackingTransaction) {
        assertNotNull(cointrackingTransaction);

        assertNotNull(cointrackingTransaction.getExchange());
        assertEquals(PoloniexToCointrackingMarshaller.POLONIEX, cointrackingTransaction.getExchange());

        assertNotNull(cointrackingTransaction.getType());
        assertEquals(PoloniexToCointrackingMarshaller.TRADE, cointrackingTransaction.getType());

        assertNotNull(cointrackingTransaction.getTradedate());
        assertEquals(poloniexTransaction.getDate(), cointrackingTransaction.getTradedate());

        assertNotNull(cointrackingTransaction.getGroup());
        assertNotNull(cointrackingTransaction.getComment());
    }

}
