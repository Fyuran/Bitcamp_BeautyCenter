package com.centro.estetico.bitcamp;

import java.time.LocalDate;

public class Prize {
	private int id;
	private String name;
	private int treshold;
	private LocalDate expiration;
	private double amount;

	public Prize(int id, String name, int treshold, LocalDate expiration, double amount) {
		this.id = id;
		this.name = name;
		this.treshold = treshold;
		this.expiration = expiration;
		this.amount = amount;
	}

//costruttore con solo valori not null
	public Prize(int id, String name, int treshold) {
		this.id = id;
		this.name = name;
		this.treshold = treshold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTreshold() {
		return treshold;
	}

	public void setTreshold(int treshold) {
		this.treshold = treshold;
	}

	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
