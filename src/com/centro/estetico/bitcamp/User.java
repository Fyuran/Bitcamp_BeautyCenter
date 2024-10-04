package com.centro.estetico.bitcamp;
import java.time.*;

public abstract class User {
	

	private int id;
	private String name;
	private String surname;
	private String birthplace;
	private boolean isFemale;
	private LocalDate BoD;
	private String notes;
	private boolean isEnabled;

	// Costruttore
	 public User(int id, String name, String surname, String birthplace, boolean isFemale, LocalDate BoD, String notes, boolean isEnabled) {
	        this.id = id;
	        this.name = name;
	        this.surname = surname;
	        this.birthplace = birthplace;
	        this.isFemale = isFemale; 
	        this.BoD = BoD;
	        this.notes = notes;
	        this.isEnabled = isEnabled;
	    }


	//Getter
	protected int getId() {
	    return id;
	}

	public String getName() {
	    return name;
	}

	public String getSurname() {
	    return surname;
	}

	public String getBirthplace() {
	    return birthplace;
	}

    public boolean getIsFemale() { 
        return isFemale;
    }

	public LocalDate getBoD() {
	    return BoD;
	}

	public String getNotes() {
	    return notes;
	}

	public boolean GetIsEnabled() {
	    return isEnabled;
	}

	
	
	//Setter


	public void setName(String name) {
	    this.name = name;
	}

	public void setSurname(String surname) {
	    this.surname = surname;
	}

	public void setBirthplace(String birthplace) {
	    this.birthplace = birthplace;
	}

	  public void setIsFemale(boolean isFemale) { 
	        this.isFemale = isFemale;
	    }

	public void setBoD(LocalDate BoD) {
	    this.BoD = BoD;
	}

	public void setNotes(String notes) {
	    this.notes = notes;
	}

	public void setIsEnabled(boolean enabled) {
	    isEnabled = enabled;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + 
	surname + ", birthplace=" + birthplace
				+ ", gender=" + isFemale + ", BoD=" + BoD + ", notes=" 
	+ notes + ", isEnabled=" + isEnabled + "]";
	}

}


//Tasto destro->Source->Generate Getters e Setters ed anche il metodo ToString
