package com.bitcamp.centro.estetico.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Reservation;
import com.bitcamp.centro.estetico.models.Treatment;
import com.bitcamp.centro.estetico.useCases.CreateReservationUseCase;
import com.bitcamp.centro.estetico.useCases.DeleteReservationUseCase;
import com.bitcamp.centro.estetico.useCases.GetReservationUseCase;
import com.bitcamp.centro.estetico.useCases.UpdateReservationUseCase;


public class ReservationController {

	//private DAOReservation daoReservation;
	private GetReservationUseCase getReservationUseCase;
	private CreateReservationUseCase createReservationUseCase;
	private UpdateReservationUseCase updateReservationUseCase;
	private DeleteReservationUseCase deleteReservationUseCase;

	public ReservationController(GetReservationUseCase getReservationUseCase) {
		//this.daoReservation = daoReservation;
		this.getReservationUseCase = getReservationUseCase;
	}
	
	public ReservationController(CreateReservationUseCase createReservationUseCase) {
		//this.daoReservation = daoReservation;
		this.createReservationUseCase = createReservationUseCase;
	} 
	
	public ReservationController(UpdateReservationUseCase updateReservationUseCase) {
		//this.daoReservation = daoReservation;
		this.updateReservationUseCase = updateReservationUseCase;
	} 
	
	public ReservationController(DeleteReservationUseCase deleteReservationUseCase) {
		//this.daoReservation = daoReservation;
		this.deleteReservationUseCase = deleteReservationUseCase;
	} 
	
	public void add(Reservation reservation)throws Exception{
		validateInputs(reservation);		
		createReservationUseCase.execute(reservation);
	}
	
	public void update(Reservation reservation)throws Exception {
		validateInputs(reservation);
		updateReservationUseCase.execute(reservation);
	}

	public void delete(Reservation reservation)throws Exception {
		deleteReservationUseCase.execute(reservation);
	}
	
	public Map<Integer, Reservation> getReservations()throws Exception{		
		return getReservationUseCase.getReservations();
	}
	
	public Reservation getReservation(int id) throws Exception{
		if(id <= 0) {
			throw new Exception("Id non valido");
		}
		else {
			return getReservationUseCase.getReservation(id);
		}		
	}
	
	public Map<Integer, Reservation> getSearchedReservations(String text)throws Exception{
		return getReservationUseCase.getSearchedReservations(text);
	}
	
	public Map<LocalTime, List<Employee>> getEmployees(LocalDate date, List<LocalTime> hours, Treatment treatment)throws Exception {
		validateInputsForEmployees(date, hours, treatment);
		return getReservationUseCase.getEmployees(date, hours, treatment);
	}
	
	
	private void validateInputs(Reservation reservation) {
		if(reservation == null) {
			throw new IllegalArgumentException("Oggetto non valido");
		}
		if (reservation.getCustomer() == null) {
			throw new IllegalArgumentException("Seleziona un cliente");
		}
		if (reservation.getTreatment() == null) {
			throw new IllegalArgumentException("Seleziona un trattamento");
		}
		if (reservation.getEmployee() == null) {
			throw new IllegalArgumentException("Seleziona un estetista");
		}
		if (reservation.getDateTime() == null) {
			throw new IllegalArgumentException("Seleziona data e ora");
		}
	}
	
	private void validateInputsForEmployees(LocalDate date, List<LocalTime> hours, Treatment treatment) {
		if (treatment == null) {
			throw new IllegalArgumentException("Seleziona un trattamento");
		}
		
		if (date == null) {
			throw new IllegalArgumentException("Seleziona una data");
		}
		
		if (hours == null || hours.size()<=0) {
			throw new IllegalArgumentException("orari non disponibili");
		}
	}
}
