package template;

import javax.swing.*;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;

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
	
	public TestFrame() {
		//Employee employee = new Employee(1, "Mario", "Rossi", "Milano", true, LocalDate.of(1990, 5, 15), "Note sul dipendente", true, 123456789L, new ArrayList<>(), LocalDate.of(2020, 1, 1), EnumSet.of(Employee.Roles.SECRETARY), null, "IT60X0542811101000000123456", "Via Roma 1", "1234567890", "mario.rossi@example.com", "mario.rossi", "password123!");
		
		Main main = new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");
		//panelU=new UserAccessPanel(null);
		panelE=new EmployeePanel();
		panelT=new TreatmentPanel();
		panelP=new ProductPanel();
		panelG=new gestioneTurni();
		panelV=new VATPanel();
		
		setSize(1075, 768);
		JTabbedPane mainPane=new JTabbedPane();
		mainPane.add(panelP);
		//mainPane.add(panelU);
		mainPane.add(panelT);
		mainPane.add(panelE);
		mainPane.add(panelG);
		mainPane.add(panelV);
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
