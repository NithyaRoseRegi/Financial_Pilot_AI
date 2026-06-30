package com.financialpilot.model;

public class BankAccount {

    private String accountNumber;
    private String bankName;
    private double balance;

    public BankAccount() {

    }

    public BankAccount(String accountNumber, String bankName, double balance) {

        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.balance = balance;

    }

    public void displayAccount() {

        System.out.println("\n========== BANK ACCOUNT ==========");

        System.out.println("Account Number : " + accountNumber);
        System.out.println("Bank Name      : " + bankName);
        System.out.println("Balance        : $" + balance);

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}