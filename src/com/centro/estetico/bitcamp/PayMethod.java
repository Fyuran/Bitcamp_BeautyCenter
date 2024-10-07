package com.centro.estetico.bitcamp;

public enum PayMethod {
	CURRENCY("Moneta"),
	CARD("Carta");
	
	private final String type;
	
	private PayMethod(String s) {
		this.type = s;
	}
	
	public static PayMethod toEnum(String s) {
		switch(s.toLowerCase()) {
			case "currency": return PayMethod.CURRENCY;
			case "card": return PayMethod.CARD;
			case "moneta": return PayMethod.CURRENCY;
			case "carta": return PayMethod.CARD;
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
