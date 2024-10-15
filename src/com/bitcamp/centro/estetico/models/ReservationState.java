package com.bitcamp.centro.estetico.models;

public enum ReservationState {
	CREATED("Creato"),
	IN_PROGRESS("In progresso"),
	CANCELLED("Cancellato");

	private final String state;

	private ReservationState(String description ) {
		this.state = description;
	}

	public static ReservationState toEnum(String s) {
		switch(s.toLowerCase()) {
			case "creato": return ReservationState.CREATED;
			case "in progresso": return ReservationState.IN_PROGRESS;
			case "cancellato": return ReservationState.CANCELLED;

			case "created": return ReservationState.CREATED;
			case "in_progress": return ReservationState.IN_PROGRESS;
			case "cancelled": return ReservationState.CANCELLED;
			default: return null;
		}

	}

	public int toSQLOrdinal() {
		return this.ordinal() + 1;
	}

	@Override
	public String toString() {
		return state;
	}
}
