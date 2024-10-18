package com.bitcamp.centro.estetico.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Product;

public class ProductSelector extends JFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> filterComboBox;
	private JTextField filterText;
    private TreatmentPanel parentPanel;

	public ProductSelector(TreatmentPanel parentPanel) {
		this.parentPanel=parentPanel;
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
        addBtn.addActionListener(e->sendProduct());
		filterPanel.add(addBtn);

		contentPane.add(filterPanel, BorderLayout.NORTH); // Aggiungi il pannello filtri in alto

		// Tabella prodotti
        String[] columnNames = {"ID","Prodotto", "Categoria"};
		model = new NonEditableTableModel(columnNames, 0);
		productTable = new JTable(model);

		 productTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    	productIds=new ArrayList<>();
	        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent event) {
	                productIds.clear(); 
	                if (!event.getValueIsAdjusting()) {
	                    int[] selectedRows = productTable.getSelectedRows(); // Per tenere conto di tutte le righe selezionate
	                	for (int selectedRow : selectedRows) {
	                        int productId = Integer.parseInt(String.valueOf(productTable.getValueAt(selectedRow, 0)));
	                        productIds.add(productId); // Aggiungi ogni ID selezionato alla lista
	                    }
	                    System.out.println("ID selezionati: " + productIds); // Debug
	                }
	            }
	        });

		
		JScrollPane productSearchScroll = new JScrollPane(productTable);
		contentPane.add(productSearchScroll, BorderLayout.CENTER); // Aggiungi la tabella al centro
		populateTable();
		setVisible(true);
	}

	public ProductSelector() {
		// TODO Auto-generated constructor stub
	}

	public void clearTable() {
		model.getDataVector().removeAllElements();
		revalidate();
	}

	public void populateTable() {
		clearTable();
		List<Product> products = ProductDAO.getAllProducts();
		if (products.isEmpty()) {
			model.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		for (Product p : products) {
    		model.addRow(new String[] {String.valueOf(p.getId()),p.getName(),p.getType().getDescription()});
		}
	}

	public void filterTable() {
		clearTable();
		List<Product> products = ProductDAO.getAllProducts();
		if (products.isEmpty()) {
			model.addRow(new String[] { "Sembra non ci siano prodotti presenti", "" });
			return;
		}
		String filter = filterComboBox.getSelectedItem().toString();
		switch (filter) {
		case "Nome":
			for (Product p : products) {
				if (p.getName().toLowerCase().contains(filterText.getText().toLowerCase())) {
					model.addRow(new String[] { p.getName(), p.getType().getDescription() });
				}
			}
			break;
		case "Categoria":
			for (Product p : products) {
				if (p.getType().getDescription().toLowerCase().contains(filterText.getText().toLowerCase())) {
					model.addRow(new String[] { p.getName(), p.getType().getDescription() });
				}
			}
			break;
		default:
			populateTable();
		}

	}
	public void sendProduct() {
    	//parentPanel.getProducts(productIds); //TODO: Fix this
    	this.dispose();
    }

}
