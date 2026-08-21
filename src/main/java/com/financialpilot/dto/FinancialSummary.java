package com.financialpilot.dto;

public class FinancialSummary {

    private double totalBankBalance;
    private double totalExpenses;
    private double remainingBalance;

    private int bankAccountCount;
    private int expenseCount;

    private double highestExpense;
    private String highestExpenseCategory;

    public FinancialSummary() {
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

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public int getBankAccountCount() {
        return bankAccountCount;
    }

    public void setBankAccountCount(int bankAccountCount) {
        this.bankAccountCount = bankAccountCount;
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