package com.financialpilot.service;

import java.util.Scanner;

import com.financialpilot.model.BankAccount;
import com.financialpilot.util.InputValidator;

public class BankAccountService {
    private final InputValidator validator = new InputValidator();
    
    public BankAccount createBankAccount(Scanner scanner) {        
    BankAccount account = new BankAccount();
    account.setAccountNumber(
            validator.readNonEmptyString(
                    scanner,
                    "Enter Account Number: "));

    account.setBankName(
            validator.readNonEmptyString(
                    scanner,
                    "Enter Bank Name: "));

    account.setBalance(
            validator.readPositiveDouble(
                    scanner,
                    "Enter Initial Balance: "));

   

        return account;
    }

    public void displayAccount(BankAccount account) {
        account.displayAccount();
    }
}