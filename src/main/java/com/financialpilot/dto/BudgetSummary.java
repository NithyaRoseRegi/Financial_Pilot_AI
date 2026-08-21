package com.financialpilot.dto;

public class BudgetSummary {

    private double budgetAmount;
    private double totalExpenses;
    private double remainingBudget;
    private String status;

    // Default Constructor
    public BudgetSummary() {
    }

    // Parameterized Constructor
    public BudgetSummary(double budgetAmount,
                         double totalExpenses,
                         double remainingBudget,
                         String status) {

        this.budgetAmount = budgetAmount;
        this.totalExpenses = totalExpenses;
        this.remainingBudget = remainingBudget;
        this.status = status;
    }

    // Getters and Setters

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}