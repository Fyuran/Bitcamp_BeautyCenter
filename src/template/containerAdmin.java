package template;

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
        setBounds(100, 100, 1045, 768);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); 

        setContentPane(contentPane);

        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        tabbedPane.setBounds(0, 0, 1032, 729); 

        
        gestioneClienti gestioneClienti = new gestioneClienti();
        gestionePremi gestionePremi = new gestionePremi();
        gestioneTurni gestioneTurni = new gestioneTurni();
        gestioneProdotti gestioneProdotti = new gestioneProdotti();
        

        
        tabbedPane.addTab("CLIENTI", gestioneClienti);
        tabbedPane.addTab("PREMI", gestionePremi);
        tabbedPane.addTab("TURNI", gestioneTurni);
        tabbedPane.addTab("PRODOTTI", gestioneProdotti);

        
        contentPane.add(tabbedPane);
    }
}
