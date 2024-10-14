package com.centro.estetico.useCases;
import DAO.DAOShift;
import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.ShiftEmployee;

public class CreateShiftUseCase {
	private DAOShift daoShift;
	
	public CreateShiftUseCase(DAOShift daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shift)throws Exception {
		daoShift.insert(shift);
	}
}
