package com.financialpilot.service;

import java.util.ArrayList;
import java.util.Scanner;

import com.financialpilot.model.Expense;
import com.financialpilot.util.InputValidator;

public class ExpenseService {

    private final InputValidator validator = new InputValidator();
    public ArrayList<Expense> createExpenses(Scanner scanner) {

        ArrayList<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense();

        System.out.print("\nHow many expenses do you want to add? ");

        int numberOfExpenses = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfExpenses; i++) {

            System.out.println("\nExpense " + i);

            System.out.print("Category: ");
            expense.setCategory(scanner.nextLine());

            System.out.print("Amount: ");
            expense.setAmount(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Description: ");
            expense.setDescription(scanner.nextLine());

            expenses.add(expense);
        }

        return expenses;
    }

    public void updateExpense(ArrayList<Expense> expenses, Scanner scanner) {

    if (expenses.isEmpty()) {
        System.out.println("\nNo expenses available.");
        return;
    }

    displayExpenses(expenses);

    System.out.print("\nEnter expense number to update: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    if (choice < 1 || choice > expenses.size()) {
        System.out.println("Invalid expense number.");
        return;
    }

    Expense expense = expenses.get(choice - 1);

    System.out.print("New Category: ");
    expense.setCategory(scanner.nextLine());

    System.out.print("New Amount: ");
    expense.setAmount(scanner.nextDouble());
    scanner.nextLine();

    System.out.print("New Description: ");
    expense.setDescription(scanner.nextLine());

    System.out.println("\nExpense updated successfully.");
}

    public void displayExpenses(ArrayList<Expense> expenses) {

    if (expenses.isEmpty()) {
        System.out.println("\nNo expenses found.");
        return;
    }

    System.out.println("\n========== ALL EXPENSES ==========");

    for (int i = 0; i < expenses.size(); i++) {

        Expense expense = expenses.get(i);

        System.out.println("\nExpense #" + (i + 1));
        System.out.println("----------------------------");
        System.out.println("Category    : " + expense.getCategory());
        System.out.println("Amount      : $" + expense.getAmount());
        System.out.println("Description : " + expense.getDescription());
    }
}

public void deleteExpense(ArrayList<Expense> expenses, Scanner scanner) {

    if (expenses.isEmpty()) {
        System.out.println("\nNo expenses available.");
        return;
    }

    displayExpenses(expenses);

    System.out.print("\nEnter expense number to delete: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    if (choice < 1 || choice > expenses.size()) {
        System.out.println("Invalid expense number.");
        return;
    }

    expenses.remove(choice - 1);

    System.out.println("\nExpense deleted successfully.");
}


}