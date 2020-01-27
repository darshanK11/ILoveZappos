package com.example.ilovezappos.ui.order_book;

public class TableModal {

    private float bid_or_ask;
    private float amount;

    public TableModal(float bid_or_ask, float amount) {
        this.bid_or_ask = bid_or_ask;
        this.amount = amount;
    }

    public float getBid_or_ask() {
        return bid_or_ask;
    }

    public void setBid_or_ask(float bid_or_ask) {
        this.bid_or_ask = bid_or_ask;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getValue() {
        return bid_or_ask * amount;
    }
}
