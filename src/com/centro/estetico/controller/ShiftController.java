package com.centro.estetico.controller;

//import wrappersForDisplayMember.*;
/*import java.time.Instant;
import java.time.LocalDate;*/
import java.time.LocalDateTime;
import java.time.LocalTime;
//import java.time.ZoneId;

import com.centro.estetico.bitcamp.*;
/*import javax.swing.JComboBox;
import javax.swing.JList;*/

import DAO.DAOShift;

/*import com.toedter.calendar.JCalendar;
import javax.swing.JOptionPane;*/

public class ShiftController {
	
	private DAOShift daoShift;
	public ShiftController(DAOShift daoShift) {
		this.daoShift = daoShift;
	}
	
	public void saveOrUpdateReservation(Shift shift, Employee employee, LocalDateTime shiftStart, LocalDateTime shiftEnd, ShiftType shiftType, String notes, IsCreatingOrUpdating icou) {
		
		validateInputs(employee, shiftStart, shiftEnd, shiftType, notes);		
		populateShift(shift, shiftStart, shiftEnd, shiftType, notes);
		
		if (icou == IsCreatingOrUpdating.CREATE) {
			daoShift.insert(shift, employee);
		} else {
			daoShift.update(shift, employee);
		}
	}

	public void validateInputs(Employee employee, LocalDateTime shiftStart, LocalDateTime shiftEnd, ShiftType shiftType, String notes) {
		
		if (employee == null) {
			throw new IllegalArgumentException("Seleziona un estetista");
		}
						
		if (shiftStart == null) {
			throw new IllegalArgumentException("Seleziona una data e un'ora di inizio");
		}
		
		if (shiftEnd == null) {
			throw new IllegalArgumentException("Seleziona una data e un'ora di fine");
		}

		if (shiftStart.toLocalTime() == LocalTime.MIDNIGHT) {
			throw new IllegalArgumentException("La data di inizio turno deve essere attuale o futura");
	    }
	    
	    if (shiftEnd.toLocalTime() == LocalTime.MIDNIGHT) {
	    	throw new IllegalArgumentException("La data di inizio turno deve essere attuale o futura");
	    }
		
		if(shiftStart.isAfter(shiftEnd)) {
			throw new IllegalArgumentException("La data di inizio turno non può essere successiva a quella di fine turno");
		}
				
		if (shiftType == null) {
			throw new IllegalArgumentException("Seleziona se si tratta di un turno lavorativo o ferie");
		}
			
		//createShiftUseCase.execute();
		/* 1. crea il turno e ottieni l'id
		 * 2. fai una query che crea uno shiftemployee con gli id di shift e shiftemployee
		 * */
		//daoShift.insert(new Shift(shiftStart, shiftEnd, shiftType, notes), employee);
	}
	
	private void populateShift(Shift shift, LocalDateTime shiftStart, LocalDateTime shiftEnd, ShiftType shiftType, String notes) {
		
		shift.setStart(shiftStart);
		shift.setEnd(shiftEnd);
		shift.setNotes(notes);
		shift.setType(shiftType);
		// reservation.setState(); // Se richiesto, imposta lo stato
		// reservation.setPaid(); // Se richiesto, imposta lo stato di pagamento
	}

}

