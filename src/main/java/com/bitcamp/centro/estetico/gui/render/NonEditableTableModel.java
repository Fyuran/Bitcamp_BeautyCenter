package com.bitcamp.centro.estetico.gui.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.models.Model;

public class NonEditableTableModel<T extends Model> extends DefaultTableModel { // non editable model for tables
    private static final long serialVersionUID = 746772300141997929L;
    private List<T> objects = new ArrayList<>();

    public NonEditableTableModel(List<T> colData) {
        super();
        if (colData.isEmpty())
            return;
        addRows(colData);
    }

    public NonEditableTableModel(T... objs) {
        this(List.of(objs));
    }

    public NonEditableTableModel() {
        super();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if(getValueAt(row, column) instanceof JButton) {
            return true;
        }
        return false;
    }
    
    public void addRow(T obj) {
        if (this.columnIdentifiers.isEmpty() || this.columnIdentifiers == null) {
            setColumnNames(obj);
        }

        insertRow(getRowCount(), new Vector<Object>(obj.toTableRow().values()));
        objects.add(obj);
    }

    public void addRows(List<T> objs) {
        if (this.columnIdentifiers.isEmpty() || this.columnIdentifiers == null) {
            setColumnNames(objs);
        }

        objs.stream().forEach(o -> addRow(o));
    }

    public void addRows(T... objs) {
        addRows(List.of(objs));
    }

    public List<T> getObjs() {
        return objects;
    }

    public T getObjAt(int row) {
        return objects.get(row);
    }

    public List<T> getObjsAt(int[] rows) {
        List<T> objTs = new ArrayList<>();
        for(int row : rows) {
            try {
                objTs.add(objects.get(row));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return objTs;
    }

    public void setColumnNames(T obj) {
        setColumnIdentifiers(new Vector<>(obj.toTableRow().keySet()));
    }

    public void setColumnNames(List<T> objs) {
        if (objs.isEmpty())
            return;
        setColumnNames(objs.get(0));
    }

    public void setColumnNames(T... objs) {
        if (objs.length <= 0)
            return;
        setColumnNames(objs[0]);
    }

    @Override
    public void setRowCount(int rowCount) {
        super.setRowCount(rowCount);
        this.objects = objects.subList(0, rowCount);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(!dataVector.isEmpty()) {
            if(getValueAt(0, columnIndex) instanceof JButton) {
                return JButton.class;
            }
        }

        return Object.class;
    }

    
}
