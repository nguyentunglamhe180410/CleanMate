package com.example.MovieInABox.data.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Voucher {

    private Integer voucherId;
    private String description;
    private java.math.BigDecimal discountPercentage;
    private java.sql.Timestamp createdAt;
    private String expireDate;
    private String voucherCode;
    private Boolean isEventVoucher;
    private String createdBy;
    private String status;

    public Voucher() {
    }

    public Voucher(Integer voucherid, String description, BigDecimal discountPercentage, Timestamp createdat, String expiredate, String vouchercode, Boolean iseventvoucher, String createdby, String status) {
        this.voucherId = voucherid;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.createdAt = createdat;
        this.expireDate = expiredate;
        this.voucherCode = vouchercode;
        this.isEventVoucher = iseventvoucher;
        this.createdBy = createdby;
        this.status = status;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Boolean getIsEventVoucher() {
        return isEventVoucher;
    }

    public void setIsEventVoucher(Boolean isEventVoucher) {
        this.isEventVoucher = isEventVoucher;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
