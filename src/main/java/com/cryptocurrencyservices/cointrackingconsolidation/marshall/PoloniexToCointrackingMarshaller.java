package com.cryptocurrencyservices.cointrackingconsolidation.marshall;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.PoloniexTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PoloniexToCointrackingMarshaller {


    public static final String POLONIEX = "Poloniex";
    public static final String TRADE = "Trade";
    public static final String BUY = "Buy";
    public static final String SELL = "Sell";

    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public CointrackingTransaction marshall(PoloniexTransaction poloniexTransaction) {
        CointrackingTransaction cointrackingTransaction = new CointrackingTransaction();



        String tradeCurrency = poloniexTransaction.getMarket()
                .substring(0, poloniexTransaction.getMarket().indexOf("/"));
        String baseCurrency = poloniexTransaction.getMarket()
                .substring(poloniexTransaction.getMarket().indexOf("/") + 1);



        cointrackingTransaction.setExchange(POLONIEX);

        cointrackingTransaction.setType(TRADE);

        cointrackingTransaction.setTradedate(poloniexTransaction.getDate());

        cointrackingTransaction.setGroup("");
        cointrackingTransaction.setComment("");

        if(StringUtils.equals(BUY, poloniexTransaction.getType())){
            BigDecimal amount = new BigDecimal(poloniexTransaction.getAmount()).setScale(8, roundingMode);
            BigDecimal quoteTotalLessFee = new BigDecimal(poloniexTransaction.getQuoteTotalLessFee()).setScale(8, roundingMode);

            cointrackingTransaction.setBuyamount(poloniexTransaction.getQuoteTotalLessFee().replace("-", ""));
            cointrackingTransaction.setSellamount(poloniexTransaction.getBaseTotalLessFee().replace("-", ""));

            cointrackingTransaction.setBuycur(tradeCurrency);
            cointrackingTransaction.setSellcur(baseCurrency);

            BigDecimal buyFee = amount.subtract(quoteTotalLessFee);
            cointrackingTransaction.setFeeamount(buyFee.toString());

            cointrackingTransaction.setFeecur(tradeCurrency);

        }
        else if(StringUtils.equals(SELL, poloniexTransaction.getType())){
            BigDecimal total = new BigDecimal(poloniexTransaction.getTotal()).setScale(8, roundingMode);
            BigDecimal baseTotalLessFee = new BigDecimal(poloniexTransaction.getBaseTotalLessFee()).setScale(8, roundingMode);

            cointrackingTransaction.setBuyamount(poloniexTransaction.getBaseTotalLessFee().replace("-", ""));
            cointrackingTransaction.setSellamount(poloniexTransaction.getQuoteTotalLessFee().replace("-", ""));

            cointrackingTransaction.setBuycur(baseCurrency);
            cointrackingTransaction.setSellcur(tradeCurrency);


            BigDecimal sellFee = total.subtract(baseTotalLessFee);
            cointrackingTransaction.setFeeamount(sellFee.toString());

            cointrackingTransaction.setFeecur(baseCurrency);
        }

        return cointrackingTransaction;
    }
}
