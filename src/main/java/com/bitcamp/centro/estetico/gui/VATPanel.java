package com.bitcamp.centro.estetico.gui;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.JSplitTxf;

public class VATPanel extends AbstractBasePanel<VAT> {

	private static VAT selectedData;

	private static final long serialVersionUID = 1L;
	private static JSplitTxf txfAmount;

	public VATPanel(JFrame parent) {
		super(parent);
		setSize(1024, 768);
		setName("IVA");
		setTitle("ALIQUOTE IVA");

		txfAmount = new JSplitTxf("Percentuale", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(txfAmount);
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	public void insertElement() {
		double amount = Double.parseDouble(txfAmount.getText());
		lbOutput.setText("Aliquota inserita");
		DAO.insert(new VAT(amount));
		refresh();
	}

	@Override
	public void updateElement() {
		if (!isDataValid())
			return;
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessuna aliquota selezionata");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		double amount = Double.parseDouble(txfAmount.getText());

		selectedData.setAmount(amount);
		lbOutput.setText("Aliquota modificata");
		DAO.update(selectedData);
		refresh();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessuna aliquota selezionata");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData.getId() == null || !selectedData.isEnabled())
			return;

		lbOutput.setText("Aliquota rimossa");
		DAO.delete(selectedData);
		refresh();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "Nessuna aliquota selezionata");
			return; // do not allow invalid ids to be passed to update
		}
		if (selectedData == null)
			return;

		DAO.toggle(selectedData);
		lbOutput.setText(selectedData.isEnabled() ? "Aliquota abilitata" : "Aliquota disabilitata");
		refresh();
	}

	@Override
	public void populateTable() {
		vats = DAO.getAll(VAT.class);
		if(!vats.isEmpty()) {
			model.addRows(vats);
		} else {
			lbOutput.setText("Lista IVA vuota");
		}
	}

	@Override
	public void clearTxfFields() {
		txfAmount.setText(0);
	}

	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting())
					return;
					
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;
					
				selectedData = model.getObjAt(selectedRow);
				if (selectedData.getId() == null || !selectedData.isEnabled())
					return;

				double amount = selectedData.getAmount();
				txfAmount.setText(amount);
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			InputValidator.validateNumber(txfAmount, 0, Integer.MAX_VALUE);
		} catch (InputValidatorException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage(), "Dati non validi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
