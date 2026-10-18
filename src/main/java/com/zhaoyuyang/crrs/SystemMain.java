package com.zhaoyuyang.crrs;

import com.zhaoyuyang.crrs.entity.User;
import com.zhaoyuyang.crrs.exception.*;
import com.zhaoyuyang.crrs.uitl.FileUtil;

import java.util.*;

public class SystemMain {
    // system info
    public static final String SYSTEM_NAME = "Campus Resource Reservation System";

    private List<User> users = FileUtil.readUsers();

    private static final Scanner scanner = new Scanner(System.in);

    private static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    private static boolean isExist(List<User> userAccounts, String input) {
        return userAccounts.stream().anyMatch(
                user -> user.getUsername().equals(input)
        );
    }

    private static void passwordGuard(String password) {
        if (isEmpty(password)) {
            throw new ParamEmptyException("Password is empty.");
        }
        if (password.length() < 6 || password.length() > 20) {
            throw new BusinessException("Password must be between 6-20 characters long.");
        }
    }

    private static void usernameGuard(String username) {
        if (isEmpty(username)) {
            throw new ParamEmptyException("Username is empty.");
        }
        if (username.length() < 4 || username.length() > 12) {
            throw new BusinessException("Username must be between 4-12 characters long.");
        }
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

    private String getUsernameInput() {
            String username = prompt("Username(4-12 characters): ");
            usernameGuard(username);
            return username;
    }

    private String getPasswordInput() {
            String password = prompt("Password(6-20 characters): ");
            passwordGuard(password);
            return password;
    }

    private void login() {
        String username = getUsernameInput();
        String password = getPasswordInput();
        if (!isExist(users, username)) {
            throw new UserNotExistsException("User " + username + " not exist.");
        }
        if (!passwordMatchesUsername(users, username, password)) {
            throw new PasswordIncorrectException("Password not match username " + username);
        }
             System.out.println("Login success. Welcome " + username + "!");
    }

     private void register() {
        String username = getUsernameInput();
        String password = getPasswordInput();
        if (isExist(users, username)) {
            throw new UserAlreadyExistsException("User " + username + " already exist.");
            }
        User newUser = new User(username, password, "student");
        users.add(newUser);
        FileUtil.writeUser(newUser);
        System.out.println("Register success.");
    }

    private void login(int tryCount) {
        for (int i = 0; i < tryCount; i++) {
            try {
                login();
                return;
            } catch (BusinessException e) {
                System.out.println("[Login try " + i + " / " + tryCount + "] "
                        + e.getMessage());
            }
        }
    }

    private void register(int tryCount) {
        for (int i = 0; i < tryCount; i++) {
            try {
                register();
                return;
            } catch (BusinessException e) {
                System.out.println("[Register try " + i + " / " + tryCount + "] "
                        + e.getMessage());
            }
       }
   }

    public static void main(String[] args) {
        System.out.println("This system is " + SYSTEM_NAME);
        System.out.println("Menu:");

        SystemMain menu = new SystemMain();
        menu.users = FileUtil.readUsers();

        while(true) {
            try {
                String choice = prompt("0. Exit\n1. Login\n2. Register\n");
                switch (choice) {
                    case "0":
                        FileUtil.writeUsers(menu.users);
                        System.exit(0);
                        break;
                    case "1":
                        menu.login(3);
                        break;
                    case "2":
                        menu.register(3);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
