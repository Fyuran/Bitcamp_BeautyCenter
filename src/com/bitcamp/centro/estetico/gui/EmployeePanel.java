package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.emptyInputException;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;
import com.github.lgooddatepicker.components.DatePicker;

public class EmployeePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static int selectedId;
	private static JTextField txtSurname;
	private static JTextField txtSearchBar;
	private static JTextField txtName;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	private static DefaultTableModel tableModel;
	private static JTextField txtMail;
	private static JTextField txtPhone;
	private static JTextField txtAddress;
	private static DatePicker txtBirthday;
	private static DatePicker txtHired;
	private static DatePicker txtTermination;
	private static JTextField txtUsername;
	private static JPasswordField txtPassword;
	private static JLabel msgLbl;
	private static JTextField txtBirthplace;
	private static JRadioButton femaleRadioBtn;
	private static JRadioButton maleRadioBtn;
	private static JTextArea txtNotes;
	private static JComboBox<String> roleComboBox;
	private static long selectedSerial;
	private static JTextField txtIban;
	private static JPanel ctrlGroup;

	/**
	 * Create the panel.
	 */
	public EmployeePanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("Operatori");
		JLabel titleTab = new JLabel("GESTIONE OPERATORI");
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 206, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "ID", "Numero seriale", "Nome", "Cognome", "Data di nascita", "Assunzione", "Scadenza",
				"Ruolo", "Username", "Indirizzo", "Città di provenienza", "Mail", "Telefono", "IBAN" };
		tableModel = new DefaultTableModel(columnNames, 0);
		// Creazione della tabella
		JTable table = new JTable(tableModel);
		// Listener della tabella per pescare i nomi che servono
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						selectedId = (int) tableModel.getValueAt(selectedRow, 0);
						selectedSerial = (long) tableModel.getValueAt(selectedRow, 1);
						String name = String.valueOf(tableModel.getValueAt(selectedRow, 2));
						String surname = String.valueOf(tableModel.getValueAt(selectedRow, 3));
						LocalDate BoD = (LocalDate) tableModel.getValueAt(selectedRow, 4);
						LocalDate hiredDate = (LocalDate) tableModel.getValueAt(selectedRow, 5);
						LocalDate terminationDate = (LocalDate) tableModel.getValueAt(selectedRow, 6);
						String roleString = String.valueOf(tableModel.getValueAt(selectedRow, 7));
						String username = String.valueOf(tableModel.getValueAt(selectedRow, 8));
						String address = String.valueOf(tableModel.getValueAt(selectedRow, 9));
						String birthplace = String.valueOf(tableModel.getValueAt(selectedRow, 10));
						String mail = String.valueOf(tableModel.getValueAt(selectedRow, 11));
						String phone = String.valueOf(tableModel.getValueAt(selectedRow, 12));
						String iban = String.valueOf(tableModel.getValueAt(selectedRow, 13));

						txtName.setText(name);
						txtSurname.setText(surname);

						txtBirthday.setDate(BoD);
						txtHired.setDate(hiredDate);
						txtTermination.setDate(terminationDate);

						roleComboBox.setSelectedItem(roleString);
						txtUsername.setText(username);
						txtAddress.setText(address);
						txtBirthplace.setText(birthplace);
						txtMail.setText(mail);
						txtPhone.setText(phone);
						txtIban.setText(iban);

						Employee thisEmployee = EmployeeDAO.getEmployee(selectedId).get();
						if (thisEmployee.isFemale()) {
							maleRadioBtn.setSelected(false);
							femaleRadioBtn.setSelected(true);
						} else {
							maleRadioBtn.setSelected(true);
							femaleRadioBtn.setSelected(false);
						}

						// { "ID", "Nome", "Cognome", "Data di nascita", "Data assunzione", "Ruolo",
						// "Username" };

						// Il listener ascolta la riga selezionata e la usa per popolare i campi

					}
				}
			}
		});

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/searchIcon.png")));
		btnSearch.setBounds(206, 8, 40, 30);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(txtName.getText());
			}
		});
		containerPanel.add(btnSearch);

		txtSearchBar = new JTextField();
		txtSearchBar.setColumns(10);
		txtSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txtSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txtSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		btnFilter.addActionListener(e -> populateTableByFilter());
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setBounds(720, 8, 40, 30);
		btnInsert.addActionListener(e -> createEmployee());
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setBounds(770, 8, 40, 30);
		btnUpdate.addActionListener(e -> updateEmployee());
		containerPanel.add(btnUpdate);

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/delete.png")));
		btnDelete.setBounds(820, 8, 40, 30);
		btnDelete.addActionListener(e -> deleteEmployee());
		containerPanel.add(btnDelete);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/disable.png")));
		btnDisable.setBounds(920, 8, 40, 30);
		containerPanel.add(btnDisable);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 276);
		containerPanel.add(outputPanel);

		JButton btnHystorical = new JButton("");
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/cartellina.png")));
		btnHystorical.setBounds(870, 8, 40, 30);
		btnHystorical.addActionListener(e -> populateTable());
		containerPanel.add(btnHystorical);

		ctrlGroup = new JPanel();
		ctrlGroup.setBounds(40, 413, 929, 323);
		add(ctrlGroup);
		ctrlGroup.setLayout(null);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(3, 26, 170, 14);
		ctrlGroup.add(lblName);
		lblName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtName = new JTextField();
		txtName.setBounds(169, 23, 220, 20);
		ctrlGroup.add(txtName);
		txtName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtName.setColumns(10);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setBounds(3, 65, 170, 17);
		ctrlGroup.add(lblSurname);
		lblSurname.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtSurname = new JTextField();
		txtSurname.setBounds(169, 63, 220, 20);
		ctrlGroup.add(txtSurname);
		txtSurname.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtSurname.setColumns(10);

		JLabel lblBirthday = new JLabel("Data di nascita:");
		lblBirthday.setBounds(3, 108, 170, 14);
		ctrlGroup.add(lblBirthday);
		lblBirthday.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtMail = new JTextField();
		txtMail.setBounds(709, 105, 220, 20);
		ctrlGroup.add(txtMail);
		txtMail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtMail.setColumns(10);

		txtPhone = new JTextField();
		txtPhone.setBounds(709, 145, 220, 20);
		ctrlGroup.add(txtPhone);
		txtPhone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtPhone.setColumns(10);

		txtAddress = new JTextField();
		txtAddress.setBounds(709, 23, 220, 20);
		ctrlGroup.add(txtAddress);
		txtAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtAddress.setColumns(10);

		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(491, 108, 170, 14);
		ctrlGroup.add(lblMail);
		lblMail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setBounds(491, 148, 170, 14);
		ctrlGroup.add(lblPhone);
		lblPhone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lblAddress = new JLabel("Indirizzo:");
		lblAddress.setBounds(491, 26, 170, 14);
		ctrlGroup.add(lblAddress);
		lblAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtBirthday = new DatePicker();
		txtBirthday.setBounds(169, 103, 220, 25);
		ctrlGroup.add(txtBirthday);

		txtHired = new DatePicker();
		txtHired.setBounds(169, 143, 220, 25);
		ctrlGroup.add(txtHired);

		JLabel lblRole = new JLabel("Ruolo:");
		lblRole.setBounds(491, 186, 170, 14);
		ctrlGroup.add(lblRole);
		lblRole.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		roleComboBox = new JComboBox<>();
		roleComboBox.setBounds(709, 180, 220, 27);
		ctrlGroup.add(roleComboBox);
		roleComboBox.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		roleComboBox.setBorder(UIManager.getBorder("TextArea.border"));

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(3, 226, 170, 14);
		ctrlGroup.add(lblUsername);
		lblUsername.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtUsername = new JTextField();
		txtUsername.setBounds(169, 223, 220, 20);
		ctrlGroup.add(txtUsername);
		txtUsername.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(3, 266, 170, 14);
		ctrlGroup.add(lblPassword);
		lblPassword.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtPassword = new JPasswordField();
		txtPassword.setBounds(169, 263, 220, 20);
		ctrlGroup.add(txtPassword);
		txtPassword.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtPassword.setActionCommand("676");
		txtPassword.setColumns(10);
		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(0, 281, 121, 14);
		ctrlGroup.add(visibleCheck);
		visibleCheck.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 11));

		msgLbl = new JLabel("");
		msgLbl.setBounds(196, 0, 625, 16);
		ctrlGroup.add(msgLbl);

		JLabel lblBirthPlace = new JLabel("Città di nascita:");
		lblBirthPlace.setBounds(491, 66, 170, 14);
		ctrlGroup.add(lblBirthPlace);
		lblBirthPlace.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtBirthplace = new JTextField();
		txtBirthplace.setBounds(709, 63, 220, 20);
		ctrlGroup.add(txtBirthplace);
		txtBirthplace.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txtBirthplace.setColumns(10);

		ButtonGroup genderBtnGroup = new ButtonGroup();
		maleRadioBtn = new JRadioButton("Uomo");
		maleRadioBtn.setBounds(491, 222, 75, 23);
		ctrlGroup.add(maleRadioBtn);
		maleRadioBtn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		genderBtnGroup.add(maleRadioBtn);

		femaleRadioBtn = new JRadioButton("Donna");
		femaleRadioBtn.setBounds(568, 222, 75, 23);
		ctrlGroup.add(femaleRadioBtn);
		femaleRadioBtn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		genderBtnGroup.add(femaleRadioBtn);

		JLabel notesLbl = new JLabel("Note:");
		notesLbl.setBounds(491, 279, 61, 16);
		ctrlGroup.add(notesLbl);
		notesLbl.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtNotes = new JTextArea();
		txtNotes.setBounds(709, 251, 220, 72);
		ctrlGroup.add(txtNotes);
		txtNotes.setBorder(UIManager.getBorder("TextField.border"));

		JLabel lbHired = new JLabel("Assunzione:");
		lbHired.setBounds(3, 148, 170, 14);
		ctrlGroup.add(lbHired);
		lbHired.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtIban = new JTextField();
		txtIban.setBounds(169, 303, 220, 20);
		ctrlGroup.add(txtIban);
		txtIban.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		txtIban.setColumns(10);

		JLabel lbIban = new JLabel("IBAN:");
		lbIban.setBounds(3, 306, 170, 14);
		ctrlGroup.add(lbIban);
		lbIban.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lbTermination = new JLabel("Scadenza:");
		lbTermination.setBounds(3, 188, 170, 14);
		ctrlGroup.add(lbTermination);
		lbTermination.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txtTermination = new DatePicker();
		txtTermination.setBounds(169, 183, 220, 25);
		ctrlGroup.add(txtTermination);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (visibleCheck.isSelected()) {
					txtPassword.setEchoChar((char) 0);
				} else {
					txtPassword.setEchoChar('•');
				}
			}
		});
		for (Roles r : Roles.values()) {
			roleComboBox.addItem(r.toString());
		}

		populateTable();
	}

	private static void clearTable() {
		tableModel.setRowCount(0);
	}

	private static void clearFields() {
		txtName.setText("");
		txtSurname.setText("");
		txtBirthday.clear();
		txtHired.clear();
		txtPhone.setText("");
		txtMail.setText("");
		txtAddress.setText("");
		txtIban.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
		txtBirthplace.setText("");
		txtNotes.setText("");
	}

	static void populateTable() {
		clearTable();
		clearFields();
		List<Object[]> data = EmployeeDAO.toTableRowAll(c -> c.isEnabled());
		if (data.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano impiegati presenti", "" });
			return;
		}
		for (Object[] row : data) {
			tableModel.addRow(row);
		}

	}

	private static void populateTableByFilter() {
		msgLbl.setText("");
		if (txtSearchBar.getText().isBlank() || txtSearchBar.getText().isEmpty()) {
			msgLbl.setText("Inserire un filtro!");
			return;
		}
		clearTable();
		List<Employee> employees = EmployeeDAO.getAllEmployees();
		if (employees.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano operatori presenti", "" });
			return;
		}
		for (Employee employee : employees) {
			if (employee.isEnabled() && employee.getTerminationDate() == null
					&& employee.getSurname().equalsIgnoreCase(txtSearchBar.getText())) {
						tableModel.addRow(new String[] { String.valueOf(employee.getId()),
						String.valueOf(employee.getEmployeeSerial()), employee.getName(), employee.getSurname(),
						String.valueOf(employee.getBoD()), String.valueOf(employee.getHiredDate()),
						employee.getRole().toString(), employee.getUserCredentials().getUsername(),
						employee.getAddress(), employee.getBirthplace(), employee.getMail(), employee.getPhone(),
						employee.getIban() });
				// {"ID","Nome","Cognome","Data di nascita","Data
				// assunzione","Ruolo","Username"};
			}
		}

		txtSearchBar.setText("");
	}

	private static void createEmployee() {
		try { // all fields must be filled
			isDataValid();
		} catch (inputValidatorException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}

		String name = txtName.getText();
		String surname = txtSurname.getText();
		String birthplace = txtBirthplace.getText();
		LocalDate BoD = txtBirthday.getDate();
		LocalDate hired = txtHired.getDate();
		String notes = txtNotes.getText();
		long employeeSerial = Employee.generateSerial();
		Roles role = Roles.toEnum(roleComboBox.getSelectedItem().toString());
		String username = txtUsername.getText();
		char[] password = txtPassword.getPassword();
		String address = txtAddress.getText();
		String iban = txtIban.getText();
		String phone = txtPhone.getText();
		String mail = txtMail.getText();

		UserDetails det = new UserDetails(name, surname, getGender(), BoD, birthplace, notes);
		UserCredentials cred = new UserCredentials(username, password, address, iban, phone, mail);
		cred = UserCredentialsDAO.insertUserCredentials(cred).get();
		Employee employee = new Employee(det, cred, employeeSerial, role, Collections.emptyList(), hired, null);
		employee = EmployeeDAO.insertEmployee(employee).get();

		System.out.println(employee);

		msgLbl.setText("Nuovo utente creato correttamente");
		populateTable();
	}

	private static void updateEmployee() {
		try {
			isDataValid();
		} catch (inputValidatorException e) {
			if (!(e instanceof emptyInputException)) { // fields can be empty
				JOptionPane.showMessageDialog(null, e.getMessage());
				return;
			}
		}
		int id = selectedId;
		String name = txtName.getText();
		String surname = txtSurname.getText();
		String birthplace = txtBirthplace.getText();
		// isFemale
		LocalDate BoD = txtBirthday.getDate();
		LocalDate hired = txtHired.getDate();
		String notes = txtNotes.getText();
		// isEnabled
		long employeeSerial = selectedSerial;
		// shifts
		// hiredDate
		Roles role = Roles.toEnum(roleComboBox.getSelectedItem().toString());
		// terminationDate
		String username = txtUsername.getText();
		char[] password = txtPassword.getPassword();
		String address = txtAddress.getText();
		String iban = txtIban.getText();
		String phone = txtPhone.getText();
		String mail = txtMail.getText();

		Employee employee = EmployeeDAO.getEmployee(id).get();
		employee.setName(name);
		employee.setSurname(surname);
		employee.setBirthplace(birthplace);
		employee.setBoD(BoD);
		employee.setHiredDate(hired);
		employee.setNotes(notes);
		employee.setEmployeeSerial(employeeSerial);
		employee.setRole(role);
		employee.setUsername(username);
		employee.setPassword(password);
		employee.setAddress(address);
		employee.setIban(iban);
		employee.setPhone(phone);
		employee.setMail(mail);

		EmployeeDAO.updateEmployee(id, employee);

		System.out.println(employee);

		msgLbl.setText(name + " " + surname + " modificato correttamente");
		populateTable();
	}

	public static void deleteEmployee() {

		msgLbl.setText("");
		EmployeeDAO.toggleEnabledEmployee(selectedId);
		msgLbl.setText("Utente rimosso correttamente");
		populateTable();
	}

	private static boolean getGender() {
		if (femaleRadioBtn.isSelected()) {
			return true;
		}
		return false;
	}

	private static boolean isDataValid() throws inputValidatorException {
		try {
			inputValidator.validateName(txtName.getText());
			inputValidator.validateSurname(txtSurname.getText());
			inputValidator.validateIban(txtIban.getText());
			inputValidator.validateEmail(txtMail.getText());
			inputValidator.validatePhoneNumber(txtPhone.getText());
			inputValidator.validateAlphanumeric(txtAddress.getText(), "Indirizzo");
			inputValidator.validateAlphanumeric(txtUsername.getText(), "Username");
			inputValidator.validatePassword(txtPassword.getPassword());
			inputValidator.validateAlphanumeric(txtBirthplace.getText(), "Luogo di nascita");
			inputValidator.isValidCity(txtBirthplace.getText());
		} catch (inputValidatorException e) {
			throw e;
		}

		return txtBirthday.isTextFieldValid();
	}
}
// Prova
