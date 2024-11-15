package com.bitcamp.centro.estetico.utils;

import javax.swing.JComponent;
import javax.swing.JLabel;

public abstract class JSplitLabel extends JSplitComps {
	private JLabel label;
	
	public JSplitLabel(JLabel label, JComponent component) {
		super(label, component);
		this.label = label;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabelText(String text) {
		label.setText(text);
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

}
