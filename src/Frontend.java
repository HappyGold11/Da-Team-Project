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

            // Create a panel for the buttons at the top
            JPanel topPanel = new JPanel();
            
            // Add multiple buttons to the top panel
            JButton button1 = new JButton("Bookmarked Drivers");
            JButton button2 = new JButton("Bookmarked Teams");
            JButton button3 = new JButton("Settings");
            JButton button4 = new JButton("Login");

            topPanel.add(button1);
            topPanel.add(button2);
            topPanel.add(button3);
            topPanel.add(button4);

            // Create tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Create first panel
            JPanel panel1 = new JPanel();
            panel1.add(new JLabel("Welcome to the Homepage"));

            // Create second panel
            JPanel panel2 = new JPanel();
            panel2.add(new JLabel("Welcome to the Driver's page"));

            // Create third panel
            JPanel panel3 = new JPanel();
            panel3.add(new JLabel("Welcome to the Teams page"));

            // Add panels as tabs
            tabbedPane.addTab("Home", panel1);
            tabbedPane.addTab("Drivers", panel2);
            tabbedPane.addTab("Teams", panel3);

            // Add components to the frame
            frame.add(topPanel, BorderLayout.NORTH);  // Buttons at the top
            frame.add(tabbedPane, BorderLayout.CENTER); // Tabs below the buttons

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
