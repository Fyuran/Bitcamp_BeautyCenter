package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.DAOReservation;
import com.bitcamp.centro.estetico.models.Reservation;

public class UpdateReservationUseCase {
	
	private DAOReservation daoReservation;
	public UpdateReservationUseCase(DAOReservation daoReservation) {
		this.daoReservation = daoReservation;
	}
	
	public void execute(Reservation reservation)throws Exception {
		daoReservation.update(reservation);
	}
}
