package com.centro.estetico.useCases;

//import template.ReservationForm;
import java.util.LinkedHashMap;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Treatment;
import DAO.*;
import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.Reservation;
import java.util.List;
import java.time.*;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JOptionPane;

//import java.util.HashMap;
import java.util.ArrayList;
//import javax.swing.JOptionPane;

public class GetReservationUseCase {
	private final DAOReservation daoReservation; // IDAO?
	private DAOShift daoShift;

	List<Employee> busiedEmployees;

	public GetReservationUseCase(DAOReservation daoReservation, DAOShift daoShift) {
		this.daoReservation = daoReservation;
		this.daoShift = daoShift;
	}
	
	public GetReservationUseCase(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public Map<Integer, Reservation> getReservations()throws Exception{		
		return daoReservation.getAll();
	}
	
	public Reservation getReservation(int id) throws Exception{
		return daoReservation.getReservation(id);
	}
	
	public Map<Integer, Reservation> getSearchedReservations(String text)throws Exception{
		return daoReservation.getSearchedReservation(text);
	}

	//GetShiftUseCase
	public Map<LocalTime, List<Employee>> getEmployees(LocalDate date, List<LocalTime> hours, Treatment treatment) throws Exception{
		// tutti gli estetisti che eseguono quel tipo di trattamento e i loro turni
		List<Employee> employees = daoShift.loadEmployeesWithShifts(treatment.getId());
		// metodo per ottenere una lista di tutti gli orari(9,10,11,12 in base alla
		// durata del trattamento)
		// e gli operatori che sono di turno in ciascun orario
		Map<LocalTime, List<Employee>> shiftsForBeautician = getShiftsForBeautician(employees, hours, date);
		
		return getFreeBeauticiansForEachTime(shiftsForBeautician, date, treatment);
	}
	
	//GetShiftUseCase
	private Map<LocalTime, List<Employee>> getShiftsForBeautician(List<Employee> employees, List<LocalTime> hours, LocalDate date) {
		Map<LocalTime, List<Employee>> operatorShifts = new LinkedHashMap<>();

		for (LocalTime time : hours) {			
			List<Employee> employeesForShift = new ArrayList<>();			
			for (Employee e : employees) {				
				if (isEmployeeOnDuty(time, e, date)) {					
					employeesForShift.add(e);
				}
				operatorShifts.put(time, employeesForShift);
			}
		}
		
		return operatorShifts;
	}
	
	//GetShiftUseCase
	private boolean isEmployeeOnDuty(LocalTime time, Employee employee, LocalDate date) {
		//JOptionPane.showMessageDialog(null, employee.getShifts());
		for (Shift shift : employee.getShifts()) {			
			LocalDate startShiftDate = shift.getStart().toLocalDate();
			LocalDate endShiftDate = shift.getEnd().toLocalDate();

			LocalTime startShiftTime = shift.getStart().toLocalTime();
			LocalTime endShiftTime = shift.getEnd().toLocalTime();

			if ((date.equals(startShiftDate) || date.isAfter(startShiftDate))
					&& (date.equals(endShiftDate) || date.isBefore(endShiftDate))) {
				if ((time.equals(startShiftTime) || time.isAfter(startShiftTime))
						&& (time.equals(endShiftTime) || time.isBefore(endShiftTime))) {
					return true;
				}
			}
		}
		return false;
	}
	
	//ReservationUseCase
	private Map<LocalTime, List<Employee>> getFreeBeauticiansForEachTime(
			Map<LocalTime, List<Employee>> shiftsForBeautician, LocalDate date, Treatment treatment)throws Exception 
	{
		List<Reservation> reservations = daoReservation.getAllBusyReservations(date, treatment);		
				
		for (LocalTime key : shiftsForBeautician.keySet()) {			
			// estetisti che lavorano in quel determinato orario
			List<Employee> employees = shiftsForBeautician.get(key);			
			for (Reservation reservation : reservations) {
				LocalTime dateTimeReservation = reservation.getDateTime().toLocalTime();
				
				if (dateTimeReservation.equals(key)) {					
					Employee beautician = reservation.getEmployee();					
					int beauticianId = beautician.getId();				
					employees.removeIf(e -> e.getId() == beauticianId);

				}
			}
		}

		return shiftsForBeautician;
	}
}
