package com.financialpilot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.financialpilot.database.BankAccountDAO;
import com.financialpilot.database.ExpenseDAO;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.ExpenseNotFoundException;
import com.financialpilot.exception.ValidationException;
import com.financialpilot.model.BankAccount;
import com.financialpilot.model.Expense;
import com.financialpilot.util.InputValidator;

public class ExpenseService {

    private UserService userService;
    private InputValidator validator = new InputValidator();

    public ExpenseService(UserService userService) {
        this.userService = userService;
    }

    /* ===========================================================
                     REST API METHODS
       =========================================================== */

    public void addExpense(Expense expense)
            throws ValidationException, DatabaseException {

        if (expense.getTitle() == null ||
                expense.getTitle().trim().isEmpty()) {

            throw new ValidationException("Title cannot be empty.");
        }

        if (expense.getCategory() == null ||
                expense.getCategory().trim().isEmpty()) {

            throw new ValidationException("Category cannot be empty.");
        }

        if (expense.getAmount() <= 0) {

            throw new ValidationException(
                    "Amount must be greater than zero.");
        }

        boolean success = ExpenseDAO.addExpense(expense);

        if (!success) {
            throw new DatabaseException(
                    "Unable to save expense.");
        }
    }

    public ArrayList<Expense> getExpenses(int userId) {

        return ExpenseDAO.getAllExpensesByUser(userId);
    }

    public Expense getExpenseById(int expenseId)
            throws ExpenseNotFoundException {

        Expense expense =
                ExpenseDAO.getExpenseById(expenseId);

        if (expense == null) {
            throw new ExpenseNotFoundException(
                    "Expense not found.");
        }

        return expense;
    }

    public void updateExpense(Expense expense)
            throws ExpenseNotFoundException,
            ValidationException,
            DatabaseException {

        Expense existingExpense =
                ExpenseDAO.getExpenseById(
                        expense.getExpenseId());

        if (existingExpense == null) {
            throw new ExpenseNotFoundException(
                    "Expense not found.");
        }

        if (expense.getAmount() <= 0) {
            throw new ValidationException(
                    "Amount must be greater than zero.");
        }

        boolean success =
                ExpenseDAO.updateExpense(expense);

        if (!success) {
            throw new DatabaseException(
                    "Unable to update expense.");
        }
    }

    public void deleteExpense(int expenseId,
                              int userId)
            throws ExpenseNotFoundException,
            DatabaseException {

        boolean success =
                ExpenseDAO.deleteExpense(
                        expenseId,
                        userId);

        if (!success) {
            throw new ExpenseNotFoundException(
                    "Expense not found.");
        }
    }

    /* ===========================================================
                   CONSOLE APPLICATION METHODS
       =========================================================== */

    public void addExpense(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        Expense expense = new Expense();

        expense.setUserId(
                userService.getCurrentUser().getUserId());

        int accountId =
                validator.readInt(
                        scanner,
                        "Enter Account ID: ");

        BankAccount account =
                BankAccountDAO.getBankAccountById(
                        String.valueOf(accountId));

        if (account == null) {
            System.out.println(
                    "Account not found.");
            return;
        }

        if (account.getUserId() !=
                userService.getCurrentUser().getUserId()) {

            System.out.println(
                    "You cannot use another user's account.");
            return;
        }

        expense.setAccountId(accountId);

        expense.setTitle(
                validator.readNonEmptyString(
                        scanner,
                        "Enter Title: "));

        expense.setCategory(
                validator.readNonEmptyString(
                        scanner,
                        "Enter Category: "));

        expense.setAmount(
                validator.readDouble(
                        scanner,
                        "Enter Amount: "));

        expense.setDate(
                validator.readDate(
                        scanner,
                        "Enter Date (yyyy-MM-dd): "));

        expense.setNote(
                validator.readNonEmptyString(
                        scanner,
                        "Enter Note: "));

        boolean success =
                ExpenseDAO.addExpense(expense);

        if (success) {
            System.out.println(
                    "\nExpense Added Successfully!");
        } else {
            System.out.println(
                    "\nFailed to Add Expense.");
        }
    }

    public void viewExpenses() {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        ArrayList<Expense> expenses =
                ExpenseDAO.getAllExpensesByUser(
                        userService.getCurrentUser().getUserId());

        if (expenses.isEmpty()) {
            System.out.println(
                    "\nNo Expenses Found.");
            return;
        }

        System.out.println(
                "\n========== EXPENSES ==========");

        for (Expense expense : expenses) {

            System.out.println(
                    "Expense ID : "
                            + expense.getExpenseId());

            System.out.println(
                    "Account ID : "
                            + expense.getAccountId());

            System.out.println(
                    "Title      : "
                            + expense.getTitle());

            System.out.println(
                    "Category   : "
                            + expense.getCategory());

            System.out.println(
                    "Amount     : ₹"
                            + expense.getAmount());

            System.out.println(
                    "Date       : "
                            + expense.getDate());

            System.out.println(
                    "Note       : "
                            + expense.getNote());

            System.out.println(
                    "------------------------------");
        }
    }

    public void updateExpense(Scanner scanner)
            throws ExpenseNotFoundException {

        // Keep your existing implementation here.
    }

    public void deleteExpense(Scanner scanner) {

        // Keep your existing implementation here.
    }

    public void analyticsMenu(Scanner scanner) {

        // Keep your existing implementation here.
    }

    public void categorySummary() {

        int userId =
                userService.getCurrentUser().getUserId();

        HashMap<String, Double> summary =
                ExpenseDAO.getCategorySummary(userId);

        if (summary.isEmpty()) {
            System.out.println("No Expenses Found.");
            return;
        }

        for (HashMap.Entry<String, Double> entry :
                summary.entrySet()) {

            System.out.printf(
                    "%-15s Rs. %.2f%n",
                    entry.getKey(),
                    entry.getValue());
        }
    }

    public void monthlySummary() {

        int userId =
                userService.getCurrentUser().getUserId();

        HashMap<Integer, Double> summary =
                ExpenseDAO.getMonthlySummary(userId);

        if (summary.isEmpty()) {
            System.out.println("No Expenses Found.");
            return;
        }

        for (Map.Entry<Integer, Double> entry :
                summary.entrySet()) {

            System.out.println(
                    "Month "
                            + entry.getKey()
                            + " : Rs. "
                            + entry.getValue());
        }
    }

    public void totalExpenses() {

        double total =
                ExpenseDAO.getTotalExpenses(
                        userService.getCurrentUser().getUserId());

        System.out.println("Total : Rs. " + total);
    }

    public void highestExpense() {

        Expense expense =
                ExpenseDAO.getHighestExpense(
                        userService.getCurrentUser().getUserId());

        System.out.println(expense);
    }

    public void mostSpentCategory() {

        String category =
                ExpenseDAO.getMostSpentCategory(
                        userService.getCurrentUser().getUserId());

        System.out.println(category);
    }
}