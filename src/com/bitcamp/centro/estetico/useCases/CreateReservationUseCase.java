package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.ReservationDAO;
import com.bitcamp.centro.estetico.models.Reservation;

public class CreateReservationUseCase {
	
	private ReservationDAO daoReservation;
	public CreateReservationUseCase(ReservationDAO daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.insert(reservation);
	}
}
