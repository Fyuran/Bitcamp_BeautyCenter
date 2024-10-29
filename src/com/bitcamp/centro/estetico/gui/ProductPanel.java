package com.bitcamp.centro.estetico.gui;

import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.ProductCat;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class ProductPanel extends BasePanel<Product> {

	private static VAT_DAO vat_DAO = VAT_DAO.getInstance();
	private static ProductDAO productDAO = ProductDAO.getInstance();

	private static final long serialVersionUID = 1L;
	private static JTextField txfName;
	private static JFormattedTextField txfMinStock;
	private static JFormattedTextField txfPrice;
	private static JComboBox<VAT> VATComboBox;
	private static JComboBox<ProductCat> categoryComboBox;
	private static int selectedId = -1;
	private static JFormattedTextField txfAmount;

	public ProductPanel() {

		setSize(1024, 768);
		setName("Prodotti");
		setTitle("GESTIONE PRODOTTI");

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

		txfMinStock = new JFormattedTextField(NumberFormat.getInstance());
		txfMinStock.setColumns(10);
		txfMinStock.setBounds(209, 512, 220, 20);
		add(txfMinStock);

		txfPrice = new JFormattedTextField(NumberFormat.getInstance());
		txfPrice.setColumns(10);
		txfPrice.setBounds(749, 432, 220, 20);
		add(txfPrice);

		JLabel lblIVA = new JLabel("IVA:");
		lblIVA.setBounds(531, 475, 170, 14);
		add(lblIVA);

		JLabel lblPrice = new JLabel("Prezzo:");
		lblPrice.setBounds(531, 437, 170, 14);
		add(lblPrice);

		categoryComboBox = new JComboBox<>(ProductCat.values());
		categoryComboBox.setBounds(209, 468, 220, 27);
		add(categoryComboBox);

		VATComboBox = new JComboBox<>(vats.toArray(new VAT[vats.size()]));
		VATComboBox.setBounds(749, 471, 220, 27);
		add(VATComboBox);


		JLabel amountLbl = new JLabel("Quantità:");
		amountLbl.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		amountLbl.setBounds(531, 514, 170, 14);
		add(amountLbl);

		txfAmount = new JFormattedTextField(NumberFormat.getInstance());
		txfAmount.setColumns(10);
		txfAmount.setBounds(749, 511, 220, 20);
		add(txfAmount);
	}

	private void populateTableByFilter() {
		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}
		clearTableModel(productModel);

		if (products.isEmpty()) {
			productModel.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Product p : products) {
			if (p.isEnabled() && p.getName().toLowerCase().contains(txfSearchBar.getText().toLowerCase())) {
				productModel.addRow(new String[] { String.valueOf(p.getId()), p.getName(), p.getType().getDescription(),
						String.valueOf(p.getAmount()), String.valueOf(p.getMinStock()), String.valueOf(p.getPrice()),
						String.valueOf(p.get()) });
			}
			// {"id","Prodotto","Categoria","Quantità","Quantità minima","Prezzo","IVA%"}
		}
		txfSearchBar.setText("");
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	public void insertElement() {
		if (!isDataValid()) return;
		String name = txfName.getText();
		int minStock = Integer.parseInt(txfMinStock.getText());
		BigDecimal price = new BigDecimal(txfPrice.getText());
		VAT vat = vat_DAO.get((VAT) VATComboBox.getSelectedItem()).get();
		ProductCat type = (ProductCat) categoryComboBox.getSelectedItem();

		Product product = new Product(name, 0, minStock, price, vat, type);
		lbOutput.setText(product.getName() + " inserito nel database");
		
		productDAO.insert(product);
		refreshTable();
	}

	@Override
	public void updateElement() {
		if (!isDataValid()) return;
		String name = txfName.getText();
		int minStock = Integer.parseInt(txfMinStock.getText());
		BigDecimal price = new BigDecimal(txfPrice.getText());
		VAT vat = vat_DAO.get((VAT) VATComboBox.getSelectedItem()).get();
		ProductCat type = (ProductCat) categoryComboBox.getSelectedItem();
		int amount = Integer.parseInt(txfAmount.getText());
		Product product = new Product(name, amount, minStock, price, vat, type);

		lbOutput.setText(product.getName() + " modificato");
		clearTxfFields();
		productDAO.update(selectedId, product);
		refreshTable();
	}

	@Override
	public void deleteElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteElement'");
	}

	@Override
	public void disableElement() {
		lbOutput.setText("");
		lbOutput.setText("Trattamento disabilitato");
		
		productDAO.toggle(selectedId);
	}

	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfMinStock.setText("");
		txfPrice.setText("");
		txfAmount.setText("");
	}

	// "ID", "Prodotto", "Categoria", "Quantità", "Quantità minima", "Prezzo", "IVA%"
	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow < 0) return;

					String name = (String) productModel.getValueAt(selectedRow, 1);
					ProductCat category = (ProductCat) productModel.getValueAt(selectedRow, 2);
					int quantity = (int) productModel.getValueAt(selectedRow, 3);
					int minimum = (int) productModel.getValueAt(selectedRow, 4);
					double price = (double) productModel.getValueAt(selectedRow, 5);
					VAT vat = (VAT) productModel.getValueAt(selectedRow, 6);

					txfName.setText(name);
					txfAmount.setValue(quantity);
					txfMinStock.setValue(minimum);
					txfPrice.setValue(price);
					categoryComboBox.setSelectedItem(category);
					VATComboBox.setSelectedItem(vat);
				}
			}
		};
	}

	@Override
	public boolean isDataValid() {
		lbOutput.setText("");

		if (!productDAO.isNameUnique(txfName.getText())) {
			lbOutput.setText("Prodotto già esistente nel database");
			return false;
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

	@Override
	public void populateTable() {
		clearTableModel(productModel);
		if (products.isEmpty())
			return;
		products.parallelStream()
				.forEach(p -> productModel.addRow(p.toTableRow()));
	}
}
