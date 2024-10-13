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
	
	public static SubPeriod toEnum(String s) {
		switch(s) {
			case "MONTHLY": return SubPeriod.MONTHLY;
			case "QUARTERLY": return SubPeriod.QUARTERLY;
			case "HALF_YEAR": return SubPeriod.HALF_YEAR;
			case "YEARLY": return SubPeriod.YEARLY;
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
}
