package com.example.cleanmate.data.model;

import java.sql.Timestamp;

public class Feedback {

    private Integer feedbackId;
    private Integer bookingId;
    private String userId;
    private String cleanerId;
    private Double rating;
    private String content;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public Feedback() {
    }

    public Feedback(Integer feedbackid, Integer bookingid, String userid, String cleanerid, Double rating, String content, Timestamp createdat, Timestamp updatedat) {
        this.feedbackId = feedbackid;
        this.bookingId = bookingid;
        this.userId = userid;
        this.cleanerId = cleanerid;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdat;
        this.updatedAt = updatedat;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
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
