package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Treatments {
	private int id;
	private String type;
	private BigDecimal price;
	private double vat;
	private Duration duration;
	private List<Product> products;
	private boolean isEnabled;
	
	public Treatments() {
		this.duration = Duration.ofMinutes(30);
	}

	public Treatments(int id, String type, BigDecimal price, double vat, Duration duration, List<Product> products,
			boolean isEnabled) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.vat = vat;
		this.duration = duration;
		this.products = products;
		this.isEnabled = isEnabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "Treatments [id=" + id + ", type=" + type + ", price=" + price + ", vat=" + vat + ", duration="
				+ duration + ", products=" + products + ", isEnabled=" + isEnabled + "]";
	}

	public List<LocalTime> getTreatmentTime() throws Exception {
		List<LocalTime> timeSlots = new ArrayList<>();

		try {

			LocalTime openingHour = Main.getBeautyCenter().getOpeningHour();
			LocalTime closingHour = Main.getBeautyCenter().getClosingHour();

			for (LocalTime time = openingHour; !time.plus(duration).isAfter(closingHour); time = time.plus(duration)) {

				timeSlots.add(time);
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return timeSlots;
	}

}
