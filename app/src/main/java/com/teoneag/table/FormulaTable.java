package com.teoneag.table;

import javax.swing.*;

public class FormulaTable extends JTable {
    public FormulaTable(FormulaTableModel model) {
        super(model);
        this.setDefaultRenderer(Object.class, new ResultCellRenderer());
        this.setDefaultEditor(Object.class, new FormulaCellEditor());
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // ToDo
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("Mouse clicked");
//                int row = rowAtPoint(e.getPoint());
//                int col = columnAtPoint(e.getPoint());
//                if (isEditing()) {
//                    int editingRow = getEditingRow();
//                    int editingCol = getEditingColumn();
//                    if (editingRow != row || editingCol != col) {
//                        String cell = model.getFormulaAt(row, col);
//                        cell += FormulaTableModel.getCellName(editingRow, editingCol);
//                        model.setFormulaAt(editingRow, editingCol, cell);
//                    }
//                } else {
//                    editCellAt(row, col);
//                    ((JTextField) getEditorComponent()).selectAll();
//                }
//            }
//        });
    }
}
