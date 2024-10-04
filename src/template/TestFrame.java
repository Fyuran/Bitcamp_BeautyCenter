package template;

import javax.swing.*;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Employee;

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
	
	public TestFrame() {
		Employee employee = new Employee(1, "Mario", "Rossi", "Milano", true, LocalDate.of(1990, 5, 15), "Note sul dipendente", true, 123456789L, new ArrayList<>(), LocalDate.of(2020, 1, 1), EnumSet.of(Employee.Roles.SECRETARY), null, "IT60X0542811101000000123456", "Via Roma 1", "1234567890", "mario.rossi@example.com", "mario.rossi", "password123!");
		//BeautyCenter bc = new BeautyCenter("Centro Estetico Bitcamp", "123456789", "info@bitcamp.com", "bitcamp@gmail.com", "Via Roma 1", "Via Milano 2", "REA123456", "PIVA987654321", LocalTime.of(9, 0), LocalTime.of(18, 0));
		
		panelU=new UserAccessPanel(employee);
		panelT=new TreatmentPanel();
		panelE=new EmployeePanel();
		panelP=new ProductPanel();
		panelG=new gestioneTurni();
		
		setSize(1075, 768);
		JTabbedPane mainPane=new JTabbedPane();
		mainPane.add(panelU);
		mainPane.add(panelT);
		mainPane.add(panelE);
		mainPane.add(panelP);
		mainPane.add(panelG);
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
