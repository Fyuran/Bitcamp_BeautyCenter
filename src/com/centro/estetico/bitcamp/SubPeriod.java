package com.centro.estetico.bitcamp;

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
	
	public int getMonths() {
		return months;
	}
	
	public String getName() {
		return name;
	}
	public int toSQLOrdinal() {
		return this.ordinal() + 1;
	}
}
