package com.bitcamp.centro.estetico.utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import com.bitcamp.centro.estetico.gui.AbstractBasePanel;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Model;

public class SearchBar extends JPanel {
    private final JButton btnSearch;
    private final JLabel filterIcon;
    private final JComboBox<String> filtersComboBox;
    private final JTextField txfSearchBar;
    public final String NONE = "Nessuno";

    public SearchBar(List<String> items) {

        btnSearch = new JButton();
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setIcon(new ImageIcon(
                AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
        btnSearch.setRolloverEnabled(true);
        btnSearch.setRolloverIcon(new ImageIcon(
                AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png"))); // #646464
        add(btnSearch);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        add(panel);

        filterIcon = new JLabel();
        filterIcon.setIcon(new ImageIcon(
                AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
        panel.add(filterIcon, gbc);

        items.addFirst(NONE);
        filtersComboBox = new JComboBox<>(items.toArray(new String[items.size()]));
        gbc.gridx = 1;
        panel.add(filtersComboBox, gbc);

        txfSearchBar = new JTextField();
        txfSearchBar.setMinimumSize(new Dimension(20, 20));
        txfSearchBar.setPreferredSize(new Dimension(100, 20));
        txfSearchBar.setColumns(20);
        gbc.gridx = 2;
        gbc.weightx = 1;
        panel.add(txfSearchBar, gbc);

    }

    public SearchBar() {
        this(new ArrayList<>());
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JComboBox<String> getFiltersComboBox() {
        return filtersComboBox;
    }

    public JTextField getTxfSearchBar() {
        return txfSearchBar;
    }

    public void addComboBoxItem(String item) {
        filtersComboBox.addItem(item);
    }

    public void clearComboBox() {
        filtersComboBox.removeAllItems();
        filtersComboBox.addItem(NONE); // make sure top filter is always this one
    }

    public String getSelectedItem() {
        return (String) filtersComboBox.getSelectedItem();
    }

    public int getSelectedIndex() {
        return filtersComboBox.getSelectedIndex() - 1; // decrease by one as 'NONE' item increases index by one, handle
                                                       // the -1 separately
    }

    public void addSearchBtnActionListener(ActionListener l) {
        btnSearch.addActionListener(l);
    }

    public void addSearchBarKeyListener(KeyListener l) {
        txfSearchBar.addKeyListener(l);
    }

    public String getText() {
        return txfSearchBar.getText();
    }

    public void clearSearchField() {
        txfSearchBar.setText("");
    }

    public <T extends Model> RowFilter<NonEditableTableModel<T>, Integer> getRowFilter() {
        return new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends NonEditableTableModel<T>, ? extends Integer> entry) {
                String regex = txfSearchBar.getText();
                if (regex == null || regex.isBlank() || regex.equalsIgnoreCase(NONE))
                    return true;

                String columnName = getSelectedItem();
                int index = entry.getModel().findColumn(columnName);
                if (index < 0) {
                    return true;
                }

                Pattern pattern = null;
                try {
                    pattern = Pattern.compile(regex.concat(".*"));
                } catch (IllegalArgumentException e) {
                    return false;
                }

                Object rowValue = entry.getValue(index);
                if (rowValue instanceof LocalDateTime dateTime) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
                    return pattern.matcher(dateTime.format(dtf)).matches();
                } else if (rowValue instanceof LocalDate date) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                    return pattern.matcher(date.format(dtf)).matches();
                }

                return pattern.matcher(rowValue.toString()).matches();
            }
        };
    }
}
