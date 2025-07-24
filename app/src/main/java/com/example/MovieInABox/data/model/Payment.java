package com.example.MovieInABox.data.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {

    private Integer paymentId;
    private Integer bookingId;
    private java.math.BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
    private java.sql.Timestamp createdAt;

    public Payment() {
    }

    public Payment(Integer paymentid, Integer bookingid, BigDecimal amount, String paymentmethod, String paymentStatus, String transactionid, Timestamp createdat) {
        this.paymentId = paymentid;
        this.bookingId = bookingid;
        this.amount = amount;
        this.paymentMethod = paymentmethod;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionid;
        this.createdAt = createdat;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
