package com.bitcamp.centro.estetico.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.Employee;

public class SetupWelcomeFrame extends JFrame {

	private static final long serialVersionUID = 5654094869271673775L;
	private static JButton nextBtn;
	
	public SetupWelcomeFrame() {
		setTitle("Benvenuto nel Gestionale Centro Estetico");
		setName("Benvenuto nel Gestionale Centro Estetico");
		setSize(1024, 768);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setIconImage(new ImageIcon(
			MainFrame.class.getResource("/com/bitcamp/centro/estetico/resources/bc_icon.png")).getImage());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 1024, 768);

		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(null);
		imagePanel.setBounds(0, 0, 1024, 400);

		JLabel imageLabel = new JLabel(
				new ImageIcon(
						SetupWelcomeFrame.class.getResource("/com/bitcamp/centro/estetico/resources/download.png")));
		imageLabel.setBounds(151, 20, 723, 360);
		imagePanel.add(imageLabel);

		JPanel textPanel = new JPanel();
		textPanel.setLayout(null);
		textPanel.setBounds(0, 400, 1024, 208);

		JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
		welcomeLabel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 24));
		welcomeLabel.setBounds(200, 10, 624, 40);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JTextArea descriptionArea = new JTextArea(
				"Il gestionale è progettato per offrire una soluzione completa "
						+ "per il tuo centro estetico. Organizza i tuoi appuntamenti, gestisci i clienti, "
						+ "e monitora tutto il centro con facilità. ");
		descriptionArea.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setEditable(false);
		descriptionArea.setBounds(100, 60, 824, 146);

		textPanel.add(welcomeLabel);
		textPanel.add(descriptionArea);

		mainPanel.add(imagePanel);
		mainPanel.add(textPanel);

		setLayout(null);
		add(mainPanel);

		nextBtn = new JButton("Entra");
		nextBtn.setBounds(423, 648, 200, 40);
		mainPanel.add(nextBtn);
		nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
		nextBtn.setFocusPainted(false);

		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (DAO.isEmpty(BeautyCenter.class)) {
					new SetupBeautyCenterFrame();
				} else {
					if (DAO.isEmpty(Employee.class)) {
						new SetupFirstAccountFrame();
					} else {
						new LoginFrame();
					}
				}
				dispose();
			}
		});
	}
}
