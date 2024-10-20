package com.bitcamp.centro.estetico.models;

public enum ShiftType {
	WORK("Lavoro"),
	HOLIDAYS("Ferie");

	private final String type;

	private ShiftType(String s) {
		this.type = s;
	}

	public static ShiftType toEnum(String s) {
		switch(s.toLowerCase()) {
			case "lavoro": return ShiftType.WORK;
			case "ferie": return ShiftType.HOLIDAYS;
			case "work": return ShiftType.WORK;
			case "holydays": return ShiftType.HOLIDAYS;
			default: return null;
		}
	}


	public int toSQLOrdinal() {
		return this.ordinal() + 1;
	}

	@Override
	public String toString() {
		return type;
	}
}
