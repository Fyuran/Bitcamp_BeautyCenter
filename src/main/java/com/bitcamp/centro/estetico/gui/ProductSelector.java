package com.bitcamp.centro.estetico.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Product;

public class ProductSelector extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JComboBox<String> filterComboBox;
	private static JTextField filterText;
	private static JPanel contentPane;
	private static JPanel parent;
	private static JTable table;
	private static NonEditableTableModel model;
	private static List<Product> products = BasePanel.products;

	public ProductSelector(JPanel parent) {
		this.parent = parent;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 100, 450, 600);
		setTitle("Selezione Prodotti");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout()); // Usa un layout manager
		setContentPane(contentPane);

		// Pannello superiore per i filtri
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new GridLayout(3, 2, 5, 5)); // Layout a griglia

		JLabel filterLabel = new JLabel("Cerca prodotti per:");
		filterPanel.add(filterLabel);

		String[] filters = { "Nome", "Categoria", "Mostra tutti" };
		filterComboBox = new JComboBox<>(filters);
		filterPanel.add(filterComboBox);

		JLabel searchLabel = new JLabel("Inserisci filtro:");
		filterPanel.add(searchLabel);

		filterText = new JTextField();
		filterPanel.add(filterText);

		JButton searchBtn = new JButton("Cerca");
		searchBtn.addActionListener(e -> filterTable());
		filterPanel.add(searchBtn);

		JButton addBtn = new JButton("Aggiungi selezione");
		addBtn.addActionListener(e -> sendProduct());
		filterPanel.add(addBtn);

		contentPane.add(filterPanel, BorderLayout.NORTH); // Aggiungi il pannello filtri in alto

		//id, name, type, amount, minStock, price, vat
		String[] columnNames = { "ID", "Nome", "Categoria" };
		model = new NonEditableTableModel(columnNames, 0);
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane productSearchScroll = new JScrollPane(table);
		contentPane.add(productSearchScroll, BorderLayout.CENTER); // Aggiungi la tabella al centro
		populateTable();
		setVisible(true);
	}

	public void clearTable() {
		model.setRowCount(0);
	}

	public void populateTable() {
		clearTable();
		if (products.isEmpty()) return;

		products.parallelStream()
		.filter(p -> p.isEnabled())
		.forEach(p -> model.addRow(p.toTableRow()));
	}

	public void filterTable() { //TODO: Implement this
		clearTable();
		if (products.isEmpty()) return;
		String filter = filterComboBox.getSelectedItem().toString();
		switch (filter) {
			case "Nome":
				break;
			case "Categoria":
				break;
			default:
				populateTable();
		}

	}

	public void sendProduct() {
		// parentPanel.getProducts(productIds); //TODO: Fix this
		this.dispose();
	}

}
