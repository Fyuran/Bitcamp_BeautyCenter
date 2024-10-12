package template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Roles;

import DAO.UserCredentialsDAO;
import utils.inputValidator;

public class ChangeUserDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtNewUsername;
	private JPasswordField txtPassword;
	private Employee activeUser;
	private UserAccessPanel parentPanel;


	/**
	 * Create the frame.
	 */
	public ChangeUserDialog(UserAccessPanel parentPanel,Employee activeUser) {
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		setTitle("Cambia username");
		this.activeUser=activeUser;
		this.parentPanel=parentPanel;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		JLabel lblNewUsername = new JLabel("Nuovo username:");
		lblNewUsername.setBounds(6, 44, 116, 16);
		getContentPane().add(lblNewUsername);
		
		JLabel lblPasswordCheck = new JLabel("Password:");
		lblPasswordCheck.setBounds(6, 84, 149, 16);
		getContentPane().add(lblPasswordCheck);
		
		txtNewUsername = new JTextField();
		txtNewUsername.setBounds(179, 39, 182, 26);
		getContentPane().add(txtNewUsername);
		
		JButton confirmBtn = new JButton("Conferma");
		confirmBtn.addActionListener(e->{
			try {
				updateUsername();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		confirmBtn.setBounds(38, 168, 117, 29);
		
		getContentPane().add(confirmBtn);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(179, 79, 182, 26);
		getContentPane().add(txtPassword);
		
		JCheckBox visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(179, 117, 182, 23);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(visibleCheck.isSelected()) {
					txtPassword.setEchoChar((char) 0);
				}else {
					txtPassword.setEchoChar('•');
				}
			}
		});
		getContentPane().add(visibleCheck);
		
		JLabel msgLbl = new JLabel(""); //label per i messaggi all'utente
		msgLbl.setBounds(179, 168, 182, 16);
		getContentPane().add(msgLbl);
		setVisible(true);
		setVisible(true);
	}
	
	private void updateUsername() throws SQLException {
		if(!isDataValid()) return;
		String username=txtNewUsername.getText();
		System.out.println(username);
		int id=activeUser.getUserCredentialsId();
		System.out.println(id);
		String password=String.valueOf(txtPassword.getPassword());
		Roles role=activeUser.getRole();
		UserCredentialsDAO.updateAccount(id, username, password, role);
        JOptionPane.showMessageDialog(this, "Username modificato correttamente");
        parentPanel.updateData();
        dispose();
		
	}
	
	private boolean isDataValid() throws SQLException {
		if(!inputValidator.isUserUnique(txtNewUsername.getText())){
			JOptionPane.showMessageDialog(this, "Nuovo username già in uso");
            return false; 
		}
		if(!UserCredentialsDAO.verifyPassword(activeUser.getUsername(), txtPassword.getPassword())) {
			System.out.println(activeUser.getUsername());
			JOptionPane.showMessageDialog(this, "Password errata");
            return false;
		}
		return true;
	}
}
