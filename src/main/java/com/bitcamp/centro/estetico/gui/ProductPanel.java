package com.bitcamp.centro.estetico.gui;

import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.ProductCat;
import com.bitcamp.centro.estetico.models.Stock;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JOptionPaneGoToTab;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.JSplitTxf;

public class ProductPanel extends AbstractBasePanel<Product> {

	private static Product selectedData = new Product();

	private static final long serialVersionUID = 1L;
	private static JSplitTxf txfName;
	private static JSplitNumber txfPrice;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JSplitComboBox<ProductCat> categoryComboBox;

	public ProductPanel(JFrame parent) {
		super(parent);

		setSize(1024, 768);
		setName("Prodotti");
		setTitle("GESTIONE PRODOTTI");

		txfName = new JSplitTxf("Nome");

		txfPrice = new JSplitNumber("Prezzo");

		categoryComboBox = new JSplitComboBox<>("Categoria");
		for (ProductCat cat : ProductCat.values()) {
			categoryComboBox.addItem(cat);
		}
		categoryComboBox.setSelectedIndex(0);

		vats.clear();
		vats.addAll(DAO.getAll(VAT.class));
		VATComboBox = new JSplitComboBox<>("IVA");
		vats.stream()
				.filter(v -> v.isEnabled())
				.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);

		actionsPanel.add(txfName);
		actionsPanel.add(txfPrice);
		actionsPanel.add(categoryComboBox);
		actionsPanel.add(VATComboBox);

	}

	@Override
	public void insertElement() {
		try { // all fields must be filled
			isDataValid();
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		String name = txfName.getText();
		BigDecimal price = new BigDecimal(txfPrice.getText());
		VAT vat = (VAT) VATComboBox.getSelectedItem();
		ProductCat type = (ProductCat) categoryComboBox.getSelectedItem();

		Product product = new Product(name, price, vat, type);
		lbOutput.setText(product.getName() + " inserito nel database");

		DAO.insert(product);
		refresh();
	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun prodotto selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		try { // all fields must be filled
			isDataValid();
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return;
		}

		String name = txfName.getText();
		BigDecimal price = new BigDecimal(txfPrice.getText());
		VAT vat = (VAT) VATComboBox.getSelectedItem();
		ProductCat type = (ProductCat) categoryComboBox.getSelectedItem();

		selectedData.setName(name);
		selectedData.setPrice(price);
		selectedData.setVat(vat);
		selectedData.setType(type);

		lbOutput.setText(selectedData.getName() + " modificato");
		clearTxfFields();
		DAO.update(selectedData);
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun prodotto selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		DAO.delete(selectedData);
		lbOutput.setText("Prodotto cancellato");
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun prodotto selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;

		DAO.toggle(selectedData);
		lbOutput.setText(selectedData.isEnabled() ? "Prodotto abilitato" : "Prodotto disabilitato");
		refresh();
	}

	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfPrice.setText(0);
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting())
					return;

				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;

				selectedData = model.getObjAt(selectedRow);
				if (selectedData.getId() == null || !selectedData.isEnabled())
					return;

				String name = selectedData.getName();
				ProductCat category = selectedData.getType();
				BigDecimal price = selectedData.getPrice();
				VAT vat = selectedData.getVat();

				txfName.setText(name);
				txfPrice.setText(price);
				categoryComboBox.setSelectedItem(category);
				VATComboBox.setSelectedItem(vat);

			}
		};
	}

	@Override
	public boolean isDataValid() {
		InputValidator.validateNumber(txfPrice);
		return true;
	}

	@Override
	public void populateTable() {
		products.clear();
		products.addAll(DAO.getAll(Product.class));
		stocks.clear();
		stocks.addAll(DAO.getAll(Stock.class));

		vats.clear();
		vats.addAll(DAO.getAll(VAT.class));
		VATComboBox.removeAllItems();
		vats.stream()
				.filter(v -> v.isEnabled())
				.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);

		if (!products.isEmpty()) {
			model.addRows(products);

			StringBuilder lowStock = new StringBuilder();
			for (Stock stock : stocks) {
				if (stock.getCurrentStock() <= (stock.getMinimumStock() / 3)) { // if it's lower than a third popup message
					lowStock.append(stock.getProduct().getName() + " quantità bassa\n");
				}
			}

			if(!lowStock.isEmpty()) {
				new JOptionPaneGoToTab(MainFrame.geJTabbedPane(),
				lowStock.toString(), "Inventario", MainFrame.getStockPanel());
			}
		} else {
			lbOutput.setText("Lista Prodotti vuota");
		}
	}
}
