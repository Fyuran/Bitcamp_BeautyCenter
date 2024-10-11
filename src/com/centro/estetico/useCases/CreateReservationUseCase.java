package com.centro.estetico.useCases;
import DAO.DAOReservation;
import com.centro.estetico.bitcamp.Reservation;

public class CreateReservationUseCase {
	
	private DAOReservation daoReservation;
	public CreateReservationUseCase(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.insert(reservation);
	}
}
