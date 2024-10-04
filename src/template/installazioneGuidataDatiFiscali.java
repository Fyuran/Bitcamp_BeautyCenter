package template;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;

import template.creaAccount;
import utils.inputValidator;
import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.VAT;
import utils.placeholderHelper;

public class installazioneGuidataDatiFiscali extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txfSearchBar;
    private JTextField txfName;
    private JTextField txfPhone;
    private JTextField txfPEC;
    private JTextField txfEmail;
    private JTextField txfSedeLegale;
    private JTextField txfSedeOperativa;
    private JTextField txfRea;
    private JTextField txfPIva;
    private JTextField txfApertura;
    private JTextField txfChiusura;
    private JComboBox<Double> cbxAliquota;
    private JButton btnConfermaInserisci;
    private JButton btnConfermaModifica;
    private JButton btnAnnulla;

    private int selectedRow = -1;
    DefaultTableModel tableModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    installazioneGuidataDatiFiscali frame = new installazioneGuidataDatiFiscali();
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
    public installazioneGuidataDatiFiscali() {
    	
        setTitle("Centro Estetico - Installazione Guidata- Dati Fiscali");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Inizializza la connessione tramite un'istanza di Main
        try {
            Main main = new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "root"); //cambiare user e password 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore durante la connessione al database: " + e.getMessage());
            e.printStackTrace();
        }

        JLabel titleDatiFiscali = new JLabel("INIZIALIZZA IL TUO CENTRO - INSERISCI  I DATI FISCALI");
        titleDatiFiscali.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
        titleDatiFiscali.setBounds(241, 11, 513, 32);
        contentPane.add(titleDatiFiscali);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(null);
        containerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setBounds(10, 65, 988, 347);
        contentPane.add(containerPanel);

        // Modello della tabella con l'ID come colonna nascosta
        String[] columnNames = { "ID", "Nome", "Telefono", "PEC", "Email", "Sede Legale", "Sede Operativa", "REA",
                "P.Iva", "Apertura", "Chiusura" };
        
     // Rende tutte le celle non modificabili
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        
        JTable table = new JTable(tableModel);;
      
        
        
        // Listener per il click sulla tabella
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txfName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txfPhone.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txfPEC.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txfEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txfSedeLegale.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    txfSedeOperativa.setText(tableModel.getValueAt(selectedRow, 6).toString());
                    txfRea.setText(tableModel.getValueAt(selectedRow, 7).toString());
                    txfPIva.setText(tableModel.getValueAt(selectedRow, 8).toString());
                    txfApertura.setText(tableModel.getValueAt(selectedRow, 9).toString());
                    txfChiusura.setText(tableModel.getValueAt(selectedRow, 10).toString());
                }
            }
        });

        // Nascondi la colonna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Aggiungere la tabella all'interno di uno JScrollPane per lo scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(23, 60, 943, 276);
        containerPanel.add(scrollPane);

        JButton btnSearch = new JButton("");
        btnSearch.setIcon(new ImageIcon(installazioneGuidataDatiFiscali.class.getResource("/iconeGestionale/searchIcon.png")));
        btnSearch.setOpaque(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setBounds(206, 8, 40, 30);
        containerPanel.add(btnSearch);
        
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = txfSearchBar.getText().trim().toLowerCase();
                
                tableModel.setRowCount(0);

                if (searchText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserisci un testo nella barra di ricerca.");
                    return;
                }

                List<BeautyCenter> beautyCenters = BeautyCenter.getAllData();

                // Filtra i dati in base alla ricerca per nome o partita IVA
                for (BeautyCenter bc : beautyCenters) {
                    String nameLower = bc.getName().toLowerCase();
                    String pIvaLower = bc.getP_IVA().toLowerCase();
                    
                    if (nameLower.contains(searchText) || pIvaLower.contains(searchText)) {
                        Object[] rowData = {
                            bc.getId(),
                            bc.getName(),
                            bc.getPhone(),
                            bc.getCertifiedMail(),
                            bc.getMail(),
                            bc.getRegisteredOffice(),
                            bc.getOperatingOffice(),
                            bc.getREA(),
                            bc.getP_IVA(),
                            bc.getOpeningHour().toString(),
                            bc.getClosingHour().toString()
                        };
                        tableModel.addRow(rowData);
                    }
                }
                
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Nessun record trovato per il testo inserito.");
                }
            }
        });

        txfSearchBar = new JTextField();
        placeholderHelper.addPlaceholder(txfSearchBar, "Ricerca per nome o p.Iva");
        txfSearchBar.setColumns(10);
        txfSearchBar.setBackground(UIManager.getColor("CheckBox.background"));
        txfSearchBar.setBounds(23, 14, 168, 24);
        containerPanel.add(txfSearchBar);

        JButton btnInsert = new JButton("");
        btnInsert.setIcon(new ImageIcon(installazioneGuidataDatiFiscali.class.getResource("/iconeGestionale/Insert.png")));
        btnInsert.setOpaque(false);
        btnInsert.setContentAreaFilled(false);
        btnInsert.setBorderPainted(false);
        btnInsert.setBounds(776, 8, 40, 30);
        containerPanel.add(btnInsert);

        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableInputs();
                btnConfermaInserisci.setVisible(true);
                btnAnnulla.setVisible(true);
            }
        });


        JButton btnUpdate = new JButton("");
        btnUpdate.setIcon(new ImageIcon(installazioneGuidataDatiFiscali.class.getResource("/iconeGestionale/Update.png")));
        btnUpdate.setOpaque(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setBounds(826, 8, 40, 30);
        containerPanel.add(btnUpdate);

        // Listener per il pulsante "Aggiorna"
        btnUpdate.addActionListener(e -> {
            if (selectedRow >= 0) {
                enableInputs();
                btnConfermaInserisci.setVisible(false);
                btnConfermaModifica.setVisible(true);
                btnAnnulla.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Seleziona una riga da aggiornare.");
            }
        });

        JButton btnDisable = new JButton("");
        btnDisable.setIcon(new ImageIcon(installazioneGuidataDatiFiscali.class.getResource("/iconeGestionale/disattivo.png")));
        btnDisable.setOpaque(false);
        btnDisable.setContentAreaFilled(false);
        btnDisable.setBorderPainted(false);
        btnDisable.setBounds(876, 8, 40, 30);
        containerPanel.add(btnDisable);

        // Listener per il pulsante "Disabilita"
        btnDisable.addActionListener(e -> {
            if (selectedRow >= 0) {
                try {
                    int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    int result = BeautyCenter.toggleEnabledData(id);
                    if (result != -1) {
                        tableModel.removeRow(selectedRow);
                        selectedRow = -1;
                        JOptionPane.showMessageDialog(null, "Centro disabilitato con successo!");
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore durante la disabilitazione.");
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Errore nel formato dell'ID: " + nfe.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleziona una riga da disabilitare.");
            }
        });

        JButton btnHystorical = new JButton("");
        btnHystorical.setIcon(new ImageIcon(installazioneGuidataDatiFiscali.class.getResource("/iconeGestionale/cartellina.png")));
        btnHystorical.setOpaque(false);
        btnHystorical.setContentAreaFilled(false);
        btnHystorical.setBorderPainted(false);
        btnHystorical.setBounds(926, 8, 40, 30);
        containerPanel.add(btnHystorical);

        // Listener per il pulsante "Hystorical"
        btnHystorical.addActionListener(e -> {
            tableModel.setRowCount(0); 
            List<BeautyCenter> beautyCenters = BeautyCenter.getAllData();

            for (BeautyCenter bc : beautyCenters) {
                try {
                    // Imposta una lista VAT vuota se non è già presente
                    if (bc.getInfoVat() == null) {
                        bc.setInfoVat(new ArrayList<>());
                    }

                    Object[] rowData = { 
                        bc.getId(),
                        bc.getName(),
                        bc.getPhone(),
                        bc.getCertifiedMail(),
                        bc.getMail(),
                        bc.getRegisteredOffice(),
                        bc.getOperatingOffice(),
                        bc.getREA(),
                        bc.getP_IVA(),
                        bc.getOpeningHour().toString(),
                        bc.getClosingHour().toString()
                    };
                    tableModel.addRow(rowData);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore nel recupero dei dati: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            if (beautyCenters.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nessun record trovato nel database.");
            }
        });

        JLabel lblName = new JLabel("Nome:");
        lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblName.setBounds(50, 448, 170, 14);
        contentPane.add(lblName);

        txfName = new JTextField();
        placeholderHelper.addPlaceholder(txfName, "Obbligatorio");
        txfName.setEnabled(false);
        txfName.setColumns(10);
        txfName.setBounds(224, 447, 220, 20);
        contentPane.add(txfName);

        JLabel lblPhone = new JLabel("Contatto Telefonico:");
        lblPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblPhone.setBounds(50, 490, 170, 14);
        contentPane.add(lblPhone);

        txfPhone = new JTextField();
        txfPhone.setEnabled(false);
        txfPhone.setColumns(10);
        txfPhone.setBounds(224, 489, 220, 20);
        contentPane.add(txfPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblEmail.setBounds(50, 574, 170, 14);
        contentPane.add(lblEmail);

        txfEmail = new JTextField();
        txfEmail.setEnabled(false);
        txfEmail.setColumns(10);
        txfEmail.setBounds(224, 573, 220, 20);
        contentPane.add(txfEmail);

        JLabel lblPEC = new JLabel("PEC:");
        lblPEC.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblPEC.setBounds(50, 532, 170, 14);
        contentPane.add(lblPEC);

        txfPEC = new JTextField();
        txfPEC.setEnabled(false);
        txfPEC.setColumns(10);
        txfPEC.setBounds(224, 531, 220, 20);
        contentPane.add(txfPEC);

        JLabel lblSedeLegale = new JLabel("Sede Legale:");
        lblSedeLegale.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblSedeLegale.setBounds(50, 616, 170, 14);
        contentPane.add(lblSedeLegale);

        txfSedeLegale = new JTextField();
        placeholderHelper.addPlaceholder(txfSedeLegale, "Obbligatorio");
        txfSedeLegale.setEnabled(false);
        txfSedeLegale.setColumns(10);
        txfSedeLegale.setBounds(224, 615, 220, 20);
        contentPane.add(txfSedeLegale);

        JLabel lblSedeOperativa = new JLabel("Sede Operativa:");
        lblSedeOperativa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblSedeOperativa.setBounds(50, 658, 170, 14);
        contentPane.add(lblSedeOperativa);

        txfSedeOperativa = new JTextField();
        placeholderHelper.addPlaceholder(txfSedeOperativa, "Obbligatorio");
        txfSedeOperativa.setEnabled(false);
        txfSedeOperativa.setColumns(10);
        txfSedeOperativa.setBounds(224, 657, 220, 20);
        contentPane.add(txfSedeOperativa);

        JLabel lblREA = new JLabel("REA:");
        lblREA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblREA.setBounds(549, 448, 170, 14);
        contentPane.add(lblREA);

        txfRea = new JTextField();
        placeholderHelper.addPlaceholder(txfRea, "Obbligatorio");
        txfRea.setEnabled(false);
        txfRea.setColumns(10);
        txfRea.setBounds(729, 447, 220, 20);
        contentPane.add(txfRea);

        JLabel lblPIva = new JLabel("Partita IVA:");
        lblPIva.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblPIva.setBounds(549, 492, 170, 14);
        contentPane.add(lblPIva);

        txfPIva = new JTextField();
        placeholderHelper.addPlaceholder(txfPIva, "Obbligatorio");
        txfPIva.setEnabled(false);
        txfPIva.setColumns(10);
        txfPIva.setBounds(729, 489, 220, 20);
        contentPane.add(txfPIva);

        JLabel lblApertura = new JLabel("Orario Apertura:");
        lblApertura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblApertura.setBounds(549, 532, 170, 14);
        contentPane.add(lblApertura);

        txfApertura = new JTextField();
        placeholderHelper.addPlaceholder(txfApertura, "Obbligatorio");
        txfApertura.setEnabled(false);
        txfApertura.setColumns(10);
        txfApertura.setBounds(729, 531, 220, 20);
        contentPane.add(txfApertura);

        JLabel lblAliquota = new JLabel("Aliquota:");
        lblAliquota.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblAliquota.setBounds(549, 616, 170, 14);
        contentPane.add(lblAliquota);
        
        cbxAliquota = new JComboBox<>(new Double[] { 5.0, 10.0, 22.0 }); 
        cbxAliquota.setBounds(729, 615, 220, 20);
        cbxAliquota.setEnabled(false); 
        contentPane.add(cbxAliquota);
        
        JLabel lblChiusura = new JLabel("Orario Chiusura:");
        lblChiusura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        lblChiusura.setBounds(549, 574, 170, 14);
        contentPane.add(lblChiusura);

        txfChiusura = new JTextField();
        placeholderHelper.addPlaceholder(txfChiusura, "Obbligatorio");
        txfChiusura.setEnabled(false);
        txfChiusura.setColumns(10);
        txfChiusura.setBounds(729, 573, 220, 20);
        contentPane.add(txfChiusura);

        JButton btnNewButton = new JButton("AVANTI");
        btnNewButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 13));
        btnNewButton.setBackground(new Color(0, 204, 102));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creaAccount accountFrame = new creaAccount();
                accountFrame.setVisible(true);
                dispose(); // chiude la finestra corrente
            }
        });
        btnNewButton.setBounds(729, 695, 220, 23);
        contentPane.add(btnNewButton);

        btnConfermaInserisci = new JButton("Inserisci");
        btnConfermaInserisci.setBounds(729, 652, 100, 30);
        btnConfermaInserisci.setVisible(false);
        contentPane.add(btnConfermaInserisci);

        btnConfermaInserisci.addActionListener(e -> {
            if (!validateInputs()) {
                JOptionPane.showMessageDialog(null, inputValidator.getErrorMessage());
                return;
            }

            try {
                // Estrai i dati dai campi di input
                String name = txfName.getText();
                String phone = txfPhone.getText();
                String certifiedMail = txfPEC.getText();
                String email = txfEmail.getText();
                String registeredOffice = txfSedeLegale.getText();
                String operatingOffice = txfSedeOperativa.getText();
                String rea = txfRea.getText();
                String pIva = txfPIva.getText();
                LocalTime openingHour = LocalTime.parse(txfApertura.getText());
                LocalTime closingHour = LocalTime.parse(txfChiusura.getText());

                // Ottieni l'aliquota selezionata dalla ComboBox
                double selectedVatRate = (double) cbxAliquota.getSelectedItem();

                // Crea un nuovo oggetto VAT e inserisci nel database
                VAT selectedVat = new VAT(selectedVatRate);
                int vatId = VAT.insertData(selectedVat);
                if (vatId == -1) {
                    JOptionPane.showMessageDialog(null, "Errore durante l'inserimento del VAT.");
                    return;
                }

                // Crea un nuovo oggetto BeautyCenter 
                BeautyCenter beautyCenter = new BeautyCenter(name, phone, certifiedMail, email, registeredOffice,
                        operatingOffice, rea, pIva, openingHour, closingHour);
                List<VAT> vatList = new ArrayList<>();
                vatList.add(selectedVat);
                beautyCenter.setInfoVat(vatList);

                int result = BeautyCenter.insertData(beautyCenter);
                if (result != -1) {
                    Object[] rowData = { beautyCenter.getId(), name, phone, certifiedMail, email, registeredOffice,
                            operatingOffice, rea, pIva, openingHour.toString(), closingHour.toString(), selectedVatRate };
                    tableModel.addRow(rowData);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dei dati.");
                }

                disableInputs();
                btnConfermaInserisci.setVisible(false);
                btnAnnulla.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Errore durante l'inserimento: " + ex.getMessage());
            }
        });





        btnConfermaModifica = new JButton("Modifica");
        btnConfermaModifica.setBounds(729, 652, 100, 30);
        btnConfermaModifica.setVisible(false);
        contentPane.add(btnConfermaModifica);

        // "Conferma Modifica"
        btnConfermaModifica.addActionListener(e -> {
            if (!validateInputs()) {
                JOptionPane.showMessageDialog(null, inputValidator.getErrorMessage());
                return;
            }

            try {
                // Estrai i dati dai campi di input
                String name = txfName.getText();
                String phone = txfPhone.getText();
                String certifiedMail = txfPEC.getText();
                String email = txfEmail.getText();
                String registeredOffice = txfSedeLegale.getText();
                String operatingOffice = txfSedeOperativa.getText();
                String rea = txfRea.getText();
                String pIva = txfPIva.getText();
                LocalTime openingHour = LocalTime.parse(txfApertura.getText());
                LocalTime closingHour = LocalTime.parse(txfChiusura.getText());

                // Ottieni l'ID del BeautyCenter dalla riga selezionata
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

                // Crea e aggiorna il BeautyCenter con le nuove informazioni
                BeautyCenter updatedBeautyCenter = new BeautyCenter(name, phone, certifiedMail, email, registeredOffice,
                        operatingOffice, rea, pIva, openingHour, closingHour);

                // Imposta una lista VAT vuota per evitare problemi 
                updatedBeautyCenter.setInfoVat(new ArrayList<>());

                int result = BeautyCenter.updateData(id, updatedBeautyCenter);
                if (result != -1) {
                    Object[] rowData = { id, name, phone, certifiedMail, email, registeredOffice, operatingOffice,
                            rea, pIva, openingHour.toString(), closingHour.toString() };
                    for (int i = 0; i < rowData.length; i++) {
                        tableModel.setValueAt(rowData[i], selectedRow, i);
                    }
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Errore durante l'aggiornamento dei dati.");
                }

                disableInputs();
                btnConfermaModifica.setVisible(false);
                btnAnnulla.setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Errore nel formato degli orari: " + ex.getMessage());
            }
        });


        btnAnnulla = new JButton("Annulla");
        btnAnnulla.setBounds(849, 652, 100, 30);
        btnAnnulla.setVisible(false);
        contentPane.add(btnAnnulla);
        
        

        // Listener per il pulsante "Annulla"
        btnAnnulla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
                disableInputs();

                btnConfermaInserisci.setVisible(false);
                btnConfermaModifica.setVisible(false);
                btnAnnulla.setVisible(false);
            }
        });
    }

    // Metodo per svuotare i campi di input
    private void clearFields() {
        txfName.setText("");
        txfPhone.setText("");
        txfPEC.setText("");
        txfEmail.setText("");
        txfSedeLegale.setText("");
        txfSedeOperativa.setText("");
        txfRea.setText("");
        txfPIva.setText("");
        txfApertura.setText("");
        txfChiusura.setText("");
    }

    // Metodo per disattivare i campi di input
    private void disableInputs() {
        txfName.setEnabled(false);
        txfPhone.setEnabled(false);
        txfPEC.setEnabled(false);
        txfEmail.setEnabled(false);
        txfSedeLegale.setEnabled(false);
        txfSedeOperativa.setEnabled(false);
        txfRea.setEnabled(false);
        txfPIva.setEnabled(false);
        txfApertura.setEnabled(false);
        txfChiusura.setEnabled(false);
        cbxAliquota.setEnabled(false);
    }

    // Metodo per attivare i campi di input
    private void enableInputs() {
        txfName.setEnabled(true);
        txfPhone.setEnabled(true);
        txfPEC.setEnabled(true);
        txfEmail.setEnabled(true);
        txfSedeLegale.setEnabled(true);
        txfSedeOperativa.setEnabled(true);
        txfRea.setEnabled(true);
        txfPIva.setEnabled(true);
        txfApertura.setEnabled(true);
        txfChiusura.setEnabled(true);
        cbxAliquota.setEnabled(true);
    }

    // Metodo per validare gli input 
    private boolean validateInputs() {
        return inputValidator.validateAlphanumeric(txfName.getText()) && inputValidator.validatePhoneNumber(txfPhone.getText())
                && inputValidator.validatePEC(txfPEC.getText()) && inputValidator.validateEmail(txfEmail.getText())
                && inputValidator.validateAlphanumeric(txfSedeLegale.getText())
                && inputValidator.validateAlphanumeric(txfSedeOperativa.getText())
                && inputValidator.validateAlphanumeric(txfRea.getText())
                && inputValidator.validatePIva(txfPIva.getText())
                && inputValidator.validateTime(txfApertura.getText())
                && inputValidator.validateTime(txfChiusura.getText());
    }
}
