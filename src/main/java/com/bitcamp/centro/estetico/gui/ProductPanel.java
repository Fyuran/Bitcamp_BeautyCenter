package com.bitcamp.centro.estetico.gui;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.ProductCat;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitTxf;

public class ProductPanel extends AbstractBasePanel<Product> {

	private static Product selectedData = new Product();

	private static final long serialVersionUID = 1L;
	private static JSplitTxf txfName;
	private static JSplitTxf txfPrice;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JSplitComboBox<ProductCat> categoryComboBox;

	public ProductPanel(JFrame parent) {
		super(parent);

		setSize(1024, 768);
		setName("Prodotti");
		setTitle("GESTIONE PRODOTTI");

		txfName = new JSplitTxf("Nome");

		txfPrice = new JSplitTxf("Prezzo", new JFormattedTextField(NumberFormat.getInstance()));

		categoryComboBox = new JSplitComboBox<>("Categoria");
		for (ProductCat cat : ProductCat.values()) {
			categoryComboBox.addItem(cat);
		}
		categoryComboBox.setSelectedIndex(0);

		vats = DAO.getAll(VAT.class);
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
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
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
		txfPrice.setText("");
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
		InputValidator.validateName(txfName);
		InputValidator.validateNumber(txfPrice);
		return true;
	}

	@Override
	public void populateTable() {
		products = DAO.getAll(Product.class);
		if (!products.isEmpty()) {
			model.addRows(products);
		} else {
			lbOutput.setText("Lista Prodotti vuota");
		}
	}
}
