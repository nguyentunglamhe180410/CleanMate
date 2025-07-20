package com.example.cleanmate.data.model;

public class WalletTransaction {

    private Integer transactionid;
    private Integer walletid;
    private java.math.BigDecimal amount;
    private String transactiontype;
    private String description;
    private String createdat;
    private Integer relatedbookingid;

    public Integer getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
    }

    public Integer getWalletid() {
        return walletid;
    }

    public void setWalletid(Integer walletid) {
        this.walletid = walletid;
    }

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public Integer getRelatedbookingid() {
        return relatedbookingid;
    }

    public void setRelatedbookingid(Integer relatedbookingid) {
        this.relatedbookingid = relatedbookingid;
    }

}
