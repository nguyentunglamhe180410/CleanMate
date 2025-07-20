package com.example.cleanmate.data.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {

    private Integer paymentid;
    private Integer bookingid;
    private java.math.BigDecimal amount;
    private String paymentmethod;
    private String paymentStatus;
    private String transactionid;
    private java.sql.Timestamp createdat;

    public Payment() {
    }

    public Payment(Integer paymentid, Integer bookingid, BigDecimal amount, String paymentmethod, String paymentStatus, String transactionid, Timestamp createdat) {
        this.paymentid = paymentid;
        this.bookingid = bookingid;
        this.amount = amount;
        this.paymentmethod = paymentmethod;
        this.paymentStatus = paymentStatus;
        this.transactionid = transactionid;
        this.createdat = createdat;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }
}
