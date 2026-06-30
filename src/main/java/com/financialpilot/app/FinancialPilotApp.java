package com.financialpilot.app;

import java.util.ArrayList;
import java.util.Scanner;

import com.financialpilot.model.BankAccount;
import com.financialpilot.model.Expense;
import com.financialpilot.model.User;
import com.financialpilot.service.BankAccountService;
import com.financialpilot.service.ExpenseService;
import com.financialpilot.service.SummaryService;
import com.financialpilot.service.UserService;

public class FinancialPilotApp {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

        UserService userService = new UserService();
        BankAccountService accountService = new BankAccountService();
        ExpenseService expenseService = new ExpenseService();
        SummaryService summaryService = new SummaryService();
        
        User user = null;
        BankAccount account = null;
        ArrayList<Expense> expenses = new ArrayList<>();

        boolean running = true;

        System.out.println("=======================================");
        System.out.println("      Welcome to Financial Pilot AI");
        System.out.println("=======================================");

        while (running) {

            displayMenu();

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    user = userService.createUser(scanner);
                    userService.displayUser(user);
                    break;

                case 2:

                    if (user == null) {
                        System.out.println("\nPlease create a user first.");
                    } else {
                        account = accountService.createBankAccount(scanner);
                        accountService.displayAccount(account);
                    }

                    break;

                case 3:

                    if (user == null) {
                        System.out.println("\nPlease create a user first.");
                    } else {

                        expenses = expenseService.createExpenses(scanner);

                    }

                    break;

                case 4:

                    if (expenses.isEmpty()) {

                        System.out.println("\nNo expenses available.");

                    } else {

                        expenseService.displayExpenses(expenses);

                    }

                    break;
                
                
                case 6:

                    if (expenses.isEmpty()) {

                        System.out.println("\nNo expenses available to delete.");

                    } else {

                        expenseService.deleteExpense(expenses, scanner);

                    }

                    break;

                case 7:

                summaryService.displaySummary(
                        user,
                        account,
                        expenses);

                        break;

                case 8:

                    running = false;

                    System.out.println("\nThank you for using Financial Pilot AI!");
                    break;

                             
                default:

                    System.out.println("\nInvalid choice.");

               }

            }

        }

       
    }

   private static void displayMenu() {

    System.out.println("\n============= Financial Pilot AI =============");
    System.out.println("1. Create User");
    System.out.println("2. Create Bank Account");
    System.out.println("3. Add Expense");
    System.out.println("4. View Expenses");
    System.out.println("5. Update Expense");
    System.out.println("6. Delete Expense");
    System.out.println("7. View Financial Summary");
    System.out.println("8. Exit");
    System.out.println("==============================================");
    }

}