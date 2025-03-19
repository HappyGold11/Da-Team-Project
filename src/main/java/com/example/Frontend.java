package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        DefaultTableModel driverTableModel = new DefaultTableModel(driverColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Makes all cells non-editable
            }
        };
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

        // Add click listener to load square-cropped headshot + stats
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
                    JLabel imageLabel;

                    if (imgURL != null) {
                        ImageIcon originalIcon = new ImageIcon(imgURL);
                        Image originalImage = originalIcon.getImage();
                        int width = originalImage.getWidth(null);
                        int height = originalImage.getHeight(null);
                        int squareSize = Math.min(width, height);
                        int x = (width - squareSize) / 2;
                        int y = (height - squareSize) / 2;

                        BufferedImage bufferedOriginal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedOriginal.createGraphics();
                        g2d.drawImage(originalImage, 0, 0, null);
                        g2d.dispose();

                        BufferedImage cropped = bufferedOriginal.getSubimage(x, y, squareSize, squareSize);
                        Image scaledImage = cropped.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                        imageLabel = new JLabel(new ImageIcon(scaledImage));
                    } else {
                        imageLabel = new JLabel("No headshot found");
                        imageLabel.setPreferredSize(new Dimension(120, 120));
                        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    }

                    imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JTextArea detailText = new JTextArea();
                    detailText.setEditable(false);
                    detailText.setFont(new Font("SansSerif", Font.PLAIN, 13));
                    detailText.setText(
                            "Name: " + driverName + "\n" +
                                    "Team: " + team + "\n" +
                                    "Wins: " + wins + "\n" +
                                    "Podiums: " + podiums + "\n" +
                                    "Salary: " + salary + "\n" +
                                    "Contract Ends: " + contractEnds + "\n" +
                                    "Career Points: " + points + "\n" +
                                    "Current Standing: " + standing + "\n"
                    );

                    // Make bookmark button
                    JButton bookmarkButton = new JButton("Bookmark Driver");
                    if (backend.isDriverBookmarked(driverName)) {
                        bookmarkButton.setText("Bookmarked ✔");
                        bookmarkButton.setEnabled(false);
                    } else {
                        bookmarkButton.addActionListener(e -> {
                            if (backend.bookmarkDriver(driverName)) {
                                bookmarkButton.setText("Bookmarked ✔");
                                bookmarkButton.setEnabled(false);
                            }
                        });
                    }

                    JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    contentPanel.add(imageLabel, BorderLayout.NORTH);
                    contentPanel.add(new JScrollPane(detailText), BorderLayout.CENTER);
                    contentPanel.add(bookmarkButton, BorderLayout.SOUTH);

                    detailPanel.add(contentPanel, BorderLayout.CENTER);
                    detailPanel.revalidate();
                    detailPanel.repaint();
                }
            }
        });

        //Teams Panel with Table and SplitPane
        JPanel panel3 = new JPanel(new BorderLayout());

        String[] teamColumns = {"Team"};
        DefaultTableModel teamTableModel = new DefaultTableModel(teamColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Makes all cells non-editable
            }
        };
        teamTable = new JTable(teamTableModel);
        styleTable(teamTable);

        JPanel teamDetailPanel = new JPanel(new BorderLayout());
        JLabel teamDetailLabel = new JLabel("Select a team to see more details...");
        teamDetailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        teamDetailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        teamDetailPanel.add(teamDetailLabel, BorderLayout.CENTER);

        JScrollPane teamTableScroll = new JScrollPane(teamTable);

        JSplitPane teamSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, teamTableScroll, teamDetailPanel);
        teamSplitPane.setDividerLocation(250);
        teamSplitPane.setResizeWeight(0.7);
        panel3.add(teamSplitPane, BorderLayout.CENTER);

        // Load team data
        for (String team : backend.getTeams()) {
            teamTableModel.addRow(new Object[]{team});
        }

        // Click listener for team rows
        teamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = teamTable.getSelectedRow();
                if (row >= 0) {
                    String teamName = (String) teamTableModel.getValueAt(row, 0);

                    teamDetailPanel.removeAll();

                    String logoPath = "/img/Team_Logos/" + teamName + ".jpg";
                    java.net.URL logoURL = getClass().getResource(logoPath);
                    JLabel logoLabel;

                    if (logoURL != null) {
                        ImageIcon originalIcon = new ImageIcon(logoURL);
                        Image originalImage = originalIcon.getImage();
                        int width = originalImage.getWidth(null);
                        int height = originalImage.getHeight(null);
                        int squareSize = Math.min(width, height);
                        int x = (width - squareSize) / 2;
                        int y = (height - squareSize) / 2;

                        BufferedImage bufferedOriginal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedOriginal.createGraphics();
                        g2d.drawImage(originalImage, 0, 0, null);
                        g2d.dispose();

                        BufferedImage cropped = bufferedOriginal.getSubimage(x, y, squareSize, squareSize);
                        Image scaledLogo = cropped.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                        logoLabel = new JLabel(new ImageIcon(scaledLogo));
                    } else {
                        logoLabel = new JLabel("No logo found");
                        logoLabel.setPreferredSize(new Dimension(120, 120));
                        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    }

                    logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JTextArea teamInfo = new JTextArea();
                    teamInfo.setEditable(false);
                    teamInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
                    teamInfo.setText(
                            "Team Name: " + teamName + "\n\n" +
                                    "[More team stats, constructor standings, or history could go here.]"
                    );

                    // Bookmark Button
                    JButton bookmarkTeamButton = new JButton("Bookmark Team");

                    if (backend.isTeamBookmarked(teamName)) {
                        bookmarkTeamButton.setText("Bookmarked ✔");
                        bookmarkTeamButton.setEnabled(false);
                    } else {
                        bookmarkTeamButton.addActionListener(e -> {
                            if (backend.bookmarkTeam(teamName)) {
                                bookmarkTeamButton.setText("Bookmarked ✔");
                                bookmarkTeamButton.setEnabled(false);
                            }
                        });
                    }



                    JPanel teamContentPanel = new JPanel(new BorderLayout(10, 10));
                    teamContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    teamContentPanel.add(logoLabel, BorderLayout.WEST);
                    teamContentPanel.add(new JScrollPane(teamInfo), BorderLayout.CENTER);
                    teamContentPanel.add(bookmarkTeamButton, BorderLayout.SOUTH);

                    teamDetailPanel.add(teamContentPanel, BorderLayout.CENTER);
                    teamDetailPanel.revalidate();
                    teamDetailPanel.repaint();
                }
            }
        });


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