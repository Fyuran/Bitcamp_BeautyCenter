package com.centro.estetico.test.bitcamp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.centro.estetico.bitcamp.Main;

import template.SubscriptionPanel;

public class SubscriptionPanelTest {

	public static void main(String[] args) {

	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            JFrame frame = new JFrame();
	            SubscriptionPanel panel = new SubscriptionPanel();
	            frame.setSize(1040, 900);
	            frame.getContentPane().add(panel);
	            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	        }
	    });
	}

}
