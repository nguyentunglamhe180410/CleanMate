package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class Withdrawrequest {

    private Integer requestid;
    private String userid;
    private java.math.BigDecimal amount;
    private String status;
    private String requestedat;
    private String processedat;
    private String adminnote;
    private Integer transactionid;
    private String processedby;

    public Withdrawrequest() {
    }

    public Withdrawrequest(Integer requestid, String userid, BigDecimal amount, String status, String requestedat, String processedat, String adminnote, Integer transactionid, String processedby) {
        this.requestid = requestid;
        this.userid = userid;
        this.amount = amount;
        this.status = status;
        this.requestedat = requestedat;
        this.processedat = processedat;
        this.adminnote = adminnote;
        this.transactionid = transactionid;
        this.processedby = processedby;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getRequestedat() {
        return requestedat;
    }

    public void setRequestedat(String requestedat) {
        this.requestedat = requestedat;
    }

    public String getProcessedat() {
        return processedat;
    }

    public void setProcessedat(String processedat) {
        this.processedat = processedat;
    }

    public String getAdminnote() {
        return adminnote;
    }

    public void setAdminnote(String adminnote) {
        this.adminnote = adminnote;
    }

    public Integer getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
    }

    public String getProcessedby() {
        return processedby;
    }

    public void setProcessedby(String processedby) {
        this.processedby = processedby;
    }
}
