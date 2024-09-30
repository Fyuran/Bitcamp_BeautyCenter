package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class benvenutoOperatori extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField mailText;
	private JPasswordField passwordText;
	private JLabel userMsgLabel;
	private JLabel passwordMsgLabel;
    public benvenutoOperatori() {
        setTitle("Benvenuto Personale - Gestionale Centro Estetico");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        JPanel loginDataPanell = new JPanel();
        loginDataPanell.setLayout(null);
        loginDataPanell.setBounds(0, 0, 1024, 768);
        loginDataPanell.setBackground(Color.WHITE);

      
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(0, 0, 1024, 305);
        imagePanel.setBackground(Color.WHITE);

     
        ImageIcon icon = new ImageIcon("images/beauty_center_image.jpg"); 
        JLabel imageLabel = new JLabel(new ImageIcon(benvenutoOperatori.class.getResource("/iconeGestionale/download.png")));
        imageLabel.setBounds(152, 64, 723, 219); 
        imagePanel.add(imageLabel);

       
        JPanel textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBounds(434, 365, 590, 137);
        textPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
        welcomeLabel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 24));
        welcomeLabel.setBounds(16, 17, 516, 40);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descriptionLabel = new JLabel("Con il nostro gestionale, lavorare è semplice e intuitivo!");
        descriptionLabel.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 20));
        descriptionLabel.setBounds(16, 79, 567, 30);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        textPanel.add(welcomeLabel);
        textPanel.add(descriptionLabel);

       
        loginDataPanell.add(imagePanel);
        loginDataPanell.add(textPanel);

        
        getContentPane().setLayout(null);
        getContentPane().add(loginDataPanell);
        
        JPanel loginDataPanel = new JPanel();
        loginDataPanel.setLayout(null);
        loginDataPanel.setBackground(Color.WHITE);
        loginDataPanel.setBounds(47, 317, 300, 326);
        loginDataPanell.add(loginDataPanel);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(57, 122, 63, 16);
        loginDataPanel.add(passwordLabel);
        
        mailText = new JTextField();
        mailText.setColumns(10);
        mailText.setBounds(139, 16, 130, 26);
        loginDataPanel.add(mailText);
        
        JLabel mailLabel = new JLabel("Indirizzo mail:");
        mailLabel.setBounds(37, 21, 90, 16);
        loginDataPanel.add(mailLabel);
        
        JButton forgottenPasswordBtn = new JButton("Password dimenticata?");
        forgottenPasswordBtn.setOpaque(false);
        forgottenPasswordBtn.setContentAreaFilled(false);
        forgottenPasswordBtn.setBorderPainted(false);
        forgottenPasswordBtn.setBounds(37, 223, 200, 29);
        loginDataPanel.add(forgottenPasswordBtn);
        
        userMsgLabel = new JLabel("", SwingConstants.CENTER);
        userMsgLabel.setBounds(57, 54, 190, 16);
        loginDataPanel.add(userMsgLabel);
        
        passwordMsgLabel = new JLabel("", SwingConstants.CENTER);
        passwordMsgLabel.setBounds(57, 165, 190, 16);
        loginDataPanel.add(passwordMsgLabel);
        
        passwordText = new JPasswordField();
        passwordText.setBounds(132, 117, 130, 26);
        loginDataPanel.add(passwordText);
        
        JCheckBox visibleCheck = new JCheckBox("Mostra password");
        visibleCheck.setBounds(64, 188, 152, 23);
        loginDataPanel.add(visibleCheck);
        
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        loginBtn.setBackground(new Color(0, 153, 0));
        loginBtn.setBounds(47, 280, 200, 40);
        loginBtn.addActionListener(e -> login());
        loginDataPanel.add(loginBtn);
        setVisible(true);

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
}
