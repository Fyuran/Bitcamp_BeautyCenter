package com.centro.estetico.test.bitcamp;

public class EmployeeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeGenerator {
    public static void main(String[] args) {
        // Supponiamo di avere un EmployeeDAO con il metodo insertEmployee.
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Generare 10 Employee
        for (int i = 0; i < 10; i++) {
            // Dettagli casuali per ogni Employee
            String name = "EmployeeName" + i;
            String surname = "EmployeeSurname" + i;
            boolean isFemale = (i % 2 == 0);
            LocalDate bod = LocalDate.of(1990, (i % 12) + 1, (i % 28) + 1);
            String birthplace = "City" + i;
            String notes = "Note" + i;

            UserDetails details = new UserDetails(name, surname, isFemale, bod, birthplace, notes);

            // Creazione delle credenziali dell'utente
            String username = "employee" + i;
            String password = "password" + i;
            String address = "Address" + i;
            String iban = "IBAN" + i;
            String phone = "123456789" + i;
            String mail = "email" + i + "@example.com";

            UserCredentials userCredentials = new UserCredentials(username, password, address, iban, phone, mail);

            // Generare un numero seriale univoco
            long serial = Employee.generateSerial();

            // Impostazione di un ruolo casuale per ogni dipendente
            Roles role = (i % 2 == 0) ? Roles.ADMIN : Roles.OPERATOR;

            // Aggiungere turni (in questo esempio una lista vuota)
            List<Shift> shifts = new ArrayList<>();

            // Impostare le date di assunzione e di termine (termine può essere null)
            LocalDate hiredDate = LocalDate.now().minusYears(i);
            LocalDate terminationDate = null; // Consideriamo che non sia ancora terminato

            // Creare l'oggetto Employee
            Employee employee = new Employee(details, userCredentials, serial, role, shifts, hiredDate, terminationDate);

            // Inserire l'Employee nel database
            try {
                employeeDAO.insertEmployee(employee);
                System.out.println("Employee " + i + " inserito con successo.");
            } catch (Exception e) {
                System.err.println("Errore durante l'inserimento di Employee " + i + ": " + e.getMessage());
            }
        }
    }
}
