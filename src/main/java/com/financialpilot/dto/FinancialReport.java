package com.financialpilot.dto;

public class FinancialReport {

    private double totalBankBalance;
    private double totalExpenses;
    private double budgetAmount;
    private double remainingBudget;

    private int expenseCount;

    private double highestExpense;
    private String highestExpenseCategory;


    public FinancialReport() {
    }


    public double getTotalBankBalance() {
        return totalBankBalance;
    }

    public void setTotalBankBalance(double totalBankBalance) {
        this.totalBankBalance = totalBankBalance;
    }


    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }


    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }


    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }


    public int getExpenseCount() {
        return expenseCount;
    }

    public void setExpenseCount(int expenseCount) {
        this.expenseCount = expenseCount;
    }


    public double getHighestExpense() {
        return highestExpense;
    }

    public void setHighestExpense(double highestExpense) {
        this.highestExpense = highestExpense;
    }


    public String getHighestExpenseCategory() {
        return highestExpenseCategory;
    }

    public void setHighestExpenseCategory(String highestExpenseCategory) {
        this.highestExpenseCategory = highestExpenseCategory;
    }
}