package com.bitcamp.centro.estetico.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.EmptyInputException;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitPf;
import com.bitcamp.centro.estetico.utils.JSplitRadialButtons;
import com.bitcamp.centro.estetico.utils.JSplitTxf;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class EmployeePanel extends AbstractBasePanel<Employee> {
	private static final long serialVersionUID = 1L;

	private static Employee selectedData = new Employee();
	private static List<Treatment> returnTreatments = new ArrayList<>();

	private static JSplitTxf txfSurname;
	private static JSplitTxf txfName;
	private static JSplitTxf txfMail;
	private static JSplitTxf txfPhone;
	private static JSplitTxf txfAddress;
	private static JSplitDatePicker txfBirthday;
	private static JSplitDatePicker txfHired;
	private static JSplitDatePicker txfTermination;
	private static JSplitTxf txfUsername;
	private static JSplitPf txfPassword;
	private static JSplitTxf txfBirthplace;
	private static JSplitRadialButtons genderBtns;
	private static JSplitTxf txfNotes;
	private static JSplitBtn treatmentBtn;
	private static JSplitComboBox<Roles> roleComboBox;
	private static JSplitTxf txfIban;

	public EmployeePanel(JFrame parent) {
		super(parent);
		setSize(1300, 768);
		setName("Operatori");
		setTitle("GESTIONE OPERATORI");

		txfName = new JSplitTxf("Nome");
		txfSurname = new JSplitTxf("Cognome");
		txfMail = new JSplitTxf("Mail");
		txfPhone = new JSplitTxf("Telefono");
		txfAddress = new JSplitTxf("Indirizzo");
		txfBirthday = new JSplitDatePicker("Data di nascita");
		txfHired = new JSplitDatePicker("Assunto");
		txfTermination = new JSplitDatePicker("Scadenza");
		roleComboBox = new JSplitComboBox<>("Ruolo");
		txfUsername = new JSplitTxf("Username");
		txfPassword = new JSplitPf("Password");
		txfBirthplace = new JSplitTxf("Luogo di nascita");
		genderBtns = new JSplitRadialButtons("Genere", "Maschio", "Femmina");
		txfNotes = new JSplitTxf("Note");
		txfIban = new JSplitTxf("Iban");
		treatmentBtn = new JSplitBtn("Trattamenti", "Scegli Trattamenti");
		treatmentBtn.addActionListener(l -> {
			ModelChooser<Treatment> picker = new ModelChooser<>(parent, "Trattamenti",
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, returnTreatments);

			treatments = DAO.getAll(Treatment.class);
			var available = treatments
			.stream()
			.filter(t -> t.isEnabled())
			.toList();

			if (!treatments.isEmpty()) {
				picker.getModel().addRows(available);
			} else {
				picker.getLbOutput().setText("Lista vuota");
			}

			picker.setVisible(true);
		});

		actionsPanel.add(txfName);
		actionsPanel.add(txfSurname);
		actionsPanel.add(txfMail);
		actionsPanel.add(txfPhone);
		actionsPanel.add(txfAddress);
		actionsPanel.add(txfBirthday);
		actionsPanel.add(txfHired);
		actionsPanel.add(txfTermination);
		actionsPanel.add(roleComboBox);
		actionsPanel.add(txfUsername);
		actionsPanel.add(txfPassword);
		actionsPanel.add(txfBirthplace);
		actionsPanel.add(genderBtns);
		actionsPanel.add(txfNotes);
		actionsPanel.add(txfIban);
		actionsPanel.add(treatmentBtn);

		for (Roles r : Roles.values()) {
			roleComboBox.addItem(r);
		}
		roleComboBox.setSelectedIndex(0);
	}

	private static Gender getGender() {
		if (genderBtns.getSelectedIndex() == 1)
			return Gender.FEMALE;
		return Gender.MALE;
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

		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthplace = txfBirthplace.getText();

		LocalDate BoD = txfBirthday.getDate();
		LocalDate hired = txfHired.getDate();
		LocalDate termination = txfTermination.getDate();

		String notes = txfNotes.getText();

		Roles role = Roles.toEnum(roleComboBox.getSelectedItem().toString());

		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();
		List<Treatment> newTreatments = null;
		if (!returnTreatments.isEmpty()) {
			newTreatments = returnTreatments;
		}

		UserCredentials cred = new UserCredentials(username, password, address, iban, phone, mail, null);
		UserDetails det = new UserDetails(name, surname, getGender(), BoD, birthplace, notes);
		Employee employee = new Employee(det, cred, role, newTreatments, null, hired, termination);

		cred.setUser(employee);
		DAO.insert(employee);

		lbOutput.setText("Nuovo operatore creato");
		refresh();
	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun operatore selezionato");
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

		Roles role = (Roles) roleComboBox.getSelectedItem();
		if (model.getRowCount() == 1 && role != Roles.ADMIN) {
			JOptionPane.showMessageDialog(parent, "Non puoi cambiare ruolo all'unico amministratore esistente",
					"Errore",
					JOptionPane.ERROR_MESSAGE);

			return;
		}

		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthplace = txfBirthplace.getText();
		LocalDate BoD = txfBirthday.getDate();
		LocalDate hired = txfHired.getDate();
		LocalDate termination = txfTermination.getDate();
		String notes = txfNotes.getText();
		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();

		selectedData.setName(name);
		selectedData.setSurname(surname);
		selectedData.setBirthplace(birthplace);
		selectedData.setBoD(BoD);
		selectedData.setHiredDate(hired);
		selectedData.setTerminationDate(termination);
		selectedData.setNotes(notes);
		selectedData.setRole(role);
		selectedData.setUsername(username);
		selectedData.setPassword(password);
		selectedData.setAddress(address);
		selectedData.setIban(iban);
		selectedData.setPhone(phone);
		selectedData.setMail(mail);
		if (returnTreatments.isEmpty()) {
			selectedData.setEnabledTreatments(null);
		} else {
			selectedData.setEnabledTreatments(returnTreatments);
		}

		DAO.update(selectedData);

		lbOutput.setText(name + " " + surname + " modificato");
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun operatore selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		if (model.getRowCount() == 1) {
			JOptionPane.showMessageDialog(parent, "Non puoi cancellare l'ultimo operatore rimasto", "Errore",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		lbOutput.setText("Operatore cancellato");
		DAO.delete(selectedData);
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun operatore selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;

		if (model.getRowCount() == 1) {
			JOptionPane.showMessageDialog(parent, "Non puoi disabilitare l'ultimo operatore rimasto",
					"Errore di disabilitazione",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		lbOutput.setText(selectedData.isEnabled() ? "Operatore abilitato" : "Operatore disabilitato");
		DAO.toggle(selectedData);
		refresh();
	}

	@Override
	public void populateTable() {
		employees = DAO.getAll(Employee.class);
		if (!employees.isEmpty()) {
			model.addRows(employees);
		} else {
			lbOutput.setText("Lista Operatori vuota");
		}
	}

	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfSurname.setText("");
		txfBirthday.clear();
		txfHired.clear();
		txfPhone.setText("");
		txfMail.setText("");
		txfAddress.setText("");
		txfIban.setText("");
		txfUsername.setText("");
		txfPassword.setText("");
		txfBirthplace.setText("");
		txfNotes.setText("");
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
				if (!selectedData.isEnabled())
					return;

				txfName.setText(selectedData.getName());
				txfSurname.setText(selectedData.getSurname());
				txfBirthday.setDate(selectedData.getBirthday());
				txfHired.setDate(selectedData.getHiredDate());
				txfTermination.setDate(selectedData.getTerminationDate());
				roleComboBox.setSelectedItem(selectedData.getRole());
				txfUsername.setText(selectedData.getUsername());
				txfAddress.setText(selectedData.getAddress());
				txfBirthplace.setText(selectedData.getBirthplace());
				txfMail.setText(selectedData.getMail());
				txfPhone.setText(selectedData.getPhone());
				txfIban.setText(selectedData.getIban());
				txfNotes.setText(selectedData.getNotes());

				if (selectedData.getGender() == Gender.FEMALE) {
					genderBtns.setSelected(1);
				} else {
					genderBtns.setSelected(0);
				}
				
				returnTreatments.clear();
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			InputValidator.validateName(txfName);
			InputValidator.validateSurname(txfSurname);
			InputValidator.validateIban(txfIban);
			InputValidator.validateEmail(txfMail);
			InputValidator.validatePhoneNumber(txfPhone);
			InputValidator.validateAlphanumeric(txfAddress, "Indirizzo");
			InputValidator.validateAlphanumeric(txfUsername, "Username");
			InputValidator.validatePassword(txfPassword);
			InputValidator.validateAlphanumeric(txfBirthplace, "Luogo di nascita");
			InputValidator.isValidCity(txfBirthplace);
		} catch (InputValidatorException e) {
			throw e;
		}

		return txfBirthday.isTextFieldValid();
	}
}
// Prova
