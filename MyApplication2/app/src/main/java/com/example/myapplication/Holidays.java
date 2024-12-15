package com.example.myapplication;

public class Holidays {
    private String startDate;
    private int numberOfDays;
    private String reason;
    public Holidays(String startDate, int numberOfDays, String reason) {
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.reason = reason;
    }
    @Override
    public String toString() {
        return "Start Date: " + startDate + "\n" +
                "Number of Days: " + numberOfDays + "\n" +
                "Reason: " + reason;
    }

    public String getStartDate() {
        return startDate;
    }
}