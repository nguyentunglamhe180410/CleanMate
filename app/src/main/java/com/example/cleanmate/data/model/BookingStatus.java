package com.example.cleanmate.data.model;

public class BookingStatus {

    private Integer bookingStatusId;
    private String status;
    private String statusDescription;

    public Integer getBookingStatusid() {
        return bookingStatusId;
    }

    public void setBookingStatusid(Integer bookingstatusid) {
        this.bookingStatusId = bookingstatusid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
