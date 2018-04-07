package com.cryptocurrencyservices.cointrackingconsolidation.service;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TransactionAggregatorService {

    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    private Map<String, CointrackingTransaction> aggregatedCointrackingTransactions;

    public void aggregate(CointrackingTransaction cointrackingTransaction) {

        String key = cointrackingTransaction.getKey();

        CointrackingTransaction retainedCointrackingTransaction = aggregatedCointrackingTransactions.get(key);

        if(retainedCointrackingTransaction != null){
//            retainedCointrackingTransaction = aggregatedCointrackingTransactions.get(key);
            BigDecimal newValue = new BigDecimal(cointrackingTransaction.getBuyamount())
                    .add(new BigDecimal(retainedCointrackingTransaction.getBuyamount()));

            retainedCointrackingTransaction.setBuyamount(newValue.toString());
        }
        else{
            retainedCointrackingTransaction = new CointrackingTransaction(cointrackingTransaction);
            aggregatedCointrackingTransactions.put(key, retainedCointrackingTransaction);
        }



    }

    public void init() {
        aggregatedCointrackingTransactions = new LinkedHashMap<>();
    }

    public Map<String, CointrackingTransaction> getaggregatedCointrackingTransactions(){
        return aggregatedCointrackingTransactions;
    }
}
