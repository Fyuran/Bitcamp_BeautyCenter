package com.bitcamp.centro.estetico.gui;


import javax.swing.SwingUtilities;

public class Main {
	public static boolean _DEBUG_MODE_FULL = true;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SetupWelcomeFrame();
			}
		});

	}
}