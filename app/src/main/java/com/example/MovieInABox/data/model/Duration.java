package com.example.MovieInABox.data.model;

public class Duration {

    private Integer durationid;
    private Integer durationTime;
    private String squareMeterSpecific;

    public Duration() {
    }

    public Duration(Integer durationid, Integer durationtime, String squaremeterspecific) {
        this.durationid = durationid;
        this.durationTime = durationtime;
        this.squareMeterSpecific = squaremeterspecific;
    }

    public Integer getDurationid() {
        return durationid;
    }

    public void setDurationid(Integer durationid) {
        this.durationid = durationid;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public String getSquareMeterSpecific() {
        return squareMeterSpecific;
    }

    public void setSquareMeterSpecific(String squareMeterSpecific) {
        this.squareMeterSpecific = squareMeterSpecific;
    }
}
