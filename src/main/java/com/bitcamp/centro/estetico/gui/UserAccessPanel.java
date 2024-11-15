package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Turn;
import com.bitcamp.centro.estetico.models.User;


public class UserAccessPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter dayFormat;
	private DateTimeFormatter timeFormat;
	private NonEditableTableModel<Turn> model;
	private User user = MainFrame.getSessionUser();
	private JLabel lblName;
	private JLabel lblUsername;
	private JLabel lblSurname;
	private JLabel lblRole;
	private JLabel lblMail;
	private JLabel lblAddress;
	private JLabel lblPhone;
	private JLabel lblIBAN;
	private JLabel lblBirthday;
	private JLabel lblHireDate;

	public UserAccessPanel() {
		dayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		timeFormat = DateTimeFormatter.ofPattern("hh:mm");
		setLayout(null);
		setSize(1024, 768);
		setName("Area personale");
		JLabel titleTab = new JLabel("Benvenuto " + user.getName());
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 206, 32);
		add(titleTab);

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "I tuoi dati:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		dataPanel.setBounds(47, 116, 459, 364);
		add(dataPanel);
		dataPanel.setLayout(null);

		lblName = new JLabel();
		lblName.setBounds(25, 47, 185, 16);

		dataPanel.add(lblName);

		lblSurname = new JLabel();
		lblSurname.setBounds(25, 87, 185, 16);
		dataPanel.add(lblSurname);

		lblRole = new JLabel();
		lblRole.setBounds(222, 87, 231, 16);
		dataPanel.add(lblRole);

		lblAddress = new JLabel();
		lblAddress.setBounds(25, 172, 415, 16);
		dataPanel.add(lblAddress);

		lblIBAN = new JLabel();
		lblIBAN.setBounds(25, 253, 353, 16);
		dataPanel.add(lblIBAN);

		lblBirthday = new JLabel();
		lblBirthday.setBounds(25, 281, 324, 16);
		dataPanel.add(lblBirthday);

		lblHireDate = new JLabel();
		lblHireDate.setBounds(25, 313, 335, 16);
		dataPanel.add(lblHireDate);

		lblUsername = new JLabel();
		lblUsername.setBounds(222, 47, 185, 16);
		dataPanel.add(lblUsername);

		lblMail = new JLabel();
		lblMail.setBounds(25, 128, 250, 16);
		dataPanel.add(lblMail);

		lblPhone = new JLabel();
		lblPhone.setBounds(25, 212, 324, 16);
		dataPanel.add(lblPhone);

		model = new NonEditableTableModel<>();

		// Creazione della tabella
		JTable shiftTable = new JTable(model);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane shiftScrollsPane = new JScrollPane(shiftTable);
		shiftScrollsPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "I tuoi turni:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		shiftScrollsPane.setBounds(535, 116, 459, 487);
		add(shiftScrollsPane);

		JButton changePasswordBtn = new JButton("Cambia password");
		changePasswordBtn.setBounds(200, 574, 145, 29);
		changePasswordBtn.addActionListener(e -> changePassword());
		add(changePasswordBtn);

		JButton changeUserBtn = new JButton("Cambia Username");
		changeUserBtn.setBounds(200, 529, 145, 29);
		changeUserBtn.addActionListener(e -> changeUsername());
		add(changeUserBtn);
		updateData();
		populateShiftTable();

	}

	void populateShiftTable() {
		if(user instanceof Employee employee) {
			var turns = employee.getTurns();
			if (turns == null)
				return;
			for (Turn shift : turns) {
				String day = shift.getStart().format(dayFormat);
				String start = shift.getStart().format(timeFormat);
				String end = shift.getEnd().format(timeFormat);
				String type = shift.getType() == null ? null : shift.getType().toString();
				model.addRow(new String[] { day, start, end, type });
			}
		}
	}

	public void updateData() {
		lblName.setText("Nome: " + user.getName());
		lblSurname.setText("Cognome: " + user.getSurname());
		if(user instanceof Employee employee) {
			lblRole.setText("Ruolo: " + employee.getRole().toString());
			lblHireDate.setText("Data di assunzione: " + employee.getHiredDate());
		}
		lblAddress.setText("Indirizzo: " + user.getAddress() + ", " + user.getBirthplace());
		lblIBAN.setText("IBAN: " + user.getIban());
		lblUsername.setText("Username: " + user.getUsername());
		lblMail.setText("Mail: " + user.getMail());
		lblPhone.setText("Telefono: " + user.getPhone());
	}

	public void changePassword() {
		ChangePassDialog changePassDialog = new ChangePassDialog();
		changePassDialog.setAlwaysOnTop(true);

	}

	public void changeUsername() {
		ChangeUserDialog changeUserDialog = new ChangeUserDialog(this);
		changeUserDialog.setAlwaysOnTop(true);
		updateData();

	}

}
