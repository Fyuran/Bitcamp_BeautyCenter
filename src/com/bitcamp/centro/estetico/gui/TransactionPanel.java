package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

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
import com.github.lgooddatepicker.components.DateTimePicker;

public class TransactionPanel extends BasePanel<Transaction> {
	
	private static JComboBox<PayMethod> PayMethodComboBox;
	private static JFormattedTextField priceTextField;
	private static JComboBox<VAT> VATComboBox;
	private static JList<Customer> customersJList;
	private static JTextArea servicesTextArea;
	private static DateTimePicker dateTimePicker;
	private static final int _ISENABLEDCOL = 7;
	private static DefaultListModel<Customer> customerListModel;

	public TransactionPanel() {
		//init BasePanel members
		setLayout(null);
		setSize(1080, 900);
		setName("Transazioni");
		setTitle("GESTIONE TRANSAZIONI");
		lbTitle.setBounds(10, 11, 1004, 32);
		menuPanel.setBounds(10, 54, 1004, 593);
		
		table.setModel(transactionModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(transactionModel, _ISENABLEDCOL));
		table.removeColumn(table.getColumnModel().getColumn(_ISENABLEDCOL));

		scrollPane.setBounds(23, 60, 959, 507);
		btnSearch.setBounds(206, 8, 40, 40);
		txfSearchBar.setBounds(23, 14, 168, 24);
		btnFilter.setBounds(256, 8, 40, 40);
		btnInsert.setBounds(792, 8, 40, 40);
		btnUpdate.setBounds(842, 8, 40, 40);
		btnDisable.setBounds(892, 8, 40, 40);
		outputPanel.setBounds(23, 60, 959, 507);
		btnRefresh.setBounds(942, 8, 40, 40);
		btnDelete.setBounds(742, 8, 40, 40);
		lbOutput.setBounds(306, 8, 438, 41);
		actionsPanel.setBounds(20, 658, 994, 181);

		JLabel lbPrice = new JLabel("€");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbPrice, 16, SpringLayout.NORTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbPrice, 10, SpringLayout.WEST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, lbPrice, -960, SpringLayout.EAST, actionsPanel);
		actionsPanel.add(lbPrice);

		JLabel lbDate = new JLabel("Data e Orario");
		springLayoutActions.putConstraint(SpringLayout.WEST, lbDate, 10, SpringLayout.WEST, actionsPanel);
		actionsPanel.add(lbDate);

		PayMethodComboBox = new JComboBox<>();
		PayMethodComboBox.setEditable(true);
		springLayoutActions.putConstraint(SpringLayout.NORTH, PayMethodComboBox, 85, SpringLayout.NORTH,
				actionsPanel);
		Arrays.stream(PayMethod.values())
		.parallel().forEach(p -> PayMethodComboBox.addItem(p));
		actionsPanel.add(PayMethodComboBox);

