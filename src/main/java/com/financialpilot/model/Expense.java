package com.financialpilot.model;

import java.time.LocalDate;

public class Expense {

    private int expenseId;
    private int userId;
    private int accountId;

    private String title;
    private String category;
    private double amount;
    private LocalDate date;
    private String note;

    // Default Constructor
    public Expense() {

    }

    // Parameterized Constructor
    public Expense(int expenseId, int userId, int accountId,
                   String title, String category,
                   double amount, LocalDate date, String note) {

        this.expenseId = expenseId;
        this.userId = userId;
        this.accountId = accountId;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    // Getters and Setters

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", userId=" + userId +
                ", accountId=" + accountId +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}