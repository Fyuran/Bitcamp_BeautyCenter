package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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

import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.SubscriptionDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.SubPeriod;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.models.VAT;

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
	private DateTimeFormatter format;

	// Campi per l'abbonamento
	private JComboBox<SubPeriod> cBoxSubPeriod; // Per il periodo dell'abbonamento
	private JTextField txfStartDate; // Data di inizio
	private JTextField txfDiscount; // Sconto

	private JTable table;
	private DefaultTableModel tableModel;

	public gestioneClienti() {
		setLayout(null);
		setSize(1024, 768);
		setName("Clienti");
		format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
				"Comune di Nascita", "Codice fiscale", "Codice Ricezione", "Punti Fedeltà", "Abbonamento", "Note" };
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

		
		MaskFormatter dateMaskBirthDay;
		try {// per settare il formato data
			dateMaskBirthDay = new MaskFormatter("##/##/####");
			txfBirthdate = new JFormattedTextField(dateMaskBirthDay);
			txfBirthdate.setColumns(10);			
			txfBirthdate.setBounds(570, 400, 120, 25);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		MaskFormatter dateMaskStattDate;
		try {// per settare il formato data
			dateMaskStattDate = new MaskFormatter("##/##/####");
			txfStartDate = new JFormattedTextField(dateMaskStattDate);
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
		btnToggleStatus.setBounds(740, 550, 120, 25);
		add(btnToggleStatus);

		// Carica i dati nella tabella
		loadCustomerData();

		// Eventi dei pulsanti
		btnSave.addActionListener(e->addCustomer());

		btnDelete.addActionListener(e->deleteCustomer());

		btnToggleStatus.addActionListener(e->toggleCustomerStatus());
	}

	// Metodo per caricare i dati dalla DAO alla tabella
	private void loadCustomerData() {
		clearTable();
//		clearFields();
		List<Customer> customers = CustomerDAO.getAllCustomers();
		if (customers.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano impiegati presenti", "" });
			return;
		}
		for (Customer c : customers) {
			if (c.isEnabled()) {
				tableModel.addRow(new String[] {
						String.valueOf(c.getId()),
						c.getName(),
						c.getSurname(),
						c.getPhone(),
						c.getMail(),
						c.getBoD().format(format),
						c.isFemale()?"Donna":"Uomo",
						c.getBirthplace(),
						c.getEU_TIN().getValue(),
						c.getRecipientCode(),
						String.valueOf(c.getLoyaltyPoints()),
						c.getSubscription()==null?"Nessun abbonamento":String.valueOf(c.getSubscription().getId()),
						c.getNotes()
						
						});
//				{"ID", "Nome", "Cognome", "Telefono", "Email", "Data di Nascita", "Sesso",
//				"Comune di Nascita", "P_IVA", "Codice Ricezione", "Punti Fedeltà", "Abbonamento", "Note" };
			}
		}

	}

	// Metodo per salvare o aggiornare il cliente
	private void addCustomer() {
		// Recupero dei dati dall'interfaccia
		
		String name = txfName.getText();
		String surname = txfSurname.getText();
		
		//Subscription sub=Subscription
		
		String piva = txfPIVA.getText();
		String recipientCode = txfRecipientCode.getText();
		int loyaltyPoints = 0;
		
		//Creazione subperiod
		System.out.println(cBoxSubPeriod.getSelectedItem().toString());
		SubPeriod subP=SubPeriod.toEnum(cBoxSubPeriod.getSelectedItem().toString());
		String startSubDateString=txfStartDate.getText();
		LocalDate startSubDate=LocalDate.parse(startSubDateString, format);
		BigDecimal price=new BigDecimal(10);
		VAT vat=VATDao.getVAT(1).get();
		double discount=Double.parseDouble(txfDiscount.getText());
		Subscription sub=new Subscription(subP,startSubDate,price,vat,discount,true);
		Subscription updatedSub=SubscriptionDAO.insertSubscription(sub).get();
		
		
		String username=name+surname+"123";
		String password="customerPassword123";
		String address="Via dei cojoni 123";
		String iban=null;
		String phone=txfPhone.getText();
		String mail=txfEmail.getText();
		
		UserCredentials cred=new UserCredentials(username,password,address,iban,phone,mail);
		UserCredentials updatedCred=UserCredentialsDAO.insertUserCredentials(cred).get();
		
		
		
		boolean isFemale=cBoxGenre.getSelectedItem().equals("Femmina");
		String birthdate = txfBirthdate.getText(); // Assicurati che il formato sia corretto
		LocalDate birthDay=LocalDate.parse(birthdate, format);
		String comuneNascita = txfComuneNascita.getText();
		String notes = txfNotes.getText();
		UserDetails det=new UserDetails(name,surname,isFemale,birthDay,comuneNascita,notes);
		
		Customer c=new Customer(det,updatedCred,piva,recipientCode,loyaltyPoints,updatedSub,null);

		
		CustomerDAO.insertCustomer(c); // Implementa la logica di inserimento/aggiornamento
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
			int customerId = Integer.parseInt(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
			CustomerDAO.toggleEnabledCustomer(customerId); // Implementa questa logica nel tuo DAO
			loadCustomerData();
		}
	}
	private void clearTable() {
		tableModel.getDataVector().removeAllElements();
		revalidate();
	}
}