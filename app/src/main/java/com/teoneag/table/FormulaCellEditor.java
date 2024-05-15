package com.teoneag.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class FormulaCellEditor extends AbstractCellEditor implements TableCellEditor {
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
