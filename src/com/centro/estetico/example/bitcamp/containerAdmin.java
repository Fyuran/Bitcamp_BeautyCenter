package com.centro.estetico.example.bitcamp;
import java.awt.BorderLayout;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class containerAdmin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    containerAdmin frame = new containerAdmin();
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
    public containerAdmin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 1045, 768);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //contentPane.setLayout(null); // Layout nullo per il content pane
        contentPane.setLayout(new BorderLayout());

        setContentPane(contentPane);

        // Crea il JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        tabbedPane.setBounds(0, 0, 1032, 729); // Imposta le dimensioni del tabbedPane

        // Crea i tuoi JPanel personalizzati e aggiungili direttamente ai tab
        gestioneClienti gestioneClienti_ = new gestioneClienti();
        gestionePremi gestionePremi_ = new gestionePremi();
        gestioneTurni gestioneTurni_ = new gestioneTurni();
        ReservationForm reservationForm = new ReservationForm();

        // Aggiungi i pannelli ai tab con i nomi appropriati
        tabbedPane.addTab("CLIENTI", gestioneClienti_);
        tabbedPane.addTab("PREMI", gestionePremi_);
        tabbedPane.addTab("TURNI", gestioneTurni_);
        tabbedPane.addTab("APPUNTAMENTI", reservationForm);

        // Aggiungi il tabbedPane al contentPane
        contentPane.add(tabbedPane);
    }
}
