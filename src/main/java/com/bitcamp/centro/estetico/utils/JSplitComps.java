package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class JSplitComps extends JPanel {
    private JComponent[] comps = new JComponent[0];
    protected final static Font font;

	static {
		font = new Font("Microsoft Sans Serif", Font.PLAIN, 14);
	}

    public JSplitComps(Collection<JComponent> comps) {
        this(comps.toArray(new JComponent[comps.size()]));
    }

    public JSplitComps(JComponent... comps) {
        super();
        this.comps = comps;
        setLayout(new RelativeLayout());

        for(int i = 0; i < comps.length; i++) {
            if(comps[i] instanceof JLabel) {
                add(comps[i], 0.4f);
            }else if (comps[i] instanceof JCheckBox) {
                add(comps[i], 0.1f);
            } else {
                add(comps[i], 1f);
            }
        }
    }

    public JComponent[] getComps() {
        return comps;
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if(comps == null) {
            this.comps = new JComponent[0];
        }
        for(JComponent comp : comps ) {
            comp.setBackground(UIManager.getColor("TextField.background"));
            comp.setForeground(UIManager.getColor("TextField.foreground"));
        }
    }
}
