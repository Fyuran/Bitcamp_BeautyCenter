package com.centro.estetico.useCases;

import java.util.Map;

import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.ShiftEmployee;

import DAO.DAOShift;

public class GetShiftUseCase {
	private DAOShift daoShift;

	public GetShiftUseCase(DAOShift daoShift) {
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
