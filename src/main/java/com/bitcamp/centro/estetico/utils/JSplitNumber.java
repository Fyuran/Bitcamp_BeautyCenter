package com.bitcamp.centro.estetico.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JSplitNumber extends JPanel {
    private NumberFormat numberFormatter;
    private JLabel label;
    private JFormattedTextField jFormattedTextField;

    public JSplitNumber(String text) {
        this(text, Locale.getDefault());
    }

    public JSplitNumber(String text, Locale locale) {
        super();
        
        label = new JLabel(text);
        numberFormatter = NumberFormat.getInstance(locale);
        jFormattedTextField = new JFormattedTextField(numberFormatter);

        setLayout(new RelativeLayout());
        add(label, 0.4f);
        add(jFormattedTextField, 1f);
    }

    public NumberFormat getNumberFormatter() {
        return numberFormatter;
    }

    public void setNumberFormatter(NumberFormat numberFormatter) {
        this.numberFormatter = numberFormatter;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JFormattedTextField getjFormattedTextField() {
        return jFormattedTextField;
    }

    public void setjFormattedTextField(JFormattedTextField jFormattedTextField) {
        this.jFormattedTextField = jFormattedTextField;
    }

    public String getText() throws NumberFormatException {
        return jFormattedTextField.getText();
    }

    public void setText(String text) throws ParseException {
        jFormattedTextField.setText(numberFormatter.parse(text).toString());
    }

    public void setText(Number number) {
        jFormattedTextField.setText(number.toString());
    }
}
