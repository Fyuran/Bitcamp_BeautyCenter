package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.DAOShift;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class CreateShiftUseCase {
	private DAOShift daoShift;
	
	public CreateShiftUseCase(DAOShift daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shift)throws Exception {
		daoShift.insert(shift);
	}
}
