package com.example.MovieInABox.data.model.viewmodels.employee;

import java.time.LocalDate;
import java.time.LocalTime;

public class CancelWorkRequestViewModel {
    private int bookingId;
    private String serviceName;
    private LocalDate date;
    private LocalTime startTime;

    public CancelWorkRequestViewModel() {}

    public CancelWorkRequestViewModel(int bookingId, String serviceName,
                                      LocalDate date, LocalTime startTime) {
        this.bookingId = bookingId;
        this.serviceName = serviceName;
        this.date = date;
        this.startTime = startTime;
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
}

