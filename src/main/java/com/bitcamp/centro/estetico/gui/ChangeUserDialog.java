package com.bitcamp.centro.estetico.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.utils.JSplitPanel;
import com.bitcamp.centro.estetico.utils.JSplitPf;
import com.bitcamp.centro.estetico.utils.JSplitTxf;

public class ChangeUserDialog extends JDialog {
	private final JSplitTxf usernameTxf;
	private final JSplitPf passwordTxf;
	private final JSplitPanel panel;
	private final JButton confirmBtn;
	private final User user = MainFrame.getSessionUser();
	private final UserAccessPanel parentPanel;

	public ChangeUserDialog(UserAccessPanel parentPanel) {
		setTitle("Cambia username");
		setLayout(new GridBagLayout());
		this.parentPanel = parentPanel;

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lbOutput = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		add(lbOutput, gbc);

		panel = new JSplitPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(panel, gbc);

		confirmBtn = new JButton("Conferma");
		confirmBtn.addActionListener(e -> updateUsername());

		usernameTxf = new JSplitTxf("Username");
		passwordTxf = new JSplitPf("Password");

		panel.add(usernameTxf);
		panel.add(passwordTxf);
		panel.add(confirmBtn);

		setVisible(true);
	}

	private void updateUsername() {
		if (!isDataValid()) return;
		
		String username = usernameTxf.getText();
		user.setUsername(username);
		
		DAO.update(user);
		JOptionPane.showMessageDialog(this, "Username modificato correttamente");
		
		parentPanel.updateData();
		dispose();

	}

	private boolean isDataValid() {
		if (!user.isValidPassword(passwordTxf.getPassword())) {
			JOptionPane.showMessageDialog(this, "Password errata");
			return false;
		}
		return true;
	}
}
