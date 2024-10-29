package com.bitcamp.centro.estetico.gui;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.JSplitLbTxf;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class VATPanel extends BasePanel<VAT> {

	private static VAT_DAO vat_DAO = VAT_DAO.getInstance();
	
	private static final long serialVersionUID = 1L;
	private static int id;
	private static double amount;
	private static boolean isEnabled;

	private static JSplitLbTxf txfAmount;


	public VATPanel() {

		setSize(1024, 768);
		setName("IVA");
		setTitle("ALIQUOTE IVA");

		table.getSelectionModel().addListSelectionListener(getTableListSelectionListener());
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(vatModel));
		table.setModel(vatModel);

		txfAmount = new JSplitLbTxf("Percentuale", new JFormattedTextField(NumberFormat.getInstance()));
		actionsPanel.add(txfAmount);
		
	}

	private void populateTableByFilter() {
		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}
		clearTable(table);
		List<VAT> vats = vat_DAO.getAll();
		if (vats.isEmpty()) {
			vatModel.addRow(new String[] { "Sembra non ci siano aliquote presenti", "" });
			return;
		}
		vats.parallelStream()
		.filter(v -> v.isEnabled() && v.getAmount() == Double.parseDouble(txfSearchBar.getText()))
		.forEach(v -> vatModel.addRow(v.toTableRow()));
		
		txfSearchBar.setText("");
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
		vat_DAO.insert(new VAT(amount));
		refreshTable();
	}

	@Override
	public void updateElement() {
		if (!isDataValid() && id >= 0) return;
		double amount = Double.parseDouble(txfAmount.getText());
		lbOutput.setText("Aliquota modificata");
		vat_DAO.update(id, new VAT(amount));
		refreshTable();
	}

	@Override
	public void deleteElement() {
		if (table.getSelectedRow() < 0) return;
		lbOutput.setText("Aliquota rimossa");
		vat_DAO.delete(id);
		refreshTable();
	}

	@Override
	public void disableElement() {
		if (table.getSelectedRow() < 0) return;
		vat_DAO.toggle(id);
		lbOutput.setText(!isEnabled ? "Aliquota abilitata" : "Aliquota disabilitata");
		refreshTable();
	}

	@Override
	public void populateTable() {
		isRefreshing = true;
		vats = vat_DAO.getAll();
		if (vats.isEmpty()) return;
		clearTable(table);
		vats.parallelStream().forEach(v -> vatModel.addRow(v.toTableRow()));
		isRefreshing = false;
	}

	@Override
	public void clearTxfFields() {
		txfAmount.setText("null");
	}


	@Override
	public ListSelectionListener getTableListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) return;
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) return;

				var values = getRowMap(table, vatModel);

				id = (int) values.get( "ID");
				amount = (double) values.get( "Percentuale");
				txfAmount.setText(amount);
				isEnabled = (boolean) values.get( "Abilitato");

				lbOutput.setText("");
			}
		};
	}

	@Override
	public boolean isDataValid() {
		try {
			inputValidator.validateVAT(txfAmount);
		} catch (inputValidatorException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Dati non validi",
			JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
