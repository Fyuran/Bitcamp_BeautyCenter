package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.SubPeriod;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.InputValidator.InvalidInputException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class SubscriptionPanel extends AbstractBasePanel<Subscription> {

	private static Subscription selectedData = new Subscription();
	private static List<Customer> returnCustomers  = new ArrayList<>();

	private static final long serialVersionUID = 1712892330014716939L;
	// actions
	private static JSplitNumber priceTextField;
	private static JSplitDatePicker datePicker;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JSplitComboBox<SubPeriod> subPeriodComboBox;
	private static JSplitNumber discountTextField;
	private static JSplitBtn customerBtn;

	public SubscriptionPanel(JFrame parent) {
		super(parent);
		setSize(1024, 768);
		setName("Abbonamenti");
		setTitle("Gestione Abbonamenti");

		priceTextField = new JSplitNumber("Prezzo");
		VATComboBox = new JSplitComboBox<>("IVA");

		customerBtn = new JSplitBtn("Cliente", "Scelta cliente");
		customerBtn.addActionListener(l1 -> {
			ModelChooser<Customer> picker = new ModelChooser<>(parent, "Scelta Cliente",
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, returnCustomers);
			customers.clear();
			customers.addAll(DAO.getAll(Customer.class));
			var available = customers
			.parallelStream()
			.filter(c -> c.isEnabled())
			.toList();

			if(!available.isEmpty()) {
				picker.addRows(available);
			}
			else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});

		subPeriodComboBox = new JSplitComboBox<>("Periodo");
		for (SubPeriod subPeriod : SubPeriod.class.getEnumConstants()) {
			subPeriodComboBox.addItem(subPeriod);
		}
		subPeriodComboBox.setSelectedIndex(0);

		datePicker = new JSplitDatePicker("Data inizio");
		discountTextField = new JSplitNumber("Sconto applicato");

		actionsPanel.add(VATComboBox);
		actionsPanel.add(priceTextField);
		actionsPanel.add(subPeriodComboBox);
		actionsPanel.add(datePicker);
		actionsPanel.add(discountTextField);
		actionsPanel.add(customerBtn);

		vats.stream()
		.filter(v -> v.isEnabled())
		.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);
	}

	@Override
	public void insertElement() {
		BigDecimal price = BigDecimal.ZERO;
		double discount = 0;
		try {
			isDataValid();
			price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			priceTextField.setBorder(UIManager.getBorder("SplitPane.border"));
		} catch (NullPointerException | NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, "Campo prezzo errato");
			priceTextField.setBorder(new LineBorder(Color.RED));
			return;
		} catch (InvalidInputException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		try {
			discount = Double.parseDouble(discountTextField.getText());
			discountTextField.setBorder(UIManager.getBorder("SplitPane.border"));
		} catch (NullPointerException | NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, "Campo Sconto errato");
			discountTextField.setBorder(new LineBorder(Color.RED));
			return;
		}

		LocalDate start = datePicker.getDate();
		SubPeriod subperiod = (SubPeriod) subPeriodComboBox.getSelectedItem();
		LocalDate end = start.plusMonths(subperiod.getMonths());
		VAT vat = DAO.get(VAT.class, VATComboBox.getSelectedItem().getId()).get();

		Customer customer = null;
		if (!returnCustomers.isEmpty()) {
			customer = returnCustomers.getFirst();
		}

		Subscription subscription = new Subscription(subperiod, start, end, price, vat, discount, customer);
		DAO.insert(subscription);

		lbOutput.setText("Abbonamento inserito");
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

		BigDecimal price = BigDecimal.ZERO;
		double discount = 0;
		try {
			isDataValid();
			price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			priceTextField.setBorder(UIManager.getBorder("SplitPane.border"));
		} catch (NullPointerException | NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, "Campo prezzo errato");
			priceTextField.setBorder(new LineBorder(Color.RED));
			return;
		} catch (InvalidInputException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		try {
			discount = Double.parseDouble(discountTextField.getText());
			discountTextField.setBorder(UIManager.getBorder("SplitPane.border"));
		} catch (NullPointerException | NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, "Campo Sconto errato");
			discountTextField.setBorder(new LineBorder(Color.RED));
			return;
		}

		LocalDate start = datePicker.getDate();
		SubPeriod subperiod = (SubPeriod) subPeriodComboBox.getSelectedItem();
		LocalDate end = start.plusMonths(subperiod.getMonths());
		VAT vat = DAO.get(VAT.class, VATComboBox.getSelectedItem().getId()).get();

		// customer is updated in modelquickviewer

		selectedData.setVat(vat);
		selectedData.setDiscount(discount);
		selectedData.setStart(start);
		selectedData.setEnd(end);
		selectedData.setPrice(price);
		selectedData.setSubperiod(subperiod);
		if(returnCustomers.isEmpty()) {
			selectedData.setCustomer(null);
		} else {
			selectedData.setCustomer(returnCustomers.getFirst());
		}

		DAO.update(selectedData);

		lbOutput.setText("Abbonamento aggiornato");
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
		lbOutput.setText("Abbonamento rimosso");
		DAO.delete(selectedData);
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
		lbOutput.setText(selectedData.isEnabled() ? "Abbonamento abilitato" : "Abbonamento disabilitato");
		refresh();
	}

	@Override
	public void populateTable() {
		vats.clear();
		vats.addAll(DAO.getAll(VAT.class));
		VATComboBox.removeAllItems();
		vats.stream()
				.filter(v -> v.isEnabled())
				.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);

		subscriptions.clear();
		subscriptions.addAll(DAO.getAll(Subscription.class));
		if (!subscriptions.isEmpty()) {
			model.addRows(subscriptions);
		} else {
			lbOutput.setText("Lista abbonamenti vuota");
		}
	}

	@Override
	public void clearTxfFields() {
		datePicker.setDate(LocalDate.now());
		discountTextField.setText(0);
		priceTextField.setText(0);
		subPeriodComboBox.setSelectedIndex(0);
		VATComboBox.setSelectedIndex(0);
		returnCustomers.clear();
	}

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

				BigDecimal price = selectedData.getPrice();
				LocalDate start = selectedData.getStart();
				VAT vat = selectedData.getVat();
				double discount = selectedData.getDiscount();

				priceTextField.setText(price);
				datePicker.setDate(start);
				VATComboBox.setSelectedItem(vat);
				discountTextField.setText(discount);

				returnCustomers.clear();
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			if (!datePicker.getDatePicker().isTextFieldValid()) {
				throw new InvalidInputException("Data non valida", datePicker);
			} else {
				datePicker.setBorder(UIManager.getBorder("SplitPane.border"));
			}
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
		}

		return datePicker.isTextFieldValid();
	}
}
