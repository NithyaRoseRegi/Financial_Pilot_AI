package com.financialpilot.service;

import java.util.Scanner;

import com.financialpilot.model.User;
import com.financialpilot.util.InputValidator;

public class UserService {
    

    private final InputValidator validator = new InputValidator();

   public User createUser(Scanner scanner) {

    User user = new User();

    user.setName(
            validator.readNonEmptyString(
                    scanner,
                    "Enter Name: "));

    user.setAge(
            validator.readPositiveInt(
                    scanner,
                    "Enter Age: "));

    user.setEmail(
            validator.readEmail(
                    scanner,
                    "Enter Email: "));

    user.setMonthlyIncome(
            validator.readPositiveDouble(
                    scanner,
                    "Enter Monthly Income: "));

    return user;
}

    public void displayUser(User user) {
        user.displayProfile();
    }
}