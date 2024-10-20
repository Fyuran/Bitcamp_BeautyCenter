package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.ShiftDAO;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class DeleteShiftUseCase {
	private ShiftDAO daoShift;
	
	public DeleteShiftUseCase(ShiftDAO daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shiftEmployee)throws Exception {
		daoShift.deleteShift(shiftEmployee.getShift());
	}
}
