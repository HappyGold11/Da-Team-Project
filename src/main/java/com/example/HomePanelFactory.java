package com.example;

import javax.swing.*;
import java.awt.*;

public class HomePanelFactory {
    /**
     * The hompage tab 
     */
    public static JPanel create() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(20, 20, 20));

        // Title header
        JLabel headerLabel = new JLabel("F1 Database - Dive Into the World of Formula 1", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        homePanel.add(headerLabel, BorderLayout.NORTH);

        // Body container
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 1, 0, 30));
        bodyPanel.setBackground(new Color(20, 20, 20));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // Section 1: Intro to F1
        bodyPanel.add(createSection(
                "What is Formula 1?",
                "Formula 1 (F1) is the pinnacle of open-wheel, single-seater racing, featuring the fastest and the most technologically advanced racing cars in the world. "
                        +
                        "Governed by strict regulations and elite engineering, F1 brings together world-class drivers, iconic circuits, and cutting-edge technology in a global championship. "
                        +
                        "From the streets of Monaco to the night race in Singapore, each event delivers a spectacle of speed, skill, and strategy.",
                "/img/logo.png"));

        // Section 2: About the FIA
        bodyPanel.add(createSection(
                "The Role of the FIA",
                "The Fédération Internationale de l'Automobile (FIA) is the governing body of Formula 1. Founded in 1904, it is responsible for regulating competition, safety standards, and fair play. "
                        +
                        "The FIA shapes the evolution of the sport through technical and sporting regulations, from crash tests to environmental initiatives, the FIA's work ensures that "
                        +
                        "Formula 1 not only entertains but leads in innovation and responsibility.",
                "/img/fia_logo.png"));

        // Section 3: F1 Cars
        bodyPanel.add(createSection(
                "Inside an F1 Car",
                "Formula 1 race cars are marvels of engineering. They are one of most technologically advanced constructs of mechanical engineering ever created — lightweight, aerodynamic, and built for extreme performance. "
                        +
                        "Powered by hybrid power units, these cars combine turbocharged V6 engines with energy recovery systems that deliver over 1000 horsepower. "
                        +
                        "Each car is customized to the team's specifications, with complex aerodynamics, tire strategy, and data telemetry driving every performance detail.",
                "/img/ferrari_car.png"));

        // Section 4: About This Program
        bodyPanel.add(createSection(
                "Explore with Our F1 Database",
                "This application is your personal pit wall — explore in-depth statistics on drivers and teams, manage your own bookmarks, and stay informed with contract, podium, "
                        +
                        "and performance data. Built with efficiency and clarity in mind, this platform simplifies your journey through the fast-paced world of Formula 1.",
                "/img/spa.png"));

        JScrollPane scrollPane = new JScrollPane(bodyPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        homePanel.add(scrollPane, BorderLayout.CENTER);
        return homePanel;
    }

    private static JPanel createSection(String title, String bodyText, String imagePath) {
        JPanel sectionPanel = new JPanel(new BorderLayout(20, 0));
        sectionPanel.setBackground(new Color(30, 30, 30));
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left text area
        JTextArea body = new JTextArea();
        body.setEditable(false);
        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        body.setText(title + "\n\n" + bodyText);
        body.setFont(new Font("SansSerif", Font.PLAIN, 15));
        body.setForeground(new Color(230, 230, 230));
        body.setBackground(new Color(30, 30, 30));
        body.setBorder(null);

        // Right placeholder
        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(HomePanelFactory.class.getResource(imagePath));
            Image scaled = icon.getImage().getScaledInstance(200, 160, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            imageLabel = new JLabel("[Image not found]");
            imageLabel.setPreferredSize(new Dimension(200, 160));
            imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
            imageLabel.setForeground(Color.LIGHT_GRAY);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        sectionPanel.add(body, BorderLayout.CENTER);
        sectionPanel.add(imageLabel, BorderLayout.EAST);
        return sectionPanel;
    }
}