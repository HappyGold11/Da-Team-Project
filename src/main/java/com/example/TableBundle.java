/*
 * Data container class bundling a JPanel, JTable, and DefaultTableModel.
 * Used to easily manage and transfer UI components together.
 */
package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableBundle {
    public JPanel panel; // The main panel component
    public JTable table; // The table component
    public DefaultTableModel model; // The table's data model

    // Constructor to initialize all fields
    public TableBundle(JPanel panel, JTable table, DefaultTableModel model) {
        this.panel = panel;
        this.table = table;
        this.model = model;
    }
}