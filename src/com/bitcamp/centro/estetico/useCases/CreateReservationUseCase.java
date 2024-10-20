package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.DAOReservation;
import com.bitcamp.centro.estetico.models.Reservation;

public class CreateReservationUseCase {
	
	private DAOReservation daoReservation;
	public CreateReservationUseCase(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.insert(reservation);
	}
}
