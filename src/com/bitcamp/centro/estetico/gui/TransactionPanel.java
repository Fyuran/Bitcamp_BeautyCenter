package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.TransactionDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.*;
import com.github.lgooddatepicker.components.DateTimePicker;

public class TransactionPanel extends JPanel {

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

			boolean isEnabled = (boolean) model.getValueAt(row, 7);

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
		List<Object[]> data = TransactionDAO.toTableRowAll();
		if (!data.isEmpty()) {
			for (Object[] row : data) {
				model.addRow(row);
			}
		} else {
			lbOutput.setText("Lista Transazioni vuota");
		}
		isRefreshing = false;
	}

	private static final long serialVersionUID = 1712892330024716939L;

	private static NonEditableTableModel model;
	private static DefaultListModel<Customer> customerListModel = new DefaultListModel<>();
	private static CustomerListCellRenderer ccr = new CustomerListCellRenderer();

	private static JTable table = new JTable();
	private static JFrame parent = MainFrame.getMainFrame();
	private static Employee sessionUser = MainFrame.getSessionUser();

	private static List<Transaction> transactions = TransactionDAO.getAllTransactions();

	// actions
	private static JPanel actionsPanel;
	private static JFormattedTextField priceTextField;
	private static JComboBox<PayMethod> choicePaymentMethod;
	private static JComboBox<VAT> choiceVAT;
	private static JTextArea servicesTextArea;
	private static JList<Customer> customersJList = new JList<>();
	private static DateTimePicker dateTimePicker;
	private static JLabel lbOutput;
	private boolean isRefreshing = false; // need to sync refreshing with event listener for lists or else it will throw

	public TransactionPanel() {
		parent.setSize(1040, 900);
		setLayout(null);
		setName("Transazioni");
		setSize(1040, 900);
		parent.setResizable(false); // user resizable

		JLabel titleTab = new JLabel("GESTIONE TRANSAZIONI");
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
		model = new NonEditableTableModel(
				new String[] { "ID", "€", "Data", "Pagamento", "IVA", "Cliente", "Servizi", "Abilitata" }, 0);

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

				DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
				LocalDateTime dateTime = LocalDateTime.parse((String) model.getValueAt(row, 2), dtf);
				dateTimePicker.setDateTimePermissive(dateTime);

				choicePaymentMethod.setSelectedItem(model.getValueAt(row, 3));

				choiceVAT.setSelectedItem(model.getValueAt(row, 4));

				Customer customer = (Customer) model.getValueAt(row, 5);
				for (Object obj : customerListModel.toArray()) {
					if (obj instanceof Customer c) {
						if (c.getFullName().equals(customer.getFullName())) {
							customersJList.setSelectedValue(obj, true);
						}
					}
				}

				String services = (String) model.getValueAt(row, 6);
				servicesTextArea.setText(services);

			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 507);
		menuPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		btnSearch.setBounds(206, 8, 40, 40);
		menuPanel.add(btnSearch);
		btnSearch.setRolloverEnabled(true);
		btnSearch.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png"))); // #646464

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
		btnFilter.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 40);
		menuPanel.add(btnFilter);
		btnFilter.setRolloverEnabled(true);
		btnFilter.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon_rollOver.png"))); // #646464

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		btnInsert.setBounds(792, 8, 40, 40);
		menuPanel.add(btnInsert);
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert_rollOver.png"))); // #646464

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		btnUpdate.setBounds(842, 8, 40, 40);
		menuPanel.add(btnUpdate);
		btnUpdate.setRolloverEnabled(true);
		btnUpdate.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update_rollOver.png"))); // #646464

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		btnDisable.setBounds(892, 8, 40, 40);
		menuPanel.add(btnDisable);
		btnDisable.setRolloverEnabled(true);
		btnDisable.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable_rollOver.png"))); // #646464

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 507);
		menuPanel.add(outputPanel);

		JButton btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh.png")));
		btnRefresh.setOpaque(false);
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setBorderPainted(false);
		btnRefresh.setBounds(942, 8, 40, 40);
		menuPanel.add(btnRefresh);
		btnRefresh.setRolloverEnabled(true);
		btnRefresh.setRolloverIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh_rollOver.png"))); // #646464

		JButton btnDelete = new JButton("");
		if (sessionUser.getRole() != Roles.ADMIN) {
			btnDelete.setVisible(false);
		}
		btnDelete.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(742, 8, 40, 40);
		menuPanel.add(btnDelete);
		btnDelete.setRolloverEnabled(true);
		btnDelete.setRolloverIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete_rollOver.png")));

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
				LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
				PayMethod paymentMethod = (PayMethod) choicePaymentMethod.getSelectedItem();
				VAT vat = (VAT) choiceVAT.getSelectedItem();
				Customer customer = customersJList.getSelectedValue();
				String services = servicesTextArea.getText();

				TransactionDAO.insertTransaction(new Transaction(price, paymentMethod, dateTime, customer, vat,
						BeautyCenterDAO.getFirstBeautyCenter().get(), services));
				lbOutput.setText("Transazione inserita");
				fillRows();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Dati errati o mancanti", "Errore di inserimento",
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
				LocalDateTime dateTime = dateTimePicker.getDateTimePermissive();
				PayMethod paymentMethod = (PayMethod) choicePaymentMethod.getSelectedItem();
				VAT vat = (VAT) choiceVAT.getSelectedItem();
				Customer customer = customersJList.getSelectedValue();
				String services = servicesTextArea.getText();

				TransactionDAO.updateTransaction(id, new Transaction(price, paymentMethod, dateTime, customer, vat,
						BeautyCenterDAO.getFirstBeautyCenter().get(), services));
				lbOutput.setText("Transazione aggiornata");
				fillRows();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Dati errati o mancanti", "Errore di aggiornamento",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		btnDisable.addActionListener(e -> {
			try {
				int row = table.getSelectedRow();
				if (row == -1) {
					throw new IllegalArgumentException("no row selected");
				}
				int col = 7;
				boolean currentFlag = (boolean) model.getValueAt(row, col);
				table.setValueAt(!currentFlag, row, col);
				table.repaint();
				Transaction trans = transactions.get(row);
				trans.setEnabled(!currentFlag);
				TransactionDAO.updateTransaction(trans.getId(), trans);
				lbOutput.setText(trans.isEnabled() ? "Transazione abilitata" : "Transazione disabilitata");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Errori dati da disabilitare", "Errore di disabilitazione",
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
				TransactionDAO.deleteTransaction(id);
				lbOutput.setText("Transazione cancellata");
				fillRows();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Impossibile cancellare", "Errore di database",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		btnRefresh.addActionListener(e -> {
			lbOutput.setText("Lista Transazioni aggiornata");
			fillRows();
		});
		actionsPanel.setBounds(20, 658, 994, 181);
		add(actionsPanel);
		SpringLayout springLayoutActions = new SpringLayout();
		actionsPanel.setLayout(springLayoutActions);
		actionsPanel.setVisible(true);

		JLabel lbPrice = new JLabel("€");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbPrice, 16, SpringLayout.NORTH, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbPrice, 10, SpringLayout.WEST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, lbPrice, -960, SpringLayout.EAST, actionsPanel);
		actionsPanel.add(lbPrice);

		JLabel lbDate = new JLabel("Data e Orario");
		springLayoutActions.putConstraint(SpringLayout.WEST, lbDate, 10, SpringLayout.WEST, actionsPanel);
		actionsPanel.add(lbDate);

		choicePaymentMethod = new JComboBox<>();
		choicePaymentMethod.setEditable(true);
		springLayoutActions.putConstraint(SpringLayout.NORTH, choicePaymentMethod, 85, SpringLayout.NORTH,
				actionsPanel);
		PayMethod[] payMethods = PayMethod.class.getEnumConstants();
		DefaultComboBoxModel<PayMethod> paymentModel = new DefaultComboBoxModel<>();
		for(PayMethod method : payMethods) {
			paymentModel.addElement(method);
		}
		choicePaymentMethod.setModel(paymentModel);
		actionsPanel.add(choicePaymentMethod);

		JLabel lblMetodoDiPagamento = new JLabel("<html><div>Metodo di Pagamento</div></html>");
		springLayoutActions.putConstraint(SpringLayout.NORTH, lblMetodoDiPagamento, 17, SpringLayout.SOUTH, lbDate);
		springLayoutActions.putConstraint(SpringLayout.WEST, choicePaymentMethod, 6, SpringLayout.EAST,
				lblMetodoDiPagamento);
		springLayoutActions.putConstraint(SpringLayout.WEST, lblMetodoDiPagamento, 10, SpringLayout.WEST, actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.EAST, lblMetodoDiPagamento, -908, SpringLayout.EAST,
				actionsPanel);
		lblMetodoDiPagamento.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lblMetodoDiPagamento);

		priceTextField = new JFormattedTextField(NumberFormat.getInstance());
		springLayoutActions.putConstraint(SpringLayout.NORTH, priceTextField, -5, SpringLayout.NORTH, lbPrice);
		springLayoutActions.putConstraint(SpringLayout.WEST, priceTextField, 58, SpringLayout.EAST, lbPrice);
		springLayoutActions.putConstraint(SpringLayout.SOUTH, priceTextField, 6, SpringLayout.SOUTH, lbPrice);
		priceTextField.setColumns(10);
		actionsPanel.add(priceTextField);

		choiceVAT = new JComboBox<>();
		springLayoutActions.putConstraint(SpringLayout.NORTH, choiceVAT, 6, SpringLayout.SOUTH, choicePaymentMethod);
		choiceVAT.setEditable(true);
		DefaultComboBoxModel<VAT> vatModel = new DefaultComboBoxModel<>();
		vatModel.addAll(VATDao.getAllVAT());
		choiceVAT.setModel(vatModel);
		choiceVAT.setSelectedIndex(0);

		choicePaymentMethod.setModel(paymentModel);
		actionsPanel.add(choiceVAT);

		JLabel lbVAT = new JLabel("IVA");
		springLayoutActions.putConstraint(SpringLayout.WEST, choiceVAT, 65, SpringLayout.EAST, lbVAT);
		springLayoutActions.putConstraint(SpringLayout.NORTH, lbVAT, 5, SpringLayout.SOUTH, lblMetodoDiPagamento);
		springLayoutActions.putConstraint(SpringLayout.WEST, lbVAT, 0, SpringLayout.WEST, lbPrice);
		lbVAT.setHorizontalAlignment(SwingConstants.CENTER);
		actionsPanel.add(lbVAT);

		JScrollPane customerListScrollPane = new JScrollPane();
		springLayoutActions.putConstraint(SpringLayout.EAST, choiceVAT, -217, SpringLayout.WEST, customerListScrollPane);
		springLayoutActions.putConstraint(SpringLayout.EAST, priceTextField, -217, SpringLayout.WEST, customerListScrollPane);
		springLayoutActions.putConstraint(SpringLayout.NORTH, customerListScrollPane, 0, SpringLayout.NORTH,
				actionsPanel);
		springLayoutActions.putConstraint(SpringLayout.SOUTH, customerListScrollPane, -4, SpringLayout.SOUTH,
				actionsPanel);
		actionsPanel.add(customerListScrollPane);

		springLayoutActions.putConstraint(SpringLayout.SOUTH, customersJList, 0, SpringLayout.SOUTH,
				lblMetodoDiPagamento);
		customersJList.setLocale(Locale.getDefault());
		customersJList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		customersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerListScrollPane.setViewportView(customersJList);
		customerListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		customersJList.setModel(customerListModel);
		customersJList.setSelectedIndex(0);
		customersJList.setCellRenderer(ccr);

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
		springLayoutActions.putConstraint(SpringLayout.EAST, choicePaymentMethod, -150, SpringLayout.WEST,
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

		fillRows();
	}
}
