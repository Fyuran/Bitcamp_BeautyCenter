package com.bitcamp.centro.estetico.gui;

import java.awt.Font;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class TreatmentPanel extends BasePanel<Treatment> {

	private static final long serialVersionUID = 1L;
	private static JTextField txfPrice;
	private static JTextField txfName;
	private static JComboBox<VAT> VATComboBox;
	private static JTextField txfDuration;
	private static int selectedId;
	private static final int _ISENABLEDCOL = 3;

	public TreatmentPanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("Trattamenti");
		setTitle("GESTIONE TRATTAMENTI");
		lbTitle.setBounds(415, 11, 220, 32);
		menuPanel.setBounds(10, 54, 1004, 347);
		
		table.setModel(treatmentModel);
		table.removeColumn(table.getColumnModel().getColumn(_ISENABLEDCOL));

		scrollPane.setBounds(23, 60, 959, 276);
		btnSearch.setBounds(206, 8, 40, 30);
		txfSearchBar.setBounds(23, 14, 168, 24);
		btnFilter.setBounds(256, 8, 40, 30);
		btnInsert.setBounds(720, 8, 40, 30);
		btnUpdate.setBounds(770, 8, 40, 30);
		btnDelete.setBounds(820, 8, 40, 30);
		btnDisable.setBounds(920, 8, 40, 30);
		outputPanel.setBounds(23, 60, 959, 276);

		productModel = new NonEditableTableModel(new String[] { "ID", "Prodotto", "Categoria"}, 0);
		JTable productTable = new JTable(productModel);
		productTable.setEnabled(false);

		JScrollPane productScrollPane = new JScrollPane(productTable);
		productScrollPane.setBounds(577, 474, 437, 181);
		add(productScrollPane);

		JLabel lbName = new JLabel("Nome trattamento*:");
		lbName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbName.setBounds(43, 437, 170, 14);
		add(lbName);

		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(209, 436, 220, 20);
		add(txfName);

		JLabel lblPrice = new JLabel("Prezzo:");
		lblPrice.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPrice.setBounds(43, 474, 170, 17);
		add(lblPrice);

		txfPrice = new JTextField();
		txfPrice.setColumns(10);
		txfPrice.setBounds(209, 474, 220, 20);
		add(txfPrice);

		JLabel lbVAT = new JLabel("IVA*:");
		lbVAT.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbVAT.setBounds(43, 513, 170, 14);
		add(lbVAT);

		VATComboBox = new JComboBox<>();
		VATComboBox.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		VATComboBox.setBounds(209, 511, 220, 22);
		add(VATComboBox);

		JLabel lbDuration = new JLabel("Durata (minuti):");
		lbDuration.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbDuration.setBounds(43, 553, 170, 14);
		add(lbDuration);

		txfDuration = new JTextField();
		txfDuration.setColumns(10);
		txfDuration.setBounds(209, 552, 220, 20);
		add(txfDuration);

		JButton productBtn = new JButton("Seleziona i prodotti");
		productBtn.setBounds(530, 432, 166, 29);
		productBtn.addActionListener(e -> new ProductSelector(TreatmentPanel.this));
		add(productBtn);
		lbOutput = new JLabel("");
		lbOutput.setBounds(248, 413, 625, 16);
		add(lbOutput);
	}

	private void populateTableByFilter() {
		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}
		clearTable();

		if (products.isEmpty()) {
			treatmentModel.addRow(new String[] { "Sembra non ci siano trattamenti presenti", "" });
			return;
		}
		for (Treatment t : treatments) {
			if (t.isEnabled() && t.getType().toLowerCase().contains(txfSearchBar.getText().toLowerCase())) {
				treatmentModel.addRow(new String[] { String.valueOf(t.getId()), t.getType(), String.valueOf(t.getPrice()),
						t.getVat().toString(), String.valueOf(t.getDuration()) });
			}
		}
		txfSearchBar.setText("");
	}

	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfPrice.setText("");
		txfDuration.setText("");
		products.clear();
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	public Optional<Treatment> insertElement() {
		if (!isDataValid()) return Optional.empty();
		String name = txfName.getText();
		BigDecimal price = new BigDecimal(txfPrice.getText());
		String vatString = String.valueOf(VATComboBox.getSelectedItem().toString());
		double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
		VAT vat = VATDao.getVATByAmount(vatAmount).get();
		int durationInt = Integer.parseInt(txfDuration.getText());
		Duration duration = Duration.ofMinutes(durationInt);

		Treatment treatment = new Treatment(name, price, vat, duration, products, true);

		Optional<Treatment> opt = TreatmentDAO.insertTreatment(treatment);
		TreatmentDAO.addProductsToTreatment(treatment, products);

		lbOutput.setText(name + " inserito correttamente");
		clearTxfFields();
		refreshTable();
		
		return opt;
	}

	@Override
	public int updateElement() {
		if (!isDataValid()) return -1;
		String name = txfName.getText();
		BigDecimal price = new BigDecimal(txfPrice.getText());
		String vatString = String.valueOf(VATComboBox.getSelectedItem().toString());
		double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
		VAT vat = VATDao.getVATByAmount(vatAmount).get();
		int durationInt = Integer.parseInt(txfDuration.getText());
		Duration duration = Duration.ofMinutes(durationInt);
		// prodottiSelezionati
		// isEnabled
		Treatment t = new Treatment(name, price, vat, duration, products, true);

		clearTxfFields();
		refreshTable();
		return TreatmentDAO.updateTreatment(selectedId, t);
	}

	@Override
	public int deleteElement() {
		try {
			int row = table.getSelectedRow();
			if (row == -1) {
				throw new IllegalArgumentException("no row selected");
			}
			final int id = (int) treatmentModel.getValueAt(row, 0);
			lbOutput.setText("Trattamento cancellato");
			refreshTable();
			return TreatmentDAO.deleteTreatment(id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Impossibile cancellare: " + e.getMessage(), "Errore di database",
					JOptionPane.ERROR_MESSAGE);
		}
		return -1;
	}

	@Override
	public int disableElement() {
		lbOutput.setText("Trattamento rimosso correttamente");
		refreshTable();
		return TreatmentDAO.toggleEnabledTreatment(selectedId);
	}

	@Override
	public void populateTable() {
		clearTable();
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
		if (!TreatmentDAO.isTreatmentNameUnique(txfName.getText())) {
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
	public ListSelectionListener getListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {

						selectedId = Integer.parseInt(String.valueOf(treatmentModel.getValueAt(selectedRow, 0)));
						String name = String.valueOf(treatmentModel.getValueAt(selectedRow, 1));
						String price = String.valueOf(String.valueOf(treatmentModel.getValueAt(selectedRow, 2)));
						String vat = String.valueOf(treatmentModel.getValueAt(selectedRow, 3) + "%");
						Duration duration = (Duration) treatmentModel.getValueAt(selectedRow, 4);
						products = TreatmentDAO.getProductsOfTreatment(selectedId);
						populateTable();

						txfName.setText(name);
						VATComboBox.setSelectedItem(vat);
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
