package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

public class Frontend {
    Backend backend = new Backend();

    private DefaultTableModel driverTableModel;
    private DefaultTableModel teamTableModel;
    private JTable driverTable;
    private JTable teamTable;

    private JTextField searchBar;

    public Frontend() {
        JFrame frame = new JFrame("F1 Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top Navigation Buttons
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

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Home Panel
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Welcome to the Homepage"));

        // Drivers Panel with Table and SplitPane
        JPanel panel2 = new JPanel(new BorderLayout());

        String[] driverColumns = {
                "Name", "Team", "Wins", "Podiums", "Salary",
                "Contract Ends", "Career Points", "Current Standing"
        };
        driverTableModel = new DefaultTableModel(driverColumns, 0);
        driverTable = new JTable(driverTableModel);
        styleTable(driverTable);

        // Detail panel
        JPanel detailPanel = new JPanel(new BorderLayout());
        JLabel detailLabel = new JLabel("Select a driver to see more details...");
        detailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailPanel.add(detailLabel, BorderLayout.CENTER);

        JScrollPane tableScrollPane = new JScrollPane(driverTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailPanel);
        splitPane.setDividerLocation(1000);
        splitPane.setResizeWeight(0.7);
        panel2.add(splitPane, BorderLayout.CENTER);

        // Load driver data
        List<String> allDrivers = backend.getDrivers();
        for (int i = 1; i < allDrivers.size(); i++) { // Skip header
            String[] rawRow = allDrivers.get(i).split(",");
            String[] row = Arrays.stream(rawRow).map(String::trim).toArray(String[]::new);
            driverTableModel.addRow(row);
        }

        // Add click listener to load headshot + details
        driverTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = driverTable.getSelectedRow();
                if (row >= 0) {
                    String driverName = (String) driverTableModel.getValueAt(row, 0);
                    String team = (String) driverTableModel.getValueAt(row, 1);
                    String wins = (String) driverTableModel.getValueAt(row, 2);
                    String podiums = (String) driverTableModel.getValueAt(row, 3);
                    String salary = (String) driverTableModel.getValueAt(row, 4);
                    String contractEnds = (String) driverTableModel.getValueAt(row, 5);
                    String points = (String) driverTableModel.getValueAt(row, 6);
                    String standing = (String) driverTableModel.getValueAt(row, 7);

                    detailPanel.removeAll();

                    String imagePath = "/img/Head_Shots/" + driverName + ".jpg";
                    java.net.URL imgURL = getClass().getResource(imagePath);

                    if (imgURL != null) {
                        ImageIcon originalIcon = new ImageIcon(imgURL);
                        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                        JTextArea detailText = new JTextArea();
                        detailText.setEditable(false);
                        detailText.setFont(new Font("Monospaced", Font.PLAIN, 13));
                        detailText.setText("Driver Details:\n\n"
                                + "Name: " + driverName + "\n"
                                + "Team: " + team + "\n"
                                + "Wins: " + wins + "\n"
                                + "Podiums: " + podiums + "\n"
                                + "Salary: " + salary + "\n"
                                + "Contract Ends: " + contractEnds + "\n"
                                + "Career Points: " + points + "\n"
                                + "Current Standing: " + standing + "\n");

                        JPanel contentPanel = new JPanel(new BorderLayout());
                        contentPanel.add(imageLabel, BorderLayout.WEST);
                        contentPanel.add(new JScrollPane(detailText), BorderLayout.CENTER);

                        detailPanel.add(contentPanel, BorderLayout.CENTER);
                    } else {
                        JLabel notFoundLabel = new JLabel("Headshot not found for " + driverName);
                        notFoundLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        detailPanel.add(notFoundLabel, BorderLayout.CENTER);
                    }

                    detailPanel.revalidate();
                    detailPanel.repaint();
                }
            }
        });

        // Teams Panel
        JPanel panel3 = new JPanel(new BorderLayout());
        String[] teamColumns = {"Team"};
        teamTableModel = new DefaultTableModel(teamColumns, 0);
        teamTable = new JTable(teamTableModel);
        styleTable(teamTable);
        panel3.add(new JScrollPane(teamTable), BorderLayout.CENTER);

        for (String team : backend.getTeams()) {
            teamTableModel.addRow(new Object[]{team});
        }

        // Add Tabs
        tabbedPane.addTab("Home", panel1);
        tabbedPane.addTab("Drivers", panel2);
        tabbedPane.addTab("Teams", panel3);

        JPanel search = new JPanel();
        searchBar = new JTextField(10);
        JButton searchButton = new JButton("Search");
        search.add(searchBar);
        search.add(searchButton);

        searchButton.addActionListener(e -> {
            callSearch(searchBar.getText(), tabbedPane.getSelectedIndex());
        });

        // Login/Register Actions
        button4.addActionListener(e -> loginDialog());
        button5.addActionListener(e -> registerDialog());

        // Final Frame Setup
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(search, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(220, 220, 220));
        header.setOpaque(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                } else {
                    c.setBackground(new Color(184, 207, 229));
                }
                return c;
            }
        });

        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    private static void loginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
    }

    private static void registerDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
    }

    private void callSearch(String text, int selectedTab) {
        if (selectedTab == 1) {
            searchDrivers(text);
        } else if (selectedTab == 2) {
            searchTeams(text);
        }
    }

    public void searchDrivers(String query) {
        List<String> filteredDrivers = backend.searchDrivers(query);
        driverTableModel.setRowCount(0);
        for (String line : filteredDrivers) {
            if (line.toLowerCase().contains("name,team")) continue;
            String[] rawRow = line.split(",");
            String[] row = Arrays.stream(rawRow).map(String::trim).toArray(String[]::new);
            driverTableModel.addRow(row);
        }
    }

    public void searchTeams(String query) {
        List<String> filteredTeams = backend.searchTeams(query);
        teamTableModel.setRowCount(0);
        for (String team : filteredTeams) {
            teamTableModel.addRow(new Object[]{team});
        }
    }

    public static void main(String[] args) {
        new Frontend();
    }
}
