package com.example.cleanmate.data.model.viewmodels.employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class WorkHistoryViewModel {
    private int bookingId;
    private String serviceName;
    private String customerFullName;
    private LocalDate date;
    private LocalTime startTime;
    private BigDecimal duration;
    private String address;
    private String note;
    private BigDecimal earnings;
    private Double rating;
    private String comment;
    private String status;

    public WorkHistoryViewModel() {}

    public WorkHistoryViewModel(int bookingId, String serviceName, String customerFullName, LocalDate date, LocalTime startTime, BigDecimal duration, String address, String note, BigDecimal earnings, Double rating, String comment, String status) {
        this.bookingId = bookingId;
        this.serviceName = serviceName;
        this.customerFullName = customerFullName;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.address = address;
        this.note = note;
        this.earnings = earnings;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getEarnings() {
        return earnings;
    }

    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

