package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.*;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.emptyInputException;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;
import com.github.lgooddatepicker.components.DatePicker;

public class EmployeePanel extends BasePanel<Employee> {
	private static final long serialVersionUID = 1L;
	private static int selectedId = -1;

	private static JTextField txfSurname;
	private static JTextField txfName;
	private static JTextField txfMail;
	private static JTextField txfPhone;
	private static JTextField txfAddress;
	private static JComboBox<Treatment> treatmentsComboBox;
	private static DatePicker txfBirthday;
	private static DatePicker txfHired;
	private static DatePicker txfTermination;
	private static JTextField txfUsername;
	private static JPasswordField txfPassword;
	private static JLabel lbOutput;
	private static JTextField txfBirthplace;
	private static JRadioButton femaleRadioBtn;
	private static JRadioButton maleRadioBtn;
	private static JTextArea txfNotes;
	private static JComboBox<Roles> roleComboBox;
	private static long selectedSerial;
	private static JTextField txfIban;
	private static JPanel ctrlGroup;

	private static final int _ISENABLEDCOL = 15;
	/**
	 * Create the panel.
	 */
	public EmployeePanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("Operatori");
		setTitle("GESTIONE OPERATORI");
		scrollPane.setBounds(23, 60, 1031, 276);
		btnSearch.setBounds(206, 8, 40, 40);
		txfSearchBar.setBounds(23, 14, 168, 24);
		btnFilter.setBounds(256, 8, 40, 40);
		btnInsert.setBounds(914, 8, 40, 40);
		btnUpdate.setBounds(964, 8, 40, 40);
		btnDisable.setBounds(1014, 8, 40, 40);
		btnDelete.setBounds(864, 8, 40, 40);
		lbOutput.setBounds(306, 8, 438, 41);
		outputPanel.setBounds(23, 60, 959, 276);
		ctrlGroup.setBounds(40, 413, 1034, 344);

		table = new JTable(employeeModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(employeeModel, _ISENABLEDCOL));
		table.getSelectionModel().addListSelectionListener(getListSelectionListener());

		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(3, 26, 170, 14);
		ctrlGroup.add(lblName);
		lblName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfName = new JTextField();
		txfName.setBounds(169, 23, 220, 20);
		ctrlGroup.add(txfName);
		txfName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfName.setColumns(10);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setBounds(3, 65, 170, 17);
		ctrlGroup.add(lblSurname);
		lblSurname.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfSurname = new JTextField();
		txfSurname.setBounds(169, 63, 220, 20);
		ctrlGroup.add(txfSurname);
		txfSurname.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfSurname.setColumns(10);

		JLabel lblBirthday = new JLabel("Data di nascita:");
		lblBirthday.setBounds(3, 108, 170, 14);
		ctrlGroup.add(lblBirthday);
		lblBirthday.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfMail = new JTextField();
		txfMail.setBounds(805, 105, 220, 20);
		ctrlGroup.add(txfMail);
		txfMail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfMail.setColumns(10);

		txfPhone = new JTextField();
		txfPhone.setBounds(805, 145, 220, 20);
		ctrlGroup.add(txfPhone);
		txfPhone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfPhone.setColumns(10);

		txfAddress = new JTextField();
		txfAddress.setBounds(805, 23, 220, 20);
		ctrlGroup.add(txfAddress);
		txfAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfAddress.setColumns(10);

		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(623, 108, 170, 14);
		ctrlGroup.add(lblMail);
		lblMail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setBounds(623, 148, 170, 14);
		ctrlGroup.add(lblPhone);
		lblPhone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lblAddress = new JLabel("Indirizzo:");
		lblAddress.setBounds(623, 26, 170, 14);
		ctrlGroup.add(lblAddress);
		lblAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfBirthday = new DatePicker();
		txfBirthday.setBounds(169, 103, 220, 25);
		ctrlGroup.add(txfBirthday);

		txfHired = new DatePicker();
		txfHired.setBounds(169, 143, 220, 25);
		ctrlGroup.add(txfHired);

		JLabel lblRole = new JLabel("Ruolo:");
		lblRole.setBounds(623, 186, 170, 14);
		ctrlGroup.add(lblRole);
		lblRole.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		roleComboBox = new JComboBox<>();
		roleComboBox.setBounds(805, 180, 220, 25);
		ctrlGroup.add(roleComboBox);
		roleComboBox.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		roleComboBox.setBorder(UIManager.getBorder("ComboBox.border"));
		roleComboBox.setBackground(Color.WHITE);

