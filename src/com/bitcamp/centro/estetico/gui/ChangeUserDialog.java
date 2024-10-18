package com.bitcamp.centro.estetico.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.models.Employee;

public class ChangeUserDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txfNewUsername;
	private JPasswordField txfPassword;
	private Employee activeUser;
	private UserAccessPanel parentPanel;

	/**
	 * Create the frame.
	 */
	public ChangeUserDialog(UserAccessPanel parentPanel, Employee activeUser) {
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		setTitle("Cambia username");
		this.activeUser = activeUser;
		this.parentPanel = parentPanel;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JLabel lblNewUsername = new JLabel("Nuovo username:");
		lblNewUsername.setBounds(6, 44, 116, 16);
		getContentPane().add(lblNewUsername);

		JLabel lblPasswordCheck = new JLabel("Password:");
		lblPasswordCheck.setBounds(6, 84, 149, 16);
		getContentPane().add(lblPasswordCheck);

		txfNewUsername = new JTextField();
		txfNewUsername.setBounds(179, 39, 182, 26);
		getContentPane().add(txfNewUsername);

		JButton confirmBtn = new JButton("Conferma");
		confirmBtn.addActionListener(e -> updateUsername());
		confirmBtn.setBounds(38, 168, 117, 29);

		getContentPane().add(confirmBtn);

		txfPassword = new JPasswordField();
		txfPassword.setBounds(179, 79, 182, 26);
		getContentPane().add(txfPassword);

		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(179, 117, 182, 23);
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
		getContentPane().add(visibleCheck);

		JLabel msgLbl = new JLabel(""); // label per i messaggi all'utente
		msgLbl.setBounds(179, 168, 182, 16);
		getContentPane().add(msgLbl);
		setVisible(true);
		setVisible(true);
	}

	private void updateUsername() {
		if (!isDataValid()) return;
		
		String username = txfNewUsername.getText();
		activeUser.setUsername(username);
		
		UserCredentialsDAO.updateEmployeeUserCredentials(activeUser);
		JOptionPane.showMessageDialog(this, "Username modificato correttamente");
		
		parentPanel.updateData();
		dispose();

	}

	private boolean isDataValid() {
		if (!UserCredentialsDAO.isUsernameUnique(txfNewUsername.getText())) {
			JOptionPane.showMessageDialog(this, "Nuovo username già in uso");
			return false;
		}
		if (!activeUser.isValidPassword(txfPassword.getPassword())) {
			System.out.println(activeUser.getUsername());
			JOptionPane.showMessageDialog(this, "Password errata");
			return false;
		}
		return true;
	}
}
