package com.bitcamp.centro.estetico.test;

import javax.swing.SwingUtilities;

import com.bitcamp.centro.estetico.gui.SetupBeautyCenterFrame;
import com.bitcamp.centro.estetico.models.Main;

public class SetupBeautyCenterFrameTest {
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            new SetupBeautyCenterFrame();
	        }
	    });
	}
}
