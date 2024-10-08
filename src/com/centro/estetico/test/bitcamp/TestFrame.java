package com.centro.estetico.test.bitcamp;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Roles;
import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.UserCredentials;
import com.centro.estetico.bitcamp.UserDetails;

import template.EmployeePanel;
import template.ProductPanel;
import template.TreatmentPanel;
import template.UserAccessPanel;
import template.gestioneTurni;

public class TestFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	TreatmentPanel panelT;
	EmployeePanel panelE;
	ProductPanel panelP;
	UserAccessPanel panelU;
	gestioneTurni panelG;

	/*
	 * Employee( 
	 * UserDetails(String name, String surname, boolean isFemale,
	 * LocalDate BoD, String birthplace, String notes),
	 * 
	 * UserCredentials(String username, String password, String address, String iban, String phone, String mail), 
	 * 
	 * boolean isEnabled, long employeeSerial, Roles role, List<Shift> turns, LocalDate hiredDate,
	 * LocalDate terminationDate)
	 * 
	 */
	public TestFrame() {
		Employee employee = new Employee(
				
				new UserDetails("Mario", "Rossi", false, LocalDate.of(1990, 5, 15), "Milano",
				"Note sul dipendente"),
				new UserCredentials("mario.rossi", "password123!", "Via Roma 1", "IT60X0542811101000000123456",
				"1234567890", "mario.rossi@example.com"),
				121321451251L, Roles.PERSONNEL, new ArrayList<Shift>(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)
				);

		new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");
		panelU = new UserAccessPanel(employee);
		panelT = new TreatmentPanel();
		panelE = new EmployeePanel();
		panelP = new ProductPanel();
		panelG = new gestioneTurni();

		setSize(1075, 768);
		JTabbedPane mainPane = new JTabbedPane();
		mainPane.add(panelP);
		mainPane.add(panelU);
		mainPane.add(panelT);
		mainPane.add(panelE);
		mainPane.add(panelG);
		add(mainPane);

		setName("Test Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new TestFrame();
//		new benvenutoOperatori();
		// new ReportVenditePDF();
	}

}
