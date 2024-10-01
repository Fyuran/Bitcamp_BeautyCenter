package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class benvenutoOperatori extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtUsername;
	private JPasswordField passwordText;
    private JLabel userMsgLabel;
	private JLabel passwordMsgLabel;
	private JLabel descriptionLabel;
	private Timer descriptionTimer;
	private int indexFraseCorrente = 0;
	
	private String[] frasi = {
			"Con il nostro gestionale, lavorare è semplice e intuitivo!",
		    "Gestisci le prenotazioni con facilità.",
		    "Tieni traccia dei tuoi clienti in un unico posto.",
		    "Aggiorna le informazioni del centro estetico in pochi clic.",
		    "Monitora i prodotti e ricevi avvisi quando sono in esaurimento.",
		    "Crea report dettagliati sulle attività del centro.",
		    "Accedi alle schede dei clienti in modo rapido e sicuro.",
		    "Personalizza i trattamenti in base alle esigenze dei tuoi clienti.",
		    "Organizza gli orari dei dipendenti in modo efficace.",
		    "Un strumento per migliorare la produttività del tuo centro estetico."
	};
	
	
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
        textPanel.setBounds(142, 316, 737, 137);
        textPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
        welcomeLabel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 24));
        welcomeLabel.setBounds(16, 17, 693, 40);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descriptionLabel = new JLabel("Con il nostro gestionale, lavorare è semplice e intuitivo!");
        descriptionLabel.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 20));
        descriptionLabel.setBounds(0, 79, 737, 30);
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
        loginDataPanel.setBounds(363, 464, 300, 231);
        loginDataPanell.add(loginDataPanel);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        passwordLabel.setBounds(37, 54, 63, 16);
        loginDataPanel.add(passwordLabel);
        
        txtUsername = new JTextField();
        txtUsername.setColumns(10);
        txtUsername.setBounds(117, 16, 152, 26);
        loginDataPanel.add(txtUsername);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        lblUsername.setBounds(37, 21, 69, 16);
        loginDataPanel.add(lblUsername);
        
        JButton forgottenPasswordBtn = new JButton("Password dimenticata?");
        forgottenPasswordBtn.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        forgottenPasswordBtn.setOpaque(false);
        forgottenPasswordBtn.setContentAreaFilled(false);
        forgottenPasswordBtn.setBorderPainted(false);
        forgottenPasswordBtn.setBounds(37, 133, 200, 29);
        loginDataPanel.add(forgottenPasswordBtn);
        
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        userMsgLabel = new JLabel("", SwingConstants.CENTER);
        userMsgLabel.setBounds(57, 45, 190, 16);
        loginDataPanel.add(userMsgLabel);
        
=======
>>>>>>> ef637e3cca21b1785ffac9882ab6ec4b9d35dde5
>>>>>>> Stashed changes
        passwordMsgLabel = new JLabel("", SwingConstants.CENTER);
        passwordMsgLabel.setBounds(67, 150, 190, 16);
        loginDataPanel.add(passwordMsgLabel);
        
        passwordText = new JPasswordField();
        passwordText.setBounds(117, 50, 152, 26);
        loginDataPanel.add(passwordText);
        
        JCheckBox visibleCheck = new JCheckBox("Mostra password");
        visibleCheck.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        visibleCheck.setBounds(71, 103, 152, 23);
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
        loginBtn.setBounds(47, 173, 200, 40);
        loginBtn.addActionListener(e -> login());
        loginDataPanel.add(loginBtn);
        
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        JLabel tipsLbl = new JLabel("Eventuale label per i tips?");
        tipsLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        tipsLbl.setBounds(469, 571, 410, 61);
        loginDataPanell.add(tipsLbl);
=======
>>>>>>> Stashed changes
        
        //frase scorrevole, cicla l'array di frasi con un timer settato
        descriptionTimer = new Timer(2000, new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		indexFraseCorrente = (indexFraseCorrente + 1) % frasi.length; //cicla l'array di frasi e usa il modulo% per resettarsi a 0 e ricominciare 
        		descriptionLabel.setText(frasi[indexFraseCorrente]);
        	}
        });
        
        descriptionTimer.start();
        
<<<<<<< Updated upstream
=======
>>>>>>> ef637e3cca21b1785ffac9882ab6ec4b9d35dde5
>>>>>>> Stashed changes
        setVisible(true);

    }
    
    
    public void login() {
		Color errorColor = new Color(255, 0, 0, 100);
		txtUsername.setBackground(Color.WHITE);
		passwordText.setBackground(Color.WHITE);
		passwordMsgLabel.setText("");
		String mail = txtUsername.getText();
		char[] password = passwordText.getPassword();
		if (mail.isBlank() || mail.isEmpty()) {
			txtUsername.setBackground(errorColor);
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
