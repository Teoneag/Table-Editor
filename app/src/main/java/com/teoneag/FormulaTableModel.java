package com.teoneag;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormulaTableModel extends AbstractTableModel {
    private final List<List<String>> data;
    private final List<List<String>> formulas;

    public FormulaTableModel(int rows, int cols) {
        data = new ArrayList<>();
        formulas = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<String> dataRow = new ArrayList<>();
            List<String> formulaRow = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                dataRow.add("");
                formulaRow.add("");
            }
            data.add(dataRow);
            formulas.add(formulaRow);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return data.getFirst().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String value = (String) aValue;
        formulas.get(rowIndex).set(columnIndex, value);
        data.get(rowIndex).set(columnIndex, evaluateFormula(value));
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public String getFormulaAt(int rowIndex, int columnIndex) {
        return formulas.get(rowIndex).get(columnIndex);
    }

    private String evaluateFormula(String formula) {
        return Evaluator.evaluate(formula, data);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void addRow() {
        int cols = getColumnCount();
        List<String> newRow = new ArrayList<>();
        List<String> newFormulaRow = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            newRow.add("");
            newFormulaRow.add("");
        }
        data.add(newRow);
        formulas.add(newFormulaRow);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removeRow(int rowIndex) {
        if (data.size() <= 1) {
            return; // Prevent removal if only one row remains.
        }
        data.remove(rowIndex);
        formulas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addColumn() {
        for (List<String> row : data) {
            row.add("");
        }
        for (List<String> row : formulas) {
            row.add("");
        }
        fireTableStructureChanged();
    }

    public void removeColumn(int columnIndex) {
        if (getColumnCount() <= 1) {
            return; // Prevent removal if only one column remains.
        }
        for (List<String> row : data) {
            row.remove(columnIndex);
        }
        for (List<String> row : formulas) {
            row.remove(columnIndex);
        }
        fireTableStructureChanged();
    }
}

class FormulaCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField;

    public FormulaCellEditor() {
        textField = new JTextField();
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText((String) value);
        return textField;
    }
}

class FormulaCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof String) {
            setText((String) value);
        }
        return c;
    }
}