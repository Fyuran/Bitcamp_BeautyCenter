package template;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Product;
import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.Employee.Roles;

import utils.inputValidator;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class EmployeePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtSurname;
	private JTextField txfSearchBar;
	private JTextField txtName;
	private JTextField txtIban;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;
	private JTextField txtMail;
	private JTextField txtPhone;
	private JTextField txtAddress;
	private JTextField txtBirthday;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel msgLbl;
	private JTextField txtBirthplace;
	private JRadioButton femaleRadioBtn;
	private JRadioButton maleRadioBtn;
	private boolean isFemale;//boolean che cambia a seconda di ciò che viene selezionato nel radiobutton. è una porcata metterlo qui? Sì. Però al momento è la soluzione più veloce. -Daniele
	private JTextArea txtNotes;
	private JComboBox<String> roleComboBox;
	private int selectedId;

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
		String[] columnNames = { "ID", "Nome", "Cognome", "Data di nascita", "Data assunzione", "Ruolo", "Username" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(tableModel);
		//Listener della tabella per pescare i nomi che servono
		 table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent event) {
	            	DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
	                if (!event.getValueIsAdjusting()) {
	                    int selectedRow = table.getSelectedRow();
	                    if (selectedRow != -1) {
	                    	selectedId=Integer.parseInt(String.valueOf(table.getValueAt(selectedRow, 0)));	
	                    	String name=String.valueOf(table.getValueAt(selectedRow, 1));
	                    	String surname=String.valueOf(table.getValueAt(selectedRow, 2));
	                    	String BoDString=String.valueOf(table.getValueAt(selectedRow, 3));
	                    	LocalDate BoD=LocalDate.parse(BoDString, format);
	                    	String roleString=String.valueOf(table.getValueAt(selectedRow, 4));
	                    	Roles role=Roles.fromString(roleString);
	                    	String username=String.valueOf(table.getValueAt(selectedRow, 5));
	                    	

	                    	
	                    	//{ "ID", "Nome", "Cognome", "Data di nascita", "Data assunzione", "Ruolo", "Username" };
	                       
	                       //Il listener ascolta la riga selezionata e la usa per popolare i campi
	                       

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
		containerPanel.add(btnSearch);

		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txfSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setBounds(720, 8, 40, 30);
		containerPanel.add(btnInsert);


		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setBounds(770, 8, 40, 30);
		containerPanel.add(btnUpdate);

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/delete.png")));
		btnDelete.setBounds(820, 8, 40, 30);
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
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome*:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(209, 436, 220, 20);
		add(txtName);

		JLabel lblSurname = new JLabel("Cognome*:");
		lblSurname.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSurname.setBounds(43, 474, 170, 17);
		add(lblSurname);

		txtSurname = new JTextField();
		txtSurname.setColumns(10);
		txtSurname.setBounds(209, 474, 220, 20);
		add(txtSurname);

		JLabel lblBirthday = new JLabel("Data di nascita");
		lblBirthday.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblBirthday.setBounds(43, 503, 170, 14);
		add(lblBirthday);

		String[] IVAs = { "Seleziona IVA" };

		JLabel lblIVA = new JLabel("IBAN:");
		lblIVA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblIVA.setBounds(43, 553, 170, 14);
		add(lblIVA);

		txtIban = new JTextField();
		txtIban.setColumns(10);
		txtIban.setBounds(209, 552, 220, 20);
		add(txtIban);

		JLabel lblBirthdayFormat = new JLabel("(gg-mm-aaaa):");
		lblBirthdayFormat.setBounds(40, 517, 96, 16);
		add(lblBirthdayFormat);

		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(749, 512, 220, 20);
		add(txtMail);

		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(749, 547, 220, 20);
		add(txtPhone);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(749, 432, 220, 20);
		add(txtAddress);

		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(531, 513, 170, 14);
		add(lblMail);

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setBounds(531, 552, 170, 14);
		add(lblPhone);

		JLabel lblAddress = new JLabel("Indirizzo:");
		lblAddress.setBounds(531, 437, 170, 14);
		add(lblAddress);

		MaskFormatter dateMask;
		try {// per settare il formato data
			dateMask = new MaskFormatter("##-##-####");
			txtBirthday = new JFormattedTextField(dateMask);
			txtBirthday.setColumns(10);
			txtBirthday.setBounds(209, 506, 220, 20);
			add(txtBirthday);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblRole = new JLabel("Ruolo:");
		lblRole.setBounds(531, 597, 170, 14);
		add(lblRole);

		String[] roles = { "Admin", "Receptionist", "Operatore" };
		roleComboBox = new JComboBox<String>(roles);
		roleComboBox.setBounds(749, 597, 220, 27);
		add(roleComboBox);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblUsername.setBounds(43, 592, 170, 14);
		add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(209, 591, 220, 20);
		add(txtUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPassword.setBounds(43, 633, 170, 14);
		add(lblPassword);

		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(209, 632, 220, 20);
		add(txtPassword);
		msgLbl = new JLabel("");
		msgLbl.setBounds(389, 606, 625, 16);
		add(msgLbl);
		
		JLabel lblBirthPlace = new JLabel("Città di nascita:");
		lblBirthPlace.setBounds(531, 475, 170, 14);
		add(lblBirthPlace);
		
		txtBirthplace = new JTextField();
		txtBirthplace.setColumns(10);
		txtBirthplace.setBounds(749, 474, 220, 20);
		add(txtBirthplace);
		
		maleRadioBtn = new JRadioButton("Uomo");
		maleRadioBtn.setBounds(517, 629, 141, 23);
		maleRadioBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(maleRadioBtn.isSelected()) {
					isFemale=false;
				}
			}
		});
		add(maleRadioBtn);
		
		femaleRadioBtn = new JRadioButton("Donna");
		femaleRadioBtn.setBounds(588, 629, 141, 23);
		femaleRadioBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(femaleRadioBtn.isSelected()) {
					isFemale=true;
				}
			}
		});
		add(femaleRadioBtn);
		
		
		ButtonGroup genderBtnGroup=new ButtonGroup();
		genderBtnGroup.add(femaleRadioBtn);
		genderBtnGroup.add(maleRadioBtn);
		
		JLabel notesLbl = new JLabel("Note:");
		notesLbl.setBounds(531, 664, 61, 16);
		add(notesLbl);
		
		txtNotes = new JTextArea();
		txtNotes.setBounds(749, 664, 220, 72);
		add(txtNotes);

	}

	private void clearTable() {
		tableModel.getDataVector().removeAllElements();
		revalidate();
	}

	private void clearFields() {
		txtName.setText("");
		txtSurname.setText("");
		txtBirthday.setText("");
		txtPhone.setText("");
		txtMail.setText("");
		txtAddress.setText("");
		txtIban.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
		txtBirthplace.setText("");
		txtNotes.setText("");
	}

	private void populateTable() {
		clearTable();
		clearFields();
		ArrayList<Employee> employees = EmployeeDao.getAllEmployees();
		if (employees.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Employee employee : employees) {
			if (employee.getTerminationDate() == null) {
				tableModel.addRow(new String[] { String.valueOf(employee.getEmployeeSerial(), employee.getName(),
						employee.getSurname(), String.valueOf(employee.getBoD()), employee.getRole().toString(),
						employee.getUserCredentials().getUser()) });
				// {"ID","Nome","Cognome","Data di nascita","Data
				// assunzione","Ruolo","Username"};
			}
		}

	}

	private void createEmployee() {
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if(!isDataValid())return;
		String name=txtName.getText();
		String surname=txtSurname.getText();
		String birthplace=txtBirthplace.getText();
		//isFemale
		LocalDate BoD=LocalDate.parse(txtBirthday.getText(), format);	
		String notes=txtNotes.getText();
		//isEnabled
		long employeeSerial=Employee.generateSerial();
		//shifts
		//hiredDate
		Roles role=Roles.fromString(roleComboBox.getSelectedItem().toString());
		//terminationDate
		String username=txtUsername.getText();
		String password=txtPassword.getText();
		String address=txtAddress.getText();
		String iban=txtIban.getText();
		String phone=txtPhone.getText();
		String mail=txtMail.getText();

			
		Employee employee=new Employee(name,surname,birthplace,isFemale,BoD,notes,true,employeeSerial,null,LocalDate.now(),
				role,null,username,password,address,iban,phone,mail);
		EmployeeDao.insertData(employee);
		msgLbl.setText("Nuovo utente creato correttamente");
		populateTable();
		

	}
	
	public void deleteEmployee() {
		msgLbl.setText("");
		EmployeeDao.toggleEnabledData(selectedId);
		msgLbl.setText("Utente rimosso correttamente");
		populateTable();
	}

	private boolean isDataValid() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		// checkNome
		if (!inputValidator.validateName(txtName.getText())) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		// checkCognome
		if (!inputValidator.validateName(txtSurname.getText())) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		try {
			// check data valida e impiegato maggiorenne
			LocalDate birthday = LocalDate.parse(txtBirthday.getText(), format);
			if (LocalDate.now().getYear() - birthday.getYear() < 18) {
				msgLbl.setText("Il tuo impiegato deve essere maggiorenne!");
				return false;
			}
		} catch (DateTimeException e) {
			msgLbl.setText("Inserire data valida!");
		}
		// check iban valido
		if (!inputValidator.validateIban(txtIban.getText(), false)) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		if (!inputValidator.validateName(txtUsername.getText())) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		// check user unico
		if (!inputValidator.isUserUnique(txtUsername.getText())) {
			msgLbl.setText("Esiste già un utente con lo stesso username!");
			return false;
		}
		if (!inputValidator.validatePassword(txtPassword.getText())) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		// l'indirizzo non è obbligatorio, quindi non mi viene in mente nessun check da
		// fare
		// check telefono
		if (!inputValidator.validatePhoneNumber(txtPhone.getText())) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		if (!inputValidator.validateEmail(txtMail.getText())) {
			msgLbl.setText(inputValidator.getErrorMessage());
			return false;
		}
		// tutto è bene quel che finisce bene
		return true;
	}

	public JTextField getTxtSurname() {
		return txtSurname;
	}

	public void setTxtSurname(JTextField txtSurname) {
		this.txtSurname = txtSurname;
	}

	public JTextField getTxfSearchBar() {
		return txfSearchBar;
	}

	public void setTxfSearchBar(JTextField txfSearchBar) {
		this.txfSearchBar = txfSearchBar;
	}

	public JTextField getTxtName() {
		return txtName;
	}

	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}

	public JTextField getTxtIban() {
		return txtIban;
	}

	public void setTxtIban(JTextField txtIban) {
		this.txtIban = txtIban;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTextField getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(JTextField txtMail) {
		this.txtMail = txtMail;
	}

	public JTextField getTxtPhone() {
		return txtPhone;
	}

	public void setTxtPhone(JTextField txtPhone) {
		this.txtPhone = txtPhone;
	}

	public JTextField getTxtAddress() {
		return txtAddress;
	}

	public void setTxtAddress(JTextField txtAddress) {
		this.txtAddress = txtAddress;
	}

	public JTextField getTxtBirthday() {
		return txtBirthday;
	}

	public void setTxtBirthday(JTextField txtBirthday) {
		this.txtBirthday = txtBirthday;
	}

	public JTextField getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(JTextField txtUsername) {
		this.txtUsername = txtUsername;
	}

	public JTextField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(JTextField txtPassword) {
		this.txtPassword = txtPassword;
	}
}
