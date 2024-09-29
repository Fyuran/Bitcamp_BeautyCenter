package com.centro.estetico.example.bitcamp;

import java.util.Date;
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
import javax.swing.JTable;
import com.toedter.calendar.JCalendar;
import javax.swing.JList;

public class ReservationForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfSearchBar;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ReservationForm() {
		setLayout(null);
		setSize(1024,768);
		
		
		JLabel lblOperatore = new JLabel("Cliente");
		lblOperatore.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblOperatore.setBounds(35, 424, 170, 14);
		add(lblOperatore);
		
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
		
		table = new JTable();
		table.setBounds(10, 0, 940, 276);
		outputPanel.add(table);
		
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
		
		JLabel lblGestioneTurni = new JLabel("GESTIONE APPUNTAMENTI");
		lblGestioneTurni.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblGestioneTurni.setBounds(413, 11, 179, 32);
		add(lblGestioneTurni);
		
		JLabel lblInizioTurno = new JLabel("Trattamento");
		lblInizioTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblInizioTurno.setBounds(35, 469, 170, 17);
		add(lblInizioTurno);
		
		JButton btnNewButton = new JButton("...");
		btnNewButton.setBounds(133, 423, 85, 21);
		add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(133, 469, 85, 21);
		add(comboBox);
				
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblData.setBounds(35, 513, 95, 14);
		add(lblData);
		
		JButton dateButtonVisible = new JButton("V");		
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(35, 548, 257, 183);
		calendar.setVisible(false);
		disablePreviousDays(calendar);
		add(calendar);
		
		JButton getVisibleInvisibleCalendarButton = new JButton("V");
		
		getVisibleInvisibleCalendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getVisibleInvisibleCalendar(calendar);
			}
		});
		getVisibleInvisibleCalendarButton.setBounds(133, 512, 85, 21);
		add(getVisibleInvisibleCalendarButton);
		
		JList list = new JList();
		list.setBounds(513, 409, 79, 222);
		add(list);
			
	}
	
	private void getVisibleInvisibleCalendar(JCalendar calendar) {
		if(calendar.isVisible()) {
			calendar.setVisible(false);
		}
		else {
			calendar.setVisible(true);
		}
	}
	
	private void disablePreviousDays(JCalendar calendar) {
		Date today = new Date();
		calendar.setSelectableDateRange(today, null);
	}
}
