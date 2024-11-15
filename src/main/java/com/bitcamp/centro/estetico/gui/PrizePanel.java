package com.bitcamp.centro.estetico.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.PrizeType;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JSplitComboBox;
import com.bitcamp.centro.estetico.utils.JSplitDatePicker;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.JSplitTxf;

public class PrizePanel extends AbstractBasePanel<Prize> {
	private static final long serialVersionUID = 1L;

	private static Prize selectedData;

	private static JSplitNumber txfThreshold;
	private static JSplitTxf txfName;
	private static JSplitDatePicker expirationDatePicker;
	private static JSplitComboBox<PrizeType> typeComboBox;

	public PrizePanel(JFrame parent) {
		super(parent);
		setName("Premi");

		setSize(1024, 768);
		setTitle("GESTIONE  PREMI");

		txfName = new JSplitTxf("Nome Premio");
		typeComboBox = new JSplitComboBox<PrizeType>("Tipo");
		for (PrizeType pt : PrizeType.values()) {
			typeComboBox.addItem(pt);
		}
		typeComboBox.setSelectedIndex(0);
		
		txfThreshold = new JSplitNumber("Punti Necessari");
		expirationDatePicker = new JSplitDatePicker("Scadenza");

		actionsPanel.add(txfName);
		actionsPanel.add(typeComboBox);
		actionsPanel.add(txfThreshold);
		actionsPanel.add(expirationDatePicker);
	}

	@Override
	public void insertElement() {
		if (!isDataValid())
			return;

		// Recupera i valori dai campi
		String name = txfName.getText();
		int loyaltyPoints = Integer.parseInt(txfThreshold.getText());
		PrizeType type = (PrizeType) typeComboBox.getSelectedItem();

		Prize prize = new Prize(name, loyaltyPoints, null, type);
		lbOutput.setText("Premio inserito");
		clearTxfFields();
		DAO.insert(prize);
		refresh();
	}

	@Override
	public void updateElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;
		if (!isDataValid())
			return;

		String name = txfName.getText();
		int loyaltyPoints = Integer.parseInt(txfThreshold.getText());
		PrizeType type = (PrizeType) typeComboBox.getSelectedItem();

		selectedData.setName(name);
		selectedData.setThreshold(loyaltyPoints);
		selectedData.setType(type);
		selectedData.setExpirationDate(expirationDatePicker.getDate());

		clearTxfFields();
		DAO.update(selectedData);
		lbOutput.setText("Premio aggiornato");
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;
		lbOutput.setText("Premio rimosso");
		DAO.delete(selectedData);
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessun cliente selezionato");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;
		DAO.toggle(selectedData);
		lbOutput.setText(selectedData.isEnabled() ? "Premio abilitato" : "Premio disabilitato");
		refresh();
	}

	@Override
	public void populateTable() {
		prizes.clear();
		prizes.addAll(DAO.getAll(Prize.class));
		if (!prizes.isEmpty()) {
			model.addRows(prizes);
		} else {
			lbOutput.setText("Lista Premi vuota");
		}
	}

	@Override
	public void clearTxfFields() {
		txfName.setText("");
		txfThreshold.setText(0);
		typeComboBox.setSelectedIndex(0);
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())
					return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;

				selectedData = model.getObjAt(selectedRow);
				if (selectedData.getId() == null || !selectedData.isEnabled())
					return;

				txfName.setText(selectedData.getName());
				txfThreshold.setText(selectedData.getThreshold());
				typeComboBox.setSelectedItem(selectedData.getType());
				expirationDatePicker.setDate(selectedData.getExpirationDate());
				
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			InputValidator.validateAlphanumeric(txfName, "Nome Premio");
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage());
			return false;
		}

		// loyaltyPoints threshold validation
		try {
			int threshold = Integer.parseInt(txfThreshold.getText());
			if (threshold < 0) {
				JOptionPane.showMessageDialog(parent, "La soglia dei punti deve essere un numero positivo.");
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, "La soglia dei punti non è valida. Deve essere un numero intero.");
			return false;
		}

		return true;
	}

}