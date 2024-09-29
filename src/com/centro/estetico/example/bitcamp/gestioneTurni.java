package com.centro.estetico.example.bitcamp;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class gestioneTurni extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfInizioturno;
	private JTextField txfSearchBar;
	private JTextField txfNotes;
	private JTextField txtFineTurno;

	/**
	 * Create the panel.
	 */
	public gestioneTurni() {
		setLayout(null);
		setSize(1024,768);
		
		
		JLabel lblOperatore = new JLabel("Operatore:");
		lblOperatore.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblOperatore.setBounds(321, 423, 170, 14);
		add(lblOperatore);
		
		txfInizioturno = new JTextField();
		txfInizioturno.setColumns(10);
		txfInizioturno.setBounds(515, 470, 220, 20);
		add(txfInizioturno);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 52, 1004, 347);
		add(containerPanel);
		
		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\searchIcon.png"));
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);
		
		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txfSearchBar);
		
		JButton btnFilter = new JButton("");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFilter.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\filterIcon.png"));
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);
		
		JButton btnInsert = new JButton("");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInsert.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\Insert.png"));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(720, 8, 40, 30);
		containerPanel.add(btnInsert);
		
		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\Update.png"));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(770, 8, 40, 30);
		containerPanel.add(btnUpdate);
		
		JButton btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelete.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\delete.png"));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(820, 8, 40, 30);
		containerPanel.add(btnDelete);
		
		JButton btnDisable = new JButton("");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDisable.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\disattivo.png"));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(920, 8, 40, 30);
		containerPanel.add(btnDisable);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 960, 276);
		containerPanel.add(outputPanel);
		
		JButton btnHystorical = new JButton("");
		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHystorical.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\cartellina.png"));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);
		
		JLabel lblGestioneTurni = new JLabel("GESTIONE TURNI");
		lblGestioneTurni.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblGestioneTurni.setBounds(413, 11, 179, 32);
		add(lblGestioneTurni);
		
		JLabel lblInizioTurno = new JLabel("Inizio Turno:");
		lblInizioTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblInizioTurno.setBounds(321, 470, 170, 17);
		add(lblInizioTurno);
		
		JLabel lblNotes = new JLabel("Note Aggiuntive:");
		lblNotes.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNotes.setBounds(321, 619, 170, 19);
		add(lblNotes);
		
		JLabel lblFineTurno = new JLabel("Fine Turno:");
		lblFineTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblFineTurno.setBounds(321, 522, 170, 14);
		add(lblFineTurno);
		
		JLabel lblTipoTurno = new JLabel("Tipo Turno:");
		lblTipoTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblTipoTurno.setBounds(321, 572, 170, 14);
		add(lblTipoTurno);
		
		txfNotes = new JTextField();
		txfNotes.setColumns(10);
		txfNotes.setBounds(515, 619, 220, 59);
		add(txfNotes);
		
		JComboBox cBoxEmployee = new JComboBox();
		cBoxEmployee.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		cBoxEmployee.setModel(new DefaultComboBoxModel(new String[] {"Seleziona Operatore"}));
		cBoxEmployee.setBounds(515, 421, 220, 22);
		add(cBoxEmployee);
		
		txtFineTurno = new JTextField();
		txtFineTurno.setColumns(10);
		txtFineTurno.setBounds(515, 521, 220, 20);
		add(txtFineTurno);
		
		JRadioButton rdbtnLavoro = new JRadioButton("Lavoro");
		rdbtnLavoro.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnLavoro.setBounds(515, 569, 109, 23);
		add(rdbtnLavoro);
		
		JRadioButton rdbtnFerie = new JRadioButton("Ferie");
		rdbtnFerie.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnFerie.setBounds(626, 569, 109, 23);
		add(rdbtnFerie);

	}
}
