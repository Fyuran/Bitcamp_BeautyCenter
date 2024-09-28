package template;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class installazioneGuidataDatiFiscali extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txfSearchBar;
	private JTextField txfName;
	private JTextField txfPhone;
	private JTextField txfPEC;
	private JTextField txfEmail;
	private JTextField txfSedeLegale;
	private JTextField txfSedeOperativa;
	private JTextField txfRea;
	private JTextField txfPIva;
	private JTextField txfAliquota;
	private JTextField txfApertura;
	private JTextField txfChiusura;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					installazioneGuidataDatiFiscali frame = new installazioneGuidataDatiFiscali();
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
	public installazioneGuidataDatiFiscali() {
		setTitle("Centro Estetico - Installazione Guidata- Dati Fiscali");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(50, 448, 170, 14);
		contentPane.add(lblName);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 65, 988, 347);
		contentPane.add(containerPanel);
		
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\searchIcon.png"));
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setBounds(206, 8, 40, 30);
		containerPanel.add(btnSearch);
		
		txfSearchBar = new JTextField();
		txfSearchBar.setColumns(10);
		txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
		txfSearchBar.setBounds(23, 14, 168, 24);
		containerPanel.add(txfSearchBar);
		
		JButton btnFilter = new JButton("");
		btnFilter.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\filterIcon.png"));
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setBounds(256, 8, 40, 30);
		containerPanel.add(btnFilter);
		
		JButton btnInsert = new JButton("");
		btnInsert.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\Insert.png"));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		btnInsert.setBounds(720, 8, 40, 30);
		containerPanel.add(btnInsert);
		
		JButton btnUpdate = new JButton("");
		btnUpdate.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\Update.png"));
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(770, 8, 40, 30);
		containerPanel.add(btnUpdate);
		
		JButton btnDelete = new JButton("");
		btnDelete.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\delete.png"));
		btnDelete.setOpaque(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(820, 8, 40, 30);
		containerPanel.add(btnDelete);
		
		JButton btnDisable = new JButton("");
		btnDisable.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\disattivo.png"));
		btnDisable.setOpaque(false);
		btnDisable.setContentAreaFilled(false);
		btnDisable.setBorderPainted(false);
		btnDisable.setBounds(920, 8, 40, 30);
		containerPanel.add(btnDisable);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBounds(23, 60, 943, 276);
		containerPanel.add(outputPanel);
		
		JButton btnHystorical = new JButton("");
		btnHystorical.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\storicoIcon.png"));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);
		
		JLabel titleDatiFiscali = new JLabel("INIZIALIZZA IL TUO CENTRO - INSERISCI  I DATI FISCALI");
		titleDatiFiscali.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleDatiFiscali.setBounds(241, 11, 513, 32);
		contentPane.add(titleDatiFiscali);
		
		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(224, 447, 220, 20);
		contentPane.add(txfName);
		
		JLabel lblPEC = new JLabel("PEC:");
		lblPEC.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPEC.setBounds(50, 532, 170, 14);
		contentPane.add(lblPEC);
		
		JLabel lblPhone = new JLabel("Contatto Telefonico:");
		lblPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPhone.setBounds(50, 490, 170, 14);
		contentPane.add(lblPhone);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblEmail.setBounds(50, 574, 170, 14);
		contentPane.add(lblEmail);
		
		JLabel lblSedeLegale = new JLabel("Sede Legale:");
		lblSedeLegale.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSedeLegale.setBounds(50, 616, 170, 14);
		contentPane.add(lblSedeLegale);
		
		JLabel lblSedeOperativa = new JLabel("Sede Operativa:");
		lblSedeOperativa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSedeOperativa.setBounds(50, 658, 170, 14);
		contentPane.add(lblSedeOperativa);
		
		txfPhone = new JTextField();
		txfPhone.setColumns(10);
		txfPhone.setBounds(224, 489, 220, 20);
		contentPane.add(txfPhone);
		
		txfPEC = new JTextField();
		txfPEC.setColumns(10);
		txfPEC.setBounds(224, 531, 220, 20);
		contentPane.add(txfPEC);
		
		txfEmail = new JTextField();
		txfEmail.setColumns(10);
		txfEmail.setBounds(224, 573, 220, 20);
		contentPane.add(txfEmail);
		
		txfSedeLegale = new JTextField();
		txfSedeLegale.setColumns(10);
		txfSedeLegale.setBounds(224, 615, 220, 20);
		contentPane.add(txfSedeLegale);
		
		txfSedeOperativa = new JTextField();
		txfSedeOperativa.setColumns(10);
		txfSedeOperativa.setBounds(224, 657, 220, 20);
		contentPane.add(txfSedeOperativa);
		
		JLabel lblREA = new JLabel("REA:");
		lblREA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblREA.setBounds(549, 448, 170, 14);
		contentPane.add(lblREA);
		
		JLabel lblPIva = new JLabel("Partita IVA:");
		lblPIva.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPIva.setBounds(549, 492, 170, 14);
		contentPane.add(lblPIva);
		
		JLabel lblAliquota = new JLabel("Aliquota %:");
		lblAliquota.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblAliquota.setBounds(549, 534, 170, 14);
		contentPane.add(lblAliquota);
		
		JLabel lblApertura = new JLabel("Orario Apertura:");
		lblApertura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblApertura.setBounds(549, 576, 170, 14);
		contentPane.add(lblApertura);
		
		JLabel lblChiusura = new JLabel("Orario Chiusura:");
		lblChiusura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblChiusura.setBounds(549, 618, 170, 14);
		contentPane.add(lblChiusura);
		
		txfRea = new JTextField();
		txfRea.setColumns(10);
		txfRea.setBounds(729, 447, 220, 20);
		contentPane.add(txfRea);
		
		txfPIva = new JTextField();
		txfPIva.setColumns(10);
		txfPIva.setBounds(729, 489, 220, 20);
		contentPane.add(txfPIva);
		
		txfAliquota = new JTextField();
		txfAliquota.setColumns(10);
		txfAliquota.setBounds(729, 531, 220, 20);
		contentPane.add(txfAliquota);
		
		txfApertura = new JTextField();
		txfApertura.setColumns(10);
		txfApertura.setBounds(729, 573, 220, 20);
		contentPane.add(txfApertura);
		
		txfChiusura = new JTextField();
		txfChiusura.setColumns(10);
		txfChiusura.setBounds(729, 615, 220, 20);
		contentPane.add(txfChiusura);
	}

}
