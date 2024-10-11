package template;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Prize;
import com.centro.estetico.bitcamp.Subscription;
import com.centro.estetico.bitcamp.SubPeriod; // Assicurati di importare SubPeriod
import com.centro.estetico.bitcamp.UserDetails; // Importa UserDetails
import com.centro.estetico.bitcamp.UserCredentials; // Importa UserCredentials
import DAO.CustomerDAO;
import DAO.UserCredentialsDAO;

import javax.swing.ImageIcon;

public class gestioneClienti extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfSurname;
	private JTextField txfSearchBar;
	private JTextField txfName;
	private JTextField txfPhone;
	private JComboBox<String> cBoxGenre;
	private JTextField txfEmail;
	private JTextField txfBirthdate;
	private JTextField txfComuneNascita;
	private JTextField txfNotes;
	private JTextField txfPIVA;
	private JTextField txfRecipientCode;

	// Campi per l'abbonamento
	private JComboBox<SubPeriod> cBoxSubPeriod; // Per il periodo dell'abbonamento
	private JTextField txfStartDate; // Data di inizio
	private JTextField txfDiscount; // Sconto

	private JTable table;
	private DefaultTableModel tableModel;

	public gestioneClienti() {
		setLayout(null);
		setSize(1024, 768);

		JLabel titleTab = new JLabel("GESTIONE CLIENTI");
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 179, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		String[] columnNames = {"ID", "Nome", "Cognome", "Telefono", "Email", "Data di Nascita", "Sesso",
				"Comune di Nascita", "P_IVA", "Codice Ricezione", "Punti Fedeltà", "Abbonamento", "Note" };
		tableModel = new DefaultTableModel(columnNames, 0);

		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		JButton btnSearch = new JButton("Cerca");
		btnSearch.setBounds(300, 20, 120, 25);
		containerPanel.add(btnSearch);

		// Campi di input per inserire o modificare un cliente
		txfName = new JTextField();
		txfName.setText("Nome");
		txfName.setBounds(50, 400, 120, 25);
		add(txfName);

		txfSurname = new JTextField();
		txfSurname.setText("Cognome");
		txfSurname.setBounds(180, 400, 120, 25);
		add(txfSurname);

		txfPhone = new JTextField();
		txfPhone.setText("Phone");
		txfPhone.setBounds(310, 400, 120, 25);
		add(txfPhone);

		txfEmail = new JTextField();
		txfEmail.setText("Email");
		txfEmail.setBounds(440, 400, 120, 25);
		add(txfEmail);

		txfBirthdate = new JTextField();
		txfBirthdate.setText("BirthDate");
		txfBirthdate.setBounds(570, 400, 120, 25);
		add(txfBirthdate);

		txfComuneNascita = new JTextField();
		txfComuneNascita.setText("Comune di nascita");
		txfComuneNascita.setBounds(50, 450, 120, 25);
		add(txfComuneNascita);

		txfNotes = new JTextField();
		txfNotes.setText("Note");
		txfNotes.setBounds(180, 450, 120, 25);
		add(txfNotes);

		cBoxGenre = new JComboBox<>();
		cBoxGenre.setModel(new DefaultComboBoxModel<>(new String[] { "Maschio", "Femmina" }));
		cBoxGenre.setBounds(310, 450, 120, 25);
		add(cBoxGenre);

		txfPIVA = new JTextField();
		txfPIVA.setText("Piva");
		txfPIVA.setBounds(440, 450, 120, 25);
		add(txfPIVA);

		txfRecipientCode = new JTextField();
		txfRecipientCode.setText("RecipientCode Stringa");
		txfRecipientCode.setBounds(570, 450, 120, 25);
		add(txfRecipientCode);

		// Aggiunta dei campi per l'abbonamento
		cBoxSubPeriod = new JComboBox<>(SubPeriod.values()); // Presupponendo che SubPeriod sia un enum
		cBoxSubPeriod.setBounds(50, 500, 120, 25);
		add(cBoxSubPeriod);

		txfStartDate = new JTextField();
		txfStartDate.setText("StartDate");
		txfStartDate.setBounds(180, 500, 120, 25);
		MaskFormatter dateMask;
		try {// per settare il formato data
			dateMask = new MaskFormatter("##/##/####");
			txfStartDate = new JFormattedTextField(dateMask);
			txfStartDate.setColumns(10);
			txfStartDate.setBounds(209, 506, 220, 20);
			add(txfStartDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		txfDiscount = new JTextField();
		txfDiscount.setText("Discount");
		txfDiscount.setBounds(440, 500, 120, 25);
		add(txfDiscount);

		// Pulsante per salvare o aggiornare il cliente
		JButton btnSave = new JButton("Salva");
		btnSave.setIcon(new ImageIcon(gestioneClienti.class.getResource("/iconeGestionale/InsertUser.png")));
		btnSave.setBounds(580, 550, 120, 25);
		add(btnSave);

		// Pulsante per eliminare il cliente selezionato
		JButton btnDelete = new JButton("Elimina");
		btnDelete.setIcon(new ImageIcon(gestioneClienti.class.getResource("/iconeGestionale/deleteUser.png")));
		btnDelete.setBounds(580, 600, 120, 25);
		add(btnDelete);

		// Pulsante per abilitare/disabilitare il cliente selezionato
		JButton btnToggleStatus = new JButton("Abilita/Disabilita");
		btnToggleStatus.setIcon(new ImageIcon(gestioneClienti.class.getResource("/iconeGestionale/userDisable.png")));
		btnToggleStatus.setBounds(580, 650, 120, 25);
		add(btnToggleStatus);

		// Carica i dati nella tabella
		loadCustomerData();

		// Eventi dei pulsanti
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveOrUpdateCustomer();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCustomer();
			}
		});

		btnToggleStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleCustomerStatus();
			}
		});
	}

	// Metodo per caricare i dati dalla DAO alla tabella
	private void loadCustomerData() {
		tableModel.setRowCount(0);
		List<Object[]> customers = CustomerDAO.toTableRowAll();
		for (Object[] row : customers) {
			tableModel.addRow(row);
		}
	}

	// Metodo per salvare o aggiornare il cliente
	private void saveOrUpdateCustomer() {
		// Recupero dei dati dall'interfaccia
		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthdate = txfBirthdate.getText(); // Assicurati che il formato sia corretto
		String comuneNascita = txfComuneNascita.getText();
		String notes = txfNotes.getText();
		String piva = txfPIVA.getText();
		String recipientCode = txfRecipientCode.getText();
		int loyaltyPoints = 0;

		// Campi per l'abbonamento
		/*
		 * 
		 * SubPeriod subPeriod = (SubPeriod) cBoxSubPeriod.getSelectedItem(); LocalDate
		 * startDate = LocalDate.parse(birthdate, format);
		 */
		// VAT vat = (VAT) cBoxVAT.getSelectedItem(); // Rimuovi questa riga
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// Salva o aggiorna il cliente nel database
		UserDetails userDetails = new UserDetails(name, surname, cBoxGenre.getSelectedItem().equals("Femmina"),
				LocalDate.parse(birthdate, format), comuneNascita, notes);

		// Crea UserCredentials
		String username = "cojonedemmerda";
		String password = "";
		
		UserCredentials userCredentials = new UserCredentials(username, password, "", "Guardate, sono un iban!", "123bipbop", "stocazzo@merda.com");
		UserCredentials updatedCred=UserCredentialsDAO.insertUserCredentials(userCredentials).get();																									// parametri
																									// necessari

		Customer customer = new Customer(-1, userDetails, updatedCred, true, piva, recipientCode, loyaltyPoints,
				null, new ArrayList<>()); // Modifica a secondo della logica di inserimento/aggiornamento
		CustomerDAO.insertCustomer(customer); // Implementa la logica di inserimento/aggiornamento
		loadCustomerData();
	}

	// Metodo per eliminare un cliente
	private void deleteCustomer() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			int customerId = Integer.parseInt(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
			CustomerDAO.toggleEnabledCustomer(customerId);
			loadCustomerData();
		}
	}

	// Metodo per abilitare o disabilitare un cliente
	private void toggleCustomerStatus() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			int customerId = (int) tableModel.getValueAt(selectedRow, 0);
			CustomerDAO.toggleEnabledCustomer(customerId); // Implementa questa logica nel tuo DAO
			loadCustomerData();
		}
	}
}