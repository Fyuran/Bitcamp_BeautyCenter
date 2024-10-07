package template;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Treatments;

public class reportC {
	private static DefaultTableModel model;

    public static void main(String[] args) {
        // Pannello
		Main main = new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");
        JFrame frame = new JFrame("Report di Vendite, Incassi e Spese del Centro Estetico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        

        //  colonne
        String[] columnNames = {"Servizio", "Vendite", "Incassi (€)", "Spese (€)"};

        // Modello della tabella (Spiegato da GBT)
        model = new DefaultTableModel(columnNames,0);
        populateTable();

        // Creazione della tabella
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        // Pannello per inserimento nuovi dati 
        JPanel inputPanel = new JPanel(new GridLayout(2, 5));
        JTextField servizioField = new JTextField();
        JTextField venditeField = new JTextField();
        JTextField incassiField = new JTextField();
        JTextField speseField = new JTextField();
        JButton aggiungiButton = new JButton("Aggiungi");

        inputPanel.add(new JLabel("Servizio"));
        inputPanel.add(new JLabel("Vendite"));
        inputPanel.add(new JLabel("Incassi (€)"));
        inputPanel.add(new JLabel("Spese (€)"));
        inputPanel.add(new JLabel(""));

        inputPanel.add(servizioField);
        inputPanel.add(venditeField);
        inputPanel.add(incassiField);
        inputPanel.add(speseField);
        inputPanel.add(aggiungiButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // bottone per calcolare il totale
        JButton calcolaTotaleButton = new JButton("Calcola Totali");
        JLabel risultatoLabel = new JLabel("Totali: ");
        
        // Bottone per esportare in PDF
        JButton esportaPDFButton = new JButton("Esporta in PDF");

        // Pannello inferiore
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(calcolaTotaleButton);
        bottomPanel.add(risultatoLabel);
        bottomPanel.add(esportaPDFButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Listener per aggiungere nuovi dati (spiegato da GBT)
        aggiungiButton.addActionListener(e -> {
            String servizio = servizioField.getText();
            int vendite;
            double incassi, spese;

            try {
                vendite = Integer.parseInt(venditeField.getText());
                incassi = Double.parseDouble(incassiField.getText());
                spese = Double.parseDouble(speseField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Inserisci valori numerici validi per Vendite, Incassi e Spese.");
                return;
            }

            // nuovi dati allla tabella
            model.addRow(new Object[]{servizio, vendite, incassi, spese});

            // reset campi
            servizioField.setText("");
            venditeField.setText("");
            incassiField.setText("");
            speseField.setText("");
        });

        // Listener per calcolare il totale
        calcolaTotaleButton.addActionListener(e -> {
            int totaleVendite = 0;
            double totaleIncassi = 0;
            double totaleSpese = 0;

            for (int i = 0; i < model.getRowCount(); i++) {
                totaleVendite += (int) model.getValueAt(i, 1);
                totaleIncassi += (double) model.getValueAt(i, 2);
                totaleSpese += (double) model.getValueAt(i, 3);
            }

            risultatoLabel.setText(String.format("Totali - Vendite: %d, Incassi: €%.2f, Spese: €%.2f",
                                                 totaleVendite, totaleIncassi, totaleSpese));
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
        //contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(50, 750);

        // titoli delle colonne
        String header = String.format("%-20s %-10s %-15s %-10s", "Servizio", "Vendite", "Incassi (€)", "Spese (€)");
        contentStream.showText(header);
        contentStream.newLine();
        contentStream.endText();

        //dati della tabella
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 730);

        for (int i = 0; i < model.getRowCount(); i++) {
            String servizio = (String) model.getValueAt(i, 0);
            int vendite = (int) model.getValueAt(i, 1);
            double incassi = (double) model.getValueAt(i, 2);
            double spese = (double) model.getValueAt(i, 3);

            String row = String.format("%-20s %-10d %-15.2f %-10.2f", servizio, vendite, incassi, spese);
            contentStream.showText(row);
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.close();

        // Salva il PDF nel file specificato
        document.save(file);
        document.close();
    }
    
    private static void populateTable() {
    	// Dati di esempio (Nome servizio, Vendite, Incassi in €, Spese in €) da modificare col gruppo
        List<Treatments> data=Treatments.getAllData();
        for(Treatments t:data) {
        	if(t.isEnabled()) {
        		model.addRow(new String[] {t.getType(),"","",""});
        		//{"Servizio", "Vendite", "Incassi (€)", "Spese (€)"};
        	}
        }
    }
}

//classe dao vendite e incassi
//data inizio e fine