package com.bitcamp.centro.estetico.gui;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.JSplitTimePicker;
import com.bitcamp.centro.estetico.utils.JSplitTxf;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class TreatmentPanel extends AbstractBasePanel<Treatment> {

	private static Treatment selectedData = new Treatment();
	private static List<Product> returnProducts = new ArrayList<>();

	private static final long serialVersionUID = 1L;
	private static JSplitNumber txfPrice;
	private static JSplitTxf txfName;
	private static JSplitTxf txfType;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JSplitTimePicker timePicker;
	private static JSplitBtn productsBtn;

	public TreatmentPanel(JFrame parent) {
		super(parent);
		setSize(1024, 768);
		setName("Trattamenti");
		setTitle("GESTIONE TRATTAMENTI");

		txfName = new JSplitTxf("Nome");
		txfType = new JSplitTxf("Descrizione");
		txfPrice = new JSplitNumber("Prezzo");
		productsBtn = new JSplitBtn("Prodotto", "Scelta Prodotti");
		productsBtn.addActionListener(l -> {
			ModelChooser<Product> picker = new ModelChooser<>(parent, "Prodotti",
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, returnProducts);
			products.clear();
			products.addAll(DAO.getAll(Product.class));
			var available = products
					.parallelStream()
					.filter(c -> c.isEnabled())
					.toList();

			if (!available.isEmpty()) {
				picker.addRows(available);
			} else
				picker.getLbOutput().setText("Lista vuota");

			picker.setVisible(true);
		});
		VATComboBox = new JSplitComboBox<>("IVA");
		timePicker = new JSplitTimePicker("Durata");

		actionsPanel.add(txfName);
		actionsPanel.add(txfType);
		actionsPanel.add(txfPrice);
		actionsPanel.add(VATComboBox);
		actionsPanel.add(timePicker);
		actionsPanel.add(productsBtn);

		vats.clear();
		vats.addAll(DAO.getAll(VAT.class));
		vats.stream()
				.filter(v -> v.isEnabled())
				.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);

	}

	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfPrice.setText(0);
		timePicker.setTime(null);
		products.clear();
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

		VAT vat = VATComboBox.getSelectedItem();
		LocalTime duration = timePicker.getTime();

		List<Product> newProducts = null;
		if (!returnProducts.isEmpty()) {
			newProducts = returnProducts;
		}
		Treatment treatment = new Treatment(name, price, vat, duration, newProducts);
		DAO.insert(treatment);

		lbOutput.setText(name + " inserito");
		clearTxfFields();
		refresh();

	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun Trattamento selezionato");
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

		VAT vat = VATComboBox.getSelectedItem();
		LocalTime duration = timePicker.getTime();

		selectedData.setType(name);
		selectedData.setPrice(price);
		selectedData.setVat(vat);
		selectedData.setTime(duration);
		if (returnProducts.isEmpty()) {
			selectedData.setProducts(null);
		} else {
			selectedData.setProducts(returnProducts);
		}

		clearTxfFields();
		DAO.update(selectedData);
		lbOutput.setText("Trattamento aggiornato");
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun Trattamento selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		DAO.delete(selectedData);
		lbOutput.setText("Trattamento cancellato");
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun Trattamento selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;

		DAO.toggle(selectedData);
		lbOutput.setText(selectedData.isEnabled() ? "Trattamento abilitato" : "Trattamento disabilitato");
		refresh();
	}

	@Override
	public void populateTable() {
		vats.clear();
		vats.addAll(DAO.getAll(VAT.class));
		VATComboBox.removeAllItems();
		vats.stream()
				.filter(v -> v.isEnabled())
				.forEach(v -> VATComboBox.addItem(v));
		VATComboBox.setSelectedIndex(0);

		treatments.clear();
		treatments.addAll(DAO.getAll(Treatment.class));
		if (!treatments.isEmpty()) {
			model.addRows(treatments);
		} else {
			lbOutput.setText("Lista Trattamenti vuota");
		}
	}

	@Override
	public boolean isDataValid() {
		try {
			InputValidator.validateName(txfName);
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
		}
		return false;
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting())
					return;

				int selectedRow = table.getSelectedRow();
				if (selectedRow <= -1) {
					return;
				}

				selectedData = model.getObjAt(selectedRow);
				if (!selectedData.isEnabled())
					return;

				String name = selectedData.getType();
				BigDecimal price = selectedData.getPrice();
				VAT vat = selectedData.getVat();
				LocalTime duration = selectedData.getTime();

				txfName.setText(name);
				VATComboBox.setSelectedItem(vat);
				txfPrice.setText(price);
				txfPrice.setText(price);
				timePicker.setTime(duration);

				returnProducts.clear();

			}
		};
	}
}
