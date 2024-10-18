package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
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
import com.bitcamp.centro.estetico.models.*;
import com.github.lgooddatepicker.components.DatePicker;

public class SubscriptionPanel extends BasePanel<Subscription>{
	private static final long serialVersionUID = 1712892330014716939L;
	// actions
	private static JFormattedTextField priceTextField;
	private static JList<Customer> customersJList = new JList<>();
	private static DatePicker datePicker;
	private static JComboBox<VAT> choiceVAT;
	private static JComboBox<SubPeriod> choiceSubPeriod;
	private static JFormattedTextField discountTextField;
	
	private static DefaultListModel<Customer> customerListModel;

	private static final int _ISENABLEDCOL = 8;

	public SubscriptionPanel() {
		setSize(1024, 768);
		setName("Abbonamenti");
		setLayout(null);
		setTitle("Gestione Abbonamenti");
		menuPanel.setBounds(10, 54, 1004, 593);

		table.setModel(subscriptionModel);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(subscriptionModel, _ISENABLEDCOL));
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) {
					return;
				}
				int row = table.getSelectedRow();
				lbOutput.setText("");
				BigDecimal price = (BigDecimal) subscriptionModel.getValueAt(row, 1); // price
				priceTextField.setText(price.toString());

				LocalDate localDate =  (LocalDate) subscriptionModel.getValueAt(row, 4);
				datePicker.setDate(localDate);

				choiceVAT.setSelectedItem(subscriptionModel.getValueAt(row, 2));
				discountTextField.setText(Double.toString((double) subscriptionModel.getValueAt(row, 6)));

				Customer customer = (Customer) subscriptionModel.getValueAt(row, 7);
				for (Object obj : customerListModel.toArray()) {
					if (obj instanceof Customer c && customer != null) {
						if (c.getFullName().equals(customer.getFullName())) {
							customersJList.setSelectedValue(obj, true);
						}
					}
				}

			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 507);
		menuPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		btnSearch.setBounds(206, 8, 40, 40);
		menuPanel.add(btnSearch);
		btnSearch.setRolloverEnabled(true);
		btnSearch.setRolloverIcon(
				new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png"))); // #646464

		JTextField txfSearchBar;
		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchBar.setBounds(23, 14, 168, 24);
		menuPanel.add(txfSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 40);
		menuPanel.add(btnFilter);
		btnFilter.setRolloverEnabled(true);
		btnFilter.setRolloverIcon(
				new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon_rollOver.png"))); // #646464

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		btnInsert.setBounds(792, 8, 40, 40);
		menuPanel.add(btnInsert);
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(
				new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert_rollOver.png"))); // #646464

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		btnUpdate.setBounds(842, 8, 40, 40);
		menuPanel.add(btnUpdate);
		btnUpdate.setRolloverEnabled(true);
		btnUpdate.setRolloverIcon(
				new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update_rollOver.png"))); // #646464

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		btnDisable.setBounds(892, 8, 40, 40);
		menuPanel.add(btnDisable);
		btnDisable.setRolloverEnabled(true);
		btnDisable.setRolloverIcon(
				new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable_rollOver.png"))); // #646464

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 507);
		menuPanel.add(outputPanel);

		JButton btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh.png")));
		btnRefresh.setOpaque(false);
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setBorderPainted(false);
		btnRefresh.setBounds(942, 8, 40, 40);
		menuPanel.add(btnRefresh);
		btnRefresh.setRolloverEnabled(true);
		btnRefresh.setRolloverIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh_rollOver.png"))); // #646464

		JButton btnDelete = new JButton("");
		if (sessionUser.getRole() != Roles.ADMIN) {
			btnDelete.setVisible(false);
		}
		btnDelete.setIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(742, 8, 40, 40);
		menuPanel.add(btnDelete);
		btnDelete.setRolloverEnabled(true);
		btnDelete.setRolloverIcon(new ImageIcon(SubscriptionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete_rollOver.png")));

		lbOutput = new JLabel("");
		lbOutput.setForeground(new Color(0, 153, 51));
		lbOutput.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lbOutput.setBounds(306, 8, 438, 41);
		menuPanel.add(lbOutput);

		// actionsPanel
		// ----------------------------------------------------------------------------------
		actionsPanel = new JPanel();
		btnInsert.addActionListener(e -> {
			try {
				BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
				LocalDate start = datePicker.getDate();

				SubPeriod subperiod = (SubPeriod) choiceSubPeriod.getSelectedItem();
				VAT vat = VATDao.getVAT(choiceVAT.getSelectedIndex()).get();
				Customer customer = customersJList.getSelectedValue();

				double discount = Double.parseDouble(discountTextField.getText());

				//"ID", "Prezzo", "IVA", "Periodo", "Inizio", "Fine", "Sconto applicato", "Cliente", "Abilitata"
				Subscription subscription = new Subscription(subperiod, start, price, vat, discount, true);
				subscription = SubscriptionDAO.insertSubscription(subscription).get();
				SubscriptionDAO.addSubscriptionToCustomer(customer, subscription, start);
				lbOutput.setText("Abbonamento inserito");
				refreshTable();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Dati errati o mancanti\n" + ex.getLocalizedMessage(), "Errore di inserimento",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		btnUpdate.addActionListener(e -> {
			try {
				int row = table.getSelectedRow();
				if (row == -1) {
					throw new IllegalArgumentException("no row selected");
				}
				final int id = (int) subscriptionModel.getValueAt(row, 0);
				BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
				LocalDate start = datePicker.getDate();

				SubPeriod subperiod = (SubPeriod) choiceSubPeriod.getSelectedItem();
				VAT vat = VATDao.getVAT(choiceVAT.getSelectedIndex()).get();
				Customer customer = customersJList.getSelectedValue();

				double discount = (double) subscriptionModel.getValueAt(row, 6);
				boolean isEnabled = (boolean) subscriptionModel.getValueAt(row, 8);

				Subscription subscription = new Subscription(subperiod, start, price, vat, discount, isEnabled);
				SubscriptionDAO.updateSubscription(id, subscription);
				SubscriptionDAO.addSubscriptionToCustomer(customer, subscription, start);

				lbOutput.setText("Abbonamento aggiornato");
				refreshTable();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Dati errati o mancanti\n"  + ex.getLocalizedMessage(), "Errore di aggiornamento",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		btnDisable.addActionListener(e -> {
			try {//"ID", "Prezzo", "IVA", "Periodo", "Inizio", "Fine", "Sconto applicato", "Cliente", "Abilitata"
				int row = table.getSelectedRow();
				if (row == -1) {
					throw new IllegalArgumentException("no row selected");
				}
				boolean currentFlag = (boolean) subscriptionModel.getValueAt(row, _ISENABLEDCOL);
				subscriptionModel.setValueAt(!currentFlag, row, _ISENABLEDCOL);
				table.repaint();
				Subscription trans = subscriptions.get(row);
				trans.setEnabled(!currentFlag);
				SubscriptionDAO.updateSubscription(trans.getId(), trans);
				lbOutput.setText("Abbonamento disabilitato");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Errori dati da disabilitare\n"  + ex.getLocalizedMessage(), "Errore di disabilitazione",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		btnDelete.addActionListener(e -> {
			try {
				int row = table.getSelectedRow();
				if (row == -1) {
					throw new IllegalArgumentException("no row selected");
				}
				final int id = (int) subscriptionModel.getValueAt(row, 0);
				SubscriptionDAO.deleteSubscription(id);
				lbOutput.setText("Abbonamento cancellato");
				refreshTable();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Impossibile cancellare\n" + ex.getLocalizedMessage(), "Errore di database",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		btnRefresh.addActionListener(e -> refreshTable());
		actionsPanel.setBounds(20, 658, 994, 181);
		add(actionsPanel);
		SpringLayout springLayoutActions = new SpringLayout();
		actionsPanel.setLayout(springLayoutActions);
		actionsPanel.setVisible(true);

		choiceVAT = new JComboBox<>();
		springLayoutActions.putConstraint(SpringLayout.NORTH, choiceVAT, 33, SpringLayout.NORTH, actionsPanel);
		choiceVAT.setEditable(true);
		DefaultComboBoxModel<VAT> vatModel = new DefaultComboBoxModel<>();
		vatModel.addAll(VATDao.getAllVAT());
		choiceVAT.setModel(vatModel);
		choiceVAT.setSelectedIndex(0);

		JLabel lbPrice = new JLabel("Prezzo");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbPrice, 10, SpringLayout.NORTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbPrice, 33, SpringLayout.WEST, actionsPanel);
		actionsPanel.add(lbPrice);

		actionsPanel.add(choiceVAT);
		JLabel lbVAT = new JLabel("IVA");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbVAT, 3, SpringLayout.NORTH, choiceVAT);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbVAT, 0, SpringLayout.WEST, lbPrice);
		lbVAT.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lbVAT);

		priceTextField = new JFormattedTextField(NumberFormat.getInstance());
		springLayoutActions.putConstraint(SpringLayout.WEST, priceTextField, 82, SpringLayout.WEST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, lbPrice, -6, SpringLayout.WEST, priceTextField);
		springLayoutActions.putConstraint(SpringLayout.WEST, choiceVAT, 0, SpringLayout.WEST, priceTextField);
		springLayoutActions.putConstraint(SpringLayout.EAST, choiceVAT, 0, SpringLayout.EAST, priceTextField);
		springLayoutActions.putConstraint(SpringLayout.NORTH, priceTextField, -3, SpringLayout.NORTH, lbPrice);
		priceTextField.setColumns(10);
		actionsPanel.add(priceTextField);

		JScrollPane customerListScrollPane = new JScrollPane();
		springLayoutActions.putConstraint(SpringLayout.WEST, customerListScrollPane, -412, SpringLayout.EAST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.NORTH, customerListScrollPane, 0, SpringLayout.NORTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.SOUTH, customerListScrollPane, -4, SpringLayout.SOUTH,
				actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, customerListScrollPane, -38, SpringLayout.EAST, actionsPanel);
		actionsPanel.add(customerListScrollPane);

		customersJList.setLocale(Locale.getDefault());
		customersJList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		customersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerListScrollPane.setViewportView(customersJList);
		customerListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		customersJList.setModel(customerListModel);
		customersJList.setSelectedIndex(0);
		customersJList.setCellRenderer(customListCellRenderer);

		JLabel lbCustomerChoice = new JLabel("Scelta Cliente");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbCustomerChoice, 81, SpringLayout.NORTH, customerListScrollPane);
		springLayoutActions.putConstraint(SpringLayout.EAST, lbCustomerChoice, -6, SpringLayout.WEST, customerListScrollPane);
		actionsPanel.add(lbCustomerChoice);

		JLabel lbSubPeriod = new JLabel("Periodo");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbSubPeriod, 12, SpringLayout.SOUTH, lbVAT);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbSubPeriod, 0, SpringLayout.WEST, lbPrice);
		lbSubPeriod.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lbSubPeriod);

		choiceSubPeriod = new JComboBox<>();
		DefaultComboBoxModel<SubPeriod> subPeriodModel = new DefaultComboBoxModel<>();
		for(SubPeriod subPeriod : SubPeriod.class.getEnumConstants()) {
			subPeriodModel.addElement(subPeriod);
		}
		choiceSubPeriod.setModel(subPeriodModel);
		springLayoutActions.putConstraint(SpringLayout.NORTH, choiceSubPeriod, 6, SpringLayout.SOUTH, choiceVAT);
		springLayoutActions.putConstraint(SpringLayout.WEST, choiceSubPeriod, 0, SpringLayout.WEST, choiceVAT);
		springLayoutActions.putConstraint(SpringLayout.EAST, choiceSubPeriod, 0, SpringLayout.EAST, choiceVAT);
		actionsPanel.add(choiceSubPeriod);

		JLabel lbStartDate = new JLabel("Inizio");
		springLayoutActions.putConstraint(SpringLayout.WEST, lbStartDate, 0, SpringLayout.WEST, lbPrice);
		lbStartDate.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lbStartDate);

		datePicker = new DatePicker();
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbStartDate, 3, SpringLayout.NORTH, datePicker);
		springLayoutActions.putConstraint(SpringLayout.NORTH, datePicker, 6, SpringLayout.SOUTH, choiceSubPeriod);
		springLayoutActions.putConstraint(SpringLayout.WEST, datePicker, 0, SpringLayout.WEST, choiceVAT);
		actionsPanel.add(datePicker);

		discountTextField = new JFormattedTextField(NumberFormat.getInstance());
		springLayoutActions.putConstraint(SpringLayout.NORTH, discountTextField, 6, SpringLayout.SOUTH, datePicker);
		springLayoutActions.putConstraint(SpringLayout.EAST, discountTextField, 0, SpringLayout.EAST, choiceVAT);
		discountTextField.setColumns(10);
		actionsPanel.add(discountTextField);

		JLabel lbDiscount = new JLabel("Sconto");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbDiscount, 3, SpringLayout.NORTH, discountTextField);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbDiscount, 0, SpringLayout.WEST, lbPrice);
		actionsPanel.add(lbDiscount);
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	Optional<Subscription> insertElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'insertElement'");
	}

	@Override
	int updateElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateElement'");
	}

	@Override
	int deleteElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteElement'");
	}

	@Override
	int disableElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'disableElement'");
	}

	@Override
	void populateTable() {
		subscriptionModel.setRowCount(0);
		customerListModel.clear();

		customers.parallelStream()
		.forEach(c -> customerListModel.addElement(c));

		List<Subscription> subscriptions = SubscriptionDAO.getAllSubscriptions();
		if (!subscriptions.isEmpty()) {
			subscriptions.parallelStream()
			.forEach(t -> subscriptionModel.addRow(t.toTableRow()));
		} else {
			lbOutput.setText("Lista Transazioni vuota");
		}
	}

	@Override
	void clearTxfFields() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}

	@Override
	ListSelectionListener getListSelectionListener() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getListSelectionListener'");
	}

	@Override
	boolean isDataValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}
}
