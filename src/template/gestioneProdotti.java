package template;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class gestioneProdotti extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfSearchBar;
	private JTextField txtName;
	private JTextField txtMinStock;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;
	private JTextField txtPrice;

	/**
	 * Create the panel.
	 */
	public gestioneProdotti() {
		setLayout(null);
		setSize(1024, 768);
		setName("Prodotti");
		JLabel titleTab = new JLabel("GESTIONE PRODOTTI");
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
		String[] columnNames = {"Prodotto","Categoria","Quantità","Quantità minima","Prezzo","IVA%"};
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
		btnSearch.setIcon(new ImageIcon(gestioneProdotti.class.getResource("/icone/searchIcon.png")));
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
		btnFilter.setIcon(new ImageIcon(gestioneProdotti.class.getResource("/icone/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(gestioneProdotti.class.getResource("/icone/Insert.png")));
		btnInsert.setBounds(778, 8, 40, 30);
		containerPanel.add(btnInsert);

		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


				// Pulisci i campi dopo l'inserimento
				txtName.setText("");
				txtMinStock.setText("");
			}

		});

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(gestioneProdotti.class.getResource("/icone/Update.png")));
		btnUpdate.setBounds(828, 8, 40, 30);
		containerPanel.add(btnUpdate);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(gestioneProdotti.class.getResource("/icone/disable.png")));
		btnDisable.setBounds(878, 8, 40, 30);
		containerPanel.add(btnDisable);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 276);
		containerPanel.add(outputPanel);

		JButton btnHystorical = new JButton("");
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setIcon(new ImageIcon(gestioneProdotti.class.getResource("/icone/cartellina.png")));
		btnHystorical.setBounds(928, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome prodotto:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(209, 436, 220, 20);
		add(txtName);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblCategory.setBounds(43, 474, 170, 17);
		add(lblCategory);

		String[]IVAs= {"Seleziona IVA"};

		JLabel lblMinStock = new JLabel("Quantità minima:");
		lblMinStock.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblMinStock.setBounds(43, 513, 170, 14);
		add(lblMinStock);

		txtMinStock = new JTextField();
		txtMinStock.setColumns(10);
		txtMinStock.setBounds(209, 512, 220, 20);
		add(txtMinStock);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(749, 432, 220, 20);
		add(txtPrice);

		JLabel lblIVA = new JLabel("IVA:");
		lblIVA.setBounds(531, 475, 170, 14);
		add(lblIVA);

		JLabel lblPrice = new JLabel("Prezzo:");
		lblPrice.setBounds(531, 437, 170, 14);
		add(lblPrice);

		String[] categories= {"Cura orale","Cura pelle","Cura capelli","Cura del corpo","Cosmetici","Profumi","Altro"};
		JComboBox<String> categoryComboBox = new JComboBox<>(categories);
		categoryComboBox.setBounds(209, 468, 220, 27);
		add(categoryComboBox);

		JComboBox<String> IvaCOmboBox = new JComboBox<>();
		IvaCOmboBox.setBounds(749, 471, 220, 27);
		add(IvaCOmboBox);


	}
}