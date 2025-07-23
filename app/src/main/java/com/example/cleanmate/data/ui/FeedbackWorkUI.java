package com.example.cleanmate.data.ui;

import java.io.Serializable;

public class FeedbackWorkUI implements Serializable {
    private String customerFullName;
    private String startTime;
    private String date;
    private int rating;
    private String content;
    private double decimalPrice;

    public FeedbackWorkUI(String customerFullName, String startTime, String date,
                          int rating, String content, double decimalPrice) {
        this.customerFullName = customerFullName;
        this.startTime = startTime;
        this.date = date;
        this.rating = rating;
        this.content = content;
        this.decimalPrice = decimalPrice;
    }

    public String getCustomerFullName() { return customerFullName; }
    public String getStartTime() { return startTime; }
    public String getDate() { return date; }
    public int getRating() { return rating; }
    public String getContent() { return content; }
    public double getDecimalPrice() { return decimalPrice; }
}
