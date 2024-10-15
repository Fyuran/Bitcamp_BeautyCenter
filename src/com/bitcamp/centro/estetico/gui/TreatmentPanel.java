package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.models.VAT;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class TreatmentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtPrice;
	private JTextField txtSearchBar;
	private JTextField txtName;
	private JComboBox<String> cBoxIVA;
	private JTextField txtDuration;
	private List<Product> products;
	private JLabel msgLbl;
	private int selectedId;
	private DefaultTableModel productModel;

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
		products = new ArrayList<>();

		JLabel titleTab = new JLabel("GESTIONE TRATTAMENTI");
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 220, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "ID", "Nome trattamento", "Prezzo", "IVA%", "Durata" };
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
						clearProdutTable();
						selectedId = Integer.parseInt(String.valueOf(table.getValueAt(selectedRow, 0)));
						String name = String.valueOf(table.getValueAt(selectedRow, 1));
						String price = String.valueOf(String.valueOf(table.getValueAt(selectedRow, 2)));
						String vat = String.valueOf(table.getValueAt(selectedRow, 3) + "%");
						String duration = String.valueOf(table.getValueAt(selectedRow, 4));
						products=TreatmentDAO.getProductsOfTreatment(selectedId);
						System.out.println(products.size());
						populateProductsTable(products);

						System.out.println(vat);
						// double vat=Double.parseDouble(vatString.substring(0,vatString.length()-1));

						// { "ID", "Nome trattamento", "Prezzo", "IVA%", "Durata" };
						// Il listener ascolta la riga selezionata e la usa per popolare i campi

						txtName.setText(name);
						cBoxIVA.setSelectedItem(vat);
						txtPrice.setText(price);
						txtPrice.setText(price);
						txtDuration.setText(duration);
						table.clearSelection();

					}
				}
			}
		});


		//tabella prodotti per trattamento
		 String[] columnProductNames = {"ID","Prodotto", "Categoria"};
			productModel = new DefaultTableModel(columnProductNames, 0);
			JTable productTable = new JTable(productModel);
			productTable.setEnabled(false);

			JScrollPane productScrollPane = new JScrollPane(productTable);
			productScrollPane.setBounds(577, 474, 437, 181);
			add(productScrollPane);


		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 276);
		containerPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
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
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		btnFilter.addActionListener(e -> populateTableByFilter());
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Insert.png")));
		btnInsert.setBounds(720, 8, 40, 30);
		btnInsert.addActionListener(e -> createTreatment());
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/Update.png")));
		btnUpdate.setBounds(770, 8, 40, 30);
		btnUpdate.addActionListener(e -> updateTreatment());
		containerPanel.add(btnUpdate);

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete.png")));
		btnDelete.setBounds(820, 8, 40, 30);
		btnDelete.addActionListener(e -> deleteTreatment());
		containerPanel.add(btnDelete);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/disable.png")));
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
		btnHystorical.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/cartellina.png")));
		btnHystorical.setBounds(870, 8, 40, 30);
		btnHystorical.addActionListener(e -> populateTable());
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome trattamento*:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(209, 436, 220, 20);
		add(txtName);

		JLabel lblPrice = new JLabel("Prezzo:");
		lblPrice.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPrice.setBounds(43, 474, 170, 17);
		add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(209, 474, 220, 20);
		add(txtPrice);

		JLabel lblIVa = new JLabel("IVA*:");
		lblIVa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblIVa.setBounds(43, 513, 170, 14);
		add(lblIVa);

		List<VAT> ivas = VATDao.getAllVAT();
		int i = 0;
		String[] ivasToString = new String[ivas.size()];
		for (VAT iva : ivas) {
			ivasToString[i] = iva.toString();
			i++;
		}
		cBoxIVA = new JComboBox<>(ivasToString);
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
				new ProductSelector(TreatmentPanel.this);
			}
		});
		add(productButton);
		msgLbl = new JLabel("");
		msgLbl.setBounds(248, 413, 625, 16);
		add(msgLbl);


		populateTable();

	}

	public void getProducts(List<Integer> productIds) {
		products.clear();
		for (int id : productIds) {
			Product p = ProductDAO.getProduct(id).get();
			products.add(p);
			System.out.println(id);
		}
		for (Product p : products) {
			System.out.println(p);
		}
		populateProductsTable(products);
		msgLbl.setText("Prodotti selezionati correttamente");
	}

	void populateTable() {
		clearTable();
		List<Treatment> treatments = TreatmentDAO.getAllTreatments();
		if (treatments.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano trattamenti presenti", "" });
			return;
		}
		for (Treatment t : treatments) {
			if (t.isEnabled()) {
				tableModel.addRow(new String[] { String.valueOf(t.getId()), t.getType(), String.valueOf(t.getPrice()),
						t.getVat().toString(), String.valueOf(t.getDuration().toMinutes()) });
			}
			// { "ID","Nome trattamento", "Prezzo", "IVA%", "Durata" };
		}
	}

	private void clearTable() {
		tableModel.getDataVector().removeAllElements();
		revalidate();
	}
	private void clearProdutTable(){
		productModel.getDataVector().removeAllElements();
		revalidate();
	}

	private void createTreatment() {
		System.out.println(isDataValid(true));
		if (isDataValid(true)) {
			String name = txtName.getText();
			BigDecimal price = new BigDecimal(txtPrice.getText());
			String vatString=String.valueOf(cBoxIVA.getSelectedItem().toString());
			double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
			VAT vat = VATDao.getVATByAmount(vatAmount).get();
			int durationInt = Integer.parseInt(txtDuration.getText());
			Duration duration = Duration.ofMinutes(durationInt);


//
			// prodottiSelezionati
			// isEnabled

			Treatment t = new Treatment(name, price, vat, duration, products, true);

			Treatment tUpdated=TreatmentDAO.insertTreatment(t).get();
			//per aggiungere i prodotti nella tabella tratmentproduct
			for(Product p:products) {
				TreatmentDAO.addProductToTreatment(tUpdated, p);
			}
			msgLbl.setText(name + " inserito correttamente");
			populateTable();
		}

	}

	private boolean isDataValid(boolean mustNameBeUnique) {
		msgLbl.setText("");
		if (mustNameBeUnique) {
			if (!TreatmentDAO.isTreatmentNameUnique(txtName.getText())) {
				msgLbl.setText("Nome del trattamento già presente");
				return false;
			}
		}
		try {
			inputValidator.validateName(txtName.getText());
		} catch (inputValidatorException e) {
			msgLbl.setText(e.getMessage());
			e.printStackTrace();
			return false;
		}

		if(Integer.parseInt(txtDuration.getText())>1200) {
			msgLbl.setText("Inserire una durata in minuti valida");
			return false;
		}
		return true;
	}

	private void populateTableByFilter() {
		msgLbl.setText("");
		if (txtSearchBar.getText().isBlank() || txtSearchBar.getText().isEmpty()) {
			msgLbl.setText("Inserire un filtro!");
			return;
		}
		clearTable();
		List<Treatment> treatments = TreatmentDAO.getAllTreatments();
		if (products.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano trattamenti presenti", "" });
			return;
		}
		for (Treatment t : treatments) {
			if (t.isEnabled() && t.getType().toLowerCase().contains(txtSearchBar.getText().toLowerCase())) {
				tableModel.addRow(new String[] { String.valueOf(t.getId()), t.getType(), String.valueOf(t.getPrice()),
						t.getVat().toString(), String.valueOf(t.getDuration()) });
			}
		}
		txtSearchBar.setText("");
	}

	private void updateTreatment() {
		if (isDataValid(false)) {
			String name = txtName.getText();
			BigDecimal price = new BigDecimal(txtPrice.getText());
			String vatString=String.valueOf(cBoxIVA.getSelectedItem().toString());
			double vatAmount = Double.parseDouble(vatString.substring(0, vatString.length() - 1));
			VAT vat = VATDao.getVATByAmount(vatAmount).get();
			int durationInt = Integer.parseInt(txtDuration.getText());
			Duration duration = Duration.ofMinutes(durationInt);
			// prodottiSelezionati
			// isEnabled
			Treatment oldTreatment=TreatmentDAO.getTreatment(selectedId).get();
			for(Product p:oldTreatment.getProducts()) {
				TreatmentDAO.removeProductFromTreatment(oldTreatment, p);
			}
			Treatment t = new Treatment(name, price, vat, duration, products, true);

			TreatmentDAO.updateTreatment(selectedId, t);
			//per aggiungere i prodotti nella tabella tratmentproduct
			for(Product p:products) {
				TreatmentDAO.addProductToTreatment(oldTreatment, p);
			}
			clearFields();
			populateTable();

		}

	}
	void populateProductsTable(List<Product> products) {
		if (products.isEmpty()) {
			productModel.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Product p : products) {
			productModel.addRow(new String[] {String.valueOf(p.getId()),p.getName(),p.getType().getDescription()});
		}
	}

	private void deleteTreatment() {
		msgLbl.setText("");
		TreatmentDAO.toggleEnabledTreatment(selectedId);
		msgLbl.setText("Trattamento rimosso correttamente");
		populateTable();
	}

	private void clearFields() {
		txtName.setText("");
		txtPrice.setText("");
		txtDuration.setText("");
		products.clear();
	}

	public JTextField getTxtPrice() {
		return txtPrice;
	}

	public void setTxtPrice(JTextField txtPrice) {
		this.txtPrice = txtPrice;
	}

	public JTextField getTxfSearchBar() {
		return txtSearchBar;
	}

	public void setTxfSearchBar(JTextField txfSearchBar) {
		this.txtSearchBar = txfSearchBar;
	}

	public JTextField getTxfName() {
		return txtName;
	}

	public void setTxfName(JTextField txfName) {
		this.txtName = txfName;
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
