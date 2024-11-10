package com.bitcamp.centro.estetico.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
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
        super(new JLabel(text), panel);

        for (String radial : radials) {
            JRadioButton btn = new JRadioButton(radial);
            btn.setFont(font);
            buttons.add(btn);

            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(btn);
            btnGrp.add(btn);
        }
    }

    public int getSelectedIndex() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isSelected())
                return i;
        }
        return -1;
    }

    public void setSelected(int i) {
        buttons.get(i).setSelected(true);
    }

    public JRadioButton getSelectedBtn() {
        for (JRadioButton btn : buttons) {
            if (btn.isSelected())
                return btn;
        }
        return null;
    }
}
