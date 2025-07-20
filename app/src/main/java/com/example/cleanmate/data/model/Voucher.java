package com.example.cleanmate.data.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Voucher {

    private Integer voucherid;
    private String description;
    private java.math.BigDecimal discountPercentage;
    private java.sql.Timestamp createdat;
    private String expiredate;
    private String vouchercode;
    private Boolean iseventvoucher;
    private String createdby;
    private String status;

    public Voucher() {
    }

    public Voucher(Integer voucherid, String description, BigDecimal discountPercentage, Timestamp createdat, String expiredate, String vouchercode, Boolean iseventvoucher, String createdby, String status) {
        this.voucherid = voucherid;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.createdat = createdat;
        this.expiredate = expiredate;
        this.vouchercode = vouchercode;
        this.iseventvoucher = iseventvoucher;
        this.createdby = createdby;
        this.status = status;
    }

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

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
