package com.example;

import javax.swing.*;
import java.awt.*;

public class FAQPage {
    // This field will store the JPanel for FAQ
    public JPanel panel;

    public FAQPage() {
        panel = create();  // Initialize the panel when FAQPage is created
    }

    public JPanel create() {
        JPanel faqPanel = new JPanel(new BorderLayout());
        faqPanel.setBackground(new Color(20, 20, 20));

        // Title header
        JLabel headerLabel = new JLabel("Frequently Asked Questions", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        faqPanel.add(headerLabel, BorderLayout.NORTH);

        // Body container
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(0, 1, 0, 30));
        bodyPanel.setBackground(new Color(20, 20, 20));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // FAQ Sections
        bodyPanel.add(createSection("How do I add drivers or teams to my list?",
                "Navigate to the 'Drivers' or 'Teams' tab and click on the 'Bookmark Driver' button. This will save your selections for quick access.",
                "/img/bookmarkbutton.png"));

        bodyPanel.add(createSection("Where can I see my Drivers or Teams list?",
                "Click the 'Show Bookmark' button at the top of the page to see your personal Driver or Team list.",
                "/img/showbookmarktab.png"));

        JScrollPane scrollPane = new JScrollPane(bodyPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        faqPanel.add(scrollPane, BorderLayout.CENTER);
        return faqPanel;
    }

    private static JPanel createSection(String question, String answer, String imagePath) {
        JPanel sectionPanel = new JPanel(new BorderLayout(20, 0));
        sectionPanel.setBackground(new Color(30, 30, 30));
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left text area (JLabel for better rendering)
        JLabel textLabel = new JLabel("<html><b>" + question + "</b><br><br>" + answer + "</html>");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        textLabel.setForeground(new Color(230, 230, 230));

        // Right image placeholder
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 160));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon(FAQPage.class.getResource(imagePath));
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image scaled = icon.getImage().getScaledInstance(200, 160, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("[Image not found]");
            imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
            imageLabel.setForeground(Color.LIGHT_GRAY);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }

        sectionPanel.add(textLabel, BorderLayout.CENTER);
        sectionPanel.add(imageLabel, BorderLayout.EAST);
        return sectionPanel;
    }
}


