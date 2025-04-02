package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TeamPanelFactory {
    /**
     * Constructs the full team tab interface, including a table listing team names
     * and a side panel for additional info and interaction.
     */
    public static TableBundle create(Backend backend, Frontend frontend) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(Color.BLACK);

        // Define columns (only one for team name)
        String[] columns = {"Team","Standing"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        JTable table = new JTable(tableModel);
        styleTable(table);

        // Default detail label on the right
        JPanel detailPanel = new JPanel(new BorderLayout());
        JLabel defaultLabel = new JLabel("Select a team to see more details...");
        defaultLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        defaultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailPanel.add(defaultLabel, BorderLayout.CENTER);

        JScrollPane tableScrollPane = new JScrollPane(table);

        // Apply background color to the viewport and scrollpane
        tableScrollPane.getViewport().setBackground(new Color(20, 20, 20)); // dark background
        tableScrollPane.setBackground(new Color(20, 20, 20));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.7);
        panel.add(splitPane, BorderLayout.CENTER);

        // Populate table with driver data (excluding header row)
        List<String> allTeams = backend.getTeams();
        for (int i = 1; i < allTeams.size(); i++) {
            String[] rawRow = allTeams.get(i).split(",");
            String[] row = Arrays.stream(rawRow).map(String::trim).toArray(String[]::new);
            tableModel.addRow(row);
        }

        // Update detail panel when row is selected
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // Extract full data for the selected driver
                    String teamName = (String) tableModel.getValueAt(row, 0);
                    String[] data = new String[2];
                    for (int i = 0; i < 2; i++) {
                        data[i] = (String) tableModel.getValueAt(row, i);
                    }
                    // Refresh the right-side panel with a new detail component
                    detailPanel.removeAll();
                    detailPanel.add(new TeamDetailPanel(teamName, data, backend, frontend), BorderLayout.CENTER);
                    detailPanel.revalidate();
                    detailPanel.repaint();
                }
            }
        });

        return new TableBundle(panel, table, tableModel);
    }

    /**
     * Styles the driver JTable with alternating row colors, header formatting, and font settings.
     */
    private static void styleTable(JTable table) {
        // Header style
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(Color.RED);

        // Table cells styling
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.WHITE);
                c.setFont(new Font("SansSerif", Font.PLAIN, 13));

                if (isSelected) {
                    c.setBackground(new Color(70, 0, 0)); // Dark red highlight
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(20, 20, 20) : new Color(30, 30, 30)); // Alternating dark rows
                }

                return c;
            }
        });

        table.setRowHeight(24);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.RED);
        table.setGridColor(Color.RED);
        table.setSelectionForeground(Color.BLACK); // Optional for contrast
    }
}