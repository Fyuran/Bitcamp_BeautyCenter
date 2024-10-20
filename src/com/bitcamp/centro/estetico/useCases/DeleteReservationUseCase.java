package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.ReservationDAO;
import com.bitcamp.centro.estetico.models.Reservation;

public class DeleteReservationUseCase {
	
	private ReservationDAO daoReservation;
	public DeleteReservationUseCase(ReservationDAO daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.disable(reservation);
	}
}
