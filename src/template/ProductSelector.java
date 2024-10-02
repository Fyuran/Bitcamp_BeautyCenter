package template;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class ProductSelector extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField productText;

    public ProductSelector() {
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

        String[] filters = {"Nome", "Categoria", "Mostra tutti"};
        JComboBox<String> filterComboBox = new JComboBox<>(filters);
        filterPanel.add(filterComboBox);

        JLabel searchLabel = new JLabel("Inserisci filtro:");
        filterPanel.add(searchLabel);

        productText = new JTextField();
        filterPanel.add(productText);

        JButton searchBtn = new JButton("Cerca");
        filterPanel.add(searchBtn);

        JButton addBtn = new JButton("Aggiungi selezione");
        filterPanel.add(addBtn);

        contentPane.add(filterPanel, BorderLayout.NORTH); // Aggiungi il pannello filtri in alto

        // Tabella prodotti
        String[] columnNames = {"Prodotto", "Categoria"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);

        JScrollPane productSearchScroll = new JScrollPane(productTable);
        contentPane.add(productSearchScroll, BorderLayout.CENTER); // Aggiungi la tabella al centro

        setVisible(true);
    }

  
}
