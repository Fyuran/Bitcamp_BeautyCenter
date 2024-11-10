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

import com.bitcamp.centro.estetico.gui.Main;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Subscription;
import com.github.weisj.darklaf.theme.spec.ColorToneRule;

import it.kamaladafrica.codicefiscale.CodiceFiscale;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = -5727490185915218173L;
    protected static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected Color unselectedForeground;
    protected Color unselectedBackground;

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
            String text = value == null ? "" : String.valueOf(value); // renderer requires text as every cell is a String
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
                foreground = table.getSelectionForeground();
                background = table.getSelectionBackground();
            } else {
                foreground = new Color(53, 126, 199);
            }
        } else {
            if(unselectedBackground != null) {
                background = unselectedBackground;
            } else {
                if(isEnabled) {
                    background = table.getBackground();
                } else {
                    if (Main.theme.getColorToneRule() == ColorToneRule.DARK) {
                        background = new Color(140,140,140);
                    } else {
                        background = Color.LIGHT_GRAY;
                    }
                }
            }

            if(unselectedForeground != null) {
                foreground = unselectedForeground;
            } else {
                foreground = table.getForeground();
            }

        }
        
        super.setForeground(foreground);
        super.setBackground(background);
        setFont(table.getFont());

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = UIManager.getColor("Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
            }
        } else {
            setBorder(getNoFocusBorder());
        }

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
}