package com.centro.estetico.test.bitcamp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.centro.estetico.bitcamp.Main;

import template.TransactionPanel;

public class TransactionPanelTest {

	public static void main(String[] args) {
		
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            JFrame frame = new JFrame();
	            TransactionPanel panel = new TransactionPanel(frame);
	            frame.setSize(1040, 900);
	            frame.getContentPane().add(panel);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	        }
	    });
	}

}
