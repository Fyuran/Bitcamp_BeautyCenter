package com.bitcamp.centro.estetico.utils;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class JSplitLbPf extends JSplitLabel {
    private JCheckBox visible = new JCheckBox("Mostra");
    private JPanel fieldPanel;
    private JPasswordField passwordField;

	public JSplitLbPf() {
		this("Label text", new JPanel());
	}
    public JSplitLbPf(String text) {
		this(text, new JPanel());
	}

	private JSplitLbPf(String text, JPanel fieldPanel) {
        super(text, fieldPanel);
        this.fieldPanel = fieldPanel;

        passwordField = new JPasswordField();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));

		visible.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 11));
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
