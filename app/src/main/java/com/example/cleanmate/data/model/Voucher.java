package com.example.cleanmate.data.model;

public class Voucher {

    private Integer voucherid;
    private String description;
    private java.math.BigDecimal discountPercentage;
    private java.sql.Timestamp createdat;
    private String expiredate;
    private String vouchercode;
    private Boolean iseventvoucher;
    private Object createdby;
    private String status;

    public Integer getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(Integer voucherid) {
        this.voucherid = voucherid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.math.BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(java.math.BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public java.sql.Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(java.sql.Timestamp createdat) {
        this.createdat = createdat;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    public String getVouchercode() {
        return vouchercode;
    }

    public void setVouchercode(String vouchercode) {
        this.vouchercode = vouchercode;
    }

    public Boolean getIseventvoucher() {
        return iseventvoucher;
    }

    public void setIseventvoucher(Boolean iseventvoucher) {
        this.iseventvoucher = iseventvoucher;
    }

    public Object getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Object createdby) {
        this.createdby = createdby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
