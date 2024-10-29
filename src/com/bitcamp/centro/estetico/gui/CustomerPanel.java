package com.bitcamp.centro.estetico.gui;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.utils.JSplitBtnField;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;
import com.bitcamp.centro.estetico.utils.JSplitRadialButtons;
import com.bitcamp.centro.estetico.utils.ObjectPicker;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.emptyInputException;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class CustomerPanel extends BasePanel<Customer> {

	private static final long serialVersionUID = 1L;
	private static UserCredentialsDAO userCredentialsDAO = UserCredentialsDAO.getInstance();
	private static CustomerDAO customerDAO = CustomerDAO.getInstance();

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
	private static JSplitBtnField txfSubscriptionBtnField;
	private static JSplitBtnField txfPrizesBtnField;

	private static int id = -1;
	private static Gender gender;
	private static String name;
	private static String surname;
	private static Subscription selectedSubscription;
	private static List<Prize> selectedPrizes;
	private static boolean isEnabled = false;

	public CustomerPanel() {

		setName("Clienti");
		setTitle("GESTIONE CLIENTI");
		setSize(1024, 768);

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
		txfSubscriptionBtnField = new JSplitBtnField("Abbonamento", "Visualizza Abbonamento");
		txfSubscriptionBtnField.addActionListener(l -> {
			ObjectPicker<Subscription> picker = new ObjectPicker<>(parent, "Scelta Cliente", subscriptions,
					ListSelectionModel.SINGLE_SELECTION);
					picker.setSelectedItems(selectedSubscription);
					
			picker.addActionListener(ll -> {
				if(!picker.isSelectionEmpty()) {
					selectedSubscription = picker.getSelectedValue();
					picker.dispose();
					txfSubscriptionBtnField.setFieldText(selectedSubscription.getSubPeriod().toString());
				}
			});
			picker.setVisible(true);
		});
		txfPrizesBtnField = new JSplitBtnField("Premi", "Visualizza Pemi");
		txfPrizesBtnField.addActionListener(l -> {
			ObjectPicker<Prize> picker = new ObjectPicker<>(parent, "Aggiunta Premi", prizes,
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					picker.setSelectedItems(selectedPrizes);
			picker.addActionListener(ll -> {
				if(!picker.isSelectionEmpty()) {
					selectedPrizes = picker.getSelectedValuesList();
					picker.dispose();
					int quantity = selectedPrizes.size();
					txfPrizesBtnField.setFieldText(quantity + (quantity == 1 ? " premio scelto" : " premi scelti"));
				}
			});
			picker.setVisible(true);
		});

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
		actionsPanel.add(txfSubscriptionBtnField);
		actionsPanel.add(txfPrizesBtnField);
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	public void insertElement() {
		try { // all fields must be filled
			isDataValid();
		} catch (inputValidatorException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}

		UserCredentials cred = new UserCredentials("", new char[0], txfAddress.getText(), txfIban.getText(),
				txfPhone.getText(), txfMail.getText());
		UserDetails det = new UserDetails(txfName.getText(), txfSurname.getText(), getGender(), txfBirthday.getDate(),
				txfBirthplace.getText(), txfNotes.getText());
		try {
			cred = userCredentialsDAO.insert(cred).orElseThrow();
		} catch (NoSuchElementException e) {
			JOptionPane.showMessageDialog(null, "Campi già esistenti");
		}
		Customer customer = new Customer(det, cred, txfPIVA.getText(), txfRecipientCode.getText(),
				Integer.parseInt(txfLoyaltyPoints.getText()), selectedSubscription, selectedPrizes);

		lbOutput.setText("Nuovo utente creato");
		customerDAO.insert(customer);
		refreshTable();
	}

	@Override
	public void updateElement() {
		try {
			isDataValid();
		} catch (inputValidatorException e) {
			if (!(e instanceof emptyInputException)) { // fields can be empty
				JOptionPane.showMessageDialog(null, e.getMessage());
				return;
			}
			e.errorComponent.setBorder(UIManager.getBorder("TextField.border")); // reset field's border in update mode
		}
		if (table.getSelectedRow() < 0) {
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
		customer.setSubscription(selectedSubscription);
		customer.addPrizes(selectedPrizes);
		customer.setGender(gender);

		lbOutput.setText(name + " " + surname + " modificato");
		customerDAO.update(id, customer);
		refreshTable();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0)
			return;
		customerDAO.delete(id);
		lbOutput.setText("Cliente rimosso");
		refreshTable();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0)
			return;
		customerDAO.toggle(id);
		lbOutput.setText(!isEnabled ? "Cliente abilitato" : "Cliente disabilitato");
		refreshTable();
	}

	@Override
	public void populateTable() {
		isRefreshing = true;
		clearTableModel(customerModel);
		customers = customerDAO.getAll();
		if (!customers.isEmpty()) {
			customers.parallelStream()
					.forEach(c -> customerModel.addRow(c.toTableRow()));
		} else {
			lbOutput.setText("Lista Clienti vuota");
		}
		isRefreshing = false;
	}

	@Override
	public void clearTxfFields() {
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
		if (genderBtns.getSelectedIndex() == 1)
			return true;
		return false;
	}

	/*
	 * "ID", "Genere", "Nome", "Cognome", "Telefono", "Email",
	 * "Data di Nascita", "Città natale", "Codice fiscale", "P.IVA",
	 * "Codice Ricezione",
	 * "Punti Fedeltà", "Abbonamento", "Note", "Abilitato"
	 */
	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing)
					return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;

				var values = getRowMap(table, customerModel);

				String phone = (String) values.get("Telefono");
				String mail = (String) values.get("Email");
				LocalDate BoD = (LocalDate) values.get("Data di Nascita");
				String birthplace = (String) values.get("Città natale");
				String address = (String) values.get("Indirizzo");
				String P_IVA = (String) values.get("P.IVA");
				String recipientCode = (String) values.get("Codice Ricezione");
				int loyaltyPoints = (int) values.get("Punti Fedeltà");
				String notes = (String) values.get("Note");
				String iban = (String) values.get("IBAN");

				id = (int) values.get("ID");
				name = (String) values.get("Nome");
				surname = (String) values.get("Cognome");
				gender = (Gender) values.get("Genere");
				isEnabled = (boolean) values.get("Abilitato");
				selectedSubscription = (Subscription) values.get("Abbonamento");

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

				if (gender == Gender.FEMALE) {
					genderBtns.setSelected(1);
				} else {
					genderBtns.setSelected(0);
				}
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			inputValidator.validateName(txfName);
			inputValidator.validateSurname(txfSurname);
			inputValidator.validateIban(txfIban);
			inputValidator.validateEmail(txfMail);
			inputValidator.validatePhoneNumber(txfPhone);
			inputValidator.validateAlphanumeric(txfAddress, "Indirizzo");
			// inputValidator.validateIban(txfIban);
			inputValidator.validateAlphanumeric(txfBirthplace, "Città natale");
			inputValidator.isValidCity(txfBirthplace);
		} catch (inputValidatorException e) {
			throw e;
		}

		return txfBirthday.isTextFieldValid();
	}
}