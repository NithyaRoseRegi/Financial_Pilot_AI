package com.financialpilot.util;

import java.util.Scanner;

public class InputValidator {

    public int readPositiveInt(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            if (scanner.hasNextInt()) {

                int value = scanner.nextInt();
                scanner.nextLine();

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than zero.");
            } else {

                System.out.println("Please enter a valid integer.");
                scanner.nextLine();

            }

        }

    }

    public double readPositiveDouble(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            if (scanner.hasNextDouble()) {

                double value = scanner.nextDouble();
                scanner.nextLine();

                if (value >= 0) {
                    return value;
                }

                System.out.println("Value cannot be negative.");

            } else {

                System.out.println("Please enter a valid number.");
                scanner.nextLine();

            }

        }

    }

    public String readNonEmptyString(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");

        }

    }

    public String readEmail(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String email = scanner.nextLine().trim();

            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                return email;

            }

            System.out.println("Invalid email address.");

        }

    }

}
