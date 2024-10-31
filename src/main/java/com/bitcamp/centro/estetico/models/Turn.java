package com.bitcamp.centro.estetico.models;

import java.time.LocalDateTime;

public class Turn implements Model {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private TurnType type;
    private String notes;
	private boolean isEnabled;
        
    public Turn(LocalDateTime start, LocalDateTime end, TurnType type, String notes) {        
        this(-1, start, end, type, notes, true);
    }
    
    private Turn(Long id, LocalDateTime start, LocalDateTime end, TurnType type, String notes, boolean isEnabled) {
    	this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.notes = notes;
		this.isEnabled = isEnabled;
    }
     
	@Override
	public Long getId() {
		return id;
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

	public TurnType getType() {
		return type;
	}

	public void setType(TurnType type) {
		this.type = type;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isShiftOver() {
		return LocalDateTime.now().isAfter(end);
	}

	@Override
	public String toString() {
		return "Shift [id=" + id + ", start=" + start + ", end=" + end + ", type=" + type + "]";
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public Object[] toTableRow() {
		return new Object[] { id, start, end, type, notes, isEnabled };
	}
}
