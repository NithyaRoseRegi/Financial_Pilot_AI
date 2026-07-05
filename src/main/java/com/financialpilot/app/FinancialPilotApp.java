package com.financialpilot.app;

import java.util.Scanner;

import com.financialpilot.service.BankAccountService;
import com.financialpilot.service.ExpenseService;
import com.financialpilot.service.UserService;

public class FinancialPilotApp {

    private static Scanner scanner = new Scanner(System.in);

    private static UserService userService = new UserService();

    private static BankAccountService bankAccountService =
            new BankAccountService(userService);

    private static ExpenseService expenseService =
            new ExpenseService(userService, bankAccountService);

    public static void main(String[] args) {

        while (true) {

            if (!userService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    // ================= LOGIN MENU =================

    public static void showLoginMenu() {

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
                userService.loginUser(scanner);
                break;

            case 3:
                System.out.println("Thank You!");
                System.exit(0);

            default:
                System.out.println("Invalid Choice");
        }
    }

    // ================= MAIN MENU =================

    public static void showMainMenu() {

        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("Welcome " +
                userService.getCurrentUser().getName());

        System.out.println("1. Bank Accounts");
        System.out.println("2. Expenses");
        System.out.println("3. Current User");
        System.out.println("4. Logout");
        System.out.println("5. Exit");

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
                userService.showCurrentUser();
                break;

            case 4:
                userService.logoutUser();
                break;

            case 5:
                System.exit(0);

            default:
                System.out.println("Invalid Choice");
        }
    }

    // ================= BANK MENU =================

    public static void showBankMenu() {

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

    public static void showExpenseMenu() {

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