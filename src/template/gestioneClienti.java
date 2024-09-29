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
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;

public class gestioneClienti extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfSurname;
	private JTextField txfSearchBar;
	private JTextField txfName;
	private JTextField txfPhone;
	private JComboBox<String> cBoxGenre;
	private JTextField txfCodiceFiscale;
	private JTextField txfEmail;
	private JTextField txfBirthdate;
	private JTextField txfComuneNascita;
	private JTextField txfProvincNascita;
	private JTextField txfNotes;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public gestioneClienti() {
		setLayout(null);
		setSize(1024, 768);

		JLabel titleTab = new JLabel("GESTIONE CLIENTI");
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 179, 32);
		add(titleTab);

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);

		// Modello della tabella con colonne
		String[] columnNames = { "Nome", "Cognome", "Codice Fiscale", "Telefono", "Email", "Data di Nascita", "Sesso",
				"Comune di Nascita", "Provincia", "Note" };
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
		btnSearch.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\searchIcon.png"));
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
		btnFilter.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\filterIcon.png"));
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\InsertUser.png"));
		btnInsert.setBounds(720, 8, 40, 30);
		containerPanel.add(btnInsert);

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Raccogliere i dati dai campi di testo
				String nome = txfName.getText();
				String cognome = txfSurname.getText();
				String codiceFiscale = txfCodiceFiscale.getText();
				String telefono = txfPhone.getText();
				String email = txfEmail.getText();
				String dataNascita = txfBirthdate.getText();
				String sesso = (String) cBoxGenre.getSelectedItem();
				String comuneNascita = txfComuneNascita.getText();
				String provinciaNascita = txfProvincNascita.getText();
				String note = txfNotes.getText();

				//aggiungere il check dei campi in modo da non aggiungere una nuova riga
				// Aggiungere una nuova riga alla tabella
				Object[] rowData = { nome, cognome, codiceFiscale, telefono, email, dataNascita, sesso, comuneNascita,
						provinciaNascita, note };
				tableModel.addRow(rowData); // Aggiunge la riga alla tabella

				// Pulisci i campi dopo l'inserimento
				txfName.setText("");
				txfSurname.setText("");
				txfCodiceFiscale.setText("");
				txfPhone.setText("");
				txfEmail.setText("");
				txfBirthdate.setText("");
				cBoxGenre.setSelectedIndex(0);
				txfComuneNascita.setText("");
				txfProvincNascita.setText("");
				txfNotes.setText("");
			}

		});

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\UpdateUser.png"));
		btnUpdate.setBounds(770, 8, 40, 30);
		containerPanel.add(btnUpdate);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ottieni l'indice della riga selezionata
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) { // Verifica che una riga sia stata selezionata
					// Riempie i campi con i valori della riga selezionata
					txfName.setText((String) tableModel.getValueAt(selectedRow, 0));
					txfSurname.setText((String) tableModel.getValueAt(selectedRow, 1));
					txfCodiceFiscale.setText((String) tableModel.getValueAt(selectedRow, 2));
					txfPhone.setText((String) tableModel.getValueAt(selectedRow, 3));
					txfEmail.setText((String) tableModel.getValueAt(selectedRow, 4));
					txfBirthdate.setText((String) tableModel.getValueAt(selectedRow, 5));
					cBoxGenre.setSelectedItem(tableModel.getValueAt(selectedRow, 6));
					txfComuneNascita.setText((String) tableModel.getValueAt(selectedRow, 7));
					txfProvincNascita.setText((String) tableModel.getValueAt(selectedRow, 8));
					txfNotes.setText((String) tableModel.getValueAt(selectedRow, 9));
				}
			}
		});

		JButton btnDelete = new JButton("");
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\deleteUser.png"));
		btnDelete.setBounds(820, 8, 40, 30);
		containerPanel.add(btnDelete);

		JButton btnDisable = new JButton("");
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\userDisable.png"));
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
		btnHystorical.setIcon(new ImageIcon(
				"C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\StoricoUser2.png"));
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(43, 437, 170, 14);
		add(lblName);

		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(209, 436, 220, 20);
		add(txfName);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSurname.setBounds(43, 474, 170, 17);
		add(lblSurname);

		txfSurname = new JTextField();
		txfSurname.setColumns(10);
		txfSurname.setBounds(209, 474, 220, 20);
		add(txfSurname);

		JLabel lblGenre = new JLabel("Sesso:");
		lblGenre.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblGenre.setBounds(43, 513, 170, 14);
		add(lblGenre);

		cBoxGenre = new JComboBox();
		cBoxGenre.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		cBoxGenre
				.setModel(new DefaultComboBoxModel(new String[] { "Seleziona Genere", "Maschio", "Femmina", "Altro" }));
		cBoxGenre.setBounds(209, 511, 220, 22);
		add(cBoxGenre);

		JLabel lblBirthdate = new JLabel("Data di nascita:");
		lblBirthdate.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblBirthdate.setBounds(43, 553, 170, 14);
		add(lblBirthdate);

		txfBirthdate = new JTextField();
		txfBirthdate.setColumns(10);
		txfBirthdate.setBounds(209, 552, 220, 20);
		add(txfBirthdate);

		JLabel lblComuneDiNascita = new JLabel("Comune di nascita:");
		lblComuneDiNascita.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblComuneDiNascita.setBounds(43, 595, 170, 14);
		add(lblComuneDiNascita);

		txfComuneNascita = new JTextField();
		txfComuneNascita.setColumns(10);
		txfComuneNascita.setBounds(209, 594, 220, 20);
		add(txfComuneNascita);

		JLabel lblProvinciaDiNascita = new JLabel("Provincia di nascita:");
		lblProvinciaDiNascita.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblProvinciaDiNascita.setBounds(43, 633, 170, 14);
		add(lblProvinciaDiNascita);

		txfProvincNascita = new JTextField();
		txfProvincNascita.setColumns(10);
		txfProvincNascita.setBounds(209, 632, 220, 20);
		add(txfProvincNascita);

		JLabel lblCodiceFiscale = new JLabel("Codice Fiscale:");
		lblCodiceFiscale.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblCodiceFiscale.setBounds(531, 437, 170, 14);
		add(lblCodiceFiscale);

		txfCodiceFiscale = new JTextField();
		txfCodiceFiscale.setColumns(10);
		txfCodiceFiscale.setBounds(749, 436, 220, 20);
		add(txfCodiceFiscale);

		JButton btnCalcolaCF = new JButton("CALCOLA C.F.");
		btnCalcolaCF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCalcolaCF.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 11));
		btnCalcolaCF.setForeground(Color.BLACK);
		btnCalcolaCF.setBackground(new Color(0, 204, 102));
		btnCalcolaCF.setBounds(749, 473, 220, 23);
		add(btnCalcolaCF);

		JLabel lblPhone = new JLabel("Contatto telefonico:");
		lblPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPhone.setBounds(531, 513, 170, 14);
		add(lblPhone);

		txfPhone = new JTextField();
		txfPhone.setColumns(10);
		txfPhone.setBounds(749, 512, 220, 20);
		add(txfPhone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblEmail.setBounds(531, 553, 170, 14);
		add(lblEmail);

		txfEmail = new JTextField();
		txfEmail.setColumns(10);
		txfEmail.setBounds(749, 552, 220, 20);
		add(txfEmail);

		JLabel lblNotes = new JLabel("Note Aggiuntive:");
		lblNotes.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNotes.setBounds(531, 593, 170, 19);
		add(lblNotes);

		txfNotes = new JTextField();
		txfNotes.setColumns(10);
		txfNotes.setBounds(749, 593, 220, 59);
		add(txfNotes);

	}
}
