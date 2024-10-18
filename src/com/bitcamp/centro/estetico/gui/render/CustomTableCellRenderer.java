package com.bitcamp.centro.estetico.gui.render;

import java.awt.Color;
import java.awt.Component;
import java.time.Duration;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Subscription;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private TableModel model;
    private int enabledColumnIndex;
    private static final long serialVersionUID = -5727490185915218173L;

    public CustomTableCellRenderer(TableModel model, int enabledColumnIndex) {
        this.model = model;
        this.enabledColumnIndex = enabledColumnIndex;
    }

    public CustomTableCellRenderer(TableModel model) {
        this(model, -1);
    }

    private CustomTableCellRenderer() {}
    
    @Override
    protected void setValue(Object value) {
        String text = value == null ? "" : String.valueOf(value); //renderer requires text as every cell is a JLabel

        if (text.equalsIgnoreCase("true")) {
            setText("Si");
        } 
        else if (text.equalsIgnoreCase("false")) {
            setText("No");
        }
        else if (value instanceof Customer c) {
            setText(c.getFullName());
        }
        else if (value instanceof Subscription s) {
            setText(s.getSubPeriod().toString());
        }
        else if(value instanceof Duration d) {
            setText(String.valueOf(d.toMinutes()));
        }
         else {
            setText(text); 
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        if(row == -1 || column == -1) return this;
        
        if(enabledColumnIndex != -1) {
            boolean isEnabled = (boolean) model.getValueAt(row, enabledColumnIndex);
            if (!isEnabled) {
                setBackground(Color.LIGHT_GRAY);
            } else {
                setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            }
        }
        if (isSelected) {
            setBorder(new LineBorder(new Color(0, 56, 98), 1));
        } else {
            setBorder(new EmptyBorder(1, 1, 1, 1));
        }
        setFont(table.getFont());
        setValue(value);
        return this;
    }

}