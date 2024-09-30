package template;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TreatmentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtPrice;
	private JTextField txfSearchBar;
	private JTextField txfName;
	private JComboBox<String> cBoxIVA;
	private JTextField txtDuration;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public TreatmentPanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("Trattamenti");
		JLabel titleTab = new JLabel("GESTIONE TRATTAMENTI");
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 206, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = {"Nome trattamento","Prezzo","IVA%","Durata"};
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(tableModel);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/searchIcon.png")));
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);

		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txfSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setBounds(720, 8, 40, 30);
		containerPanel.add(btnInsert);

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				// Raccogliere i dati dai campi di testo
//				String nome = txfName.getText();
//				String prezzo = txtPrice.getText();
//				String iva = (String) cBoxIVA.getSelectedItem();
//				String durata = txtDuration.getText();
//
//				//aggiungere il check dei campi in modo da non aggiungere una nuova riga
//				// Aggiungere una nuova riga alla tabella
//				Object[] rowData = { nome, prezzo,  iva, durata};
//				tableModel.addRow(rowData); // Aggiunge la riga alla tabella

				// Pulisci i campi dopo l'inserimento
				txfName.setText("");
				txtPrice.setText("");
				txtDuration.setText("");
				cBoxIVA.setSelectedIndex(0);
			}

		});

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setBounds(770, 8, 40, 30);
		containerPanel.add(btnUpdate);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ottieni l'indice della riga selezionata
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) { // Verifica che una riga sia stata selezionata
					// Riempie i campi con i valori della riga selezionata
					txfName.setText((String) tableModel.getValueAt(selectedRow, 0));
					txtPrice.setText((String) tableModel.getValueAt(selectedRow, 1));
					txtDuration.setText((String) tableModel.getValueAt(selectedRow, 5));
					cBoxIVA.setSelectedItem(tableModel.getValueAt(selectedRow, 6));
				}
			}
		});

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/delete.png")));
		btnDelete.setBounds(820, 8, 40, 30);
		containerPanel.add(btnDelete);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/disable.png")));
		btnDisable.setBounds(920, 8, 40, 30);
		containerPanel.add(btnDisable);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 276);
		containerPanel.add(outputPanel);

		JButton btnHystorical = new JButton("");
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/cartellina.png")));
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome trattamento:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(209, 436, 220, 20);
		add(txfName);

		JLabel lblPrice = new JLabel("Prezzo:");
		lblPrice.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPrice.setBounds(43, 474, 170, 17);
		add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(209, 474, 220, 20);
		add(txtPrice);

		JLabel lblIVa = new JLabel("IVA:");
		lblIVa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblIVa.setBounds(43, 513, 170, 14);
		add(lblIVa);

		String[]IVAs= {"Seleziona IVA"};
		cBoxIVA = new JComboBox<String>(IVAs);
		cBoxIVA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		cBoxIVA.setBounds(209, 511, 220, 22);
		add(cBoxIVA);

		JLabel lblDuration = new JLabel("Durata (minuti):");
		lblDuration.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblDuration.setBounds(43, 553, 170, 14);
		add(lblDuration);

		txtDuration = new JTextField();
		txtDuration.setColumns(10);
		txtDuration.setBounds(209, 552, 220, 20);
		add(txtDuration);
		
		JButton productButton = new JButton("Seleziona i prodotti");
		productButton.setBounds(530, 432, 166, 29);
		productButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProductSelector();
			}
		});
		add(productButton);

	}

	public JTextField getTxtPrice() {
		return txtPrice;
	}

	public void setTxtPrice(JTextField txtPrice) {
		this.txtPrice = txtPrice;
	}

	public JTextField getTxfSearchBar() {
		return txfSearchBar;
	}

	public void setTxfSearchBar(JTextField txfSearchBar) {
		this.txfSearchBar = txfSearchBar;
	}

	public JTextField getTxfName() {
		return txfName;
	}

	public void setTxfName(JTextField txfName) {
		this.txfName = txfName;
	}

	public JComboBox<String> getcBoxIVA() {
		return cBoxIVA;
	}

	public void setcBoxIVA(JComboBox<String> cBoxIVA) {
		this.cBoxIVA = cBoxIVA;
	}

	public JTextField getTxtDuration() {
		return txtDuration;
	}

	public void setTxtDuration(JTextField txtDuration) {
		this.txtDuration = txtDuration;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
}
