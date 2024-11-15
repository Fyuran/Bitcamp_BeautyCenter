package com.bitcamp.centro.estetico.utils;

import javax.swing.JLabel;
import javax.swing.JTextField;

//JSplitPane.VERTICAL_SPLIT = 0
//JSplitPane.HORIZONTAL_SPLIT = 1
public class JSplitTxf extends JSplitLabel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public JSplitTxf() {
		this("Label text");
	}

	public JSplitTxf(String text) {
		this(text, new JTextField());
	}

	public JSplitTxf(String text, JTextField textField) {
		super(new JLabel(text), textField);
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
		textField.setFont(font);
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

	public boolean isEditable() {
		return textField.isEditable();
	}

	public void setIsEditable(boolean isEditable) {
		textField.setEditable(isEditable);
	}
}
