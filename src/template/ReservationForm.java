package template;

import com.centro.estetico.bitcamp.*;
import com.centro.estetico.enums.IsCreatingOrUpdating;
import com.centro.estetico.bitcamp.repository.*;
import com.centro.estetico.useCases.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JCalendar;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.util.HashMap;
import com.centro.estetico.bitcamp.controller.*;
import java.util.Calendar;
public class ReservationForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField searchReservation;
	private JTable table;
	private JList<LocalTime> timeList;
	private HashMap<Integer, Customer> customers;
	private JScrollPane scrollPane;
	private List<LocalTime> times;
	private Map<LocalTime, List<Employee>> freeEmployees;
	private Map<Integer, Reservation> reservations;
	private IsCreatingOrUpdating icou;

	/**
	 * Create the panel.
	 */

	public ReservationForm() {
		customers = new HashMap<Integer, Customer>();
		intialize();		
		reservations = getReservations(); //metodo per popolare la mappa
		populateReservationsTable(reservations); //metodo per popolare la tabella dalla mappa
		times = new ArrayList<LocalTime>();
		freeEmployees = new HashMap<LocalTime, List<Employee>>();		
	}

	public void intialize() {
		setLayout(null);
		setSize(1257, 768);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 52, 1237, 347);
		add(containerPanel);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(10, 8, 1217, 329);
		containerPanel.add(outputPanel);

		table = new JTable();
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setBounds(0, 38, 1217, 291);
		outputPanel.add(tablePane);

		JButton addReservation = new JButton();
		addReservation.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/Insert.png")));
		addReservation.setBounds(943, 7, 24, 21);
		outputPanel.add(addReservation);

		JButton editReservation = new JButton();

		editReservation.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/disable.png")));
		editReservation.setBounds(977, 7, 24, 21);
		outputPanel.add(editReservation);

		JButton btnHystorical = new JButton("");
		btnHystorical.setIcon(new ImageIcon(""));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);

		JLabel lblGestioneTurni = new JLabel("GESTIONE APPUNTAMENTI");
		lblGestioneTurni.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblGestioneTurni.setBounds(413, 11, 243, 32);
		add(lblGestioneTurni);

		JButton dateButtonVisible = new JButton("V");

		JCalendar calendar = new JCalendar();
		calendar.setBounds(45, 548, 257, 183);
		calendar.setVisible(false);
		add(calendar);

		searchReservation = new JTextField();
		searchReservation.setBounds(10, 19, 168, 24);
		add(searchReservation);
		searchReservation.setColumns(10);
		searchReservation.setBackground(UIManager.getColor("CheckBox.background"));


		JPanel anagraphicPanel = new JPanel();
		anagraphicPanel.setLayout(null);
		anagraphicPanel.setBounds(20, 409, 817, 349);
		add(anagraphicPanel);

		JButton searchCustomers = new JButton("...");
		searchCustomers.setBounds(208, 22, 53, 21);
		anagraphicPanel.add(searchCustomers);
		searchCustomers.setEnabled(false);

		JLabel lblInizioTurno = new JLabel("Trattamento");
		lblInizioTurno.setEnabled(false);
		lblInizioTurno.setBounds(22, 61, 85, 17);
		anagraphicPanel.add(lblInizioTurno);
		lblInizioTurno.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));

		JComboBox treatmentsComboBox = new JComboBox();
		treatmentsComboBox.setBounds(111, 61, 87, 21);
		anagraphicPanel.add(treatmentsComboBox);
		treatmentsComboBox.setEnabled(false);
		populateTreatmentsList(treatmentsComboBox);
		// non selezionare nessun item dalla combobox
		treatmentsComboBox.setSelectedItem(null);

		JLabel lblData = new JLabel("Data");
		lblData.setEnabled(false);
		lblData.setBounds(22, 105, 95, 14);
		anagraphicPanel.add(lblData);
		lblData.setFont(new Font("Dialog", Font.PLAIN, 14));

		JButton getVisibleInvisibleCalendarButton = new JButton("V");
		getVisibleInvisibleCalendarButton.setBounds(111, 98, 87, 21);
		anagraphicPanel.add(getVisibleInvisibleCalendarButton);
		getVisibleInvisibleCalendarButton.setEnabled(false);

		JLabel lblTime = new JLabel("Orari");
		lblTime.setEnabled(false);
		lblTime.setBounds(304, 23, 85, 14);
		anagraphicPanel.add(lblTime);
		lblTime.setFont(new Font("Dialog", Font.PLAIN, 14));
		// timeList.setBounds(319, 455, 95, 278);
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(305, 45, 95, 278);
		anagraphicPanel.add(scrollPane);

		timeList = new JList<>();
		scrollPane.setViewportView(timeList);
		timeList.setEnabled(false);

		JLabel lblAvailableBeauticians = new JLabel("Estetisti disponibili:");
		lblAvailableBeauticians.setEnabled(false);
		lblAvailableBeauticians.setBounds(437, 23, 145, 14);
		anagraphicPanel.add(lblAvailableBeauticians);
		lblAvailableBeauticians.setFont(new Font("Dialog", Font.PLAIN, 14));

		JButton confirmBtn = new JButton("Conferma");
		confirmBtn.setEnabled(false);
		confirmBtn.setBounds(613, 302, 95, 21);
		anagraphicPanel.add(confirmBtn);

		JButton btnCancel = new JButton("Annulla");
		btnCancel.setEnabled(false);
		btnCancel.setBounds(708, 302, 85, 21);
		anagraphicPanel.add(btnCancel);

		JLabel lblCustomers = new JLabel("Cliente");
		lblCustomers.setEnabled(false);
		lblCustomers.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblCustomers.setBounds(22, 23, 85, 14);
		anagraphicPanel.add(lblCustomers);

		JList<Employee> beauticiansList = new JList<>();
		beauticiansList.setBounds(437, 46, 122, 277);
		anagraphicPanel.add(beauticiansList);
		beauticiansList.setEnabled(false);

		JComboBox customersComboBox = new JComboBox();
		customersComboBox.setEnabled(false);
		customersComboBox.setBounds(113, 22, 85, 21);
		anagraphicPanel.add(customersComboBox);

		populateCustomersComboBox(customersComboBox);
		// non selezionare alcun cliente;
		customersComboBox.setSelectedItem(null);
		
		JButton searchReservationButton = new JButton("Cerca");
		searchReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchReservation();
			}
		});
		searchReservationButton.setBounds(188, 20, 70, 22);
		add(searchReservationButton);
		
		JButton cancelReservationButton = new JButton("Annulla");
		cancelReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchReservation.setText("");
				reservations = getReservations();
				populateReservationsTable(reservations);
			}
		});
		cancelReservationButton.setBounds(268, 20, 70, 22);
		add(cancelReservationButton);

		// evento quando l'utente cambia la data dal calendario
		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if (treatmentsComboBox.getSelectedItem() != null) {
					/*
					 * Treatment treatment = (Treatment)treatmentsComboBox.getSelectedItem();
					 * getReservationTimeInterval(treatment);
					 */
					getFreeBeauticians(calendar, treatmentsComboBox, beauticiansList);
					LocalTime selectedTime = timeList.getSelectedValue();
					if (selectedTime != null) {
						populateFreeEmployeesList(freeEmployees, beauticiansList);
					}
				} else {
					JOptionPane.showMessageDialog(ReservationForm.this, "Seleziona un trattamento");
				}
			}
		});

		// rendi visibile o invisibile il calendario
		getVisibleInvisibleCalendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getVisibleInvisibleCalendar(calendar);
			}
		});

		// selezione item dai trattamenti
		treatmentsComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();
				// lista intera degli orari
				getReservationTimeInterval(treatment);
				getFreeBeauticians(calendar, treatmentsComboBox, beauticiansList);

			}
		});

		// crea appuntamento
		addReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				icou = IsCreatingOrUpdating.CREATE;
				enablePanel(anagraphicPanel);
				enablePanel(calendar);
				getVisibleInvisibleCalendar(calendar);
				
				disableUpperPanel(addReservation, editReservation, searchReservation);
				// disablePreviousDays(calendar);
			}
		});

		// modifica appuntamento
		editReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					icou = IsCreatingOrUpdating.UPDATE;
					enablePanel(anagraphicPanel);
					enablePanel(calendar);
					getVisibleInvisibleCalendar(calendar);
					disableUpperPanel(addReservation, editReservation, searchReservation);
					fillControlsFromMap(customersComboBox, treatmentsComboBox, calendar);
				}
				else {
					JOptionPane.showMessageDialog(ReservationForm.this, "Seleziona un appuntamento");
				}
				
			}
		});

		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// pulsante conferma
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOReservation daoReservation = new DAOReservation();				
				ReservationController reservationController = new ReservationController(daoReservation);
				
				try {
					
					if(icou == IsCreatingOrUpdating.CREATE) {
						reservationController.sendDataToDB(customersComboBox, treatmentsComboBox,
							    calendar, beauticiansList, timeList, new Reservation(),icou);
					}
					else {
						Reservation reservation = getSelectedReservationFromTable();
						reservationController.sendDataToDB(customersComboBox, treatmentsComboBox,
							    calendar, beauticiansList, timeList, reservation, icou);
					}
					
					// pulire i dati delle textbox,combobox
					cleanPanelData(anagraphicPanel);
					// disabilitare componenti
					disableComponents(anagraphicPanel);
					// disabilita calenario
					disableComponents(calendar);
					// rendi invisbile il calendario
					getVisibleInvisibleCalendar(calendar);
					enableUpperPanel(addReservation, editReservation, searchReservation);
					//ripopolo il dizionario
					reservations = getReservations();
					//ripopolo la tabella dai valori che ho nel dizionario					
					populateReservationsTable(reservations);
				}

				catch (Exception ex) {
					JOptionPane.showMessageDialog(ReservationForm.this, ex.getMessage());
				}
			}
		});

		// pulsante annulla
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanPanelData(anagraphicPanel);
				disableComponents(anagraphicPanel);
				disableComponents(calendar);
				enableUpperPanel(addReservation, editReservation, searchReservation);
				getVisibleInvisibleCalendar(calendar);
			}
		});

		// searchCustomer
		searchCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(ReservationForm.this);				
				SearchCustomerForm scf = new SearchCustomerForm(parentFrame, customers);
				scf.setVisible(true);
				/*----------------------------------------------------------------------*/
				Customer selectedCustomer = scf.getSelectedCustomer();
				if (selectedCustomer != null) {
					customersComboBox.setSelectedItem(selectedCustomer);
				}
			}
		});

		// selezione orario da lista
		timeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					LocalTime selectedTime = timeList.getSelectedValue();
					populateFreeEmployeesList(freeEmployees, beauticiansList);
				}
			}
		});
	}
	
	private Map<Integer,Reservation> getReservations(){
		DAOReservation daoReservation = new DAOReservation();
		reservations = daoReservation.getAll();
		return reservations;
	}

	private void populateReservationsTable(Map<Integer, Reservation>reservations) {		

		String[] columnNames = { "ID", "Cliente", "Data e Ora", "Operatore", "Servizio", "Durata" };

		// Creazione di un modello di tabella personalizzato
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Impedisce la modifica di qualsiasi cella
			}
		};

		for (Map.Entry<Integer, Reservation> entry : reservations.entrySet()) {
			Reservation reservation = entry.getValue();

			Duration duration = reservation.getTreatment().getDuration();
			long hours = duration.toHours();
			long minutes = duration.toMinutes() % 60;
			String formattedDuration = String.format("%dh %02dm", hours, minutes);

			Object[] rowData = { reservation.getId(), reservation.getCustomer(),
					reservation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
					reservation.getEmployee(), reservation.getTreatment().getType(), formattedDuration };
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}

	private void getVisibleInvisibleCalendar(JCalendar calendar) {
		// JOptionPane.showMessageDialog(ReservationForm.this ,calendar.isVisible());
		if (calendar.isVisible()) {
			calendar.setVisible(false);
		} else {
			calendar.setVisible(true);
		}
	}

	private void disablePreviousDays(JCalendar calendar) {
		Date today = new Date();
		calendar.setSelectableDateRange(today, null);
	}

	// prova
	private void populateTreatmentsList(JComboBox treatmentsComboBox) {
		DAOTreatment daoTreatment = new DAOTreatment();
		Map<Integer, Treatment> treatments = daoTreatment.getAll();

		for (Map.Entry<Integer, Treatment> entry : treatments.entrySet()) {
			treatmentsComboBox.addItem(entry.getValue());
		}

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

	private void disableComponents(Component component) {
		component.setEnabled(false);
		if (component instanceof Container) {
			if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component view = scrollPane.getViewport().getView();
				if (view != null) {
					disableComponents(view);
				}
			}

			else {
				for (Component child : ((Container) component).getComponents()) {
					disableComponents(child);
				}
			}
		}
	}

	/*
	 * private void disableJCalendar(JCalendar calendar) {
	 * calendar.setEnabled(false); // Disabilita il componente principale for
	 * (Component comp : calendar.getComponents()) {
	 * //JOptionPane.showMessageDialog(ReservationForm.this, comp);
	 * comp.setEnabled(false); } }
	 */

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
		}

		catch (Exception ex) {
			return;
		}
	}

	private void getReservationTimeInterval(Treatment treatment) {
		try {
			times = treatment.getTreatmentTime();

			DefaultListModel<LocalTime> model = new DefaultListModel<>();

			for (LocalTime time : times) {
				model.addElement(time);
			}
			timeList.setModel(model);
		} catch (Exception ex) {
			return;
		}
	}

	// metodo per popolare la combobox dei clienti
	private void populateCustomersComboBox(JComboBox customersComboBox) {		
		DAOCustomer daoCustomer = new DAOCustomer();
		customers = daoCustomer.getAll();		

		for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
			customersComboBox.addItem(entry.getValue());
		}
	}

	private void getFreeBeauticians(JCalendar calendar, JComboBox treatmentsComboBox, JList<Employee> beauticiansList) {
		// recupero la data selezionata
		LocalDate selectedDate = calendar.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (treatmentsComboBox.getSelectedItem() != null && calendar.getDate() != null && times != null) {
			DAOReservation daoReservation = new DAOReservation();
			DAOEmployee daoEmployee = new DAOEmployee();
			GetReservationUseCase gruc = new GetReservationUseCase(daoReservation, daoEmployee, selectedDate, times,
					(Treatment) treatmentsComboBox.getSelectedItem());
			freeEmployees = gruc.Execute();
			
		

		}
	}

	private void populateFreeEmployeesList(Map<LocalTime, List<Employee>> freeEmployees,
		JList<Employee> beauticiansList) {
		DefaultListModel<Employee> listModel = new DefaultListModel<>();
		LocalTime time = (LocalTime) timeList.getSelectedValue();

		if (freeEmployees != null) {
			for (Employee e : freeEmployees.get(time)) {
				listModel.addElement(e);
			}
		}

		beauticiansList.setModel(listModel);
	}
	
	private Reservation getSelectedReservationFromTable() {
		//Reservation reservation = reservations.get(table.getValueAt(table.getSelectedRow(), 0));
		DAOReservation daoReservation = new DAOReservation(); 
		Reservation reservation = daoReservation.getReservation((int)table.getValueAt(table.getSelectedRow(), 0));
		return reservation;
	}
	
	private void fillControlsFromMap(JComboBox customersComboBox, JComboBox treatmentsComboBox, JCalendar calendar) {
		Reservation reservation = getSelectedReservationFromTable();
		
		customersComboBox.setSelectedItem(reservation.getCustomer());
		treatmentsComboBox.setSelectedItem(reservation.getTreatment());
		
		int year = reservation.getDateTime().getYear();
		int month = reservation.getDateTime().getMonthValue()-1;
		int day = reservation.getDateTime().getDayOfMonth();
		
		Calendar calendarObj = Calendar.getInstance();
		calendarObj.set(year, month, day, 0, 0, 0);
		Date date = calendarObj.getTime();
		calendar.setDate(date);
	}
	
	private void disableUpperPanel(JButton addReservation, JButton editReservation, JTextField searchTextBox) {
		addReservation.setEnabled(false);
		editReservation.setEnabled(false);
		searchTextBox.setEnabled(false);
	}
	
	private void enableUpperPanel(JButton addReservation, JButton editReservation, JTextField searchTextBox) {
		addReservation.setEnabled(true);
		editReservation.setEnabled(true);
		searchTextBox.setEnabled(true);
	}
	
	private void searchReservation() {
		DAOReservation daoReservation = new DAOReservation();
		Map<Integer,Reservation>reservations = new HashMap<Integer,Reservation>();
		reservations = daoReservation.getSearchedReservation(searchReservation.getText());
		populateReservationsTable(reservations);
	}
}
