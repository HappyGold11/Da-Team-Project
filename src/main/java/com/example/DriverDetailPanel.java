package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

class DriverDetailPanel extends JPanel {
    /**
     * Panel showing detailed statistics and headshot of a selected driver,
     * including bookmark functionality.
     */
    public DriverDetailPanel(String driverName, String[] data, Backend backend, Frontend frontend) {
        super(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Image of driver (headshot), driver stats, and a bookmark toggle button
        JLabel imageLabel = buildDriverImage(driverName);
        JTextArea detailText = buildDriverText(data);
        JButton bookmarkButton = buildBookmarkButton(driverName, backend, frontend);

        // Layout for detail panel
        add(imageLabel, BorderLayout.NORTH);
        add(new JScrollPane(detailText), BorderLayout.CENTER);
        add(bookmarkButton, BorderLayout.SOUTH);
    }

    /**
     * Attempts to load and crop a square driver headshot from the resources.
     */
    private JLabel buildDriverImage(String driverName) {
        String imagePath = "/img/Head_Shots/" + driverName + ".png";
        URL imgURL = getClass().getResource(imagePath);
        JLabel imageLabel;

        if (imgURL != null) {
            // Load and crop image
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image originalImage = originalIcon.getImage();
            BufferedImage cropped = ImageUtils.cropSquare(originalImage);
            Image scaled = cropped.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaled));
        } else {
            // Fallback when image is missing
            imageLabel = new JLabel("No headshot found");
            imageLabel.setPreferredSize(new Dimension(120, 120));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return imageLabel;
    }

    /**
     * Formats and presents driver statistics from the CSV.
     */
    private JTextArea buildDriverText(String[] data) {
        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setFont(new Font("SansSerif", Font.PLAIN, 13));
        text.setText(
                "Name: " + data[0] + "\n" +
                        "Team: " + data[1] + "\n" +
                        "Wins: " + data[2] + "\n" +
                        "Podiums: " + data[3] + "\n" +
                        "Salary: " + data[4] + "\n" +
                        "Contract Ends: " + data[5] + "\n" +
                        "Career Points: " + data[6] + "\n" +
                        "Current Standing: " + data[7] + "\n"
        );
        return text;
    }

    /**
     * Creates a bookmark toggle button and connects its logic to backend.
     */
    private JButton buildBookmarkButton(String driverName, Backend backend, Frontend frontend) {
        JButton button = new JButton(backend.isDriverBookmarked(driverName) ? "Unbookmark Driver" : "Bookmark Driver");

        button.addActionListener(e -> {
            if (backend.isDriverBookmarked(driverName)) {
                backend.unbookmarkDriver(driverName);
                button.setText("Bookmark Driver");
            } else {
                backend.bookmarkDriver(driverName);
                button.setText("Unbookmark Driver");
            }
            // Refresh list if in bookmarked-only view
            if (frontend != null && frontend.showBookmarkedDriversOnly) {
                frontend.searchDrivers("");
            }
        });

        return button;
    }
}