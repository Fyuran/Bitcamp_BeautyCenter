package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.PayMethod;
import com.bitcamp.centro.estetico.models.Transaction;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDateTimePicker;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.JSplitTxf;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class TransactionPanel extends AbstractBasePanel<Transaction> {
	private static JSplitComboBox<PayMethod> payMethodComboBox;
	private static JSplitNumber priceTextField;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JSplitTxf servicesTextArea;
	private static JSplitDateTimePicker dateTimePicker;
	private static JSplitBtn customerBtn;

	private static List<Customer> returnCustomers = new ArrayList<>();

	private static BeautyCenter beautyCenter = DAO.get(BeautyCenter.class, 1L).get();
	private static Transaction selectedData = new Transaction();

	public TransactionPanel(JFrame parent) {
		super(parent);
		setSize(1080, 900);
		setName("Transazioni");
		setTitle("GESTIONE TRANSAZIONI");

		payMethodComboBox = new JSplitComboBox<>("Pagamento");
		for (PayMethod payMethod : PayMethod.values()) {
			payMethodComboBox.addItem(payMethod);
		}
		payMethodComboBox.setSelectedIndex(0);

		priceTextField = new JSplitNumber("Conto");
		VATComboBox = new JSplitComboBox<>("IVA");
		servicesTextArea = new JSplitTxf("Servizi Effettuati");
		dateTimePicker = new JSplitDateTimePicker("Data e Orario");

		customerBtn = new JSplitBtn("Cliente", "Scelta Cliente");
		customerBtn.addActionListener(l1 -> {
			ModelChooser<Customer> picker = new ModelChooser<>(parent, "Scelta Cliente",
					ListSelectionModel.SINGLE_SELECTION, returnCustomers);	

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

		actionsPanel.add(payMethodComboBox);
		actionsPanel.add(priceTextField);
		actionsPanel.add(VATComboBox);
		actionsPanel.add(customerBtn);
		actionsPanel.add(servicesTextArea);
		actionsPanel.add(dateTimePicker);

		vats.stream()
		.filter(v -> v.isEnabled())
		.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);

	}

	@Override
	public void insertElement() {
		BigDecimal price = BigDecimal.ZERO;
		try {
			price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			priceTextField.setBorder(UIManager.getBorder("SplitPane.border"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent, "Campo prezzo errato");
			priceTextField.setBorder(new LineBorder(Color.RED));
			return;
		}

		LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
		PayMethod paymentMethod = (PayMethod) payMethodComboBox.getSelectedItem();
		VAT vat = (VAT) VATComboBox.getSelectedItem();
		String services = servicesTextArea.getText();
		Customer customer = null;
		if (!returnCustomers.isEmpty()) {
			customer = returnCustomers.getFirst();
		}

		lbOutput.setText("Transazione inserita");
		DAO.insert(new Transaction(price, vat, dateTime, paymentMethod, customer, beautyCenter, services));
		refresh();
	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessuna transazione selezionata");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;
		if(returnCustomers.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente scelto", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			selectedData.setCustomer(returnCustomers.getFirst());
		}

		BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
		LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
		PayMethod paymentMethod = (PayMethod) payMethodComboBox.getSelectedItem();
		VAT vat = (VAT) VATComboBox.getSelectedItem();
		String services = servicesTextArea.getText();
		// customer is updated by model quick viewer

		selectedData.setPrice(price);
		selectedData.setDateTime(dateTime);
		selectedData.setPaymentMethod(paymentMethod);
		selectedData.setVat(vat);
		selectedData.setServices(services);

		lbOutput.setText("Transazione aggiornata");
		DAO.update(selectedData);
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessuna transazione selezionata");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;
		lbOutput.setText("Transazione cancellata");
		DAO.delete(selectedData);
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessuna transazione selezionata");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;
		DAO.toggle(selectedData);
		lbOutput.setText(selectedData.isEnabled() ? "Transazione abilitata" : "Transazione disabilitata");
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

		transactions.clear();
		transactions.addAll(DAO.getAll(Transaction.class));
		if (!transactions.isEmpty()) {
			transactions.parallelStream()
					.forEach(t -> model.addRow(t));
		} else {
			lbOutput.setText("Lista Transazioni vuota");
		}
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting())
					return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;

				selectedData = model.getObjAt(selectedRow);
				if (selectedData.getId() == null || !selectedData.isEnabled())
					return;

				BigDecimal price = selectedData.getPrice(); // price
				LocalDateTime dateTime = selectedData.getDateTime();
				PayMethod payMethod = selectedData.getPaymentMethod();
				VAT vat = selectedData.getVat();
				String services = selectedData.getServices();

				priceTextField.setText(price);
				dateTimePicker.setDateTimePermissive(dateTime);
				payMethodComboBox.setSelectedItem(payMethod);
				VATComboBox.setSelectedItem(vat);
				servicesTextArea.setText(services);
				
				returnCustomers.clear();

			}
		};
	}

	@Override
	public boolean isDataValid() {
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}

	@Override
	public void clearTxfFields() {
		payMethodComboBox.setSelectedIndex(0);
		priceTextField.setText(0);
		returnCustomers.clear();
		servicesTextArea.setText("");
		VATComboBox.setSelectedIndex(0);
		dateTimePicker.setDateTimePermissive(LocalDateTime.now());
	}

}
