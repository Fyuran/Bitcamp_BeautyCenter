package com.centro.estetico.bitcamp;

import java.time.LocalDateTime;

public class Shift {
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ShiftType type;
    public enum ShiftType {WORK,HOLIDAYS}
    // Costruttore
    public Shift(int id, LocalDateTime start, LocalDateTime end, ShiftType type) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
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
	@Override
	public String toString() {
		return "Shift [id=" + id + ", start=" + start + ", end=" + end + ", type=" + type + "]";
	}


	
	

}
