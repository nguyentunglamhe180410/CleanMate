package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class ServicePrice {

    private Integer priceid;
    private Integer durationid;
    private Integer serviceid;
    private java.math.BigDecimal price;

    public ServicePrice() {
    }

    public ServicePrice(Integer priceid, Integer durationid, Integer serviceid, BigDecimal price) {
        this.priceid = priceid;
        this.durationid = durationid;
        this.serviceid = serviceid;
        this.price = price;
    }

    public Integer getPriceid() {
        return priceid;
    }

    public void setPriceid(Integer priceid) {
        this.priceid = priceid;
    }

    public Integer getDurationid() {
        return durationid;
    }

    public void setDurationid(Integer durationid) {
        this.durationid = durationid;
    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
