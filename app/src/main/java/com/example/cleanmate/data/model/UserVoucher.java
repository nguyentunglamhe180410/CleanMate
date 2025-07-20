package com.example.cleanmate.data.model;

public class UserVoucher {

    private Integer uservoucherid;
    private String userid;
    private Integer voucherid;
    private Integer quantity;
    private Boolean isused;
    private java.sql.Timestamp usedat;

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

    public Boolean getIsused() {
        return isused;
    }

    public void setIsused(Boolean isused) {
        this.isused = isused;
    }

    public java.sql.Timestamp getUsedat() {
        return usedat;
    }

    public void setUsedat(java.sql.Timestamp usedat) {
        this.usedat = usedat;
    }

}
