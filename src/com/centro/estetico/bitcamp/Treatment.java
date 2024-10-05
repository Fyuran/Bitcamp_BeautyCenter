package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import template.TreatmentPanel;

public class Treatment {
	private int id;
	private String type;
	private BigDecimal price;
	private VAT vat;
	private Duration duration;
	private List<Product> products;
	private boolean isEnabled;
	
	public Treatment() {
		this.duration = Duration.ofMinutes(30);
	}

	public Treatment(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1), 
			rs.getString(2), 
			rs.getBigDecimal(3),
			rs.getInt(4), 
			Duration.ofMillis(rs.getTime(5).getTime()), 
			rs.getBoolean(6)
		);		
	}
	
	private Treatment(int id, String type, BigDecimal price, int vatId, Duration duration, boolean isEnabled) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.vat = Main.getBeautyCenter().getInfoVat().stream().filter(vat -> vat.getId() == vatId).findFirst().orElseThrow();
		this.duration = duration;
		this.products = TreatmentPanel.getAllProductsByTreatmentId(id);
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

	public VAT getVat() {
		return vat;
	}

	public void setVat(VAT vat) {
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
