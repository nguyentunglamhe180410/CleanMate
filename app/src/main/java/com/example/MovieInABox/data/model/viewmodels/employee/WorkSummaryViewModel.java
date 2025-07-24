package com.example.MovieInABox.data.model.viewmodels.employee;

public class WorkSummaryViewModel {
    private int newWorkCount;
    private int pendingWorkCount;
    private int confirmedWorkCount;

    public WorkSummaryViewModel() {}

    public WorkSummaryViewModel(int newWorkCount, int pendingWorkCount, int confirmedWorkCount) {
        this.newWorkCount = newWorkCount;
        this.pendingWorkCount = pendingWorkCount;
        this.confirmedWorkCount = confirmedWorkCount;
    }

    public int getNewWorkCount() {
        return newWorkCount;
    }

    public void setNewWorkCount(int newWorkCount) {
        this.newWorkCount = newWorkCount;
    }

    public int getPendingWorkCount() {
        return pendingWorkCount;
    }

    public void setPendingWorkCount(int pendingWorkCount) {
        this.pendingWorkCount = pendingWorkCount;
    }

    public int getConfirmedWorkCount() {
        return confirmedWorkCount;
    }

    public void setConfirmedWorkCount(int confirmedWorkCount) {
        this.confirmedWorkCount = confirmedWorkCount;
    }
}
