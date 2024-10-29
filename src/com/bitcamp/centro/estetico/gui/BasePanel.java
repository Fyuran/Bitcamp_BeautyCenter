package com.bitcamp.centro.estetico.gui;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.bitcamp.centro.estetico.gui.render.CustomListCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.*;
import com.bitcamp.centro.estetico.utils.JSplitPanel;

public abstract class BasePanel<T extends Model> extends JPanel {

	private static final long serialVersionUID = 1712892330024716939L;

	protected static CustomListCellRenderer customListCellRenderer = new CustomListCellRenderer();

	protected final JTable table;

	protected static List<Treatment> treatments = MainFrame.treatments;
	protected static List<Product> products = MainFrame.products;
	protected static List<Customer> customers = MainFrame.customers;
	protected static List<Employee> employees = MainFrame.employees;
	protected static List<Prize> prizes = MainFrame.prizes;
	protected static List<Subscription> subscriptions = MainFrame.subscriptions;
	protected static List<Transaction> transactions = MainFrame.transactions;
	protected static List<UserCredentials> credentials = MainFrame.credentials;
	protected static List<VAT> vats = MainFrame.vats;

	private final static String[] treatmentCol = new String[] { "ID", "Nome trattamento", "Prezzo", "IVA%", "Durata",
			"Abilitato" };

	private final static String[] productCol = new String[] { "ID", "Prodotto", "Categoria", "Quantità",
			"Quantità minima", "Prezzo", "IVA%", "Abilitato" };

	private final static String[] customersCol = new String[] { "ID", "Genere", "Nome", "Cognome", "Telefono", "Email",
			"Indirizzo",
			"Data di Nascita", "Città natale", "Codice fiscale", "P.IVA", "Codice Ricezione",
			"Punti Fedeltà", "Abbonamento", "Note", "IBAN", "Abilitato" };

	private final static String[] employeesCol = new String[] { "ID", "Genere", "Numero seriale","Codice Fiscale", "Nome", "Cognome",
			"Data di Nascita", "Assunzione", "Scadenza",
			"Ruolo", "Username", "Indirizzo", "Città natale", "Email", "Telefono", "IBAN", "Abilitato" };

	private final static String[] prizesCol = new String[] { "ID", "Nome", "Punti Necessari", "Tipo", "€ in Buono", "Scadenza",
			"Abilitato" };

	private final static String[] subscriptionsCol = new String[] { "ID", "Prezzo", "IVA", "Periodo", "Inizio", "Fine",
			"Sconto applicato", "Cliente", "Abilitato" };

	private final static String[] transactionCol = new String[] { "ID", "Conto", "Data", "Pagamento", "IVA", "Cliente",
			"Servizi", "Abilitato" };
			
	private final static String[] credentialsCol = new String[] { "ID", "Username", "Password", "Address", "Iban",
			"Phone", "Mail", "Abilitato" };

	private final static String[] vatCol = new String[] { "ID", "Percentuale", "Abilitato" };

	protected final static DefaultTableModel treatmentModel = new NonEditableTableModel(treatmentCol, 0);
	protected final static DefaultTableModel productModel = new NonEditableTableModel(productCol, 0);
	protected final static DefaultTableModel customerModel = new NonEditableTableModel(customersCol, 0);
	protected final static DefaultTableModel employeeModel = new NonEditableTableModel(employeesCol, 0);
	protected final static DefaultTableModel prizeModel = new NonEditableTableModel(prizesCol, 0);
	protected final static DefaultTableModel subscriptionModel = new NonEditableTableModel(subscriptionsCol, 0);
	protected final static DefaultTableModel transactionModel = new NonEditableTableModel(transactionCol, 0);
	protected final static DefaultTableModel credentialsModel = new NonEditableTableModel(credentialsCol, 0);
	protected final static DefaultTableModel vatModel = new NonEditableTableModel(vatCol, 0);

	protected final static Employee sessionUser = MainFrame.getSessionUser();
	protected final JTextField txfSearchBar;
	protected final JLabel lbTitle;

	//frame owner
	protected static JFrame parent;


	// panels
	protected final JPanel menuPanel;
	protected final JPanel outputPanel;
	protected final JSplitPanel actionsPanel;

