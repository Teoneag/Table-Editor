package com.teoneag;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.teoneag.tokenizer.Token;
import com.teoneag.tokenizer.Tokenizer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableEditor extends JFrame {
    private final JPanel mainContent = new JPanel(new BorderLayout());
    private final JLabel statusBar = new JLabel("Ready");
    private File currentFile = null;
    private JTable table = null;
    private DefaultTableModel tableModel = null;
    private boolean toSave = false;
    private List<List<Double>> sheet;

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
        if (toSave) {
            if (!askSaveCurrent()) return;
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
                int nrows = Integer.parseInt(rowsField.getText());
                int ncols = Integer.parseInt(colsField.getText());
                tableModel = new DefaultTableModel(nrows, ncols);
                table = new JTable(tableModel);
                sheet = new ArrayList<>(rows);
                for (int i = 0; i < rows; i++) {
                    List<Double> innerList = new ArrayList<>(Collections.nCopies(cols, null));
                    sheet.add(innerList);
                }
                tableModel.addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        toSave = true;
                        statusBar.setText("Table modified. Save to keep changes.");
                        String value = tableModel.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                        List<Token> tokens = Tokenizer.tokenize(value);
                        System.out.println("Tokens: " + tokens);
                        Node node = Parser.parse(tokens, sheet);
                        System.out.println("Node: " + node);

                        int row = e.getFirstRow();
                        int col = e.getColumn();

                    }
                });
                displayOnCenter(new JScrollPane(table));
                toSave = true;
                statusBar.setText("New table created with " + rowsField.getText() + " rows and " + colsField.getText() + " columns. Save to keep changes.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Enter valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                newTable(rows, cols);
            }
        }
    }

    private void openTable() {
        if (toSave) {
            if (!askSaveCurrent()) return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            tableModel = new DefaultTableModel();
            table = new JTable(tableModel);
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            try (CSVReader reader = new CSVReader(new FileReader(currentFile))) {
                String[] nextLine;
                boolean isFirstLine = true;

                while ((nextLine = reader.readNext()) != null) {
                    if (isFirstLine) {
                        for (String column : nextLine) {
                            tableModel.addColumn(column);
                        }
                        isFirstLine = false;
                    } else {
                        tableModel.addRow(nextLine);
                    }
                }

                int nrows = tableModel.getRowCount();
                int ncols = tableModel.getColumnCount();

                sheet = new ArrayList<>(nrows);
                for (int i = 0; i < nrows; i++) {
                    sheet.add(new ArrayList<>(ncols));
                    for (int j = 0; j < ncols; j++) {
                        // ToDo
//                        int value =
                    }
                }

                displayOnCenter(new JScrollPane(table));
                toSave = false;
                statusBar.setText("Opened successfully from " + currentFile.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
    }

    private void exit() {
        if (toSave) {
            if (!askSaveCurrent()) return;
        }
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

    /**
     * @return true if cancel otherwise false
     */
    private boolean askSaveCurrent() {
        int result = JOptionPane.showConfirmDialog(this, "Do you want to save the current table?", "Save Table", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            saveTable();
            return true;
        }
        return result != JOptionPane.CANCEL_OPTION;
    }

    private void saveTableAsCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(currentFile))) {
            // Write the column names
            String[] columnNames = new String[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                columnNames[i] = tableModel.getColumnName(i);
            }
            writer.writeNext(columnNames);

            // Write the rows
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String[] rowData = new String[tableModel.getColumnCount()];
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object cellValue = tableModel.getValueAt(i, j);
                    rowData[j] = cellValue == null ? "" : cellValue.toString();
                }
                writer.writeNext(rowData, false);
            }

            statusBar.setText("Saved successfully to " + currentFile.getAbsolutePath());
            toSave = false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
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
        JLabel label = new JLabel("No table opened, create a ");
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
