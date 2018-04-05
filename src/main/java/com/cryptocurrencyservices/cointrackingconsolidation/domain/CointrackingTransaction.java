package com.cryptocurrencyservices.cointrackingconsolidation.domain;

public class CointrackingTransaction {

    private String type;
    private Double buyamount;
    private String buycur;
    private Double sellamount;
    private String sellcur;
    private Double feeamount;
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

    public Double getBuyamount() {
        return buyamount;
    }

    public void setBuyamount(Double buyamount) {
        this.buyamount = buyamount;
    }

    public String getBuycur() {
        return buycur;
    }

    public void setBuycur(String buycur) {
        this.buycur = buycur;
    }

    public Double getSellamount() {
        return sellamount;
    }

    public void setSellamount(Double sellamount) {
        this.sellamount = sellamount;
    }

    public String getSellcur() {
        return sellcur;
    }

    public void setSellcur(String sellcur) {
        this.sellcur = sellcur;
    }

    public Double getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(Double feeamount) {
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
