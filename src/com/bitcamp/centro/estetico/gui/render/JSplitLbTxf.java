package com.bitcamp.centro.estetico.gui.render;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//JSplitPane.VERTICAL_SPLIT = 0
//JSplitPane.HORIZONTAL_SPLIT = 1
public class JSplitLbTxf extends JSplitLabel {
	private static final long serialVersionUID = 1L;

	public JSplitLbTxf() {
		this("Label text");
	}
	public JSplitLbTxf(String text) {
		this(text, new JTextField());
	}
	public JSplitLbTxf(String text, JTextField field) {
		super(text, field);
		setDefaultSize();

		field.getDocument().addDocumentListener(new DocumentListener() {

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
				values.put(label.getText(), field.getText());
			}
		});
	}

	public JTextField getJTextField() {
		if(! (rightComponent instanceof JTextField)) {
			throw new IllegalArgumentException("right component not instance of JTextField");
		}
		return (JTextField) rightComponent;
	}

	public void setColumns(int columns) {
		JTextField txf = getJTextField();
		txf.setColumns(columns);
	}

	private void setDefaultSize() {
		JTextField txf = getJTextField();
		//txf.setColumns(20);
		txf.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
	}

	public String getText() {
		if(! (rightComponent instanceof JTextField)) {
			throw new IllegalArgumentException("right component not instance of JTextField");
		}
		return  ((JTextField) rightComponent).getText();
	}

	public void setText(String text) {
		getJTextField().setText(text);
	}
    public void setText(Number number) {
        setText(String.valueOf(number));
    }
}
