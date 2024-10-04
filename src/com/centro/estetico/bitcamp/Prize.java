package com.centro.estetico.bitcamp;

import java.time.LocalDate;

public class Prize {
    private int id;
    private String name;
    private int threshold;
    private LocalDate expirationDate;
    private double amount;
    private boolean isEnabled;

    // Costruttore
    public Prize(int id, String name, int threshold, LocalDate expirationDate, double amount, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.threshold = threshold;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.isEnabled = isEnabled;
    }

    // Costruttore vuoto
    public Prize() {
        this.id = 0;
        this.name = "";
        this.threshold = 0;
        this.expirationDate = LocalDate.now();
        this.amount = 0;
        this.isEnabled = true;
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

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "Prize [id=" + id + ", name=" + name + ", threshold=" + threshold + ", expirationDate=" + expirationDate
				+ ", amount=" + amount + ", isEnabled=" + isEnabled + "]";
	}
    
    


}
