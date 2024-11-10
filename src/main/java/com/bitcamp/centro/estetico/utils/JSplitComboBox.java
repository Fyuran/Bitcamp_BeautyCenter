package com.bitcamp.centro.estetico.utils;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class JSplitComboBox<T> extends JSplitLabel {
    private JComboBox<T> comboBox;

    public JSplitComboBox() {
        this("Label text");
    }

    public JSplitComboBox(String text) {
        this(text, new JComboBox<T>());
    }

    public JSplitComboBox(String text, JComboBox<T> comboBox) {
        super(new JLabel(text), comboBox);
        this.comboBox = comboBox;

    }

    public JComboBox<T> getComboBox() {
        return comboBox;
    }

    public void addItem(T item) {
        comboBox.addItem(item);
    }

    public void removeAllItems() {
        comboBox.removeAllItems();
    }

    public T getSelectedItem() {
        return (T) comboBox.getSelectedItem();
    }

    public void setSelectedItem(T anObject) {
        comboBox.setSelectedItem(anObject);
    }

    public void setSelectedIndex(int anIndex) {
        comboBox.setSelectedIndex(anIndex);
    }

    public void addActionListener(ActionListener l) {
        comboBox.addActionListener(l);
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if(comboBox == null) {
            this.comboBox = new JComboBox<>();
        }
        comboBox.setBackground(UIManager.getColor("ComboBox.background"));
        comboBox.setForeground(UIManager.getColor("ComboBox.foreground"));
    }
}
