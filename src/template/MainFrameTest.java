package template;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Employee;

public class MainFrameTest {
private MainFrame mainFrame;
private BeautyCenter bc;
private Employee employee;
private Employee admin;
public MainFrameTest() {
	employee = new Employee(1, "Mario", "Rossi", "Milano", false, LocalDate.of(1990, 5, 15), "Note sul dipendente", true, 123456789L, new ArrayList<>(), LocalDate.of(2020, 1, 1), EnumSet.of(Employee.Roles.SECRETARY), null, "IT60X0542811101000000123456", "Via Roma 1", "1234567890", "mario.rossi@example.com", "mario.rossi", "password123!");
	admin= new Employee(1, "Marta", "Paolucci", "Roma", true, LocalDate.of(1985, 6, 15), "Note", true, 123456789L, new ArrayList<>(), LocalDate.of(2023, 1, 1), EnumSet.of(Employee.Roles.ADMIN), null, "IT60X0542811101000000123456", "Via Roma 123", "3331234567", "mario.rossi@mail.com", "adminuser", "AdminPass123!");

	bc = new BeautyCenter("Centro Estetico Bitcamp", "123456789", "info@bitcamp.com", "bitcamp@gmail.com", "Via Roma 1", "Via Milano 2", "REA123456", "PIVA987654321", LocalTime.of(9, 0), LocalTime.of(18, 0));
	
	this.mainFrame=new MainFrame(admin,bc);
}
	public static void main(String[] args) {
		new MainFrameTest();

	}

}
