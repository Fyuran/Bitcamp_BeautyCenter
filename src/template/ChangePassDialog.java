package template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Roles;

import DAO.UserCredentialsDAO;

public class ChangePassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPasswordField newPasswordTxt1;
	private JPasswordField oldPasswordTxt;
	private JPasswordField newPasswordTxt2;
	private Employee activeUser;

	/**
	 * Create the frame.
	 */
	public ChangePassDialog(Employee activeUser) {
		setBounds(100, 100, 400, 500);
		getContentPane().setLayout(null);
		setTitle("Cambia password");
		this.activeUser = activeUser;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JLabel lblOldPassword = new JLabel("Vecchia password:");
		lblOldPassword.setBounds(6, 91, 116, 16);
		getContentPane().add(lblOldPassword);

		JLabel lblNewPassword1 = new JLabel("Nuova password:");
		lblNewPassword1.setBounds(6, 132, 116, 16);
		getContentPane().add(lblNewPassword1);

		JLabel lblNewPassword2 = new JLabel("Nuova password(ripeti):");
		lblNewPassword2.setBounds(6, 172, 149, 16);
		getContentPane().add(lblNewPassword2);

		newPasswordTxt1 = new JPasswordField();
		newPasswordTxt1.setBounds(179, 127, 182, 26);
		getContentPane().add(newPasswordTxt1);

		JButton confirmBtn = new JButton("Conferma");
		confirmBtn.addActionListener(e -> updatePassword());
		confirmBtn.setBounds(38, 256, 117, 29);

		getContentPane().add(confirmBtn);

		oldPasswordTxt = new JPasswordField();
		oldPasswordTxt.setBounds(179, 86, 182, 26);
		getContentPane().add(oldPasswordTxt);

		newPasswordTxt2 = new JPasswordField();
		newPasswordTxt2.setBounds(179, 167, 182, 26);
		getContentPane().add(newPasswordTxt2);

		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(179, 205, 182, 23);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (visibleCheck.isSelected()) {
					oldPasswordTxt.setEchoChar((char) 0);
					newPasswordTxt1.setEchoChar((char) 0);
					newPasswordTxt2.setEchoChar((char) 0);
				} else {
					oldPasswordTxt.setEchoChar('•');
					newPasswordTxt1.setEchoChar('•');
					newPasswordTxt2.setEchoChar('•');
				}
			}
		});
		getContentPane().add(visibleCheck);

		JLabel msgLbl = new JLabel(""); // label per i messaggi all'utente
		msgLbl.setBounds(179, 261, 182, 16);
		getContentPane().add(msgLbl);
		setVisible(true);
	}

	private void updatePassword() {
		if (!isDataValid()) return;
		
		int id = activeUser.getId();
		String username = activeUser.getUsername();
		Roles role = activeUser.getRole();
		
		UserCredentialsDAO.updateUserCredentials(id, username, newPasswordTxt1.getPassword(), role); //password will be encrypted by called function
		JOptionPane.showMessageDialog(this, "Password modificata correttamente");
		dispose();

	}

	private boolean isDataValid() {
		String oldPassword = String.valueOf(oldPasswordTxt.getPassword());
		String newPassword1 = String.valueOf(newPasswordTxt1.getPassword());
		String newPassword2 = String.valueOf(newPasswordTxt2.getPassword());
		System.out.println(oldPassword);
		
		if (!UserCredentialsDAO.verifyPassword(activeUser.getUsername(), oldPassword)) {
			JOptionPane.showMessageDialog(this, "Vecchia password errata");
			return false;
		}
		if (!newPassword1.equals(newPassword2)) {
			JOptionPane.showMessageDialog(this, "Le password non coincidono");
			return false;
		}
		return true;
	}

	public JPasswordField getNewPasswordTxt1() {
		return newPasswordTxt1;
	}

	public void setNewPasswordTxt1(JPasswordField newPasswordTxt1) {
		this.newPasswordTxt1 = newPasswordTxt1;
	}

	public JPasswordField getOldPasswordTxt() {
		return oldPasswordTxt;
	}

	public void setOldPasswordTxt(JPasswordField oldPasswordTxt) {
		this.oldPasswordTxt = oldPasswordTxt;
	}

	public JPasswordField getNewPasswordTxt2() {
		return newPasswordTxt2;
	}

	public void setNewPasswordTxt2(JPasswordField newPasswordTxt2) {
		this.newPasswordTxt2 = newPasswordTxt2;
	}

}
