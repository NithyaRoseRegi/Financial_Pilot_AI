package com.financialpilot.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {

    public String readNonEmptyString(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    public int readInt(Scanner scanner, String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public double readDouble(Scanner scanner, String message) {

        while (true) {

            try {

                System.out.print(message);

                double value = Double.parseDouble(scanner.nextLine());

                if (value < 0) {

                    System.out.println("Value cannot be negative.");

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    public LocalDate readDate(Scanner scanner, String message) {

        while (true) {

            try {

                System.out.print(message);

                return LocalDate.parse(scanner.nextLine());

            } catch (DateTimeParseException e) {

                System.out.println("Date must be in YYYY-MM-DD format.");
            }
        }
    }

    public String readEmail(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String email = scanner.nextLine().trim();

            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                return email;
            }

            System.out.println("Invalid email.");
        }
    }

    public String readPassword(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String password = scanner.nextLine();

            if (password.length() >= 6) {

                return password;
            }

            System.out.println("Password must contain at least 6 characters.");
        }
    }

    public boolean confirm(Scanner scanner, String message) {

        while (true) {

            System.out.print(message + " (Y/N): ");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Y"))
                return true;

            if (input.equalsIgnoreCase("N"))
                return false;

            System.out.println("Please enter Y or N.");
        }
    }
}