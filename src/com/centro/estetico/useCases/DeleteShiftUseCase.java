package com.centro.estetico.useCases;
import com.centro.estetico.bitcamp.ShiftEmployee;
import DAO.DAOShift;

public class DeleteShiftUseCase {
	private DAOShift daoShift;
	
	public DeleteShiftUseCase(DAOShift daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shiftEmployee)throws Exception {
		daoShift.deleteShift(shiftEmployee.getShift());
	}
}
