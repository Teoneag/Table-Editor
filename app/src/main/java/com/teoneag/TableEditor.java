package com.teoneag;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class TableEditor extends JFrame {
    private JTable table = null;
    private DefaultTableModel tableModel = null;

    public TableEditor() {
        setTitle("Table Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();

        if (table == null) createNoTableMessage();

    }

    private void newTable() {
        // ToDo
    }

    private void openTable() {
        // ToDo
    }

    private void saveTable() {
        // ToDo
    }

    private void saveAsTable() {
        // ToDo
    }

    private void exit() {
        System.exit(0);
    }

    private void undo() {
        // ToDo
    }

    private void redo() {
        // ToDo
    }

    private void cut() {
        // ToDo
    }

    private void copy() {
        // ToDo
    }

    private void paste() {
        // ToDo
    }

    private void insertRow() {
        // ToDo
    }

    private void insertColumn() {
        // ToDo
    }

    private void deleteRow() {
        // ToDo
    }

    private void deleteColumn() {
        // ToDo
    }

    private void about() {
        // ToDo
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "New", e -> newTable());
        addMenuItem(fileMenu, "Open", e -> openTable());
        addMenuItem(fileMenu, "Save", e -> saveTable());
        addMenuItem(fileMenu, "Save As", e -> saveAsTable());
        addMenuItem(fileMenu, "Exit", e -> exit());
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        addMenuItem(editMenu, "Undo", e -> undo());
        addMenuItem(editMenu, "Redo", e -> redo());
        addMenuItem(editMenu, "Cut", e -> cut());
        addMenuItem(editMenu, "Copy", e -> copy());
        addMenuItem(editMenu, "Paste", e -> paste());
        menuBar.add(editMenu);

        JMenu tableMenu = new JMenu("Table");
        addMenuItem(tableMenu, "Insert Row", e -> insertRow());
        addMenuItem(tableMenu, "Insert Column", e -> insertColumn());
        addMenuItem(tableMenu, "Delete Row", e -> deleteRow());
        addMenuItem(tableMenu, "Delete Column", e -> deleteColumn());
        menuBar.add(tableMenu);

        JMenu helpMenu = new JMenu("Help");
        addMenuItem(helpMenu, "About", e -> about());
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void addMenuItem(JMenu menu, String name, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }

    private void createNoTableMessage() {
        JLabel label = new JLabel("No table opened, please create a ");
        JButton newButton = new JButton("new");
        JLabel label2 = new JLabel(" table or ");
        JButton openButton = new JButton("open");
        JLabel label3 = new JLabel(" one.");

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(label);
        messagePanel.add(newButton);
        messagePanel.add(label2);
        messagePanel.add(openButton);
        messagePanel.add(label3);

        setLayout(new GridBagLayout());
        add(messagePanel, new GridBagConstraints());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TableEditor().setVisible(true);
        });
    }
}
