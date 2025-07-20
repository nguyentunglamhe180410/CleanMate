package com.example.cleanmate.data.model.viewmodels.customer;

import java.time.LocalDateTime;

public class CustomerReviewViewModel {
    private int bookingId;
    private String customerFullName;
    private double rating;
    private String comment;
    private LocalDateTime reviewDate;

    public CustomerReviewViewModel() {}

    public CustomerReviewViewModel(int bookingId, String customerFullName,
                                   double rating, String comment,
                                   LocalDateTime reviewDate) {
        this.bookingId = bookingId;
        this.customerFullName = customerFullName;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}
