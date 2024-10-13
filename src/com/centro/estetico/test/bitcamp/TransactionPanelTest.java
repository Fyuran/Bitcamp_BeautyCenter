package com.centro.estetico.test.bitcamp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.centro.estetico.bitcamp.Main;

import template.TransactionPanel;

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
