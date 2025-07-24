package com.example.MovieInABox.data.model.viewmodels.customer;

import java.util.List;

public class CustomerReviewSummaryViewModel {
    private double averageRating;
    private int totalReviews;
    private List<CustomerReviewViewModel> reviews;

    public CustomerReviewSummaryViewModel() {}

    public CustomerReviewSummaryViewModel(double averageRating, int totalReviews,
                                          List<CustomerReviewViewModel> reviews) {
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.reviews = reviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<CustomerReviewViewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<CustomerReviewViewModel> reviews) {
        this.reviews = reviews;
    }
}

