package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.TransactionDAO;
import com.bitcamp.centro.estetico.models.PayMethod;
import com.bitcamp.centro.estetico.models.Transaction;
import com.toedter.calendar.JDateChooser;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtSearchBar;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JComboBox<PayMethod> payMethodSelector;
	private JTextField txtCardPercentage;
	private JTextField txtTotalAmount;
	private JTextField txtCurrencyPercentage;
	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;
	private JLabel msgLbl;
	private int selectedId;

	/**
	 * Create the panel.
	 */
	public ReportPanel() {
		setLayout(null);
		setSize(1024, 768);
		setName("REPORT");
		JLabel titleTab = new JLabel("REPORT TRANSAZIONI");
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
		String[] columnNames = { "id", "€", "CLIENTE", "DATA", "PAGAMENTO", "SERVIZI" };
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
					}
				}
			}
		});
		
		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 90, 959, 246);
		containerPanel.add(scrollPane);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 90, 959, 246);
		containerPanel.add(outputPanel);
		
		JButton btnReset = new JButton("");
		btnReset.setOpaque(false);
		btnReset.setContentAreaFilled(false);
		btnReset.setBorderPainted(false);
		btnReset.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/cartellina.png")));
		btnReset.setBounds(900, 8, 40, 30);
		btnReset.addActionListener(event -> populateTable());
		containerPanel.add(btnReset);

		JButton btnFilter = new JButton("");
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setIcon(new ImageIcon(TreatmentPanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		btnFilter.setBounds(950, 8, 40, 30);
		btnFilter.addActionListener(e -> populateTableByFilter());
		containerPanel.add(btnFilter);

		// label e textfield degli input
		JLabel lblSearchBar = new JLabel("Nome Cliente:");
		lblSearchBar.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(lblSearchBar);

		txtSearchBar = new JTextField();
		txtSearchBar.setColumns(10);
		txtSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txtSearchBar.setBounds(153, 14, 168, 24);
		containerPanel.add(txtSearchBar);
		
		JLabel lblPayMethod = new JLabel("Pagamento:");
		lblPayMethod.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPayMethod.setBounds(413, 14, 168, 24);
		containerPanel.add(lblPayMethod);
		
		payMethodSelector = new JComboBox<PayMethod>();
		payMethodSelector.addItem(null);
		payMethodSelector.addItem(PayMethod.CARD);
		payMethodSelector.addItem(PayMethod.CURRENCY);
		payMethodSelector.setBackground(UIManager.getColor("CheckBox.background"));
		payMethodSelector.setBounds(543, 14, 168, 24);
		containerPanel.add(payMethodSelector);
		
		JLabel lblStartDate = new JLabel("Data da:");
		lblStartDate.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblStartDate.setBounds(23, 44, 168, 24);
		containerPanel.add(lblStartDate);

		startDateChooser = new JDateChooser();
		startDateChooser.getDateEditor().getUiComponent().setBackground(UIManager.getColor("CheckBox.background"));
		startDateChooser.setBounds(153, 44, 168, 24);
		containerPanel.add(startDateChooser);
		
		JLabel lblEndDate = new JLabel("Data a:");
		lblEndDate.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblEndDate.setBounds(413, 44, 168, 24);
		containerPanel.add(lblEndDate);
		
		endDateChooser = new JDateChooser();
		endDateChooser.getDateEditor().getUiComponent().setBackground(UIManager.getColor("CheckBox.background"));
		endDateChooser.setBounds(543, 44, 168, 24);
		containerPanel.add(endDateChooser);
		
		// label e textfield degli input
		JLabel lblTotalAmount = new JLabel("Incasso totale:");
		lblTotalAmount.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblTotalAmount.setBounds(43, 437, 170, 14);
		add(lblTotalAmount);

		txtTotalAmount = new JTextField();
		txtTotalAmount.setColumns(10);
		txtTotalAmount.setBounds(259, 436, 220, 20);
		txtTotalAmount.setText("0");
		txtTotalAmount.setEnabled(false);
		add(txtTotalAmount);
		
		JLabel lblCardPercentage = new JLabel("% pagamenti con carta:");
		lblCardPercentage.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblCardPercentage.setBounds(43, 467, 170, 14);
		add(lblCardPercentage);

		txtCardPercentage = new JTextField();
		txtCardPercentage.setColumns(10);
		txtCardPercentage.setBounds(259, 466, 220, 20);
		txtCardPercentage.setText("0");
		txtCardPercentage.setEnabled(false);
		add(txtCardPercentage);
		
		JLabel lblCurrencyPercentage = new JLabel("% pagamenti contanti:");
		lblCurrencyPercentage.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblCurrencyPercentage.setBounds(553, 467, 170, 14);
		add(lblCurrencyPercentage);

		txtCurrencyPercentage = new JTextField();
		txtCurrencyPercentage.setColumns(10);
		txtCurrencyPercentage.setBounds(769, 466, 220, 20);
		txtCurrencyPercentage.setText("0");
		txtCurrencyPercentage.setEnabled(false);
		add(txtCurrencyPercentage);

		msgLbl = new JLabel("");
		msgLbl.setBounds(389, 606, 625, 16);
		add(msgLbl);
		populateTable();

	}

	private void populateTable() {
		clearFilters();
		clearTable();
		List<Transaction> transactions = TransactionDAO.getAllTransactions();
		if (transactions.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano transazioni presenti", "" });
			return;
		}
		List<Transaction> filteredTransactions = new ArrayList<Transaction>();
		for (Transaction t : transactions) {
			if (t.isEnabled()) {
				filteredTransactions.add(t);
				tableModel.addRow(new String[] { String.valueOf(t.getId()), String.valueOf(t.getPrice()), t.getCustomer().getName() + " " + t.getCustomer().getSurname(), String.valueOf(t.getDateTime()), String.valueOf(t.getPaymentMethod()), String.valueOf(t.getServices())});
			}
		}
		calculateReport(filteredTransactions);
	}

	private void clearTable() {
		tableModel.getDataVector().removeAllElements();
		revalidate();
	}
	
	private void clearFilters() {
		txtSearchBar.setText("");
		payMethodSelector.setSelectedIndex(0);
		startDateChooser.setDate(null);
		endDateChooser.setDate(null);
	}

	private void populateTableByFilter() {
		msgLbl.setText("");
		if (txtSearchBar.getText().isBlank() && startDateChooser.getDate() == null && endDateChooser.getDate() == null && payMethodSelector.getSelectedItem() == null) {
			msgLbl.setText("Inserire un filtro!");
			return;
		}
		String customerName = txtSearchBar.getText();
		PayMethod payMethod = payMethodSelector.getItemAt(payMethodSelector.getSelectedIndex());
		
		clearTable();
		List<Transaction> transactions = TransactionDAO.getAllTransactions();
		
		List<Transaction> filteredTransactions = new ArrayList<Transaction>();
		for (Transaction t : transactions) {
			boolean filterName = customerName != null ? t.getCustomer().getName().toLowerCase().contains(customerName.toLowerCase()) : true;
			boolean filterPayMethod = payMethod != null ? t.getPaymentMethod() == payMethod : true;
			boolean filterStartDate = startDateChooser.getDate() != null 
					? !t.getDateTime().toLocalDate().isBefore(startDateChooser.getDate()
						.toInstant()
						.atZone(ZoneId.systemDefault())  // Ottieni la zona fuso orario predefinita
				        .toLocalDate()) 
					: true;
			boolean filterEndDate = endDateChooser.getDate() != null 
					? !t.getDateTime().toLocalDate().isAfter(endDateChooser.getDate()
							.toInstant()
							.atZone(ZoneId.systemDefault())  // Ottieni la zona fuso orario predefinita
					        .toLocalDate()) 
						: true;;
			
			if (t.isEnabled() && filterName && filterPayMethod && filterStartDate && filterEndDate) {
				filteredTransactions.add(t);
				tableModel.addRow(new String[] { String.valueOf(t.getId()), String.valueOf(t.getPrice()), t.getCustomer().getName() + " " + t.getCustomer().getSurname(), String.valueOf(t.getDateTime()), String.valueOf(t.getPaymentMethod()), String.valueOf(t.getServices())});
			}
		}
		if (filteredTransactions.isEmpty()) {
			tableModel.addRow(new String[] { "Sembra non ci siano transazioni presenti che soddifino i filtri richiesti", "" });
			return;
		}
		calculateReport(filteredTransactions);
	}
	private void calculateReport(List<Transaction> transactions) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalAmountCard = BigDecimal.ZERO;
		BigDecimal totalAmountCurrency = BigDecimal.ZERO;
		for (Transaction t : transactions) {
			if ( t.getPaymentMethod() == PayMethod.CARD) {
				totalAmountCard = totalAmountCard.add(t.getPrice());
			} else if ( t.getPaymentMethod() == PayMethod.CURRENCY) {
				totalAmountCurrency = totalAmountCurrency.add(t.getPrice());
			}
			totalAmount = totalAmount.add(t.getPrice());
		}
		if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
	        // Calcola le percentuali
	        BigDecimal cardPercentage = totalAmountCard
	                .multiply(BigDecimal.valueOf(100))
	                .divide(totalAmount, 2, RoundingMode.HALF_UP);
	        BigDecimal currencyPercentage = totalAmountCurrency
	                .multiply(BigDecimal.valueOf(100))
	                .divide(totalAmount, 2, RoundingMode.HALF_UP);

	        // Converti i valori in stringhe
	        String totalAmountString = totalAmount.toString();
	        String cardPercentageString = cardPercentage + "%";
	        String currencyPercentageString = currencyPercentage + "%";

	        txtTotalAmount.setText(totalAmountString);
	        txtCardPercentage.setText(cardPercentageString);
	        txtCurrencyPercentage.setText(currencyPercentageString);
	    }
	}
}
