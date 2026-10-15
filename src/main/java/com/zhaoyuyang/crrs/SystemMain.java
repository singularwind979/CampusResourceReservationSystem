package com.zhaoyuyang.crrs;

import java.util.Scanner;

public class SystemMain {
    // system info
    public static final String SYSTEM_NAME = "Campus Resource Reservation System";

    // admin test account
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123456";

    // user test account
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "123456";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("This system is " + SYSTEM_NAME);
            System.out.println("Please login to continue.");

            System.out.print("Username please:");
            String username = scanner.nextLine();

            System.out.print("Password please:");
            String password = scanner.nextLine();

            if (ADMIN_USERNAME.equals(username)
                && ADMIN_PASSWORD.equals(password)) {
                System.out.println("Welcome, admin!");
            } else if (USER_USERNAME.equals(username)
                && USER_PASSWORD.equals(password)) {
                System.out.println("Welcome, user!");
            } else {
                System.out.println("Login failed.");
            }
        }
    }
}
