package com.centro.estetico.bitcamp;

public enum ProductCat {
ORAL_CARE("Cura Orale"),
SKIN_CARE("Cura Pelle"),
HAIR_CARE("Cura Capelli"),
BODY_CARE("Cura del corpo"),
COSMETICS("Cosmetici"),
PERFUMES("Profumi"),
OTHER("Altro");
	
	private final String description;

	private ProductCat(String description ) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}

