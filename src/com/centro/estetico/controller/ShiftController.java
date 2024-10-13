package com.centro.estetico.controller;

import java.util.Map;
import com.centro.estetico.useCases.*;

import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.ShiftEmployee;


public class ShiftController {

	private GetShiftUseCase getShiftUseCase;
	private CreateShiftUseCase createShiftUseCase;
	private UpdateShiftUseCase updateShiftUseCase;
	private DeleteShiftUseCase deleteShiftUseCase;
	

	public ShiftController(GetShiftUseCase getShiftUseCase) {		
		this.getShiftUseCase = getShiftUseCase;
	}
	
	public ShiftController(CreateShiftUseCase createShiftUseCase) {		
		this.createShiftUseCase = createShiftUseCase;
	} 
	
	public ShiftController(UpdateShiftUseCase updateShiftUseCase) {		
		this.updateShiftUseCase = updateShiftUseCase;
	} 
	
	public ShiftController(DeleteShiftUseCase deleteShiftUseCase) {		
		this.deleteShiftUseCase = deleteShiftUseCase;
	} 
	
	public void add(ShiftEmployee shiftEmployee)throws Exception{
		validateInputs(shiftEmployee);		
		createShiftUseCase.execute(shiftEmployee);
	}
	
	public void update(ShiftEmployee shiftEmployee)throws Exception {
		validateInputs(shiftEmployee);
		updateShiftUseCase.execute(shiftEmployee);
	}

	public void delete(ShiftEmployee shiftEmployee)throws Exception {
		deleteShiftUseCase.execute(shiftEmployee);
	}
	
	public Map<Integer, ShiftEmployee> getShifts()throws Exception{		
		return getShiftUseCase.getShifts();
	}
	
	public ShiftEmployee getShift(int id) throws Exception{
		if(id <= 0) {
			throw new Exception("Id non valido");
		}
		else {
			return getShiftUseCase.getShift(id);
		}		
	}
	
	public Map<Integer, Shift> getSearchedShifts(String text)throws Exception{
		return getShiftUseCase.getSearchedShifts(text);
	}
	
	
	private void validateInputs(ShiftEmployee shiftEmployee) {		
		if(shiftEmployee.getShift() == null) {
			throw new IllegalArgumentException("Oggetto non valido");
		}
		if (shiftEmployee.getShift().getStart() == null) {
			throw new IllegalArgumentException("Data di inizio turno non valida");
		}
		if (shiftEmployee.getShift().getEnd() == null) {
			throw new IllegalArgumentException("Data di fine turno non valida");
		}
		if (shiftEmployee.getShift().getStart().isAfter(shiftEmployee.getShift().getEnd())) {
			throw new IllegalArgumentException("La data di inizio non può essere successiva a quella di fine");
		}
		if (shiftEmployee.getEmployee() == null) {
			throw new IllegalArgumentException("Seleziona un estetista");
		}
	}		
}

