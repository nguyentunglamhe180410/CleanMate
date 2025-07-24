package com.example.MovieInABox.data.model.viewmodels.employee;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class FeedbackHistoryViewModel {
    private int bookingId;
    private LocalDateTime date;
    private LocalTime startTime;
    private Double rating;
    private String content;
    private String customerFullName;

    public FeedbackHistoryViewModel() {}

    public FeedbackHistoryViewModel(int bookingId, LocalDateTime date, LocalTime startTime,
                                    Double rating, String content, String customerFullName) {
        this.bookingId = bookingId;
        this.date = date;
        this.startTime = startTime;
        this.rating = rating;
        this.content = content;
        this.customerFullName = customerFullName;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
}
