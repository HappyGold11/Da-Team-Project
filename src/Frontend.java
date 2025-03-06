import javax.swing.*;
import java.awt.*;

public class Frontend {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("F1 Database");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            //DefaultListModel<String> driverListModel;

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
            Backend backend = new Backend();
            // Create tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Create first panel
            JPanel panel1 = new JPanel();
            panel1.add(new JLabel("Welcome to the Homepage"));

            // Create second panel
            JPanel panel2 = new JPanel();
            //panel2.add(new JLabel("Welcome to the Driver Page!"));
            // Create a JList to display drivers 
            DefaultListModel<String> driverListModel = new DefaultListModel<>();
            JList<String> driverList = new JList<>(driverListModel);
            panel2.add(new JScrollPane(driverList));
            
            // Populate the JList with driver names from backend
            for (String driver : backend.getDrivers()) {
                driverListModel.addElement(driver);
            }
            panel2.setLayout(new BorderLayout());
            panel2.add(new JScrollPane(driverList), BorderLayout.CENTER);



            // Create third panel
            JPanel panel3 = new JPanel();
            panel3.add(new JLabel("Welcome to the Teams page"));

            // Create a JList to display teams
            DefaultListModel<String> teamListModel = new DefaultListModel<>();
            JList<String> teamList = new JList<>(teamListModel);
            panel3.setLayout(new BorderLayout());
            panel3.add(new JScrollPane(teamList), BorderLayout.CENTER);

            // Populate the JList with team names from backend
            for (String team : backend.getTeams()) {
                teamListModel.addElement(team);
            }
            panel3.setLayout(new BorderLayout());
            panel3.add(new JScrollPane(teamList), BorderLayout.CENTER);



            // Add panels as tabs
            tabbedPane.addTab("Home", panel1);
            tabbedPane.addTab("Drivers", panel2);
            tabbedPane.addTab("Teams", panel3);

            // Add components to the frame
            frame.add(topPanel, BorderLayout.NORTH);  // Buttons at the top
            frame.add(tabbedPane, BorderLayout.CENTER); // Tabs below the buttons

            // Make the frame visible
            frame.setVisible(true);
            System.out.println(backend.getDrivers());;

            
        });
    }
}
