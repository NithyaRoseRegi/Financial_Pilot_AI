package com.financialpilot.model;

public class User {

    private String name;
    private int age;
    private String email;
    private double monthlyIncome;

    public User() {

    }

    public User(String name, int age, String email, double monthlyIncome) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.monthlyIncome = monthlyIncome;
    }

    public void displayProfile() {

        System.out.println("\n========== USER PROFILE ==========");

        System.out.println("Name            : " + name);
        System.out.println("Age             : " + age);
        System.out.println("Email           : " + email);
        System.out.println("Monthly Income  : $" + monthlyIncome);

    }

    public void displayProfile(boolean showIncome) {

    System.out.println("\n========== USER PROFILE ==========");
    System.out.println("Name           : " + name);
    System.out.println("Age            : " + age);
    System.out.println("Email          : " + email);

    if (showIncome) {
        System.out.println("Monthly Income : $" + monthlyIncome);
    }
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
}