		treatmentsComboBox = new JComboBox<>();
		ctrlGroup.add(roleComboBox);
		roleComboBox.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		roleComboBox.setBorder(UIManager.getBorder("TextArea.border"));

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(3, 226, 170, 14);
		ctrlGroup.add(lblUsername);
		lblUsername.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfUsername = new JTextField();
		txfUsername.setBounds(169, 223, 220, 20);
		ctrlGroup.add(txfUsername);
		txfUsername.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(3, 266, 170, 14);
		ctrlGroup.add(lblPassword);
		lblPassword.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfPassword = new JPasswordField();
		txfPassword.setBounds(169, 263, 220, 20);
		ctrlGroup.add(txfPassword);
		txfPassword.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfPassword.setActionCommand("676");
		txfPassword.setColumns(10);
		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(0, 281, 121, 14);
		ctrlGroup.add(visibleCheck);
		visibleCheck.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 11));

		JLabel lblBirthPlace = new JLabel("Città di nascita:");
		lblBirthPlace.setBounds(623, 66, 170, 14);
		ctrlGroup.add(lblBirthPlace);
		lblBirthPlace.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfBirthplace = new JTextField();
		txfBirthplace.setBounds(805, 63, 220, 20);
		ctrlGroup.add(txfBirthplace);
		txfBirthplace.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfBirthplace.setColumns(10);

		ButtonGroup genderBtnGroup = new ButtonGroup();
		maleRadioBtn = new JRadioButton("Uomo");
		maleRadioBtn.setBounds(805, 222, 75, 23);
		ctrlGroup.add(maleRadioBtn);
		maleRadioBtn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		genderBtnGroup.add(maleRadioBtn);

		femaleRadioBtn = new JRadioButton("Donna");
		femaleRadioBtn.setBounds(882, 222, 75, 23);
		ctrlGroup.add(femaleRadioBtn);
		femaleRadioBtn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		genderBtnGroup.add(femaleRadioBtn);

		JLabel notesLbl = new JLabel("Note:");
		notesLbl.setBounds(623, 265, 170, 14);
		ctrlGroup.add(notesLbl);
		notesLbl.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfNotes = new JTextArea();
		txfNotes.setBounds(805, 263, 220, 20);
		ctrlGroup.add(txfNotes);
		txfNotes.setBorder(UIManager.getBorder("TextField.border"));

		JLabel lbHired = new JLabel("Assunzione:");
		lbHired.setBounds(3, 148, 170, 14);
		ctrlGroup.add(lbHired);
		lbHired.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfIban = new JTextField();
		txfIban.setBounds(169, 303, 220, 20);
		ctrlGroup.add(txfIban);
		txfIban.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		txfIban.setColumns(10);

		JLabel lbIban = new JLabel("IBAN:");
		lbIban.setBounds(3, 306, 170, 14);
		ctrlGroup.add(lbIban);
		lbIban.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lbTermination = new JLabel("Scadenza:");
		lbTermination.setBounds(3, 188, 170, 14);
		ctrlGroup.add(lbTermination);
		lbTermination.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfTermination = new DatePicker();
		txfTermination.setBounds(169, 183, 220, 25);
		ctrlGroup.add(txfTermination);
		
		treatmentsComboBox = new JComboBox<>();
		treatmentsComboBox.setBorder(UIManager.getBorder("TextField.border"));
		treatmentsComboBox.setBounds(805, 303, 220, 25);
		treatmentsComboBox.setBackground(Color.WHITE);
		ctrlGroup.add(treatmentsComboBox);
		
		JLabel lbTreatment = new JLabel("Trattamento");
		lbTreatment.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		lbTreatment.setBounds(623, 305, 170, 14);
		ctrlGroup.add(lbTreatment);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (visibleCheck.isSelected()) {
					txfPassword.setEchoChar((char) 0);
				} else {
					txfPassword.setEchoChar('•');
				}
			}
		});
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
		treatmentsComboBox.removeAllItems();
		treatments.parallelStream()
				.forEach(t -> treatmentsComboBox.addItem(t));

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

	private static boolean getGender() {
		if (femaleRadioBtn.isSelected()) {
			return true;
		}
		return false;
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	Optional<Employee> insertElement() {
		try { // all fields must be filled
			isDataValid();
		} catch (inputValidatorException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return Optional.empty();
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
		Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();

		UserCredentials cred = new UserCredentials(username, password, address, iban, phone, mail);
		if(!UserCredentialsDAO.isUsernameUnique(username)) return Optional.empty();

		UserDetails det = new UserDetails(name, surname, getGender(), BoD, birthplace, notes);
		cred = UserCredentialsDAO.insertUserCredentials(cred).get();
		Employee employee = new Employee(det, cred, employeeSerial, role, Collections.emptyList(), hired, termination,
				treatment);
				
			
		lbOutput.setText("Nuovo utente creato");
		return EmployeeDAO.insertEmployee(employee);
	}

	@Override
	int updateElement() {
		try {
			isDataValid();
		} catch (inputValidatorException e) {
			if (!(e instanceof emptyInputException)) { // fields can be empty
				JOptionPane.showMessageDialog(null, e.getMessage());
				return -1;
			}
			e.errorComponent.setBorder(UIManager.getBorder("TextField.border")); //reset field's border in update mode
		}
		int id = selectedId;
		if (id <= 0) {
			JOptionPane.showMessageDialog(null, "Nessun operatore selezionato");
			return -1; // do not allow invalid ids to be passed to update
		}

		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthplace = txfBirthplace.getText();
		// isFemale
		LocalDate BoD = txfBirthday.getDate();
		LocalDate hired = txfHired.getDate();
		LocalDate termination = txfTermination.getDate();
		String notes = txfNotes.getText();
		// isEnabled
		long employeeSerial = selectedSerial;
		// shifts
		// hiredDate
		Roles role = (Roles) roleComboBox.getSelectedItem();
		Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();

		// terminationDate
		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();

		Employee employee = EmployeeDAO.getEmployee(id).get();
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
		employee.setTreatment(treatment);

		lbOutput.setText(name + " " + surname + " modificato");
		return EmployeeDAO.updateEmployee(id, employee);
	}

	@Override
	int deleteElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("Nessuna riga scelta");
			}
			final int id = (int) employeeModel.getValueAt(row, 0);
			lbOutput.setText("Operatore cancellato");
			populateTable();
			return EmployeeDAO.deleteEmployee(id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Impossibile cancellare: " + e.getMessage(), "Errore di database",
					JOptionPane.ERROR_MESSAGE);
		}
		return -1;
	}

	@Override
	int disableElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("Nessuna riga scelta");
			}
			final int id = (int) employeeModel.getValueAt(row, 0);
			boolean currentFlag = (boolean) employeeModel.getValueAt(row, _ISENABLEDCOL);
			employeeModel.setValueAt(!currentFlag, row, _ISENABLEDCOL);
			table.repaint();
			
			lbOutput.setText(currentFlag ? "Operatore abilitato" : "Operatore disabilitato");
			return EmployeeDAO.toggleEnabledEmployee(id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errori dati da disabilitare: " + e.getMessage(), "Errore di disabilitazione",
					JOptionPane.ERROR_MESSAGE);
		}
		return -1;
	}

	@Override
	void populateTable() {
		employeeModel.setRowCount(0);
		clearTxfFields();

		treatmentsComboBox.removeAllItems();
		treatments.parallelStream()
				.filter(t -> t.isEnabled())
				.forEach(t -> treatmentsComboBox.addItem(t));


		if (!employees.isEmpty()) {
			employees.parallelStream()
					.forEach(e -> employeeModel.addRow(e.toTableRow()));
		} else {
			lbOutput.setText("Lista Transazioni vuota");
		}
	}

	@Override
	void clearTxfFields() {
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
	ListSelectionListener getListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow <= -1) return;

					selectedId = (int) employeeModel.getValueAt(selectedRow, 0);
					selectedSerial = (long) employeeModel.getValueAt(selectedRow, 1);
					String name = (String) employeeModel.getValueAt(selectedRow, 2);
					String surname = (String) employeeModel.getValueAt(selectedRow, 3);
					LocalDate BoD = (LocalDate) employeeModel.getValueAt(selectedRow, 4);
					LocalDate hiredDate = (LocalDate) employeeModel.getValueAt(selectedRow, 5);
					LocalDate terminationDate = (LocalDate) employeeModel.getValueAt(selectedRow, 6);
					Roles role = (Roles) employeeModel.getValueAt(selectedRow, 7);
					String username = (String) employeeModel.getValueAt(selectedRow, 8);
					String address = (String) employeeModel.getValueAt(selectedRow, 9);
					String birthplace = (String) employeeModel.getValueAt(selectedRow, 10);
					String mail = (String) employeeModel.getValueAt(selectedRow, 11);
					String phone = (String) employeeModel.getValueAt(selectedRow, 12);
					String iban = (String) employeeModel.getValueAt(selectedRow, 13);
					Treatment treatment = (Treatment) employeeModel.getValueAt(selectedRow, 14);

					txfName.setText(name);
					txfSurname.setText(surname);

					txfBirthday.setDate(BoD);
					txfHired.setDate(hiredDate);
					txfTermination.setDate(terminationDate);

					roleComboBox.setSelectedItem(role);
					treatmentsComboBox.setSelectedItem(treatment);

					txfUsername.setText(username);
					txfAddress.setText(address);
					txfBirthplace.setText(birthplace);
					txfMail.setText(mail);
					txfPhone.setText(phone);
					txfIban.setText(iban);

					Employee thisEmployee = EmployeeDAO.getEmployee(selectedId).get();
					if (thisEmployee.isFemale()) {
						maleRadioBtn.setSelected(false);
						femaleRadioBtn.setSelected(true);
					} else {
						maleRadioBtn.setSelected(true);
						femaleRadioBtn.setSelected(false);
					}
				}
			}
		};
	}

	@Override
	boolean isDataValid() {
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
