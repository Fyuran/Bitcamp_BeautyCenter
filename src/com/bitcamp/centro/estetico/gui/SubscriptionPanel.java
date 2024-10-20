package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.SubscriptionDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.SubPeriod;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;

public class SubscriptionPanel extends BasePanel<Subscription> {

	private static int id = -1;
	private static boolean isEnabled = false;
	private static double discount;

	private static final long serialVersionUID = 1712892330014716939L;
	// actions
	private static JSplitLbTxf priceTextField;
	private static JList<Customer> customersJList;
	private static JSplitDatePicker datePicker;
	private static JSplitComboBox<VAT> vatComboBox;
	private static JSplitComboBox<SubPeriod> subPeriodComboBox;
	private static JSplitLbTxf discountTextField;
	private static DefaultListModel<Customer> customerListModel;

	public SubscriptionPanel() {
		setSize(1024, 768);
		setName("Abbonamenti");
		setTitle("Gestione Abbonamenti");

		table.setModel(subscriptionModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(subscriptionModel));
		table.getSelectionModel().addListSelectionListener(getListSelectionListener());

		vatComboBox = new JSplitComboBox<>("IVA");
		VATDao.getAllVAT().parallelStream().forEach(v -> vatComboBox.addItem(v));
		actionsPanel.add(vatComboBox);

		priceTextField = new JSplitLbTxf("Prezzo", new JFormattedTextField(NumberFormat.getInstance()));
		priceTextField.setColumns(10);
		actionsPanel.add(priceTextField);

		customersJList = new JList<>();
		JScrollPane customerListScrollPane = new JScrollPane(customersJList);
		actionsPanel.add(customerListScrollPane);

		customersJList.setLocale(Locale.getDefault());
		customersJList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		customersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		customerListModel = new DefaultListModel<>();
		customersJList.setModel(customerListModel);
		customersJList.setSelectedIndex(0);
		customersJList.setCellRenderer(customListCellRenderer);
		actionsPanel.add(customerListScrollPane);

		subPeriodComboBox = new JSplitComboBox<>("Periodo");
		for(SubPeriod subPeriod : SubPeriod.class.getEnumConstants()) {
			subPeriodComboBox.addItem(subPeriod);
		}		
		actionsPanel.add(subPeriodComboBox);

		datePicker = new JSplitDatePicker("Data inizio");
		actionsPanel.add(datePicker);

		discountTextField = new JSplitLbTxf("Sconto applicato", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(discountTextField);
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	void insertElement() {
		try {
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDate start = datePicker.getDate();

			SubPeriod subperiod = (SubPeriod) subPeriodComboBox.getSelectedItem();
			VAT vat = VATDao.getVAT(vatComboBox.getSelectedItem()).get();
			Customer customer = customersJList.getSelectedValue();

			double discount = Double.parseDouble(discountTextField.getText());

			// "ID", "Prezzo", "IVA", "Periodo", "Inizio", "Fine", "Sconto applicato",
			// "Cliente", "Abilitata"
			Subscription subscription = new Subscription(subperiod, start, price, vat, discount, true);
			Optional<Subscription> opt = SubscriptionDAO.insertSubscription(subscription);
			subscription = opt.get();
			lbOutput.setText("Abbonamento inserito");
			SubscriptionDAO.addSubscriptionToCustomer(customer, subscription, start);
			refreshTable();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), ex.getLocalizedMessage(), "Errore di inserimento",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	void updateElement() {
		try {
			if (id <= -1) return;
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDate start = datePicker.getDate();

			SubPeriod subperiod = (SubPeriod) subPeriodComboBox.getSelectedItem();
			VAT vat = vatComboBox.getSelectedItem();
			Customer customer = customersJList.getSelectedValue();

			Subscription subscription = new Subscription(subperiod, start, price, vat, discount, isEnabled);
			SubscriptionDAO.addSubscriptionToCustomer(customer, subscription, start);

			lbOutput.setText("Abbonamento aggiornato");
			SubscriptionDAO.updateSubscription(id, subscription);
			refreshTable();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Dati errati o mancanti\n" + ex.getLocalizedMessage(),
					"Errore di aggiornamento",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	void deleteElement() {
		if(id <= -1) return;
		lbOutput.setText("Abbonamento rimosso");
		SubscriptionDAO.deleteSubscription(id);
		refreshTable();
	}

	@Override
	void disableElement() {
		if (id <= -1) return;
		lbOutput.setText(!isEnabled ? "Abbonamento abilitato" : "Abbonamento disabilitato");
		SubscriptionDAO.toggleEnabledSubscription(id);
		refreshTable();
	}

	@Override
	void populateTable() {
		isRefreshing = true;
		subscriptionModel.setRowCount(0);
		customerListModel.clear();

		customers.parallelStream()
				.forEach(c -> customerListModel.addElement(c));

		List<Subscription> subscriptions = SubscriptionDAO.getAllSubscriptions();
		if (!subscriptions.isEmpty()) {
			subscriptions.parallelStream()
					.forEach(t -> subscriptionModel.addRow(t.toTableRow()));
		} else {
			lbOutput.setText("Lista abbonamenti vuota");
		}
		isRefreshing = false;
	}

	@Override
	void clearTxfFields() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}

	@Override
	ListSelectionListener getListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) return;

				var values = getRowMap(table, subscriptionModel);

				id = (int) values.get("ID");
				isEnabled = (boolean) values.get("Abilitato");

				BigDecimal price = (BigDecimal) values.get("Prezzo");
				LocalDate localDate = (LocalDate) values.get("Inizio");
				VAT vat = (VAT) values.get("IVA");
				discount = (double) values.get("Sconto applicato");

				priceTextField.setText(price.toString());
				datePicker.setDate(localDate);
				vatComboBox.setSelectedItem(vat);
				discountTextField.setText(discount);
				
				lbOutput.setText("");
				Customer customer = (Customer) values.get("Cliente");
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
	boolean isDataValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}
}
