package com.example.cleanmate.data.model;

import java.math.BigDecimal;

public class ServicePrice {

    private Integer priceId;
    private Integer durationId;
    private Integer serviceId;
    private java.math.BigDecimal price;

    public ServicePrice() {
    }

    public ServicePrice(Integer priceid, Integer durationid, Integer serviceid, BigDecimal price) {
        this.priceId = priceid;
        this.durationId = durationid;
        this.serviceId = serviceid;
        this.price = price;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getDurationId() {
        return durationId;
    }

    public void setDurationId(Integer durationId) {
        this.durationId = durationId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
