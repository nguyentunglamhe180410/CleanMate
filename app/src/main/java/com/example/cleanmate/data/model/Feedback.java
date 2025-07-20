package com.example.cleanmate.data.model;

import java.sql.Timestamp;

public class Feedback {

    private Integer feedbackid;
    private Integer bookingid;
    private String userid;
    private String cleanerid;
    private Double rating;
    private String content;
    private java.sql.Timestamp createdat;
    private java.sql.Timestamp updatedat;

    public Feedback() {
    }

    public Feedback(Integer feedbackid, Integer bookingid, String userid, String cleanerid, Double rating, String content, Timestamp createdat, Timestamp updatedat) {
        this.feedbackid = feedbackid;
        this.bookingid = bookingid;
        this.userid = userid;
        this.cleanerid = cleanerid;
        this.rating = rating;
        this.content = content;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public Integer getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(Integer feedbackid) {
        this.feedbackid = feedbackid;
    }

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCleanerid() {
        return cleanerid;
    }

    public void setCleanerid(String cleanerid) {
        this.cleanerid = cleanerid;
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

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }
}
