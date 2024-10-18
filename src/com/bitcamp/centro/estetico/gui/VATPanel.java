package com.bitcamp.centro.estetico.gui;

import java.awt.Font;
import java.util.List;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class VATPanel extends BasePanel<VAT> {

	private static final long serialVersionUID = 1L;
	private static JTextField txfAmount;
	private static int selectedId;

	public VATPanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("VAT");
		setTitle("ALIQUOTE IVA");
		scrollPane.setBounds(23, 60, 959, 276);
		btnSearch.setBounds(206, 8, 40, 30);
		txfSearchBar.setBounds(23, 14, 168, 24);
		btnFilter.setBounds(256, 8, 40, 30);
		btnInsert.setBounds(770, 8, 40, 30);
		btnUpdate.setBounds(820, 8, 40, 30);
		btnDisable.setBounds(920, 8, 40, 30);
		lbTitle.setBounds(415, 11, 206, 32);

		table.getSelectionModel().addListSelectionListener(getListSelectionListener());
		table.setModel(vatModel);

		JLabel lblAmount = new JLabel("Valore Aliquota:");
		lblAmount.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblAmount.setBounds(43, 437, 170, 14);
		add(lblAmount);

		txfAmount = new JTextField();
		txfAmount.setColumns(10);
		txfAmount.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		txfAmount.setBounds(209, 436, 220, 20);
		add(txfAmount);
		
	}

	private void populateTableByFilter() {
		lbOutput.setText("");
		if (txfSearchBar.getText().isBlank() || txfSearchBar.getText().isEmpty()) {
			lbOutput.setText("Inserire un filtro!");
			return;
		}
		clearTable();
		List<VAT> vats = VATDao.getAllVAT();
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
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	Optional<VAT> insertElement() {
		double amount = Double.parseDouble(txfAmount.getText());
		lbOutput.setText("Aliquota inserita");
		refreshTable();
		return VATDao.insertVAT(new VAT(amount));
	}

	@Override
	int updateElement() {
		if (!isDataValid() && selectedId >= 0) return -1;
		double amount = Double.parseDouble(txfAmount.getText());
		lbOutput.setText("Aliquota modificata");
		refreshTable();
		return VATDao.updateVAT(selectedId, new VAT(amount));
	}

	@Override
	int deleteElement() {
		lbOutput.setText("Aliquota rimossa");
		refreshTable();
		return VATDao.deleteVAT(selectedId);
	}

	@Override
	int disableElement() {
		if(selectedId <= -1) return -1;
		lbOutput.setText("Aliquota disabilitata");
		return VATDao.toggleEnabledVAT(selectedId);
	}

	@Override
	void populateTable() {
		if (vats.isEmpty()) return;
		clearTable();
		vats.parallelStream().forEach(v -> vatModel.addRow(v.toTableRow()));
	}

	@Override
	void clearTxfFields() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}


	@Override
	ListSelectionListener getListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (isRefreshing) return;

				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					selectedId = (int) vatModel.getValueAt(selectedRow, 0);
				}
			}
		};
	}

	@Override
	boolean isDataValid() {
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
