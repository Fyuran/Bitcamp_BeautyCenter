package com.bitcamp.centro.estetico.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.bitcamp.centro.estetico.gui.TransactionPanel;
import com.bitcamp.centro.estetico.models.Main;

public class TransactionPanelTest {

	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            JFrame frame = new JFrame();
	            TransactionPanel panel = new TransactionPanel();
	            frame.setSize(1040, 900);
	            frame.getContentPane().add(panel);
	            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	        }
	    });
	}

}
