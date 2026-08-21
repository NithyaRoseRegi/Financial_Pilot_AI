package com.financialpilot.service;
import java.util.List;
import java.util.Scanner;

import com.financialpilot.database.BudgetDAO;
import com.financialpilot.dto.BudgetSummary;
import com.financialpilot.model.Budget;
import com.financialpilot.model.User;

public class BudgetService {

    UserService userservice = new UserService();

    public void budgetMenu(Scanner scanner) {

        while (true) {

            System.out.println("\n========== BUDGET MENU ==========");
            System.out.println("1. Set Budget");
            System.out.println("2. View Budget");
            System.out.println("3. Update Budget");
            System.out.println("4. Delete Budget");
            System.out.println("5. View Remaining Budget");
            System.out.println("6. Back");

            System.out.print("Enter Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    addBudget(scanner);
                    break;

                case 2:
                    viewBudget(scanner);
                    break;

                case 3:
                    updateBudget(scanner);
                    break;

                case 4:
                    deleteBudget(scanner);
                    break;

                case 5:
                    viewRemainingBudget(scanner);
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    // ---------------- Add Budget ----------------

    public void addBudget(Scanner scanner) {

        User currentUser = userservice.getCurrentUser();

        System.out.print("Enter Month (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        if (BudgetDAO.budgetExists(currentUser.getUserId(), month, year)) {

            System.out.println("Budget already exists for this month.");
            return;
        }

        System.out.print("Enter Budget Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Budget budget = new Budget();

        budget.setUserId(currentUser.getUserId());
        budget.setMonth(month);
        budget.setYear(year);
        budget.setAmount(amount);

        if (BudgetDAO.addBudget(budget)) {

            System.out.println("Budget Added Successfully!");

        } else {

            System.out.println("Failed to Add Budget.");
        }
    }

    // ---------------- View Budget ----------------

    public void viewBudget(Scanner scanner) {

        User currentUser = userservice.getCurrentUser();

        System.out.print("Enter Month: ");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        Budget budget = BudgetDAO.getBudget(currentUser.getUserId(), month, year);

        if (budget == null) {

            System.out.println("No Budget Found.");
            return;
        }

        System.out.println("\n========== BUDGET ==========");
        System.out.println(budget);
    }

    // ---------------- Update Budget ----------------

    public void updateBudget(Scanner scanner) {

        User currentUser = userservice.getCurrentUser();

        System.out.print("Enter Month: ");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        Budget budget = BudgetDAO.getBudget(currentUser.getUserId(), month, year);

        if (budget == null) {

            System.out.println("Budget Not Found.");
            return;
        }

        System.out.print("Enter New Budget Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        budget.setAmount(amount);

        if (BudgetDAO.updateBudget(budget)) {

            System.out.println("Budget Updated Successfully!");

        } else {

            System.out.println("Update Failed.");
        }
    }

    // ---------------- Delete Budget ----------------

    public void deleteBudget(Scanner scanner) {

        User currentUser = userservice.getCurrentUser();

        System.out.print("Enter Month: ");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        Budget budget = BudgetDAO.getBudget(currentUser.getUserId(), month, year);

        if (budget == null) {

            System.out.println("Budget Not Found.");
            return;
        }

        if (BudgetDAO.deleteBudget(budget.getBudgetId())) {

            System.out.println("Budget Deleted Successfully!");

        } else {

            System.out.println("Delete Failed.");
        }
    }

    // ---------------- Remaining Budget ----------------

    public void viewRemainingBudget(Scanner scanner) {

        User currentUser = userservice.getCurrentUser();

        System.out.print("Enter Month: ");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        Budget budget = BudgetDAO.getBudget(currentUser.getUserId(), month, year);

        if (budget == null) {

            System.out.println("No Budget Found.");
            return;
        }

        double totalExpenses =
                BudgetDAO.getTotalExpenses(currentUser.getUserId(), month, year);

        double remaining =
                BudgetDAO.getRemainingBudget(currentUser.getUserId(), month, year);

        System.out.println("\n========== MONTHLY BUDGET ==========");
        System.out.println("Budget           : Rs. " + budget.getAmount());
        System.out.println("Total Expenses   : Rs. " + totalExpenses);
        System.out.println("Remaining Budget : Rs. " + remaining);

        if (remaining >= 0) {

            System.out.println("Status           : Within Budget");

        } else {

            System.out.println("Status           : Budget Exceeded");
        }
    }

    // ================= REST API =================

public void addBudget(Budget budget) throws Exception {

    if (budget == null) {
        throw new Exception("Budget details cannot be empty.");
    }

    if (budget.getAmount() <= 0) {
        throw new Exception("Budget amount must be greater than zero.");
    }

    if (budget.getMonth() < 1 || budget.getMonth() > 12) {
        throw new Exception("Invalid month.");
    }

    if (BudgetDAO.budgetExists(
            budget.getUserId(),
            budget.getMonth(),
            budget.getYear())) {

        throw new Exception("Budget already exists for this month.");
    }

    boolean added = BudgetDAO.addBudget(budget);

    if (!added) {
        throw new Exception("Unable to add budget.");
    }
}

public Budget getBudget(int userId,
                        int month,
                        int year) throws Exception {

    Budget budget =
            BudgetDAO.getBudget(userId, month, year);

    if (budget == null) {
        throw new Exception("Budget not found.");
    }

    return budget;
}

public List<Budget> getAllBudgets(int userId) {

    return BudgetDAO.getAllBudgets(userId);

}

public void updateBudget(Budget budget)
        throws Exception {

    if (budget == null) {
        throw new Exception("Budget details cannot be empty.");
    }

    if (budget.getAmount() <= 0) {
        throw new Exception("Budget amount must be greater than zero.");
    }

    Budget existingBudget =
            BudgetDAO.getBudget(
                    budget.getUserId(),
                    budget.getMonth(),
                    budget.getYear());

    if (existingBudget == null) {
        throw new Exception("Budget not found.");
    }

    budget.setBudgetId(existingBudget.getBudgetId());

    boolean updated =
            BudgetDAO.updateBudget(budget);

    if (!updated) {
        throw new Exception("Unable to update budget.");
    }
}
public void deleteBudget(int userId,
                         int month,
                         int year)
        throws Exception {

    Budget budget =
            BudgetDAO.getBudget(userId,
                    month,
                    year);

    if (budget == null) {
        throw new Exception("Budget not found.");
    }

    boolean deleted =
            BudgetDAO.deleteBudget(
                    budget.getBudgetId());

    if (!deleted) {
        throw new Exception("Unable to delete budget.");
    }
}
 public BudgetSummary getRemainingBudget(
        int userId,
        int month,
        int year)
        throws Exception {

    Budget budget =
            BudgetDAO.getBudget(userId,
                    month,
                    year);

    if (budget == null) {
        throw new Exception("Budget not found.");
    }

    double totalExpenses =
            BudgetDAO.getTotalExpenses(
                    userId,
                    month,
                    year);

    double remaining =
            BudgetDAO.getRemainingBudget(
                    userId,
                    month,
                    year);

    BudgetSummary summary =
            new BudgetSummary();

    summary.setBudgetAmount(
            budget.getAmount());

    summary.setTotalExpenses(
            totalExpenses);

    summary.setRemainingBudget(
            remaining);

    if (remaining >= 0) {
        summary.setStatus("Within Budget");
    } else {
        summary.setStatus("Budget Exceeded");
    }

    return summary;
}
}
