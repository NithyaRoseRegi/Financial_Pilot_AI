package com.financialpilot.app;

import java.util.Scanner;

import com.financialpilot.exception.AuthenticationException;
import com.financialpilot.exception.BankAccountNotFoundException;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.DuplicateEmailException;
import com.financialpilot.exception.ExpenseNotFoundException;
import com.financialpilot.exception.ValidationException;
import com.financialpilot.service.BankAccountService;
import com.financialpilot.service.BudgetService;
import com.financialpilot.service.ExpenseService;
import com.financialpilot.service.UserService;

public class FinancialPilotApp {

    private static Scanner scanner = new Scanner(System.in);

    private static UserService userService = new UserService();

    private static BankAccountService bankAccountService =
            new BankAccountService(userService);

    private static ExpenseService expenseService =
            new ExpenseService(userService);

    private static BudgetService budgetService = new BudgetService();

    public static void main(String[] args) throws DuplicateEmailException, ValidationException, DatabaseException, BankAccountNotFoundException, ExpenseNotFoundException {

        while (true) {

            if (!userService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    // ================= LOGIN MENU =================

    public static void showLoginMenu() throws DuplicateEmailException, DatabaseException, ValidationException {

        System.out.println("\n========== FINANCIAL PILOT AI ==========");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        System.out.print("Enter Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                userService.registerUser(scanner);
                break;

            case 2:
                try {
                    userService.loginUser(scanner);
                } catch (AuthenticationException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                System.out.println("Thank You!");
                System.exit(0);

            default:
                System.out.println("Invalid Choice");
        }
    }

    // ================= MAIN MENU =================

    public static void showMainMenu() throws BankAccountNotFoundException, ExpenseNotFoundException {

        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("Welcome " +
                userService.getCurrentUser().getName());

        System.out.println("1. Bank Accounts");
        System.out.println("2. Expenses");
        System.out.println("3. Budget");
        System.out.println("4. Analytics");
        System.out.println("5. Current User");
        System.out.println("6. Logout");
        System.out.println("7. Exit");

        System.out.print("Enter Choice: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                showBankMenu();
                break;

            case 2:
                showExpenseMenu();
                break;

            case 3:
                budgetService.budgetMenu(scanner);
                break;
                
            case 4:
                userService.showCurrentUser();
                break;

            case 5:
                expenseService.analyticsMenu(scanner);
                 break;

            case 6:
                userService.logoutUser();
                break;

            case 7:
                System.exit(0);

            default:
                System.out.println("Invalid Choice");
        }
    }

    // ================= BANK MENU =================

    public static void showBankMenu() throws BankAccountNotFoundException {

        while (true) {

            System.out.println("\n========== BANK MENU ==========");
            System.out.println("1. Add Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Update Account");
            System.out.println("4. Delete Account");
            System.out.println("5. Back");

            System.out.print("Enter Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    bankAccountService.addBankAccount(scanner);
                    break;

                case 2:
                    bankAccountService.viewBankAccounts();
                    break;

                case 3:
                    bankAccountService.updateBankAccount(scanner);
                    break;

                case 4:
                    bankAccountService.deleteBankAccount(scanner);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // ================= EXPENSE MENU =================

    public static void showExpenseMenu() throws ExpenseNotFoundException {

        while (true) {

            System.out.println("\n========== EXPENSE MENU ==========");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Back");

            System.out.print("Enter Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    expenseService.addExpense(scanner);
                    break;

                case 2:
                    expenseService.viewExpenses();
                    break;

                case 3:
                    expenseService.updateExpense(scanner);
                    break;

                case 4:
                    expenseService.deleteExpense(scanner);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}