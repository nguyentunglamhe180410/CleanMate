package com.example.cleanmate.data.model;

public class CleanerProfile {

    private Integer cleanerId;
    private String userId;
    private Double rating;
    private Integer experienceYear;
    private Boolean available;
    private String area;

    public Integer getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(Integer cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(Integer experienceYear) {
        this.experienceYear = experienceYear;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public CleanerProfile() {
    }

    public CleanerProfile(Integer cleanerId, String userid, Double rating, Integer experienceYear, Boolean available, String area) {
        this.cleanerId = cleanerId;
        this.userId = userid;
        this.rating = rating;
        this.experienceYear = experienceYear;
        this.available = available;
        this.area = area;
    }
}
