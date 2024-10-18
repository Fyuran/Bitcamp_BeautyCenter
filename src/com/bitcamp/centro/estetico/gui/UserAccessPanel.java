package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Shift;

public class UserAccessPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter dayFormat;
	private DateTimeFormatter timeFormat;
	private NonEditableTableModel model;
	private Employee sessionUser = MainFrame.getSessionUser();
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

	/**
	 * Create the panel.
	 *
	 * @throws PropertyVetoException
	 */
	public UserAccessPanel() {
		dayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		timeFormat = DateTimeFormatter.ofPattern("hh:mm");
		setLayout(null);
		setSize(1024, 768);
		setName("Area personale");
		JLabel titleTab = new JLabel("Benvenuto " + sessionUser.getName());
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

		String[] columnNames = { "Giorno", "Inizio turno", "Fine turno", "Tipologia" };
		model = new NonEditableTableModel(columnNames, 0);

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
		// popolamento tabella
		int i=1;

		for (Shift shift : sessionUser.getShift()) {

			if (!shift.isShiftOver()) {
				String day = shift.getStart().format(dayFormat);
				String start = shift.getStart().format(timeFormat);
				String end = shift.getEnd().format(timeFormat);
				String type = shift.getType() == null ? null : shift.getType().toString();
				System.out.println("Turno "+i+":\n ID: "+shift.getId()+"\nGiorno: "+day+ "\nDa a: "+start+"-"+end+"\nTipo: "+type);
				i++;
				model.addRow(new String[] { day, start, end, type });


			}
		}
	}

	public void updateData() {
		lblName.setText("Nome: " + sessionUser.getName());
		lblSurname.setText("Cognome: " + sessionUser.getSurname());
		lblRole.setText("Ruolo: " + sessionUser.getRole().toString());
		lblAddress.setText("Indirizzo: " + sessionUser.getAddress() + ", " + sessionUser.getBirthplace());
		lblIBAN.setText("IBAN: " + sessionUser.getIban());
		lblHireDate.setText("Data di assunzione: " + sessionUser.getHiredDate());
		lblUsername.setText("Username: " + sessionUser.getUsername());
		lblMail.setText("Mail: " + sessionUser.getMail());
		lblPhone.setText("Telefono: " + sessionUser.getPhone());
	}

	public void changePassword() {
		ChangePassDialog changePassDialog = new ChangePassDialog(sessionUser);
		changePassDialog.setAlwaysOnTop(true);
		updateActiveUser();


	}

	public void changeUsername() {
		ChangeUserDialog changeUserDialog = new ChangeUserDialog(this,sessionUser);
		changeUserDialog.setAlwaysOnTop(true);
		updateActiveUser();
		updateData();

	}
	public void updateActiveUser() {
		this.sessionUser=EmployeeDAO.getEmployee(sessionUser.getId()).get();
	}

}
