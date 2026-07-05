package com.financialpilot.service;

import java.util.Scanner;

import com.financialpilot.database.UserDAO;
import com.financialpilot.model.User;
import com.financialpilot.util.InputValidator;

public class UserService {

    private User currentUser;
    private InputValidator validator = new InputValidator();
    // Register User
    public void registerUser(Scanner scanner) {

        User user = new User();

        user.setName(validator.readNonEmptyString(scanner, "Enter Name: "));

        user.setEmail(validator.readEmail(scanner, "Enter Email: "));

        user.setPassword(validator.readPassword(scanner, "Enter Password: "));

        boolean success = UserDAO.registerUser(user);

        if (success) {
            System.out.println("\n✅ User registered successfully!");
        } else {
            System.out.println("\n❌ Registration failed.");
        }
    }

    // Login User
    public void loginUser(Scanner scanner) {

      
        String email = validator.readEmail(scanner, "Enter Email: ");

        String password = validator.readPassword(scanner, "Enter Password: ");

        currentUser = UserDAO.login(email, password);

        if (currentUser != null) {
            System.out.println("\n✅ Login Successful!");
            System.out.println("Welcome, " + currentUser.getName() + "!");
        } else {
            System.out.println("\n❌ Invalid email or password.");
        }
    }

    // Logout User
    public void logoutUser() {

        if (currentUser != null) {
            System.out.println("Goodbye, " + currentUser.getName() + "!");
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    // Get Current Logged-in User
    public User getCurrentUser() {
        return currentUser;
    }

    // Check Login Status
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Display Current User
    public void showCurrentUser() {

        if (currentUser != null) {
            System.out.println("\nCurrent User");
            System.out.println("----------------------");
            System.out.println("User ID : " + currentUser.getUserId());
            System.out.println("Name    : " + currentUser.getName());
            System.out.println("Email   : " + currentUser.getEmail());
        } else {
            System.out.println("No user is logged in.");
        }
    }
}