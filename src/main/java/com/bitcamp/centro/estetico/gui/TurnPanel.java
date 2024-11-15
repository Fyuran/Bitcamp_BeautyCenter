package com.bitcamp.centro.estetico.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Turn;
import com.bitcamp.centro.estetico.models.TurnType;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.InputValidator.InvalidInputException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDateTimePicker;
import com.bitcamp.centro.estetico.utils.JSplitTxf;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class TurnPanel extends AbstractBasePanel<Turn> {

    private static Turn selectedData = new Turn();
    private static List<Employee> returnEmployees = new ArrayList<>();

    private static JSplitDateTimePicker startDatePicker;
    private static JSplitDateTimePicker endDatePicker;
    private static JSplitComboBox<TurnType> turnTypesCombobox;
    private static JSplitTxf notesTxf;
    private static JSplitBtn employeesBtn;

    public TurnPanel(JFrame parent) {
        super(parent);
        setName("Turni");
        setTitle("GESTIONE TURNI");
        setSize(1300, 768);

        employeesBtn = new JSplitBtn("Operatori", "Scelta Operatori");
        employeesBtn.addActionListener(l1 -> {
            ModelChooser<Employee> picker = new ModelChooser<>(parent, "Scelta Operatori",
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, returnEmployees);

            employees.clear();
            employees.addAll(DAO.getAll(Employee.class));
            var available = employees
                    .parallelStream()
                    .filter(e -> e.isEnabled())
                    .toList();

            if (!available.isEmpty()) {
                picker.addRows(available);
            } else
                picker.getLbOutput().setText("Lista vuota");

            picker.setVisible(true);
        });

        startDatePicker = new JSplitDateTimePicker("Inizio");
        endDatePicker = new JSplitDateTimePicker("Fine");
        notesTxf = new JSplitTxf("Note");

        turnTypesCombobox = new JSplitComboBox<>("Tipo");
        for (TurnType type : TurnType.values()) {
            turnTypesCombobox.addItem(type);
        }
        turnTypesCombobox.setSelectedIndex(0);

        actionsPanel.add(startDatePicker);
        actionsPanel.add(endDatePicker);
        actionsPanel.add(notesTxf);
        actionsPanel.add(turnTypesCombobox);
        actionsPanel.add(employeesBtn);
    }

    @Override
    public void insertElement() {
        try { // all fields must be filled
			isDataValid();
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}
        
        LocalDateTime start = startDatePicker.getDateTimePermissive();
        LocalDateTime end = endDatePicker.getDateTimePermissive();
        TurnType type = turnTypesCombobox.getSelectedItem();
        String notes = notesTxf.getText();

        List<Employee> assignedEmployees = null;
        if (returnEmployees != null) {
            assignedEmployees = returnEmployees;
        }

        Turn turn = new Turn(start, end, type, notes, assignedEmployees);

        DAO.insert(turn);
        lbOutput.setText("Nuovo turno creato");
        refresh();
    }

    @Override
    public void updateElement() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Nessun turno selezionato");
            return; // do not allow invalid ids to be passed to update
        }
        if (selectedData.getId() == null || !selectedData.isEnabled())
            return;
        try { // all fields must be filled
            isDataValid();
        } catch (InputValidatorException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage());
            return;
        }

        LocalDateTime start = startDatePicker.getDateTimePermissive();
        LocalDateTime end = endDatePicker.getDateTimePermissive();
        TurnType type = turnTypesCombobox.getSelectedItem();
        String notes = notesTxf.getText();
        List<Employee> assignedEmployees = null;
        if (returnEmployees != null) {
            assignedEmployees = returnEmployees;
        }

        selectedData.setStart(start);
        selectedData.setEnd(end);
        selectedData.setType(type);
        selectedData.setNotes(notes);
        selectedData.setAssignedEmployees(assignedEmployees);

        DAO.update(selectedData);
        lbOutput.setText("Turno aggiornato");
        refresh();
    }

    @Override
    public void deleteElement() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Nessun turno selezionato");
            return; // do not allow invalid ids to be passed to update
        }
        if (selectedData.getId() == null || !selectedData.isEnabled())
            return;

        DAO.delete(selectedData);
        lbOutput.setText("Turno cancellato");
        refresh();
    }

    @Override
    public void disableElement() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Nessun turno selezionato");
            return; // do not allow invalid ids to be passed to update
        }
        if (selectedData.getId() == null || !selectedData.isEnabled())
            return;

        DAO.toggle(selectedData);
        lbOutput.setText("Turno disabilitato");
        refresh();
    }

    @Override
    public void populateTable() {
        turns.clear();
        turns.addAll(DAO.getAll(Turn.class));
        if (!turns.isEmpty()) {
            model.addRows(turns);
        } else {
            lbOutput.setText("Lista Turni vuota");
        }
    }

    @Override
    public void clearTxfFields() {
        startDatePicker.setDateTimePermissive(LocalDateTime.now());
        endDatePicker.setDateTimePermissive(LocalDateTime.now());
        turnTypesCombobox.setSelectedIndex(0);
        notesTxf.setText("");

        returnEmployees.clear();
    }

    @Override
    public ListSelectionListener getTableListSelectionListener() {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (event.getValueIsAdjusting())
                    return;
                int selectedRow = table.getSelectedRow();
                if (selectedRow < 0)
                    return;

                selectedData = model.getObjAt(selectedRow);
                if (selectedData == null || !selectedData.isEnabled())
                    return;

                startDatePicker.setDateTimePermissive(selectedData.getStart());
                endDatePicker.setDateTimePermissive(selectedData.getEnd());
                turnTypesCombobox.setSelectedItem(selectedData.getType());
                notesTxf.setText(selectedData.getNotes());

                returnEmployees.clear();
            }
        };
    }

    @Override
    public boolean isDataValid() {
        try {
            LocalDateTime start = startDatePicker.getDateTimePermissive();
            LocalDateTime end = endDatePicker.getDateTimePermissive();

            if(start.isAfter(end))
                throw new InvalidInputException("Data iniziale dopo data finale", startDatePicker);
            if(end.isBefore(start))
                throw new InvalidInputException("Data finale dopo data iniziale", endDatePicker);
        } catch (InputValidatorException e) {
            throw e;
        }

        return true;
    }

}
