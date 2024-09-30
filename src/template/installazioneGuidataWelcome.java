package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class installazioneGuidataWelcome extends JFrame {

    public installazioneGuidataWelcome() {
        setTitle("Benvenuto nel Gestionale Centro Estetico");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Pannello principale con layout assoluto
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1024, 768);
        mainPanel.setBackground(Color.WHITE);
        
        // Pannello per l'immagine
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(0, 0, 1024, 400);
        imagePanel.setBackground(Color.WHITE);
        
        // Caricamento dell'immagine
        ImageIcon icon = new ImageIcon("iconeGestionale/download.png"); 
        JLabel imageLabel = new JLabel(new ImageIcon(installazioneGuidataWelcome.class.getResource("/iconeGestionale/download.png")));
        imageLabel.setBounds(151, 20, 723, 360); 
        imagePanel.add(imageLabel);
        
        // Pannello per la descrizione
        JPanel textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBounds(0, 400, 1024, 208);
        textPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
        welcomeLabel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 24));
        welcomeLabel.setBounds(200, 10, 624, 40);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea descriptionArea = new JTextArea(
                "Il gestionale è progettato per offrire una soluzione completa "
                + "per il tuo centro estetico. Organizza i tuoi appuntamenti, gestisci i clienti, "
                + "e monitora tutto il centro con facilità. "
                + "\n\nL'installazione guidata ti aiuterà a configurare rapidamente il software, "
                + "così potrai iniziare subito a ottimizzare la gestione del tuo centro estetico!"
        );
        descriptionArea.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBounds(100, 60, 824, 146);
        
        textPanel.add(welcomeLabel);
        textPanel.add(descriptionArea);

        
        mainPanel.add(imagePanel);
        mainPanel.add(textPanel);

        
        getContentPane().setLayout(null);
        getContentPane().add(mainPanel);
        
                JButton avantiButton = new JButton("Inizia l'Installazione");
                avantiButton.setBounds(423, 648, 200, 40);
                mainPanel.add(avantiButton);
                avantiButton.setFont(new Font("Arial", Font.BOLD, 16));
                avantiButton.setBackground(new Color(0, 153, 0)); // Colore verde moderno
                avantiButton.setForeground(new Color(0, 0, 0));
                avantiButton.setFocusPainted(false);
                avantiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                        // Azione per il pulsante
                        avantiButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Passa alla prossima finestra dell'installazione guidata
                                new installazioneGuidataDatiFiscali().setVisible(true);
                                dispose(); // Chiude la finestra di benvenuto
                            }
                        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new installazioneGuidataWelcome().setVisible(true);
        });
    }
}
