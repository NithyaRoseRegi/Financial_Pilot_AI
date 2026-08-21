package com.financialpilot.dto;

import java.util.Map;

public class ReportSummary {

    private double totalBudget;

    private double totalExpense;

    private double remainingBudget;

    private double highestExpense;

    private String highestExpenseCategory;

    private double lowestExpense;

    private String lowestExpenseCategory;

    private Map<String, Double> categoryWiseExpenses;

    public ReportSummary() {

    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(double remainingBudget) {
        this.remainingBudget = remainingBudget;
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

    public double getLowestExpense() {
        return lowestExpense;
    }

    public void setLowestExpense(double lowestExpense) {
        this.lowestExpense = lowestExpense;
    }

    public String getLowestExpenseCategory() {
        return lowestExpenseCategory;
    }

    public void setLowestExpenseCategory(String lowestExpenseCategory) {
        this.lowestExpenseCategory = lowestExpenseCategory;
    }

    public Map<String, Double> getCategoryWiseExpenses() {
        return categoryWiseExpenses;
    }

    public void setCategoryWiseExpenses(
            Map<String, Double> categoryWiseExpenses) {

        this.categoryWiseExpenses = categoryWiseExpenses;
    }

}