	// buttons
	private final JButton btnFilter;
	private final JButton btnSearch;
	private final JButton btnInsert;
	private final JButton btnUpdate;
	private final JButton btnDisable;
	private final JButton btnRefresh;
	private final JButton btnDelete;

	// actions
	protected final JLabel lbOutput;
	protected static boolean isRefreshing = false; // need to sync refreshing with event listener for lists or else it will throw

	BasePanel() {
		setMaximumSize(new Dimension(300, 300));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setName("PLACEHOLDER");
		setSize(1024, 768);
		setBackground(new Color(255, 255, 255));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		menuPanel = new JPanel();
		menuPanel.setBounds(10, 11, 1007, 49);
		menuPanel.setBackground(Color.LIGHT_GRAY);
		add(menuPanel);
		GridBagLayout gbl_menuPanel = new GridBagLayout();
		gbl_menuPanel.rowHeights = new int[] { 33, 33, 33 };

		menuPanel.setLayout(gbl_menuPanel);

		btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 1;
		menuPanel.add(btnSearch, gbc_btnSearch);
		btnSearch.setRolloverEnabled(true);
		btnSearch.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png"))); // #646464

		btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.fill = GridBagConstraints.VERTICAL;
		gbc_btnFilter.insets = new Insets(0, 0, 0, 0);
		gbc_btnFilter.gridx = 1;
		gbc_btnFilter.gridy = 1;
		menuPanel.add(btnFilter, gbc_btnFilter);
		btnFilter.setRolloverEnabled(true);
		btnFilter.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon_rollOver.png"))); // #646464

		txfSearchBar = new JTextField();
		txfSearchBar.setMinimumSize(new Dimension(20, 20));
		txfSearchBar.setPreferredSize(new Dimension(100, 20));
		txfSearchBar.setColumns(20);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		GridBagConstraints gbc_txfSearchBar = new GridBagConstraints();
		gbc_txfSearchBar.weightx = 1.0;
		gbc_txfSearchBar.anchor = GridBagConstraints.WEST;
		gbc_txfSearchBar.insets = new Insets(0, 0, 0, 0);
		gbc_txfSearchBar.gridx = 2;
		gbc_txfSearchBar.gridy = 1;
		menuPanel.add(txfSearchBar, gbc_txfSearchBar);

		lbTitle = new JLabel("PLACEHOLDER");
		GridBagConstraints gbc_lbTitle = new GridBagConstraints();
		gbc_lbTitle.gridwidth = 9;
		gbc_lbTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbTitle.weightx = 0.2;

		gbc_lbTitle.insets = new Insets(0, 0, 0, 0);
		gbc_lbTitle.gridx = 0;
		gbc_lbTitle.gridy = 0;
		menuPanel.add(lbTitle, gbc_lbTitle);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));

		btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(
				new ImageIcon(BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		GridBagConstraints gbc_btnInsert = new GridBagConstraints();

		gbc_btnInsert.insets = new Insets(0, 0, 0, 0);
		gbc_btnInsert.gridx = 4;
		gbc_btnInsert.gridy = 1;
		menuPanel.add(btnInsert, gbc_btnInsert);
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert_rollOver.png"))); // #646464

		btnInsert.addActionListener(e -> insertElement());

		btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(
				new ImageIcon(BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();

		gbc_btnUpdate.insets = new Insets(0, 0, 0, 0);
		gbc_btnUpdate.gridx = 5;
		gbc_btnUpdate.gridy = 1;
		menuPanel.add(btnUpdate, gbc_btnUpdate);
		btnUpdate.setRolloverEnabled(true);
		btnUpdate.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update_rollOver.png"))); // #646464
		btnUpdate.addActionListener(e -> updateElement());

		btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		GridBagConstraints gbc_btnDisable = new GridBagConstraints();
		gbc_btnDisable.fill = GridBagConstraints.VERTICAL;

		gbc_btnDisable.insets = new Insets(0, 0, 0, 0);
		gbc_btnDisable.gridx = 6;
		gbc_btnDisable.gridy = 1;
		menuPanel.add(btnDisable, gbc_btnDisable);
		btnDisable.setRolloverEnabled(true);
		btnDisable.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable_rollOver.png"))); // #646464
		btnDisable.addActionListener(e -> disableElement());

		btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh.png")));
		btnRefresh.setOpaque(false);
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setBorderPainted(false);
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.fill = GridBagConstraints.VERTICAL;

		gbc_btnRefresh.insets = new Insets(0, 0, 0, 0);
		gbc_btnRefresh.gridx = 7;
		gbc_btnRefresh.gridy = 1;
		menuPanel.add(btnRefresh, gbc_btnRefresh);
		btnRefresh.setRolloverEnabled(true);
		btnRefresh.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh_rollOver.png"))); // #646464
		btnRefresh.addActionListener(e -> refreshTable());

		btnDelete = new JButton("");
		btnDelete.setIcon(
				new ImageIcon(BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 0, 0);
		gbc_btnDelete.fill = GridBagConstraints.VERTICAL;

		gbc_btnDelete.gridx = 8;
		gbc_btnDelete.gridy = 1;
		menuPanel.add(btnDelete, gbc_btnDelete);
		btnDelete.setRolloverEnabled(true);
		btnDelete.setRolloverIcon(new ImageIcon(
				BasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete_rollOver.png")));
		btnDelete.addActionListener(e -> deleteElement());

		lbOutput = new JLabel();
		lbOutput.setForeground(new Color(0, 204, 51));
		lbOutput.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbOutput = new GridBagConstraints();
		gbc_lbOutput.gridwidth = 9;
		gbc_lbOutput.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbOutput.insets = new Insets(0, 0, 0, 0);
		gbc_lbOutput.gridx = 0;
		gbc_lbOutput.gridy = 2;
		menuPanel.add(lbOutput, gbc_lbOutput);

		outputPanel = new JPanel();
		outputPanel.setPreferredSize(new Dimension(800, 800));
		outputPanel.setBackground(Color.WHITE);
		add(outputPanel);
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setBounds(new Rectangle(0, 0, 1024, 0));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFocusable(false);
		table.getSelectionModel().addListSelectionListener(getTableListSelectionListener());
		outputPanel.setBounds(10, 70, 1007, 497);
		outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane(table);
		outputPanel.add(scrollPane);
		// actionsPanel
		// ----------------------------------------------------------------------------------
		actionsPanel = new JSplitPanel();
		actionsPanel.setBounds(10, 578, 1007, 181);
		add(actionsPanel);

		if (!isAdmin()) {
			btnDelete.setVisible(false);
		}
	}

	void setTitle(String text) {
		lbTitle.setText(text);
	}

	boolean isAdmin() {
		return sessionUser.getRole() == Roles.ADMIN;
	}

	TableColumn[] hideTableColumns(JTable table, int... colIndexes) {
		TableColumnModel model = table.getColumnModel();
		int columnCount = model.getColumnCount();
		if (columnCount == 0)
			return new TableColumn[0];

		TableColumn[] columns = new TableColumn[colIndexes.length];
		for (int i = 0; i < colIndexes.length; i++) {
			TableColumn column = model.getColumn(i);
			columns[i] = column;
			table.removeColumn(column);
		}

		return columns;
	}

	public void refreshTable() {
		CompletableFuture.runAsync(() -> populateTable());
	}

	public void clearTableModel(DefaultTableModel model) {
		model.setRowCount(0);
	}

	public void clearTable(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
	}

	public HashMap<String, Object> getRowMap(JTable table, DefaultTableModel model) {
		HashMap<String, Object> hash = new HashMap<>();
		if (model.getRowCount() <= 0)
			return hash;

		var vector = model.getDataVector().get(table.getSelectedRow());
		int columnCount = model.getColumnCount();
		if (columnCount != vector.size()) {
			throw new IllegalArgumentException("Column count is different from values in vector");
		}

		for (int i = 0; i < model.getColumnCount(); i++) {
			hash.put(model.getColumnName(i), vector.get(i));
		}

		return hash;
	}

	abstract void search();
    abstract void insertElement();
    abstract void updateElement();
    abstract void deleteElement();
    abstract void disableElement();
    abstract void populateTable();
    abstract void clearTxfFields();
    abstract ListSelectionListener getTableListSelectionListener();
    abstract boolean isDataValid();
}
