package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Frontend {
    /**
     * Constructs the graphical interface for the F1 database app,
     * including theme colors, tab navigation, data loading, search, and bookmark filtering.
     */

    // Color Theme
    private final Color backgroundColor = new Color(20, 20, 20);
    private final Color foregroundColor = Color.WHITE;
    private final Color buttonColor = new Color(225, 6, 0);
    private final Color tableHeaderColor = new Color(30, 30, 30);

    // Backend for data management
    Backend backend = new Backend();

    // GUI Components
    public DefaultTableModel driverTableModel;
    public DefaultTableModel teamTableModel;
    public JTable driverTable;
    public JTable teamTable;
    public JTextField searchBar;
    public boolean showBookmarkedDriversOnly = false;
    public boolean showBookmarkedTeamsOnly = false;
    private JTabbedPane tabbedPane;

    /**
     * Sets up the main JFrame, builds tabbed content, top bar, and search panel.
     */
    public Frontend() {
        JFrame frame = new JFrame("F1 Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(backgroundColor);

        // Set application icon
        ImageIcon logo = new ImageIcon(getClass().getResource("/img/logo.png"));
        frame.setIconImage(logo.getImage());

        // Build and add components
        JPanel topPanel = buildTopPanel(frame);
        tabbedPane = buildTabbedPane();
        JPanel searchPanel = buildSearchPanel(tabbedPane);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(searchPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Creates the top button bar with toggle and user controls.
     */
    private JPanel buildTopPanel(JFrame frame) {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);

        JButton bookmarkToggleButton = createStyledButton("Show Bookmarked");
        JButton button3 = createStyledButton("Settings");
        JButton button4 = createStyledButton("Login");
        JButton button5 = createStyledButton("Register");

        topPanel.add(bookmarkToggleButton);
        topPanel.add(button3);
        topPanel.add(button4);
        topPanel.add(button5);

        button4.addActionListener(e -> loginDialog());
        button5.addActionListener(e -> registerDialog());

        bookmarkToggleButton.addActionListener(e -> {
            boolean showingBookmarks = !showBookmarkedDriversOnly;
            showBookmarkedDriversOnly = showingBookmarks;
            showBookmarkedTeamsOnly = showingBookmarks;

            bookmarkToggleButton.setText(showingBookmarks ? "Show All" : "Show Bookmarked");

            int selectedTab = tabbedPane.getSelectedIndex();
            if (selectedTab == 1) loadDriverData(showBookmarkedDriversOnly);
            else if (selectedTab == 2) loadTeamData(showBookmarkedTeamsOnly);
        });

        return topPanel;
    }

    /**
     * Utility to create red-themed buttons.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Builds the tabbed view for Home, Drivers, and Teams with custom styling.
     */
    private JTabbedPane buildTabbedPane() {
        tabbedPane = new JTabbedPane();

        // Custom tab UI to paint unselected tabs red
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? backgroundColor : Color.RED);
                g.fillRect(x, y, w, h);
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                                     int tabIndex, String title, Rectangle textRect, boolean isSelected) {
                g.setFont(font);
                g.setColor(Color.WHITE);
                g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }
        });

        JPanel homePanel = HomePanelFactory.create();
        TableBundle driverBundle = DriverPanelFactory.create(backend, this);
        driverTable = driverBundle.table;
        driverTableModel = driverBundle.model;
        styleTable(driverTable);

        TableBundle teamBundle = TeamPanelFactory.create(backend, this);
        teamTable = teamBundle.table;
        teamTableModel = teamBundle.model;
        styleTable(teamTable);

        FAQPage faqPage = new FAQPage();

        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Drivers", driverBundle.panel);
        tabbedPane.addTab("Teams", teamBundle.panel);
        tabbedPane.addTab("FAQ", faqPage.panel);

        tabbedPane.addChangeListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex();
            if (selectedTab == 1) loadDriverData(showBookmarkedDriversOnly);
            else if (selectedTab == 2) loadTeamData(showBookmarkedTeamsOnly);
        });

        return tabbedPane;
    }

    /**
     * Applies consistent styling to all tables.
     */
    private void styleTable(JTable table) {
        table.setBackground(backgroundColor);
        table.setForeground(foregroundColor);
        table.setSelectionBackground(Color.DARK_GRAY);
        table.setSelectionForeground(foregroundColor);
        table.setGridColor(Color.RED);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setBackground(tableHeaderColor);
        table.getTableHeader().setForeground(foregroundColor);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * Builds the search bar and search button.
     */
    private JPanel buildSearchPanel(JTabbedPane tabbedPane) {
        JPanel search = new JPanel();
        search.setBackground(backgroundColor);

        searchBar = new JTextField(10);
        searchBar.setBackground(Color.BLACK);
        searchBar.setForeground(foregroundColor);
        searchBar.setCaretColor(foregroundColor);

        JButton searchButton = createStyledButton("Search");

        search.add(searchBar);
        search.add(searchButton);

        searchButton.addActionListener(e -> callSearch(searchBar.getText(), tabbedPane.getSelectedIndex()));

        return search;
    }

    /**
     * Routes the search text to the appropriate tab.
     */
    private void callSearch(String text, int selectedTab) {
        if (selectedTab == 1) searchDrivers(text);
        else if (selectedTab == 2) searchTeams(text);
    }

    /**
     * Searches drivers and updates the driver table.
     */
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

    /**
     * Searches teams and updates the team table.
     */
    public void searchTeams(String query) {
        List<String> filteredTeams = backend.searchTeams(query);
        teamTableModel.setRowCount(0);
        for (String team : filteredTeams) {
            teamTableModel.addRow(new Object[]{team});
        }
    }

    /**
     * Loads driver data with optional filtering by bookmark.
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
     * Loads team data with optional filtering by bookmark.
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
     * Simple login form popup.
     */
    private static void loginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Simple registration form popup.
     */
    private static void registerDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Launch entry point.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Frontend::new);
    }
}
