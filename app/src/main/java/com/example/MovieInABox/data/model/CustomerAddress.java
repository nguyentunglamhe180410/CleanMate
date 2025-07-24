package com.example.MovieInABox.data.model;

import java.math.BigDecimal;

public class CustomerAddress {

    private Integer addressId;
    private String userId;
    private String ggFormattAdaddress;
    private String ggDispalyName;
    private String ggPlaceId;
    private String addressNo;
    private Boolean isInUse;
    private Boolean isDefault;
    private java.math.BigDecimal latitude;
    private java.math.BigDecimal longitude;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGgFormattAdaddress() {
        return ggFormattAdaddress;
    }

    public void setGgFormattAdaddress(String ggFormattAdaddress) {
        this.ggFormattAdaddress = ggFormattAdaddress;
    }

    public String getGgDispalyName() {
        return ggDispalyName;
    }

    public void setGgDispalyName(String ggDispalyName) {
        this.ggDispalyName = ggDispalyName;
    }

    public String getGgPlaceId() {
        return ggPlaceId;
    }

    public void setGgPlaceId(String ggPlaceId) {
        this.ggPlaceId = ggPlaceId;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public Boolean getIsInUse() {
        return isInUse;
    }

    public void setIsInUse(Boolean isInUse) {
        this.isInUse = isInUse;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
        this.addressId = addressid;
        this.userId = userid;
        this.ggFormattAdaddress = ggFormattedaddress;
        this.ggDispalyName = ggDispalyname;
        this.ggPlaceId = ggPlaceid;
        this.addressNo = addressno;
        this.isInUse = isinuse;
        this.isDefault = isdefault;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
