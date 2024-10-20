package com.bitcamp.centro.estetico.useCases;

import java.util.Map;

import com.bitcamp.centro.estetico.DAO.ShiftDAO;
import com.bitcamp.centro.estetico.models.ShiftEmployee;

public class GetShiftUseCase {
	private ShiftDAO daoShift;

	public GetShiftUseCase(ShiftDAO daoShift) {
		this.daoShift = daoShift;
	}

	public Map<Integer, ShiftEmployee> getShifts()throws Exception {
		return daoShift.getShifts();
	}

	public ShiftEmployee getShift(int id)throws Exception {
		return daoShift.getShift(id);
	}
	
	public Map<Integer, ShiftEmployee> getSearchedShifts(String text)throws Exception{
		return daoShift.getSearchedShifts(text);
	}
}
