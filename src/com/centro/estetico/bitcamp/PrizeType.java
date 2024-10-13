package com.centro.estetico.bitcamp;

public enum PrizeType {
	TREATMENT("Trattamento"),
	PRODUCT("Prodotto"),
	DISCOUNT("Sconto"),
	SUBSCRIPTION("Abbonamento");

	private final String type;

	private PrizeType(String s) {
		this.type = s;
	}

	public static PrizeType toEnum(String s) {
		switch(s.toLowerCase()) {
			case "trattamento": return PrizeType.TREATMENT;
			case "prodotto": return PrizeType.PRODUCT;
			case "sconto": return PrizeType.DISCOUNT;
			case "abbonamento": return PrizeType.SUBSCRIPTION;

			case "treatment": return PrizeType.TREATMENT;
			case "product": return PrizeType.PRODUCT;
			case "discount": return PrizeType.DISCOUNT;
			case "subscription": return PrizeType.SUBSCRIPTION;
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
