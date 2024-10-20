package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class gestioneTurni extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfInizioturno;
	private JTextField txfSearchBar;
	private JTextField txfNotes;
	private JTextField txtFineTurno;
	private int selectedRow = -1;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public gestioneTurni() {
		setLayout(null);
		setSize(1024, 768);

		JLabel lblGestioneTurni = new JLabel("GESTIONE TURNI");
		lblGestioneTurni.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblGestioneTurni.setBounds(413, 11, 179, 32);
		add(lblGestioneTurni);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 52, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "Operatore", "Inizio Turno", "Fine Turno", "Tipo Turno", "Note" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(tableModel);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		JPanel outputPanel = new JPanel();
		scrollPane.setColumnHeaderView(outputPanel);
		outputPanel.setLayout(null);

		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setIcon(new ImageIcon(gestioneTurni.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
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
		btnFilter.setIcon(new ImageIcon(gestioneTurni.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
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
		btnInsert.setIcon(new ImageIcon(gestioneTurni.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(793, 8, 40, 30);
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setIcon(new ImageIcon(gestioneTurni.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(843, 8, 40, 30);
		containerPanel.add(btnUpdate);

		JButton btnDisable = new JButton("");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDisable.setIcon(new ImageIcon(gestioneTurni.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(893, 8, 40, 30);
		containerPanel.add(btnDisable);

		JButton btnHystorical = new JButton("");
		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHystorical.setIcon(new ImageIcon(gestioneTurni.class.getResource("/com/bitcamp/centro/estetico/resources/cartellina.png")));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(943, 8, 40, 30);
		containerPanel.add(btnHystorical);

		JLabel lblOperatore = new JLabel("Operatore:");
		lblOperatore.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblOperatore.setBounds(321, 423, 170, 14);
		add(lblOperatore);

		JComboBox cBoxEmployee = new JComboBox();
		cBoxEmployee.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		cBoxEmployee.setModel(new DefaultComboBoxModel(new String[] { "Seleziona Operatore" }));
		cBoxEmployee.setBounds(515, 421, 220, 22);
		add(cBoxEmployee);

		JLabel lblInizioTurno = new JLabel("Inizio Turno*:");
		lblInizioTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblInizioTurno.setBounds(321, 470, 170, 17);
		add(lblInizioTurno);

		txfInizioturno = new JTextField();
		txfInizioturno.setColumns(10);
		txfInizioturno.setBounds(515, 470, 220, 20);
		add(txfInizioturno);

		JLabel lblFineTurno = new JLabel("Fine Turno*:");
		lblFineTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblFineTurno.setBounds(321, 522, 170, 14);
		add(lblFineTurno);

		txtFineTurno = new JTextField();
		txtFineTurno.setColumns(10);
		txtFineTurno.setBounds(515, 521, 220, 20);
		add(txtFineTurno);

		JLabel lblTipoTurno = new JLabel("Tipo Turno:");
		lblTipoTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblTipoTurno.setBounds(321, 572, 170, 14);
		add(lblTipoTurno);

		JRadioButton rdbtnLavoro = new JRadioButton("Lavoro");
		rdbtnLavoro.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnLavoro.setBounds(515, 569, 109, 23);
		add(rdbtnLavoro);

		JRadioButton rdbtnFerie = new JRadioButton("Ferie");
		rdbtnFerie.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnFerie.setBounds(626, 569, 109, 23);
		add(rdbtnFerie);

		JLabel lblNotes = new JLabel("Note Aggiuntive:");
		lblNotes.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNotes.setBounds(321, 619, 170, 19);
		add(lblNotes);

		txfNotes = new JTextField();
		txfNotes.setColumns(10);
		txfNotes.setBounds(515, 619, 220, 59);
		add(txfNotes);

	}
}
