package com.teoneag;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TableEditor extends JFrame {
    private final JPanel mainContent = new JPanel(new BorderLayout());
    private final JLabel statusBar = new JLabel("Ready");
    private File currentFile = null;
    private JTable table = null;
    private DefaultTableModel tableModel = null;
    private boolean isSaved = true;

    public TableEditor() {
        setTitle("Table Editor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(mainContent, BorderLayout.CENTER);

        createMenuBar();
        createStatusBar();
        createNoTableMessage();
    }

    private void newTable(int rows, int cols) {
        if (table != null) {
            int result = JOptionPane.showConfirmDialog(this, "Do you want to save the current table?", "Save Table", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveTable();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        JTextField rowsField = createNumericTextField(5);
        JTextField colsField = createNumericTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Number of rows:"));
        panel.add(rowsField);
        panel.add(new JLabel("Number of columns:"));
        panel.add(colsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Create new table", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                tableModel = new DefaultTableModel(Integer.parseInt(rowsField.getText()), Integer.parseInt(colsField.getText()));
                table = new JTable(tableModel);
                displayOnCenter(new JScrollPane(table));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                newTable(rows, cols);
            }
        }
    }

    private void openTable() {
        // ToDo
    }

    private void saveTable() {
        if (table == null) {
            showSaveError();
            return;
        }
        if (currentFile == null) saveAsTable();
        else saveTableAsCSV();
    }

    private void saveAsTable() {
        if (table == null) {
            showSaveError();
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            if (!currentFile.getName().endsWith(".csv")) currentFile = new File(currentFile.getAbsolutePath() + ".csv");
            saveTableAsCSV();
        }
        isSaved = true;
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

    private void showSaveError() {
        JOptionPane.showMessageDialog(this, "No table to save. Create or open one first.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void saveTableAsCSV() {
        // ToDo check if the cell contains "," and do smth
        try (FileWriter fileWriter = new FileWriter(currentFile)) {
            // Write the column names
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                fileWriter.write(tableModel.getColumnName(i) + ",");
            }
            fileWriter.write("\n");

            // Write the rows
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    if (tableModel.getValueAt(i, j) == null) fileWriter.write(",");
                    else fileWriter.write(tableModel.getValueAt(i, j).toString() + ",");
                }
                fileWriter.write("\n");
            }

            statusBar.setText("Saved successfully to " + currentFile.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createStatusBar() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        JSeparator separator = new JSeparator();

        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        statusPanel.add(separator, BorderLayout.NORTH);
        statusPanel.add(statusBar, BorderLayout.SOUTH);

        add(statusPanel, BorderLayout.SOUTH);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "New", e -> newTable(5, 5));
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

    private void createNoTableMessage() {
        JLabel label = new JLabel("No table opened, please create a ");
        JButton newButton = new JButton("new");
        newButton.addActionListener(e -> newTable(5, 5));
        JLabel label2 = new JLabel(" table or ");
        JButton openButton = new JButton("open");
        openButton.addActionListener(e -> openTable());
        JLabel label3 = new JLabel(" one.");

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(label);
        messagePanel.add(newButton);
        messagePanel.add(label2);
        messagePanel.add(openButton);
        messagePanel.add(label3);

        displayOnCenter(messagePanel);
    }

    private void displayOnCenter(Component component) {
        mainContent.removeAll();
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(component);
        mainContent.add(centerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void addMenuItem(JMenu menu, String name, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }

    private JTextField createNumericTextField(int value) {
        JTextField textField = new JTextField(Integer.toString(value), 5);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TableEditor().setVisible(true);
        });
    }
}
