package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class WalletTransaction {

    private Integer transactionId;
    private Integer walletId;
    private java.math.BigDecimal amount;
    private String transactionType;
    private String description;
    private String createdAt;
    private Integer relatedBookingId;

    public WalletTransaction() {
    }

    public WalletTransaction(Integer transactionid, Integer walletid, BigDecimal amount, String transactiontype, String description, String createdat, Integer relatedbookingid) {
        this.transactionId = transactionid;
        this.walletId = walletid;
        this.amount = amount;
        this.transactionType = transactiontype;
        this.description = description;
        this.createdAt = createdat;
        this.relatedBookingId = relatedbookingid;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRelatedBookingId() {
        return relatedBookingId;
    }

    public void setRelatedBookingId(Integer relatedBookingId) {
        this.relatedBookingId = relatedBookingId;
    }
}
