package com.example.MovieInABox.data.model;

import java.math.BigDecimal;

public class WithdrawRequest {

    private Integer requestId;
    private String userId;
    private java.math.BigDecimal amount;
    private String status;
    private String requestedAt;
    private String processedAt;
    private String adminNote;
    private Integer transactionId;
    private String processedBy;

    public WithdrawRequest() {
    }

    public WithdrawRequest(Integer requestid, String userid, BigDecimal amount, String status, String requestedat, String processedat, String adminnote, Integer transactionid, String processedby) {
        this.requestId = requestid;
        this.userId = userid;
        this.amount = amount;
        this.status = status;
        this.requestedAt = requestedat;
        this.processedAt = processedat;
        this.adminNote = adminnote;
        this.transactionId = transactionid;
        this.processedBy = processedby;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(String processedAt) {
        this.processedAt = processedAt;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
}