		JLabel lbPayMethod = new JLabel("<html><div>Metodo di Pagamento</div></html>");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbPayMethod, 17, SpringLayout.SOUTH, lbDate);
		springLayoutActions.putConstraint(SpringLayout.WEST, PayMethodComboBox, 6, SpringLayout.EAST,
				lbPayMethod);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbPayMethod, 10, SpringLayout.WEST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, lbPayMethod, -908, SpringLayout.EAST,
				actionsPanel);
		lbPayMethod.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lbPayMethod);

		priceTextField = new JFormattedTextField(NumberFormat.getInstance());
		springLayoutActions.putConstraint(SpringLayout.NORTH, priceTextField, -5, SpringLayout.NORTH, lbPrice);
		springLayoutActions.putConstraint(SpringLayout.WEST, priceTextField, 58, SpringLayout.EAST, lbPrice);
		springLayoutActions.putConstraint(SpringLayout.SOUTH, priceTextField, 6, SpringLayout.SOUTH, lbPrice);
		priceTextField.setColumns(10);
		actionsPanel.add(priceTextField);

		VATComboBox = new JComboBox<>();
		springLayoutActions.putConstraint(SpringLayout.NORTH, VATComboBox, 6, SpringLayout.SOUTH, PayMethodComboBox);
		VATComboBox.setEditable(true);
		DefaultComboBoxModel<VAT> vatModel = new DefaultComboBoxModel<>();
		vatModel.addAll(vats);
		VATComboBox.setModel(vatModel);
		VATComboBox.setSelectedIndex(0);
		actionsPanel.add(VATComboBox);

		JLabel lbVAT = new JLabel("IVA");
		springLayoutActions.putConstraint(SpringLayout.WEST, VATComboBox, 65, SpringLayout.EAST, lbVAT);
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbVAT, 5, SpringLayout.SOUTH, lbPayMethod);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbVAT, 0, SpringLayout.WEST, lbPrice);
		lbVAT.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lbVAT);

		JScrollPane customerListScrollPane = new JScrollPane();
		springLayoutActions.putConstraint(SpringLayout.EAST, VATComboBox, -217, SpringLayout.WEST, customerListScrollPane);
		springLayoutActions.putConstraint(SpringLayout.EAST, priceTextField, -217, SpringLayout.WEST, customerListScrollPane);
		springLayoutActions.putConstraint(SpringLayout.NORTH, customerListScrollPane, 0, SpringLayout.NORTH,
				actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.SOUTH, customerListScrollPane, -4, SpringLayout.SOUTH,
				actionsPanel);
		actionsPanel.add(customerListScrollPane);

		customersJList = new JList<>();
		springLayoutActions.putConstraint(SpringLayout.SOUTH, customersJList, 0, SpringLayout.SOUTH,
				lbPayMethod);
		customersJList.setLocale(Locale.getDefault());
		customersJList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		customersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerListScrollPane.setViewportView(customersJList);
		customerListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		customersJList.setModel(customerListModel);
		customersJList.setCellRenderer(customListCellRenderer);

		servicesTextArea = new JTextArea();
		springLayoutActions.putConstraint(SpringLayout.NORTH, servicesTextArea, 0, SpringLayout.NORTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.SOUTH, servicesTextArea, -4, SpringLayout.SOUTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.WEST, servicesTextArea, 788, SpringLayout.WEST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, servicesTextArea, -10, SpringLayout.EAST, actionsPanel);
		servicesTextArea.setBorder(new LineBorder(Color.BLACK, 1, true));
		servicesTextArea.setDropMode(DropMode.INSERT);
		servicesTextArea.setWrapStyleWord(true);
		servicesTextArea.setLineWrap(true);
		actionsPanel.add(servicesTextArea);

		JLabel lblServices = new JLabel("Servizi Effettuati");
		springLayoutActions.putConstraint(SpringLayout.EAST, customerListScrollPane, -19, SpringLayout.WEST,
				lblServices);
		springLayoutActions.putConstraint(SpringLayout.NORTH, lblServices, 81, SpringLayout.NORTH, servicesTextArea);
		springLayoutActions.putConstraint(SpringLayout.EAST, lblServices, -6, SpringLayout.WEST, servicesTextArea);
		actionsPanel.add(lblServices);

		JLabel lbCustomerChoice = new JLabel("Scelta Cliente");
		springLayoutActions.putConstraint(SpringLayout.EAST, PayMethodComboBox, -150, SpringLayout.WEST,
				lbCustomerChoice);
		springLayoutActions.putConstraint(SpringLayout.WEST, customerListScrollPane, 2, SpringLayout.EAST,
				lbCustomerChoice);
		springLayoutActions.putConstraint(SpringLayout.EAST, lbCustomerChoice, -568, SpringLayout.EAST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbCustomerChoice, 81, SpringLayout.NORTH,
				customerListScrollPane);
		actionsPanel.add(lbCustomerChoice);

		dateTimePicker = new DateTimePicker();
		springLayoutActions.putConstraint(SpringLayout.EAST, lbDate, -6, SpringLayout.WEST, dateTimePicker);
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbDate, 4, SpringLayout.NORTH, dateTimePicker);
		springLayoutActions.putConstraint(SpringLayout.NORTH, dateTimePicker, 46, SpringLayout.NORTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.WEST, dateTimePicker, 92, SpringLayout.WEST, actionsPanel);
		actionsPanel.add(dateTimePicker);
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	

	@Override
	public Optional<Transaction> insertElement() {
		try {
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
			PayMethod paymentMethod = (PayMethod) PayMethodComboBox.getSelectedItem();
			VAT vat = (VAT) VATComboBox.getSelectedItem();
			Customer customer = customersJList.getSelectedValue();
			String services = servicesTextArea.getText();

			lbOutput.setText("Transazione inserita");
			refreshTable();
			
			return TransactionDAO.insertTransaction(new Transaction(price, paymentMethod, dateTime, customer, vat,
					BeautyCenterDAO.getFirstBeautyCenter().get(), services));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Dati errati o mancanti", "Errore di inserimento",
					JOptionPane.ERROR_MESSAGE);
		}
		return Optional.empty();
	}

	@Override
	public int updateElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("no row selected");
			}
			final int id = (int) transactionModel.getValueAt(row, 0);
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
			PayMethod paymentMethod = (PayMethod) PayMethodComboBox.getSelectedItem();
			VAT vat = (VAT) VATComboBox.getSelectedItem();
			Customer customer = customersJList.getSelectedValue();
			String services = servicesTextArea.getText();

			lbOutput.setText("Transazione aggiornata");
			refreshTable();

			return TransactionDAO.updateTransaction(id, new Transaction(price, paymentMethod, dateTime, customer, vat,
					BeautyCenterDAO.getFirstBeautyCenter().get(), services));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Dati errati o mancanti", "Errore di aggiornamento",
					JOptionPane.ERROR_MESSAGE);
		}
		return -1;
	}

	@Override
	public int deleteElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("no row selected");
			}
			final int id = (int) transactionModel.getValueAt(row, 0);
			lbOutput.setText("Transazione cancellata");
			refreshTable();
			return TransactionDAO.deleteTransaction(id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Impossibile cancellare: " + e.getMessage(), "Errore di database",
					JOptionPane.ERROR_MESSAGE);
		}
		return -1;
	}

	@Override
	public int disableElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("Nessuna riga scelta");
			}
			boolean currentFlag = (boolean) transactionModel.getValueAt(row, _ISENABLEDCOL);
			transactionModel.setValueAt(!currentFlag, row, _ISENABLEDCOL);
			table.repaint();
			Transaction trans = transactions.get(row);
			trans.setEnabled(!currentFlag);
			lbOutput.setText(trans.isEnabled() ? "Transazione abilitata" : "Transazione disabilitata");
			return TransactionDAO.updateTransaction(trans.getId(), trans);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Impossibile disabilitare",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return -1;
	}

	@Override
	public void populateTable() {
		clearTable();
		customerListModel.clear();

		customers.parallelStream()
		.forEach(c -> customerListModel.addElement(c));

		if (!transactions.isEmpty()) {
			transactions.parallelStream()
			.forEach(t -> transactionModel.addRow(t.toTableRow()));
		} else {
			lbOutput.setText("Lista Transazioni vuota");
		}
	}

	@Override
	public ListSelectionListener getListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) {
					return;
				}
				int row = table.getSelectedRow();
				lbOutput.setText("");
				BigDecimal price = (BigDecimal) transactionModel.getValueAt(row, 1); // price
				priceTextField.setText(price.toString());

				DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
				LocalDateTime dateTime = LocalDateTime.parse((String) transactionModel.getValueAt(row, 2), dtf);
				dateTimePicker.setDateTimePermissive(dateTime);

				PayMethodComboBox.setSelectedItem(transactionModel.getValueAt(row, 3));

				VATComboBox.setSelectedItem(transactionModel.getValueAt(row, 4));

				Customer customer = (Customer) transactionModel.getValueAt(row, 5);
				for (Object obj : customerListModel.toArray()) {
					if (obj instanceof Customer c) {
						if (c.getFullName().equals(customer.getFullName())) {
							customersJList.setSelectedValue(obj, true);
						}
					}
				}

				String services = (String) transactionModel.getValueAt(row, 6);
				servicesTextArea.setText(services);

			}
		};
	}

	@Override
	boolean isDataValid() {
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}

	@Override
	void clearTxfFields() {
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}

	
}
