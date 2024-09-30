package com.centro.estetico.bitcamp;

import java.time.LocalDate;

public class Subscription {
	private int id;
	private SubPeriod property;
	private LocalDate start;
	private LocalDate end; // da uml è richiesto che sia final ma non posso inizializzarla
	private double price;
	private double vat;
	private double discount;

//tutti i valori sono obbligatori per il database
	public Subscription(int id, SubPeriod property, LocalDate start, LocalDate end, double price, double vat,
			double discount) {
		this.id = id;
		this.property = property;
		this.start = start;
		this.end = end;
		this.price = price;
		this.vat = vat;
		this.discount = discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SubPeriod getProperty() {
		return property;
	}

	public void setProperty(SubPeriod property) {
		this.property = property;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
}
