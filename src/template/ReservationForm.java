package template;

import com.centro.estetico.bitcamp.*;

import DAO.*;
import com.centro.estetico.controller.ReservationController;
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
import java.util.Calendar;

//import wrappersForDisplayMember.*;

public class ReservationForm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField searchReservation;
	private JTable table;
	private JList<LocalTime> timeList;
	// private JList<EmployeeWrapper> beauticiansList;
	private JList<Employee> beauticiansList;
	private JScrollPane scrollPane;
	private JScrollPane tablePane;
	private JPanel containerPanel;
	private JPanel outputPanel;
	private JPanel anagraphicPanel;
	private JButton addReservation;
	private JButton editReservation;
	private JButton deleteReservation;
	private JButton btnHystorical;
	private JButton searchCustomers;
	private JButton dateButtonVisible;
	private JButton confirmBtn;
	private JButton getVisibleInvisibleCalendarButton;
	private JButton btnCancel;
	private JButton searchReservationButton;
	private JButton cancelReservationButton;
	private JLabel lblManageShifts;
	private JLabel lblStartShift;
	private JLabel lblAvailableBeauticians;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblCustomers;
	private JComboBox customersComboBox;
	private JComboBox treatmentsComboBox;
	private JCalendar calendar;

	/*-----------------------------------------------------------------------*/

	private List<Customer> customers;
	private List<LocalTime> times;
	private Map<LocalTime, List<Employee>> freeEmployees;
	private Map<Integer, Reservation> reservations;
	private IsCreatingOrUpdating icou;
	// private Map<Integer, CustomerWrapper> customerMap;
	private Map<Integer, Customer> customerMap;
	// private Map<Integer, TreatmentWrapper> treatmentMap;
	private Map<Integer, Treatment> treatmentMap;
	
	private ActionListener treatmentComboBoxListener;
	/**
	 * Create the panel.
	 */

	public ReservationForm() {
		intialize();
		events();
		loadReservations();
		populateCustomersComboBox(customersComboBox);		
		populateTreatmentsComboBox(treatmentsComboBox);		
	}

	public void intialize() {
		customerMap = new HashMap<>();
		treatmentMap = new HashMap<>();
		customers = new ArrayList<Customer>();
		times = new ArrayList<LocalTime>();
		freeEmployees = new HashMap<LocalTime, List<Employee>>();
		reservations = new HashMap<Integer, Reservation>();

		setLayout(null);
		setSize(1257, 768);

		containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 52, 1237, 347);
		add(containerPanel);

		outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(10, 8, 1217, 329);
		containerPanel.add(outputPanel);

		table = new JTable();
		tablePane = new JScrollPane(table);
		tablePane.setBounds(0, 38, 1217, 291);
		outputPanel.add(tablePane);

		addReservation = new JButton();
		addReservation.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/Insert.png")));
		addReservation.setBounds(943, 7, 24, 21);
		outputPanel.add(addReservation);

		editReservation = new JButton();

		editReservation.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/disable.png")));
		editReservation.setBounds(977, 7, 24, 21);
		outputPanel.add(editReservation);

		deleteReservation = new JButton("");
		deleteReservation.setBounds(1011, 7, 24, 21);
		deleteReservation.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/delete.png")));
		outputPanel.add(deleteReservation);

		btnHystorical = new JButton("");
		btnHystorical.setIcon(new ImageIcon(""));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);

		lblManageShifts = new JLabel("GESTIONE APPUNTAMENTI");
		lblManageShifts.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblManageShifts.setBounds(413, 11, 243, 32);
		add(lblManageShifts);

		dateButtonVisible = new JButton("V");

		calendar = new JCalendar();
		calendar.setBounds(45, 548, 257, 183);
		calendar.setVisible(false);
		add(calendar);

		searchReservation = new JTextField();
		searchReservation.setBounds(10, 19, 168, 24);
		add(searchReservation);
		searchReservation.setColumns(10);
		searchReservation.setBackground(UIManager.getColor("CheckBox.background"));

		anagraphicPanel = new JPanel();
		anagraphicPanel.setLayout(null);
		anagraphicPanel.setBounds(20, 409, 817, 349);
		add(anagraphicPanel);

		searchCustomers = new JButton("...");
		searchCustomers.setBounds(208, 22, 53, 21);
		anagraphicPanel.add(searchCustomers);
		searchCustomers.setEnabled(false);

		lblStartShift = new JLabel("Trattamento");
		lblStartShift.setEnabled(false);
		lblStartShift.setBounds(22, 61, 85, 17);
		lblStartShift.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		anagraphicPanel.add(lblStartShift);

		treatmentsComboBox = new JComboBox();
		treatmentsComboBox.setBounds(111, 61, 87, 21);
		anagraphicPanel.add(treatmentsComboBox);
		treatmentsComboBox.setEnabled(false);
		// non selezionare nessun item dalla combobox
		//treatmentsComboBox.setSelectedItem(null);

		lblDate = new JLabel("Data");
		lblDate.setEnabled(false);
		lblDate.setBounds(22, 105, 95, 14);
		anagraphicPanel.add(lblDate);
		lblDate.setFont(new Font("Dialog", Font.PLAIN, 14));

		getVisibleInvisibleCalendarButton = new JButton("V");
		getVisibleInvisibleCalendarButton.setBounds(111, 98, 87, 21);
		anagraphicPanel.add(getVisibleInvisibleCalendarButton);
		getVisibleInvisibleCalendarButton.setEnabled(false);

		lblTime = new JLabel("Orari");
		lblTime.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblTime.setEnabled(false);
		lblTime.setBounds(304, 23, 85, 14);
		anagraphicPanel.add(lblTime);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(305, 45, 95, 278);
		anagraphicPanel.add(scrollPane);

		timeList = new JList<>();
		scrollPane.setViewportView(timeList);
		timeList.setEnabled(false);

		lblAvailableBeauticians = new JLabel("Estetisti disponibili:");
		lblAvailableBeauticians.setEnabled(false);
		lblAvailableBeauticians.setBounds(437, 23, 145, 14);
		anagraphicPanel.add(lblAvailableBeauticians);
		lblAvailableBeauticians.setFont(new Font("Dialog", Font.PLAIN, 14));

		confirmBtn = new JButton("Conferma");
		confirmBtn.setEnabled(false);
		confirmBtn.setBounds(613, 302, 95, 21);
		anagraphicPanel.add(confirmBtn);

		btnCancel = new JButton("Annulla");
		btnCancel.setEnabled(false);
		btnCancel.setBounds(708, 302, 85, 21);
		anagraphicPanel.add(btnCancel);

		lblCustomers = new JLabel("Cliente");
		lblCustomers.setEnabled(false);
		lblCustomers.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblCustomers.setBounds(22, 23, 85, 14);
		anagraphicPanel.add(lblCustomers);

		beauticiansList = new JList<>();
		beauticiansList = new JList<>();
		beauticiansList.setBounds(437, 46, 122, 277);
		beauticiansList.setEnabled(false);
		anagraphicPanel.add(beauticiansList);

		customersComboBox = new JComboBox();
		customersComboBox.setEnabled(false);
		customersComboBox.setBounds(113, 22, 85, 21);
		anagraphicPanel.add(customersComboBox);

		searchReservationButton = new JButton();
		searchReservationButton.setBounds(188, 18, 27, 24);
		searchReservationButton
				.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/searchIcon.png")));
		add(searchReservationButton);

		cancelReservationButton = new JButton();
		cancelReservationButton.setBounds(225, 18, 36, 24);
		cancelReservationButton
				.setIcon(new ImageIcon(ReservationForm.class.getResource("/iconeGestionale/potrebbeServire.png")));
		add(cancelReservationButton);

	}

	private void events() {
		searchReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchReservation();
			}
		});

		cancelReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchReservation.setText("");
				reservations = getReservations();
				populateReservationsTable(reservations);
			}
		});

		// evento quando l'utente cambia la data dal calendario
		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if (treatmentsComboBox.getSelectedItem() != null) {
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

		treatmentComboBoxListener = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (treatmentsComboBox.getSelectedItem() != null) {
	                Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();
	                cleanBeauticiansList();
	                getReservationTimeInterval(treatment);	                
	                getFreeBeauticians(calendar, treatmentsComboBox, beauticiansList);
	            }
	        }
	    };

		// crea appuntamento
		addReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				icou = IsCreatingOrUpdating.CREATE;
				enablePanel(anagraphicPanel);
				enablePanel(calendar);
				getVisibleInvisibleCalendar(calendar);

				disableUpperPanel(addReservation, editReservation, deleteReservation, searchReservation,
						searchReservationButton, cancelReservationButton);
				// disablePreviousDays(calendar);
			}
		});

		// modifica appuntamento
		editReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					icou = IsCreatingOrUpdating.UPDATE;
					enablePanel(anagraphicPanel);
					enablePanel(calendar);
					getVisibleInvisibleCalendar(calendar);
					disableUpperPanel(addReservation, editReservation, deleteReservation, searchReservation,
							searchReservationButton, cancelReservationButton);
					fillControlsFromMap(customersComboBox, treatmentsComboBox, calendar);
				} else {
					JOptionPane.showMessageDialog(ReservationForm.this, "Seleziona un appuntamento");
				}

			}
		});

		// cancella appuntamento
		deleteReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int answer = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler cancellare l'appuntamento?",
							"Conferma", JOptionPane.YES_NO_OPTION);

					if (answer == JOptionPane.YES_OPTION) {
						Reservation reservation = getSelectedReservationFromTable();
						deleteReservation(reservation);
						reservations = getReservations();
						// ripopolo la tabella dai valori che ho nel dizionario
						populateReservationsTable(reservations);
					}
				} else {
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

				try {

					sendObjectToController();
					// pulire i dati delle textbox,combobox
					cleanPanelData(anagraphicPanel);
					// disabilitare componenti
					disableComponents(anagraphicPanel);
					// disabilita calenario
					disableComponents(calendar);
					// rendi invisbile il calendario
					getVisibleInvisibleCalendar(calendar);

					enableUpperPanel(addReservation, editReservation, deleteReservation, searchReservation,
							searchReservationButton, cancelReservationButton);
					// ripopolo il dizionario
					reservations = getReservations();
					// ripopolo la tabella dai valori che ho nel dizionario
					populateReservationsTable(reservations);
				}

				catch (Exception ex) {
					JOptionPane.showMessageDialog(ReservationForm.this, ex.getMessage());
				}
			}
		});

		/*
		 * LocalDate localDate = date.toInstant() .atZone(ZoneId.systemDefault())
		 * .toLocalDate();
		 */

		// pulsante annulla
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanPanelData(anagraphicPanel);
				disableComponents(anagraphicPanel);
				disableComponents(calendar);
				enableUpperPanel(addReservation, editReservation, deleteReservation, searchReservation,
						searchReservationButton, cancelReservationButton);
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

	private void loadReservations() {
		reservations = getReservations(); // metodo per popolare la mappa
		populateReservationsTable(reservations); // metodo per popolare la tabella dalla mappa
	}

	private Map<Integer, Reservation> getReservations() {
		DAOReservation daoReservation = new DAOReservation();
		GetReservationUseCase gruc = new GetReservationUseCase(daoReservation);
		ReservationController rc = new ReservationController(gruc);
		try {
			reservations = rc.getReservations();
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}		
		return reservations;
	}

	private void populateReservationsTable(Map<Integer, Reservation> reservations) {

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

			// CustomerWrapper cw = new CustomerWrapper(reservation.getCustomer());

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
			} else if (component instanceof JTextField) {
				((JTextField) component).setText("");
			} else if (component instanceof JComboBox) {
				((JComboBox<?>) component).setSelectedIndex(-1);
			} else if (component instanceof JList) {
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
		}

		catch (Exception ex) {
			return;
		}
	}
	
	private void cleanBeauticiansList() {		
		beauticiansList.setModel(new DefaultListModel<Employee>());
	}

	private void getReservationTimeInterval(Treatment treatment) {
		// USE CASE
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
		// USE CASE
		customers = CustomerDAO.getAllCustomers();

		for (Customer customer : customers) {
			// CustomerWrapper cw = new CustomerWrapper(customer);
			customersComboBox.addItem(customer);
			customerMap.put(customer.getId(), customer);
		}
		customersComboBox.setSelectedItem(null);
	}

	// prova
	private void populateTreatmentsComboBox(JComboBox treatmentsComboBox) {
		// USE CASE
		treatmentsComboBox.removeActionListener(treatmentComboBoxListener);

	    List<Treatment> treatments = TreatmentDAO.getAllTreatments();
	    for (Treatment t : treatments) {
	        treatmentsComboBox.addItem(t);
	        treatmentMap.put(t.getId(), t);
	    }

	    // Imposta l'elemento selezionato a null
	    treatmentsComboBox.setSelectedItem(null);

	    // Riaggiungi l'ActionListener solo dopo il popolamento
	    treatmentsComboBox.addActionListener(treatmentComboBoxListener);
	}

	private void getFreeBeauticians(JCalendar calendar, JComboBox treatmentsComboBox, JList<Employee> beauticiansList) {
		// recupero la data selezionata
		LocalDate selectedDate = calendar.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (treatmentsComboBox.getSelectedItem() != null && calendar.getDate() != null && times != null) {
			DAOReservation daoReservation = new DAOReservation();
			DAOShift daoShift = new DAOShift();
			
			Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();			
			GetReservationUseCase gruc = new GetReservationUseCase(daoReservation, daoShift);
			ReservationController reservationController = new ReservationController(gruc);
			try {
				freeEmployees = reservationController.getEmployees(selectedDate, times, treatment);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			//freeEmployees = gruc.execute();
		}
	}

	private void populateFreeEmployeesList(Map<LocalTime, List<Employee>> freeEmployees,
			JList<Employee> beauticiansList) {

		DefaultListModel<Employee> listModel = new DefaultListModel<>();
		LocalTime time = (LocalTime) timeList.getSelectedValue();

		if (freeEmployees != null) {
			for (Employee e : freeEmployees.get(time)) {
				// Employee ew = new Employee(e);
				listModel.addElement(e);
			}
		}

		beauticiansList.setModel(listModel);
	}

	private Reservation getSelectedReservationFromTable() {
		DAOReservation daoReservation = new DAOReservation();
		DAOShift daoShift = new DAOShift();
		GetReservationUseCase getReservationUseCase = new GetReservationUseCase(daoReservation, daoShift);
		ReservationController reservationController = new ReservationController(getReservationUseCase);
		Reservation reservation = new Reservation();
		try {
			reservation = reservationController.getReservation((int) table.getValueAt(table.getSelectedRow(), 0));
			return reservation;
		}
		
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		return reservation;
	}

	private void fillControlsFromMap(JComboBox customersComboBox, JComboBox treatmentsComboBox, JCalendar calendar) {
		Reservation reservation = getSelectedReservationFromTable();

		Customer customer = customerMap.get(reservation.getCustomer().getId());
		customersComboBox.setSelectedItem(customer);

		Treatment treatment = treatmentMap.get(reservation.getTreatment().getId());
		treatmentsComboBox.setSelectedItem(treatment);

		int year = reservation.getDateTime().getYear();
		int month = reservation.getDateTime().getMonthValue() - 1;
		int day = reservation.getDateTime().getDayOfMonth();

		Calendar calendarObj = Calendar.getInstance();
		calendarObj.set(year, month, day, 0, 0, 0);
		Date date = calendarObj.getTime();
		calendar.setDate(date);
	}

	private void disableUpperPanel(JButton addReservation, JButton editReservation, JButton deleteReservation,
			JTextField searchTextBox, JButton searchReservationButton, JButton cancelReservationButton) {
		addReservation.setEnabled(false);
		editReservation.setEnabled(false);
		deleteReservation.setEnabled(false);
		searchTextBox.setEnabled(false);
		searchReservationButton.setEnabled(false);
		cancelReservationButton.setEnabled(false);
	}

	private void enableUpperPanel(JButton addReservation, JButton editReservation, JButton deleteReservation,
			JTextField searchTextBox, JButton searchReservationButton, JButton cancelReservationButton) {
		addReservation.setEnabled(true);
		editReservation.setEnabled(true);
		deleteReservation.setEnabled(true);
		searchTextBox.setEnabled(true);
		searchReservationButton.setEnabled(true);
		cancelReservationButton.setEnabled(true);

	}

	private void searchReservation() {	
		DAOReservation daoReservation = new DAOReservation();
		GetReservationUseCase getReservationUseCase = new GetReservationUseCase(daoReservation);
		ReservationController reservationController = new ReservationController(getReservationUseCase);
		
		Map<Integer, Reservation> reservations = new HashMap<Integer, Reservation>();
		try {
			reservations = reservationController.getSearchedReservations(searchReservation.getText());
			populateReservationsTable(reservations);
		}
		 
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void deleteReservation(Reservation reservation) {
		// USE CASE
		DAOReservation daoReservation = new DAOReservation();
		DeleteReservationUseCase deleteReservationUseCase = new DeleteReservationUseCase(daoReservation);
		ReservationController reservationDeleteController = new ReservationController(deleteReservationUseCase);
		try {
			reservationDeleteController.delete(reservation);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void sendObjectToController() {
		DAOReservation daoReservation = new DAOReservation();
		CreateReservationUseCase createReservationUseCase = new CreateReservationUseCase(daoReservation);
		UpdateReservationUseCase updateReservationUseCase = new UpdateReservationUseCase(daoReservation); 
		ReservationController reservationCreateController = new ReservationController(createReservationUseCase);
		ReservationController updateCreateController = new ReservationController(updateReservationUseCase);

		Customer customer = (Customer) customersComboBox.getSelectedItem();
		Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();
		LocalDate localDate = calendar.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		Employee employee = (Employee) beauticiansList.getSelectedValue();
		LocalTime localTime = (LocalTime) timeList.getSelectedValue();
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		
		
		try {
			if (icou == IsCreatingOrUpdating.CREATE) {
				reservationCreateController.add(new Reservation(customer, treatment, employee, localDateTime));
			} 
			else {
				Reservation reservation = getSelectedReservationFromTable();
				int id = reservation.getId();
				reservation = new  Reservation(customer, treatment, employee, localDateTime);
				reservation.setId(id);
				if(employee == null && treatment.getId() == reservation.getTreatment().getId()) {
					employee = reservation.getEmployee();
					localTime = reservation.getDateTime().toLocalTime();
				}
				
				updateCreateController.update(reservation);
			}

		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}
