package com.bitcamp.centro.estetico.models;

public enum Roles {
	PERSONNEL("Personale"),
	SECRETARY("Front-desk"),
	ADMIN("Amministratore");

	private final String type;

	private Roles(String s) {
		this.type = s;
	}

	public static Roles toEnum(String s) {
		switch (s.toLowerCase()) {
			case "personale":
				return Roles.PERSONNEL;
			case "personnel":
				return Roles.PERSONNEL;
			case "front-desk":
				return Roles.SECRETARY;
			case "secretary":
				return Roles.SECRETARY;
			case "amministratore":
				return Roles.ADMIN;
			case "admin":
				return Roles.ADMIN;
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