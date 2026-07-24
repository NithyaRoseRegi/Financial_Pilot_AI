package com.financialpilot.model;

public class Budget {

    private int budgetId;
    private int userId;
    private int month;
    private int year;
    private double amount;

    // Default Constructor
    public Budget() {

    }

    // Parameterized Constructor
    public Budget(int userId, int month, int year, double amount) {

        this.userId = userId;
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    // Getters and Setters

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {

        return "Budget ID : " + budgetId +
               "\nUser ID : " + userId +
               "\nMonth : " + month +
               "\nYear : " + year +
               "\nBudget : Rs. " + amount;
    }
}