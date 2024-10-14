package com.centro.estetico.useCases;

import com.centro.estetico.bitcamp.ShiftEmployee;

import DAO.DAOShift;

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
