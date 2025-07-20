package com.example.cleanmate.data.model.viewmodels.wallet;

import java.math.BigDecimal;

public class TransactionViewModel {
    private int transactionId;
    private int walletId;
    private BigDecimal amount;
    private String transactionType;
    private String description;
    private int month;
    private int date;
    private Integer relatedBookingId;  // nullable

    public TransactionViewModel() {}

    public TransactionViewModel(int transactionId, int walletId, BigDecimal amount,
                                String transactionType, String description,
                                int month, int date, Integer relatedBookingId) {
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.month = month;
        this.date = date;
        this.relatedBookingId = relatedBookingId;
    }

    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getWalletId() {
        return walletId;
    }
    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
    }

    public Integer getRelatedBookingId() {
        return relatedBookingId;
    }
    public void setRelatedBookingId(Integer relatedBookingId) {
        this.relatedBookingId = relatedBookingId;
    }
}

