package template;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class gestionePremi extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txfThreshold;
	private JTextField txfSearchbar;
	private JTextField txfNotes;
	private JTextField txfScadenzaPremio;
	private JTextField txfNomePremio;
	private JTextField txfImportoPremio;

	/**
	 * Create the panel.
	 */
	public gestionePremi() {
		setLayout(null);
		setSize(1024,768);
		
		JLabel lblNomePremio = new JLabel("Nome Premio:");
		lblNomePremio.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNomePremio.setBounds(320, 424, 170, 14);
		add(lblNomePremio);
		
		txfThreshold = new JTextField();
		txfThreshold.setColumns(10);
		txfThreshold.setBounds(514, 471, 220, 20);
		add(txfThreshold);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 54, 1004, 347);
		add(containerPanel);
		
		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\searchIcon.png"));
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);
		
		txfSearchbar = new JTextField();
		txfSearchbar.setColumns(10);
		txfSearchbar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchbar.setBounds(23, 14, 168, 24);
		containerPanel.add(txfSearchbar);
		
		JButton btnFilter = new JButton("");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFilter.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\filterIcon.png"));
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
		btnInsert.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\Insert.png"));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(720, 8, 40, 30);
		containerPanel.add(btnInsert);
		
		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\Update.png"));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(770, 8, 40, 30);
		containerPanel.add(btnUpdate);
		
		JButton btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelete.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\delete.png"));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(820, 8, 40, 30);
		containerPanel.add(btnDelete);
		
		JButton btnDisable = new JButton("");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDisable.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\disattivo.png"));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(920, 8, 40, 30);
		containerPanel.add(btnDisable);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 960, 276);
		containerPanel.add(outputPanel);
		
		JButton btnHystorical = new JButton("");
		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHystorical.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\cartellina.png"));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);
		
		JLabel lblGestionePremi = new JLabel("GESTIONE  PREMI");
		lblGestionePremi.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblGestionePremi.setBounds(415, 11, 179, 32);
		add(lblGestionePremi);
		
		JLabel lblPuntiNecessari = new JLabel("Punti necessari:");
		lblPuntiNecessari.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPuntiNecessari.setBounds(320, 471, 170, 17);
		add(lblPuntiNecessari);
		
		JLabel lblNotes = new JLabel("Note Aggiuntive:");
		lblNotes.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNotes.setBounds(320, 620, 170, 19);
		add(lblNotes);
		
		JLabel lblScadenza = new JLabel("Scadenza:");
		lblScadenza.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblScadenza.setBounds(320, 523, 170, 14);
		add(lblScadenza);
		
		JLabel lblImporto = new JLabel("Importo:");
		lblImporto.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblImporto.setBounds(320, 575, 170, 14);
		add(lblImporto);
		
		txfNotes = new JTextField();
		txfNotes.setColumns(10);
		txfNotes.setBounds(514, 620, 220, 59);
		add(txfNotes);
		
		txfScadenzaPremio = new JTextField();
		txfScadenzaPremio.setColumns(10);
		txfScadenzaPremio.setBounds(514, 522, 220, 20);
		add(txfScadenzaPremio);
		
		txfNomePremio = new JTextField();
		txfNomePremio.setColumns(10);
		txfNomePremio.setBounds(514, 423, 220, 20);
		add(txfNomePremio);
		
		txfImportoPremio = new JTextField();
		txfImportoPremio.setColumns(10);
		txfImportoPremio.setBounds(514, 574, 220, 20);
		add(txfImportoPremio);
		

	}

}
