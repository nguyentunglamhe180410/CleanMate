package com.example.cleanmate.data.model;

public class Withdrawrequest {

    private Integer requestid;
    private String userid;
    private java.math.BigDecimal amount;
    private String status;
    private String requestedat;
    private String processedat;
    private Object adminnote;
    private Integer transactionid;
    private String processedby;

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

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
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

    public Object getAdminnote() {
        return adminnote;
    }

    public void setAdminnote(Object adminnote) {
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
