package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.DAOShift;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class DeleteShiftUseCase {
	private DAOShift daoShift;
	
	public DeleteShiftUseCase(DAOShift daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shiftEmployee)throws Exception {
		daoShift.deleteShift(shiftEmployee.getShift());
	}
}
