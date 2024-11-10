package com.bitcamp.centro.estetico.gui;

import javax.swing.SwingUtilities;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.Theme;

public class Main {
	public static boolean _DEBUG_MODE_FULL = true;
	public static Theme theme;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				System.setProperty("darklaf.allowNativeCode", "true");
				System.setProperty("darklaf.animations", "true");
				LafManager.install(LafManager.themeForPreferredStyle(LafManager.getPreferredThemeStyle()));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			theme = LafManager.getTheme();
			new SetupWelcomeFrame();
		});

	}
}