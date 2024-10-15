package com.bitcamp.centro.estetico.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.gui.MainFrame;
import com.bitcamp.centro.estetico.models.Main;

public class TestMainFrame extends JFrame {
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            new MainFrame(EmployeeDAO.getEmployee(1).get(), BeautyCenterDAO.getFirstBeautyCenter().get());
	        }
	    });
	}
}
