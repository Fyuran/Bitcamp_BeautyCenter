package com.bitcamp.centro.estetico.useCases;

import com.bitcamp.centro.estetico.DAO.DAOShift;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class UpdateShiftUseCase {
	private DAOShift daoShift;
	public UpdateShiftUseCase(DAOShift daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shiftEmployee)throws Exception {
		daoShift.updateShiftEmployee(shiftEmployee);
		daoShift.updateShift(shiftEmployee.getShift());
	}
}
