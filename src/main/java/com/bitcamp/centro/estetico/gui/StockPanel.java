package com.bitcamp.centro.estetico.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.controller.DAO;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Stock;
import com.bitcamp.centro.estetico.utils.InputValidator;
import com.bitcamp.centro.estetico.utils.InputValidator.InputValidatorException;
import com.bitcamp.centro.estetico.utils.InputValidator.InvalidInputException;
import com.bitcamp.centro.estetico.utils.JSplitBtn;
import com.bitcamp.centro.estetico.utils.JSplitNumber;
import com.bitcamp.centro.estetico.utils.ModelChooser;

public class StockPanel extends AbstractBasePanel<Stock> {

    private static Stock selectedData = new Stock();
    private static List<Product> returnProduct = new ArrayList<>();

    private static JSplitBtn productsBtn;
    private static JSplitNumber txfMinStock;
    private static JSplitNumber txfCurrentStock;

    public StockPanel(JFrame parent) {
        super(parent);
        setName("Inventario");
        setTitle("GESTIONE INVENTARIO");
        setSize(1300, 768);

        txfMinStock = new JSplitNumber("Minimo");
        txfMinStock.setText(1);
        txfCurrentStock = new JSplitNumber("Corrente");
        txfCurrentStock.setText(0);

        productsBtn = new JSplitBtn("Prodotti", "Scelta Prodotti");
        productsBtn.addActionListener(l1 -> {
            ModelChooser<Product> picker = new ModelChooser<>(parent, "Scelta Prodotti",
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, returnProduct);
            products.clear();
            products.addAll(DAO.getAll(Product.class));
            var available = products
                    .parallelStream()
                    .filter(p -> p.isEnabled())
                    .toList();

            if (!available.isEmpty()) {
                picker.addRows(available);
            } else
                picker.getLbOutput().setText("Lista vuota");

            picker.setVisible(true);
        });

        actionsPanel.add(txfMinStock);
        actionsPanel.add(productsBtn);
        actionsPanel.add(txfCurrentStock);
    }

    @Override
    public void insertElement() {
        try { // all fields must be filled
            isDataValid();
        } catch (InputValidatorException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage());
            return;
        }

        int min = Integer.parseInt(txfMinStock.getText());
        int current = Integer.parseInt(txfCurrentStock.getText());

        Stock stock = new Stock(returnProduct.getFirst(), current, min);

        DAO.insert(stock);

        lbOutput.setText("Inventario creato");
        refresh();
    }

    @Override
    public void updateElement() {
        try { // all fields must be filled
            isDataValid();
        } catch (InputValidatorException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage());
            return;
        }

        int min = Integer.parseInt(txfMinStock.getText());
        int current = Integer.parseInt(txfCurrentStock.getText());

        selectedData.setMinimumStock(min);
        selectedData.setCurrentStock(current);
        selectedData.setProduct(returnProduct.getFirst());

        DAO.update(selectedData);

        lbOutput.setText("Inventario aggiornato");
        refresh();
    }

    @Override
    public void deleteElement() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Nessun inventario selezionato");
            return; // do not allow invalid ids to be passed to update
        }
        if (selectedData == null)
            return;

        DAO.delete(selectedData);
        lbOutput.setText("Inventario cancellato");
        refresh();
    }

    @Override
    public void disableElement() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Nessun inventario selezionato");
            return; // do not allow invalid ids to be passed to update
        }
        if (selectedData == null)
            return;

        DAO.toggle(selectedData);
        lbOutput.setText(selectedData.isEnabled() ? "Inventario abilitato" : "Inventario disabilitato");
        refresh();
    }

    @Override
    public void populateTable() {
        stocks.clear();
        stocks.addAll(DAO.getAll(Stock.class));
        if (!stocks.isEmpty()) {
            model.addRows(stocks);
        } else {
            lbOutput.setText("Lista Inventario vuota");
        }
    }

    @Override
    public void clearTxfFields() {
        txfMinStock.setText(1);
        txfCurrentStock.setText(0);
        returnProduct.clear();
    }

    @Override
    public ListSelectionListener getTableListSelectionListener() {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (event.getValueIsAdjusting())
                    return;
                int selectedRow = table.getSelectedRow();
                if (selectedRow < 0)
                    return;

                selectedData = model.getObjAt(selectedRow);
                if (selectedData == null || !selectedData.isEnabled())
                    return;

                txfCurrentStock.setText(selectedData.getCurrentStock());
                txfMinStock.setText(selectedData.getMinimumStock());

                returnProduct.clear();
            }
        };
    }

    @Override
    public boolean isDataValid() {
        try {
            InputValidator.validateBtn(productsBtn, returnProduct);
            InputValidator.validateNumber(txfMinStock, 1, Integer.MAX_VALUE);
            InputValidator.validateNumber(txfCurrentStock, 0, Integer.MAX_VALUE);

            if (Integer.parseInt(txfMinStock.getText()) < Integer.parseInt(txfCurrentStock.getText())) {
                throw new InvalidInputException("Minimo non valido", txfMinStock);
            }
        } catch (InputValidatorException e) {
            throw e;
        }

        return true;
    }

}
