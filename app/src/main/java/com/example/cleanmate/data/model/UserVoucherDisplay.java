package com.example.cleanmate.data.model;

import java.sql.Timestamp;

public class UserVoucherDisplay {
    private Integer userVoucherId;
    private String userId;
    private Integer voucherId;
    private String voucherCode;
    private String description;
    private Double discountPercentage;
    private String expireDate;
    private Integer quantity;
    private Boolean isUsed;
    private Timestamp usedAt;
    private String status;

    public UserVoucherDisplay() {
    }

    public UserVoucherDisplay(Integer userVoucherId, String userId, Integer voucherId, 
                             String voucherCode, String description, Double discountPercentage,
                             String expireDate, Integer quantity, Boolean isUsed, 
                             Timestamp usedAt, String status) {
        this.userVoucherId = userVoucherId;
        this.userId = userId;
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.isUsed = isUsed;
        this.usedAt = usedAt;
        this.status = status;
    }

    // Getters
    public Integer getUserVoucherId() {
        return userVoucherId;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public String getDescription() {
        return description;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public Timestamp getUsedAt() {
        return usedAt;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setUserVoucherId(Integer userVoucherId) {
        this.userVoucherId = userVoucherId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void setUsedAt(Timestamp usedAt) {
        this.usedAt = usedAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserVoucherDisplay{" +
                "userVoucherId=" + userVoucherId +
                ", userId='" + userId + '\'' +
                ", voucherId=" + voucherId +
                ", voucherCode='" + voucherCode + '\'' +
                ", description='" + description + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", expireDate='" + expireDate + '\'' +
                ", quantity=" + quantity +
                ", isUsed=" + isUsed +
                ", usedAt=" + usedAt +
                ", status='" + status + '\'' +
                '}';
    }
} 