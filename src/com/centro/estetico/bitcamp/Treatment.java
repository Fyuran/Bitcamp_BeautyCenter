package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import DAO.TreatmentDAO;
import DAO.VATDao;

public class Treatment {
	private int id;
	private String type;
	private BigDecimal price;
	private VAT vat;
	private Duration duration;
	private List<Product> products;
	private boolean isEnabled;

	public Treatment(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1), 
			rs.getString(2), 
			rs.getBigDecimal(3),
			VATDao.getVAT(rs.getInt(4)).get(), 
			Duration.ofMillis(rs.getTime(5).getTime()),
			TreatmentDAO.getProductsOfTreatment(rs.getInt(1)),
			rs.getBoolean(6)
		);		
	}
	
	private Treatment(int id, String type, BigDecimal price, VAT vat, Duration duration, List<Product> products, boolean isEnabled) {
		this.id = id;
		this.type = type;
		this.price = price;
		this.vat = vat;
		this.duration = duration;
		this.products = products;
		this.isEnabled = isEnabled;
	}
	
	public Treatment(String type, BigDecimal price, VAT vat, Duration duration, List<Product> products, boolean isEnabled) {
		this(-1, type, price, vat, duration, products, isEnabled);
	}
	
	public Treatment(int id, Treatment obj) {
		this(id, obj.type, obj.price, obj.vat, obj.duration, obj.products, obj.isEnabled);
	}
	
	public int getId() {
		return id;
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

	public Object[] toTableRow() {
		return new Object[] {
				id, type, price, vat, duration, products, isEnabled
		};
	}

	public void addProducts(Product...products) {
		for(Product product : products) {
			if(products != null) {
				this.products.add(product);				
			}
		}
	}

	public void removeProducts(Product...products) {
		for(Product product : products) {
			if(products != null) {
				this.products.remove(product);
			}
		}
	}
	
}
