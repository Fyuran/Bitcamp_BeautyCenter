package com.bitcamp.centro.estetico.utils;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JSplitBtn extends JSplitLabel {
    private JButton button;

    public JSplitBtn() {
		this("Label text", "Button text");
	}
	public JSplitBtn(String text, String btnText) {
		this(text, new JButton(btnText));
	}
	private JSplitBtn(String text, JButton button) {
		super(text, button);
        this.button = button;       
	}

    public void addActionListener(ActionListener l) {
        button.addActionListener(l);
    }

    public JButton getButton() {
        return button;
    }
}
