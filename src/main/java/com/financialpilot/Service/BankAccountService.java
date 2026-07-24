package com.financialpilot.service;

import java.util.ArrayList;
import java.util.Scanner;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.ValidationException;
import com.financialpilot.database.BankAccountDAO;
import com.financialpilot.exception.BankAccountNotFoundException;
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
                BankAccountDAO.getAllAccountsByUser(userId);

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
    public void deleteBankAccount(Scanner scanner) throws BankAccountNotFoundException {

        if (!userService.isLoggedIn()) {
            System.out.println("\nPlease login first.");
            return;
        }

        String accountId = validator.readNonEmptyString(scanner, "Enter Account ID: ");

        BankAccount account =
                BankAccountDAO.getBankAccountById(accountId);

        if (account == null) {
            throw new BankAccountNotFoundException(
        "Bank Account not found.");
           
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
    public void addBankAccount(BankAccount account)
        throws ValidationException,
               DatabaseException {

    if (account.getBankName() == null ||
        account.getBankName().trim().isEmpty()) {

        throw new ValidationException(
                "Bank name cannot be empty.");
    }

    if (account.getAccountNumber() == null ||
        account.getAccountNumber().trim().isEmpty()) {

        throw new ValidationException(
                "Account number cannot be empty.");
    }

    if (account.getAccountType() == null ||
        account.getAccountType().trim().isEmpty()) {

        throw new ValidationException(
                "Account type cannot be empty.");
    }

    if (account.getBalance() < 0) {

        throw new ValidationException(
                "Balance cannot be negative.");
    }

    boolean success =
            BankAccountDAO.addBankAccount(account);

    if (!success) {

        throw new DatabaseException(
                "Unable to save bank account.");
    }
}
public BankAccount getBankAccountById(int accountId)
        throws BankAccountNotFoundException {

    BankAccount account =
            BankAccountDAO.getBankAccountById(String.valueOf(accountId));

    if (account == null) {

        throw new BankAccountNotFoundException(
                "Bank account not found.");
    }

    return account;
}
public ArrayList<BankAccount> getAllAccountsByUser(int userId) {

    return BankAccountDAO.getAllAccountsByUser(userId);
}
public void updateBankAccount(BankAccount account)
        throws ValidationException,
               DatabaseException,
               BankAccountNotFoundException {

    BankAccount existing =
            BankAccountDAO.getBankAccountById(
                    String.valueOf(account.getAccountId()));

    if (existing == null) {

        throw new BankAccountNotFoundException(
                "Bank account not found.");
    }

    if (account.getBankName() == null ||
        account.getBankName().trim().isEmpty()) {

        throw new ValidationException(
                "Bank name cannot be empty.");
    }

    boolean success =
            BankAccountDAO.updateBankAccount(account);

    if (!success) {

        throw new DatabaseException(
                "Unable to update bank account.");
    }
}
public void deleteBankAccount (String accountId)
        throws BankAccountNotFoundException,
               DatabaseException {

    BankAccount account =
            BankAccountDAO.getBankAccountById(accountId);

    if (account == null) {

        throw new BankAccountNotFoundException(
                "Bank account not found.");
    }

    boolean success =
            BankAccountDAO.deleteBankAccount(
                    accountId);

    if (!success) {

        throw new DatabaseException(
                "Unable to delete bank account.");
    }
}    
}