package com.centro.estetico.test.bitcamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Roles;
import com.centro.estetico.bitcamp.UserCredentials;
import com.centro.estetico.bitcamp.UserDetails;

import template.MainFrame;

public class MainFrameTest {
	private MainFrame mainFrame;
	private BeautyCenter bc;
	private Employee employee;
	private Employee admin;

	public MainFrameTest() {
		employee = new Employee(

				new UserDetails("Mario", "Rossi", false, LocalDate.of(1990, 5, 15), "Milano",
				"Note sul dipendente"),
				new UserCredentials("mario.rossi", "password123!", "Via Roma 1", "IT60X0542811101000000123456",
				"1234567890", "mario.rossi@example.com"),
				121321451251L, Roles.PERSONNEL, new ArrayList<>(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)
				);
		admin = new Employee(

				new UserDetails("Marta", "Paolucci", true, LocalDate.of(1985, 5, 15), "Roma",
				"Note"),
				new UserCredentials("adminuser", "AdminPass123!", "Via Roma 123", "IT60X0542811101000000123456",
				"3331234567", "marta.paolucci@mail.com"),
				123456789L, Roles.ADMIN, new ArrayList<>(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)
				);

		bc = new BeautyCenter("Centro Estetico Bitcamp", "123456789", "info@bitcamp.com", "bitcamp@gmail.com",
				"Via Roma 1", "Via Milano 2", "REA123456", "PIVA987654321", LocalTime.of(9, 0), LocalTime.of(18, 0));

		this.mainFrame = new MainFrame(admin, bc);
	}

	public static void main(String[] args) {
		new MainFrameTest();

	}

}
