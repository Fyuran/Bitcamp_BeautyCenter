package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import com.bitcamp.centro.estetico.gui.render.ButtonTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Model;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Reservation;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.Stock;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.Transaction;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.Turn;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.models.UserCredentials;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitPanel;
import com.bitcamp.centro.estetico.utils.SearchBar;

import it.kamaladafrica.codicefiscale.CodiceFiscale;

public abstract class AbstractBasePanel<T extends Model> extends JPanel {

	public static final Comparator<Object> comparator = new Comparator<Object>() {

		@Override
		public int compare(final Object o1, final Object o2) {
			if(o1 instanceof final JButton btn1 && o2 instanceof final JButton btn2) {
				return btn1.getText().compareTo(btn2.getText());
			}
			else if (o1 instanceof final LocalDateTime dt1 && o2 instanceof final LocalDateTime dt2) {
				return dt1.compareTo(dt2);

			} else if (o1 instanceof final LocalDate d1 && o2 instanceof final LocalDate d2) {
				return d1.compareTo(d2);

			} else if (o1 instanceof final Gender g1 && o2 instanceof final Gender g2) {
				return g1.compareTo(g2);

			} else if (o1 instanceof final Customer c1 && o2 instanceof final Customer c2) {
				return c1.getFullName().compareTo(c2.getFullName());
				
			} else if (o1 instanceof final Subscription s1 && o2 instanceof final Subscription s2) {
				return s1.getSubperiod().compareTo(s2.getSubperiod());

			} else if (o1 instanceof final Duration d1 && o2 instanceof final Duration d2) {
				return d1.compareTo(d2);

			} else if (o1 instanceof final CodiceFiscale cf1 && o2 instanceof final CodiceFiscale cf2) {
				return cf1.getValue().compareTo(cf2.getValue());

			} else if (o1 instanceof final Product p1 && o2 instanceof final Product p2) {
				return p1.getName().compareTo(p2.getName());
			}
			
			return o1.toString().compareTo(o2.toString());
		}
		
	};

	protected static final List<Treatment> treatments = new ArrayList<>();
	protected static final List<Product> products = new ArrayList<>();
	protected static final List<Customer> customers = new ArrayList<>();
	protected static final List<Employee> employees = new ArrayList<>();
	protected static final List<Prize> prizes = new ArrayList<>();
	protected static final List<Subscription> subscriptions = new ArrayList<>();
	protected static final List<Transaction> transactions = new ArrayList<>();
	protected static final List<UserCredentials> credentials = new ArrayList<>();
	protected static final List<VAT> vats = new ArrayList<>();
	protected static final List<Reservation> reservations = new ArrayList<>();
	protected static final List<Stock> stocks = new ArrayList<>();
	protected static final List<Turn> turns = new ArrayList<>();

	protected final JTable table;
	protected final NonEditableTableModel<T> model = new NonEditableTableModel<>();
	protected final TableRowSorter<NonEditableTableModel<T>> tableRowSorter = new TableRowSorter<>(model);

	private final static User user = MainFrame.getSessionUser();
	private final JLabel lbTitle;

	// owner
	protected final JFrame parent;

	// panels
	private final JPanel menuPanel;
	private final JPanel outputPanel;
	protected final JSplitPanel actionsPanel;

	// buttons
	protected final SearchBar searchFiltersBar;
	private final JButton btnInsert;
	private final JButton btnUpdate;
	private final JButton btnDisable;
	private final JButton btnRefresh;
	private final JButton btnDelete;

	// actions
	protected final JLabel lbOutput;

