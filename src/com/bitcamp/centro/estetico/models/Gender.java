package com.bitcamp.centro.estetico.models;

public enum Gender {
    MALE("Maschio"),
    FEMALE("Femmina");

    private final String gender;

	private Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public static Gender toEnum(String s) {
		switch(s.toLowerCase()) {
			case "maschio": return Gender.MALE;
			case "femmina": return Gender.FEMALE;

			case "male": return Gender.MALE;
			case "female": return Gender.FEMALE;

			default: return null;
		}

	}

	public int toSQLOrdinal() {
		return this.ordinal() + 1;
	}

	public static Gender fromBool(boolean bool) {
		if(!bool) return Gender.MALE;
        return Gender.FEMALE;
	}

    public boolean toBoolean() {
        if(this == Gender.MALE) return false; //not female
        return true; //is female
    }

	@Override
	public String toString() {
		return gender;
	}
}
