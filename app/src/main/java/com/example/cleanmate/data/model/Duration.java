package com.example.cleanmate.data.model;

public class Duration {

    private Integer durationid;
    private Integer durationtime;
    private String squaremeterspecific;

    public Duration() {
    }

    public Duration(Integer durationid, Integer durationtime, String squaremeterspecific) {
        this.durationid = durationid;
        this.durationtime = durationtime;
        this.squaremeterspecific = squaremeterspecific;
    }

    public Integer getDurationid() {
        return durationid;
    }

    public void setDurationid(Integer durationid) {
        this.durationid = durationid;
    }

    public Integer getDurationtime() {
        return durationtime;
    }

    public void setDurationtime(Integer durationtime) {
        this.durationtime = durationtime;
    }

    public String getSquaremeterspecific() {
        return squaremeterspecific;
    }

    public void setSquaremeterspecific(String squaremeterspecific) {
        this.squaremeterspecific = squaremeterspecific;
    }
}
