package com.zhaoyuyang.crrs.uitl;

import com.zhaoyuyang.crrs.entity.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {
    private static final Path USER_FILE_PATH = Paths.get("data/user.txt");

    private FileUtil() {
    }

    public static List<User> readUsers() {
        if (!Files.exists(USER_FILE_PATH)) {
            try {
                Files.createFile(USER_FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error: failed to create user file. " + e.getMessage());
            }
        }

        List<User> users = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(USER_FILE_PATH)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    users.add(deserializeUser(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error: failed to read user file. " + e.getMessage());
        }
        return users;
    }

    public static void writeUser(User user) {
        try (BufferedWriter writer = Files.newBufferedWriter(USER_FILE_PATH,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(serializeUser(user));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error: failed to write user file. " + e.getMessage());
        }
    }

    public static void writeUsers(List<User> users) {
        try (BufferedWriter writer = Files.newBufferedWriter(USER_FILE_PATH,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (User user : users) {
                writer.write(serializeUser(user));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error: failed to write user file. " + e.getMessage());
        }
    }

    private static final String SEP = "\\|";
    private static final String USER_FORMAT = "%s|%s|%s|%s";

    private static String serializeUser(User user) {
        return String.format(
                USER_FORMAT,
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                serializeDateTime(user.getRegisterTime()));
    }

    private static User deserializeUser(String userStr) {
        String[] fields = userStr.split(SEP);
        return new User(
                fields[0],
                fields[1],
                fields[2],
                deserializeDateTime(fields[3]));
    }

    private static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String serializeDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMAT);
    }

    private static LocalDateTime deserializeDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMAT);
    }
}
