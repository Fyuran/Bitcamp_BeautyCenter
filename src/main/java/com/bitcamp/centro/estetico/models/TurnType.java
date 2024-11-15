package com.bitcamp.centro.estetico.models;

public enum TurnType {
	WORK("Lavoro"),
	HOLIDAYS("Ferie");

	private final String type;

	private TurnType(String s) {
		this.type = s;
	}

	public static TurnType toEnum(String s) {
		switch (s.toLowerCase()) {
			case "lavoro":
				return TurnType.WORK;
			case "ferie":
				return TurnType.HOLIDAYS;

			case "work":
				return TurnType.WORK;
			case "holidays":
				return TurnType.HOLIDAYS;
			default:
				return null;
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
