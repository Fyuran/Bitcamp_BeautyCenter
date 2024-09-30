package template;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JTextField mailText;
	private JLabel userMsgLabel;
	private JLabel passwordMsgLabel;
	private JLabel passwordLabel;
	private JLabel mailLabel;
	private JButton loginBtn;
	private JButton forgottenPasswordBtn;
	private JLabel welcomeLbl;
	private JPasswordField passwordText;
	private JCheckBox visibleCheck;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		//setting della finestra
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 300, 400);
		setResizable(false);
		//creazione pannello principale
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(300, 400);
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(73, 167, 63, 16);
		mainPane.add(passwordLabel);

		mailText = new JTextField();
		mailText.setBounds(148, 89, 130, 26);
		mainPane.add(mailText);
		mailText.setColumns(10);

		mailLabel = new JLabel("Indirizzo mail:");
		mailLabel.setBounds(46, 94, 90, 16);
		mainPane.add(mailLabel);

		loginBtn = new JButton("Login");
		loginBtn.setBounds(82, 303, 117, 29);
		loginBtn.addActionListener(e -> login());
		mainPane.add(loginBtn);

		forgottenPasswordBtn = new JButton("Password dimenticata?");
		forgottenPasswordBtn.setBounds(47, 262, 200, 29);
		forgottenPasswordBtn.addActionListener(e->forgottenPassword());
		mainPane.add(forgottenPasswordBtn);
		forgottenPasswordBtn.setOpaque(false);
		forgottenPasswordBtn.setContentAreaFilled(false);
		forgottenPasswordBtn.setBorderPainted(false);

		welcomeLbl = new JLabel("Benvenuto", SwingConstants.CENTER);
		welcomeLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		welcomeLbl.setBounds(82, 18, 137, 56);
		mainPane.add(welcomeLbl);

		userMsgLabel = new JLabel("", SwingConstants.CENTER);
		userMsgLabel.setBounds(57, 127, 190, 16);
		mainPane.add(userMsgLabel);

		passwordMsgLabel = new JLabel("", SwingConstants.CENTER);
		passwordMsgLabel.setBounds(57, 195, 190, 16);
		mainPane.add(passwordMsgLabel);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(148, 162, 130, 26);
		mainPane.add(passwordText);
		
		visibleCheck = new JCheckBox("Mostra password");
		visibleCheck.setBounds(82, 212, 152, 23);
		visibleCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(visibleCheck.isSelected()) {
					passwordText.setEchoChar((char) 0);
				}else {
					passwordText.setEchoChar('•');
				}
			}
		});
		mainPane.add(visibleCheck);
	}

	public void login() {
		Color errorColor = new Color(255, 0, 0, 100);
		mailText.setBackground(Color.WHITE);
		passwordText.setBackground(Color.WHITE);
		userMsgLabel.setText("");
		passwordMsgLabel.setText("");
		String mail = mailText.getText();
		char[] password = passwordText.getPassword();
		if (mail.isBlank() || mail.isEmpty()) {
			mailText.setBackground(errorColor);
			userMsgLabel.setText("Inserire indirizzo mail");
		}
		if (password.length==0){
			passwordText.setBackground(errorColor);
			passwordMsgLabel.setText("Inserire password");
		}
	}
	
	public void forgottenPassword() {
		//metodo da definire o rimuovere
	}

	public JPanel getMainPane() {
		return mainPane;
	}

	public void setMainPane(JPanel mainPane) {
		this.mainPane = mainPane;
	}

	public JTextField getMailText() {
		return mailText;
	}

	public void setMailText(JTextField mailText) {
		this.mailText = mailText;
	}

	public JLabel getUserMsgLabel() {
		return userMsgLabel;
	}

	public void setUserMsgLabel(JLabel userMsgLabel) {
		this.userMsgLabel = userMsgLabel;
	}

	public JLabel getPasswordMsgLabel() {
		return passwordMsgLabel;
	}

	public void setPasswordMsgLabel(JLabel passwordMsgLabel) {
		this.passwordMsgLabel = passwordMsgLabel;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(JLabel passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public JLabel getMailLabel() {
		return mailLabel;
	}

	public void setMailLabel(JLabel mailLabel) {
		this.mailLabel = mailLabel;
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(JButton loginBtn) {
		this.loginBtn = loginBtn;
	}

	public JButton getForgottenPasswordBtn() {
		return forgottenPasswordBtn;
	}

	public void setForgottenPasswordBtn(JButton forgottenPasswordBtn) {
		this.forgottenPasswordBtn = forgottenPasswordBtn;
	}

	public JLabel getWelcomeLbl() {
		return welcomeLbl;
	}

	public void setWelcomeLbl(JLabel welcomeLbl) {
		this.welcomeLbl = welcomeLbl;
	}

	public JPasswordField getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(JPasswordField passwordText) {
		this.passwordText = passwordText;
	}

	public JCheckBox getVisibleCheck() {
		return visibleCheck;
	}

	public void setVisibleCheck(JCheckBox visibleCheck) {
		this.visibleCheck = visibleCheck;
	}
	
}
