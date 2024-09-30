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

public class ChangePassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPasswordField newPasswordTxt1;
	private JPasswordField oldPasswordTxt;
	private JPasswordField newPasswordTxt2;


	/**
	 * Create the frame.
	 */
	public ChangePassDialog() {
		setBounds(100, 100, 400, 500);
		getContentPane().setLayout(null);
		setTitle("Cambia password");
		
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
				if(visibleCheck.isSelected()) {
					oldPasswordTxt.setEchoChar((char) 0);
					newPasswordTxt1.setEchoChar((char) 0);
					newPasswordTxt2.setEchoChar((char) 0);
				}else {
					oldPasswordTxt.setEchoChar('•');
					newPasswordTxt1.setEchoChar('•');
					newPasswordTxt2.setEchoChar('•');
				}
			}
		});
		getContentPane().add(visibleCheck);
		setVisible(true);
	}
}
