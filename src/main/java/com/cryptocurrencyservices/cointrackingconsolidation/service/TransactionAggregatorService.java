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

    private Map<String, CointrackingTransaction> aggregatedCointrackingTransactions = new LinkedHashMap<>();

    public void aggregate(CointrackingTransaction cointrackingTransaction) {

        CointrackingTransaction retainedCointrackingTransaction = null;

        if(!aggregatedCointrackingTransactions.isEmpty()){
            retainedCointrackingTransaction = aggregatedCointrackingTransactions.get("1");
            BigDecimal newValue = new BigDecimal(cointrackingTransaction.getBuyamount())
                    .add(new BigDecimal(retainedCointrackingTransaction.getBuyamount()));

            retainedCointrackingTransaction.setBuyamount(newValue.toString());
        }
        else{
            retainedCointrackingTransaction = new CointrackingTransaction(cointrackingTransaction);
            aggregatedCointrackingTransactions.put("1", retainedCointrackingTransaction);
        }



    }

    public void init() {
        aggregatedCointrackingTransactions = new LinkedHashMap<>();
    }

    public Map<String, CointrackingTransaction> getaggregatedCointrackingTransactions(){
        return aggregatedCointrackingTransactions;
    }
}
