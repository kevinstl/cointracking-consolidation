package com.cryptocurrencyservices.cointrackingconsolidation.marshall;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.BittrexTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class BittrexToCointrackingMarshaller {


    public static final String BITTREX = "Bittrex";
    public static final String TRADE = "Trade";
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    public static final String CURRENCY_PAIR_DELIMITER = "-";
    public static final SimpleDateFormat BITTREX_SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static final SimpleDateFormat COINTRACKING_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public CointrackingTransaction marshall(BittrexTransaction bittrexTransaction) {
        CointrackingTransaction cointrackingTransaction = new CointrackingTransaction();



        String baseCurrency = bittrexTransaction.getExchange()
                .substring(0, bittrexTransaction.getExchange().indexOf(CURRENCY_PAIR_DELIMITER));
        String tradeCurrency = bittrexTransaction.getExchange()
                .substring(bittrexTransaction.getExchange().indexOf(CURRENCY_PAIR_DELIMITER) + 1);

        cointrackingTransaction.setExchange(BITTREX);

        cointrackingTransaction.setType(TRADE);


        Date bittrexTradeDate = null;
        try {
            bittrexTradeDate = BITTREX_SIMPLE_DATE_FORMAT.parse(bittrexTransaction.getClosed());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String cointrackingTradeDate = COINTRACKING_SIMPLE_DATE_FORMAT.format(bittrexTradeDate);
        cointrackingTransaction.setTradedate(cointrackingTradeDate);


        cointrackingTransaction.setGroup("");
        cointrackingTransaction.setComment("");

        BigDecimal fee = new BigDecimal(bittrexTransaction.getCommissionPaid()).setScale(8, roundingMode);
        cointrackingTransaction.setFeeamount(fee.toString());
        cointrackingTransaction.setFeecur(baseCurrency);

        if(StringUtils.contains(bittrexTransaction.getType(), BUY)){
            cointrackingTransaction.setBuyamount(bittrexTransaction.getQuantity().replace("-", ""));

            BigDecimal price = new BigDecimal(bittrexTransaction.getPrice()).setScale(8, roundingMode);
            BigDecimal sellAmount = price.add(fee).setScale(8, roundingMode);
            cointrackingTransaction.setSellamount(sellAmount.toString().replace("-", ""));

            cointrackingTransaction.setBuycur(tradeCurrency);
            cointrackingTransaction.setSellcur(baseCurrency);

//            BigDecimal buyFee = new BigDecimal(bittrexTransaction.getCommissionPaid()).setScale(8, roundingMode);
//            cointrackingTransaction.setFeeamount(buyFee.toString());
//
//            cointrackingTransaction.setFeecur(tradeCurrency);

        }
        else if(StringUtils.contains(bittrexTransaction.getType(), SELL)){
//            BigDecimal total = new BigDecimal(bittrexTransaction.getTotal()).setScale(8, roundingMode);
//            BigDecimal baseTotalLessFee = new BigDecimal(bittrexTransaction.getBaseTotalLessFee()).setScale(8, roundingMode);

            BigDecimal price = new BigDecimal(bittrexTransaction.getPrice()).setScale(8, roundingMode);
            BigDecimal buyAmount = price.subtract(fee).setScale(8, roundingMode);
            cointrackingTransaction.setBuyamount(buyAmount.toString().replace("-", ""));


            cointrackingTransaction.setSellamount(bittrexTransaction.getQuantity().replace("-", ""));

            cointrackingTransaction.setBuycur(baseCurrency);
            cointrackingTransaction.setSellcur(tradeCurrency);


//            BigDecimal sellFee = new BigDecimal(bittrexTransaction.getCommissionPaid()).setScale(8, roundingMode);
//            cointrackingTransaction.setFeeamount(sellFee.toString());
//
//            cointrackingTransaction.setFeecur(baseCurrency);
        }

        return cointrackingTransaction;
    }
}
