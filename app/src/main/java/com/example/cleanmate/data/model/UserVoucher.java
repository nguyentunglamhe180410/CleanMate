package com.example.cleanmate.data.model;

import java.sql.Timestamp;

public class UserVoucher {

    private Integer userVoucherId;
    private String userId;
    private Integer voucherId;
    private Integer quantity;
    private Boolean isUsed;
    private java.sql.Timestamp usedAt;

    public UserVoucher() {
    }

    public UserVoucher(Integer uservoucherid, String userid, Integer voucherid, Integer quantity, Boolean isused, Timestamp usedat) {
        this.userVoucherId = uservoucherid;
        this.userId = userid;
        this.voucherId = voucherid;
        this.quantity = quantity;
        this.isUsed = isused;
        this.usedAt = usedat;
    }

    public Integer getUserVoucherId() {
        return userVoucherId;
    }

    public void setUserVoucherId(Integer userVoucherId) {
        this.userVoucherId = userVoucherId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Timestamp getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Timestamp usedAt) {
        this.usedAt = usedAt;
    }
}
