package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PoloniexToCointrackingTransactionFactory {


    public static final String POLONIEX = "Poloniex";
    public static final String TRADE = "Trade";
    public static final String BUY = "Buy";
    public static final String SELL = "Sell";

    public CointrackingTransaction build(PoloniexTransaction poloniexTransaction) {
        CointrackingTransaction cointrackingTransaction = new CointrackingTransaction();

        cointrackingTransaction.setBuyamount(poloniexTransaction.getAmount());
        String tradeCurrency = poloniexTransaction.getMarket()
                .substring(0, poloniexTransaction.getMarket().indexOf("/"));
        String baseCurrency = poloniexTransaction.getMarket()
                .substring(poloniexTransaction.getMarket().indexOf("/") + 1);

        cointrackingTransaction.setSellamount(poloniexTransaction.getAmount());

        cointrackingTransaction.setExchange(POLONIEX);






        cointrackingTransaction.setType(TRADE);

        cointrackingTransaction.setTradedate(poloniexTransaction.getDate());

        if(StringUtils.equals(BUY, poloniexTransaction.getType())){
            cointrackingTransaction.setBuycur(tradeCurrency);
            cointrackingTransaction.setSellcur(baseCurrency);

            BigDecimal amount = new BigDecimal(poloniexTransaction.getAmount()).setScale(8, RoundingMode.HALF_EVEN);
            BigDecimal quoteTotalLessFee = new BigDecimal(poloniexTransaction.getQuoteTotalLessFee()).setScale(8, RoundingMode.HALF_EVEN);

            BigDecimal buyFee = amount.subtract(quoteTotalLessFee);
            cointrackingTransaction.setFeeamount(buyFee.toString());

            cointrackingTransaction.setFeecur(tradeCurrency);

        }
        else if(StringUtils.equals(SELL, poloniexTransaction.getType())){
            cointrackingTransaction.setBuycur(baseCurrency);
            cointrackingTransaction.setSellcur(tradeCurrency);

            BigDecimal total = new BigDecimal(poloniexTransaction.getTotal()).setScale(8, RoundingMode.HALF_EVEN);
            BigDecimal baseTotalLessFee = new BigDecimal(poloniexTransaction.getBaseTotalLessFee()).setScale(8, RoundingMode.HALF_EVEN);

            BigDecimal sellFee = total.subtract(baseTotalLessFee);
            cointrackingTransaction.setFeeamount(sellFee.toString());

            cointrackingTransaction.setFeecur(baseCurrency);
        }

        return cointrackingTransaction;
    }
}
