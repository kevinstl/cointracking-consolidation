package com.cryptocurrencyservices.cointrackingconsolidation.marshall;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.BitfinexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class BitfinexToCointrackingMarshaller {


    public static final String BITTREX = "Bitfinex";
    public static final String TRADE = "Trade";
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    public static final String CURRENCY_PAIR_DELIMITER = "/";

    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public CointrackingTransaction marshall(BitfinexTransaction bitfinexTransaction) {
        CointrackingTransaction cointrackingTransaction = new CointrackingTransaction();



        String baseCurrency = bitfinexTransaction.getPair()
                .substring(0, bitfinexTransaction.getPair().indexOf(CURRENCY_PAIR_DELIMITER));
        String tradeCurrency = bitfinexTransaction.getPair()
                .substring(bitfinexTransaction.getPair().indexOf(CURRENCY_PAIR_DELIMITER) + 1);

        cointrackingTransaction.setExchange(BITTREX);

        cointrackingTransaction.setType(TRADE);

        cointrackingTransaction.setTradedate(bitfinexTransaction.getDate());


        cointrackingTransaction.setGroup("");
        cointrackingTransaction.setComment("");

        BigDecimal fee = new BigDecimal(bitfinexTransaction.getFee().replace("-", "")).setScale(8, roundingMode);
        cointrackingTransaction.setFeeamount(fee.toString());
        cointrackingTransaction.setFeecur(tradeCurrency);

        BigDecimal amount = new BigDecimal(bitfinexTransaction.getAmount().replace("-", "")).setScale(8, roundingMode);
        BigDecimal price = new BigDecimal(bitfinexTransaction.getPrice()).setScale(8, roundingMode);
        BigDecimal tradeAmount = amount.multiply(price).setScale(8, roundingMode);

        if(Double.valueOf(bitfinexTransaction.getAmount()) > 0){
            cointrackingTransaction.setBuyamount(amount.toString());
            cointrackingTransaction.setSellamount(tradeAmount.add(fee).toString());
            cointrackingTransaction.setBuycur(baseCurrency);
            cointrackingTransaction.setSellcur(tradeCurrency);
        }
        else if(Double.valueOf(bitfinexTransaction.getAmount()) < 0){
            cointrackingTransaction.setBuyamount(tradeAmount.subtract(fee).toString());
            cointrackingTransaction.setSellamount(amount.toString());
            cointrackingTransaction.setBuycur(tradeCurrency);
            cointrackingTransaction.setSellcur(baseCurrency);
        }

        return cointrackingTransaction;
    }
}
