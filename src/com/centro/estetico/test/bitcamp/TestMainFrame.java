package com.centro.estetico.test.bitcamp;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;

import DAO.BeautyCenterDAO;
import DAO.EmployeeDAO;
import template.EmployeePanel;
import template.MainFrame;
import template.ProductPanel;
import template.SetupFirstAccountFrame;
import template.TreatmentPanel;
import template.UserAccessPanel;
import template.gestioneClienti;
import template.gestioneTurni;

public class TestMainFrame extends JFrame {
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
	            new MainFrame(EmployeeDAO.getEmployee(1).get(), BeautyCenterDAO.getBeautyCenter(1).get());
	        }
	    });
	}
}
