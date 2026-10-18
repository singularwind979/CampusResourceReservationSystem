package com.zhaoyuyang.crrs;

import com.zhaoyuyang.crrs.entity.User;
import com.zhaoyuyang.crrs.uitl.FileUtil;

import java.util.*;

public class SystemMain {
    // system info
    public static final String SYSTEM_NAME = "Campus Resource Reservation System";

    private List<User> users = FileUtil.readUsers();

    private static final Scanner scanner = new Scanner(System.in);

    private static boolean isNotEmpty(String input) {
        return input != null && !input.isEmpty();
    }

    private static boolean isRightLength(String input, int minLength, int maxLength) {
        return input.length() >= minLength && input.length() <= maxLength;
    }

    private static boolean isExist(List<User> userAccounts, String input) {
        return userAccounts.stream().anyMatch(
                user -> user.getUsername().equals(input)
        );
    }

    private static boolean isValidPassword(String password) {
        return isNotEmpty(password) && isRightLength(password, 6, 20);
    }

    private static boolean isValidUsername(String username) {
        return isNotEmpty(username) && isRightLength(username, 4, 12);
    }

    private static boolean passwordMatchesUsername(List<User> userAccounts, String username, String password) {
        return userAccounts.stream().anyMatch(
                user -> user.getUsername().equals(username)
                            && user.getPassword().equals(password)
        );
    }

    private static String prompt(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String loginUsername() {
        while (true) {
            String username = prompt("Username(4-12 characters): ");
            if (isValidUsername(username) && isExist(users, username)) {
                return username;
            }
            System.out.println("Username not valid or not exist.");
        }
    }

    private String loginPassword() {
        while (true) {
            String password = prompt("Password(6-20 characters): ");
            if (isValidPassword(password)) {
                return password;
            }
            System.out.println("Password not valid.");
        }
    }

     private void login() {
         for (int i = 0; i < 3; i++) {
             String username = loginUsername();
             String password = loginPassword();
             if (passwordMatchesUsername(users, username, password)) {
                 System.out.println("Login success. Welcome " + username + "!");
                 return;
             }
             System.out.println("Password not match username.");
         }
         System.out.println("Login failed.");
     }

    private String registerUsername() {
        while (true) {
            String username = prompt("Username(4-12 characters): ");
            if (isValidUsername(username) && !isExist(users, username)) {
                return username;
            }
            System.out.println("Username not valid or already exist.");
        }
     }

     private String registerPassword() {
        while (true) {
            String password = prompt("Password(6-20 characters): ");
            if (isValidPassword(password)) {
                return password;
            }
            System.out.println("Password not valid.");
        }
     }

     private void register() {
        String username = registerUsername();
        String password = registerPassword();
        User newUser = new User(username, password, "student");
        users.add(newUser);
        FileUtil.writeUser(newUser);
        System.out.println("Register success.");
     }

    public static void main(String[] args) {
        System.out.println("This system is " + SYSTEM_NAME);
        System.out.println("Menu:");

        SystemMain menu = new SystemMain();
        menu.users = FileUtil.readUsers();
        while(true) {
            String choice = prompt("0. Exit\n1. Login\n2. Register\n");
            switch (choice) {
                case "0": FileUtil.writeUsers(menu.users); System.exit(0); break;
                case "1": menu.login(); break;
                case "2": menu.register(); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }
}
