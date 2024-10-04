package com.centro.estetico.bitcamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends User {

	

	// LA TENIAMO PULITA
	private String EU_TIN;// codice fiscale
	private Subscription subscription;
	private ArrayList<Prize> prize; // Premi
	private String VAT;
	private String recipientCode;

	public Customer(int id, String name, String surname, String birthplace, boolean isFemale, LocalDate BoD,
			String notes, boolean isEnabled, Subscription subscription, ArrayList<Prize> prize, String VAT,
			String recipientCode) {
		super(-1, name, surname, birthplace, isFemale, BoD, notes, isEnabled);
		// Il codice fiscale lo calcola in automatico
		this.subscription = subscription;
		this.prize = prize != null ? prize : new ArrayList<>();
		this.VAT = VAT;
		this.recipientCode = recipientCode;
		this.EU_TIN = calculateEU_TIN(name, surname, birthplace, isFemale, BoD);
	}

	private static String calculateEU_TIN(String name, String surname, String birthplace, boolean isFemale,
			LocalDate BoD) {
		String EU_TIN = "";// Calcolo del codice fiscale
		return EU_TIN;

	}

	//Getter e setter
	public String getEuTin() {
		return EU_TIN;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public ArrayList<Prize> getPrize() {
		return prize;
	}

	public void setPrize(ArrayList<Prize> prize) {
		this.prize = prize;
	}

	public String getVAT() {
		return VAT;
	}

	public void setVAT(String VAT) {
		this.VAT = VAT;
	}

	public String getRecipientCode() {
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}

	@Override
	public String toString() {
		return "Customer [EU_TIN=" + EU_TIN + ", subscription=" + subscription + ", prize=" + prize + ", VAT=" + VAT
				+ ", recipientCode=" + recipientCode + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getSurname()=" + getSurname() + ", getBirthplace()=" + getBirthplace() + ", getIsFemale()="
				+ getIsFemale() + ", getBoD()=" + getBoD() + ", getNotes()=" + getNotes() + ", GetIsEnabled()="
				+ GetIsEnabled() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
	
}
