package com.example;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles user authentication for the F1 database UI.
 * This includes user registration, login, logout, and session tracking.
 */
public class LoginManager {
    private static final String LOGIN_FILE = "Bookmarks/login_data.csv"; // Storage file for user credentials
    private final Map<String, String> credentials = new HashMap<>();     // In-memory credential store
    private String currentUser = null;                                    // Active session user

    /**
     * Constructor that loads existing credentials from file.
     */
    public LoginManager() {
        loadCredentials();
    }

    /**
     * Loads user credentials from the login file into memory.
     */
    private void loadCredentials() {
        try {
            Files.lines(Paths.get(LOGIN_FILE)).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            });
        } catch (IOException e) {
            System.out.println("Could not load login data: " + e.getMessage());
        }
    }

    /**
     * Persists a new username and hashed password to the login file.
     */
    private void saveCredential(String username, String hashedPassword) {
        try (FileWriter writer = new FileWriter(LOGIN_FILE, true)) {
            writer.write(username + "," + hashedPassword + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a new user.
     * return true if successful, false if username already exists
     */
    public boolean register(String username, String password) {
        if (credentials.containsKey(username)) {
            return false;
        }
        String hashed = hashPassword(password);
        credentials.put(username, hashed);
        saveCredential(username, hashed);
        return true;
    }

    /**
     * Attempts to log in with the provided credentials.
     * return true if successful
     */
    public boolean login(String username, String password) {
        boolean success = credentials.containsKey(username) &&
                credentials.get(username).equals(hashPassword(password));
        if (success) {
            currentUser = username;
        }
        return success;
    }

    /**
     * Returns the currently logged-in user, or null.
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Securely hashes the given password using SHA-256.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays a login dialog and attempts login.
     */
    public static void showLoginDialog(LoginManager manager) {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] fields = {"Username:", usernameField, "Password:", passwordField};

        int option = JOptionPane.showConfirmDialog(null, fields, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (manager.login(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials.");
            }
        }
    }

    /**
     * Displays a registration dialog and attempts account creation.
     */
    public static void showRegisterDialog(LoginManager manager) {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] fields = {"Username:", usernameField, "Password:", passwordField};

        int option = JOptionPane.showConfirmDialog(null, fields, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (manager.register(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists.");
            }
        }
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        currentUser = null;
    }
}
