package com.cryptocurrencyservices.cointrackingconsolidation.domain;


public class CointrackingTransaction {

    public CointrackingTransaction(){

    }

    public CointrackingTransaction(CointrackingTransaction cointrackingTransaction){
        this.type = cointrackingTransaction.type;
        this.buyamount = cointrackingTransaction.buyamount;
        this.buycur = cointrackingTransaction.buycur;
        this.sellamount = cointrackingTransaction.sellamount;
        this.sellcur = cointrackingTransaction.sellcur;
        this.feeamount = cointrackingTransaction.feeamount;
        this.feecur = cointrackingTransaction.feecur;
        this.exchange = cointrackingTransaction.exchange;
        this.group = cointrackingTransaction.group;
        this.comment = cointrackingTransaction.comment;
        this.tradedate = cointrackingTransaction.tradedate;
    }



    private String type;
    private String buyamount;
    private String buycur;
    private String sellamount;
    private String sellcur;
    private String feeamount;
    private String feecur;
    private String exchange;
    private String group;
    private String comment;
    private String tradedate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyamount() {
        return buyamount;
    }

    public void setBuyamount(String buyamount) {
        this.buyamount = buyamount;
    }

    public String getBuycur() {
        return buycur;
    }

    public void setBuycur(String buycur) {
        this.buycur = buycur;
    }

    public String getSellamount() {
        return sellamount;
    }

    public void setSellamount(String sellamount) {
        this.sellamount = sellamount;
    }

    public String getSellcur() {
        return sellcur;
    }

    public void setSellcur(String sellcur) {
        this.sellcur = sellcur;
    }

    public String getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(String feeamount) {
        this.feeamount = feeamount;
    }

    public String getFeecur() {
        return feecur;
    }

    public void setFeecur(String feecur) {
        this.feecur = feecur;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }

    public String getKey(){
        return getExchange() + "_"
                + getBuycur()  + "_"
                + getSellcur()  + "_"
//                + getTradedate().substring(0); // By millisecond
                + getTradedate().substring(0, 10); // By Day
//                + getTradedate().substring(0, 9);    // By Day Teen Digit
//                + getTradedate().substring(0, 7);    // By Month
    }

    //    @Override
//    public String toString() {
//        return new ToStringBuilder(this)
//                .append("type", type)
//                .append("buyAmount", buyAmount)
//                .append("buyCur", buyCur)
//                .append("sellAmount", sellAmount)
//                .append("sellCur", sellCur)
//                .append("feeAmount", feeAmount)
//                .append("feeCur", feeCur)
//                .append("tradeDate", tradeDate)
//                .append("exchange", exchange)
//                .append("group", group)
//                .append("comment", comment).toString();
//    }


}
