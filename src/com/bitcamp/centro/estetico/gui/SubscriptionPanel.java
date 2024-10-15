package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.SubscriptionDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.*;
import com.github.lgooddatepicker.components.DatePicker;

public class SubscriptionPanel extends JPanel{
	private static class NonEditableTableModel extends DefaultTableModel { // non editable model for tables
		private static final long serialVersionUID = 746772300141997929L;

		public NonEditableTableModel(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;
		}
	}

	private static class RowIsEnabledCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = -5727490185915218173L;

		@Override
		protected void setValue(Object value) {
			if (value == null) {
				setText("");
			} else {
				String s = value.toString();
				if (s.equalsIgnoreCase("true")) {
					setText("Si");
				} else if (s.equalsIgnoreCase("false")) {
					setText("No");
				} else {
					setText(s);
				}

				if (value instanceof Customer c) {
					setText(c.getFullName());
				}
			}
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			boolean isEnabled = (boolean) model.getValueAt(row, 8);

			if (!isEnabled) {// if isEnabled
				setBackground(Color.LIGHT_GRAY);
			} else {
				setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
			}
			if (isSelected) {
				setBorder(new LineBorder(new Color(0, 56, 98), 1));
			} else {
				setBorder(new EmptyBorder(1, 1, 1, 1));
			}
			setFont(table.getFont());
			setValue(value);
			return this;
		}

	}

	// customer cell render for list overriding default toString() call when
	// rendering cells
	private static class CustomerListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = -1600461002451054914L;

		private CustomerListCellRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Customer customer = (Customer) value;
			setText(customer.getFullName() + " " + customer.getEU_TIN().getValue());

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			return this;
		}
	}

	private enum filters {
		ID, DATE, IS_ENABLED

	}

	void fillRows() {
		isRefreshing = true;
		model.setRowCount(0);
		customerListModel.clear();
		CustomerDAO.getAllCustomers().forEach(c -> customerListModel.addElement(c));
		List<Object[]> data = SubscriptionDAO.toTableRowAll();
		if (!data.isEmpty()) {
			for (Object[] row : data) {
				model.addRow(row);
			}
		} else {
			lbOutput.setText("Lista Abbonamenti vuota");
		}
		isRefreshing = false;
	}

	private static final long serialVersionUID = 1712892330014716939L;

	private static NonEditableTableModel model;
	private static DefaultListModel<Customer> customerListModel = new DefaultListModel<>();
	private static CustomerListCellRenderer ccr = new CustomerListCellRenderer();

	private static JTable table = new JTable();
	private static JFrame parent = MainFrame.getMainFrame();
	private static Employee sessionUser = MainFrame.getSessionUser();

	private static List<Subscription> subscriptions = SubscriptionDAO.getAllSubscriptions();

	// actions
	private static JPanel actionsPanel;
	private static JFormattedTextField priceTextField;
	private static JList<Customer> customersJList = new JList<>();
	private static DatePicker datePicker;
	private static JComboBox<VAT> choiceVAT;
	private static JLabel lbOutput;
	private static boolean isRefreshing = false; // need to sync refreshing with event listener for lists or else it will throw
	private static JComboBox<SubPeriod> choiceSubPeriod;
	private static JFormattedTextField discountTextField;

	public SubscriptionPanel() {
		parent.setSize(1040, 900);
		setName("Abbonamenti");
		setLayout(null);
		setSize(1040, 900);
		parent.setResizable(false); // user resizable

		JLabel titleTab = new JLabel("GESTIONE ABBONAMENTI");
		titleTab.setHorizontalAlignment(SwingConstants.CENTER);
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(10, 11, 1004, 32);
		add(titleTab);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		menuPanel.setBackground(new Color(255, 255, 255));
		menuPanel.setBounds(10, 54, 1004, 593);
		add(menuPanel);

		// Modello della tabella con colonne
		model = new NonEditableTableModel( //id, price, vat, subperiod, start, end, discount, isEnabled
				new String[] { "ID", "Prezzo", "IVA", "Periodo", "Inizio", "Fine", "Sconto applicato", "Cliente", "Abilitata" }, 0);

		// Creazione della tabella
		table.setModel(model);
		table.setDefaultRenderer(Object.class, new RowIsEnabledCellRenderer());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFocusable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) {
					return;
				}
				int row = table.getSelectedRow();
				lbOutput.setText("");
				BigDecimal price = (BigDecimal) model.getValueAt(row, 1); // price
				priceTextField.setText(price.toString());

				LocalDate localDate =  (LocalDate) model.getValueAt(row, 4);
				datePicker.setDate(localDate);

				choiceVAT.setSelectedItem(model.getValueAt(row, 2));
				discountTextField.setText(Double.toString((double) model.getValueAt(row, 6)));

				Customer customer = (Customer) model.getValueAt(row, 7);
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
		lbOutput.setFont(new Font("Tahoma", Font.BOLD, 16));
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
				fillRows();
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
				final int id = (int) model.getValueAt(row, 0);
				BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceTextField.getText()));
				LocalDate start = datePicker.getDate();

				SubPeriod subperiod = (SubPeriod) choiceSubPeriod.getSelectedItem();
				VAT vat = VATDao.getVAT(choiceVAT.getSelectedIndex()).get();
				Customer customer = customersJList.getSelectedValue();

				double discount = (double) model.getValueAt(row, 6);
				boolean isEnabled = (boolean) model.getValueAt(row, 8);

				Subscription subscription = new Subscription(subperiod, start, price, vat, discount, isEnabled);
				SubscriptionDAO.updateSubscription(id, subscription);
				SubscriptionDAO.addSubscriptionToCustomer(customer, subscription, start);

				lbOutput.setText("Abbonamento aggiornato");
				fillRows();
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
				int col = 8;
				boolean currentFlag = (boolean) model.getValueAt(row, col);
				table.setValueAt(!currentFlag, row, col);
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
				final int id = (int) model.getValueAt(row, 0);
				SubscriptionDAO.deleteSubscription(id);
				lbOutput.setText("Abbonamento cancellato");
				fillRows();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Impossibile cancellare\n" + ex.getLocalizedMessage(), "Errore di database",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		btnRefresh.addActionListener(e -> fillRows());
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
		customersJList.setCellRenderer(ccr);

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

		fillRows();
	}
}
