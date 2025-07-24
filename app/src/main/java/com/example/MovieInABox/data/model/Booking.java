package com.example.MovieInABox.data.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private Integer bookingId;
    private Integer servicePriceId;
    private String cleanerId;
    private String userId;
    private Integer bookingStatusId;
    private String note;
    private Integer addressId;
    private String date;
    private String startTime;
    private java.math.BigDecimal totalPrice;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(Integer servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public String getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return LocalTime.parse(startTime);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getBookingStatusId() {
        return bookingStatusId;
    }

    public void setBookingStatusId(Integer bookingStatusId) {
        this.bookingStatusId = bookingStatusId;
    }

    public Booking() {
    }

    public Booking(Integer bookingId, Integer servicePriceId, String cleanerId, String userId, Integer bookingStatusId, String note, Integer addressId, String date, String startTime, BigDecimal totalPrice, Timestamp createdAt, Timestamp updatedAt) {
        this.bookingId = bookingId;
        this.servicePriceId = servicePriceId;
        this.cleanerId = cleanerId;
        this.userId = userId;
        this.bookingStatusId = bookingStatusId;
        this.note = note;
        this.addressId = addressId;
        this.date = date;
        this.startTime = startTime;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
