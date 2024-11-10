package com.bitcamp.centro.estetico.gui.render;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.bitcamp.centro.estetico.models.Customer;

public class CustomListCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = -1600461002451054914L;

    public CustomListCellRenderer() {
        super();
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        if(value instanceof Customer c) {
            setText(c.getFullName() + " " + c.getEu_tin());
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}