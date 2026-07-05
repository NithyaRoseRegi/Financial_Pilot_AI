package com.financialpilot.service;

import java.util.ArrayList;
import java.util.Scanner;

import com.financialpilot.database.BankAccountDAO;
import com.financialpilot.database.ExpenseDAO;
import com.financialpilot.model.BankAccount;
import com.financialpilot.model.Expense;
import com.financialpilot.util.InputValidator;

public class ExpenseService {

    private UserService userService;
    private BankAccountService bankAccountService;
    private InputValidator validator = new InputValidator();
    // Constructor
    public ExpenseService(UserService userService,
                          BankAccountService bankAccountService) {

        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    // Add Expense
    public void addExpense(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        Expense expense = new Expense();

        expense.setUserId(userService.getCurrentUser().getUserId());

        int accountId = validator.readInt(scanner, "Enter Account ID: ");
        BankAccount account = BankAccountDAO.getBankAccountById(String.valueOf(accountId));

        if (account == null) {
            System.out.println("Account not found with ID " + accountId + ".");
            return;
        }

        if (account.getUserId() != userService.getCurrentUser().getUserId()) {
            System.out.println("You cannot use another user's account.");
            return;
        }

        expense.setAccountId(accountId);

        expense.setTitle(validator.readNonEmptyString(scanner, "Enter Title: "));

        expense.setCategory(validator.readNonEmptyString(scanner, "Enter Category: "));

        expense.setAmount(validator.readDouble(scanner, "Enter Amount: ")); 
       
        expense.setDate(validator.readDate(scanner, "Enter Date (yyyy-MM-dd): "));
       
        expense.setNote(validator.readNonEmptyString(scanner, "Enter Note: "));

        boolean success = ExpenseDAO.addExpense(expense);

        if (success) {
            System.out.println("\nExpense Added Successfully!");
        } else {
            System.out.println("\nFailed to Add Expense.");
        }
    }

    // View Expenses
    public void viewExpenses() {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        int userId = userService.getCurrentUser().getUserId();

        ArrayList<Expense> expenses =
                ExpenseDAO.getAllExpensesByUser(userId);

        if (expenses.isEmpty()) {
            System.out.println("\nNo Expenses Found.");
            return;
        }

        System.out.println("\n========== EXPENSES ==========");

        for (Expense expense : expenses) {

            System.out.println("Expense ID : " + expense.getExpenseId());
            System.out.println("Account ID : " + expense.getAccountId());
            System.out.println("Title      : " + expense.getTitle());
            System.out.println("Category   : " + expense.getCategory());
            System.out.println("Amount     : ₹" + expense.getAmount());
            System.out.println("Date       : " + expense.getDate());
            System.out.println("Note       : " + expense.getNote());

            System.out.println("--------------------------------");
        }
    }

    // Update Expense
    public void updateExpense(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

      
        int expenseId = Integer.parseInt(validator.readNonEmptyString(scanner, "Enter Expense ID: "));

        Expense expense = ExpenseDAO.getExpenseById(expenseId);

        if (expense == null) {
            System.out.println("Expense not found.");
            return;
        }

        if (expense.getUserId() != userService.getCurrentUser().getUserId()) {
            System.out.println("You cannot update another user's expense.");
            return;
        }

        int accountId = validator.readInt(scanner, "Enter Account ID: ");
        BankAccount account = BankAccountDAO.getBankAccountById(String.valueOf(accountId));

        if (account == null) {
            System.out.println("Account not found with ID " + accountId + ".");
            return;
        }

        if (account.getUserId() != userService.getCurrentUser().getUserId()) {
            System.out.println("You cannot use another user's account.");
            return;
        }

        expense.setAccountId(accountId);

        expense.setTitle(validator.readNonEmptyString(scanner, "Enter Title: "));

        expense.setCategory(validator.readNonEmptyString(scanner, "Enter Category: "));

        expense.setAmount(validator.readDouble(scanner, "Enter Amount: "));

        expense.setDate(validator.readDate(scanner, "Enter Date (yyyy-MM-dd): "));

        expense.setNote(validator.readNonEmptyString(scanner, "Enter Note: "));

        boolean success = ExpenseDAO.updateExpense(expense);

        if (success) {
            System.out.println("\nExpense Updated Successfully!");
        } else {
            System.out.println("\nFailed to Update Expense.");
        }
    }

    // Delete Expense
    public void deleteExpense(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        int expenseId = validator.readInt(scanner, "Enter Expense ID: ");

        boolean success = ExpenseDAO.deleteExpense(
                expenseId,
                userService.getCurrentUser().getUserId()
        );

        if (success) {
            System.out.println("\nExpense Deleted Successfully!");
        } else {
            System.out.println("\nFailed to Delete Expense.");
        }
    }
}