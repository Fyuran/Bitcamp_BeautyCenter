package com.centro.estetico.useCases;
import DAO.DAOReservation;
import com.centro.estetico.bitcamp.Reservation;

public class DeleteReservationUseCase {
	
	private DAOReservation daoReservation;
	public DeleteReservationUseCase(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.disable(reservation);
	}
}
