package com.centro.estetico.controller;
import wrappersForDisplayMember.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import com.centro.estetico.bitcamp.*;
import javax.swing.JComboBox;
import javax.swing.JList;

//import DAO;
import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Reservation;
import com.centro.estetico.bitcamp.Treatment;
import com.toedter.calendar.JCalendar;

public class ReservationController {
	
	private DAO.DAOReservation daoReservation;
	public ReservationController(DAO.DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}

	public void sendDataToDB(JComboBox customersComboBox, JComboBox treatmentsComboBox, JCalendar calendar, 
			JList beauticiansList, JList timeList, Reservation reservation, IsCreatingOrUpdating icou) {

		CustomerWrapper cw = (CustomerWrapper) customersComboBox.getSelectedItem();
		Customer customer = cw.getCustomer();

		if (customer == null) {
			throw new IllegalArgumentException("Seleziona un cliente");
		}

		TreatmentWrapper tw = (TreatmentWrapper) treatmentsComboBox.getSelectedItem();
		Treatment treatment = tw.getTreatment();
		
		if (treatment == null) {
			throw new IllegalArgumentException("Seleziona un trattamento");
		}
		
		// giorno e ora
		Instant instant = calendar.getDate().toInstant();
		if (instant == null) {
			throw new IllegalArgumentException("Seleziona una data");
		}
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.ofInstant(instant, zoneId);
		LocalTime localTime = (LocalTime) timeList.getSelectedValue();

		if (localTime == null) {
			throw new IllegalArgumentException("Seleziona un orario");
		}
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		
		
		EmployeeWrapper ew = (EmployeeWrapper) beauticiansList.getSelectedValue();
		Employee employee = ew.getEmployee();
		if (employee == null) {
			throw new IllegalArgumentException("Seleziona un estetista");
		}

		
		reservation.setCustomer(customer);
		reservation.setTreatment(treatment);
		reservation.setEmployee(employee);
		reservation.setDateTime(localDateTime);
		// reservation.setState();
		// reservation.setPaid();
		
		if(icou == IsCreatingOrUpdating.CREATE) {
			daoReservation.insert(reservation);
		}
		else {
			daoReservation.update(reservation);
		}
				
	}

}
