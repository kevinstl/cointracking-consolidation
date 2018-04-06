package com.cryptocurrencyservices.cointrackingconsolidation.domain;


public class PoloniexTransaction {

    private String date;
    private String market;
    private String category;
    private String type;
    private String price;
    private String amount;
    private String total;
    private String fee;
    private String orderNumber;
    private String baseTotalLessFee;
    private String quoteTotalLessFee;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBaseTotalLessFee() {
        return baseTotalLessFee;
    }

    public void setBaseTotalLessFee(String baseTotalLessFee) {
        this.baseTotalLessFee = baseTotalLessFee;
    }

    public String getQuoteTotalLessFee() {
        return quoteTotalLessFee;
    }

    public void setQuoteTotalLessFee(String quoteTotalLessFee) {
        this.quoteTotalLessFee = quoteTotalLessFee;
    }
}
