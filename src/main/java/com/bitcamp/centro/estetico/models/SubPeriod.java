package com.bitcamp.centro.estetico.models;

public enum SubPeriod {
	MONTHLY(1, "Mensile"),
	QUARTERLY(3, "Trimestrale"),
	HALF_YEAR(6, "Semestrale"),
	YEARLY(12, "Annuale");

	private final int months;
	private final String name;

	SubPeriod(int property, String name) {
		this.months = property;
		this.name = name;
	}

	public static SubPeriod toEnum(String s) {
		switch(s.toLowerCase()) {
			case "mensile": return SubPeriod.MONTHLY;
			case "trimestrale": return SubPeriod.QUARTERLY;
			case "semestrale": return SubPeriod.HALF_YEAR;
			case "annuale": return SubPeriod.YEARLY;

			case "monthly": return SubPeriod.MONTHLY;
			case "quarterly": return SubPeriod.QUARTERLY;
			case "half_year": return SubPeriod.HALF_YEAR;
			case "yearly": return SubPeriod.YEARLY;
			default: return null;
		}

	}

	public int getMonths() {
		return months;
	}

	public String getName() {
		return name;
	}
	public int toSQLOrdinal() {
		return this.ordinal() + 1;
	}

	@Override
	public String toString() {
		return name;
	}
}
