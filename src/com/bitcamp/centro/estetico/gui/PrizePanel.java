package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.PrizeDAO;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.PrizeType;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;
import com.bitcamp.centro.estetico.utils.placeholderHelper;

public class PrizePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfThreshold;
	private JTextField txfSearchbar;
	private JTextField txfNomePremio;
	private JTextField txfImportoPremio;
	private JComboBox<PrizeType> cbxTipoPremio;
	private JButton btnConfermaInserisci;
	private JButton btnConfermaModifica;
	private JButton btnAnnulla;
	private JTable table;
	private int selectedRow = -1;

	// Modello della tabella
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public PrizePanel() {
		setName("Premi");
		setLayout(null);
		setSize(1024, 768);

		JLabel lblGestionePremi = new JLabel("GESTIONE  PREMI");
		lblGestionePremi.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblGestionePremi.setBounds(415, 11, 179, 32);
		add(lblGestionePremi);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "ID", "Nome", "Punti Necessari", "Tipo", "Importo" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		table = new JTable(tableModel);

		// Nascondi la colonna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);


		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 960, 276);
		containerPanel.add(outputPanel);

		txfSearchbar = new JTextField();
		placeholderHelper.addPlaceholder(txfSearchbar, "Ricerca per nome o punti necessari");
		txfSearchbar.setEnabled(false);
		txfSearchbar.setColumns(10);
		txfSearchbar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchbar.setBounds(23, 14, 207, 24);
		containerPanel.add(txfSearchbar);

		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {

		    @Override
			public void actionPerformed(ActionEvent e) {
		    	txfSearchbar.setEnabled(true);

		        String searchText = txfSearchbar.getText().trim();


		        List<Prize> prizes = PrizeDAO.searchActivePrizes(searchText);

		        // Aggiorna la tabella con i risultati della ricerca
		        tableModel.setRowCount(0);
		        for (Prize prize : prizes) {
		            Object[] rowData = new Object[5];
		            rowData[0] = prize.getId();
		            rowData[1] = prize.getName();
		            rowData[2] = prize.getThreshold();
		            rowData[3] = prize.getType().toString();
		            rowData[4] = prize.getAmount();
		            tableModel.addRow(rowData);
		        }
		    }
		});



		btnSearch.setIcon(new ImageIcon(PrizePanel.class.getResource("/iconeGestionale/searchIcon.png")));
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setBounds(252, 8, 40, 30);
		containerPanel.add(btnSearch);

		JButton btnInsert = new JButton("");
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableInputs();
				btnConfermaInserisci.setVisible(true);
				btnAnnulla.setVisible(true);
			}
		});
		btnInsert.setIcon(new ImageIcon(PrizePanel.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(793, 8, 40, 30);
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(e -> {
			selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				enableInputs();
				populateInputsFromTable();
				btnConfermaInserisci.setVisible(false);
				btnConfermaModifica.setVisible(true);
				btnAnnulla.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Seleziona una riga da aggiornare.");
			}
		});
		btnUpdate.setIcon(new ImageIcon(PrizePanel.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(843, 8, 40, 30);
		containerPanel.add(btnUpdate);

		JButton btnDisable = new JButton("");
		btnDisable.setIcon(new ImageIcon(PrizePanel.class.getResource("/iconeGestionale/disattivo.png")));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(893, 8, 40, 30);
		containerPanel.add(btnDisable);

		btnDisable.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent e) {
		        // Ottieni la riga selezionata
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow >= 0) {
		            // Ottieni l'ID del premio dalla tabella
		            int id = (int) table.getValueAt(selectedRow, 0);

		            // Recupera il premio dal database
		            Optional<Prize> premioOpt = PrizeDAO.getPrize(id);
		            if (!premioOpt.isPresent()) {
		                JOptionPane.showMessageDialog(null, "Premio non trovato.");
		                return;
		            }

		            Prize premioCorrente = premioOpt.get();


		            int result = PrizeDAO.toggleEnabledPrize(premioCorrente);
		            if (result > 0) {
		                JOptionPane.showMessageDialog(null, "Premio abilitato/disabilitato correttamente.");
		                loadPrizesToTable();  // Aggiorna la tabella per riflettere il cambiamento
		            } else {
		                JOptionPane.showMessageDialog(null, "Errore durante l'operazione.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Seleziona un premio dalla tabella.");
		        }
		    }
		});


		JButton btnHystorical = new JButton("");
		btnHystorical.setIcon(new ImageIcon(PrizePanel.class.getResource("/iconeGestionale/cartellina.png")));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(943, 8, 40, 30);
		containerPanel.add(btnHystorical);

		btnHystorical.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent e) {

		        loadActivePrizesToTable();  // Mostra solo i premi attivi
		    }
		});


		JLabel lblNomePremio = new JLabel("Nome Premio:");
		lblNomePremio.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNomePremio.setBounds(320, 439, 170, 14);
		add(lblNomePremio);

		txfNomePremio = new JTextField();
		txfNomePremio.setEnabled(false);
		txfNomePremio.setColumns(10);
		txfNomePremio.setBounds(514, 438, 220, 20);
		add(txfNomePremio);

		cbxTipoPremio = new JComboBox<>(PrizeType.values());
		cbxTipoPremio.setEnabled(false);
		cbxTipoPremio.setBounds(514, 485, 220, 22);
		add(cbxTipoPremio);

		JLabel lblTipoPremio = new JLabel("Tipo Premio");
		lblTipoPremio.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblTipoPremio.setBounds(320, 487, 170, 14);
		add(lblTipoPremio);

		JLabel lblPuntiNecessari = new JLabel("Punti necessari:");
		lblPuntiNecessari.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPuntiNecessari.setBounds(320, 533, 170, 17);
		add(lblPuntiNecessari);

		txfThreshold = new JTextField();
		txfThreshold.setEnabled(false);
		txfThreshold.setColumns(10);
		txfThreshold.setBounds(514, 533, 220, 20);
		add(txfThreshold);

		JLabel lblImporto = new JLabel("Importo:");
		lblImporto.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblImporto.setBounds(320, 586, 170, 14);
		add(lblImporto);

		txfImportoPremio = new JTextField();
		txfImportoPremio.setEnabled(false);
		txfImportoPremio.setColumns(10);
		txfImportoPremio.setBounds(514, 585, 220, 20);
		add(txfImportoPremio);

		btnConfermaInserisci = new JButton("Inserisci");
		btnConfermaInserisci.setBounds(400, 644, 100, 30);
		btnConfermaInserisci.setVisible(false);
		add(btnConfermaInserisci);

		btnConfermaInserisci.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent e) {
		        if (!validateInputs()) {
		            JOptionPane.showMessageDialog(null, "Errore di validazione nei campi");
		            return;
		        }

		        // Recupera i valori dai campi
		        String nomePremio = txfNomePremio.getText();
		        int punti = Integer.parseInt(txfThreshold.getText());
		        PrizeType tipoPremio = (PrizeType) cbxTipoPremio.getSelectedItem();
		        double importo = Double.parseDouble(txfImportoPremio.getText());

		        Prize nuovoPremio = new Prize(
		            nomePremio,
		            punti,
		            tipoPremio,
		            LocalDate.now(),
		            importo
		        );

		        // Inserimento nel database
		        if (PrizeDAO.insertPrize(nuovoPremio).isPresent()) {
		            JOptionPane.showMessageDialog(null, "Premio inserito correttamente!");
		        } else {
		            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento del premio");
		        }

		        clearFields();
		        disableInputs();
		        btnConfermaInserisci.setVisible(false);
		        loadActivePrizesToTable();  // Aggiorna la tabella
		    }
		});




		btnConfermaModifica = new JButton("Modifica");
		btnConfermaModifica.setBounds(400, 644, 100, 30);
		btnConfermaModifica.setVisible(false);
		add(btnConfermaModifica);

		btnConfermaModifica.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent e) {
		        if (!validateInputs() || selectedRow < 0) {
		            JOptionPane.showMessageDialog(null, "Errore di validazione o nessuna riga selezionata");
		            return;
		        }

		        // Recupera i valori dai campi
		        String nomePremio = txfNomePremio.getText();
		        int punti = Integer.parseInt(txfThreshold.getText());
		        PrizeType tipoPremio = (PrizeType) cbxTipoPremio.getSelectedItem();
		        double importo = Double.parseDouble(txfImportoPremio.getText());

		        // Ottieni l'ID del premio selezionato
		        int id = (int) table.getValueAt(selectedRow, 0);

		        // Recupera il premio dal database
		        Optional<Prize> premioOpt = PrizeDAO.getPrize(id);
		        if (!premioOpt.isPresent()) {
		            JOptionPane.showMessageDialog(null, "Premio non trovato.");
		            return;
		        }

		        Prize premioCorrente = premioOpt.get();

		        Prize premioModificato = new Prize(
		            nomePremio,
		            punti,
		            tipoPremio,
		            Optional.ofNullable(premioCorrente.getExpirationDate()).orElse(LocalDate.now()),
		            importo
		        );

		        if (PrizeDAO.updatePrize(id, premioModificato) > 0) {
		            JOptionPane.showMessageDialog(null, "Premio modificato correttamente!");
		        } else {
		            JOptionPane.showMessageDialog(null, "Errore durante la modifica del premio");
		        }

		        clearFields();
		        disableInputs();
		        btnConfermaModifica.setVisible(false);
		        loadActivePrizesToTable();  // Aggiorna la tabella
		    }
		});





		btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(514, 644, 100, 30);
		btnAnnulla.setVisible(false);
		btnAnnulla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
				disableInputs();
				btnConfermaInserisci.setVisible(false);
				btnConfermaModifica.setVisible(false);
				btnAnnulla.setVisible(false);
			}
		});
		add(btnAnnulla);
	}

	// Metodo per caricare i premi nella tabella
	private void loadPrizesToTable() {
	    tableModel.setRowCount(0);  // Resetta la tabella
	    List<Prize> prizes = PrizeDAO.getAllPrizes();

	    for (Prize prize : prizes) {
	        Object[] rowData = new Object[5];
	        rowData[0] = prize.getId();
	        rowData[1] = prize.getName();
	        rowData[2] = prize.getThreshold();
	        rowData[3] = prize.getType().toString();
	        rowData[4] = prize.getAmount();

	        tableModel.addRow(rowData);
	    }
	}

	void loadActivePrizesToTable() {
	    tableModel.setRowCount(0);  // Resetta la tabella
	    List<Prize> prizes = PrizeDAO.getAllActivePrizes();  // Ottieni solo i premi attivi
	    for (Prize prize : prizes) {
	        Object[] rowData = new Object[6];
	        rowData[0] = prize.getId();
	        rowData[1] = prize.getName();
	        rowData[2] = prize.getThreshold();
	        rowData[3] = prize.getType().toString();
	        rowData[4] = prize.getAmount();
	        rowData[5] = prize.getExpirationDate() != null ? prize.getExpirationDate().toString() : "N/A";

	        tableModel.addRow(rowData);
	    }
	}




	// Metodo per popolare i campi di input dalla riga selezionata
	private void populateInputsFromTable() {
	    // Recupera i valori dalla tabella e li imposta nei campi di input
	    txfNomePremio.setText((String) table.getValueAt(selectedRow, 1));
	    txfThreshold.setText(String.valueOf(table.getValueAt(selectedRow, 2)));

	    String tipoPremioString = (String) table.getValueAt(selectedRow, 3);
	    PrizeType tipoPremio = PrizeType.toEnum(tipoPremioString);  // Conversione corretta

	    cbxTipoPremio.setSelectedItem(tipoPremio);
	    txfImportoPremio.setText(String.valueOf(table.getValueAt(selectedRow, 4)));
	}


	// Metodo per svuotare i campi di input
	private void clearFields() {
		txfNomePremio.setText("");
		txfImportoPremio.setText("");
		txfThreshold.setText("");
		cbxTipoPremio.setSelectedIndex(0);
	}

	// Metodo per disattivare i campi di input
	private void disableInputs() {
		txfNomePremio.setEnabled(false);
		txfImportoPremio.setEnabled(false);
		txfThreshold.setEnabled(false);
		cbxTipoPremio.setEnabled(false);
	}

	// Metodo per attivare i campi di input
	private void enableInputs() {
		txfNomePremio.setEnabled(true);
		txfImportoPremio.setEnabled(true);
		txfThreshold.setEnabled(true);
		cbxTipoPremio.setEnabled(true);
	}

	// Metodo per validare gli input
	private boolean validateInputs() {   
	    try {
			inputValidator.validateAlphanumeric(txfNomePremio.getText(), "Nome Premio"); // Validazione del nome premio
	        double importo = Double.parseDouble(txfImportoPremio.getText()); // Validazione dell'importo premio
	        inputValidator.validateNumber(importo, 0, 10000); // Valore massimo arbitrario per evitare errori
	    } catch (inputValidatorException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage());
	        return false;
	    }

	    // Validazione della soglia dei punti
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