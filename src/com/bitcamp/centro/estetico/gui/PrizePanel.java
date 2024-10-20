package com.bitcamp.centro.estetico.gui;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.PrizeDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.PrizeType;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;
import com.bitcamp.centro.estetico.utils.inputValidator;

public class PrizePanel extends BasePanel<Prize> {
	private static final long serialVersionUID = 1L;
	private static int id = -1;
	private static boolean isEnabled = false;

	private static JSplitLbTxf txfThreshold;
	private static JSplitLbTxf txfName;
	private static JSplitLbTxf txfPrice;
	private static JSplitDatePicker expiration;
	private static JSplitComboBox<PrizeType> typeComboBox;

	public PrizePanel() {
		setName("Premi");

		setSize(1024, 768);
		setTitle("GESTIONE  PREMI");
		
		table.getSelectionModel().addListSelectionListener(getListSelectionListener());
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(prizeModel));
		table.setModel(prizeModel);

		txfName = new JSplitLbTxf("Nome Premio");
		actionsPanel.add(txfName);

		typeComboBox = new JSplitComboBox<PrizeType>("Tipo");
		for(PrizeType pt : PrizeType.values()) {
			typeComboBox.addItem(pt);
		}
		actionsPanel.add(typeComboBox);

		txfThreshold = new JSplitLbTxf("Punti Necessari", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(txfThreshold);

		txfPrice = new JSplitLbTxf("€ in Buono", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(txfPrice);

		expiration = new JSplitDatePicker("Scadenza");
		actionsPanel.add(expiration);
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	void insertElement() {
		if (!isDataValid()) return;

		// Recupera i valori dai campi
		String name = txfName.getText();
		int loyaltyPoints = Integer.parseInt(txfThreshold.getText());
		PrizeType type = (PrizeType) typeComboBox.getSelectedItem();
		double price = Double.parseDouble(txfPrice.getText());

		Prize newPrize = new Prize(
			name,
			loyaltyPoints,
			type,
			LocalDate.now(),
			price
		);
		lbOutput.setText("Premio inserito");
		clearTxfFields();
		PrizeDAO.insertPrize(newPrize);
		refreshTable();
	}

	@Override
	void updateElement() {
		if (!isDataValid() || id < 0) return;

		String name = txfName.getText();
		int loyaltyPoints = Integer.parseInt(txfThreshold.getText());
		PrizeType type = (PrizeType) typeComboBox.getSelectedItem();
		double price = Double.parseDouble(txfPrice.getText());

		Prize oldPrize = PrizeDAO.getPrize(id).get();
		Prize updatedPrize = new Prize(
			name,
			loyaltyPoints,
			type,
			Optional.ofNullable(oldPrize.getExpirationDate()).orElse(null),
			price
		);
		lbOutput.setText("Premio aggiornato");
		clearTxfFields();
		PrizeDAO.updatePrize(id, updatedPrize);
		refreshTable();
	}

	@Override
	void deleteElement() {
		if(id <= -1) return;
		lbOutput.setText("Premio rimosso");
		PrizeDAO.deletePrize(id);
		refreshTable();
	}

	@Override
	void disableElement() {
		if(id <= -1) return;
		PrizeDAO.toggleEnabledPrize(id);
		lbOutput.setText(!isEnabled ? "Premio abilitato" : "Premio disabilitato");
		refreshTable();
	}

	@Override
	void populateTable() {
		isRefreshing = true;
		prizeModel.setRowCount(0);
		clearTxfFields();

		prizes = PrizeDAO.getAllPrizes();
		if (!prizes.isEmpty()) {
			prizes.parallelStream()
					.forEach(e -> prizeModel.addRow(e.toTableRow()));
		} else {
			lbOutput.setText("Lista Premi vuota");
		}
		isRefreshing = false;
	}

	@Override
	void clearTxfFields() {
		txfName.setText("");
		txfPrice.setText("");
		txfThreshold.setText("");
		typeComboBox.setSelectedIndex(0);
	}

	@Override
	ListSelectionListener getListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (isRefreshing) return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) return;
				
				var values = getRowMap(table, prizeModel);

				id = (int) values.get("ID");
				isEnabled = (boolean) values.get("Abilitato");
				String name = (String) values.get("Nome");
				int threshold = (int) values.get("Punti Necessari");
				PrizeType type = (PrizeType) values.get("Tipo");
				double price = (double) values.get("€ in Buono");

				txfName.setText(name);
				txfThreshold.setText(threshold);
				typeComboBox.setSelectedItem(type);
				txfPrice.setText(price);
			}
		};
	}

	@Override
	boolean isDataValid() {
		try {
			if(!inputValidator.validateAlphanumeric(txfName.getText()) &&
	        !inputValidator.validateDecimal(Double.parseDouble(txfPrice.getText()), 0, 10000))
			 throw new RuntimeException("Dati non validi");
	    } catch (RuntimeException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage());
	        return false;
	    }

	    // loyaltyPoints threshold validation
	    try {
	        int threshold = Integer.parseInt(txfThreshold.getText());
	        if (threshold < 0) {
	            JOptionPane.showMessageDialog(null, "La soglia dei punti deve essere un numero positivo.");
	            return false;
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "La soglia dei punti non è valida. Deve essere un numero intero.");
	        return false;
	    }


	    return true;
	}

}