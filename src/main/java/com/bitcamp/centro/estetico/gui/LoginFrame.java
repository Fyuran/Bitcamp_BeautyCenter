package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.utils.JSplitPf;
import com.bitcamp.centro.estetico.utils.JSplitTxf;
import com.github.weisj.darklaf.components.border.DarkBorders;

public class LoginFrame {
    private static JFrame frame;
    private static JSplitTxf userTxf;
    private static JSplitPf passwordTxf;
    private static JLabel descriptionLb;
    private static Timer descriptionTimer;
    private static int currentSentenceIndex = 0;
    private static Font fontb_20 = new Font("MS Reference Sans Serif", Font.BOLD, 24);
    private static Font fontp_20 = new Font("MS Reference Sans Serif", Font.PLAIN, 20);

    private String[] sentences = {
            "Con il nostro gestionale, lavorare è semplice e intuitivo!",
            "Gestisci le prenotazioni con facilità.",
            "Tieni traccia dei tuoi clienti in un unico posto.",
            "Aggiorna le informazioni del centro estetico in pochi clic.",
            "Accedi alle schede dei clienti in modo rapido e sicuro.",
            "Personalizza i trattamenti in base alle esigenze dei tuoi clienti.",
            "Organizza gli orari dei dipendenti in modo efficace.",
            "Uno strumento per migliorare la produttività del tuo centro estetico."
    };

    public LoginFrame() {
        frame = new JFrame("Benvenuto nel Gestionale Centro Estetico");
        frame.setName("Benvenuto nel Gestionale Centro Estetico");
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon(
            MainFrame.class.getResource("/com/bitcamp/centro/estetico/resources/bc_icon.png")).getImage());
        frame.setResizable(false);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getRootPane().setBorder(DarkBorders.createWidgetLineBorder(5, 5, 5, 5));

        JPanel p1 = new JPanel(new GridBagLayout());
        frame.add(p1);
        GridBagConstraints gbc = new GridBagConstraints();

        Image img = new ImageIcon(LoginFrame.class.getResource("/com/bitcamp/centro/estetico/resources/download.png"))
                .getImage();
        img = img.getScaledInstance(600, -1, Image.SCALE_FAST);
        JLabel imgLb = new JLabel(new ImageIcon(img));
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        p1.add(imgLb, gbc);

        JLabel welcomeLb = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
        welcomeLb.setFont(fontb_20);
        welcomeLb.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(welcomeLb, gbc);

        descriptionLb = new JLabel("Con il nostro gestionale, lavorare è semplice e intuitivo!");
        descriptionLb.setFont(fontp_20);
        descriptionLb.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(descriptionLb, gbc);

        JPanel p2 = new JPanel(new GridBagLayout());
        p2.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        frame.add(p2);
        
        userTxf = new JSplitTxf("Username");
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        p2.add(userTxf, gbc);

        passwordTxf = new JSplitPf("Password");
        gbc.gridy = 1;
        p2.add(passwordTxf, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
        loginBtn.addActionListener(e -> login());
        
        gbc.gridy = 2;
        gbc.ipadx = 2;
        gbc.ipady = 2;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        loginBtn.setMinimumSize(new Dimension(200, 30));
        p2.add(loginBtn, gbc);

        descriptionTimer = new Timer(5000, e -> {
            currentSentenceIndex = (currentSentenceIndex + 1) % sentences.length;
            descriptionLb.setText(sentences[currentSentenceIndex]);
        });
        descriptionTimer.start();

        frame.getRootPane().setDefaultButton(loginBtn);
        frame.setVisible(true);
    }

    public void login() {
        String username = userTxf.getText();
        char[] password = passwordTxf.getPassword();

        if (Main._DEBUG_MODE_FULL) {
            User user = DAO.getUser("admin").get();
            new MainFrame(user, DAO.get(BeautyCenter.class, 1L).get());
            frame.dispose();
            return;
        }

        User user = DAO.getUser(username).get();
        if (user.isValidPassword(password)) {
            new MainFrame(user, DAO.get(BeautyCenter.class, 1L).get());
            frame.dispose();

        } else {
            JOptionPane.showMessageDialog(frame, "Credenziali non valide.", "Errore di Accesso",
                    JOptionPane.ERROR_MESSAGE);
        }

        userTxf.setText("");
        passwordTxf.setText("");
    }
}
