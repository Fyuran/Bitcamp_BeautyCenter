package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.ProductCat;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class ProductPanel extends BasePanel<Product> {

	private static final long serialVersionUID = 1L;
	private JTextField txfName;
	private JTextField txfMinStock;
	private JTextField txfPrice;
	private JComboBox<String> VATComboBox;
	private JComboBox<String> categoryComboBox;
	private int selectedId = -1;
	private JTextField txfAmount;

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
		model = new NonEditableTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(model);
		// Listener della tabella per pescare i nomi che servono
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						selectedId = Integer.parseInt(String.valueOf(model.getValueAt(selectedRow, 0)));
						String name = String.valueOf(model.getValueAt(selectedRow, 1));
						String category = String.valueOf(String.valueOf(model.getValueAt(selectedRow, 2)));
						String amount=String.valueOf(model.getValueAt(selectedRow, 3));
						String minStock = String.valueOf(model.getValueAt(selectedRow, 4));
						String price = String.valueOf(model.getValueAt(selectedRow, 5));
						String vatString = String.valueOf(model.getValueAt(selectedRow, 6) + "%");
						System.out.println(vatString);
						// double vat=Double.parseDouble(vatString.substring(0,vatString.length()-1));

						// Il listener ascolta la riga selezionata e la usa per popolare i campi
						txfName.setText(name);
						categoryComboBox.setSelectedItem(category);
						txfMinStock.setText(minStock);
						txfPrice.setText(price);
						VATComboBox.setSelectedItem(vatString);
						txfAmount.setText(amount);
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
		btnSearch.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);

		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txfSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		btnFilter.addActionListener(e -> populateTableByFilter());
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		btnInsert.setBounds(720, 8, 40, 30);
		btnInsert.addActionListener(e -> createProduct());
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		btnUpdate.setBounds(770, 8, 40, 30);
		btnUpdate.addActionListener(e->updateProduct());
		containerPanel.add(btnUpdate);

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setBounds(820, 8, 40, 30);
		btnDelete.addActionListener(e->deleteProduct());
		containerPanel.add(btnDelete);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
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
		btnHystorical.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/cartellina.png")));
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome prodotto:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(209, 436, 220, 20);
		add(txfName);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblCategory.setBounds(43, 474, 170, 17);
		add(lblCategory);

		JLabel lblMinStock = new JLabel("Quantità minima:");
		lblMinStock.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblMinStock.setBounds(43, 513, 170, 14);
		add(lblMinStock);

		txfMinStock = new JTextField();
		txfMinStock.setColumns(10);
		txfMinStock.setBounds(209, 512, 220, 20);
		add(txfMinStock);

		txfPrice = new JTextField();
		txfPrice.setColumns(10);
		txfPrice.setBounds(749, 432, 220, 20);
		add(txfPrice);

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
		VATComboBox = new JComboBox<>(ivasToString);
		VATComboBox.setBounds(749, 471, 220, 27);
		add(VATComboBox);

		lbOutput = new JLabel("");
		lbOutput.setBounds(389, 606, 625, 16);
		add(lbOutput);

		JLabel amountLbl = new JLabel("Quantità:");
		amountLbl.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		amountLbl.setBounds(531, 514, 170, 14);
		add(amountLbl);

		txfAmount = new JTextField();
		txfAmount.setColumns(10);
		txfAmount.setBounds(749, 511, 220, 20);
		add(txfAmount);
	}

	void populateTable() {
		clearTable();
		List<Product> products = ProductDAO.getAllProducts();
		if (products.isEmpty()) {
			model.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		products.parallelStream()
		.forEach(p -> model.addRow(p.toTableRow()));
	}

	private void clearTable() {
		model.getDataVector().removeAllElements();
		revalidate();
	}

	private void createProduct() {
		System.out.println(isDataValid(true));
		if (isDataValid(true)) {
			String name = txfName.getText();
			int minStock = Integer.parseInt(txfMinStock.getText());
			BigDecimal price = new BigDecimal(txfPrice.getText());
			String vatString = VATComboBox.getSelectedItem().toString();
			double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
			VAT vat = VATDao.getVATByAmount((float) vatAmount).get();
			ProductCat type = ProductCat.fromDescription(categoryComboBox.getSelectedItem().toString());
			// Product(String name, int amount, int minStock, BigDecimal price, VAT vat,
			// ProductCat type)
			Product product = new Product(name, 0, minStock, price, vat, type);
			product = ProductDAO.insertProduct(product).get();
			System.out.println(product);
			lbOutput.setText(product.getName() + " inserito nel database");
			populateTable();

			// Product product=new Product(txfName.getText(),0,)
			// (nameText., int amount, int minStock, BigDecimal price, double vat,
			// ProductCat type,
			// boolean isEnabled)
		}

	}

	private boolean isDataValid(boolean mustNameBeUnique) {
		lbOutput.setText("");
		if (mustNameBeUnique) {
			if (!Product.isNameUnique(txfName.getText())) {
				lbOutput.setText("Prodotto già esistente nel database");
				return false;
			}
		}
		try {
			inputValidator.validateName(txfName);
		} catch (inputValidatorException e) {
			lbOutput.setText(e.getMessage());
			e.printStackTrace();
			return false;
		}
		String minStockText = txfMinStock.getText();
		System.out.println(minStockText);
		if (minStockText.isEmpty()) {
			lbOutput.setText("La quantità minima non può essere vuota");
			return false;
		}

		try {
			int minStock = Integer.parseInt(txfMinStock.getText());
			if (minStock <= 0) {
				lbOutput.setText("La quantità minima deve essere un numero valido");
				System.out.println("try");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			lbOutput.setText("La quantità minima deve essere un numero valido");
			System.out.println("catch");
			return false;
		}
		String priceText = txfPrice.getText().trim();
		if (priceText.isEmpty()) {
			lbOutput.setText("Il prezzo non può essere vuoto");
			return false;
		}
		try {
			double price = Double.parseDouble(txfPrice.getText());
			if (price <= 0) {
				lbOutput.setText("Il prezzo deve essere un numero valido");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Prezzo valido");

		return true;
	}

	private void populateTableByFilter() {
		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}
		clearTable();
		List<Product> products = ProductDAO.getAllProducts();
		if (products.isEmpty()) {
			model.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Product p : products) {
			if (p.isEnabled() && p.getName().toLowerCase().contains(txfSearchBar.getText().toLowerCase())) {
				model.addRow(new String[] { String.valueOf(p.getId()), p.getName(), p.getType().getDescription(),
						String.valueOf(p.getAmount()), String.valueOf(p.getMinStock()), String.valueOf(p.getPrice()),
						String.valueOf(p.getVat()) });
			}
			// {"id","Prodotto","Categoria","Quantità","Quantità minima","Prezzo","IVA%"}
		}
		txfSearchBar.setText("");
	}

	private void updateProduct() {
		if (isDataValid(false)) {
			String name = txfName.getText();
			int minStock = Integer.parseInt(txfMinStock.getText());
			BigDecimal price = new BigDecimal(txfPrice.getText());
			String vatString = VATComboBox.getSelectedItem().toString();
			double vatDouble = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
			VAT vat=VATDao.getVATByAmount(vatDouble).get();
			ProductCat type = ProductCat.fromDescription(categoryComboBox.getSelectedItem().toString());
			int amount=Integer.parseInt(txfAmount.getText());
			Product product=new Product(name, amount,minStock,price,vat,type);
			//(String name, int amount, int minStock, BigDecimal price, VAT vat, ProductCat type)
			ProductDAO.updateData(selectedId,product);
			lbOutput.setText(product.getName() + " modificato correttamente");
			clearFields();
			populateTable();

		}

	}
	private void deleteProduct() {
		lbOutput.setText("");
		ProductDAO.toggleEnabledProduct(selectedId);
		lbOutput.setText("Trattamento rimosso correttamente");
		populateTable();
	}

	private void clearFields() {
		txfName.setText("");
		txfMinStock.setText("");
		txfPrice.setText("");
		txfAmount.setText("");
	}

	public JTextField gettxfSearchBar() {
		return txfSearchBar;
	}

	public void settxfSearchBar(JTextField txfSearchBar) {
		this.txfSearchBar = txfSearchBar;
	}

	public JTextField getTxtName() {
		return txfName;
	}

	public void setTxtName(JTextField txfName) {
		this.txfName = txfName;
	}

	public JTextField getTxtMinStock() {
		return txfMinStock;
	}

	public void setTxtMinStock(JTextField txfMinStock) {
		this.txfMinStock = txfMinStock;
	}

	public NonEditableTableModel getTableModel() {
		return model;
	}

	public void setTableModel(NonEditableTableModel model) {
		this.model = model;
	}

	public JTextField getTxtPrice() {
		return txfPrice;
	}

	public void setTxtPrice(JTextField txfPrice) {
		this.txfPrice = txfPrice;
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	Optional<Product> insertElement() {
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
