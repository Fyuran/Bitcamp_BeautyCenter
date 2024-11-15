package com.bitcamp.centro.estetico.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.UserDetails;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.EmptyInputException;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.InputValidator.InvalidInputException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.JSplitPf;
import com.bitcamp.centro.estetico.utils.JSplitRadialButtons;
import com.bitcamp.centro.estetico.utils.JSplitTxf;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class CustomerPanel extends AbstractBasePanel<Customer> {

	private static final long serialVersionUID = 1L;

	private static JSplitTxf txfName;
	private static JSplitTxf txfSurname;
	private static JSplitTxf txfPhone;
	private static JSplitTxf txfMail;
	private static JSplitTxf txfAddress;
	private static JSplitTxf txfIban;
	private static JSplitTxf txfUsername;
	private static JSplitPf txfPassword;
	private static JSplitTxf txfBirthplace;
	private static JSplitRadialButtons genderBtns;
	private static JSplitDatePicker txfBirthday;
	private static JSplitTxf txfNotes;
	private static JSplitTxf txfPIVA;
	private static JSplitTxf txfRecipientCode;
	private static JSplitNumber txfLoyaltyPoints;
	private static JSplitBtn subscriptionBtn;
	private static JSplitBtn prizesBtnField;

	private static List<Subscription> returnSubscriptions = new ArrayList<>();
	private static List<Prize> returnPrizes = new ArrayList<>();

	private static Customer selectedData = new Customer();

	public CustomerPanel(JFrame parent) {
		super(parent);
		setName("Clienti");
		setTitle("GESTIONE CLIENTI");
		setSize(1300, 768);

		txfName = new JSplitTxf("Nome");
		txfSurname = new JSplitTxf("Cognome");
		txfMail = new JSplitTxf("Mail");
		txfPhone = new JSplitTxf("Telefono");
		txfAddress = new JSplitTxf("Indirizzo");
		txfBirthday = new JSplitDatePicker("Data di nascita");
		txfUsername = new JSplitTxf("Username");
		txfPassword = new JSplitPf("Password");
		txfBirthplace = new JSplitTxf("Luogo di nascita");
		genderBtns = new JSplitRadialButtons("Genere", "Maschio", "Femmina");
		txfNotes = new JSplitTxf("Note");
		txfIban = new JSplitTxf("IBAN");
		txfPIVA = new JSplitTxf("P.IVA");
		txfRecipientCode = new JSplitTxf("Codice Ricezione");
		txfLoyaltyPoints = new JSplitNumber("Punti fedeltà");
		txfLoyaltyPoints.setText(0);
		subscriptionBtn = new JSplitBtn("Abbonamento", "Scegli Abbonamenti");
		subscriptionBtn.addActionListener(l -> {
			ModelChooser<Subscription> picker = new ModelChooser<>(parent, "Abbonamenti",
					ListSelectionModel.SINGLE_SELECTION, returnSubscriptions);
			subscriptions.clear();
			subscriptions.addAll(DAO.getAll(Subscription.class));
			var available = subscriptions
					.parallelStream()
					.filter(c -> c.isEnabled())
					.toList();

			if (!available.isEmpty()) {
				picker.addRows(available);
			} else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});
		prizesBtnField = new JSplitBtn("Premi", "Scegli Pemi");
		prizesBtnField.addActionListener(l -> {
			ModelChooser<Prize> picker = new ModelChooser<>(parent, "Premi",
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, returnPrizes);
			prizes.clear();
			prizes.addAll(DAO.getAll(Prize.class));
			var available = prizes
					.parallelStream()
					.filter(p -> p.isEnabled())
					.toList();

			if (!available.isEmpty()) {
				picker.addRows(available);
			} else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});

		actionsPanel.add(txfName);
		actionsPanel.add(txfSurname);
		actionsPanel.add(txfMail);
		actionsPanel.add(txfPhone);
		actionsPanel.add(txfUsername);
		actionsPanel.add(txfAddress);
		actionsPanel.add(txfPassword);
		actionsPanel.add(txfBirthday);
		actionsPanel.add(txfBirthplace);
		actionsPanel.add(genderBtns);
		actionsPanel.add(txfNotes);
		actionsPanel.add(txfIban);
		actionsPanel.add(txfPIVA);
		actionsPanel.add(txfRecipientCode);
		actionsPanel.add(txfLoyaltyPoints);
		actionsPanel.add(subscriptionBtn);
		actionsPanel.add(prizesBtnField);

	}

	@Override
	public void insertElement() {
		try { // all fields must be filled
			isDataValid();
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		String name = txfName.getText();
		String surname = txfSurname.getText();
		String birthplace = txfBirthplace.getText();
		LocalDate BoD = txfBirthday.getDate();
		// LocalDate hired = LocalDate.now();
		String notes = txfNotes.getText();
		// String username = txfUsername.getText();
		// char[] password = txfPassword.getPassword();
		String address = txfAddress.getText();
		String iban = txfIban.getText();
		String phone = txfPhone.getText();
		String mail = txfMail.getText();
		String recipientCode = txfRecipientCode.getText();
		String username = txfUsername.getText();
		char[] password = txfPassword.getPassword();

		String loyaltytfxText = txfLoyaltyPoints.getText();
		int loyaltyPoints = loyaltytfxText.isBlank() ? 0 : Integer.parseInt(loyaltytfxText);

		UserCredentials cred = new UserCredentials(username, null, address, iban, phone, mail, null);
		cred.setPassword(password);
		UserDetails det = new UserDetails(name, surname, getGender(), BoD, birthplace, notes);

		try {
			InputValidator.validateUsername(txfUsername, cred);
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		Subscription newSubscription = null;
		if (!returnSubscriptions.isEmpty()) {
			newSubscription = returnSubscriptions.getFirst();
		}
		List<Prize> newPrizes = null;
		if (!returnPrizes.isEmpty()) {
			newPrizes = returnPrizes;
		}

		Customer customer = new Customer(det, cred, mail, recipientCode, loyaltyPoints, newSubscription, newPrizes);
		cred.setUser(customer);

		DAO.insert(customer);

		lbOutput.setText("Nuovo utente creato");
		refresh();
	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		try {
			isDataValid();
		} catch (InputValidatorException e) {
			if (!(e instanceof EmptyInputException)) { // fields can be empty
				JOptionPane.showMessageDialog(parent, e.getMessage());
				return;
			}
			e.errorComponent.setBorder(UIManager.getBorder("SplitPane.border")); // reset field's border in update mode
		}

		selectedData.setName(txfName.getText());
		selectedData.setSurname(txfSurname.getText());
		selectedData.setBirthplace(txfBirthplace.getText());
		selectedData.setBoD(txfBirthday.getDate());
		selectedData.setNotes(txfNotes.getText());
		selectedData.setUsername(txfUsername.getText());
		selectedData.setPassword(txfPassword.getPassword());
		selectedData.setAddress(txfAddress.getText());
		selectedData.setPhone(txfPhone.getText());
		selectedData.setMail(txfMail.getText());
		selectedData.setRecipientCode(txfRecipientCode.getText());
		selectedData.setLoyaltyPoints(Integer.parseInt(txfLoyaltyPoints.getText()));
		selectedData.setP_iva(txfPIVA.getText());
		selectedData.setGender(getGender());
		if (returnSubscriptions.isEmpty()) {
			selectedData.setSubscription(null);
		} else {
			selectedData.setSubscription(returnSubscriptions.getFirst());
		}
		if (returnPrizes.isEmpty()) {
			selectedData.setPrizes(null);
		} else {
			selectedData.setPrizes(returnPrizes);
		}

		try {
			InputValidator.validateUsername(txfUsername, selectedData.getUserCredentials());
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		DAO.update(selectedData);
		lbOutput.setText("Utente modificato");
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		DAO.delete(selectedData);
		lbOutput.setText("Cliente rimosso");
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;

		DAO.toggle(selectedData);
		lbOutput.setText(selectedData.isEnabled() ? "Cliente abilitato" : "Cliente disabilitato");
		refresh();
	}

	@Override
	public void populateTable() {
		customers.clear();
		customers.addAll(DAO.getAll(Customer.class));
		if (!customers.isEmpty()) {
			model.addRows(customers);
		} else {
			lbOutput.setText("Lista Clienti vuota");
		}
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

	private static Gender getGender() {
		if (genderBtns.getSelectedIndex() == 1)
			return Gender.FEMALE;
		return Gender.MALE;
	}

	/*
	 * "ID", "Genere", "Nome", "Cognome", "Telefono", "Email",
	 * "Data di Nascita", "Luogo di nascita", "Codice fiscale", "P.IVA",
	 * "Codice Ricezione",
	 * "Punti Fedeltà", "Abbonamento", "Note", "Abilitato"
	 */
	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting())
					return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;

				selectedData = model.getObjAt(selectedRow);
				if (selectedData == null || !selectedData.isEnabled())
					return;

				txfName.setText(selectedData.getName());
				txfSurname.setText(selectedData.getSurname());
				txfPhone.setText(selectedData.getPhone());
				txfMail.setText(selectedData.getMail());
				txfBirthday.setDate(selectedData.getBirthday());
				txfBirthplace.setText(selectedData.getBirthplace());
				txfIban.setText(selectedData.getIban());
				txfNotes.setText(selectedData.getNotes());
				txfLoyaltyPoints.setText(selectedData.getLoyaltyPoints());
				txfPIVA.setText(selectedData.getP_iva());
				txfRecipientCode.setText(selectedData.getRecipientCode());
				txfUsername.setText(selectedData.getUsername());
				txfAddress.setText(selectedData.getAddress());

				if (selectedData.getGender() == Gender.FEMALE) {
					genderBtns.setSelected(1);
				} else {
					genderBtns.setSelected(0);
				}

				returnPrizes.clear();
				returnSubscriptions.clear();
				;
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			if (!txfBirthday.getDatePicker().isTextFieldValid()) {
				throw new InvalidInputException("Anno di nascita non valida", txfBirthday);
			} else {
				txfBirthday.setBorder(UIManager.getBorder("SplitPane.border"));
			}
			InputValidator.validateName(txfName);
			InputValidator.validateSurname(txfSurname);
			InputValidator.validatePhoneNumber(txfPhone);
			InputValidator.validateAlphanumeric(txfAddress, "Indirizzo");
			InputValidator.validateAlphanumeric(txfUsername, "Username");
			InputValidator.validatePassword(txfPassword);
			InputValidator.validateAlphanumeric(txfBirthplace, "Luogo di nascita");
			InputValidator.isValidCity(txfBirthplace);
			InputValidator.validateNumber(txfLoyaltyPoints, 0, Integer.MAX_VALUE);
		} catch (InputValidatorException e) {
			throw e;
		}

		return txfBirthday.isTextFieldValid();
	}

}