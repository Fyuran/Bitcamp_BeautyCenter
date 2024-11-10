package com.bitcamp.centro.estetico.utils;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class JSplitPf extends JSplitLabel {
    private JCheckBox visible = new JCheckBox("Mostra");
    private JPanel fieldPanel;
    private JPasswordField passwordField;

    public JSplitPf() {
        this("Label text", new JPanel());
    }

    public JSplitPf(String text) {
        this(text, new JPanel());
    }

    private JSplitPf(String text, JPanel fieldPanel) {
        super(new JLabel(text), fieldPanel);
        this.fieldPanel = fieldPanel;

        passwordField = new JPasswordField();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));

        visible.setFont(font);
        visible.addActionListener(e -> {
            if (visible.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
        fieldPanel.add(passwordField);
        fieldPanel.add(visible);
    }

    public JPasswordField getJPasswordField() {
        return passwordField;
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void setText(String text) {
        passwordField.setText(text);
    }
}
