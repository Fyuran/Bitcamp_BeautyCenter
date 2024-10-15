package com.bitcamp.centro.estetico.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.models.*;

public class EmployeeTest {

	public static void main(String[] args) {
		new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");
		 // Supponiamo di avere un EmployeeDAO con il metodo insertEmployee.


        // Generare 10 Employee
        for (int i = 0; i < 10; i++) {
            // Dettagli casuali per ogni Employee
            String name = "EmployeeName" + i;
            String surname = "EmployeeSurname" + i;
            boolean isFemale = (i % 2 == 0);
            LocalDate bod = LocalDate.of(1990, (i % 12) + 1, (i % 28) + 1);
            String birthplace = "Roma";
            String notes = "Note" + i;

            UserDetails details = new UserDetails(name, surname, isFemale, bod, birthplace, notes);

            // Creazione delle credenziali dell'utente
            String username = "employee" + i;
            String password = "password" + i;
            String address = "Address" + i;
            String iban = "IBAN" + i;
            String phone = "123456789" + i;
            String mail = "email" + i + "@example.com";

            UserCredentials userCredentials = new UserCredentials(username, password.toCharArray(), address, iban, phone, mail);

            // Generare un numero seriale univoco
            long serial = Employee.generateSerial();

            // Impostazione di un ruolo casuale per ogni dipendente
            Roles role = (i % 2 == 0) ? Roles.ADMIN : Roles.PERSONNEL;

            // Aggiungere turni (in questo esempio una lista vuota)
            List<Shift> shifts = new ArrayList<>();

            // Impostare le date di assunzione e di termine (termine può essere null)
            LocalDate hiredDate = LocalDate.of(1950, 11, 11);
            LocalDate terminationDate = LocalDate.of(2050, 10, 9);// Consideriamo che non sia ancora terminato

            // Creare l'oggetto Employee
            Employee employee = new Employee(details, userCredentials, serial, role, shifts, hiredDate, terminationDate);

            // Inserire l'Employee nel database
            try {
                EmployeeDAO.insertEmployee(employee);
                System.out.println("Employee " + i + " inserito con successo.");
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }
	}

}



