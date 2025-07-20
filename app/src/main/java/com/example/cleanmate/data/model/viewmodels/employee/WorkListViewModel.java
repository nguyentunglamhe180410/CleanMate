package com.example.cleanmate.data.model.viewmodels.employee;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class WorkListViewModel {
    private int bookingId;
    private String serviceName;
    private String customerFullName;
    private LocalDate date;
    private LocalTime startTime;
    private int duration;
    private String address;
    private String note;
    private BigDecimal totalPrice;
    private String status;
    private String addressNo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public WorkListViewModel() {}

    public WorkListViewModel(int bookingId, String serviceName, String customerFullName, LocalDate date, LocalTime startTime, int duration, String address, String note, BigDecimal totalPrice, String status, String addressNo, Timestamp createdAt, Timestamp updatedAt) {
        this.bookingId = bookingId;
        this.serviceName = serviceName;
        this.customerFullName = customerFullName;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.address = address;
        this.note = note;
        this.totalPrice = totalPrice;
        this.status = status;
        this.addressNo = addressNo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
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
}
