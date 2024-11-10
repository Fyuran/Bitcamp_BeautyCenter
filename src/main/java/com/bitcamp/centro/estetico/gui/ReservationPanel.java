package com.bitcamp.centro.estetico.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Reservation;
import com.bitcamp.centro.estetico.models.ReservationState;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.Turn;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.EmptyInputException;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDateTimePicker;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class ReservationPanel extends AbstractBasePanel<Reservation> {

	private static JSplitDateTimePicker dateTimePicker;
	private static JSplitComboBox<ReservationState> comboBoxReservationState;
	private static JSplitBtn customerBtn;
	private static JSplitBtn treatmentBtn;
	private static JSplitBtn employeesBtn;

	private static List<Customer> returnCustomer = new ArrayList<>();
	private static List<Treatment> returnTreatments = new ArrayList<>();
	private static List<Employee> returnEmployees = new ArrayList<>();

	private static Reservation selectedData = new Reservation();

	public ReservationPanel(JFrame parent) {
		super(parent);
		setName("Appuntamenti");
		setTitle("GESTIONE APPUNTAMENTI");
		setSize(1300, 768);

		dateTimePicker = new JSplitDateTimePicker("Data & Ora");
		customerBtn = new JSplitBtn("Cliente", "Scelta cliente");
		customerBtn.addActionListener(l1 -> {
			ModelChooser<Customer> picker = new ModelChooser<>(parent, "Scelta Cliente",
					ListSelectionModel.SINGLE_SELECTION, returnCustomer);

			customers = DAO.getAll(Customer.class);
			var available = customers
					.parallelStream()
					.filter(c -> c.isEnabled())
					.toList();

			if (!available.isEmpty()) {
				NonEditableTableModel<Customer> model = picker.getModel();
				model.addRows(available);
			} else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});
		treatmentBtn = new JSplitBtn("Trattamento", "Scelta Trattamento");
		treatmentBtn.addActionListener(l1 -> {
			ModelChooser<Treatment> picker = new ModelChooser<>(parent, "Scelta Trattamento",
					ListSelectionModel.SINGLE_SELECTION, returnTreatments);

			treatments = DAO.getAll(Treatment.class);
			var available = treatments
					.parallelStream()
					.filter(t -> t.isEnabled())
					.toList();

			if (!available.isEmpty()) {
				NonEditableTableModel<Treatment> model = picker.getModel();
				model.addRows(available);
			} else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});
		employeesBtn = new JSplitBtn("Operatori", "Scelta Operatori");
		employeesBtn.addActionListener(l1 -> {
			ModelChooser<Employee> picker = new ModelChooser<>(parent, "Scelta Operatori",
					ListSelectionModel.SINGLE_SELECTION, returnEmployees);

			employees = DAO.getAll(Employee.class);
			var available = employees
				.parallelStream()
				.filter(e -> {
					List<Turn> turns = e.getTurns();
					boolean isValid = false;
					if (turns.isEmpty()) {
						isValid = true;
					} else {
						isValid = turns.stream()
							.anyMatch(t -> { //find out if anyone has a turn within reservation's datetime
								var selectedDataTime = selectedData.getDateTime();
								return t.getStart().compareTo(selectedDataTime) <= 0 &&
										t.getEnd().compareTo(selectedDataTime) >= 0;
							});
					}
					return isValid;
				})
				.toList();

			if (!available.isEmpty()) {
				NonEditableTableModel<Employee> model = picker.getModel();
				model.addRows(available);
			} else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});

		comboBoxReservationState = new JSplitComboBox<>("Stato");
		for (ReservationState state : ReservationState.values()) {
			comboBoxReservationState.addItem(state);
		}
		comboBoxReservationState.setSelectedIndex(0);

		actionsPanel.add(dateTimePicker);
		actionsPanel.add(comboBoxReservationState);
		actionsPanel.add(customerBtn);
		actionsPanel.add(treatmentBtn);
		actionsPanel.add(employeesBtn);
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	public void insertElement() {
		try { // all fields must be filled
			isDataValid();
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		Customer customer = returnCustomer.getFirst();
		Treatment treatment = returnTreatments.getFirst();
		LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
		ReservationState state = comboBoxReservationState.getSelectedItem();

		Reservation reservation = new Reservation(customer, treatment, employees, dateTime, state);

		DAO.insert(reservation);

		lbOutput.setText("Nuovo appuntamento creato");
		refresh();
	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun appuntamento selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		try {
			isDataValid();
		} catch (InputValidatorException e) {
			if (!(e instanceof EmptyInputException)) { // fields can be empty
				JOptionPane.showMessageDialog(parent, e.getMessage());
				return;
			}
			e.errorComponent.setBorder(UIManager.getBorder("SplitPane.border")); // reset field's border in update mode
		}

		Customer customer = returnCustomer.getFirst();
		Treatment treatment = returnTreatments.getFirst();
		LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
		ReservationState state = comboBoxReservationState.getSelectedItem();

		selectedData.setCustomer(customer);
		selectedData.setTreatment(treatment);
		selectedData.setDateTime(dateTime);
		selectedData.setState(state);
		if (returnCustomer.isEmpty()) {
			selectedData.setCustomer(null);
		} else {
			selectedData.setCustomer(returnCustomer.getFirst());
		}
		if (returnTreatments.isEmpty()) {
			selectedData.setTreatment(null);
		} else {
			selectedData.setTreatment(returnTreatments.getFirst());
		}
		if (returnEmployees.isEmpty()) {
			selectedData.setEmployees(null);
		} else {
			selectedData.setEmployees(returnEmployees);
		}
		
		DAO.update(selectedData);
		lbOutput.setText("Appuntamento aggiornato");
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun appuntamento selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		DAO.delete(selectedData);
		lbOutput.setText("Appuntamento cancellato");
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun appuntamento selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		DAO.toggle(selectedData);
		lbOutput.setText("Appuntamento disabilitato");
		refresh();
	}

	@Override
	public void populateTable() {
		reservations = DAO.getAll(Reservation.class);
		if (!reservations.isEmpty()) {
			model.addRows(reservations);
		} else {
			lbOutput.setText("Lista Appuntamenti vuota");
		}
	}

	@Override
	public void clearTxfFields() {
		comboBoxReservationState.setSelectedIndex(0);
		dateTimePicker.setDateTimePermissive(LocalDateTime.now());
		returnCustomer.clear();
		returnEmployees.clear();
		returnTreatments.clear();
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

				dateTimePicker.setDateTimePermissive(selectedData.getDateTime());
				comboBoxReservationState.setSelectedItem(selectedData.getState());

				returnCustomer.clear();
				returnEmployees.clear();
				returnTreatments.clear();
				
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			InputValidator.validateBtn(customerBtn, returnCustomer);
			InputValidator.validateBtn(treatmentBtn, returnTreatments);
			InputValidator.validateBtn(employeesBtn, returnEmployees);
		} catch (InputValidatorException e) {
			throw e;
		}

		return true;
	}

}
