package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class JSplitRadialButtons extends JSplitLabel {
    private List<JRadioButton> buttons = new ArrayList<>();
    ButtonGroup btnGrp = new ButtonGroup();

    public JSplitRadialButtons() {
		this("Label", "1", "2");
	}
	public JSplitRadialButtons(String text, String... radials) {
		this(text, new JPanel(), radials);
	}
	public JSplitRadialButtons(String text, JPanel panel, String... radials) {
		super(text, panel);

        for(String radial : radials) {
            JRadioButton btn = new JRadioButton(radial);
            btn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
            buttons.add(btn);

            panel.add(btn);
            btnGrp.add(btn);
        }
	}

    public int getSelectedIndex() {
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).isSelected()) return i;
        }
        return -1;
    }

    public void setSelected(int i) {
        buttons.get(i).setSelected(true);
    }

    public JRadioButton getSelectedBtn() {
        for(JRadioButton btn : buttons) {
            if(btn.isSelected()) return btn;
        }
        return null;
    }
}
