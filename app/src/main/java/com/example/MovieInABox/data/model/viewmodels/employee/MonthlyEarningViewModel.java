package com.example.MovieInABox.data.model.viewmodels.employee;
import java.math.BigDecimal;

public class MonthlyEarningViewModel {
    private int month;
    private BigDecimal earnings;

    public MonthlyEarningViewModel() {}

    public MonthlyEarningViewModel(int month, BigDecimal earnings) {
        this.month = month;
        this.earnings = earnings;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getEarnings() {
        return earnings;
    }

    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }
}

