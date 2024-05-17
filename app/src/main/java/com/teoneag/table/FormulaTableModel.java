package com.teoneag.table;

import com.teoneag.formula.Evaluator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormulaTableModel extends AbstractTableModel {
    private final List<List<String>> formulas;
    private final List<List<String>> data;

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

    /**
     * Sets the value of a cell in the table & updates all cells that depend on it.
     * @param aValue   value to assign to cell
     * @param rowIndex   row of cell
     * @param columnIndex  column of cell
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String value = (String) aValue;
        formulas.get(rowIndex).set(columnIndex, value);
        data.get(rowIndex).set(columnIndex, evaluateFormula(value));
        fireTableCellUpdated(rowIndex, columnIndex);

        String cellName = getCellName(rowIndex, columnIndex);
        for (int i = 0; i < formulas.size(); i++) {
            for (int j = 0; j < formulas.getFirst().size(); j++) {
                if (formulas.get(i).get(j).toLowerCase().contains(cellName)) {
                    data.get(i).set(j, evaluateFormula(formulas.get(i).get(j)));
                    fireTableCellUpdated(i, j);
                }
            }
        }
    }

    public String getFormulaAt(int rowIndex, int columnIndex) {
        return formulas.get(rowIndex).get(columnIndex);
    }

    private String evaluateFormula(String formula) {
        if (!formula.startsWith("=")) {
            return formula;
        }
        try {
            return Evaluator.evaluate(formula.replace("=", "").toLowerCase(), this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error in formula syntax. If you don't want a formula, don't start with '='.\nEvaluating this: "
                    + formula + ", you got this: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "ERROR";
        }
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

    public double getCellValue(String cellReference) {
        // ab37 -> (37, 28)
        String letters = cellReference.replaceAll("\\d", "");
        String numbers = cellReference.replaceAll("\\D", "");

        int row = Integer.parseInt(numbers) - 1;
        int column = 0;
        for (int i = 0; i < letters.length(); i++) {
            column = column * 26 + letters.charAt(i) - 'a' + 1;
        }
        column--;
        return Double.parseDouble(data.get(row).get(column));
    }

    public static String getCellName(int row, int column) {
        StringBuilder cellName = new StringBuilder();
        while (column >= 0) {
            cellName.insert(0, (char) (column % 26 + 'a'));
            column = (column / 26) - 1;
        }
        row++;
        return cellName.toString() + row;
    }
}

class ResultCellRenderer extends DefaultTableCellRenderer {
    @Override
    protected void setValue(Object value) {
        setText(value != null ? value.toString() : "");
    }
}

class FormulaCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField = new JTextField();;

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        FormulaTableModel model = (FormulaTableModel) table.getModel();
        textField.setText(model.getFormulaAt(row, column));
        return textField;
    }
}
