package com.bitcamp.centro.estetico.gui;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.models.Turn;
import com.bitcamp.centro.estetico.models.TurnType;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitBtnField;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitTxf;

public class TurnPanel extends AbstractBasePanel<Turn>{

    private static JSplitDatePicker startDatePicker;
    private static JSplitDatePicker endDatePicker;
    private static JSplitComboBox<TurnType> turnTypesCombobox;
    private static JSplitTxf notes;
    private static JSplitBtn showEmployees;

    public TurnPanel(JFrame parent) {
        super(parent);

    }

    @Override
    public void search() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public void insertElement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertElement'");
    }

    @Override
    public void updateElement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateElement'");
    }

    @Override
    public void deleteElement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteElement'");
    }

    @Override
    public void disableElement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableElement'");
    }

    @Override
    public void populateTable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'populateTable'");
    }

    @Override
    public void clearTxfFields() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
    }

    @Override
    public ListSelectionListener getTableListSelectionListener() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTableListSelectionListener'");
    }

    @Override
    public boolean isDataValid() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
    }

}
