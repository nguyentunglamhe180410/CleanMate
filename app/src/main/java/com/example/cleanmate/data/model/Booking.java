package com.example.cleanmate.data.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {

    private Integer bookingid;
    private Integer servicePriceid;
    private String cleanerid;
    private String userid;
    private Integer bookingstatusid;
    private String note;
    private Integer addressid;
    private String date;
    private String starttime;
    private java.math.BigDecimal totalprice;
    private java.sql.Timestamp createdat;
    private java.sql.Timestamp updatedat;

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public Integer getServicePriceid() {
        return servicePriceid;
    }

    public void setServicePriceid(Integer servicePriceid) {
        this.servicePriceid = servicePriceid;
    }

    public String getCleanerid() {
        return cleanerid;
    }

    public void setCleanerid(String cleanerid) {
        this.cleanerid = cleanerid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getBooking tatusid() {
        return bookingstatusid;
    }

    public void setBooking tatusid(Integer bookingstatusid) {
        this.bookingstatusid = bookingstatusid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }

    public Booking() {
    }

    public Booking(Integer bookingid, Integer servicePriceid, String cleanerid, String userid, Integer bookingstatusid, String note, Integer addressid, String date, String starttime, BigDecimal totalprice, Timestamp createdat, Timestamp updatedat) {
        this.bookingid = bookingid;
        this.servicePriceid = servicePriceid;
        this.cleanerid = cleanerid;
        this.userid = userid;
        this.bookingstatusid = bookingstatusid;
        this.note = note;
        this.addressid = addressid;
        this.date = date;
        this.starttime = starttime;
        this.totalprice = totalprice;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }
}
