package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.utils.inputValidator;

public class creaAccount extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldSearch;
    private JTextField txfUsername;
    private JComboBox<Roles> cBoxRuolo;
    private JPasswordField txfPassword;
    private JPasswordField txfConfirmPassword;
    private JButton btnConfermaInserisci;
    private JButton btnConfermaModifica;
    private JButton btnAnnulla;
    private JButton btnHystorical;
    private int selectedRow = -1;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection conn;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                creaAccount frame = new creaAccount();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public creaAccount() {
        
        try {
            System.out.println("Tentativo di connessione al database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "root");
            
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connessione al database non riuscita.");
            }

            System.out.println("Connessione al database riuscita.");
            conn.setAutoCommit(false);    
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la connessione al database: " + e.getMessage());
            e.printStackTrace();
            return; 
        }

        // interfaccia 
        initializeUI();

       
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
    }

    // Metodo interfaccia 
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCreaAccountAdmin = new JLabel("CREA ACCOUNT ADMIN - RECEPTIONIST - PERSONALE");
        lblCreaAccountAdmin.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
        lblCreaAccountAdmin.setBounds(266, 23, 474, 32);
        contentPane.add(lblCreaAccountAdmin);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(null);
        containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setBounds(10, 77, 988, 347);
        contentPane.add(containerPanel);

        // Modello della tabella con colonne
        String[] columnNames = { "ID", "Username", "Ruolo" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Nascondere la colonna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

      
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(23, 60, 943, 276);
        containerPanel.add(scrollPane);

        JButton btnSearch = new JButton("");
        btnSearch.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/searchIcon.png")));
        btnSearch.setOpaque(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setBounds(206, 8, 40, 30);
        containerPanel.add(btnSearch);

        textFieldSearch = new JTextField();
        textFieldSearch.setColumns(10);
        textFieldSearch.setBackground(UIManager.getColor("CheckBox.background"));
        textFieldSearch.setBounds(23, 14, 168, 24);
        containerPanel.add(textFieldSearch);

        // Listener per il pulsante "Cerca"
        btnSearch.addActionListener(e -> searchAccounts());

        JButton btnInsert = new JButton("");
        btnInsert.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/InsertUser.png")));
        btnInsert.setOpaque(false);
        btnInsert.setContentAreaFilled(false);
        btnInsert.setBorderPainted(false);
        btnInsert.setBounds(776, 8, 40, 30);
        containerPanel.add(btnInsert);
        btnInsert.addActionListener(e -> {
            enableInputs();
            btnConfermaInserisci.setVisible(true);
            btnAnnulla.setVisible(true);
        });

        JButton btnUpdate = new JButton("");
        btnUpdate.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/UpdateUser.png")));
        btnUpdate.setOpaque(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setBounds(826, 8, 40, 30);
        containerPanel.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) { 
                txfUsername.setText(tableModel.getValueAt(selectedRow, 1).toString());
                cBoxRuolo.setSelectedItem(Roles.valueOf(tableModel.getValueAt(selectedRow, 2).toString()));

                enableInputs();
                btnConfermaModifica.setVisible(true);
                btnAnnulla.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Seleziona una riga da aggiornare.");
            }
        });

        JButton btnDisable = new JButton("");
        btnDisable.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/userDisable.png")));
        btnDisable.setOpaque(false);
        btnDisable.setContentAreaFilled(false);
        btnDisable.setBorderPainted(false);
        btnDisable.setBounds(876, 8, 40, 30);
        containerPanel.add(btnDisable);

        btnDisable.addActionListener(e -> {
            selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int accountId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    boolean success = UserCredentialsDAO.disableAccount(accountId);
                    if (success) {
                        tableModel.removeRow(selectedRow);
                        selectedRow = -1;
                        JOptionPane.showMessageDialog(null, "Account disabilitato con successo!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore nella disabilitazione dell'account.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Errore durante la disabilitazione dell'account: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleziona una riga da disabilitare.");
            }
        });

        // Pulsante btnHystorical per visualizzare tutti gli account attivi
        btnHystorical = new JButton("");
        btnHystorical.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/StoricoUser2.png")));
        btnHystorical.setOpaque(false);
        btnHystorical.setContentAreaFilled(false);
        btnHystorical.setBorderPainted(false);
        btnHystorical.setBounds(926, 8, 40, 30);
        containerPanel.add(btnHystorical);

       
        btnHystorical.addActionListener(e -> {
        	searchActiveAccounts();
        });

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblUsername.setBounds(47, 491, 170, 14);
		contentPane.add(lblUsername);

		txfUsername = new JTextField();
		txfUsername.setEnabled(false);
		txfUsername.setColumns(10);
		txfUsername.setBounds(227, 490, 220, 20);
		contentPane.add(txfUsername);

		JLabel lblRuolo = new JLabel("Ruolo:");
		lblRuolo.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblRuolo.setBounds(47, 533, 170, 14);
		contentPane.add(lblRuolo);

		// Inizializzare la JComboBox con gli oggetti enum `Roles`
		cBoxRuolo = new JComboBox<>(Roles.values());
		cBoxRuolo.setEnabled(false);
		cBoxRuolo.setBounds(227, 531, 220, 22);
		contentPane.add(cBoxRuolo);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPassword.setBounds(525, 491, 170, 14);
		contentPane.add(lblPassword);

		txfPassword = new JPasswordField();
		txfPassword.setEnabled(false);
		txfPassword.setBounds(705, 490, 220, 20);
		contentPane.add(txfPassword);

		JLabel lblConfermaPassword = new JLabel("Conferma Password:");
		lblConfermaPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblConfermaPassword.setBounds(525, 533, 170, 14);
		contentPane.add(lblConfermaPassword);

		txfConfirmPassword = new JPasswordField();
		txfConfirmPassword.setEnabled(false);
		txfConfirmPassword.setBounds(705, 532, 220, 20);
		contentPane.add(txfConfirmPassword);

		btnConfermaInserisci = new JButton("Inserisci");
		btnConfermaInserisci.setBounds(394, 591, 100, 30);
		btnConfermaInserisci.setVisible(false);
		contentPane.add(btnConfermaInserisci);

		btnConfermaInserisci.addActionListener(e -> {
            if (!validateInputs()) {
                return;
            }

            String username = txfUsername.getText();
            String password = new String(txfPassword.getPassword());
            Roles role = (Roles) cBoxRuolo.getSelectedItem();

            try {
                boolean success = UserCredentialsDAO.createAccount(username, password, role);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Account creato con successo!");
                    searchAccounts();
                    clearFields();
                    disableInputs();
                    btnConfermaInserisci.setVisible(false);
                    btnAnnulla.setVisible(false);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la creazione dell'account: " + ex.getMessage());
            }
        });

		btnConfermaModifica = new JButton("Modifica");
		btnConfermaModifica.addActionListener(e -> {
		    if (!validateInputs()) {
		        return; 
		    }

		    String username = txfUsername.getText();
		    String password = new String(txfPassword.getPassword());
		    Roles role = (Roles) cBoxRuolo.getSelectedItem();

		    if (selectedRow < 0) {
		        JOptionPane.showMessageDialog(null, "Seleziona una riga da modificare.");
		        return;
		    }

		    int accountId = (int) tableModel.getValueAt(selectedRow, 0);

		    try {
		        boolean success = UserCredentialsDAO.updateAccount(accountId, username, password, role);
		        if (success) {
		            JOptionPane.showMessageDialog(null, "Account aggiornato con successo!");
		            // Aggiorna la tabella degli account
		            searchActiveAccounts();
		            clearFields();
		            disableInputs();
		            btnConfermaModifica.setVisible(false);
		            btnAnnulla.setVisible(false);
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Errore durante l'aggiornamento dell'account: " + ex.getMessage());
		    }
		});

		btnConfermaModifica.setBounds(394, 591, 100, 30);
		btnConfermaModifica.setVisible(false);
		contentPane.add(btnConfermaModifica);

		btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(514, 591, 100, 30);
		btnAnnulla.setVisible(false);
		contentPane.add(btnAnnulla);

		btnAnnulla.addActionListener(e -> {
			clearFields();
			disableInputs();
			btnConfermaInserisci.setVisible(false);
			btnAnnulla.setVisible(false);
		});

		JButton btnVaiLogin = new JButton("VAI AL LOGIN");
		btnVaiLogin.setBackground(new Color(0, 204, 102));
		btnVaiLogin.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		btnVaiLogin.setBounds(394, 634, 220, 23);
		contentPane.add(btnVaiLogin);

		btnVaiLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				benvenutoOperatori benvenutoOperatori = new benvenutoOperatori();
				benvenutoOperatori.setVisible(true);
				dispose(); // chiude la finestra corrente
			}
		});

		JButton btnBack = new JButton("TORNA INDIETRO");
		btnBack.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 13));
		btnBack.setBounds(394, 668, 220, 23);
		contentPane.add(btnBack);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				installazioneGuidataDatiFiscali datiFiscaliFrame = new installazioneGuidataDatiFiscali();
				datiFiscaliFrame.setVisible(true);
				dispose();
			}
		});

	}

	// Metodo per cercare account attivi in base alla ricerca
	private void searchAccounts() {
	    String searchText = textFieldSearch.getText().trim(); 
	    try {
	        List<Object[]> accounts;
	        if (searchText.isEmpty()) {
	            // Se la barra di ricerca è vuota, mostra tutti gli account attivi
	            accounts = UserCredentialsDAO.searchActiveAccounts();
	        } else {
	            // Altrimenti, cerca in base all'username o al ruolo
	            accounts = UserCredentialsDAO.searchAccounts(searchText);
	        }
	        
	        tableModel.setRowCount(0);
	        
	        for (Object[] account : accounts) {
	            tableModel.addRow(account);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Errore durante la ricerca degli account: " + e.getMessage());
	    }
	}


	 // Metodo per cercare tutti gli account attivi e popolare la tabella
    private void searchActiveAccounts() {
        try {
            List<Object[]> accounts = UserCredentialsDAO.searchActiveAccounts();
            // Svuota la tabella
            tableModel.setRowCount(0);
            // Popola la tabella con tutti gli account attivi
            for (Object[] account : accounts) {
                tableModel.addRow(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante la visualizzazione degli account attivi: " + e.getMessage());
        }
    }

    // Metodo per svuotare i campi di input
    private void clearFields() {
        txfUsername.setText("");
        txfPassword.setText("");
        txfConfirmPassword.setText("");
        cBoxRuolo.setSelectedIndex(0);
    }

    // Metodo per attivare i campi di input
    private void enableInputs() {
        txfUsername.setEnabled(true);
        txfPassword.setEnabled(true);
        txfConfirmPassword.setEnabled(true);
        cBoxRuolo.setEnabled(true);
    }

    // Metodo per disattivare i campi di input
    private void disableInputs() {
        txfUsername.setEnabled(false);
        txfPassword.setEnabled(false);
        txfConfirmPassword.setEnabled(false);
        cBoxRuolo.setEnabled(false);
    }

 // Metodo per validare gli input
    private boolean validateInputs() {
        String username = txfUsername.getText().trim();
        String password = new String(txfPassword.getPassword());
        String confirmPassword = new String(txfConfirmPassword.getPassword());

        // Validazione dell'username
        if (!inputValidator.validateAlphanumeric(username)) {
            JOptionPane.showMessageDialog(null, inputValidator.getErrorMessage());
            return false;
        }

        // Validazione della password
        if (!inputValidator.validatePassword(password)) {
            JOptionPane.showMessageDialog(null, inputValidator.getErrorMessage());
            return false;
        }

        // Conferma password
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Le password non coincidono.");
            return false;
        }

        return true;
    }


    // Metodo per chiudere la connessione
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}