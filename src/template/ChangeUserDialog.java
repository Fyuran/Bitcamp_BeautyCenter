package template;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

public class ChangeUserDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtNewUsername;
	private JPasswordField txtPassword;


	/**
	 * Create the frame.
	 */
	public ChangeUserDialog() {
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		setTitle("Cambia username");
		
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
		setVisible(true);
	}
}
