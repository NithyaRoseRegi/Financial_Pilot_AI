package com.financialpilot.model;

public class Expense {

    private String category;
    private double amount;
    private String description;

    public Expense() {

    }

    public Expense(String category, double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public void displayExpense() {

        System.out.println("----------------------------");
        System.out.println("Category    : " + category);
        System.out.println("Amount      : $" + amount);
        System.out.println("Description : " + description);

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}