	AbstractBasePanel(final JFrame parent) {
		this.parent = parent;

		setMaximumSize(new Dimension(300, 300));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setName("PLACEHOLDER");
		setSize(1024, 768);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		menuPanel = new JPanel();
		add(menuPanel);
		final GridBagLayout gbl_menuPanel = new GridBagLayout();
		gbl_menuPanel.rowHeights = new int[] { 33, 33, 33 };

		menuPanel.setLayout(gbl_menuPanel);

		searchFiltersBar = new SearchBar();
		searchFiltersBar.addSearchBtnActionListener(l -> {
			search();
			searchFiltersBar.clearSearchField();
		});
		searchFiltersBar.addSearchBarKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
					searchFiltersBar.clearSearchField();
				}
			}	
		});
		final GridBagConstraints gbc_searchFiltersBar = new GridBagConstraints();
		gbc_searchFiltersBar.gridx = 0;
		gbc_searchFiltersBar.weightx = 0;
		gbc_searchFiltersBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchFiltersBar.gridwidth = 1;
		gbc_searchFiltersBar.gridy = 1;
		menuPanel.add(searchFiltersBar, gbc_searchFiltersBar);

		GridBagConstraints gbc_Box = new GridBagConstraints();
		gbc_Box.gridx = 1;
		gbc_Box.gridy = 1;
		gbc_Box.weightx = 1;
		gbc_Box.fill = GridBagConstraints.HORIZONTAL;
		menuPanel.add(Box.createHorizontalStrut(10), gbc_Box);

		lbTitle = new JLabel("PLACEHOLDER");
		final GridBagConstraints gbc_lbTitle = new GridBagConstraints();
		gbc_lbTitle.gridwidth = 9;
		gbc_lbTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbTitle.weightx = 0.2;

		gbc_lbTitle.insets = new Insets(0, 0, 0, 0);
		gbc_lbTitle.gridx = 0;
		gbc_lbTitle.gridy = 0;
		menuPanel.add(lbTitle, gbc_lbTitle);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));

		btnInsert = new JButton();
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(
				new ImageIcon(
						AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		final GridBagConstraints gbc_btnInsert = new GridBagConstraints();

		gbc_btnInsert.insets = new Insets(0, 0, 0, 0);
		gbc_btnInsert.gridx = 4;
		gbc_btnInsert.fill = GridBagConstraints.BOTH;
		gbc_btnInsert.gridy = 1;
		menuPanel.add(btnInsert, gbc_btnInsert);
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert_rollOver.png"))); // #646464

		btnInsert.addActionListener(e -> insertElement());

		btnUpdate = new JButton();
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(
				new ImageIcon(
						AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		final GridBagConstraints gbc_btnUpdate = new GridBagConstraints();

		gbc_btnUpdate.insets = new Insets(0, 0, 0, 0);
		gbc_btnUpdate.gridx = 5;
		gbc_btnUpdate.fill = GridBagConstraints.BOTH;
		gbc_btnUpdate.gridy = 1;
		menuPanel.add(btnUpdate, gbc_btnUpdate);
		btnUpdate.setRolloverEnabled(true);
		btnUpdate.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update_rollOver.png"))); // #646464
		btnUpdate.addActionListener(e -> updateElement());

		btnDisable = new JButton();
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
		final GridBagConstraints gbc_btnDisable = new GridBagConstraints();
		gbc_btnDisable.fill = GridBagConstraints.VERTICAL;
		gbc_btnDisable.insets = new Insets(0, 0, 0, 0);
		gbc_btnDisable.fill = GridBagConstraints.BOTH;
		gbc_btnDisable.gridx = 6;
		gbc_btnDisable.gridy = 1;
		menuPanel.add(btnDisable, gbc_btnDisable);
		btnDisable.setRolloverEnabled(true);
		btnDisable.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable_rollOver.png"))); // #646464
		btnDisable.addActionListener(e -> disableElement());

		btnRefresh = new JButton();
		btnRefresh.setIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh.png")));
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setBorderPainted(false);
		final GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.fill = GridBagConstraints.VERTICAL;
		gbc_btnRefresh.insets = new Insets(0, 0, 0, 0);
		gbc_btnRefresh.gridx = 7;
		gbc_btnRefresh.fill = GridBagConstraints.BOTH;
		gbc_btnRefresh.gridy = 1;
		menuPanel.add(btnRefresh, gbc_btnRefresh);
		btnRefresh.setRolloverEnabled(true);
		btnRefresh.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/Refresh_rollOver.png"))); // #646464
		btnRefresh.addActionListener(e -> refresh());

		btnDelete = new JButton();
		btnDelete.setIcon(
				new ImageIcon(
						AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		final GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 0, 0);
		gbc_btnDelete.fill = GridBagConstraints.VERTICAL;
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
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
		final GridBagConstraints gbc_lbOutput = new GridBagConstraints();
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
		table.setRowSorter(tableRowSorter);

		outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

		final JScrollPane scrollPane = new JScrollPane(table);
		outputPanel.add(scrollPane);
		// actionsPanel
		// ----------------------------------------------------------------------------------
		actionsPanel = new JSplitPanel();
		add(actionsPanel);

		if (!isAdmin()) {
			//TODO: manage this
		}
		btnDelete.setVisible(false);
	}

	void setTitle(final String text) {
		lbTitle.setText(text);
	}

	boolean isAdmin() {
		if (user instanceof final Employee e) {
			if (e.getRole() == Roles.ADMIN) {
				return true;
			}
		}
		return false;
	}

	TableColumn[] hideTableColumns(final JTable table, final int... colIndexes) {
		final TableColumnModel model = table.getColumnModel();
		final int columnCount = model.getColumnCount();
		if (columnCount == 0)
			return new TableColumn[0];

		final TableColumn[] columns = new TableColumn[colIndexes.length];
		for (int i = 0; i < colIndexes.length; i++) {
			final TableColumn column = model.getColumn(i);
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

			tableRowSorter.setRowFilter(null);

			for(int i = 0; i < model.getColumnCount(); i++) {
				tableRowSorter.setComparator(i, comparator);
			}
			
			searchFiltersBar.clearComboBox();
			for(String identifier : model.getColumnNames()) {
				searchFiltersBar.addComboBoxItem(identifier);
			}

		}).exceptionally(t -> {
			t.printStackTrace();
			return null;
		});
	}

	public void clearTable() {
		lbOutput.setText("");
		model.setRowCount(0);
	}

	public void search() {
		RowFilter<NonEditableTableModel<T>, Integer> filter = searchFiltersBar.getRowFilter();
		tableRowSorter.setRowFilter(filter);
	};

	public abstract void insertElement();

	public abstract void updateElement();

	public abstract void deleteElement();

	public abstract void disableElement();

	public abstract void populateTable();

	public abstract void clearTxfFields();

	public abstract ListSelectionListener getTableListSelectionListener();

	public abstract boolean isDataValid();
}
