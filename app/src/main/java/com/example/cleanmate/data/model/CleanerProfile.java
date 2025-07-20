package com.example.cleanmate.data.model;

public class CleanerProfile {

    private Integer cleanerid;
    private String userid;
    private Double rating;
    private Integer experienceYear;
    private Boolean available;
    private String area;

    public Integer getCleanerid() {
        return cleanerid;
    }

    public void setCleanerid(Integer cleanerid) {
        this.cleanerid = cleanerid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public CleanerProfile(Integer cleanerid, String userid, Double rating, Integer experienceYear, Boolean available, String area) {
        this.cleanerid = cleanerid;
        this.userid = userid;
        this.rating = rating;
        this.experienceYear = experienceYear;
        this.available = available;
        this.area = area;
    }
}
