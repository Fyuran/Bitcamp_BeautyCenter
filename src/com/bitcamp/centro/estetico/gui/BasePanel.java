package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.*;
import com.bitcamp.centro.estetico.gui.render.CustomListCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.*;

public abstract class BasePanel<T> extends JPanel {
    enum filters {
		ID, DATE, IS_ENABLED

	}

	private static final long serialVersionUID = 1712892330024716939L;

	static CustomListCellRenderer customListCellRenderer = new CustomListCellRenderer(); 

	JTable table = new JTable();
	static List<Treatment> treatments = TreatmentDAO.getAllTreatments();
	static List<Product> products = ProductDAO.getAllProducts();
	static List<Customer> customers = CustomerDAO.getAllCustomers();
	static List<Employee> employees = EmployeeDAO.getAllEmployees();
	static List<Prize> prizes = PrizeDAO.getAllPrizes();
	static List<Subscription> subscriptions = SubscriptionDAO.getAllSubscriptions();
	static List<Transaction> transactions = TransactionDAO.getAllTransactions();
	static List<UserCredentials> credentials = UserCredentialsDAO.getAllUserCredentials();
	static List<VAT> vats = VATDao.getAllVAT();

	private final static String[] treatmentCol = new String[] { "ID", "Nome trattamento", "Prezzo", "IVA%", "Durata", "Abilitato"};
	private final static String[] productCol = new String[] { "ID", "Prodotto", "Categoria", "Abilitato" };

	private final static String[] customersCol = new String[] {  "ID", "Nome", "Cognome", "Telefono", "Email", "Data di Nascita", "Sesso",
	"Comune di Nascita", "Codice fiscale", "P.IVA", "Codice Ricezione", "Punti Fedeltà", "Abbonamento", "Note", "Abilitato" };

	private final static String[] employeesCol = new String[] { "ID", "Numero seriale", "Nome", "Cognome", "Data di nascita", "Assunzione", "Scadenza",
				"Ruolo", "Username", "Indirizzo", "Città di provenienza", "Mail", "Telefono", "IBAN", "Trattamento",
				"Abilitato" };
	
	private final static String[] prizesCol = new String[] { "ID", "Nome", "Punti Necessari", "Tipo", "Importo", "Abilitato" };
	private final static String[] subscriptionsCol = new String[] { "ID", "Prezzo", "IVA", "Periodo", "Inizio", "Fine", "Sconto applicato", "Cliente", "Abilitata" };

	private final static String[] transactionCol = new String[] { "ID", "€", "Data", "Pagamento", "IVA", "Cliente", "Servizi", "Abilitata" };
	private final static String[] credentialsCol = new String[] { "ID", "Username", "Password", "Address", "Iban", "Phone", "Mail", "Abilitato" };

	private final static String[] vatCol = new String[] { "ID", "%", "Stato", "Abilitato" };

	static NonEditableTableModel treatmentModel = new NonEditableTableModel(treatmentCol, 0);
	static NonEditableTableModel productModel = new NonEditableTableModel(productCol, 0);
	static NonEditableTableModel customerModel = new NonEditableTableModel(customersCol, 0);
	static NonEditableTableModel employeeModel = new NonEditableTableModel(employeesCol, 0);
	static NonEditableTableModel prizeModel = new NonEditableTableModel(prizesCol, 0);
	static NonEditableTableModel subscriptionModel = new NonEditableTableModel(subscriptionsCol, 0);
	static NonEditableTableModel transactionModel = new NonEditableTableModel(transactionCol, 0);
	static NonEditableTableModel credentialsModel = new NonEditableTableModel(credentialsCol, 0);
	static NonEditableTableModel vatModel = new NonEditableTableModel(vatCol, 0);

	static Employee sessionUser = MainFrame.getSessionUser();
    JScrollPane scrollPane;
    SpringLayout springLayoutActions;
    JTextField txfSearchBar;
    JLabel lbTitle;

