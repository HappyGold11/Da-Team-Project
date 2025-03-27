package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DriverPanelFactory {
    /**
     * Constructs the full driver tab interface, including a table for listing all drivers
     * and a detail panel that updates when a row is selected.
     * This method returns a TableBundle containing the root panel, table, and table model.
     */
    public static TableBundle create(Backend backend, Frontend frontend) {
        JPanel panel = new JPanel(new BorderLayout()); // Root panel for layout

        // Define columns expected from the driver CSV file
        String[] columns = {
                "Name", "Team", "Wins", "Podiums", "Salary",
                "Contract Ends", "Career Points", "Current Standing"
        };

        // Table model that prevents editing cells
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create JTable using the above model and apply consistent styling
        JTable table = new JTable(tableModel);
        styleTable(table);

        // Right-side detail panel with default instructional label
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBackground(new Color(20, 20, 20)); // Black background

        JLabel defaultLabel = new JLabel("Select a driver to see more details...");
        defaultLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        defaultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        defaultLabel.setForeground(Color.RED); // Red text

        detailPanel.add(defaultLabel, BorderLayout.CENTER);


        // Combine the table and detail panel using JSplitPane
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.getViewport().setBackground(new Color(20, 20, 20)); // Match table bg
        tableScrollPane.setBackground(new Color(20, 20, 20));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailPanel);
        splitPane.setDividerLocation(1000); // Width allocation for table vs. detail view
        splitPane.setResizeWeight(0.7); // Resize ratio when window changes size
        panel.add(splitPane, BorderLayout.CENTER);

        // Populate table with driver data (excluding header row)
        List<String> allDrivers = backend.getDrivers();
        for (int i = 1; i < allDrivers.size(); i++) {
            String[] rawRow = allDrivers.get(i).split(",");
            String[] row = Arrays.stream(rawRow).map(String::trim).toArray(String[]::new);
            tableModel.addRow(row);
        }

        // Respond to user clicks by loading driver detail view
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // Extract full data for the selected driver
                    String driverName = (String) tableModel.getValueAt(row, 0);
                    String[] data = new String[8];
                    for (int i = 0; i < 8; i++) {
                        data[i] = (String) tableModel.getValueAt(row, i);
                    }
                    // Refresh the right-side panel with a new detail component
                    detailPanel.removeAll();
                    detailPanel.add(new DriverDetailPanel(driverName, data, backend, frontend), BorderLayout.CENTER);
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
                c.setForeground(Color.RED);
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
        table.setSelectionForeground(Color.WHITE); // Optional for contrast
    }
}