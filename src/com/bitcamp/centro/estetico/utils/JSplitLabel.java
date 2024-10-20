package com.bitcamp.centro.estetico.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

public abstract class JSplitLabel extends JSplitPane {
	protected JLabel label;

    private JSplitLabel(int newOrientation, JComponent newLeftComponent, JComponent newRightComponent) {
		super(newOrientation, newLeftComponent, newRightComponent);

        label = getLabel();
		//label.setHorizontalAlignment(SwingConstants.CENTER);
		setLabelSize(120, newRightComponent.getMinimumSize().height);
		label.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        setBackground(Color.LIGHT_GRAY);

		newLeftComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		newRightComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
	}

    public JSplitLabel(int newOrientation, String text, JComponent newRightComponent) {
        this(newOrientation, new JLabel(text), newRightComponent);
    }

    public JSplitLabel(String text, JComponent newRightComponent) {
        this(JSplitPane.HORIZONTAL_SPLIT, new JLabel(text), newRightComponent);
    }

    public JLabel getLabel() {
		if(! (leftComponent instanceof JLabel)) {
			throw new IllegalArgumentException("left component not instance of JLabel");
		}
		return (JLabel) leftComponent;
	}
    
    public void setLabelText(String text) {
		if(! (leftComponent instanceof JLabel)) {
			throw new IllegalArgumentException("left component not instance of JLabel");
		}
		((JLabel) leftComponent).setText(text);
	}

    public void setLabelSize(int x, int y) {
		JLabel label = getLabel();
		label.setMinimumSize(new Dimension(x, y));
	}

	@Override
	public void setFont(Font font) {
		leftComponent.setFont(font);
		rightComponent.setFont(font);
	}
}
