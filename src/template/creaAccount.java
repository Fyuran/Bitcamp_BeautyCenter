package template;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class creaAccount extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txfName;
	private JTextField txfPhone;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;

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
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(50, 460, 170, 14);
		contentPane.add(lblName);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		containerPanel.setBackground(Color.WHITE);
		containerPanel.setBounds(10, 77, 988, 347);
		contentPane.add(containerPanel);
		
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
		btnInsert.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\InsertUser.png"));
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
		btnUpdate.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\UpdateUser.png"));
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
		btnDelete.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\deleteUser.png"));
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
		btnDisable.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\userDisable.png"));
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
		btnHystorical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHystorical.setIcon(new ImageIcon("C:\\Users\\vince\\OneDrive\\Desktop\\Java\\Centro_Estetico\\src\\icone\\StoricoUser2.png"));
		btnHystorical.setOpaque(false);
		btnHystorical.setContentAreaFilled(false);
		btnHystorical.setBorderPainted(false);
		btnHystorical.setBounds(870, 8, 40, 30);
		containerPanel.add(btnHystorical);
		
		JLabel lblCreaAccountAdmin = new JLabel("CREA ACCOUNT ADMIN - RECEPTIONIST - PERSONALE");
		lblCreaAccountAdmin.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		lblCreaAccountAdmin.setBounds(266, 23, 474, 32);
		contentPane.add(lblCreaAccountAdmin);
		
		txfName = new JTextField();
		txfName.setColumns(10);
		txfName.setBounds(224, 459, 220, 20);
		contentPane.add(txfName);
		
		JLabel lblPEC = new JLabel("PEC:");
		lblPEC.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPEC.setBounds(50, 544, 170, 14);
		contentPane.add(lblPEC);
		
		JLabel lblPhone = new JLabel("Contatto Telefonico:");
		lblPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPhone.setBounds(50, 502, 170, 14);
		contentPane.add(lblPhone);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblEmail.setBounds(50, 586, 170, 14);
		contentPane.add(lblEmail);
		
		JLabel lblSedeLegale = new JLabel("Sede Legale:");
		lblSedeLegale.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSedeLegale.setBounds(50, 628, 170, 14);
		contentPane.add(lblSedeLegale);
		
		JLabel lblSedeOperativa = new JLabel("Sede Operativa:");
		lblSedeOperativa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSedeOperativa.setBounds(50, 670, 170, 14);
		contentPane.add(lblSedeOperativa);
		
		txfPhone = new JTextField();
		txfPhone.setColumns(10);
		txfPhone.setBounds(224, 501, 220, 20);
		contentPane.add(txfPhone);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(224, 543, 220, 20);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(224, 585, 220, 20);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(224, 627, 220, 20);
		contentPane.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(224, 669, 220, 20);
		contentPane.add(textField_6);
		
		JLabel lblREA = new JLabel("REA:");
		lblREA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblREA.setBounds(549, 460, 170, 14);
		contentPane.add(lblREA);
		
		JLabel lblPIva = new JLabel("Partita IVA:");
		lblPIva.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPIva.setBounds(549, 504, 170, 14);
		contentPane.add(lblPIva);
		
		JLabel lblAliquota = new JLabel("Aliquota %:");
		lblAliquota.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblAliquota.setBounds(549, 546, 170, 14);
		contentPane.add(lblAliquota);
		
		JLabel lblApertura = new JLabel("Orario Apertura:");
		lblApertura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblApertura.setBounds(549, 588, 170, 14);
		contentPane.add(lblApertura);
		
		JLabel lblChiusura = new JLabel("Orario Chiusura:");
		lblChiusura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblChiusura.setBounds(549, 630, 170, 14);
		contentPane.add(lblChiusura);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(729, 459, 220, 20);
		contentPane.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(729, 501, 220, 20);
		contentPane.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(729, 543, 220, 20);
		contentPane.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(729, 585, 220, 20);
		contentPane.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(729, 627, 220, 20);
		contentPane.add(textField_11);
	}

}
