package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class UserWallet {

    private Integer walletid;
    private String userid;
    private java.math.BigDecimal balance;
    private String updatedat;

    public UserWallet() {
    }

    public UserWallet(Integer walletid, String userid, BigDecimal balance, String updatedat) {
        this.walletid = walletid;
        this.userid = userid;
        this.balance = balance;
        this.updatedat = updatedat;
    }

    public Integer getWalletid() {
        return walletid;
    }

    public void setWalletid(Integer walletid) {
        this.walletid = walletid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }
}
