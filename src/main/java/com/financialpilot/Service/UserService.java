package com.financialpilot.service;

import java.util.Scanner;
import java.util.ArrayList;


import com.financialpilot.exception.UserNotFoundException;
import com.financialpilot.database.UserDAO;
import com.financialpilot.exception.AuthenticationException;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.DuplicateEmailException;
import com.financialpilot.exception.ValidationException;
import com.financialpilot.model.User;
import com.financialpilot.util.InputValidator;
import com.financialpilot.util.PasswordUtil;

public class UserService {

    private User currentUser;
    private InputValidator validator = new InputValidator();
    private UserDAO userDAO = new UserDAO();
    // Register User
    public void registerUser(Scanner scanner) throws DuplicateEmailException, DatabaseException, ValidationException {

        User user = new User();

        user.setName(validator.readNonEmptyString(scanner, "Enter Name: "));

        user.setEmail(validator.readEmail(scanner, "Enter Email: "));

        user.setPassword(validator.readPassword(scanner, "Enter Password: "));

        if (UserDAO.emailExists(user.getEmail())) {
                throw new DuplicateEmailException("Email already registered.");
        }
        
        
        boolean success = UserDAO.registerUser(user);

        if (success) {
            System.out.println("\n✅ User registered successfully!");
        } else {
            System.out.println("\n❌ Registration failed.");
        }
    }

    public void registerUser(User user)
        throws DuplicateEmailException,
               DatabaseException,
               ValidationException {

    if (user.getName() == null || user.getName().trim().isEmpty()) {
        throw new ValidationException("Name cannot be empty.");
    }

    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
        throw new ValidationException("Email cannot be empty.");
    }

    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
        throw new ValidationException("Password cannot be empty.");
    }

    if (UserDAO.emailExists(user.getEmail())) {
        throw new DuplicateEmailException("Email already registered.");
    }

    boolean success = UserDAO.registerUser(user);

    if (!success) {
        throw new DatabaseException("Failed to register user.");
    }
}

    // Login User
    public void loginUser(Scanner scanner) throws AuthenticationException, ValidationException {

      
        String email = validator.readEmail(scanner, "Enter Email: ");

        String password = validator.readPassword(scanner, "Enter Password: ");

      
        User user = UserDAO.getUserByEmail(email);

       if (user == null) {
           throw new AuthenticationException("Invalid email or password.");
       }

       if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
              throw new AuthenticationException("Invalid email or password.");
        }

        if (PasswordUtil.verifyPassword(password, user.getPassword())) {

        currentUser = user;

        System.out.println("Login Successful!");
        System.out.println("Welcome, " + user.getName());

        } else {

        System.out.println("Invalid Password.");
        }
       
    }

    public User loginUser(String email,
                      String password)
        throws AuthenticationException {

    User user = UserDAO.getUserByEmail(email);

    if (user == null) {
        throw new AuthenticationException(
                "Invalid email or password.");
    }

    if (!PasswordUtil.verifyPassword(
            password,
            user.getPassword())) {

        throw new AuthenticationException(
                "Invalid email or password.");
    }

    currentUser = user;

    return user;
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
    public User getUserById(int userId)
        throws UserNotFoundException {

    User user = UserDAO.getUserById(userId);

    if (user == null) {

        throw new UserNotFoundException(
                "User not found.");
    }

    return user;
   }
   public ArrayList<User> getAllUsers() {

    return UserDAO.getAllUsers();
   }
   public void updateUser(User user)
        throws ValidationException,
               DatabaseException,
               UserNotFoundException {

    User existingUser =
            UserDAO.getUserById(user.getUserId());

    if (existingUser == null) {

        throw new UserNotFoundException(
                "User not found.");
    }

    if (user.getName() == null ||
        user.getName().trim().isEmpty()) {

        throw new ValidationException(
                "Name cannot be empty.");
    }

    if (user.getEmail() == null ||
        user.getEmail().trim().isEmpty()) {

        throw new ValidationException(
                "Email cannot be empty.");
    }

    if (user.getPassword() == null ||
        user.getPassword().trim().isEmpty()) {

        throw new ValidationException(
                "Password cannot be empty.");
    }

    boolean success =
            UserDAO.updateUser(user);

    if (!success) {

        throw new DatabaseException(
                "Unable to update user.");
    }
}
public void deleteUser(int userId)
        throws UserNotFoundException,
               DatabaseException {

    User user =
            UserDAO.getUserById(userId);

    if (user == null) {

        throw new UserNotFoundException(
                "User not found.");
    }

    boolean success =
            UserDAO.deleteUser(userId);

    if (!success) {

        throw new DatabaseException(
                "Unable to delete user.");
    }
}
}