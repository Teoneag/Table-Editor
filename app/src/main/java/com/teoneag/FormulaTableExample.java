package com.teoneag;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class FormulaTableExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Formula Table Example");
            FormulaTableModel model = new FormulaTableModel(10, 5);
            JTable table = new JTable(model) {
                @Override
                public TableCellRenderer getCellRenderer(int row, int column) {
                    return new FormulaCellRenderer();
                }

                @Override
                public TableCellEditor getCellEditor(int row, int column) {
                    return new FormulaCellEditor();
                }

                @Override
                public boolean editCellAt(int row, int column, EventObject e) {
                    boolean isEditable = super.editCellAt(row, column, e);
                    if (isEditable && e instanceof MouseEvent) {
                        // Show formula in the editor
                        ((JTextComponent) getEditorComponent()).setText(model.getFormulaAt(row, column));
                    }
                    return isEditable;
                }
            };

            JPanel buttonPanel = new JPanel();
            JButton addRowButton = new JButton("Add Row");
            JButton removeRowButton = new JButton("Remove Row");
            JButton addColumnButton = new JButton("Add Column");
            JButton removeColumnButton = new JButton("Remove Column");

            addRowButton.addActionListener(e -> model.addRow());
            removeRowButton.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                }
            });

            addColumnButton.addActionListener(e -> model.addColumn());
            removeColumnButton.addActionListener(e -> {
                int selectedColumn = table.getSelectedColumn();
                if (selectedColumn != -1) {
                    model.removeColumn(selectedColumn);
                }
            });

            buttonPanel.add(addRowButton);
            buttonPanel.add(removeRowButton);
            buttonPanel.add(addColumnButton);
            buttonPanel.add(removeColumnButton);

            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}