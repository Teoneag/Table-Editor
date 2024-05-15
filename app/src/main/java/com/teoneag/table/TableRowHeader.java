package com.teoneag.table;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;

public class TableRowHeader extends JList implements TableModelListener {
    private JTable table;

    @SuppressWarnings("unchecked")
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

    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE) {
            repaint();
        }
    }

    /*
     *  Use the table to implement the ListModel
     */
    class TableListModel extends AbstractListModel {
        public int getSize() {
            return table.getRowCount();
        }

        public Object getElementAt(int index) {
            return String.valueOf(index + 1);
        }
    }

    /*
     *  Use the table row header properties to render each cell
     */
    class RowHeaderRenderer extends DefaultListCellRenderer {
        RowHeaderRenderer() {
            setHorizontalAlignment(CENTER);
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setFont(table.getTableHeader().getFont());
            setBackground(table.getTableHeader().getBackground());
            setForeground(table.getTableHeader().getForeground());
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getTableHeader().getBackground());
            }

            setText((value == null) ? "" : value.toString());

            return this;
        }
    }
}