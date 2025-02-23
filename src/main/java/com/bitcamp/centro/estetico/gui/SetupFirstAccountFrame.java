package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.PlaceholderHelper;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

public class SetupFirstAccountFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTextField txfSurname;
	private static JTextField txfName;
	private static JTextField txfIban;

	private static JTextField txfMail;
	private static JTextField txfPhone;
	private static JTextField txfAddress;
	private static JTextField txfUsername;
	private static JPasswordField txfPassword;
	private static JTextField txfBirthplace;
	private static JRadioButton femaleRadioBtn;
	private static JRadioButton maleRadioBtn;
	private static JTextArea txfNotes;
	private static DatePicker txfBirthday;
	private static JLabel lbOutput;
	private static JButton btnAbort;
	private static JButton btnNext;
	private static JButton btnInsert;

	/**
	 * Create the panel.
	 */
	public SetupFirstAccountFrame() {
		setTitle("Benvenuto nel Gestionale Centro Estetico");
		setName("Benvenuto nel Gestionale Centro Estetico");
		setLayout(null);
		setSize(1024, 500);
		setName("Primo Account");
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(
		MainFrame.class.getResource("/com/bitcamp/centro/estetico/resources/bc_icon.png")).getImage());


		JLabel titleTab = new JLabel("CREAZIONE ACCOUNT AMMINISTRATORE");
		titleTab.setHorizontalAlignment(SwingConstants.CENTER);
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(0, 11, 1014, 32);
		add(titleTab);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(34, 92, 170, 14);
		add(lblName);

		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(200, 89, 220, 25);
		add(txfName);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSurname.setBounds(34, 131, 170, 17);
		add(lblSurname);

		txfSurname = new JTextField();
		txfSurname.setColumns(10);
		txfSurname.setBounds(200, 129, 220, 25);
		add(txfSurname);

		JLabel lblBirthday = new JLabel("Data di nascita:");
		lblBirthday.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblBirthday.setBounds(34, 172, 170, 14);
		add(lblBirthday);

		JLabel lblIVA = new JLabel("IBAN:");
		lblIVA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblIVA.setBounds(34, 212, 170, 14);
		add(lblIVA);

		txfIban = new JTextField();
		txfIban.setColumns(10);
		txfIban.setBounds(200, 209, 220, 25);
		add(txfIban);

		txfMail = new JTextField();
		txfMail.setColumns(10);
		txfMail.setBounds(740, 169, 220, 25);
		add(txfMail);

		txfPhone = new JTextField();
		txfPhone.setColumns(10);
		txfPhone.setBounds(740, 209, 220, 25);
		add(txfPhone);

		txfAddress = new JTextField();
		txfAddress.setColumns(10);
		txfAddress.setBounds(740, 89, 220, 25);
		add(txfAddress);

		JLabel lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblMail.setBounds(522, 168, 170, 14);
		add(lblMail);

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPhone.setBounds(522, 212, 170, 14);
		add(lblPhone);

		JLabel lblAddress = new JLabel("Indirizzo:");
		lblAddress.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblAddress.setBounds(522, 92, 170, 14);
		add(lblAddress);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblUsername.setBounds(34, 252, 170, 14);
		add(lblUsername);

		txfUsername = new JTextField();
		txfUsername.setColumns(10);
		txfUsername.setBounds(200, 249, 220, 25);
		add(txfUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPassword.setBounds(34, 292, 170, 14);
		add(lblPassword);

		txfPassword = new JPasswordField();
		txfPassword.setColumns(10);
		txfPassword.setBounds(200, 289, 220, 25);
		add(txfPassword);

		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 11));
		visibleCheck.setBounds(30, 320, 180, 20);
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
		add(visibleCheck);

		JLabel lblBirthPlace = new JLabel("Città di nascita:");
		lblBirthPlace.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblBirthPlace.setBounds(522, 130, 170, 14);
		add(lblBirthPlace);

		txfBirthplace = new JTextField();
		txfBirthplace.setColumns(10);
		txfBirthplace.setBounds(740, 129, 220, 25);
		add(txfBirthplace);

		maleRadioBtn = new JRadioButton("Uomo");
		maleRadioBtn.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		maleRadioBtn.setSelected(true);
		maleRadioBtn.setBounds(516, 288, 71, 23);

		add(maleRadioBtn);

		femaleRadioBtn = new JRadioButton("Donna");
		femaleRadioBtn.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		femaleRadioBtn.setBounds(587, 288, 71, 23);

		add(femaleRadioBtn);

		JLabel notesLbl = new JLabel("Note:");
		notesLbl.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		notesLbl.setBounds(522, 251, 61, 16);
		add(notesLbl);

		txfNotes = new JTextArea();
		txfNotes.setBorder(UIManager.getBorder("FormattedTextField.border"));
		txfNotes.setBounds(740, 252, 220, 54);
		add(txfNotes);

		lbOutput = new JLabel("");
		lbOutput.setForeground(new Color(0, 102, 51));
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lbOutput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbOutput.setBounds(10, 48, 1004, 20);
		add(lbOutput);

		txfBirthday = new DatePicker();
		txfBirthday.setBounds(200, 169, 220, 25);
		add(txfBirthday);
        DatePickerSettings settings = new DatePickerSettings();
        settings.setColor(DateArea.CalendarBackgroundNormalDates, UIManager.getColor("Panel.background"));
        settings.setColor(DateArea.BackgroundOverallCalendarPanel, UIManager.getColor("Panel.background"));
        settings.setColor(DateArea.TextFieldBackgroundValidDate, UIManager.getColor("TextField.background"));
        settings.setColor(DateArea.CalendarBackgroundSelectedDate, UIManager.getColor("Button.background"));
        settings.setColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers, UIManager.getColor("Label.background"));
        settings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, UIManager.getColor("Label.background"));
        settings.setColor(DateArea.BackgroundTodayLabel, UIManager.getColor("Label.background"));
        settings.setColor(DateArea.DatePickerTextValidDate, UIManager.getColor("TextField.foreground"));
        settings.setColor(DateArea.BackgroundClearLabel, UIManager.getColor("Button.background"));
        settings.setColor(DateArea.CalendarTextNormalDates, UIManager.getColor("Label.foreground"));
        settings.setColor(DateArea.CalendarBorderSelectedDate, UIManager.getColor("Table.selectionBackground").darker());
        settings.setColor(DateArea.BackgroundCalendarPanelLabelsOnHover, UIManager.getColor("ComboBox.buttonHighlight"));
        settings.setColor(DateArea.CalendarTextWeekdays, UIManager.getColor("Button.foreground"));
        settings.setColorBackgroundWeekdayLabels(UIManager.getColor("Button.background"), true);
		txfBirthday.setSettings(settings);

		btnAbort = new JButton("Annulla");
		btnAbort.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		btnAbort.setBounds(508, 380, 100, 30);
		add(btnAbort);

		// Listener per il pulsante "Annulla"
		btnAbort.addActionListener(e -> clearFields());

		btnNext = new JButton("AVANTI");
		btnNext.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		btnNext.setBackground(new Color(64, 64, 64)); // green new Color(0, 204, 102)
		btnNext.addActionListener(e -> {
			new LoginFrame();
			dispose();
		});
		btnNext.setBounds(388, 427, 220, 23);
		btnNext.setEnabled(false);
		add(btnNext);

		btnInsert = new JButton("Inserisci");
		btnInsert.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		btnInsert.setBounds(388, 380, 100, 30);
		add(btnInsert);
		if (!DAO.isEmpty(Employee.class)) {
			btnInsert.setEnabled(false);
			btnNext.setEnabled(true);
		}

		btnInsert.addActionListener(e -> createEmployee());

		PlaceholderHelper.addPlaceholder(txfName, "*");
		PlaceholderHelper.addPlaceholder(txfSurname, "*");
		PlaceholderHelper.addPlaceholder(txfIban, "*");
		PlaceholderHelper.addPlaceholder(txfMail, "*");
		PlaceholderHelper.addPlaceholder(txfPhone, "*");
		PlaceholderHelper.addPlaceholder(txfAddress, "*");
		PlaceholderHelper.addPlaceholder(txfUsername, "*");
		PlaceholderHelper.addPlaceholder(txfBirthplace, "*");
	}

	private static void createEmployee() {
		if (!validateInputs())
			return;

		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthplace = txfBirthplace.getText();
		LocalDate BoD = txfBirthday.getDate();
		LocalDate hired = LocalDate.now();
		String notes = txfNotes.getText();
		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();

		UserDetails det = new UserDetails(name, surname, getGender(), BoD, birthplace, notes);
		UserCredentials cred = new UserCredentials(username, null, address, iban, phone, mail, null);
		cred.setPassword(password);

		Employee employee = new Employee(det, cred, Roles.ADMIN, null, null, hired, null);
		cred.setUser(employee);	

		DAO.insert(employee);

		lbOutput.setText("Nuovo utente creato correttamente");

		btnInsert.setEnabled(false);
		btnNext.setEnabled(true);
		btnNext.setBackground(new Color(0, 204, 102));
	}

	private static Gender getGender() {
		if (femaleRadioBtn.isSelected()) {
			return Gender.FEMALE;
		}
		return Gender.MALE;
	}

	private static void clearFields() {
		txfName.setText("");
		txfSurname.setText("");
		txfIban.setText("");
		txfMail.setText("");
		txfPhone.setText("");
		txfAddress.setText("");
		txfBirthday.clear();
		txfUsername.setText("");
		txfPassword.setText("");
		txfBirthplace.setText("");
		txfNotes.setText("");
	}

	private static boolean validateInputs() {
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
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}

		return txfBirthday.isTextFieldValid();
	}
}
// Prova
