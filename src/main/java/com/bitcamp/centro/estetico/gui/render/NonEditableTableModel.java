package com.bitcamp.centro.estetico.gui.render;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel { // non editable model for tables
    private static final long serialVersionUID = 746772300141997929L;

    public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    private NonEditableTableModel() {}

    @Override
    public boolean isCellEditable(int row, int column) {
        // all cells false
        return false;
    }
}
