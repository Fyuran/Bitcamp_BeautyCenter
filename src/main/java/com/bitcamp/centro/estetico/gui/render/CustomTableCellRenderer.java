package com.bitcamp.centro.estetico.gui.render;

import java.awt.Color;
import java.awt.Component;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Subscription;

import it.kamaladafrica.codicefiscale.CodiceFiscale;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = -5727490185915218173L;

    protected static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private static Color disabledBackground = new Color(34,34,34);
    private static Color disabledForeground = new Color(255,255,255);
    private static Color disabledBackgroundSelect = new Color(99,99,99);
    private static Color disabledBackgroundFocus = new Color(101, 35, 255);

    public CustomTableCellRenderer() {
        super();

        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void setValue(Object value) {

        if (value instanceof LocalDateTime dateTime) {
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
            setText(dateTime.format(dtf));
        } else if (value instanceof LocalDate date) {
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            setText(date.format(dtf));
        } else if (value instanceof Optional opt) {
            setValue(opt.orElse(null)); // callback to setValue
        } else if (value instanceof Gender g) {
            setText(g.getGender());
        } else if (value instanceof Customer c) {
            setText(c.getFullName());
        } else if (value instanceof Subscription s) {
            setText(s.getSubperiod().toString());
        } else if (value instanceof Duration d) {
            setText(String.valueOf(d.toMinutes()));
        } else if (value instanceof CodiceFiscale cf) {
            setText(cf.getValue());
        } else if (value instanceof Product p) {
            setText(p.getName());
        } else {
            String text = value == null ? "" : String.valueOf(value); // renderer requires text as every cell is a
                                                                      // String
            setText(text);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (table == null) {
            return this;
        }
        if(value instanceof JButton) {
            return this;
        }
        TableModel model = table.getModel();
        TableColumn isEnabledCol = table.getColumn("Abilitato");
        boolean isEnabled = (boolean) model.getValueAt(row, isEnabledCol.getModelIndex());

        Color background = null;
        Color foreground = null;
        
        if (isSelected) {
            if(isEnabled) {
                background = UIManager.getColor("Table.backgroundSelectedNoFocus");
                foreground = UIManager.getColor("Table.foregroundSelectedNoFocus");
            } else {
                background = disabledBackgroundSelect;
                foreground = disabledForeground;
            }
        } else {
            if(isEnabled) {
                background = UIManager.getColor("Table.background");
                foreground = UIManager.getColor("Table.foreground");
            } else {
                background = disabledBackground;
                foreground = disabledForeground;
            }
        }  
        
        if (hasFocus) {
            Border border = null;
            if (isSelected) {           
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
                setBorder(border);
            } else {
                background = UIManager.getColor("Table.backgroundSelected");
                foreground = UIManager.getColor("Table.foregroundSelected");
            }
        } else {
            setBorder(getNoFocusBorder());
        }

        //check for StockPanel
        try {
            TableColumn currentStockColumn = table.getColumn("Scorta");
            TableColumn minStockColumn = table.getColumn("Richiesto");
            int currentStock = (int) model.getValueAt(row, currentStockColumn.getModelIndex());
            int minStock = (int) model.getValueAt(row, minStockColumn.getModelIndex());
            //orange (255,69,0)
            if (currentStock <= (minStock / 3)) { // if it's lower than a third popup message
                background = new Color(155,69,0);
                foreground = disabledForeground;
            }
        } catch (IllegalArgumentException e) {
            //do nothing
        }

        setForeground(foreground);
        setBackground(background);

        setFont(UIManager.getFont("Table.font"));
        setValue(value);

        return this;
    }

    protected Border getNoFocusBorder() {
        Border border = UIManager.getBorder("Table.cellNoFocusBorder");
        if (border != null) {
            if (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER) {
                return border;
            }
        }
        return noFocusBorder;
    }

    public float lerp(float a, float b, float f) {
        return (a * (1.0f - f)) + (b * f);
    }

    public Number linearConversion(Number oldMin, Number oldMax, Number oldValue, Number newMin, Number newMax) {
        return (((oldValue.floatValue() - oldMin.floatValue()) * (newMax.floatValue() - newMin.floatValue()))
                / (oldMax.floatValue() - oldMin.floatValue())) + newMin.floatValue();
    }
}