package template;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.centro.estetico.bitcamp.VAT;

import DAO.VATDao;

public class VATPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtSearchBar;
	private JTextField txtAmount;
	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;
	private JLabel msgLbl;
	private int selectedId;

	/**
	 * Create the panel.
	 */
	public VATPanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("VAT");
		JLabel titleTab = new JLabel("ALIQUOTE IVA");
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
		String[] columnNames = { "id", "IVA%", "STATO" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(tableModel);
		// Listener della tabella per pescare i nomi che servono
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						selectedId = Integer.parseInt(String.valueOf(table.getValueAt(selectedRow, 0)));
						String amountString = String.valueOf(table.getValueAt(selectedRow, 1) + "%");

						// Il listener ascolta la riga selezionata e la usa per popolare i campi
						txtAmount.setText(amountString);
						table.clearSelection();
					}
				}
			}
		});

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

		txtSearchBar = new JTextField();
		txtSearchBar.setColumns(10);
		txtSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txtSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txtSearchBar);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		btnFilter.addActionListener(e -> populateTableByFilter());
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Insert.png")));
//		btnInsert.setBounds(720, 8, 40, 30);
		btnInsert.setBounds(770, 8, 40, 30);
		btnInsert.addActionListener(e -> createVat());
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/Update.png")));
//		btnUpdate.setBounds(770, 8, 40, 30);
		btnUpdate.setBounds(820, 8, 40, 30);
		btnUpdate.addActionListener(e -> updateVat());
		containerPanel.add(btnUpdate);

//		JButton btnDelete = new JButton("");
//		btnDelete.setOpaque(false);
//		btnDelete.setContentAreaFilled(false);
//		btnDelete.setBorderPainted(false);
//		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/delete.png")));
//		btnDelete.setBounds(820, 8, 40, 30);
//		btnDelete.addActionListener(e -> disableVat());
//		containerPanel.add(btnDelete);

		JButton btnHystorical = new JButton("");
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/cartellina.png")));
		btnHystorical.setBounds(870, 8, 40, 30);
		btnHystorical.addActionListener(e -> populateTable());
		containerPanel.add(btnHystorical);
		
		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/iconeGestionale/disable.png")));
		btnDisable.setBounds(920, 8, 40, 30);
		btnDisable.addActionListener(e -> disableVat());
		containerPanel.add(btnDisable);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 276);
		containerPanel.add(outputPanel);

		// label e textfield degli input
		JLabel lblAmount = new JLabel("Valore Aliquota:");
		lblAmount.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblAmount.setBounds(43, 437, 170, 14);
		add(lblAmount);

		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBounds(209, 436, 220, 20);
		add(txtAmount);

		msgLbl = new JLabel("");
		msgLbl.setBounds(389, 606, 625, 16);
		add(msgLbl);
		populateTable();

	}

	private void populateTable() {
		clearTable();
		List<VAT> vats = VATDao.getAllVAT();
		if (vats.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano aliquote presenti", "" });
			return;
		}
		for (VAT v : vats) {
//			if (v.isEnabled())
				tableModel.addRow(new String[] { String.valueOf(v.getId()), String.valueOf(v.getAmount()), v.isEnabled() ? "ABILITATA" : "DISABILITATA" });
		}
	}

	private void clearTable() {
		tableModel.getDataVector().removeAllElements();
		revalidate();
	}

	private void populateTableByFilter() {
		msgLbl.setText("");
		if (txtSearchBar.getText().isBlank() || txtSearchBar.getText().isEmpty()) {
			msgLbl.setText("Inserire un filtro!");
			return;
		}
		clearTable();
		List<VAT> vats = VATDao.getAllVAT();
		if (vats.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano aliquote presenti", "" });
			return;
		}
		for (VAT v : vats) {
			if (v.isEnabled() && String.valueOf(v.getAmount()).concat("%").contains(txtSearchBar.getText())) {
				tableModel.addRow(new String[] { String.valueOf(v.getId()), String.valueOf(v.getAmount()) });
			}
		}
		txtSearchBar.setText("");
	}

	private void createVat() {
		if (isDataValid()) {
			double amount = getAmountFromString(txtAmount.getText());
			VAT vat = new VAT(amount);
			vat = VATDao.insertVAT(vat).get();
			System.out.println(vat);
			msgLbl.setText("Aliquota inserita nel database");
			populateTable();
		}
	}

	private void updateVat() {
		if (isDataValid() && selectedId > 0) {
			double amount = getAmountFromString(txtAmount.getText());
			VATDao.updateVAT(selectedId, new VAT(amount));
			msgLbl.setText("Aliquota modificata correttamente");
			populateTable();
		}
	}

//	private void deleteVat() {
//		if(selectedId > 0) {
//			msgLbl.setText("");
//			VAT.toggleEnabledData(selectedId);
//			msgLbl.setText("Prodotto rimosso correttamente");
//			populateTable();
//		}
//	}
	
	private void disableVat() {
		if(selectedId > 0) {
			msgLbl.setText("");
			VATDao.toggleEnabledVAT(selectedId);
			msgLbl.setText("Stato Aliquota modificato");
			populateTable();
		}
	}

	private boolean isDataValid() {
		try {
			double amount = getAmountFromString(txtAmount.getText());
			if (amount <= 0.1 || amount >= 100) {
				msgLbl.setText("L'aliquota deve essere compresa tra 0.1 e 100 esclusi");
				return false;
			} else if(VATDao.existByAmount(amount)) {
				msgLbl.setText("Aliquota già presente nel database");
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			msgLbl.setText("L'aliquota deve essere un numero valido");
			return false;
		}
		return true;
	}

	public JTextField getTxfSearchBar() {
		return txtSearchBar;
	}

	public void setTxfSearchBar(JTextField txfSearchBar) {
		this.txtSearchBar = txfSearchBar;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	private double getAmountFromString(String amountString) {
		return amountString.endsWith("%") 
			? Double.parseDouble(amountString.substring(0, amountString.length() - 1)) 
			: Double.parseDouble(amountString);
	}
}
