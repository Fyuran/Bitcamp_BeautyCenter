package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.DAOShift;
import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.controller.ShiftController;
import com.bitcamp.centro.estetico.models.*;
import com.bitcamp.centro.estetico.useCases.CreateShiftUseCase;
import com.bitcamp.centro.estetico.useCases.DeleteShiftUseCase;
import com.bitcamp.centro.estetico.useCases.GetShiftUseCase;
import com.bitcamp.centro.estetico.useCases.UpdateShiftUseCase;
import com.github.lgooddatepicker.components.DateTimePicker;

public class ShiftForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblEndShift;
	private JTextField searchShift;
	private JTextField txtNotes;
	private int selectedRow = -1;
	private ButtonGroup group;
	private DateTimePicker startShift;
	private DateTimePicker endShift;
	private JLabel lblManageShifts;
	private JPanel containerPanel;
	private JScrollPane scrollPane;
	private JPanel outputPanel;
	private JButton searchShiftButton;
	private JButton cancelShiftButton;
	private JButton addShift;
	private JButton editShift;
	private JButton deleteShift;
	private JButton btnHystorical;
	private JLabel lblOperator;
	private JComboBox cBoxEmployee;
	private JLabel lblShiftType;
	private JLabel lblStartShift;
	private JRadioButton rdbtnWork;
	private JRadioButton rdbtnHolidays;
	private JLabel lblNotes;
	private JTable table;
	private JPanel anagraphicPanel;
	private DefaultTableModel tableModel;
	private JButton confirmBtn;
	private JButton cancelBtn;

	private ShiftType shiftType;
	private IsCreatingOrUpdating icou;

	private Map<Integer, ShiftEmployee> shifts;
	private Map<Integer, Employee> employees;

	private DAOShift daoShift;

	private ShiftController shiftGetController;
	private ShiftController shiftCreateController;
	private ShiftController shiftUpdateController;
	private ShiftController shiftDeleteController;

	private GetShiftUseCase getShiftUseCase;
	private CreateShiftUseCase createShiftUseCase;
	private UpdateShiftUseCase updateShiftUseCase;
	private DeleteShiftUseCase deleteShiftUseCase;

	private ShiftEmployee shiftEmployee;

	/**
	 * Create the panel.
	 */
	public ShiftForm() {
		initialize();
		events();
		shifts = getAllShifts();
		populateShiftsTable(shifts);
		loadEmployees();
	}

	private void initialize() {
		shiftType = ShiftType.WORK;
		shifts = new HashMap<Integer, ShiftEmployee>();
		employees = new HashMap<Integer, Employee>();
		daoShift = new DAOShift();

		getShiftUseCase = new GetShiftUseCase(daoShift);
		createShiftUseCase = new CreateShiftUseCase(daoShift);
		updateShiftUseCase = new UpdateShiftUseCase(daoShift);
		deleteShiftUseCase = new DeleteShiftUseCase(daoShift);

		shiftGetController = new ShiftController(getShiftUseCase);
		shiftCreateController = new ShiftController(createShiftUseCase);
		shiftUpdateController = new ShiftController(updateShiftUseCase);
		shiftDeleteController = new ShiftController(deleteShiftUseCase);

		setLayout(null);
		setSize(1024, 768);
		setName("Turni");

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

		searchShiftButton = new JButton("");

		searchShiftButton.setIcon(new ImageIcon(ShiftForm.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		searchShiftButton.setOpaque(false);
		searchShiftButton.setContentAreaFilled(false);
		searchShiftButton.setBorderPainted(false);
		searchShiftButton.setBounds(206, 8, 40, 30);
		containerPanel.add(searchShiftButton);

		searchShift = new JTextField();
		searchShift.setColumns(10);
		searchShift.setBackground(UIManager.getColor("CheckBox.background"));
		searchShift.setBounds(23, 14, 168, 24);
		containerPanel.add(searchShift);

		cancelShiftButton = new JButton("");
		cancelShiftButton.setIcon(new ImageIcon(ShiftForm.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		cancelShiftButton.setOpaque(false);
		cancelShiftButton.setContentAreaFilled(false);
		cancelShiftButton.setBorderPainted(false);
		cancelShiftButton.setBounds(256, 8, 40, 30);
		containerPanel.add(cancelShiftButton);

		addShift = new JButton("");
		addShift.setIcon(new ImageIcon(ShiftForm.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		addShift.setOpaque(false);
		addShift.setContentAreaFilled(false);
		addShift.setBorderPainted(false);
		addShift.setBounds(793, 8, 40, 30);
		containerPanel.add(addShift);

		editShift = new JButton("");
		editShift.setIcon(new ImageIcon(ShiftForm.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		editShift.setOpaque(false);
		editShift.setContentAreaFilled(false);
		editShift.setBorderPainted(false);
		editShift.setBounds(843, 8, 40, 30);
		containerPanel.add(editShift);

		deleteShift = new JButton("");
		deleteShift.setIcon(new ImageIcon(ShiftForm.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		deleteShift.setOpaque(false);
		deleteShift.setContentAreaFilled(false);
		deleteShift.setBorderPainted(false);
		deleteShift.setBounds(893, 8, 40, 30);
		containerPanel.add(deleteShift);

		btnHystorical = new JButton("");
		btnHystorical.setIcon(new ImageIcon(ShiftForm.class.getResource("/com/bitcamp/centro/estetico/resources/cartellina.png")));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(943, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// parte inferiore

		anagraphicPanel = new JPanel();
		anagraphicPanel.setBounds(370, 424, 432, 334);
		anagraphicPanel.setLayout(null);
		anagraphicPanel.setEnabled(false);
		add(anagraphicPanel);

		rdbtnWork = new JRadioButton("Lavoro");
		rdbtnWork.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnWork.setBounds(145, 148, 74, 23);
		rdbtnWork.setEnabled(false);
		rdbtnWork.setSelected(true);
		anagraphicPanel.add(rdbtnWork);

		rdbtnHolidays = new JRadioButton("Ferie");
		rdbtnHolidays.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		rdbtnHolidays.setBounds(256, 148, 109, 23);
		rdbtnHolidays.setEnabled(false);
		anagraphicPanel.add(rdbtnHolidays);

		txtNotes = new JTextField();
		txtNotes.setColumns(10);
		txtNotes.setBounds(145, 194, 220, 59);
		txtNotes.setEnabled(false);
		anagraphicPanel.add(txtNotes);

		lblOperator = new JLabel("Operatore:");
		lblOperator.setBounds(10, 10, 170, 14);
		lblOperator.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblOperator.setEnabled(false);
		anagraphicPanel.add(lblOperator);

		lblStartShift = new JLabel("Inizio Turno*:");
		lblStartShift.setBounds(10, 51, 84, 17);
		lblStartShift.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblStartShift.setEnabled(false);
		anagraphicPanel.add(lblStartShift);

		lblEndShift = new JLabel("Fine Turno*:");
		lblEndShift.setBounds(10, 97, 84, 17);
		lblEndShift.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblEndShift.setEnabled(false);
		anagraphicPanel.add(lblEndShift);

		lblShiftType = new JLabel("Tipo Turno:");
		lblShiftType.setBounds(10, 151, 121, 14);
		lblShiftType.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblShiftType.setEnabled(false);
		anagraphicPanel.add(lblShiftType);

		lblNotes = new JLabel("Note Aggiuntive:");
		lblNotes.setBounds(10, 192, 170, 19);
		lblNotes.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNotes.setEnabled(false);
		anagraphicPanel.add(lblNotes);

		cBoxEmployee = new JComboBox();
		cBoxEmployee.setBounds(145, 7, 220, 22);
		cBoxEmployee.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		cBoxEmployee.setModel(new DefaultComboBoxModel());
		cBoxEmployee.setEnabled(false);
		anagraphicPanel.add(cBoxEmployee);

		startShift = new DateTimePicker();
		startShift.setBounds(145, 50, 220, 20);
		startShift.setEnabled(false);
		anagraphicPanel.add(startShift);

		endShift = new DateTimePicker();
		endShift.setBounds(145, 98, 220, 20);
		endShift.setEnabled(false);
		anagraphicPanel.add(endShift);

		confirmBtn = new JButton("Conferma");
		confirmBtn.setBounds(145, 303, 109, 21);
		confirmBtn.setEnabled(false);
		anagraphicPanel.add(confirmBtn);

		cancelBtn = new JButton("Annulla");
		cancelBtn.setBounds(254, 303, 111, 21);
		cancelBtn.setEnabled(false);
		anagraphicPanel.add(cancelBtn);

		group = new ButtonGroup();
		group.add(rdbtnWork);
		group.add(rdbtnHolidays);
	}

	private void events() {

		addShift.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// sblocca il pannello inferiore e tutti i suoi componenti
				icou = IsCreatingOrUpdating.CREATE;
				enablePanel(anagraphicPanel);
				disableEnableUpperCommands(false);
			}
		});

		// cancella
		deleteShift.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int answer = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler cancellare questo turno?",
							"Conferma", JOptionPane.YES_NO_OPTION);
					
					if (answer == JOptionPane.YES_OPTION) {
						try {
							shiftEmployee = getSelectedShiftFromTable();
							shiftDeleteController.delete(shiftEmployee);
							shifts = getAllShifts();
							populateShiftsTable(shifts);
						} 
						catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Seleziona un turno da cancellare");
				}
			}
		});
		// modifica
		editShift.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					icou = IsCreatingOrUpdating.UPDATE;
					enablePanel(anagraphicPanel);
					disableEnableUpperCommands(false);
					shiftEmployee = getSelectedShiftFromTable();
					fillControls(shiftEmployee);
				} 
				else {
					JOptionPane.showMessageDialog(ShiftForm.this, "Seleziona un turno");
				}
			}
		});

		// cerca
		searchShiftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					shifts = shiftGetController.getSearchedShifts(searchShift.getText());					
					populateShiftsTable(shifts);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		cancelShiftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchShift.setText("");
				shifts = getAllShifts();
				populateShiftsTable(shifts);
			}
		});

		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// conferma
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (sendObjectToController(shiftEmployee)) {
						disablePanel(anagraphicPanel);
						cleanPanelData(anagraphicPanel);
						disableEnableUpperCommands(true);
						shifts = getAllShifts();
						populateShiftsTable(shifts);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		// annulla
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disablePanel(anagraphicPanel);
				cleanPanelData(anagraphicPanel);
				disableEnableUpperCommands(true);
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

	/*private void loadShifts(Map<Integer, ShiftEmployee> shifts) {
		//shifts = getAllShifts(); // metodo per popolare la mappa
		populateShiftsTable(shifts); // metodo per popolare la tabella dalla mappa
	}*/

	private void populateShiftsTable(Map<Integer, ShiftEmployee> shifts) {
		String[] columnNames = { "ID", "Inizio", "Fine", "Tipo", "Operatore" };

		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Impedisce la modifica di qualsiasi cella
			}
		};

		for (Map.Entry<Integer, ShiftEmployee> entry : shifts.entrySet()) {
			ShiftEmployee shiftEmployee = entry.getValue();

			Object[] rowData = { entry.getKey(), shiftEmployee.getShift().getStart(), shiftEmployee.getShift().getEnd(),
					shiftEmployee.getShift().getType(), shiftEmployee.getEmployee().toString() };
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}

	private Map<Integer, ShiftEmployee> getAllShifts() {
		try {
			shifts = shiftGetController.getShifts();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		return shifts;
	}

	private void loadEmployees() {
		List<Employee> employeesList = EmployeeDAO.filterEmployeesByRole(Roles.PERSONNEL); // ----> da sostituire con

		for (Employee employee : employeesList) {
			// CustomerWrapper cw = new CustomerWrapper(customer);
			cBoxEmployee.addItem(employee);
			employees.put(employee.getId(), employee);
		}
		cBoxEmployee.setSelectedItem(null);
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
			} else if (component instanceof JTextField) {
				((JTextField) component).setText("");
			} else if (component instanceof JComboBox) {
				((JComboBox<?>) component).setSelectedIndex(-1);
			}

			else if (component instanceof JList) {
				JList<?> list = (JList<?>) component;
				ListModel<?> model = list.getModel();
				if (model instanceof DefaultListModel<?>) {
					((DefaultListModel<?>) model).clear();
				}
			} else if (component instanceof JScrollPane) {
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
		searchShift.setEnabled(flag);
		addShift.setEnabled(flag);
		editShift.setEnabled(flag);
		deleteShift.setEnabled(flag);
		btnHystorical.setEnabled(flag);
		searchShiftButton.setEnabled(flag);
		cancelShiftButton.setEnabled(flag);
	}

	private ShiftEmployee getSelectedShiftFromTable() {

		ShiftEmployee shift = new ShiftEmployee();

		try {
			shift = shiftGetController.getShift((int) table.getValueAt(table.getSelectedRow(), 0));
			return shift;
		}

		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		return shift;
	}

	// funzione che si attiva quando si preme sul tasto modifica
	private void fillControls(ShiftEmployee shiftEmployee) {

		Employee employee = employees.get(shiftEmployee.getEmployee().getId());
		cBoxEmployee.setSelectedItem(employee);
		LocalDateTime startDate = shiftEmployee.getShift().getStart();
		LocalDateTime endDate = shiftEmployee.getShift().getEnd();
		startShift.setDateTimePermissive(startDate);
		endShift.setDateTimePermissive(endDate);
		txtNotes.setText(shiftEmployee.getShift().getNotes());
		ShiftType type = shiftEmployee.getShift().getType();

		if (type == ShiftType.WORK) {
			rdbtnWork.setSelected(true);
		} else {
			rdbtnHolidays.setSelected(true);
		}
	}

	// crea shift
	private void createShift() throws Exception {
		shiftCreateController.add(returnNewShiftEmployee());
	}

	// modifica shift
	private void updateShift(ShiftEmployee selectedShift) throws Exception {
		int selectedEmployeeShiftId = selectedShift.getId(); // valori recuperati dalla tabella
		int selectedShiftId = selectedShift.getShift().getId(); // valori recuperati dalla tabella

		ShiftEmployee se = returnNewShiftEmployee(); // oggetto con nuovi valori
		se.getShift().setId(selectedShiftId); // settare id di shift
		se.setId(selectedEmployeeShiftId);

		shiftUpdateController.update(se);
	}

	private ShiftEmployee returnNewShiftEmployee() throws Exception {
		Employee employee = (Employee) cBoxEmployee.getSelectedItem();

		LocalDateTime sdt;
		LocalDateTime edt;
		if (startShift.getDateTimePermissive() != null || endShift.getDateTimePermissive() != null) {
			sdt = startShift.getDateTimePermissive();
			edt = endShift.getDateTimePermissive();
		}

		else {
			throw new Exception("Controlla correttamente data e ora");
		}

		Shift shift = new Shift(sdt, edt, shiftType, txtNotes.getText());
		ShiftEmployee se = new ShiftEmployee(shift, employee);

		return se;
	}

	private boolean sendObjectToController(ShiftEmployee se) throws Exception {

		if (icou == IsCreatingOrUpdating.CREATE) {
			createShift();
		} else {
			updateShift(se);
		}
		return true;
	}

}
