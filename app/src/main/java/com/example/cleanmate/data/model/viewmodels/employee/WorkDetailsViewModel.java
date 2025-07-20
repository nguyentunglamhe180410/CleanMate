package com.example.cleanmate.data.model.viewmodels.employee;

import java.math.BigDecimal;

public class WorkDetailsViewModel {
    private int bookingId;
    private String serviceName;
    private String serviceDescription;
    private String duration;
    private String price;
    private String commission;
    private String date;
    private String startTime;
    private String address;
    private String addressNo;
    private String note;
    private String status;
    private int statusId;
    private boolean isRead;
    private String customerFullName;
    private String customerPhoneNumber;
    private String employeeId;
    private String placeID;
    private String latitude;
    private String longitude;
    private BigDecimal decimalPrice;
    private BigDecimal decimalCommission;

    public WorkDetailsViewModel() {}

    public WorkDetailsViewModel(int bookingId, String serviceName, String serviceDescription, String duration, String price, String commission, String date, String startTime, String address, String addressNo, String note, String status, int statusId, boolean isRead, String customerFullName, String customerPhoneNumber, String employeeId, String placeID, String latitude, String longitude, BigDecimal decimalPrice, BigDecimal decimalCommission) {
        this.bookingId = bookingId;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.duration = duration;
        this.price = price;
        this.commission = commission;
        this.date = date;
        this.startTime = startTime;
        this.address = address;
        this.addressNo = addressNo;
        this.note = note;
        this.status = status;
        this.statusId = statusId;
        this.isRead = isRead;
        this.customerFullName = customerFullName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.employeeId = employeeId;
        this.placeID = placeID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.decimalPrice = decimalPrice;
        this.decimalCommission = decimalCommission;
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

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getDecimalPrice() {
        return decimalPrice;
    }

    public void setDecimalPrice(BigDecimal decimalPrice) {
        this.decimalPrice = decimalPrice;
    }

    public BigDecimal getDecimalCommission() {
        return decimalCommission;
    }

    public void setDecimalCommission(BigDecimal decimalCommission) {
        this.decimalCommission = decimalCommission;
    }
}

