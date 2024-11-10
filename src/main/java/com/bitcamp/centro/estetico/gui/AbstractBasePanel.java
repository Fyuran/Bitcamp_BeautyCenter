package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.bitcamp.centro.estetico.gui.render.ButtonTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Model;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Reservation;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.Stock;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.Transaction;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitPanel;

public abstract class AbstractBasePanel<T extends Model> extends JPanel {

	private static final long serialVersionUID = 1712892330024716939L;

	protected static List<Treatment> treatments = new ArrayList<>();
	protected static List<Product> products = new ArrayList<>();
	protected static List<Customer> customers = new ArrayList<>();
	protected static List<Employee> employees = new ArrayList<>();
	protected static List<Prize> prizes = new ArrayList<>();
	protected static List<Subscription> subscriptions = new ArrayList<>();
	protected static List<Transaction> transactions = new ArrayList<>();
	protected static List<UserCredentials> credentials = new ArrayList<>();
	protected static List<VAT> vats = new ArrayList<>();
	protected static List<Reservation> reservations = new ArrayList<>();
	protected static List<Stock> stocks = new ArrayList<>();

	protected final JTable table;
	protected NonEditableTableModel<T> model = new NonEditableTableModel<>();

	private final static User user = MainFrame.getSessionUser();
	private final JTextField txfSearchBar;
	private final JLabel lbTitle;

	// owner
	protected JFrame parent;

	// panels
	private final JPanel menuPanel;
	private final JPanel outputPanel;
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

	AbstractBasePanel(JFrame parent) {
		this.parent = parent;

		setMaximumSize(new Dimension(300, 300));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setName("PLACEHOLDER");
		setSize(1024, 768);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		menuPanel = new JPanel();
		add(menuPanel);
		GridBagLayout gbl_menuPanel = new GridBagLayout();
		gbl_menuPanel.rowHeights = new int[] { 33, 33, 33 };

		menuPanel.setLayout(gbl_menuPanel);

		btnSearch = new JButton("");
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 1;
		menuPanel.add(btnSearch, gbc_btnSearch);
		btnSearch.setRolloverEnabled(true);
		btnSearch.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png"))); // #646464

		btnFilter = new JButton("");
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.fill = GridBagConstraints.VERTICAL;
		gbc_btnFilter.insets = new Insets(0, 0, 0, 0);
		gbc_btnFilter.gridx = 1;
		gbc_btnFilter.gridy = 1;
		menuPanel.add(btnFilter, gbc_btnFilter);
		btnFilter.setRolloverEnabled(true);
		btnFilter.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon_rollOver.png"))); // #646464

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
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(
				new ImageIcon(
						AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		GridBagConstraints gbc_btnInsert = new GridBagConstraints();

		gbc_btnInsert.insets = new Insets(0, 0, 0, 0);
		gbc_btnInsert.gridx = 4;
		gbc_btnInsert.gridy = 1;
		menuPanel.add(btnInsert, gbc_btnInsert);
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert_rollOver.png"))); // #646464

		btnInsert.addActionListener(e -> insertElement());

		btnUpdate = new JButton("");
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(
				new ImageIcon(
						AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();

		gbc_btnUpdate.insets = new Insets(0, 0, 0, 0);
		gbc_btnUpdate.gridx = 5;
		gbc_btnUpdate.gridy = 1;
		menuPanel.add(btnUpdate, gbc_btnUpdate);
		btnUpdate.setRolloverEnabled(true);
		btnUpdate.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update_rollOver.png"))); // #646464
		btnUpdate.addActionListener(e -> updateElement());

		btnDisable = new JButton("");
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		GridBagConstraints gbc_btnDisable = new GridBagConstraints();
		gbc_btnDisable.fill = GridBagConstraints.VERTICAL;

		gbc_btnDisable.insets = new Insets(0, 0, 0, 0);
		gbc_btnDisable.gridx = 6;
		gbc_btnDisable.gridy = 1;
		menuPanel.add(btnDisable, gbc_btnDisable);
		btnDisable.setRolloverEnabled(true);
		btnDisable.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable_rollOver.png"))); // #646464
		btnDisable.addActionListener(e -> disableElement());

		btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh.png")));
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
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh_rollOver.png"))); // #646464
		btnRefresh.addActionListener(e -> refresh());

		btnDelete = new JButton("");
		btnDelete.setIcon(
				new ImageIcon(
						AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
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
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete_rollOver.png")));
		btnDelete.addActionListener(e -> deleteElement());

		lbOutput = new JLabel();
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
		add(outputPanel);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFocusable(false);
		table.getSelectionModel().addListSelectionListener(getTableListSelectionListener());
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		table.setDefaultRenderer(JButton.class, new ButtonTableCellRenderer(table));
		table.setDefaultEditor(JButton.class, new ButtonTableCellRenderer(table));

		outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane(table);
		outputPanel.add(scrollPane);
		// actionsPanel
		// ----------------------------------------------------------------------------------
		actionsPanel = new JSplitPanel();
		add(actionsPanel);

		if (!isAdmin()) {
			btnDelete.setVisible(false);
		}
	}

	void setTitle(String text) {
		lbTitle.setText(text);
	}

	boolean isAdmin() {
		if (user instanceof Employee e) {
			if (e.getRole() == Roles.ADMIN) {
				return true;
			}
		}
		return false;
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

	public void refresh() {
		CompletableFuture.runAsync(() -> {
			clearTxfFields();
			clearTable();
			populateTable();
			table.repaint();
		}).exceptionally(t -> {
			t.printStackTrace();
			return null;
		});
	}

	public void clearTable() {
		lbOutput.setText("");
		model.setRowCount(0);
	}

	public abstract void search();

	public abstract void insertElement();

	public abstract void updateElement();

	public abstract void deleteElement();

	public abstract void disableElement();

	public abstract void populateTable();

	public abstract void clearTxfFields();

	public abstract ListSelectionListener getTableListSelectionListener();

	public abstract boolean isDataValid();
}
