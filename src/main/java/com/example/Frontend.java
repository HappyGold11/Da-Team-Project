package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Frontend {
    Backend backend = new Backend();
    private DefaultListModel<String> driverListModel;
    private DefaultListModel<String> teamListModel;
    private JTextField searchBar;
    private JList<String> driverList; 
    private JList<String> teamList;

    public Frontend() {
        // Create the main frame
        JFrame frame = new JFrame("F1 Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Create a panel for the buttons at the top
        JPanel topPanel = new JPanel();
        JButton button1 = new JButton("Bookmarked Drivers");
        JButton button2 = new JButton("Bookmarked Teams");
        JButton button3 = new JButton("Settings");
        JButton button4 = new JButton("Login");
        JButton button5 = new JButton("Register");

        topPanel.add(button1);
        topPanel.add(button2);
        topPanel.add(button3);
        topPanel.add(button4);
        topPanel.add(button5);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Home Panel
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Welcome to the Homepage"));

        // Drivers Panel
        JPanel panel2 = new JPanel();
        driverListModel = new DefaultListModel<>();
        driverList = new JList<>(driverListModel);
        panel2.setLayout(new BorderLayout());
        panel2.add(new JScrollPane(driverList), BorderLayout.CENTER);
        for (String driver : backend.getDrivers()) {
            driverListModel.addElement(driver);
        }

        // Teams Panel
        JPanel panel3 = new JPanel();
        teamListModel = new DefaultListModel<>();
        teamList = new JList<>(teamListModel);
        panel3.setLayout(new BorderLayout());
        panel3.add(new JScrollPane(teamList), BorderLayout.CENTER);
        for (String team : backend.getTeams()) {
            teamListModel.addElement(team);
        }

        // Add tabs
        tabbedPane.addTab("Home", panel1);
        tabbedPane.addTab("Drivers", panel2);
        tabbedPane.addTab("Teams", panel3);

        // Search Panel
        JPanel search = new JPanel();
        searchBar = new JTextField(10);
        JButton searchButton = new JButton("Search");
        search.add(searchBar);
        search.add(searchButton);

        // Search button logic
        searchButton.addActionListener(e -> {
            callSearch(searchBar.getText(), tabbedPane.getSelectedIndex());
        });

        // Add components to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(search, BorderLayout.SOUTH);

        // Action listeners for Login & Register
        button4.addActionListener(e -> loginDialog());
        button5.addActionListener(e -> registerDialog());

        // Make frame visible
        frame.setVisible(true);
    }

    // Show Register Dialog
    private static void registerDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
    }

    // Show Login Dialog
    private static void loginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
    }

    // Calls search for the correct tab
    private void callSearch(String text, int selectedTab) {
        if (selectedTab == 1) {
            searchDrivers(text);
        } else if (selectedTab == 2) {
            searchTeams(text);
        }
    }

    // Search function for teams
    public void searchTeams(String query) {
        List<String> filteredTeams = backend.searchTeams(query); 
        teamListModel.clear();
        for (String team : filteredTeams) {
            teamListModel.addElement(team);
        }
    }

    // Search function for drivers
    public void searchDrivers(String query) {
        List<String> filteredDrivers = backend.searchDrivers(query); 
        driverListModel.clear();
        for (String driver : filteredDrivers) {
            driverListModel.addElement(driver);
        }
    }

    public static void main(String[] args) {
        new Frontend();
    }
}


