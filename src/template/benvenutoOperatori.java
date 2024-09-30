package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class benvenutoOperatori extends JFrame {

    public benvenutoOperatori() {
        setTitle("Benvenuto Personale - Gestionale Centro Estetico");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1024, 768);
        mainPanel.setBackground(Color.WHITE);

      
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(0, 0, 1024, 353);
        imagePanel.setBackground(Color.WHITE);

     
        ImageIcon icon = new ImageIcon("images/beauty_center_image.jpg"); 
        JLabel imageLabel = new JLabel(new ImageIcon(benvenutoOperatori.class.getResource("/icone/download.png")));
        imageLabel.setBounds(152, 64, 723, 260); 
        imagePanel.add(imageLabel);

       
        JPanel textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBounds(0, 364, 1024, 137);
        textPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Centro Estetico!");
        welcomeLabel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 24));
        welcomeLabel.setBounds(200, 20, 624, 40);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descriptionLabel = new JLabel("Con il nostro gestionale, lavorare è semplice e intuitivo!");
        descriptionLabel.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 20));
        descriptionLabel.setBounds(200, 80, 624, 30);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        textPanel.add(welcomeLabel);
        textPanel.add(descriptionLabel);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 550, 1024, 100);
        buttonPanel.setBackground(Color.WHITE);

        JButton entraButton = new JButton("Login");
        entraButton.setFont(new Font("Arial", Font.BOLD, 16));
        entraButton.setBackground(new Color(0, 153, 0)); 
        entraButton.setForeground(Color.BLACK);
        entraButton.setFocusPainted(false);
        entraButton.setBounds(413, 31, 200, 40); 
        entraButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(entraButton);

     
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new containerAdmin().setVisible(true); 
                dispose();
            }
        });

       
        mainPanel.add(imagePanel);
        mainPanel.add(textPanel);
        mainPanel.add(buttonPanel);

        
        getContentPane().setLayout(null);
        getContentPane().add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new benvenutoOperatori().setVisible(true);
        });
    }
}
