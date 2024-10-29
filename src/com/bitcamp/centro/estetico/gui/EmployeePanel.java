package com.bitcamp.centro.estetico.gui;

import java.time.LocalDate;
import java.util.Collections;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.*;
import com.bitcamp.centro.estetico.utils.*;
import com.bitcamp.centro.estetico.utils.inputValidator.emptyInputException;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class EmployeePanel extends BasePanel<Employee> {
	private static final long serialVersionUID = 1L;

	private static EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
	private static UserCredentialsDAO userCredentialsDAO = UserCredentialsDAO.getInstance();
	
	private static int id = -1;
	private static long selectedSerial;
	private static boolean isEnabled = false;

	private static JSplitLbTxf txfSurname;
	private static JSplitLbTxf txfName;
	private static JSplitLbTxf txfMail;
	private static JSplitLbTxf txfPhone;
	private static JSplitLbTxf txfAddress;
	private static JSplitDatePicker txfBirthday;
	private static JSplitDatePicker txfHired;
	private static JSplitDatePicker txfTermination;
	private static JSplitLbTxf txfUsername;
	private static JSplitLbPf txfPassword;
	private static JSplitLbTxf txfBirthplace;
	private static JSplitRadialButtons genderBtns;
	private static JSplitLbTxf txfNotes;
	private static JSplitComboBox<Roles> roleComboBox;
	private static JSplitLbTxf txfIban;

	public EmployeePanel() {
		setSize(1024, 768);
		setName("Operatori");
		setTitle("GESTIONE OPERATORI");

		table.setModel(employeeModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(employeeModel));

		txfName = new JSplitLbTxf("Nome");
		txfSurname = new JSplitLbTxf("Cognome");
		txfMail = new JSplitLbTxf("Mail");
		txfPhone = new JSplitLbTxf("Telefono");
		txfAddress = new JSplitLbTxf("Indirizzo");
		txfBirthday = new JSplitDatePicker("Data di nascita");
		txfHired = new JSplitDatePicker("Assunto");
		txfTermination = new JSplitDatePicker("Scadenza");
		roleComboBox = new JSplitComboBox<>("Ruolo");
		txfUsername = new JSplitLbTxf("Username");
		txfPassword = new JSplitLbPf("Password");
		txfBirthplace = new JSplitLbTxf("Luogo di nascita");
		genderBtns = new JSplitRadialButtons("Genere", "Maschio", "Femmina");
		txfNotes = new JSplitLbTxf("Note");
		txfIban = new JSplitLbTxf("Iban");

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

		for (Roles r : Roles.values()) {
			roleComboBox.addItem(r);
		}
	}

	private void fillRowsByFilter() {

		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}

		clearTxfFields();
		employeeModel.setRowCount(0);

		if (employees.isEmpty()) {
			lbOutput.setText("Lista operatori vuota");
			return;
		}
		for (Employee employee : employees) {
			if (employee.isEnabled() && employee.getTerminationDate() == null
					&& employee.getSurname().equalsIgnoreCase(txfSearchBar.getText())) {
				employeeModel.addRow(employee.toTableRow());
			}
		}

		txfSearchBar.setText("");
	}

	private static boolean gender() {
		if(genderBtns.getSelectedIndex() == 1) return true;
		return false;
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
		} catch (inputValidatorException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}

		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthplace = txfBirthplace.getText();

		LocalDate BoD = txfBirthday.getDate();
		LocalDate hired = txfHired.getDate();
		LocalDate termination = txfTermination.getDate();

		String notes = txfNotes.getText();

		long employeeSerial = Employee.generateSerial();
		Roles role = Roles.toEnum(roleComboBox.getSelectedItem().toString());

		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();

		UserCredentials cred = new UserCredentials(username, password, address, iban, phone, mail);
		if(!userCredentialsDAO.isUsernameUnique(username)) return;
		try {
			cred = userCredentialsDAO.insert(cred).orElseThrow();
		} catch (NoSuchElementException e) {
			JOptionPane.showMessageDialog(null, "Campi già esistenti");
			return;
		}

		UserDetails det = new UserDetails(name, surname, gender(), BoD, birthplace, notes);
		Employee employee = new Employee(det, cred, employeeSerial, role, Collections.emptyList(), hired, termination);
		
		lbOutput.setText("Nuovo utente creato");

		employeeDAO.insert(employee);
		refreshTable();
	}

	@Override
	public void updateElement() {
		try {
			isDataValid();
		} catch (inputValidatorException e) {
			if (!(e instanceof emptyInputException)) { // fields can be empty
				JOptionPane.showMessageDialog(null, e.getMessage());
				return;
			}
			e.errorComponent.setBorder(UIManager.getBorder("TextField.border")); //reset field's border in update mode
		}
		
		if (id <= 0) {
			JOptionPane.showMessageDialog(null, "Nessun operatore selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		Roles role = (Roles) roleComboBox.getSelectedItem();
		if(employeeModel.getRowCount() == 1 && role != Roles.ADMIN) {
			JOptionPane.showMessageDialog(null, "Non puoi cambiare ruolo all'unico amministratore esistente", "Errore",
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
		long employeeSerial = selectedSerial;
		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();

		Employee employee = employees.parallelStream().filter(e -> e.getId() == id).findFirst().get();
		employee.setName(name);
		employee.setSurname(surname);
		employee.setBirthplace(birthplace);
		employee.setBoD(BoD);
		employee.setHiredDate(hired);
		employee.setTerminationDate(termination);
		employee.setNotes(notes);
		employee.setEmployeeSerial(employeeSerial);
		employee.setRole(role);
		employee.setUsername(username);
		employee.setPassword(password);
		employee.setAddress(address);
		employee.setIban(iban);
		employee.setPhone(phone);
		employee.setMail(mail);

		lbOutput.setText(name + " " + surname + " modificato");
		
		employeeDAO.update(id, employee);
		refreshTable();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) return;
		if(employeeModel.getRowCount() == 1) {
			JOptionPane.showMessageDialog(null, "Non puoi cancellare l'ultimo utente rimasto", "Errore",
			JOptionPane.ERROR_MESSAGE);
			return;
		}
		lbOutput.setText("Operatore cancellato");
		employeeDAO.delete(id);
		refreshTable();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) return;
		if(employeeModel.getRowCount() == 1) {
			JOptionPane.showMessageDialog(null, "Non puoi disabilitare l'ultimo utente rimasto", "Errore di disabilitazione",
			JOptionPane.ERROR_MESSAGE);
			return;
		}		
		lbOutput.setText(!isEnabled ? "Operatore abilitato" : "Operatore disabilitato");
		employeeDAO.toggle(id);
		refreshTable();
	}

	@Override
	public void populateTable() {
		isRefreshing = true;
		employeeModel.setRowCount(0);
		clearTxfFields();

		employees = employeeDAO.getAll();
		if (!employees.isEmpty()) {
			employees.parallelStream()
					.forEach(e -> employeeModel.addRow(e.toTableRow()));
		} else {
			lbOutput.setText("Lista Operatori vuota");
		}
		isRefreshing = false;
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

	/*"ID", "Genere", "Numero seriale", "Nome", "Cognome", "Data di nascita", "Assunzione", "Scadenza",
	"Ruolo", "Username", "Indirizzo", "Città di provenienza", "Mail", "Telefono", "IBAN", "Abilitato" */
	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) return;
				
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) return;

				var values = getRowMap(table, employeeModel);
				
				String name = (String) values.get( "Nome");
				String surname = (String) values.get( "Cognome");
				LocalDate BoD = (LocalDate) values.get( "Data di Nascita");
				LocalDate hiredDate = (LocalDate) values.get( "Assunzione");
				LocalDate terminationDate = (LocalDate) values.get( "Scadenza");
				Roles role = (Roles) values.get( "Ruolo");
				String username = (String) values.get( "Username");
				String address = (String) values.get( "Indirizzo");
				String birthplace = (String) values.get("Città natale");
				String mail = (String) values.get( "Email");
				String phone = (String) values.get( "Telefono");
				String iban = (String) values.get( "IBAN");
				Gender gender = (Gender) values.get( "Genere");
				String notes = (String) values.get( "Note");

				id = (int) values.get( "ID");
				selectedSerial = (long) values.get( "Numero seriale");
				isEnabled = (boolean) values.get( "Abilitato");

				txfName.setText(name);
				txfSurname.setText(surname);
				txfBirthday.setDate(BoD);
				txfHired.setDate(hiredDate);
				txfTermination.setDate(terminationDate);
				roleComboBox.setSelectedItem(role);
				txfUsername.setText(username);
				txfAddress.setText(address);
				txfBirthplace.setText(birthplace);
				txfMail.setText(mail);
				txfPhone.setText(phone);
				txfIban.setText(iban);
				txfNotes.setText(notes);
				
				if (gender == Gender.FEMALE) {
					genderBtns.setSelected(1);
				} else {
					genderBtns.setSelected(0);
				}
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			inputValidator.validateName(txfName);
			inputValidator.validateSurname(txfSurname);
			inputValidator.validateIban(txfIban);
			inputValidator.validateEmail(txfMail);
			inputValidator.validatePhoneNumber(txfPhone);
			inputValidator.validateAlphanumeric(txfAddress, "Indirizzo");
			inputValidator.validateAlphanumeric(txfUsername, "Username");
			inputValidator.validatePassword(txfPassword);
			inputValidator.validateAlphanumeric(txfBirthplace, "Luogo di nascita");
			inputValidator.isValidCity(txfBirthplace);
		} catch (inputValidatorException e) {
			throw e;
		}

		return txfBirthday.isTextFieldValid();
	}
}
// Prova
