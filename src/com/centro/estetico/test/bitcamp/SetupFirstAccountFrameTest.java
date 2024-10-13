package com.centro.estetico.test.bitcamp;

import javax.swing.SwingUtilities;

import com.centro.estetico.bitcamp.Main;

import template.SetupFirstAccountFrame;

public class SetupFirstAccountFrameTest {
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            new SetupFirstAccountFrame();
	        }
	    });
	}
}
