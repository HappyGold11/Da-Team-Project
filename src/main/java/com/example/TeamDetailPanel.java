package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

class TeamDetailPanel extends JPanel {
    /**
     * Panel that displays logo and info about a selected team, with bookmarking control.
     */
    public TeamDetailPanel(String teamName, String[] data,Backend backend, Frontend frontend) {
        super(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.BLACK); // white background for the panel

        JLabel logoLabel = buildTeamLogo(teamName);
        JTextArea teamInfo = buildTeamText(data);
        JButton bookmarkButton = buildBookmarkButton(teamName, backend, frontend);

        add(logoLabel, BorderLayout.WEST);
        add(new JScrollPane(teamInfo), BorderLayout.CENTER);
        add(bookmarkButton, BorderLayout.SOUTH);
    }

    /**
     * Loads and scales the logo image of a team from resources.
     */
    private JLabel buildTeamLogo(String teamName) {
        String logoPath = "/img/Team_Logos/" + teamName + ".png";
        URL logoURL = getClass().getResource(logoPath);
        JLabel logoLabel;

        if (logoURL != null) {
            ImageIcon originalIcon = new ImageIcon(logoURL);
            Image originalImage = originalIcon.getImage();

            // Calculate the height based on the aspect ratio
            int newWidth = 250;
            int newHeight = (int) (originalImage.getHeight(null) * ((double) newWidth / originalImage.getWidth(null)));

            // Scale the image to the new dimensions while preserving the aspect ratio
            Image scaled = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaled));
        } else {
            logoLabel = new JLabel("No logo found");
            logoLabel.setPreferredSize(new Dimension(120, 120));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return logoLabel;
    }

    /**
     * Displays placeholder or actual team information.
     */
    private JTextArea buildTeamText(String[] data) {
        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setFont(new Font("SansSerif", Font.PLAIN, 13));
        text.setForeground(Color.RED);
        text.setBackground(new Color(20, 20, 20));
        text.setCaretColor(Color.RED);

        text.setText(
                "Team name: " + data[0] + "\n" +
                        "Standing: " + data[1] + "\n"
        );
        return text;
    }

    /**
     * Creates bookmark toggle for teams and hooks into backend for persistence.
     */
    private JButton buildBookmarkButton(String teamName, Backend backend, Frontend frontend) {
        JButton button = new JButton(backend.isTeamBookmarked(teamName) ? "Unbookmark Team" : "Bookmark Team");

        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));

        button.addActionListener(e -> {
            if (backend.isTeamBookmarked(teamName)) {
                backend.unbookmarkTeam(teamName);
                button.setText("Bookmark Team");
            } else {
                backend.bookmarkTeam(teamName);
                button.setText("Unbookmark Team");
            }
            if (frontend != null && frontend.showBookmarkedTeamsOnly) {
                frontend.searchTeams("");
            }
        });

        return button;
    }
}
