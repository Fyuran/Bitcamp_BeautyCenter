package com.centro.estetico.useCases;
import DAO.DAOReservation;
import com.centro.estetico.bitcamp.Reservation;

public class UpdateReservationUseCase {
	
	private DAOReservation daoReservation;
	public UpdateReservationUseCase(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.update(reservation);
	}
}
