package com.bitcamp.centro.estetico.utils;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JOptionPaneGoToTab {

    public JOptionPaneGoToTab(JTabbedPane pane, String msg, String title, JPanel tabbedPane) {
        final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit()
        .getDesktopProperty("win.sound.exclamation");
        if (runnable != null)
            runnable.run();

        msg += "Andare al pannello " + tabbedPane.getName() + "?";
        int choice = JOptionPane.showOptionDialog(pane, msg, title, 0, JOptionPane.INFORMATION_MESSAGE, null, null,
                null);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                pane.setSelectedComponent(tabbedPane);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(pane, "Finestra non trovata", "Errore Generico",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (choice == JOptionPane.NO_OPTION) {
            // do nothing and close dialog
        }
    }
}
