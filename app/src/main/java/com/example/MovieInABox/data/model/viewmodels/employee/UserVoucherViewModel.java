package com.example.MovieInABox.data.model.viewmodels.employee;

import java.sql.Date;

public class UserVoucherViewModel {
    private Integer uservoucherid;
    private String userid;
    private Integer voucherid;
    private Integer quantity;
    private String description;
    private Double discountPercentage;
    private Date expiredate;
    private String voucherStatus;
    private boolean isUsed;

    public UserVoucherViewModel() {
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public UserVoucherViewModel(Integer uservoucherid, String userid, Integer voucherid, Integer quantity, String description, Double discountPercentage, Date expiredate, String voucherStatus) {
        this.uservoucherid = uservoucherid;
        this.userid = userid;
        this.voucherid = voucherid;
        this.quantity = quantity;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.expiredate = expiredate;
        this.voucherStatus = voucherStatus;
    }

    public Integer getUservoucherid() {
        return uservoucherid;
    }

    public void setUservoucherid(Integer uservoucherid) {
        this.uservoucherid = uservoucherid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(Integer voucherid) {
        this.voucherid = voucherid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }
}
