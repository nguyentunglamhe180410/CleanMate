package com.example.MovieInABox.data.model.viewmodels.employee;


import com.example.MovieInABox.data.model.viewmodels.wallet.TransactionViewModel;
import com.example.MovieInABox.data.model.viewmodels.wallet.WithdrawRequestViewModel;

import java.math.BigDecimal;
import java.util.List;

public class EarningsSummaryViewModel {
    private BigDecimal totalEarnings;
    private BigDecimal availableBalance;
    private List<TransactionViewModel> transactions;
    private List<WithdrawRequestViewModel> withdrawalRequests;

    public EarningsSummaryViewModel() {}

    public EarningsSummaryViewModel(BigDecimal totalEarnings, BigDecimal availableBalance,
                                    List<TransactionViewModel> transactions,
                                    List<WithdrawRequestViewModel> withdrawalRequests) {
        this.totalEarnings = totalEarnings;
        this.availableBalance = availableBalance;
        this.transactions = transactions;
        this.withdrawalRequests = withdrawalRequests;
    }

    public EarningsSummaryViewModel(BigDecimal totalEarnings, BigDecimal availableBalance, List<TransactionViewModel> transactions) {
        this.totalEarnings = totalEarnings;
        this.availableBalance = availableBalance;
        this.transactions = transactions;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public List<TransactionViewModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionViewModel> transactions) {
        this.transactions = transactions;
    }

    public List<WithdrawRequestViewModel> getWithdrawalRequests() {
        return withdrawalRequests;
    }

    public void setWithdrawalRequests(List<WithdrawRequestViewModel> withdrawalRequests) {
        this.withdrawalRequests = withdrawalRequests;
    }
}

