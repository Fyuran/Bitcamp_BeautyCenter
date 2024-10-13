package template;

import javax.swing.*;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Roles;

import DAO.EmployeeDAO;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;

public class TestFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	TreatmentPanel panelT;
	EmployeePanel panelE;
	ProductPanel panelP;
	UserAccessPanel panelU;
	gestioneTurni panelG;
	VATPanel panelV;
	ReportPanel panelR;
	
	public TestFrame() {
		Main main = new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "admin");
		Employee employee=EmployeeDAO.getEmployee(1).get();

		//panelU=new UserAccessPanel(null);
		panelE=new EmployeePanel();
		panelT=new TreatmentPanel();
		panelP=new ProductPanel();
		panelG=new gestioneTurni();
		panelV=new VATPanel();
		panelU=new UserAccessPanel(employee);
		panelR=new ReportPanel();
		setSize(1075, 768);
		JTabbedPane mainPane=new JTabbedPane();
		mainPane.add(panelP);
		mainPane.add(panelU);
		mainPane.add(panelT);
		mainPane.add(panelE);
		mainPane.add(panelG);
		mainPane.add(panelV);
		mainPane.add(panelR);
		
		add(mainPane);
		
		setName("Test Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new TestFrame();
//		new benvenutoOperatori();
		//new ReportVenditePDF();
	}

}
