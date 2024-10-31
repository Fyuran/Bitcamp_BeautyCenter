package com.bitcamp.centro.estetico.utils;

import java.awt.Font;

import javax.swing.JTextField;

//JSplitPane.VERTICAL_SPLIT = 0
//JSplitPane.HORIZONTAL_SPLIT = 1
public class JSplitLbTxf extends JSplitLabel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public JSplitLbTxf() {
		this("Label text");
	}
	public JSplitLbTxf(String text) {
		this(text, new JTextField());
	}
	public JSplitLbTxf(String text, JTextField textField) {
		super(text, textField);
		this.textField = textField;

		setDefaultSize();
	}

	public JTextField getJTextField() {
		return textField;
	}

	public void setColumns(int columns) {
		textField.setColumns(columns);
	}

	private void setDefaultSize() {
		textField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
	}

	public String getText() {
		return textField.getText();
	}

	public void setText(String text) {
		textField.setText(text);
	}
    public void setText(Number number) {
        textField.setText(String.valueOf(number));
    }
}
