package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.DAOShift;
import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.controller.ShiftController;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.ShiftType;
import com.github.lgooddatepicker.components.DateTimePicker;


public class ShiftForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblEndShift;
	private JTextField txfSearchBar;
	private JTextField txfNotes;	
	private int selectedRow = -1;
	private ButtonGroup group;
	private DateTimePicker startShift;
	private DateTimePicker endShift;
	private JLabel lblManageShifts;
	private JPanel containerPanel;
	private JScrollPane scrollPane;
	private JPanel outputPanel;
	private JButton btnSearch;
	private JButton btnFilter;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDisable;
	private JButton btnHystorical;
	private JLabel lblOperator;
	private JComboBox cBoxEmployee;
	private JLabel lblShiftType;
	private JLabel lblStartShift;
	private JRadioButton rdbtnWork;
	private JRadioButton rdbtnHolidays;
	private JLabel lblNotes;
	private JTable table;
	private JPanel controlsPanel;
	private DefaultTableModel tableModel;
	private JButton confirmBtn;
	private JButton cancelBtn;
	
	private ShiftType shiftType;

	/**
	 * Create the panel.
	 */
	public ShiftForm() {
		initialize();
		events();
		loadShifts();
		loadEmployees();
	}

	private void initialize() {		
		shiftType = ShiftType.WORK;
		
		setLayout(null);
		setSize(1024, 768);				
		
		containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 52, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "Operatore", "Inizio Turno", "Fine Turno", "Tipo Turno", "Note" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		table = new JTable(tableModel);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		outputPanel = new JPanel();
		scrollPane.setColumnHeaderView(outputPanel);
		outputPanel.setLayout(null);

		lblManageShifts = new JLabel("GESTIONE TURNI");
		lblManageShifts.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblManageShifts.setBounds(413, 11, 179, 32);
		add(lblManageShifts);

		btnSearch = new JButton("");

		btnSearch.setIcon(new ImageIcon(ShiftForm.class.getResource("/iconeGestionale/searchIcon.png")));
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

		btnFilter = new JButton("");
		btnFilter.setIcon(new ImageIcon(ShiftForm.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		btnInsert = new JButton("");
		btnInsert.setIcon(new ImageIcon(ShiftForm.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(793, 8, 40, 30);
		containerPanel.add(btnInsert);

		btnUpdate = new JButton("");
		btnUpdate.setIcon(new ImageIcon(ShiftForm.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(843, 8, 40, 30);
		containerPanel.add(btnUpdate);

		btnDisable = new JButton("");
		btnDisable.setIcon(new ImageIcon(ShiftForm.class.getResource("/iconeGestionale/disable.png")));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(893, 8, 40, 30);
		containerPanel.add(btnDisable);

		btnHystorical = new JButton("");
		btnHystorical.setIcon(new ImageIcon(ShiftForm.class.getResource("/iconeGestionale/cartellina.png")));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(943, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// parte inferiore

		controlsPanel = new JPanel();
		controlsPanel.setBounds(370, 424, 432, 334);
		controlsPanel.setLayout(null);
		controlsPanel.setEnabled(false);
		add(controlsPanel);

		rdbtnWork = new JRadioButton("Lavoro");
		rdbtnWork.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnWork.setBounds(145, 148, 109, 23);
		rdbtnWork.setEnabled(false);
		rdbtnWork.setSelected(true);		
		controlsPanel.add(rdbtnWork);

		rdbtnHolidays = new JRadioButton("Ferie");
		rdbtnHolidays.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnHolidays.setBounds(256, 148, 109, 23);
		rdbtnHolidays.setEnabled(false);
		controlsPanel.add(rdbtnHolidays);

		txfNotes = new JTextField();
		txfNotes.setColumns(10);
		txfNotes.setBounds(145, 194, 220, 59);
		txfNotes.setEnabled(false);
		controlsPanel.add(txfNotes);

		lblOperator = new JLabel("Operatore:");
		lblOperator.setBounds(10, 10, 170, 14);
		lblOperator.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblOperator.setEnabled(false);
		controlsPanel.add(lblOperator);

		lblStartShift = new JLabel("Inizio Turno*:");
		lblStartShift.setBounds(10, 51, 84, 17);
		lblStartShift.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblStartShift.setEnabled(false);
		controlsPanel.add(lblStartShift);

		lblEndShift = new JLabel("Fine Turno*:");
		lblEndShift.setBounds(10, 97, 84, 17);
		lblEndShift.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblEndShift.setEnabled(false);
		controlsPanel.add(lblEndShift);

		lblShiftType = new JLabel("Tipo Turno:");
		lblShiftType.setBounds(10, 151, 170, 14);
		lblShiftType.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblShiftType.setEnabled(false);
		controlsPanel.add(lblShiftType);

		lblNotes = new JLabel("Note Aggiuntive:");
		lblNotes.setBounds(10, 192, 170, 19);
		lblNotes.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNotes.setEnabled(false);
		controlsPanel.add(lblNotes);

		cBoxEmployee = new JComboBox();
		cBoxEmployee.setBounds(145, 7, 220, 22);
		cBoxEmployee.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		cBoxEmployee.setModel(new DefaultComboBoxModel());
		cBoxEmployee.setEnabled(false);
		controlsPanel.add(cBoxEmployee);

		startShift = new DateTimePicker();
		startShift.setBounds(145, 50, 220, 20);
		startShift.setEnabled(false);
		controlsPanel.add(startShift);

		endShift = new DateTimePicker();
		endShift.setBounds(145, 98, 220, 20);
		endShift.setEnabled(false);
		controlsPanel.add(endShift);

		confirmBtn = new JButton("Conferma");
		confirmBtn.setBounds(145, 303, 109, 21);
		confirmBtn.setEnabled(false);
		controlsPanel.add(confirmBtn);

		cancelBtn = new JButton("Annulla");
		cancelBtn.setBounds(254, 303, 111, 21);
		cancelBtn.setEnabled(false);
		controlsPanel.add(cancelBtn);
		
		group = new ButtonGroup();
		//group.setBounds(254, 303, 111, 21);
		//group.setEnabled(false);
		group.add(rdbtnWork);
		group.add(rdbtnHolidays);
		//controlsPanel.add(group);
	}

	private void events() {
		// crea
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// sblocca il pannello inferiore e tutti i suoi componenti
				enablePanel(controlsPanel);
				disableEnableUpperCommands(false);
			}
		});

		// cancella
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableEnableUpperCommands(false);
			}
		});
		// modifica
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// cerca
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// conferma  
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(sendShiftToController()) {
					disablePanel(controlsPanel);														
					cleanPanelData(controlsPanel);
					disableEnableUpperCommands(true);
				}
				
			}
		});

		// annulla
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disablePanel(controlsPanel);
				// crea oggetto e mandalo al controller

				// pulisci il pannello
				cleanPanelData(controlsPanel);
			}
		});

		rdbtnWork.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {								
				shiftType = ShiftType.WORK;				
			}
		});
		
		rdbtnHolidays.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {									
				shiftType = ShiftType.HOLIDAYS;								
			}
		});
	}
	
	private void loadShifts() {
		//controller a cui passo il dao e a sua volta richiama lo usecase
		//
		String[] columnNames = { "ID", "Cliente", "Data e Ora", "Operatore", "Servizio", "Durata" };
	}
	
	private void loadEmployees() {
		List<Employee> employeesList = EmployeeDAO.getEmployeesByRole(Roles.PERSONNEL); //----> da sostituire con getAllBeauticians		
		Vector<Employee> employeesVector = new Vector<>(employeesList);
		DefaultComboBoxModel<Employee> employeesModel = new DefaultComboBoxModel<>(employeesVector);
		cBoxEmployee.setModel(employeesModel);		
	}

	private void enablePanel(Component component) {
		component.setEnabled(true);
		if (component instanceof Container) {
			if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component view = scrollPane.getViewport().getView();
				if (view != null) {
					enablePanel(view); // Chiamata ricorsiva per la vista del JScrollPane
				}
			} else {
				for (Component child : ((Container) component).getComponents()) {
					enablePanel(child); // Chiamata ricorsiva per i componenti figli
				}
			}
		}
	}

	private void disablePanel(Component component) {
		component.setEnabled(false);
		if (component instanceof Container) {
			if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component view = scrollPane.getViewport().getView();
				if (view != null) {
					disablePanel(view);
				}
			}

			else {
				for (Component child : ((Container) component).getComponents()) {
					disablePanel(child);
				}
			}
		}
	}

	private void cleanPanelData(Component component) {
		try {
			if (component instanceof JPanel) {
				for (Component comp : ((JPanel) component).getComponents()) {
					cleanPanelData(comp);
				}
			} 
			else if (component instanceof JTextField) {
				((JTextField) component).setText("");
			} 
			else if (component instanceof JComboBox) {
				((JComboBox<?>) component).setSelectedIndex(-1);
			}

			else if (component instanceof JList) {
				JList<?> list = (JList<?>) component;
				ListModel<?> model = list.getModel();
				if (model instanceof DefaultListModel<?>) {
					((DefaultListModel<?>) model).clear();
				}
			} 
			else if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component view = scrollPane.getViewport().getView();
				cleanPanelData(view);
			}

			else if (component instanceof JRadioButton) {
				JRadioButton rb = (JRadioButton) component;
				rb.setSelected(false);
			}
			
		}

		catch (Exception ex) {
			return;
		}
	}
	
	private void disableEnableUpperCommands(boolean flag) {
		txfSearchBar.setEnabled(flag);
		btnInsert.setEnabled(flag);
		btnUpdate.setEnabled(flag);
		btnDisable.setEnabled(flag);
		btnHystorical.setEnabled(flag);
		btnSearch.setEnabled(flag);
		btnFilter.setEnabled(flag);
	}
	
	private boolean sendShiftToController() {
		DAOShift daoShift = new DAOShift();		
		ShiftController shiftController = new ShiftController(daoShift);
		
		//employee
		Employee employee = null;
		if(cBoxEmployee.getItemCount() > 0) {			
			employee = (Employee)cBoxEmployee.getSelectedItem();
		}				
		
		//orario di inizio e di fine
		LocalDateTime startShiftDT = startShift.getDateTimePermissive();
		LocalDateTime endShiftDT = endShift.getDateTimePermissive();
		String notes = txfNotes.getText();
				
		
		try {
			//shiftController.execute(employee, startShiftDT, endShiftDT, shiftType, notes);
			return true;
		}
		
		catch(IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return false;
		}		
	}
}
