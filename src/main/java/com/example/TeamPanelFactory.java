package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class TeamPanelFactory {
    /**
     * Constructs the full team tab interface, including a table listing team names
     * and a side panel for additional info and interaction.
     */
    public static TableBundle create(Backend backend, Frontend frontend) {
        JPanel panel = new JPanel(new BorderLayout());

        // Define columns (only one for team name)
        String[] columns = {"Team"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.7);
        panel.add(splitPane, BorderLayout.CENTER);

        // Load team names into table
        for (String team : backend.getTeams()) {
            tableModel.addRow(new Object[]{team});
        }

        // Update detail panel when row is selected
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String teamName = (String) tableModel.getValueAt(row, 0);
                    detailPanel.removeAll();
                    detailPanel.add(new TeamDetailPanel(teamName, backend, frontend), BorderLayout.CENTER);
                    detailPanel.revalidate();
                    detailPanel.repaint();
                }
            }
        });

        return new TableBundle(panel, table, tableModel);
    }

    /**
     * Applies alternating row styles and font customization to team table.
     */
    private static void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(220, 220, 220));

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
}