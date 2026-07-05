package com.financialpilot.service;

import java.util.ArrayList;
import java.util.Scanner;

import com.financialpilot.database.BankAccountDAO;
import com.financialpilot.model.BankAccount;
import com.financialpilot.model.User;
import com.financialpilot.util.InputValidator;

public class BankAccountService {

    private UserService userService;
    private InputValidator validator = new InputValidator();
    // Constructor
    public BankAccountService(UserService userService) {
        this.userService = userService;
    }

    // Add Bank Account
    public void addBankAccount(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        BankAccount account = new BankAccount();

        User currentUser = userService.getCurrentUser();

        account.setUserId(currentUser.getUserId());

       
        account.setBankName(validator.readNonEmptyString(scanner, "Enter Bank Name: "));

        account.setAccountNumber(validator.readNonEmptyString(scanner, "Enter Account Number: "));

        account.setAccountType(validator.readNonEmptyString(scanner, "Enter Account Type (Savings/Current): "));

        account.setBalance(validator.readDouble(scanner, "Enter Initial Balance: "));

        boolean success = BankAccountDAO.addBankAccount(account);

        if (success) {
            System.out.println("\nBank Account Added Successfully!");
        } else {
            System.out.println("\nFailed to Add Bank Account.");
        }
    }

    // View All Bank Accounts
    public void viewBankAccounts() {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        int userId = userService.getCurrentUser().getUserId();

        ArrayList<BankAccount> accounts =
                BankAccountDAO.getAllBankAccountsByUser(userId);

        if (accounts.isEmpty()) {
            System.out.println("\nNo bank accounts found.");
            return;
        }

        System.out.println("\n========== BANK ACCOUNTS ==========");

        for (BankAccount account : accounts) {

            System.out.println("Account ID     : " + account.getAccountId());
            System.out.println("Bank Name      : " + account.getBankName());
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Account Type   : " + account.getAccountType());
            System.out.println("Balance        : ₹" + account.getBalance());

            System.out.println("-----------------------------------");
        }
    }

    // Update Bank Account
    public void updateBankAccount(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        String accountId = validator.readNonEmptyString(scanner, "Enter Account ID: ");

        BankAccount account =
                BankAccountDAO.getBankAccountById(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (account.getUserId() != userService.getCurrentUser().getUserId()) {
            System.out.println("You cannot update another user's account.");
            return;
        }

        account.setBankName(validator.readNonEmptyString(scanner, "Enter New Bank Name: "));

        account.setAccountNumber(validator.readNonEmptyString(scanner, "Enter New Account Number: "));

        account.setAccountType(validator.readNonEmptyString(scanner, "Enter New Account Type: "));

        account.setBalance(validator.readDouble(scanner, "Enter New Balance: "));

        boolean success = BankAccountDAO.updateBankAccount(account);

        if (success) {
            System.out.println("\nBank Account Updated Successfully!");
        } else {
            System.out.println("\nUpdate Failed.");
        }
    }

    // Delete Bank Account
    public void deleteBankAccount(Scanner scanner) {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        String accountId = validator.readNonEmptyString(scanner, "Enter Account ID: ");

        BankAccount account =
                BankAccountDAO.getBankAccountById(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (account.getUserId() != userService.getCurrentUser().getUserId()) {
            System.out.println("You cannot delete another user's account.");
            return;
        }

        boolean success =
                BankAccountDAO.deleteBankAccount(accountId);

        if (success) {
            System.out.println("\nBank Account Deleted Successfully!");
        } else {
            System.out.println("\nDeletion Failed.");
        }
    }
}