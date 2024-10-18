package com.bitcamp.centro.estetico.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.utils.inputValidator;
import com.bitcamp.centro.estetico.utils.inputValidator.inputValidatorException;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txfUsername;
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
            "Uno strumento per migliorare la produttività del tuo centro estetico."
    };

    public LoginFrame() {
        setTitle("Benvenuto nel Gestionale Centro Estetico");
        setName("Benvenuto nel Gestionale Centro Estetico");
        setSize(1024, 768);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel loginDataPanell = new JPanel();
        loginDataPanell.setLayout(null);
        loginDataPanell.setBounds(0, 0, 1024, 768);
        loginDataPanell.setBackground(Color.WHITE);

        // Componenti dell'interfaccia utente
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(0, 0, 1024, 305);
        imagePanel.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel(new ImageIcon(LoginFrame.class.getResource("/com/bitcamp/centro/estetico/resources/download.png")));
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

        descriptionLabel = new JLabel("Con il nostro gestionale, lavorare è semplice e intuitivo!");
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
        loginDataPanel.setBounds(363, 464, 449, 231);
        loginDataPanell.add(loginDataPanel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        passwordLabel.setBounds(37, 54, 63, 16);
        loginDataPanel.add(passwordLabel);

        txfUsername = new JTextField();
        txfUsername.setColumns(10);
        txfUsername.setBounds(117, 16, 152, 26);
        loginDataPanel.add(txfUsername);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        lblUsername.setBounds(37, 21, 69, 16);
        loginDataPanel.add(lblUsername);

        userMsgLabel = new JLabel("", SwingConstants.CENTER);
        userMsgLabel.setBounds(281, 26, 190, 16);
        loginDataPanel.add(userMsgLabel);

        passwordMsgLabel = new JLabel("", SwingConstants.CENTER);
        passwordMsgLabel.setBounds(281, 54, 190, 16);
        loginDataPanel.add(passwordMsgLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(117, 50, 152, 26);
        loginDataPanel.add(passwordText);

        JCheckBox visibleCheck = new JCheckBox("Mostra password");
        visibleCheck.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
        visibleCheck.setBounds(71, 103, 152, 23);
        visibleCheck.addActionListener(e -> {
            if (visibleCheck.isSelected()) {
                passwordText.setEchoChar((char) 0);
            } else {
                passwordText.setEchoChar('•');
            }
        });
        loginDataPanel.add(visibleCheck);

        JButton loginBtn = new JButton("Login");
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        loginBtn.setBackground(new Color(0, 153, 0));
        loginBtn.setBounds(47, 173, 200, 40);
        loginBtn.addActionListener(e -> login()); // Chiama il metodo login
        loginDataPanel.add(loginBtn);

        // Frase scorrevole con un timer
        descriptionTimer = new Timer(5000, e -> {
            indexFraseCorrente = (indexFraseCorrente + 1) % frasi.length;
            descriptionLabel.setText(frasi[indexFraseCorrente]);
        });
        descriptionTimer.start();

        getRootPane().setDefaultButton(loginBtn);

        setVisible(true);

        if(Main._DEBUG_MODE_FULL) {
            Employee currentEmployee = UserCredentialsDAO.getEmployeeOfUsername("fyuran").get();
            new MainFrame(currentEmployee, BeautyCenterDAO.getFirstBeautyCenter().get());
            dispose();
        }
    }

    // Metodo di login
    public void login() {
        userMsgLabel.setText("");
        passwordMsgLabel.setText("");
        String username = txfUsername.getText();
        char[] password = passwordText.getPassword();

        try {
            inputValidator.validateAlphanumeric(txfUsername, "Username"); // Validazione dell'username
            inputValidator.validatePassword(passwordText);
        } catch (inputValidatorException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
	        return;
        }
        // Verifica delle credenziali
        boolean isAuthenticated = UserCredentialsDAO.isValidPassword(username, password);
        if (isAuthenticated) {
            Employee currentEmployee = UserCredentialsDAO.getEmployeeOfUsername(username).get();
            new MainFrame(currentEmployee, BeautyCenterDAO.getFirstBeautyCenter().get());
            dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Credenziali non valide. Riprova.", "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
        }

     // Svuota i campi
        txfUsername.setText("");
        passwordText.setText("");
    }
}
