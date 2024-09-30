package template;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ProductSelector extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField productText;



	/**
	 * Create the frame.
	 */
	public ProductSelector() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 100, 450, 600);
		setTitle("Selezione Prodotti");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] columnNames = {"Prodotto","Categoria"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable productTable = new JTable(tableModel);
		
		JScrollPane productSearchScroll = new JScrollPane(productTable);
		productSearchScroll.setBounds(0, 133, 450, 439);
		contentPane.add(productSearchScroll);
		
		JLabel filterLabel = new JLabel("Cerca prodotti per:");
		filterLabel.setBounds(70, 18, 123, 16);
		contentPane.add(filterLabel);
		
		String[] filters={"Nome","Categoria","Mostra tutti"};
		JComboBox<String> filterComboBox = new JComboBox<String>(filters);
		filterComboBox.setBounds(205, 14, 149, 27);
		contentPane.add(filterComboBox);
		
		JLabel searchLabel = new JLabel("Inserisci filtro:");
		searchLabel.setBounds(70, 64, 123, 16);
		contentPane.add(searchLabel);
		
		productText = new JTextField();
		productText.setBounds(205, 53, 139, 26);
		contentPane.add(productText);
		productText.setColumns(10);
		
		JButton searchBtn = new JButton("Cerca");
		searchBtn.setBounds(34, 92, 149, 29);
		contentPane.add(searchBtn);
		
		JButton addBtn = new JButton("Aggiungi selezione");
		addBtn.setBounds(238, 92, 164, 29);
		contentPane.add(addBtn);
		setVisible(true);

	}
}
