package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.ProductCat;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtSearchBar;
	private JTextField txtName;
	private JTextField txtMinStock;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;
	private JTextField txtPrice;
	private JLabel msgLbl;
	private JComboBox<String> ivaComboBox;
	private JComboBox<String> categoryComboBox;
	private int selectedId;
	private JTextField txtAmount;

	/**
	 * Create the panel.
	 */
	public ProductPanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("Prodotti");
		JLabel titleTab = new JLabel("GESTIONE PRODOTTI");
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 206, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "ID", "Prodotto", "Categoria", "Quantità", "Quantità minima", "Prezzo", "IVA%" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(tableModel);
		// Listener della tabella per pescare i nomi che servono
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						selectedId = Integer.parseInt(String.valueOf(table.getValueAt(selectedRow, 0)));
						String name = String.valueOf(table.getValueAt(selectedRow, 1));
						String category = String.valueOf(String.valueOf(table.getValueAt(selectedRow, 2)));
						String amount=String.valueOf(table.getValueAt(selectedRow, 3));
						String minStock = String.valueOf(table.getValueAt(selectedRow, 4));
						String price = String.valueOf(table.getValueAt(selectedRow, 5));
						String vatString = String.valueOf(table.getValueAt(selectedRow, 6) + "%");
						System.out.println(vatString);
						// double vat=Double.parseDouble(vatString.substring(0,vatString.length()-1));

						// Il listener ascolta la riga selezionata e la usa per popolare i campi
						txtName.setText(name);
						categoryComboBox.setSelectedItem(category);
						txtMinStock.setText(minStock);
						txtPrice.setText(price);
						ivaComboBox.setSelectedItem(vatString);
						txtAmount.setText(amount);
						table.clearSelection();

					}
				}
			}
		});

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/searchIcon.png")));
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);

		txtSearchBar = new JTextField();
		txtSearchBar.setColumns(10);
		txtSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txtSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txtSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		btnFilter.addActionListener(e -> populateTableByFilter());
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setBounds(720, 8, 40, 30);
		btnInsert.addActionListener(e -> createProduct());
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setBounds(770, 8, 40, 30);
		btnUpdate.addActionListener(e->updateProduct());
		containerPanel.add(btnUpdate);

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/delete.png")));
		btnDelete.setBounds(820, 8, 40, 30);
		btnDelete.addActionListener(e->deleteProduct());
		containerPanel.add(btnDelete);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/disable.png")));
		btnDisable.setBounds(920, 8, 40, 30);
		containerPanel.add(btnDisable);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 276);
		containerPanel.add(outputPanel);

		JButton btnHystorical = new JButton("");
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/cartellina.png")));
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome prodotto:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(209, 436, 220, 20);
		add(txtName);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblCategory.setBounds(43, 474, 170, 17);
		add(lblCategory);

		JLabel lblMinStock = new JLabel("Quantità minima:");
		lblMinStock.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblMinStock.setBounds(43, 513, 170, 14);
		add(lblMinStock);

		txtMinStock = new JTextField();
		txtMinStock.setColumns(10);
		txtMinStock.setBounds(209, 512, 220, 20);
		add(txtMinStock);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(749, 432, 220, 20);
		add(txtPrice);

		JLabel lblIVA = new JLabel("IVA:");
		lblIVA.setBounds(531, 475, 170, 14);
		add(lblIVA);

		JLabel lblPrice = new JLabel("Prezzo:");
		lblPrice.setBounds(531, 437, 170, 14);
		add(lblPrice);

		categoryComboBox = new JComboBox<>();
		for (ProductCat cat : ProductCat.values()) {
			categoryComboBox.addItem(cat.getDescription());
		}
		categoryComboBox.setBounds(209, 468, 220, 27);
		add(categoryComboBox);

		List<VAT> ivas = VATDao.getAllVAT();
		int i = 0;
		String[] ivasToString = new String[ivas.size()];
		for (VAT iva : ivas) {
			ivasToString[i] = iva.toString();
			i++;
		}
		ivaComboBox = new JComboBox<>(ivasToString);
		ivaComboBox.setBounds(749, 471, 220, 27);
		add(ivaComboBox);

		msgLbl = new JLabel("");
		msgLbl.setBounds(389, 606, 625, 16);
		add(msgLbl);

		JLabel amountLbl = new JLabel("Quantità:");
		amountLbl.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		amountLbl.setBounds(531, 514, 170, 14);
		add(amountLbl);

		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBounds(749, 511, 220, 20);
		add(txtAmount);
		populateTable();

	}

	void populateTable() {
		clearTable();
		List<Product> products = ProductDAO.getAllProducts();
		if (products.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Product p : products) {
			tableModel.addRow(new String[] { String.valueOf(p.getId()), p.getName(), p.getType().getDescription(),
					String.valueOf(p.getAmount()), String.valueOf(p.getMinStock()), String.valueOf(p.getPrice()),
					String.valueOf(p.getVat()) });
			// {"Prodotto","Categoria","Quantità","Quantità minima","Prezzo","IVA%"}
		}
	}

	private void clearTable() {
		tableModel.getDataVector().removeAllElements();
		revalidate();
	}

	private void createProduct() {
		System.out.println(isDataValid(true));
		if (isDataValid(true)) {
			String name = txtName.getText();
			int minStock = Integer.parseInt(txtMinStock.getText());
			BigDecimal price = new BigDecimal(txtPrice.getText());
			String vatString = ivaComboBox.getSelectedItem().toString();
			double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
			VAT vat = VATDao.getVATByAmount((float) vatAmount).get();
			ProductCat type = ProductCat.fromDescription(categoryComboBox.getSelectedItem().toString());
			// Product(String name, int amount, int minStock, BigDecimal price, VAT vat,
			// ProductCat type)
			Product product = new Product(name, 0, minStock, price, vat, type);
			product = ProductDAO.insertProduct(product).get();
			System.out.println(product);
			msgLbl.setText(product.getName() + " inserito nel database");
			populateTable();

			// Product product=new Product(txtName.getText(),0,)
			// (nameText., int amount, int minStock, BigDecimal price, double vat,
			// ProductCat type,
			// boolean isEnabled)
		}

	}

	private boolean isDataValid(boolean mustNameBeUnique) {
		msgLbl.setText("");
		if (mustNameBeUnique) {
			if (!Product.isNameUnique(txtName.getText())) {
				msgLbl.setText("Prodotto già esistente nel database");
				return false;
			}
		}
		try {
			inputValidator.validateName(txtName.getText());
		} catch (inputValidatorException e) {
			msgLbl.setText(e.getMessage());
			e.printStackTrace();
			return false;
		}
		String minStockText = txtMinStock.getText();
		System.out.println(minStockText);
		if (minStockText.isEmpty()) {
			msgLbl.setText("La quantità minima non può essere vuota");
			return false;
		}

		try {
			int minStock = Integer.parseInt(txtMinStock.getText());
			if (minStock <= 0) {
				msgLbl.setText("La quantità minima deve essere un numero valido");
				System.out.println("try");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			msgLbl.setText("La quantità minima deve essere un numero valido");
			System.out.println("catch");
			return false;
		}
		String priceText = txtPrice.getText().trim();
		if (priceText.isEmpty()) {
			msgLbl.setText("Il prezzo non può essere vuoto");
			return false;
		}
		try {
			double price = Double.parseDouble(txtPrice.getText());
			if (price <= 0) {
				msgLbl.setText("Il prezzo deve essere un numero valido");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Prezzo valido");

		return true;
	}

	private void populateTableByFilter() {
		msgLbl.setText("");
		if (txtSearchBar.getText().isBlank() || txtSearchBar.getText().isEmpty()) {
			msgLbl.setText("Inserire un filtro!");
			return;
		}
		clearTable();
		List<Product> products = ProductDAO.getAllProducts();
		if (products.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Product p : products) {
			if (p.isEnabled() && p.getName().toLowerCase().contains(txtSearchBar.getText().toLowerCase())) {
				tableModel.addRow(new String[] { String.valueOf(p.getId()), p.getName(), p.getType().getDescription(),
						String.valueOf(p.getAmount()), String.valueOf(p.getMinStock()), String.valueOf(p.getPrice()),
						String.valueOf(p.getVat()) });
			}
			// {"id","Prodotto","Categoria","Quantità","Quantità minima","Prezzo","IVA%"}
		}
		txtSearchBar.setText("");
	}

	private void updateProduct() {
		if (isDataValid(false)) {
			String name = txtName.getText();
			int minStock = Integer.parseInt(txtMinStock.getText());
			BigDecimal price = new BigDecimal(txtPrice.getText());
			String vatString = ivaComboBox.getSelectedItem().toString();
			double vatDouble = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
			VAT vat=VATDao.getVATByAmount(vatDouble).get();
			ProductCat type = ProductCat.fromDescription(categoryComboBox.getSelectedItem().toString());
			int amount=Integer.parseInt(txtAmount.getText());
			Product product=new Product(name, amount,minStock,price,vat,type);
			//(String name, int amount, int minStock, BigDecimal price, VAT vat, ProductCat type)
			ProductDAO.updateData(selectedId,product);
			msgLbl.setText(product.getName() + " modificato correttamente");
			clearFields();
			populateTable();

		}

	}
	private void deleteProduct() {
		msgLbl.setText("");
		ProductDAO.toggleEnabledProduct(selectedId);
		msgLbl.setText("Trattamento rimosso correttamente");
		populateTable();
	}

	private void clearFields() {
		txtName.setText("");
		txtMinStock.setText("");
		txtPrice.setText("");
		txtAmount.setText("");
	}

	public JTextField gettxtSearchBar() {
		return txtSearchBar;
	}

	public void settxtSearchBar(JTextField txtSearchBar) {
		this.txtSearchBar = txtSearchBar;
	}

	public JTextField getTxtName() {
		return txtName;
	}

	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}

	public JTextField getTxtMinStock() {
		return txtMinStock;
	}

	public void setTxtMinStock(JTextField txtMinStock) {
		this.txtMinStock = txtMinStock;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTextField getTxtPrice() {
		return txtPrice;
	}

	public void setTxtPrice(JTextField txtPrice) {
		this.txtPrice = txtPrice;
	}
}
