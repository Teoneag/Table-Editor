package com.teoneag.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class FormulaTable {
    public static JTable create(FormulaTableModel model) {
        final JTable table = new JTable(model) {
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
                    ((JTextComponent) getEditorComponent()).setText(model.getFormulaAt(row, column));
                }
                return isEditable;
            }
        };
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        return table;
    }
}
