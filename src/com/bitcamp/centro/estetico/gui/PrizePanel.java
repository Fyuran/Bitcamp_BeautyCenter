package com.bitcamp.centro.estetico.gui;

import java.awt.Font;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.PrizeDAO;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.PrizeType;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class PrizePanel extends BasePanel<Prize> {

	private static final long serialVersionUID = 1L;
	private JTextField txfThreshold;
	private JTextField txfName;
	private JTextField txfPrice;
	private JComboBox<PrizeType> typeComboBox;
	private int selectedRow = -1;


	/**
	 * Create the panel.
	 */
	public PrizePanel() {
		setName("Premi");
		setLayout(null);
		setSize(1024, 768);
		setTitle("GESTIONE  PREMI");

		txfSearchBar.setBounds(23, 14, 207, 24);
		btnSearch.setBounds(252, 8, 40, 30);
		btnInsert.setBounds(793, 8, 40, 30);
		btnUpdate.setBounds(843, 8, 40, 30);
		btnDisable.setBounds(893, 8, 40, 30);
		
		JLabel lbName = new JLabel("Nome Premio:");
		lbName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbName.setBounds(320, 439, 170, 14);
		add(lbName);

		txfName = new JTextField();
		txfName.setEnabled(false);
		txfName.setColumns(10);
		txfName.setBounds(514, 438, 220, 20);
		add(txfName);

		typeComboBox = new JComboBox<>(PrizeType.values());
		typeComboBox.setEnabled(false);
		typeComboBox.setBounds(514, 485, 220, 22);
		add(typeComboBox);

		JLabel lbType = new JLabel("Tipo Premio");
		lbType.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbType.setBounds(320, 487, 170, 14);
		add(lbType);

		JLabel lbThreshold = new JLabel("loyaltyPoints necessari:");
		lbThreshold.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbThreshold.setBounds(320, 533, 170, 17);
		add(lbThreshold);

		txfThreshold = new JTextField();
		txfThreshold.setEnabled(false);
		txfThreshold.setColumns(10);
		txfThreshold.setBounds(514, 533, 220, 20);
		add(txfThreshold);

		JLabel lbPrice = new JLabel("price:");
		lbPrice.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lbPrice.setBounds(320, 586, 170, 14);
		add(lbPrice);

		txfPrice = new JTextField();
		txfPrice.setEnabled(false);
		txfPrice.setColumns(10);
		txfPrice.setBounds(514, 585, 220, 20);
		add(txfPrice);
	}

	// Metodo per caricare i premi nella tabella
	private void loadPrizesToTable() {
	    prizeModel.setRowCount(0);  // Resetta la tabella
	    List<Prize> prizes = PrizeDAO.getAllPrizes();
		prizes.parallelStream()
		.forEach(p -> prizeModel.addRow(p.toTableRow()));
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	Optional<Prize> insertElement() {
		if (!isDataValid()) {
			JOptionPane.showMessageDialog(null, "Errore di validazione nei campi");
			return Optional.empty();
		}

		// Recupera i valori dai campi
		String name = txfName.getText();
		int loyaltyPoints = Integer.parseInt(txfThreshold.getText());
		PrizeType type = (PrizeType) typeComboBox.getSelectedItem();
		double price = Double.parseDouble(txfPrice.getText());

		Prize nuovoPremio = new Prize(
			name,
			loyaltyPoints,
			type,
			LocalDate.now(),
			price
		);

		// Inserimento nel database
		
		clearTxfFields();
		refreshTable();  // Aggiorna la tabella
		
		return PrizeDAO.insertPrize(nuovoPremio);
	}

	@Override
	int updateElement() {
		if (!isDataValid() || selectedRow < 0) {
			JOptionPane.showMessageDialog(null, "Errore di validazione o nessuna riga selezionata");
			return -1;
		}

		// Recupera i valori dai campi
		String name = txfName.getText();
		int loyaltyPoints = Integer.parseInt(txfThreshold.getText());
		PrizeType type = (PrizeType) typeComboBox.getSelectedItem();
		double price = Double.parseDouble(txfPrice.getText());

		// Ottieni l'ID del premio selezionato
		int id = (int) prizeModel.getValueAt(selectedRow, 0);

		// Recupera il premio dal database
		Optional<Prize> premioOpt = PrizeDAO.getPrize(id);
		if (!premioOpt.isPresent()) {
			JOptionPane.showMessageDialog(null, "Premio non trovato.");
			return -1;
		}

		Prize premioCorrente = premioOpt.get();

		Prize premioModificato = new Prize(
			name,
			loyaltyPoints,
			type,
			Optional.ofNullable(premioCorrente.getExpirationDate()).orElse(LocalDate.now()),
			price
		);

		
		clearTxfFields();
		refreshTable();
		return PrizeDAO.updatePrize(id, premioModificato);
	}

	@Override
	int deleteElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteElement'");
	}

	@Override
	int disableElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'disableElement'");
	}

	@Override
	void populateTable() {
		prizeModel.setRowCount(0);  // Resetta la tabella
		prizes.parallelStream()
		.filter(p -> p.isEnabled())
		.forEach(p -> prizeModel.addRow(p.toTableRow()));
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
				txfName.setText((String) prizeModel.getValueAt(selectedRow, 1));
				txfThreshold.setText(String.valueOf(prizeModel.getValueAt(selectedRow, 2)));
		
				String tipoPremioString = (String) prizeModel.getValueAt(selectedRow, 3);
				PrizeType type = PrizeType.toEnum(tipoPremioString);  // Conversione corretta
		
				typeComboBox.setSelectedItem(type);
				txfPrice.setText(String.valueOf(prizeModel.getValueAt(selectedRow, 4)));
			}
		};
	}

	@Override
	boolean isDataValid() {
		try {
			inputValidator.validateAlphanumeric(txfName, "Nome Premio"); // Validazione del nome premio
	        inputValidator.validateNumber(txfPrice, 0, 10000); // Valore massimo arbitrario per evitare errori
	    } catch (inputValidatorException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage());
	        return false;
	    }

	    // Validazione della soglia dei loyaltyPoints
	    try {
	        int threshold = Integer.parseInt(txfThreshold.getText());
	        if (threshold < 0) {
	            JOptionPane.showMessageDialog(null, "La soglia dei loyaltyPoints deve essere un numero positivo.");
	            return false;
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "La soglia dei loyaltyPoints non è valida. Deve essere un numero intero.");
	        return false;
	    }


	    return true;
	}

}