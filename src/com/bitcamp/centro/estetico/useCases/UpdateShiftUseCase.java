package com.bitcamp.centro.estetico.useCases;

import com.bitcamp.centro.estetico.DAO.ShiftDAO;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class UpdateShiftUseCase {
	private ShiftDAO daoShift;
	public UpdateShiftUseCase(ShiftDAO daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shiftEmployee)throws Exception {
		daoShift.updateShiftEmployee(shiftEmployee);
		daoShift.updateShift(shiftEmployee.getShift());
	}
}
