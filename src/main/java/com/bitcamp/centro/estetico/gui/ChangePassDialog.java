package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.utils.JSplitPf;
import com.github.weisj.darklaf.components.border.DarkBorders;

public class ChangePassDialog extends JDialog {

	private final JSplitPf newPasswordTxf;
	private final JSplitPf newPasswordConfirmTxf;
	private final JSplitPf oldPasswordTxf;
	private final JButton confirmBtn;
	private final User user = MainFrame.getSessionUser();

	public ChangePassDialog() {
		setTitle("Cambio password");
		setSize(new Dimension(400, 400));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		getRootPane().setBorder(DarkBorders.createLineBorder(5, 5, 5, 5));
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lbOutput = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.2;
		add(lbOutput, gbc);

		oldPasswordTxf = new JSplitPf("Vecchia Password");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		add(oldPasswordTxf, gbc);

		newPasswordTxf = new JSplitPf("Nuova Password");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		add(newPasswordTxf, gbc);

		newPasswordConfirmTxf = new JSplitPf("<html><p style='text-align:center'>Ripeti Nuova Password</p><html>");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		add(newPasswordConfirmTxf, gbc);

		confirmBtn = new JButton("Conferma");
		confirmBtn.addActionListener(e -> updatePassword());
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		add(confirmBtn, gbc);

		setVisible(true);
	}

	private void updatePassword() {
		if (!isValidPassword())
			return;
		user.setPassword(newPasswordTxf.getPassword());

		DAO.update(user);
		JOptionPane.showMessageDialog(this, "Password modificata correttamente");
		dispose();

	}

	private boolean isValidPassword() {
		char[] oldPassword = oldPasswordTxf.getPassword();
		char[] newPassword1 = newPasswordTxf.getPassword();
		char[] newPassword2 = newPasswordConfirmTxf.getPassword();

		if (!user.isValidPassword(oldPassword)) {
			JOptionPane.showMessageDialog(this, "Vecchia password errata");
			return false;
		}
		if (!Arrays.equals(newPassword1, newPassword2)) {
			JOptionPane.showMessageDialog(this, "Le password non coincidono");
			return false;
		}
		return true;
	}

}
