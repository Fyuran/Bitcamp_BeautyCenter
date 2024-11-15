package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.Employee;
import com.github.weisj.darklaf.components.border.DarkBorders;

public class SetupWelcomeFrame {
	private static JFrame frame;

	public SetupWelcomeFrame() {
		frame = new JFrame("Benvenuto");
		frame.setName("Benvenuto nel Gestionale Centro Estetico");
		frame.setSize(1000, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(new ImageIcon(
				MainFrame.class.getResource("/com/bitcamp/centro/estetico/resources/bc_icon.png")).getImage());
		frame.setResizable(false);
		frame.getRootPane().setBorder(DarkBorders.createWidgetLineBorder(5, 5, 5, 5));

		JPanel panel = new JPanel(new GridBagLayout());
		frame.add(panel);
		
		GridBagConstraints gbc = new GridBagConstraints();

		Image img = new ImageIcon(
				SetupWelcomeFrame.class.getResource("/com/bitcamp/centro/estetico/resources/download.png")).getImage();
		img = img.getScaledInstance(600, -1, Image.SCALE_FAST);
		JLabel imgLb = new JLabel(new ImageIcon(img));
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 0, 0, 0);
		panel.add(imgLb, gbc);

		JLabel welcomeLb = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
		welcomeLb.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 24));
		welcomeLb.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(welcomeLb, gbc);

		JLabel descriptionLb = new JLabel(
				"<html><p style='text-align:center'>Il gestionale è progettato per offrire una soluzione completa per il tuo centro estetico."
						+ "Organizza i tuoi appuntamenti, gestisci i clienti, e monitora tutto il centro con facilità.</p></html>");
		descriptionLb.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
		descriptionLb.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLb.setMinimumSize(new Dimension(600, 100));
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(descriptionLb, gbc);

		JButton nextBtn = new JButton("Entra");
		nextBtn.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		nextBtn.setFocusPainted(false);
		nextBtn.addActionListener((l) -> {
			if (DAO.isEmpty(BeautyCenter.class)) {
				new SetupBeautyCenterFrame();
			} else {
				if (DAO.isEmpty(Employee.class)) {
					new SetupFirstAccountFrame();
				} else {
					new LoginFrame();
				}
			}
			frame.dispose();
		});
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.NONE;
		nextBtn.setMinimumSize(new Dimension(200, 30));
		panel.add(nextBtn, gbc);

		frame.getRootPane().setDefaultButton(nextBtn);
	}
}
