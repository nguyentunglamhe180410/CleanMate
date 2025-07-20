package com.example.cleanmate.data.model;

public class BookingStatus {

    private Integer bookingstatusid;
    private String status;
    private String statusdescription;

    public Integer getBookingstatusid() {
        return bookingstatusid;
    }

    public void setBookingstatusid(Integer bookingstatusid) {
        this.bookingstatusid = bookingstatusid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusdescription() {
        return statusdescription;
    }

    public void setStatusdescription(String statusdescription) {
        this.statusdescription = statusdescription;
    }

}
