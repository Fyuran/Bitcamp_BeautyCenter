package com.bitcamp.centro.estetico.gui;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;
import com.bitcamp.centro.estetico.utils.JSplitRadialButtons;
import com.bitcamp.centro.estetico.utils.inputValidator;

public class CustomerPanel extends BasePanel<Customer> {

	private static final long serialVersionUID = 1L;

	private static JSplitLbTxf txfName;
	private static JSplitLbTxf txfSurname;
	private static JSplitLbTxf txfPhone;
	private static JSplitLbTxf txfMail;
	private static JSplitLbTxf txfAddress;
	private static JSplitLbTxf txfIban;
	private static JSplitLbTxf txfBirthplace;
	private static JSplitRadialButtons genderBtns;
	private static JSplitDatePicker txfBirthday;
	private static JSplitLbTxf txfNotes;
	private static JSplitLbTxf txfPIVA;
	private static JSplitLbTxf txfRecipientCode;
	private static JSplitLbTxf txfLoyaltyPoints;

	private static int id = -1;
	private static boolean gender;
	private static String name;
	private static String surname;
	private static boolean isEnabled = false;

	private static final int _ISENABLEDCOL = customerModel.getColumnCount() - 1;

	public CustomerPanel() {

		setName("Clienti");
		setTitle("GESTIONE CLIENTI");
		setSize(1024,768);

		table.setModel(customerModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(customerModel));

		txfName = new JSplitLbTxf("Nome");
		txfSurname = new JSplitLbTxf("Cognome");
		txfMail = new JSplitLbTxf("Mail");
		txfPhone = new JSplitLbTxf("Telefono");
		txfAddress = new JSplitLbTxf("Indirizzo");
		txfBirthday = new JSplitDatePicker("Data di nascita");
		txfBirthplace = new JSplitLbTxf("Città Natale");
		genderBtns = new JSplitRadialButtons("Genere", "Maschio", "Femmina");
		txfNotes = new JSplitLbTxf("Note");
		txfIban = new JSplitLbTxf("IBAN");
		txfPIVA = new JSplitLbTxf("P.IVA"); 
		txfRecipientCode = new JSplitLbTxf("Codice Ricezione");
		txfLoyaltyPoints = new JSplitLbTxf("Punti fedeltà");

		actionsPanel.add(txfName);
		actionsPanel.add(txfSurname);
		actionsPanel.add(txfMail);
		actionsPanel.add(txfPhone);
		actionsPanel.add(txfAddress);
		actionsPanel.add(txfBirthday);
		actionsPanel.add(txfBirthplace);
		actionsPanel.add(genderBtns);
		actionsPanel.add(txfNotes);
		actionsPanel.add(txfIban);
		actionsPanel.add(txfPIVA);
		actionsPanel.add(txfRecipientCode);
		actionsPanel.add(txfLoyaltyPoints);
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	void insertElement() {
		try {
			isDataValid();
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}

		UserCredentials cred = new UserCredentials("", new char[0], txfAddress.getText(), txfIban.getText(), txfPhone.getText(), txfMail.getText());
		UserDetails det = new UserDetails(txfName.getText(), txfSurname.getText(), getGender(), txfBirthday.getDate(), txfBirthplace.getText(), txfNotes.getText());
		try {
			cred = UserCredentialsDAO.insertUserCredentials(cred).orElseThrow();
		} catch (NoSuchElementException e) {
			JOptionPane.showMessageDialog(null, "Campi già esistenti");
		}
		Customer customer = new Customer(det, cred, txfPIVA.getText(), txfRecipientCode.getText(), Integer.parseInt(txfLoyaltyPoints.getText()), null, null);
			
		lbOutput.setText("Nuovo utente creato");
		CustomerDAO.insertCustomer(customer);
		refreshTable();
	}

	@Override
	void updateElement() {
		try {
			isDataValid();
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
		if (id <= 0) {
			JOptionPane.showMessageDialog(null, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}

		Customer customer = customers.parallelStream().filter(e -> e.getId() == id).findFirst().get();
		customer.setName(txfName.getText());
		customer.setSurname(txfSurname.getText());
		customer.setBirthplace(txfBirthplace.getText());
		customer.setBoD(txfBirthday.getDate());
		customer.setNotes(txfNotes.getText());
		customer.setAddress(txfAddress.getText());
		customer.setPhone(txfPhone.getText());
		customer.setMail(txfMail.getText());
		customer.setRecipientCode(txfRecipientCode.getText());
		customer.setLoyaltyPoints(Integer.parseInt(txfLoyaltyPoints.getText()));
		customer.setP_IVA(txfPIVA.getText());
		customer.setSubscription(null); //TODO: implement sub picker
		customer.setIsFemale(gender);

		lbOutput.setText(name + " " + surname + " modificato");
		CustomerDAO.updateCustomer(id, customer);
		refreshTable();
	}

	@Override
	void deleteElement() {
		if(id <= -1) return;
		CustomerDAO.deleteCustomer(id);
		lbOutput.setText("Cliente rimosso");
		refreshTable();
	}

	@Override
	void disableElement() {
		if(id <= -1) return;
		CustomerDAO.toggleEnabledCustomer(id);
		lbOutput.setText(!isEnabled ? "Cliente abilitato" : "Cliente disabilitato");
		refreshTable();
	}

	@Override
	void populateTable() {
		isRefreshing = true;
		clearTableModel(customerModel);
		customers = CustomerDAO.getAllCustomers();
		if (!customers.isEmpty()) {
			customers.parallelStream()
			.forEach(c -> customerModel.addRow(c.toTableRow()));
		} else {
			lbOutput.setText("Lista Clienti vuota");
		}
		isRefreshing = false;
	}

	@Override
	void clearTxfFields() {
		txfName.setText("");
		txfSurname.setText("");
		txfBirthday.clear();
		txfAddress.setText("");
		txfPhone.setText("");
		txfMail.setText("");
		txfAddress.setText("");
		txfIban.setText("");
		txfBirthplace.setText("");
		txfNotes.setText("");
		txfPIVA.setText("");
		txfRecipientCode.setText("");
	}

	private static boolean getGender() {
		if(genderBtns.getSelectedIndex() == 1) return true;
		return false;
	}

/*	"ID", "Genere", "Nome", "Cognome", "Telefono", "Email",
			"Data di Nascita", "Città natale", "Codice fiscale", "P.IVA", "Codice Ricezione",
			"Punti Fedeltà", "Abbonamento", "Note", "Abilitato" */
	@Override
	ListSelectionListener getListSelectionListener() {
				return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) return;

				var values = getRowMap(table, customerModel);
				
				String phone = (String) values.get( "Telefono");
				String mail = (String) values.get( "Email");
				LocalDate BoD = (LocalDate) values.get( "Data di Nascita");
				String birthplace = (String) values.get("Città natale");
				String address = (String) values.get( "Indirizzo");
				String P_IVA = (String) values.get("P.IVA");
				String recipientCode = (String) values.get( "Codice Ricezione");
				int loyaltyPoints = (int) values.get( "Punti Fedeltà");
				Subscription subscription = (Subscription) values.get( "Abbonamento"); //TODO: Implement subscriptions elsewhere?
				String notes = (String) values.get( "Note");
				String iban = (String) values.get( "IBAN");

				id = (int) values.get( "ID");
				name = (String) values.get( "Nome");
				surname = (String) values.get( "Cognome");
				gender = (boolean) values.get( "Genere");
				isEnabled = (boolean) values.get("Abilitato");

				txfName.setText(name);
				txfSurname.setText(surname);
				txfPhone.setText(phone);
				txfMail.setText(mail);
				txfBirthday.setDate(BoD);
				txfBirthplace.setText(birthplace);
				txfIban.setText(iban);
				txfNotes.setText(notes);
				txfLoyaltyPoints.setText(loyaltyPoints);
				txfPIVA.setText(P_IVA);
				txfRecipientCode.setText(recipientCode);
				txfAddress.setText(address);

				if (gender) {
					genderBtns.setSelected(1);
				} else {
					genderBtns.setSelected(0);
				}
			}
		};
	}

	@Override
	boolean isDataValid() {
		try {
			if(!inputValidator.validateName(txfName.getText()) &&
			!inputValidator.validateName(txfSurname.getText()) &&
			!inputValidator.validateIban(txfIban.getText(), false) &&
			!inputValidator.validateEmail(txfMail.getText()) &&
			!inputValidator.validatePhoneNumber(txfPhone.getText()) &&
			!inputValidator.validateAlphanumeric(txfAddress.getText()) &&
			//inputValidator.validateIban(txfIban) &&
			!inputValidator.validateAlphanumeric(txfBirthplace.getText())) throw new RuntimeException();
		} catch (RuntimeException e) {
			throw e;
		}

		return txfBirthday.isTextFieldValid();
	}
}