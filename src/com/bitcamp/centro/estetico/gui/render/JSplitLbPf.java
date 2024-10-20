package com.bitcamp.centro.estetico.gui.render;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
				this.getJPasswordField().setEchoChar((char) 0);
			} else {
				this.getJPasswordField().setEchoChar('•');
			}
		});

        fieldPanel.add(passwordField);
        fieldPanel.add(visible);

		passwordField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}
			
			private void update(DocumentEvent e) {
				values.put(label.getText(), passwordField.getPassword());
			}
		});
	}

    public JPasswordField getJPasswordField() {
		if(! (passwordField instanceof JPasswordField)) {
			throw new IllegalArgumentException("right component not instance of JPasswordField");
		}
		return passwordField;
	}

    public char[] getPassword() {
        return getJPasswordField().getPassword();
    }
    public void setText(String text) {
        getJPasswordField().setText(text);
    }
}
