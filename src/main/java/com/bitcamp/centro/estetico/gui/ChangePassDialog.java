package com.bitcamp.centro.estetico.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Employee;

public class ChangePassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static JPasswordField newPasswordTxt1;
	private static JPasswordField oldPasswordTxt;
	private static JPasswordField newPasswordTxt2;
	private static Employee activeUser;

	/**
	 * Create the frame.
	 */
	public ChangePassDialog(Employee activeUser) {
		setBounds(100, 100, 400, 500);
		getContentPane().setLayout(null);
		setTitle("Cambia password");
		this.activeUser = activeUser;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JLabel lblOldPassword = new JLabel("Vecchia password:");
		lblOldPassword.setBounds(6, 91, 116, 16);
		getContentPane().add(lblOldPassword);

		JLabel lblNewPassword1 = new JLabel("Nuova password:");
		lblNewPassword1.setBounds(6, 132, 116, 16);
		getContentPane().add(lblNewPassword1);

		JLabel lblNewPassword2 = new JLabel("Nuova password(ripeti):");
		lblNewPassword2.setBounds(6, 172, 149, 16);
		getContentPane().add(lblNewPassword2);

		newPasswordTxt1 = new JPasswordField();
		newPasswordTxt1.setBounds(179, 127, 182, 26);
		getContentPane().add(newPasswordTxt1);

		JButton confirmBtn = new JButton("Conferma");
		confirmBtn.addActionListener(e -> updatePassword());
		confirmBtn.setBounds(38, 256, 117, 29);

		getContentPane().add(confirmBtn);

		oldPasswordTxt = new JPasswordField();
		oldPasswordTxt.setBounds(179, 86, 182, 26);
		getContentPane().add(oldPasswordTxt);

		newPasswordTxt2 = new JPasswordField();
		newPasswordTxt2.setBounds(179, 167, 182, 26);
		getContentPane().add(newPasswordTxt2);

		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(179, 205, 182, 23);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (visibleCheck.isSelected()) {
					oldPasswordTxt.setEchoChar((char) 0);
					newPasswordTxt1.setEchoChar((char) 0);
					newPasswordTxt2.setEchoChar((char) 0);
				} else {
					oldPasswordTxt.setEchoChar('•');
					newPasswordTxt1.setEchoChar('•');
					newPasswordTxt2.setEchoChar('•');
				}
			}
		});
		getContentPane().add(visibleCheck);

		JLabel msgLbl = new JLabel(""); // label per i messaggi all'utente
		msgLbl.setBounds(179, 261, 182, 16);
		getContentPane().add(msgLbl);
		setVisible(true);
	}

	private void updatePassword() {
		if (!isValidPassword()) return;
		activeUser.setPassword(newPasswordTxt1.getPassword());
		
		DAO.update(activeUser); 
		JOptionPane.showMessageDialog(this, "Password modificata correttamente");
		dispose();

	}

	private boolean isValidPassword() {
		char[] oldPassword = oldPasswordTxt.getPassword();
		char[] newPassword1 = newPasswordTxt1.getPassword();
		char[] newPassword2 = newPasswordTxt2.getPassword();
		System.out.println(oldPassword);
		
		if (!activeUser.isValidPassword(oldPassword)) {
			JOptionPane.showMessageDialog(this, "Vecchia password errata");
			return false;
		}
		if (!newPassword1.equals(newPassword2)) {
			JOptionPane.showMessageDialog(this, "Le password non coincidono");
			return false;
		}
		return true;
	}

}
