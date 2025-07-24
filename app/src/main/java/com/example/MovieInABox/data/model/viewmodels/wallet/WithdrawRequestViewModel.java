package com.example.MovieInABox.data.model.viewmodels.wallet;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class WithdrawRequestViewModel {
    private int withdrawalId;
    private BigDecimal amount;
    private LocalDateTime requestDate;
    private String status; // Pending, Approved, Done, Rejected

    public WithdrawRequestViewModel() {}

    public WithdrawRequestViewModel(int withdrawalId, BigDecimal amount,
                                    LocalDateTime requestDate, String status) {
        this.withdrawalId = withdrawalId;
        this.amount = amount;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getWithdrawalId() {
        return withdrawalId;
    }
    public void setWithdrawalId(int withdrawalId) {
        this.withdrawalId = withdrawalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

