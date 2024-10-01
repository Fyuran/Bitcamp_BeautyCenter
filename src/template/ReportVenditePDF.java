import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReportVenditePDF {

    public static void main(String[] args) {
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

        // Nomi delle colonne
        String[] columnNames = {"Prodotto", "Vendite", "Incassi (€)", "Spese (€)"};

        // Modello della tabella
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Creazione della tabella
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        // Pannello per inserimento nuovi dati
        JPanel inputPanel = new JPanel(new GridLayout(2, 5));
        JTextField prodottoField = new JTextField();
        JTextField venditeField = new JTextField();
        JTextField incassiField = new JTextField();
        JTextField speseField = new JTextField();
        JButton aggiungiButton = new JButton("Aggiungi");

        inputPanel.add(new JLabel("Prodotto"));
        inputPanel.add(new JLabel("Vendite"));
        inputPanel.add(new JLabel("Incassi (€)"));
        inputPanel.add(new JLabel("Spese (€)"));
        inputPanel.add(new JLabel(""));
        
        inputPanel.add(prodottoField);
        inputPanel.add(venditeField);
        inputPanel.add(incassiField);
        inputPanel.add(speseField);
        inputPanel.add(aggiungiButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Aggiungiamo un bottone per calcolare il totale
        JButton calcolaTotaleButton = new JButton("Calcola Totali");
        JLabel risultatoLabel = new JLabel("Totali: ");
        
        // Bottone per esportare in PDF
        JButton esportaPDFButton = new JButton("Esporta in PDF");
        
        // Bottone per applicare filtro
        JButton filtraButton = new JButton("Applica Filtro");
        JTextField filtroField = new JTextField(10);
        
        // Pannello inferiore
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(calcolaTotaleButton);
        bottomPanel.add(risultatoLabel);
        bottomPanel.add(new JLabel("Filtro Prodotto:"));
        bottomPanel.add(filtroField);
        bottomPanel.add(filtraButton);
        bottomPanel.add(esportaPDFButton);
        
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Listener per aggiungere nuovi dati
        aggiungiButton.addActionListener(e -> {
            String prodotto = prodottoField.getText();
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

            model.addRow(new Object[]{prodotto, vendite, incassi, spese});

            // Puliamo i campi
            prodottoField.setText("");
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

        // Listener per applicare il filtro
        filtraButton.addActionListener((ActionEvent e) -> {
            String filtro = filtroField.getText();
            filtraDati(filtro, model);
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
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
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
    private static void filtraDati(String filtro, DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String prodotto = (String) model.getValueAt(i, 0);
            if (!prodotto.toLowerCase().contains(filtro.toLowerCase())) {
                model.removeRow(i);
            }
        }
    }
}