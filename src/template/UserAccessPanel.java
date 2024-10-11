package template;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Shift;

public class UserAccessPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter dayFormat;
	private DateTimeFormatter timeFormat;
	private DefaultTableModel tableModel;
	private Employee activeUser;
	private JLabel lblName;
	private JLabel lblUsername;
	private JLabel lblSurname;
	private JLabel lblRole;
	private JLabel lblMail;
	private JLabel lblAddress;
	private JLabel lblPhone;
	private JLabel lblIBAN;
	private JLabel lblBirthday;
	private JLabel lblHireDate;

	/**
	 * Create the panel.
	 * 
	 * @throws PropertyVetoException
	 */
	public UserAccessPanel(Employee employee) {
		if (employee == null) {
			JOptionPane.showMessageDialog(this, "Errore di connessione");
			System.exit(1);
		}
		this.activeUser = employee;
		dayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		timeFormat = DateTimeFormatter.ofPattern("hh:mm");
		setLayout(null);
		setSize(1024, 768);
		setName("Area personale");
		JLabel titleTab = new JLabel("Benvenuto " + activeUser.getName());
		titleTab.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleTab.setBounds(415, 11, 206, 32);
		add(titleTab);

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "I tuoi dati:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		dataPanel.setBounds(47, 116, 459, 364);
		add(dataPanel);
		dataPanel.setLayout(null);

		lblName = new JLabel();
		lblName.setBounds(25, 47, 185, 16);

		dataPanel.add(lblName);

		lblSurname = new JLabel();
		lblSurname.setBounds(25, 87, 185, 16);
		dataPanel.add(lblSurname);

		lblRole = new JLabel();
		lblRole.setBounds(222, 87, 231, 16);
		dataPanel.add(lblRole);

		lblAddress = new JLabel();
		lblAddress.setBounds(25, 172, 415, 16);
		dataPanel.add(lblAddress);

		lblIBAN = new JLabel();
		lblIBAN.setBounds(25, 253, 353, 16);
		dataPanel.add(lblIBAN);

		lblBirthday = new JLabel();
		lblBirthday.setBounds(25, 281, 324, 16);
		dataPanel.add(lblBirthday);

		lblHireDate = new JLabel();
		lblHireDate.setBounds(25, 313, 335, 16);
		dataPanel.add(lblHireDate);

		lblUsername = new JLabel();
		lblUsername.setBounds(222, 47, 185, 16);
		dataPanel.add(lblUsername);

		lblMail = new JLabel();
		lblMail.setBounds(25, 128, 250, 16);
		dataPanel.add(lblMail);

		lblPhone = new JLabel();
		lblPhone.setBounds(25, 212, 324, 16);
		dataPanel.add(lblPhone);

		String[] columnNames = { "Giorno", "Inizio turno", "Fine turno", "Tipologia" };
		tableModel = new DefaultTableModel(columnNames, 0);

		// Creazione della tabella
		JTable shiftTable = new JTable(tableModel);
		
		// Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
		JScrollPane shiftScrollsPane = new JScrollPane(shiftTable);
		shiftScrollsPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "I tuoi turni:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		shiftScrollsPane.setBounds(535, 116, 459, 487);
		add(shiftScrollsPane);

		JButton changePasswordBtn = new JButton("Cambia password");
		changePasswordBtn.setBounds(200, 574, 145, 29);
		changePasswordBtn.addActionListener(e -> changePassword());
		add(changePasswordBtn);

		JButton changeUserBtn = new JButton("Cambia Username");
		changeUserBtn.setBounds(200, 529, 145, 29);
		changeUserBtn.addActionListener(e -> changeUsername());
		add(changeUserBtn);
		updateData();
		populateShiftTable();

	}

	private void populateShiftTable() {
		System.out.println("ID impiegato: " + activeUser.getId());
		// popolamento tabella
		int i=1;

		for (Shift shift : activeUser.getShift()) {
			
			if (!shift.isShiftOver()) {
				String day = shift.getStart().format(dayFormat);
				String start = shift.getStart().format(timeFormat);
				String end = shift.getEnd().format(timeFormat);
				String type=shift.getType().toString();
				System.out.println("Turno "+i+":\n ID: "+shift.getId()+"\nGiorno: "+day+ "\nDa a: "+start+"-"+end+"\nTipo: "+type);
				i++;
				tableModel.addRow(new String[] { day, start, end, type });
				

			}
		}
	}

	public void updateData() {
		lblName.setText("Nome: " + activeUser.getName());
		lblSurname.setText("Cognome: " + activeUser.getSurname());
		lblRole.setText("Ruolo: " + activeUser.getRole().toString());
		lblAddress.setText("Indirizzo: " + activeUser.getAddress() + ", " + activeUser.getBirthplace());
		lblIBAN.setText("IBAN: " + activeUser.getIban());
		lblHireDate.setText("Data di assunzione: " + activeUser.getHiredDate());
		lblUsername.setText("Username: " + activeUser.getUsername());
		lblMail.setText("Mail: " + activeUser.getMail());
		lblPhone.setText("Telefono: " + activeUser.getPhone());
	}

	public void changePassword() {
		ChangePassDialog changePassDialog = new ChangePassDialog();
		changePassDialog.setAlwaysOnTop(true);
		changePassDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);

	}

	public void changeUsername() {
		ChangeUserDialog changeUserDialog = new ChangeUserDialog();
		changeUserDialog.setAlwaysOnTop(true);
		changeUserDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}

}
