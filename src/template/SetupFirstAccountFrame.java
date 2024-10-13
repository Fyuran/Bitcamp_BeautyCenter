package template;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;

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

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Roles;
import com.centro.estetico.bitcamp.UserCredentials;
import com.centro.estetico.bitcamp.UserDetails;
import com.github.lgooddatepicker.components.DatePicker;

import DAO.EmployeeDAO;
import DAO.UserCredentialsDAO;
import utils.inputValidator;
import utils.placeholderHelper;

public class SetupFirstAccountFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTextField txtSurname;
	private static JTextField txtName;
	private static JTextField txtIban;

	private static JTextField txtMail;
	private static JTextField txtPhone;
	private static JTextField txtAddress;
	private static JTextField txtUsername;
	private static JPasswordField txtPassword;
	private static JTextField txtBirthplace;
	private static JRadioButton femaleRadioBtn;
	private static JRadioButton maleRadioBtn;
	private static JTextArea txtNotes;
	private static DatePicker txtBirthday;
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(200, 89, 220, 20);
		add(txtName);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSurname.setBounds(34, 131, 170, 17);
		add(lblSurname);

		txtSurname = new JTextField();
		txtSurname.setColumns(10);
		txtSurname.setBounds(200, 129, 220, 20);
		add(txtSurname);

		JLabel lblBirthday = new JLabel("Data di nascita:");
		lblBirthday.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblBirthday.setBounds(34, 172, 170, 14);
		add(lblBirthday);

		JLabel lblIVA = new JLabel("IBAN:");
		lblIVA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblIVA.setBounds(34, 212, 170, 14);
		add(lblIVA);

		txtIban = new JTextField();
		txtIban.setColumns(10);
		txtIban.setBounds(200, 209, 220, 20);
		add(txtIban);

		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(740, 169, 220, 20);
		add(txtMail);

		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(740, 209, 220, 20);
		add(txtPhone);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(740, 89, 220, 20);
		add(txtAddress);

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

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(200, 249, 220, 20);
		add(txtUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPassword.setBounds(34, 292, 170, 14);
		add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(200, 289, 220, 20);
		add(txtPassword);

		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 11));
		visibleCheck.setBounds(30, 313, 121, 14);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(visibleCheck.isSelected()) {
					txtPassword.setEchoChar((char) 0);
				} else {
					txtPassword.setEchoChar('•');
				}
			}
		});
		add(visibleCheck);
		
		JLabel lblBirthPlace = new JLabel("Città di nascita:");
		lblBirthPlace.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblBirthPlace.setBounds(522, 130, 170, 14);
		add(lblBirthPlace);

		txtBirthplace = new JTextField();
		txtBirthplace.setColumns(10);
		txtBirthplace.setBounds(740, 129, 220, 20);
		add(txtBirthplace);

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

		txtNotes = new JTextArea();
		txtNotes.setBorder(UIManager.getBorder("FormattedTextField.border"));
		txtNotes.setBounds(740, 252, 220, 54);
		add(txtNotes);

		lbOutput = new JLabel("");
		lbOutput.setForeground(new Color(0, 102, 51));
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lbOutput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbOutput.setBounds(10, 48, 1004, 20);
		add(lbOutput);

		txtBirthday = new DatePicker();
		txtBirthday.setBounds(200, 169, 220, 25);
		add(txtBirthday);

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
		if (!EmployeeDAO.isEmpty()) {
			btnInsert.setEnabled(false);
			btnNext.setEnabled(true);
		}

		btnInsert.addActionListener(e -> createEmployee());
		
		placeholderHelper.addPlaceholder(txtName, "*");
		placeholderHelper.addPlaceholder(txtSurname, "*");
		placeholderHelper.addPlaceholder(txtIban, "*");
		placeholderHelper.addPlaceholder(txtMail, "*");
		placeholderHelper.addPlaceholder(txtPhone, "*");
		placeholderHelper.addPlaceholder(txtAddress, "*");
		placeholderHelper.addPlaceholder(txtUsername, "*");
		placeholderHelper.addPlaceholder(txtBirthplace, "*");
	}

	private static void createEmployee() {
		if (!validateInputs()) {
			JOptionPane.showMessageDialog(null, inputValidator.getErrorMessage());
			return;
		}

		String name = txtName.getText();
		String surname = txtSurname.getText();
		String birthplace = txtBirthplace.getText();
		LocalDate BoD = txtBirthday.getDate();
		LocalDate hired = LocalDate.now();
		String notes = txtNotes.getText();
		long employeeSerial = Employee.generateSerial();
		String username = txtUsername.getText();
		String password = String.valueOf(txtPassword.getPassword());
		String address = txtAddress.getText();
		String iban = txtIban.getText();
		String phone = txtPhone.getText();
		String mail = txtMail.getText();

		UserDetails det = new UserDetails(name, surname, getGender(), BoD, birthplace, notes);
		UserCredentials cred = new UserCredentials(username, password, address, iban, phone, mail);
		cred = UserCredentialsDAO.insertUserCredentials(cred).get();
		Employee employee = new Employee(det, cred, employeeSerial, Roles.ADMIN, Collections.emptyList(),
				hired, null);
		EmployeeDAO.insertEmployee(employee);

		lbOutput.setText("Nuovo utente creato correttamente");

		btnInsert.setEnabled(false);
		btnNext.setEnabled(true);
		btnNext.setBackground(new Color(0, 204, 102));
	}

	private static boolean getGender() {
		if (femaleRadioBtn.isSelected()) {
			return true;
		}
		return false;
	}

	private static void clearFields() {
		txtName.setText("");
		txtSurname.setText("");
		txtIban.setText("");
		txtMail.setText("");
		txtPhone.setText("");
		txtAddress.setText("");
		txtBirthday.clear();
		txtUsername.setText("");
		txtPassword.setText("");
		txtBirthplace.setText("");
		txtNotes.setText("");
	}

	private static boolean validateInputs() {
		return inputValidator.validateName(txtName.getText()) && inputValidator.validateSurname(txtSurname.getText())
				&& inputValidator.validateIban(txtIban.getText(), false)
				&& inputValidator.validateEmail(txtMail.getText())
				&& inputValidator.validatePhoneNumber(txtPhone.getText())
				&& inputValidator.validateAlphanumeric(txtAddress.getText(), "Indirizzo")
				&& txtBirthday.isTextFieldValid()
				&& inputValidator.validateAlphanumeric(txtUsername.getText(), "Username")
				&& inputValidator.validatePassword(txtPassword.getPassword())
				&& inputValidator.validateAlphanumeric(txtBirthplace.getText(), "Luogo di nascita")
				&& inputValidator.isValidCity(txtBirthplace.getText());
	}
}
// Prova
