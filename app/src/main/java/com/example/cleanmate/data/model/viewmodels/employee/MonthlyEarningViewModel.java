package com.example.cleanmate.data.model.viewmodels.employee;
import java.math.BigDecimal;

public class MonthlyEarningViewModel {
    private String month;
    private BigDecimal earnings;

    public MonthlyEarningViewModel() {}

    public MonthlyEarningViewModel(String month, BigDecimal earnings) {
        this.month = month;
        this.earnings = earnings;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getEarnings() {
        return earnings;
    }

    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }
}

