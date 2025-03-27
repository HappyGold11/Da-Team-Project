package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Frontend {
    /**
     * The Frontend class constructs the graphical user interface for the F1 database application.
     * It sets up the main JFrame, builds interactive tabs for viewing driver and team data,
     * and allows users to filter results and manage bookmarks through search and toggle functions.
     */

    // Backend instance to fetch and manage data
    Backend backend = new Backend();


    // GUI Components
    public DefaultTableModel driverTableModel;
    public DefaultTableModel teamTableModel;
    public JTable driverTable;
    public JTable teamTable;
    public JTextField searchBar;

    // Flags to indicate whether only bookmarked items are shown
    public boolean showBookmarkedDriversOnly = false;
    public boolean showBookmarkedTeamsOnly = false;

    // The main tabbed interface
    private JTabbedPane tabbedPane;

    /**
     * Constructs the main GUI window including the top control panel, tabbed content,
     * and search bar. Initializes and displays the frame.
     */
    public Frontend() {
        JFrame frame = new JFrame("F1 Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Set window icon from resources
        ImageIcon logo = new ImageIcon(getClass().getResource("/img/logo.png"));
        frame.setIconImage(logo.getImage());

        // Construct and add the main components
        JPanel topPanel = buildTopPanel(frame);
        tabbedPane = buildTabbedPane();
        JPanel searchPanel = buildSearchPanel(tabbedPane);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(searchPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Builds the top panel containing control buttons.
     */
    private JPanel buildTopPanel(JFrame frame) {
        JPanel topPanel = new JPanel();

        JButton bookmarkToggleButton = new JButton("Show Bookmarked");
        JButton button3 = new JButton("Settings");
        JButton button4 = new JButton("Login");
        JButton button5 = new JButton("Register");

        topPanel.add(bookmarkToggleButton);
        topPanel.add(button3);
        topPanel.add(button4);
        topPanel.add(button5);

        // Register button actions for login and register dialogs
        button4.addActionListener(e -> loginDialog());
        button5.addActionListener(e -> registerDialog());

        // Toggle between showing all entries and bookmarked entries
        bookmarkToggleButton.addActionListener(e -> {
            boolean showingBookmarks = !showBookmarkedDriversOnly;
            showBookmarkedDriversOnly = showingBookmarks;
            showBookmarkedTeamsOnly = showingBookmarks;

            bookmarkToggleButton.setText(showingBookmarks ? "Show All" : "Show Bookmarked");

            int selectedTab = tabbedPane.getSelectedIndex();
            if (selectedTab == 1) {
                loadDriverData(showBookmarkedDriversOnly);
            } else if (selectedTab == 2) {
                loadTeamData(showBookmarkedTeamsOnly);
            }
        });

        return topPanel;
    }

    /**
     * Creates and configures the tabbed pane with "Home", "Drivers", and "Teams" tabs.
     */
    private JTabbedPane buildTabbedPane() {
        tabbedPane = new JTabbedPane();

        // Home tab placeholder
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Homepage"));

        // Drivers tab setup
        TableBundle driverBundle = DriverPanelFactory.create(backend, this);
        driverTable = driverBundle.table;
        driverTableModel = driverBundle.model;

        // Teams tab setup
        TableBundle teamBundle = TeamPanelFactory.create(backend, this);
        teamTable = teamBundle.table;
        teamTableModel = teamBundle.model;

        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Drivers", driverBundle.panel);
        tabbedPane.addTab("Teams", teamBundle.panel);

        // Update table content depending on selected tab
        tabbedPane.addChangeListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex();
            if (selectedTab == 1) loadDriverData(showBookmarkedDriversOnly);
            if (selectedTab == 2) loadTeamData(showBookmarkedTeamsOnly);
        });

        return tabbedPane;
    }

    /**
     * Builds the search bar and associated button.
     */
    private JPanel buildSearchPanel(JTabbedPane tabbedPane) {
        JPanel search = new JPanel();
        searchBar = new JTextField(10);
        JButton searchButton = new JButton("Search");

        search.add(searchBar);
        search.add(searchButton);

        // Trigger appropriate search depending on active tab
        searchButton.addActionListener(e -> callSearch(searchBar.getText(), tabbedPane.getSelectedIndex()));
        return search;
    }

    /**
     * Routes search input to either the driver or team search method.
     */
    private void callSearch(String text, int selectedTab) {
        if (selectedTab == 1) searchDrivers(text);
        else if (selectedTab == 2) searchTeams(text);
    }

    /**
     * Filters driver list based on query and updates the table.
     */
    public void searchDrivers(String query) {
        List<String> filteredDrivers = backend.searchDrivers(query);
        driverTableModel.setRowCount(0); // Clear existing rows

        for (String line : filteredDrivers) {
            if (line.toLowerCase().contains("name,team")) continue; // Skip header line
            String[] rawRow = line.split(",");
            String[] row = Arrays.stream(rawRow).map(String::trim).toArray(String[]::new);
            driverTableModel.addRow(row);
        }
    }

    /**
     * Filters team list based on query and updates the table.
     */
    public void searchTeams(String query) {
        List<String> filteredTeams = backend.searchTeams(query);
        teamTableModel.setRowCount(0); // Clear existing rows

        for (String team : filteredTeams) {
            teamTableModel.addRow(new Object[]{team});
        }
    }

    /**
     * Loads and displays driver data in the table, filtered optionally by bookmark.
     */
    public void loadDriverData(boolean onlyBookmarked) {
        driverTableModel.setRowCount(0);

        for (String line : backend.getDrivers()) {
            if (line.toLowerCase().contains("name,team")) continue;
            String[] row = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
            if (!onlyBookmarked || backend.isDriverBookmarked(row[0])) {
                driverTableModel.addRow(row);
            }
        }
    }

    /**
     * Loads and displays team data in the table, filtered optionally by bookmark.
     */
    public void loadTeamData(boolean onlyBookmarked) {
        teamTableModel.setRowCount(0);

        for (String team : backend.getTeams()) {
            if (!onlyBookmarked || backend.isTeamBookmarked(team)) {
                teamTableModel.addRow(new Object[]{team});
            }
        }
    }

    /**
     * Displays a simple login form in a dialog.
     */
    private static void loginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};

        JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Displays a simple registration form in a dialog.
     */
    private static void registerDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};

        JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        new Frontend();
    }
}
