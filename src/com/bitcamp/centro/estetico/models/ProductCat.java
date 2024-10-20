package com.bitcamp.centro.estetico.models;

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
	public static ProductCat fromDescription(String description) {
        for (ProductCat category : ProductCat.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nessun ProductCat trovato per la descrizione: " + description);
    }
	
	public int toSQLOrdinal() {
		return this.ordinal() + 1;
	}
}

