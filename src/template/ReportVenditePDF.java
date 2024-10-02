package template;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class ReportVenditePDF {
	static DefaultTableModel model;
	String[] columnNames= {"Prodotto", "Vendite", "Incassi (€)", "Spese (€)"};  // Nomi delle colonne


    public ReportVenditePDF() {
        // Creiamo il frame principale
        JFrame frame = new JFrame("Report di Vendite, Incassi e Spese");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        // Dati di esempio
        Object[][] data = {
            {"Prodotto A", 20, 200.0, 50.0},
            {"Prodotto B", 35, 350.0, 100.0},
            {"Prodotto C", 15, 150.0, 40.0},
            {"Prodotto D", 50, 500.0, 200.0},
            {"Prodotto E", 10, 100.0, 30.0}
        };


        // Modello della tabella
        model = new DefaultTableModel(data, columnNames);
        frame.getContentPane().setLayout(null);

        // Creazione della tabella
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 58, 800, 323);
        frame.getContentPane().add(scrollPane);

        // Pannello per inserimento nuovi dati
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(0, 0, 800, 58);
        JTextField prodottoField = new JTextField();
        prodottoField.setBounds(0, 29, 160, 29);
        prodottoField.setEditable(false);
        JTextField venditeField = new JTextField();
        venditeField.setBounds(160, 29, 160, 29);
        JTextField incassiField = new JTextField();
        incassiField.setBounds(320, 29, 160, 29);
        JTextField speseField = new JTextField();
        speseField.setBounds(480, 29, 160, 29);
        JButton aggiungiButton = new JButton("Aggiungi");
        aggiungiButton.setBounds(640, 29, 160, 29);
        inputPanel.setLayout(null);
        JLabel label_2 = new JLabel("Vendite");
        label_2.setBounds(160, 0, 160, 29);
        inputPanel.add(label_2);
        JLabel label_3 = new JLabel("Incassi (€)");
        label_3.setBounds(320, 0, 160, 29);
        inputPanel.add(label_3);
        JLabel label_4 = new JLabel("Spese (€)");
        label_4.setBounds(480, 0, 160, 29);
        inputPanel.add(label_4);
        JLabel label_5 = new JLabel("");
        label_5.setBounds(640, 0, 160, 29);
        inputPanel.add(label_5);
        
        inputPanel.add(prodottoField);
        inputPanel.add(venditeField);
        inputPanel.add(incassiField);
        inputPanel.add(speseField);
        inputPanel.add(aggiungiButton);

        frame.getContentPane().add(inputPanel);

        // Aggiungiamo un bottone per calcolare il totale
        JButton calcolaTotaleButton = new JButton("Calcola Totali");
        calcolaTotaleButton.setBounds(35, 5, 130, 29);
        JLabel risultatoLabel = new JLabel("Totali: ");
        risultatoLabel.setBounds(170, 11, 611, 16);
        
        JButton newProductButton = new JButton("Lista prodotti");
        newProductButton.setBounds(0, 1, 148, 29);
        newProductButton.addActionListener(p-> getProduct());
        
        // Bottone per esportare in PDF
        JButton esportaPDFButton = new JButton("Esporta in PDF");
        esportaPDFButton.setBounds(659, 39, 135, 29);
        
        // Bottone per applicare filtro
        JButton filtraButton = new JButton("Applica Filtro");
        filtraButton.setBounds(277, 39, 128, 29);
        JTextField filtroField = new JTextField(10);
        filtroField.setBounds(142, 40, 130, 26);
        
        // Pannello inferiore
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 380, 800, 92);
        bottomPanel.setLayout(null);
        bottomPanel.add(calcolaTotaleButton);
        bottomPanel.add(risultatoLabel);
        JLabel label = new JLabel("Filtro Prodotto:");
        label.setBounds(35, 45, 95, 16);
        bottomPanel.add(label);
        bottomPanel.add(filtroField);
        bottomPanel.add(filtraButton);
        bottomPanel.add(esportaPDFButton);
        inputPanel.add(newProductButton);

        
        //bottone per rimuovere i filtri
        JButton rimuoviFiltriButton = new JButton("Rimuovi Filtri");
        rimuoviFiltriButton.setBounds(417, 39, 128, 29);
        bottomPanel.add(rimuoviFiltriButton);
        
        frame.getContentPane().add(bottomPanel);

        // Listener per aggiungere nuovi dati
        aggiungiButton.addActionListener(e -> {
            String prodotto = prodottoField.getText();
            int vendite;
            double incassi, spese;

            //check che il campo prodotto non sia vuoto
            if(prodotto.isBlank()||prodotto.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inserisci un prodotto valido.");
                return;
            }
            try {
                vendite = Integer.parseInt(venditeField.getText());
                incassi = Double.parseDouble(incassiField.getText());
                spese = Double.parseDouble(speseField.getText());
                //check che il numero vendite sia positivo
                if(vendite<1) {
                    JOptionPane.showMessageDialog(frame, "Inserisci valore numerico positivo per Vendite.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Inserisci valori numerici validi per Vendite, Incassi e Spese.");
                return;
            }

            model.addRow(new Object[]{prodotto, vendite, incassi, spese});

            // Puliamo i campi
            prodottoField.setText("");
            venditeField.setText("");
            incassiField.setText("");
            speseField.setText("");
            risultatoLabel.setText("");
            
            
            
        });

        // Listener per calcolare il totale
        calcolaTotaleButton.addActionListener(e -> {
            int totaleVendite = 0;
            double totaleIncassi = 0;
            double totaleSpese = 0;
            double totaleEntrate=0;

            for (int i = 0; i < model.getRowCount(); i++) {
                totaleVendite += (int) model.getValueAt(i, 1);
                totaleIncassi += (double) model.getValueAt(i, 2);
                totaleSpese += (double) model.getValueAt(i, 3);
                totaleEntrate=totaleIncassi-totaleSpese;
            }

            risultatoLabel.setText(String.format("Totali - Vendite: %d, Incassi: €%.2f, Spese: €%.2f, Totale entrate: €%.2f",
                                                 totaleVendite, totaleIncassi, totaleSpese, totaleEntrate));
            
            
        });

        // Listener per esportare in PDF
        esportaPDFButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int scelta = fileChooser.showSaveDialog(frame);
            if (scelta == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    esportaInPDF(file, model);
                    JOptionPane.showMessageDialog(frame, "Esportazione in PDF completata.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Errore durante l'esportazione in PDF.");
                }
            }
        });

        // Listener per applicare il filtro
        filtraButton.addActionListener((ActionEvent e) -> {
            String filtro = filtroField.getText();
            filtraDati(filtro);
        });

        // Mostra la finestra
        frame.setVisible(true);
    }

    // Metodo per esportare i dati della tabella in un file PDF usando Apache PDFBox
    private static void esportaInPDF(File file, DefaultTableModel model) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Impostiamo il font
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD),12);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(50, 750);

        // Scriviamo i titoli delle colonne
        String header = String.format("%-20s %-10s %-15s %-10s", "Prodotto", "Vendite", "Incassi (€)", "Spese (€)");
        contentStream.showText(header);
        contentStream.newLine();
        contentStream.endText();

        // Scriviamo i dati della tabella
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 730);

        for (int i = 0; i < model.getRowCount(); i++) {
            String prodotto = (String) model.getValueAt(i, 0);
            int vendite = (int) model.getValueAt(i, 1);
            double incassi = (double) model.getValueAt(i, 2);
            double spese = (double) model.getValueAt(i, 3);

            String row = String.format("%-20s %-10d %-15.2f %-10.2f", prodotto, vendite, incassi, spese);
            contentStream.showText(row);
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.close();

        // Salva il PDF nel file specificato
        document.save(file);
        document.close();
    }

    // Metodo per filtrare i dati
    private static void filtraDati(String filtro) {
    	/*Questa funzione crea un grosso problema: quando fa una ricerca per filtro
    	 i prodotti non corrispondenti vengono rimossi permanentemente.
    	 Da modificare nel seguente modo:
    	 va creato un DefaultTableModel locale in cui si aggiungono tutti i prodotti
    	 corrispondenti usando una query sql.
    	 Per ora non la tocco perché non sono sicuro di cosa sia il valore "spesa"
    	 e di come sarà gestita la query - Daniele*/
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String prodotto = (String) model.getValueAt(i, 0);
            if (!prodotto.toLowerCase().contains(filtro.toLowerCase())) {
                model.removeRow(i);
            }

        }
    }
    
    //Metodo per retrivare i prodotti
    private static void getProduct() {
    	ProductSelector products=new ProductSelector();
    	products.setAlwaysOnTop(true);
    }
}