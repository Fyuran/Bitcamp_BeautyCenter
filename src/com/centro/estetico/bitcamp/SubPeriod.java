package com.centro.estetico.bitcamp;

public enum SubPeriod {
MONTLY(1),
QUARTERLY(3),
HALF_YEAR(6),
YEARLY(12);
	private int property;
	 SubPeriod(int property) {
		this.property=property;
	}
	public int getProperty() {
		return property;
	}
	public void setProperty(int property) {
		this.property = property;
	}
	 
}
