package com.example.MovieInABox.data.model.viewmodels.employee;

public class AcceptWorkNotificationViewModel {
    private int bookingId;
    private String customerFullName;
    private String customerPhoneNumber;
    private String customerEmail;

    public AcceptWorkNotificationViewModel() {}

    public AcceptWorkNotificationViewModel(int bookingId, String customerFullName,
                                           String customerPhoneNumber, String customerEmail) {
        this.bookingId = bookingId;
        this.customerFullName = customerFullName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerEmail = customerEmail;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

