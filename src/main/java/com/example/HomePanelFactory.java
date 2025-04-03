package com.example;

import javax.swing.*;
import java.awt.*;

public class HomePanelFactory {
        /**
         * The homepage tab
         */
        public static JPanel create() {
                JPanel homePanel = new JPanel(new BorderLayout());
                homePanel.setBackground(new Color(20, 20, 20));

                // Title header
                JLabel headerLabel = new JLabel("F1 Database - Dive Into the World of Formula 1",
                                SwingConstants.CENTER);
                headerLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
                headerLabel.setForeground(Color.WHITE);
                headerLabel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
                homePanel.add(headerLabel, BorderLayout.NORTH);

                // Body container
                JPanel bodyPanel = new JPanel();
                bodyPanel.setLayout(new GridLayout(5, 1, 0, 30)); // Increased row count to 5
                bodyPanel.setBackground(new Color(20, 20, 20));
                bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

                // Section 1: Split Section - Two Side-by-Side Boxes
                bodyPanel.add(createSplitSection(
                                "Upcoming Race",
                                "The next race on the F1 2025 calender will be in at the Suzuka Circuit in Japan on Sun, April 6 at 1:00 a.m. EST",
                                "/img/suzuka.png",
                                "Current Fastest Qualifying",
                                "Oscar Piastri, the 23 year old Australian driver, set the world record for the fastest time around the Shanghai International Circuit at a 1:30.63 ",
                                "/img/Head_Shots/Oscar Piastri.png"));

                // Section 2: Intro to F1
                bodyPanel.add(createSection(
                                "What is Formula 1?",
                                "Formula 1 (F1) is the pinnacle of open-wheel, single-seater racing, featuring the fastest and the most technologically advanced racing cars in the world. "
                                                +
                                                "Governed by strict regulations and elite engineering, F1 brings together world-class drivers, iconic circuits, and cutting-edge technology in a global championship. "
                                                +
                                                "From the streets of Monaco to the night race in Singapore, each event delivers a spectacle of speed, skill, and strategy.",
                                "/img/logo.png"));

                // Section 3: About the FIA
                bodyPanel.add(createSection(
                                "The Role of the FIA",
                                "The Fédération Internationale de l'Automobile (FIA) is the governing body of Formula 1. Founded in 1904, it is responsible for regulating competition, safety standards, and fair play. "
                                                +
                                                "The FIA shapes the evolution of the sport through technical and sporting regulations, from crash tests to environmental initiatives, the FIA's work ensures that "
                                                +
                                                "Formula 1 not only entertains but leads in innovation and responsibility.",
                                "/img/fia_logo.png"));

                // Section 4: F1 Cars
                bodyPanel.add(createSection(
                                "Inside an F1 Car",
                                "Formula 1 race cars are marvels of engineering. They are one of most technologically advanced constructs of mechanical engineering ever created — lightweight, aerodynamic, and built for extreme performance. "
                                                +
                                                "Powered by hybrid power units, these cars combine turbocharged V6 engines with energy recovery systems that deliver over 1000 horsepower. "
                                                +
                                                "Each car is customized to the team's specifications, with complex aerodynamics, tire strategy, and data telemetry driving every performance detail.",
                                "/img/ferrari_car.png"));

                // Section 5: About This Program
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

                // Text area
                JTextArea body = new JTextArea();
                body.setEditable(false);
                body.setLineWrap(true);
                body.setWrapStyleWord(true);
                body.setText(title + "\n\n" + bodyText);
                body.setFont(new Font("SansSerif", Font.PLAIN, 15));
                body.setForeground(new Color(230, 230, 230));
                body.setBackground(new Color(30, 30, 30));
                body.setBorder(null);

                // Image label with preserved aspect ratio
                JLabel imageLabel;
                try {
                        ImageIcon icon = new ImageIcon(HomePanelFactory.class.getResource(imagePath));
                        Image img = icon.getImage();
                        int maxW = 200, maxH = 160;
                        int imgW = icon.getIconWidth();
                        int imgH = icon.getIconHeight();
                        double scale = Math.min((double) maxW / imgW, (double) maxH / imgH);
                        int newW = (int) (imgW * scale);
                        int newH = (int) (imgH * scale);
                        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
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

        private static JPanel createSplitSection(String leftTitle, String leftBody, String leftImagePath,
                        String rightTitle, String rightBody, String rightImagePath) {
                JPanel sectionPanel = new JPanel(new BorderLayout());
                sectionPanel.setBackground(new Color(30, 30, 30));
                sectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JPanel splitPanel = new JPanel(new GridLayout(1, 2, 20, 0));
                splitPanel.setBackground(new Color(30, 30, 30));

                JPanel left = createMiniSection(leftTitle, leftBody, leftImagePath);
                JPanel right = createMiniSection(rightTitle, rightBody, rightImagePath);

                splitPanel.add(left);
                splitPanel.add(right);

                sectionPanel.add(splitPanel, BorderLayout.CENTER);
                return sectionPanel;
        }

        private static JPanel createMiniSection(String title, String bodyText, String imagePath) {
                JPanel panel = new JPanel(new BorderLayout(10, 0));
                panel.setBackground(new Color(45, 45, 45));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JTextArea text = new JTextArea(title + "\n\n" + bodyText);
                text.setEditable(false);
                text.setLineWrap(true);
                text.setWrapStyleWord(true);
                text.setFont(new Font("SansSerif", Font.PLAIN, 14));
                text.setForeground(Color.WHITE);
                text.setBackground(new Color(45, 45, 45));
                text.setBorder(null);

                JLabel imageLabel;
                try {
                        ImageIcon icon = new ImageIcon(HomePanelFactory.class.getResource(imagePath));
                        Image img = icon.getImage();
                        int maxW = 150, maxH = 120;
                        int imgW = icon.getIconWidth();
                        int imgH = icon.getIconHeight();
                        double scale = Math.min((double) maxW / imgW, (double) maxH / imgH);
                        int newW = (int) (imgW * scale);
                        int newH = (int) (imgH * scale);
                        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                        imageLabel = new JLabel(new ImageIcon(scaled));
                } catch (Exception e) {
                        imageLabel = new JLabel("[Image not found]");
                        imageLabel.setPreferredSize(new Dimension(150, 120));
                        imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
                        imageLabel.setForeground(Color.LIGHT_GRAY);
                        imageLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                }

                panel.add(text, BorderLayout.CENTER);
                panel.add(imageLabel, BorderLayout.EAST);
                return panel;
        }

}
