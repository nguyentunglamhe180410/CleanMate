package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class CustomerAddress {

    private Integer addressid;
    private String userid;
    private String ggFormattedaddress;
    private String ggDispalyname;
    private String ggPlaceid;
    private String addressno;
    private Boolean isinuse;
    private Boolean isdefault;
    private java.math.BigDecimal latitude;
    private java.math.BigDecimal longitude;

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGgFormattedaddress() {
        return ggFormattedaddress;
    }

    public void setGgFormattedaddress(String ggFormattedaddress) {
        this.ggFormattedaddress = ggFormattedaddress;
    }

    public String getGgDispalyname() {
        return ggDispalyname;
    }

    public void setGgDispalyname(String ggDispalyname) {
        this.ggDispalyname = ggDispalyname;
    }

    public String getGgPlaceid() {
        return ggPlaceid;
    }

    public void setGgPlaceid(String ggPlaceid) {
        this.ggPlaceid = ggPlaceid;
    }

    public String getAddressno() {
        return addressno;
    }

    public void setAddressno(String addressno) {
        this.addressno = addressno;
    }

    public Boolean getIsinuse() {
        return isinuse;
    }

    public void setIsinuse(Boolean isinuse) {
        this.isinuse = isinuse;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public CustomerAddress() {
    }

    public CustomerAddress(Integer addressid, String userid, String ggFormattedaddress, String ggDispalyname, String ggPlaceid, String addressno, Boolean isinuse, Boolean isdefault, BigDecimal latitude, BigDecimal longitude) {
        this.addressid = addressid;
        this.userid = userid;
        this.ggFormattedaddress = ggFormattedaddress;
        this.ggDispalyname = ggDispalyname;
        this.ggPlaceid = ggPlaceid;
        this.addressno = addressno;
        this.isinuse = isinuse;
        this.isdefault = isdefault;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
