package com.financialpilot.service;

import java.util.ArrayList;

import com.financialpilot.model.BankAccount;
import com.financialpilot.model.Expense;
import com.financialpilot.model.User;

public class SummaryService {

    public void displaySummary(User user,
                               BankAccount account,
                               ArrayList<Expense> expenses) {

        System.out.println("\n========== FINANCIAL SUMMARY ==========");

        if (user != null) {
            System.out.println("Monthly Income : $" + user.getMonthlyIncome());
        }

        if (account != null) {
            System.out.println("Bank Balance   : $" + account.getBalance());
        }

        double totalExpenses = 0;

        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        System.out.println("Total Expenses : $" + totalExpenses);

        if (user != null) {

            double remaining = user.getMonthlyIncome() - totalExpenses;

            System.out.println("Remaining Money: $" + remaining);

            double savingsRate =
                    (remaining / user.getMonthlyIncome()) * 100;

            System.out.printf("Savings Rate   : %.2f%%\n", savingsRate);
        }

        System.out.println("=======================================");
    }
}