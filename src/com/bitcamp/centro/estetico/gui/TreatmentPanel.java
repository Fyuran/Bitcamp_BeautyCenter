package com.bitcamp.centro.estetico.gui;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class TreatmentPanel extends BasePanel<Treatment> {

	private static VAT_DAO vat_DAO = VAT_DAO.getInstance();
	private static TreatmentDAO treatmentDAO = TreatmentDAO.getInstance();

	private static int id = -1;
	private static boolean isEnabled = false;

	private static final long serialVersionUID = 1L;
	private static JSplitLbTxf txfPrice;
	private static JSplitLbTxf txfName;
	private static JSplitLbTxf txfType;
	private static JSplitComboBox<VAT> VATComboBox;
	private static JSplitLbTxf txfDuration;

	public TreatmentPanel() {

		setSize(1024, 768);
		setName("Trattamenti");
		setTitle("GESTIONE TRATTAMENTI");
		
		table.setModel(treatmentModel);

		JTable productTable = new JTable(productModel);
		productTable.setEnabled(false);

		JScrollPane productScrollPane = new JScrollPane(productTable);
		productScrollPane.setBounds(577, 474, 437, 181);
		actionsPanel.add(productScrollPane);

		txfName = new JSplitLbTxf("Nome");
		actionsPanel.add(txfName);

		txfType = new JSplitLbTxf("Descrizione");
		actionsPanel.add(txfType);

		txfPrice = new JSplitLbTxf("Prezzo", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(txfPrice);

		VATComboBox = new JSplitComboBox<>();
		actionsPanel.add(VATComboBox);

		txfDuration = new JSplitLbTxf("Durata", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(txfDuration);

		JButton productBtn = new JButton("Seleziona i prodotti");
		productBtn.setBounds(530, 432, 166, 29);
		productBtn.addActionListener(e -> new ProductSelector(this));
		actionsPanel.add(productBtn);
	}

	private void populateTableByFilter() {
		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}
		clearTable(table);

		if (products.isEmpty()) return;
		for (Treatment t : treatments) {
			if (t.isEnabled() && t.getType().toLowerCase().contains(txfSearchBar.getText().toLowerCase())) {
				treatmentModel.addRow(new String[] { String.valueOf(t.getId()), t.getType(), String.valueOf(t.getPrice()),
						t.get().toString(), String.valueOf(t.getDuration()) });
			}
		}
	}
	
	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfPrice.setText("");
		txfDuration.setText("");
		products.clear();
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
		BigDecimal price = new BigDecimal(txfPrice.getText());
		String vatString = String.valueOf(VATComboBox.getSelectedItem().toString());
		double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
		VAT vat = vat_DAO.getVATByAmount(vatAmount).get();
		int durationInt = Integer.parseInt(txfDuration.getText());
		Duration duration = Duration.ofMinutes(durationInt);

		Treatment treatment = new Treatment(name, price, vat, duration, products, true);

		Optional<Treatment> opt = treatmentDAO.insert(treatment);
		treatmentDAO.addProductsToTreatment(treatment, products);

		lbOutput.setText(name + " inserito");
		clearTxfFields();
		refreshTable();
		
	}

	@Override
	public void updateElement() {
		if (!isDataValid()) return;
		String name = txfName.getText();
		BigDecimal price = new BigDecimal(txfPrice.getText());
		String vatString = String.valueOf(VATComboBox.getSelectedItem().toString());
		double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
		VAT vat = vat_DAO.getVATByAmount(vatAmount).get();
		int durationInt = Integer.parseInt(txfDuration.getText());
		Duration duration = Duration.ofMinutes(durationInt);
		// prodottiSelezionati
		// isEnabled
		Treatment t = new Treatment(name, price, vat, duration, products, true);

		clearTxfFields();
		treatmentDAO.update(id, t);
		refreshTable();
	}

	@Override
	public void deleteElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("no row selected");
			}
			final int id = (int) treatmentModel.getValueAt(row, 0);
			lbOutput.setText("Trattamento cancellato");
			treatmentDAO.delete(id);
			refreshTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Impossibile cancellare: " + e.getMessage(), "Errore di database",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void disableElement() {
		lbOutput.setText("Trattamento disabilitato");
		treatmentDAO.toggle(id);
	}

	@Override
	public void populateTable() {
		clearTable(table);
		if (treatments.isEmpty()) return;
		treatments.parallelStream()
				.filter(t -> t.isEnabled() || isAdmin())
				.forEach(t -> treatmentModel.addRow(t.toTableRow()));
		
		if (products.isEmpty()) return;
		products.parallelStream()
				.filter(t -> t.isEnabled())
				.forEach(p -> productModel.addRow(p.toTableRow()));
	}

	@Override
	public boolean isDataValid() {
		if (!treatmentDAO.isTreatmentNameUnique(txfName.getText())) {
			lbOutput.setText("Nome del trattamento già presente");
			return false;
		}
		try {
			inputValidator.validateName(txfName);
		} catch (inputValidatorException e) {
			lbOutput.setText(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {

						id = Integer.parseInt(String.valueOf(treatmentModel.getValueAt(selectedRow, 0)));
						String name = String.valueOf(treatmentModel.getValueAt(selectedRow, 1));
						String price = String.valueOf(String.valueOf(treatmentModel.getValueAt(selectedRow, 2)));
						String vat = String.valueOf(treatmentModel.getValueAt(selectedRow, 3) + "%");
						Duration duration = (Duration) treatmentModel.getValueAt(selectedRow, 4);
						products = treatmentDAO.getProductsOfTreatment(id);
						populateTable();

						txfName.setText(name);
						//VATComboBox.setSelectedItem(vat);
						txfPrice.setText(price);
						txfPrice.setText(price);
						txfDuration.setText(String.valueOf(duration.toMinutes()));
						table.clearSelection();

					}
				}
			}
		};
	}
}
