package com.bitcamp.centro.estetico.useCases;
import com.bitcamp.centro.estetico.DAO.ShiftDAO;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class CreateShiftUseCase {
	private ShiftDAO daoShift;
	
	public CreateShiftUseCase(ShiftDAO daoShift) {
		this.daoShift = daoShift;
	}
	
	public void execute(ShiftEmployee shift)throws Exception {
		daoShift.insert(shift);
	}
}
