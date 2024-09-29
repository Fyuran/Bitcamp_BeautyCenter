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
			case "moneta": return PayMethod.CURRENCY;
			case "carta": return PayMethod.CARD;
			default: return null;
		}
		
	}
	public String getType() {
		return type;
	}
}