    //panels
    JPanel menuPanel;
    JPanel outputPanel;
	JPanel actionsPanel;
    
    //buttons
    JButton btnFilter;
    JButton btnSearch;
    JButton btnInsert;
    JButton btnUpdate;
    JButton btnDisable;
    JButton btnRefresh;
    JButton btnDelete;

	// actions
	JLabel lbOutput;
	static boolean isRefreshing = false; // need to sync refreshing with event listener for lists or else it will throw

	BasePanel() {

		lbTitle = new JLabel("PLACEHOLDER");
		setName("PLACEHOLDER");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		add(lbTitle);

		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		menuPanel.setBackground(new Color(255, 255, 255));
		add(menuPanel);

		// Creazione della tabella
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFocusable(false);
		table.getSelectionModel().addListSelectionListener(getListSelectionListener());

		scrollPane = new JScrollPane(table);
		menuPanel.add(scrollPane);

        btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		menuPanel.add(btnSearch);
		btnSearch.setRolloverEnabled(true);
		btnSearch.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png"))); // #646464

		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		menuPanel.add(txfSearchBar);

		btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		menuPanel.add(btnFilter);
		btnFilter.setRolloverEnabled(true);
		btnFilter.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon_rollOver.png"))); // #646464

		btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		menuPanel.add(btnInsert);
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert_rollOver.png"))); // #646464

		btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		menuPanel.add(btnUpdate);
		btnUpdate.setRolloverEnabled(true);
		btnUpdate.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update_rollOver.png"))); // #646464

		btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		menuPanel.add(btnDisable);
		btnDisable.setRolloverEnabled(true);
		btnDisable.setRolloverIcon(
				new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable_rollOver.png"))); // #646464

		outputPanel = new JPanel();
		outputPanel.setLayout(null);
		menuPanel.add(outputPanel);

		btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh.png")));
		btnRefresh.setOpaque(false);
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setBorderPainted(false);
		menuPanel.add(btnRefresh);
		btnRefresh.setRolloverEnabled(true);
		btnRefresh.setRolloverIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh_rollOver.png"))); // #646464

		btnDelete = new JButton("");
		if (!isAdmin()) {
			btnDelete.setVisible(false);
		}
		btnDelete.setIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		menuPanel.add(btnDelete);
		btnDelete.setRolloverEnabled(true);
		btnDelete.setRolloverIcon(new ImageIcon(TransactionPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete_rollOver.png")));

		lbOutput = new JLabel("");
		lbOutput.setForeground(new Color(0, 153, 51));
		lbOutput.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		menuPanel.add(lbOutput);

		// actionsPanel
		// ----------------------------------------------------------------------------------
		actionsPanel = new JPanel();
		btnInsert.addActionListener(e -> insertElement());
		btnUpdate.addActionListener(e -> updateElement());
		btnDisable.addActionListener(e -> disableElement());
		btnDelete.addActionListener(e -> deleteElement());
		btnRefresh.addActionListener(e -> refreshTable());
		add(actionsPanel);

		springLayoutActions = new SpringLayout();
		actionsPanel.setLayout(springLayoutActions);
		actionsPanel.setVisible(true);
    }

    void setTitle(String text) {
        lbTitle.setText(text);    
    }

	boolean isAdmin() {
		return sessionUser.getRole() == Roles.ADMIN;
	}

    abstract void search();

    abstract Optional<T> insertElement();
    abstract int updateElement();
    abstract int deleteElement(); //ADMIN only
    abstract int disableElement(); //renders element not visible by non admins
    abstract void populateTable();
	public void refreshTable() {
		isRefreshing = true;
		populateTable();
		isRefreshing = false;
	}
	public void clearTable(NonEditableTableModel model) {
		model.setRowCount(0);
	}
	abstract void clearTxfFields();
    abstract ListSelectionListener getListSelectionListener();
    abstract boolean isDataValid();
}
