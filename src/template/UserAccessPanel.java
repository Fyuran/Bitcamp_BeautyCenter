package template;

import java.awt.Font;
import java.beans.PropertyVetoException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.centro.estetico.bitcamp.Employee;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class UserAccessPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * @throws PropertyVetoException 
	 */
	public UserAccessPanel(Employee employee) {
		if(employee==null) {
			JOptionPane.showMessageDialog(this, "Errore di connessione");
			System.exit(1);
		}
		setLayout(null);
		setSize(1024, 768);
		setName("Area personale");
		JLabel titleTab = new JLabel("Benvenuto "+employee.getName());
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 206, 32);
		add(titleTab);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "I tuoi dati:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		dataPanel.setBounds(47, 116, 459, 364);
		add(dataPanel);
		dataPanel.setLayout(null);
		
		
		JLabel lblName = new JLabel("Nome: "+employee.getName());
		lblName.setBounds(25, 47, 185, 16);
		
		dataPanel.add(lblName);
		
		JLabel lblSurname = new JLabel("Cognome: "+employee.getSurname());
		lblSurname.setBounds(25, 87, 185, 16);
		dataPanel.add(lblSurname);
		
		JLabel lblRole = new JLabel("Ruolo: "+employee.getRolesAsString());
		lblRole.setBounds(25, 127, 185, 16);
		dataPanel.add(lblRole);
		
		JLabel lblAddress = new JLabel("Indirizzo: "+employee.getAddress()+", "+employee.getBirthplace());
		lblAddress.setBounds(25, 172, 185, 16);
		dataPanel.add(lblAddress);
		
		JLabel lblIBAN = new JLabel("IBAN: "+employee.getIban());
		lblIBAN.setBounds(25, 253, 185, 16);
		dataPanel.add(lblIBAN);
		
		JLabel lblBirthday = new JLabel("Data di nascita: "+employee.getBoD());
		lblBirthday.setBounds(222, 47, 185, 16);
		dataPanel.add(lblBirthday);
		
		JLabel lblHireDate = new JLabel("Data di assunzione: "+employee.getHiredDate());
		lblHireDate.setBounds(222, 87, 185, 16);
		dataPanel.add(lblHireDate);
		
		JLabel lblUsername = new JLabel("Username: "+employee.getUsername());
		lblUsername.setBounds(222, 127, 185, 16);
		dataPanel.add(lblUsername);
		
		JLabel lblMail = new JLabel("Mail: "+employee.getMail());
		lblMail.setBounds(222, 172, 185, 16);
		dataPanel.add(lblMail);
		
		JLabel lblPhone = new JLabel("Telefono: "+employee.getPhone());
		lblPhone.setBounds(25, 212, 185, 16);
		dataPanel.add(lblPhone);
		
		
		
		String[] columnNames = {"Giorno","Inizio turno","Fine turno","Tipologia"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable shiftTable = new JTable(tableModel);

		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane shiftScrollsPane = new JScrollPane(shiftTable);
		shiftScrollsPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "I tuoi turni:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		shiftScrollsPane.setBounds(535, 116, 459, 487);
		add(shiftScrollsPane);
		
		JButton changePasswordBtn = new JButton("Cambia password");
		changePasswordBtn.setBounds(200, 574, 145, 29);
		changePasswordBtn.addActionListener(e->changePassword());
		add(changePasswordBtn);
		
		JButton changeUserBtn = new JButton("Cambia Username");
		changeUserBtn.setBounds(200, 529, 145, 29);
		changeUserBtn.addActionListener(e->changeUsername());
		add(changeUserBtn);
		

		

		
	}
	
	public void changePassword() {
		ChangePassDialog changePassDialog=new ChangePassDialog();
		changePassDialog.setAlwaysOnTop(true);
		changePassDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		
	}
	public void changeUsername() {
		ChangeUserDialog changeUserDialog=new ChangeUserDialog();
		changeUserDialog.setAlwaysOnTop(true);
		changeUserDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}
	
}
