package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class UserWallet {

    private Integer walletId;
    private String userId;
    private java.math.BigDecimal balance;
    private String updatedAt;

    public UserWallet() {
    }

    public UserWallet(Integer walletid, String userid, BigDecimal balance, String updatedat) {
        this.walletId = walletid;
        this.userId = userid;
        this.balance = balance;
        this.updatedAt = updatedat;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
