package com.bitcamp.centro.estetico.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class JSplitPanel extends JPanel {
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();

    public JSplitPanel() {
        super();
        setLayout(new GridLayout(1, 2));

        leftPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        rightPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        super.add(leftPanel);
        super.add(rightPanel);
    }

    public Component add(JComponent component) {
        Component[] leftComponents = leftPanel.getComponents();
        Component[] rightComponents = rightPanel.getComponents();

        if (leftComponents.length <= rightComponents.length) {
            leftPanel.add(component);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        } else {
            rightPanel.add(component);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return component;
    }

    @Override
    public Component[] getComponents() {
        Component[] leftComponents = leftPanel.getComponents();
        Component[] rightComponents = rightPanel.getComponents();

        List<Component> components = new ArrayList<>();
        for (int i = 0; i < leftComponents.length; i++) {
            components.add(leftComponents[i]);
        }
        for (int i = 0; i < rightComponents.length; i++) {
            components.add(rightComponents[i]);
        }

        return components.toArray(new Component[components.size()]);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(JPanel leftPanel) {
        this.leftPanel = leftPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(JPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if(leftPanel != null && rightPanel != null) {
            leftPanel.setBackground(UIManager.getColor("background"));
            rightPanel.setBackground(UIManager.getColor("background"));
        }
    }

    /*
     * public void addComponents(Component... components) {
     * int quantity = components.length;
     * 
     * int remainder = Math.ceilMod(quantity, _PANELS_QUANTITY);
     * int quotient = Math.ceilDiv(quantity, _PANELS_QUANTITY);
     * 
     * List<Component> comps = Arrays.asList(components);
     * List<List<Component>> distributedComps = new ArrayList<>();
     * for(int i = 0; i < quantity; i += quotient) {
     * distributedComps.add(comps.subList(i, quotient + i));
     * }
     * }
     */
}
