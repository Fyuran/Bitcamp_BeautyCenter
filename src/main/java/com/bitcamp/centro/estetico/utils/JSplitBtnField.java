package com.bitcamp.centro.estetico.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JSplitBtnField extends JSplitLabel{
    private JButton button;
    private JPanel panel;
    private JTextField field;

    public JSplitBtnField() {
		this("Label text", "Button text");
	}
	public JSplitBtnField(String text, String btnText) {
		this(text, new JPanel());
        button.setText(btnText);
	}
	private JSplitBtnField(String text, JPanel panel) {
		super(text, panel);
        
        JButton button = new JButton();
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        JTextField field = new JTextField(20);
        field.setEditable(false);
        field.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        button.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        panel.add(button);
        panel.add(field);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        this.panel = panel;
        this.button = button;
        this.field = field;
	}

    public void addActionListener(ActionListener l) {
        button.addActionListener(l);
    }

    public void setFieldText(String text) {
        field.setText(text);
    }

    public void setColumns(int columns) {
        field.setColumns(columns);
    }
    
    public JPanel getPanel() {
        return panel;
    }
    public JTextField getField() {
        return field;
    }
    public JButton getButton() {
        return button;
    }
}
