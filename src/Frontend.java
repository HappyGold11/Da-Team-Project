import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Frontend {
    Backend backend = new Backend();

    public Frontend(){
        // Create the main frame
        JFrame frame = new JFrame("F1 Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Create a panel for the buttons at the top
        JPanel topPanel = new JPanel();
        
        // Add multiple buttons to the top panel
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

        // Create first panel
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Welcome to the Homepage"));

        // Create second panel (Drivers)
        JPanel panel2 = new JPanel();
        DefaultListModel<String> driverListModel = new DefaultListModel<>();
        JList<String> driverList = new JList<>(driverListModel);
        panel2.setLayout(new BorderLayout());
        panel2.add(new JScrollPane(driverList), BorderLayout.CENTER);
        for (String driver : backend.getDrivers()) {
            driverListModel.addElement(driver);
        }

        // Create third panel (Teams)
        JPanel panel3 = new JPanel();
        DefaultListModel<String> teamListModel = new DefaultListModel<>();
        JList<String> teamList = new JList<>(teamListModel);
        panel3.setLayout(new BorderLayout());
        panel3.add(new JScrollPane(teamList), BorderLayout.CENTER);
        for (String team : backend.getTeams()) {
            teamListModel.addElement(team);
        }

        // Add panels as tabs
        tabbedPane.addTab("Home", panel1);
        tabbedPane.addTab("Drivers", panel2);
        tabbedPane.addTab("Teams", panel3);

        //Search function
        JPanel search = new JPanel();
        JTextField searchBar = new JTextField(10); //Change number to set size of searchbar
        JButton searchButton = new JButton("Search");
        search.add(searchBar);
        search.add(searchButton);

        //Run the search function
        searchButton.addActionListener(e -> {
            callSearch(searchBar.getText(), tabbedPane.getSelectedIndex());
        });

        // Add components to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(search, BorderLayout.SOUTH);


        // Add action listeners for Login & Register
        button4.addActionListener(e -> loginDialog());
        button5.addActionListener(e -> registerDialog());

        // Make the frame visible
        frame.setVisible(true);
    }

    // Show Register Dialog
    private static void registerDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
    }

    // Show Login Dialog
    private static void loginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};
        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        
    }

    //Checks if user is on drivers or teams tab, then runs the search in that category
    private void callSearch(String text, int selectedTab) {
        if (selectedTab == 1) {
            backend.searchDrivers(text);
        } else if (selectedTab == 2) {
            backend.searchTeams(text);
        }
    }

    public static void main(String[] args) {
        Frontend f = new Frontend();
    }
   
}

