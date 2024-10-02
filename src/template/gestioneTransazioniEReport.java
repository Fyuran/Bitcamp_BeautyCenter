package template;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Transaction;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Choice;

public class gestioneTransazioniEReport extends JPanel {
	
	static class NonEditableTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 746772300141997929L;

		public NonEditableTableModel() {
			super();
		}

		public NonEditableTableModel(int rowCount, int columnCount) {
			super(rowCount, columnCount);
		}

		public NonEditableTableModel(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public NonEditableTableModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		public NonEditableTableModel(Vector<?> columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public NonEditableTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
			super(data, columnNames);
		}

		@Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	}
	
	private enum filters {
		ID,
		DATE,
		IS_ENABLED
		
	}
	private static final long serialVersionUID = 1712892330024716939L;
	private JTextField txfSearchBar;
	private NonEditableTableModel tableModelTransaction;
	private DefaultTableModel tableModelCustomer;
	private static JTable table = new JTable();
	
	public gestioneTransazioniEReport() {
		setLayout(null);
		setSize(1024, 768);

		JLabel titleTab = new JLabel("TRANSAZIONI E REPORT CLIENTI");
		titleTab.setHorizontalAlignment(SwingConstants.CENTER);
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 408, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 593);
		add(containerPanel);

		// Modello della tabella con colonne
		tableModelTransaction = new NonEditableTableModel(new String[]{ "#", "€", "Data", "Pagamento", "IVA", "Cliente", "Servizi"}, 0) {
			private static final long serialVersionUID = -8821488174946072955L;
			
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
			
		};
		fillRows(Transaction.class, tableModelTransaction);
		tableModelCustomer = gestioneClienti.tableModel;
		


		// Creazione della tabella
		table.setModel(tableModelTransaction);
		table.setFillsViewportHeight(true);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 959, 507);
		containerPanel.add(scrollPane);

		JButton btnSearch = new JButton("");
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setIcon(new ImageIcon(gestioneTransazioniEReport.class.getResource("/iconeGestionale/searchIcon.png")));
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
		btnFilter.setIcon(new ImageIcon(gestioneTransazioniEReport.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(gestioneTransazioniEReport.class.getResource("/iconeGestionale/Insert.png")));
		btnInsert.setBounds(792, 8, 40, 30);
		containerPanel.add(btnInsert);



		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(gestioneTransazioniEReport.class.getResource("/iconeGestionale/Update.png")));
		btnUpdate.setBounds(842, 8, 40, 30);
		containerPanel.add(btnUpdate);



		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(gestioneTransazioniEReport.class.getResource("/iconeGestionale/disable.png")));
		btnDisable.setBounds(892, 8, 40, 30);
		containerPanel.add(btnDisable);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 959, 507);
		containerPanel.add(outputPanel);
		
		JButton btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(gestioneTransazioniEReport.class.getResource("/iconeGestionale/Refresh.png")));
		btnRefresh.setOpaque(false);
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setBorderPainted(false);
		btnRefresh.setBounds(942, 8, 40, 30);
		containerPanel.add(btnRefresh);
		
		Choice lbSelectData = new Choice();
		lbSelectData.setBounds(618, 14, 168, 24);
		containerPanel.add(lbSelectData);
		lbSelectData.add("Transazioni");
		lbSelectData.add("Clienti");
		lbSelectData.addItemListener(e->{
			int index = lbSelectData.getSelectedIndex();
			switch(index) {
			case 0:
				table.setModel(tableModelTransaction);
				fillRows(Transaction.class, tableModelTransaction);
				break;
			case 1: 
				table.setModel(tableModelCustomer);
				fillRows(Customer.class, tableModelTransaction);
				break;
			default:
				table.setModel(new NonEditableTableModel());
			}
		});
	}
	
	private void fillRows(Class<?> c, DefaultTableModel model) {
		List<Object[]> data = new ArrayList<>();
		
		for(int i = 0; i < model.getRowCount(); i++) { //clear added rows
			model.removeRow(i);
		}
		if(c == Transaction.class) {
			List<Transaction> list = Transaction.getAllData();
			list.forEach(e -> data.add(e.toTableRow()));
		}
		/*if(c == Customer.class) {
			List<Customer> list = Customer.getAllData();
			list.forEach(e -> data.add(e.toTableRow()));
		}*/
		if(!data.isEmpty()) {
			for(Object[] row: data) {
				model.addRow(row);
			}			
		}
	}
}
