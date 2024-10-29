package com.bitcamp.centro.estetico.gui.render;

import java.awt.Color;
import java.awt.Component;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Subscription;

import it.kamaladafrica.codicefiscale.CodiceFiscale;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private TableModel model;
    private static final long serialVersionUID = -5727490185915218173L;

    public CustomTableCellRenderer(TableModel model) {
        this.model = model;
    }

    private CustomTableCellRenderer() {}
    
    @Override
    protected void setValue(Object value) {

        if(value instanceof LocalDateTime dateTime) {
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
            setText(dateTime.format(dtf));
        }
        else if(value instanceof LocalDate date) {
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            setText(date.format(dtf));
        }
        else if(value instanceof Optional opt) {
            setValue(opt.orElse(null)); //callback to setValue
        }
        else if(value instanceof Gender g) {
            setText(g.getGender());
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
        else if(value instanceof CodiceFiscale cf) {
            setText(cf.getValue());
        }
        else {
            String text = value == null ? "" : String.valueOf(value); //renderer requires text as every cell is a JLabel

            if (text.equalsIgnoreCase("true")) {
                setText("Si");
            } 
            else if (text.equalsIgnoreCase("false")) {
                setText("No");
            } else {
                setText(text); 
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        if(row == -1 || column == -1) return this;
        Optional<Object> modelValue = Optional.ofNullable(model.getValueAt(row, table.getColumnCount() - 1));
        
        if(modelValue.isPresent()) {
            boolean isEnabled = false;
            try {
                isEnabled = (boolean) modelValue.orElseThrow(() -> new NoSuchElementException("check isEnabled col"));
                if (!isEnabled) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                }
            } catch(Exception e) {
                setBackground(Color.RED);
                setToolTipText(e.getMessage());
                e.printStackTrace();
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