package com.bitcamp.centro.estetico.gui;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.SubscriptionDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.SubPeriod;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitBtnField;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;
import com.bitcamp.centro.estetico.utils.ObjectPicker;

public class SubscriptionPanel extends BasePanel<Subscription> {

	private static SubscriptionDAO subscriptionDAO = SubscriptionDAO.getInstance();
	private static VAT_DAO vat_DAO = VAT_DAO.getInstance();
	private static Long id = -1;
	private static boolean isEnabled = false;
	private static double discount;
	private static Customer selectedCustomer;

	private static final long serialVersionUID = 1712892330014716939L;
	// actions
	private static JSplitLbTxf priceTextField;
	private static JSplitDatePicker datePicker;
	private static JSplitComboBox<VAT> vatComboBox;
	private static JSplitComboBox<SubPeriod> subPeriodComboBox;
	private static JSplitLbTxf discountTextField;
	private static JSplitBtnField customerBtnField;

	public SubscriptionPanel() {
		setSize(1024, 768);
		setName("Abbonamenti");
		setTitle("Gestione Abbonamenti");

		table.setModel(subscriptionModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(subscriptionModel));
		table.getSelectionModel().addListSelectionListener(getTableListSelectionListener());

		vatComboBox = new JSplitComboBox<>("IVA");
		vats.parallelStream().forEach(v -> vatComboBox.addItem(v));

		priceTextField = new JSplitLbTxf("Prezzo", new JFormattedTextField(NumberFormat.getInstance()));

		customerBtnField = new JSplitBtnField("Cliente", "Menù clienti");
		customerBtnField.addActionListener(l -> {
			ObjectPicker<Customer> picker = new ObjectPicker<>(parent, "Scelta Cliente", customers,
					ListSelectionModel.SINGLE_SELECTION);
			picker.addActionListener(ll -> {
				if(!picker.isSelectionEmpty()) {
					selectedCustomer = picker.getSelectedValue();
					picker.dispose();
					customerBtnField.setFieldText(selectedCustomer.getFullName());
				}
			});
			picker.setVisible(true);
		});

		subPeriodComboBox = new JSplitComboBox<>("Periodo");
		for(SubPeriod subPeriod : SubPeriod.class.getEnumConstants()) {
			subPeriodComboBox.addItem(subPeriod);
		}

		datePicker = new JSplitDatePicker("Data inizio");
		discountTextField = new JSplitLbTxf("Sconto applicato", new JFormattedTextField(NumberFormat.getInstance()));

		actionsPanel.add(vatComboBox);
		actionsPanel.add(priceTextField);
		actionsPanel.add(subPeriodComboBox);
		actionsPanel.add(datePicker);
		actionsPanel.add(discountTextField);
		actionsPanel.add(customerBtnField);

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
			LocalDate start = datePicker.getDate();
			SubPeriod subperiod = (SubPeriod) subPeriodComboBox.getSelectedItem();
			VAT vat = vat_DAO.get(vatComboBox.getSelectedItem()).get();
			double discount = Double.parseDouble(discountTextField.getText());
			Subscription subscription = new Subscription(subperiod, start, price, vat, discount, true);
			subscription = subscriptionDAO.insert(subscription).get();

			lbOutput.setText("Abbonamento inserito");
			if(selectedCustomer != null) {
				subscriptionDAO.insertSubscriptionToCustomer(selectedCustomer, subscription, start);
			}
			refreshTable();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), e.getLocalizedMessage(), "Errore di inserimento",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void updateElement() {
		try {
			if (table.getSelectedRow() < 0) return;

			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
			LocalDate start = datePicker.getDate();
			SubPeriod subperiod = (SubPeriod) subPeriodComboBox.getSelectedItem();
			VAT vat = vatComboBox.getSelectedItem();
			Subscription subscription = subscriptionDAO.get(id).get();
			
			subscription.setVat(vat);
			subscription.setEnabled(isEnabled);
			subscription.setDiscount(discount);
			subscription.setStart(start);
			subscription.setPrice(price);
			subscription.setSubPeriod(subperiod);

			if(selectedCustomer != null) {
				if(start != null) {
					subscriptionDAO.insertSubscriptionToCustomer(selectedCustomer, subscription, start);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Dati errati o mancanti: Nessuna data scelta",
					"Errore di aggiornamento",
					JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			lbOutput.setText("Abbonamento aggiornato");
			subscriptionDAO.update(id, subscription);
			refreshTable();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Dati errati o mancanti\n" + e.getLocalizedMessage(),
					"Errore di aggiornamento",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) return;
		lbOutput.setText("Abbonamento rimosso");
		subscriptionDAO.delete(id);
		refreshTable();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) return;
		lbOutput.setText(!isEnabled ? "Abbonamento abilitato" : "Abbonamento disabilitato");
		subscriptionDAO.toggle(id);
		refreshTable();
	}

	@Override
	public void populateTable() {
		isRefreshing = true;
		subscriptionModel.setRowCount(0);

		List<Subscription> subscriptions = subscriptionDAO.getAll();
		if (!subscriptions.isEmpty()) {
			subscriptions.parallelStream()
					.forEach(t -> subscriptionModel.addRow(t.toTableRow()));
		} else {
			lbOutput.setText("Lista abbonamenti vuota");
		}


		isRefreshing = false;
	}

	@Override
	public void clearTxfFields() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
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
				LocalDate start = (LocalDate) values.get("Inizio");
				VAT vat = (VAT) values.get("IVA");
				discount = (double) values.get("Sconto applicato");
				selectedCustomer = (Customer) values.get("Cliente");

				priceTextField.setText(price.toString());
				datePicker.setDate(start);
				vatComboBox.setSelectedItem(vat);
				if(selectedCustomer != null) {
					customerBtnField.setFieldText(selectedCustomer.getFullName());
				}
				discountTextField.setText(discount);
				lbOutput.setText("");

			}
		};
	}

	@Override
	public boolean isDataValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}
}
