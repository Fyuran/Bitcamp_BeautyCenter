package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.TransactionDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.PayMethod;
import com.bitcamp.centro.estetico.models.Transaction;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDateTimePicker;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;

public class TransactionPanel extends BasePanel<Transaction> {
	
	private static TransactionDAO transactionDAO = TransactionDAO.getInstance();
	private static BeautyCenterDAO beautyCenterDAO = BeautyCenterDAO.getInstance();

	private static JSplitComboBox<PayMethod> payMethodComboBox;
	private static JSplitLbTxf priceTextField;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JList<Customer> customersJList;
	private static DefaultListModel<Customer> customerListModel;
	private static JSplitLbTxf servicesTextArea;
	private static JSplitDateTimePicker dateTimePicker;
	private static int id = -1;
	private static boolean isEnabled = false;

	public TransactionPanel() {
		//init BasePanel members

		setSize(1080, 900);
		setName("Transazioni");
		setTitle("GESTIONE TRANSAZIONI");
		
		table.setModel(transactionModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(transactionModel));

		payMethodComboBox = new JSplitComboBox<>("Pagamento");
		actionsPanel.add(payMethodComboBox);
		for(PayMethod payMethod : PayMethod.values()) {
			payMethodComboBox.addItem(payMethod);
		}

		priceTextField = new JSplitLbTxf("Conto", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(priceTextField);

		VATComboBox = new JSplitComboBox<>("IVA");
		actionsPanel.add(VATComboBox);
		for(VAT vat : vats) {
			VATComboBox.addItem(vat);
		}

		JScrollPane customerListScrollPane = new JScrollPane();
		actionsPanel.add(customerListScrollPane);

		customerListModel = new DefaultListModel<>();
		customersJList = new JList<>();
		customersJList.setLocale(Locale.getDefault());
		customersJList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		customersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerListScrollPane.setViewportView(customersJList);
		customerListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		customersJList.setModel(customerListModel);
		customersJList.setCellRenderer(customListCellRenderer);

		servicesTextArea = new JSplitLbTxf("Servizi Effettuati");
		actionsPanel.add(servicesTextArea);

		dateTimePicker = new JSplitDateTimePicker("Data e Orario");
		actionsPanel.add(dateTimePicker);
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	

	@Override
	public void insertElement() {
		try {
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
			PayMethod paymentMethod = (PayMethod) payMethodComboBox.getSelectedItem();
			VAT vat = (VAT) VATComboBox.getSelectedItem();
			Customer customer = customersJList.getSelectedValue();
			String services = servicesTextArea.getText();

			lbOutput.setText("Transazione inserita");
			transactionDAO.insert(new Transaction(price, paymentMethod, dateTime, customer, vat,
				beautyCenterDAO.getFirst().get(), services));
			refreshTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Dati errati o mancanti", "Errore di inserimento",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void updateElement() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow == -1) {
				throw new IllegalArgumentException("no selectedRow selected");
			}
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
			PayMethod paymentMethod = (PayMethod) payMethodComboBox.getSelectedItem();
			VAT vat = (VAT) VATComboBox.getSelectedItem();
			Customer customer = customersJList.getSelectedValue();
			String services = servicesTextArea.getText();

			lbOutput.setText("Transazione aggiornata");
			transactionDAO.update(id, new Transaction(price, paymentMethod, dateTime, customer, vat,
				beautyCenterDAO.getFirst().get(), services));
			refreshTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Dati errati o mancanti", "Errore di aggiornamento",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) return;
		lbOutput.setText("Transazione cancellata");
		transactionDAO.delete(id);
		refreshTable();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) return;
		lbOutput.setText(!isEnabled ? "Transazione abilitata" : "Transazione disabilitata");
		transactionDAO.toggle(id);
		refreshTable();
	}

	@Override
	public void populateTable() {
		isRefreshing = true;
		clearTable(table);
		customerListModel.clear();
		
		customers.parallelStream()
		.forEach(c -> customerListModel.addElement(c));

		transactions = transactionDAO.getAll();
		if (!transactions.isEmpty()) {
			transactions.parallelStream()
			.forEach(t -> transactionModel.addRow(t.toTableRow()));
		} else {
			lbOutput.setText("Lista Transazioni vuota");
		}
		isRefreshing = false;
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) return;

				var values = getRowMap(table, transactionModel);

				id = (int) values.get("ID");
				isEnabled = (boolean) values.get("Abilitato");
				
				BigDecimal price = (BigDecimal) values.get("Conto"); // price
				LocalDateTime dateTime = (LocalDateTime) values.get("Data");
				PayMethod payMethod = (PayMethod) values.get("Pagamento");
				VAT vat = (VAT) values.get("IVA");
				Customer customer = (Customer) values.get("Cliente");
				String services = (String) values.get("Servizi");

				priceTextField.setText(price.toString());
				dateTimePicker.setDateTimePermissive(dateTime);
				payMethodComboBox.setSelectedItem(payMethod);
				VATComboBox.setSelectedItem(vat);
				servicesTextArea.setText(services);

				lbOutput.setText("");
				for (Object obj : customerListModel.toArray()) {
					Customer c = (Customer) obj;
					if (c.getFullName().equals(customer.getFullName())) {
						customersJList.setSelectedValue(obj, true);
					}
				}		
			}
		};
	}

	@Override
	public boolean isDataValid() {
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}

	@Override
	public void clearTxfFields() {
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}

	
}
