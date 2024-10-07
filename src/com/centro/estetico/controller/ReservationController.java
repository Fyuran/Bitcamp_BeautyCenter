package com.centro.estetico.bitcamp.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import javax.swing.JComboBox;
import javax.swing.JList;

import com.centro.estetico.bitcamp.repository.*;
import com.centro.estetico.enums.IsCreatingOrUpdating;
import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Reservation;
import com.centro.estetico.bitcamp.Treatment;
import com.toedter.calendar.JCalendar;

public class ReservationController {
	
	private DAOReservation daoReservation;
	public ReservationController(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}

	public void sendDataToDB(JComboBox customersComboBox, JComboBox treatmentsComboBox, JCalendar calendar, 
			JList beauticiansList, JList timeList, Reservation reservation, IsCreatingOrUpdating icou) {

		Customer customer = (Customer) customersComboBox.getSelectedItem();

		if (customer == null) {
			throw new IllegalArgumentException("Seleziona un cliente");
		}

		Treatment treatment = (Treatment) treatmentsComboBox.getSelectedItem();
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
		
		
		Employee employee = (Employee) beauticiansList.getSelectedValue();
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
