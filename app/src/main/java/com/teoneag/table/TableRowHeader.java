// inspired from https://stackoverflow.com/questions/10620448/jtable-row-header

package com.teoneag.table;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;

public class TableRowHeader extends JList<String> implements TableModelListener {
    private final JTable table;

    public TableRowHeader(JTable table) {
        this.table = table;

        setAutoscrolls(false);
        setCellRenderer(new RowHeaderRenderer());
        setFixedCellHeight(table.getRowHeight());
        setFixedCellWidth(50);
        setFocusable(false);
        setModel(new TableListModel());
        setOpaque(false);
        setSelectionModel(table.getSelectionModel());
        table.getModel().addTableModelListener(this);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE) {
            repaint();
        }
    }

    private class TableListModel extends AbstractListModel<String> {
        @Override
        public int getSize() {
            return table.getRowCount();
        }

        @Override
        public String getElementAt(int index) {
            return String.valueOf(index + 1);
        }
    }

    private class RowHeaderRenderer extends DefaultListCellRenderer {
        RowHeaderRenderer() {
            setHorizontalAlignment(CENTER);
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setFont(table.getTableHeader().getFont());
            setBackground(table.getTableHeader().getBackground());
            setForeground(table.getTableHeader().getForeground());
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getTableHeader().getBackground());
            setText(value != null ? value.toString() : "");
            return this;
        }
    }
}