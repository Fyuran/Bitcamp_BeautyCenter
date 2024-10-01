package template;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import template.installazioneGuidataDatiFiscali;

public class creaAccount extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txfUsername;
	private JTextField txfEmail;
	private JPasswordField txfPassword;
	private JPasswordField txfConfirmPassword;
	private JPasswordField txfRispostaSegreta;

	private int selectedRow = -1;

	// Modello della tabella (scope a livello di classe per poter aggiornare la
	// tabella)
	DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					creaAccount frame = new creaAccount();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public creaAccount() {
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
		String[] columnNames = { "Username", "Ruolo", "Password" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable table = new JTable(tableModel);

		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 60, 943, 276);
		containerPanel.add(scrollPane);

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 943, 276);
		containerPanel.add(outputPanel);

		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/searchIcon.png")));
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(UIManager.getColor("CheckBox.background"));
		textField.setBounds(23, 14, 168, 24);
		containerPanel.add(textField);

		JButton btnFilter = new JButton("");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFilter.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/filterIcon.png")));
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);

		JButton btnInsert = new JButton("");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInsert.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/InsertUser.png")));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(776, 8, 40, 30);
		containerPanel.add(btnInsert);

		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/UpdateUser.png")));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(826, 8, 40, 30);
		containerPanel.add(btnUpdate);

		JButton btnDisable = new JButton("");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDisable.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/userDisable.png")));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(876, 8, 40, 30);
		containerPanel.add(btnDisable);

		JButton btnHystorical = new JButton("");
		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHystorical.setIcon(new ImageIcon(creaAccount.class.getResource("/iconeGestionale/StoricoUser2.png")));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(926, 8, 40, 30);
		containerPanel.add(btnHystorical);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblUsername.setBounds(47, 491, 170, 14);
		contentPane.add(lblUsername);

		txfUsername = new JTextField();
		txfUsername.setEditable(false);
		txfUsername.setColumns(10);
		txfUsername.setBounds(227, 490, 220, 20);
		contentPane.add(txfUsername);

		JLabel lblRuolo = new JLabel("Ruolo:");
		lblRuolo.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblRuolo.setBounds(47, 533, 170, 14);
		contentPane.add(lblRuolo);

		JComboBox cBoxRuolo = new JComboBox();
		cBoxRuolo.setModel(
				new DefaultComboBoxModel(new String[] { "Selezione un ruolo", "Admin", "Segretaria", "Personale" }));
		cBoxRuolo.setBounds(227, 531, 220, 22);
		contentPane.add(cBoxRuolo);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPassword.setBounds(47, 578, 170, 14);
		contentPane.add(lblPassword);

		txfPassword = new JPasswordField();
		txfPassword.setBounds(227, 577, 220, 20);
		contentPane.add(txfPassword);

		JLabel lblConfermaPassword = new JLabel("Conferma Password:");
		lblConfermaPassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblConfermaPassword.setBounds(47, 623, 170, 14);
		contentPane.add(lblConfermaPassword);

		txfConfirmPassword = new JPasswordField();
		txfConfirmPassword.setBounds(227, 622, 220, 20);
		contentPane.add(txfConfirmPassword);

		JLabel lblDomandaSegreta = new JLabel("Domanda Segreta:");
		lblDomandaSegreta.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblDomandaSegreta.setBounds(526, 491, 170, 14);
		contentPane.add(lblDomandaSegreta);

		JComboBox cBoxDomandaSegreta = new JComboBox();
		cBoxDomandaSegreta.setModel(
				new DefaultComboBoxModel(new String[] { "Seleziona una domanda", "Qual'e' stato il tuo primo lavoro?",
						"Qual'e' stato il tuo primo animale?", "Qual'e' stata la tua prima auto?", "" }));
		cBoxDomandaSegreta.setBounds(705, 489, 220, 22);
		contentPane.add(cBoxDomandaSegreta);

		JLabel lblRispostaSegreta = new JLabel("Risposta Segreta:");
		lblRispostaSegreta.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblRispostaSegreta.setBounds(526, 533, 170, 14);
		contentPane.add(lblRispostaSegreta);

		txfRispostaSegreta = new JPasswordField();
		txfRispostaSegreta.setBounds(705, 532, 220, 20);
		contentPane.add(txfRispostaSegreta);

		JButton btnVaiLogin = new JButton("VAI AL LOGIN");
		btnVaiLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				benvenutoOperatori benvenutoOperatori = new benvenutoOperatori();
				benvenutoOperatori.setVisible(true);
				dispose(); //chiude la finestra corrente
			}
		});
		btnVaiLogin.setBackground(new Color(0, 204, 102));
		btnVaiLogin.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		btnVaiLogin.setBounds(705, 620, 220, 23);
		contentPane.add(btnVaiLogin);

		JButton btnBack = new JButton("TORNA INDIETRO");
		btnBack.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 13));
		btnBack.setBounds(705, 654, 220, 23);
		contentPane.add(btnBack);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				installazioneGuidataDatiFiscali datiFiscaliFrame = new installazioneGuidataDatiFiscali();
				datiFiscaliFrame.setVisible(true);
				dispose();
			}
		});

	}
}
