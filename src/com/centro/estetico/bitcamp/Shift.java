package com.centro.estetico.bitcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Shift {
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ShiftType type;
    private String notes;
        
    public Shift(LocalDateTime start, LocalDateTime end, ShiftType type, String notes) {        
        this.start = start;
        this.end = end;
        this.type = type;
        this.notes = notes;
    }
    
    public Shift(int id, LocalDateTime start, LocalDateTime end, ShiftType type, String notes) {
    	this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.notes = notes;
    }
    
    public Shift() {
    	
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public ShiftType getType() {
		return type;
	}

	public void setType(ShiftType type) {
		this.type = type;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Shift [id=" + id + ", start=" + start + ", end=" + end + ", type=" + type + "]";
	}
}
