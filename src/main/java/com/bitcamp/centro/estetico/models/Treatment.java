package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;

public class Treatment implements Model {
	private Long id;
	private String type;
	private BigDecimal price;
	private VAT vat;
	private Duration duration;
	private List<Product> products;
	private boolean isEnabled;

	public Treatment(ResultSet rs) throws SQLException {

		this.id = rs.getInt(1);
		this.type = rs.getString(2);
		this.price = rs.getBigDecimal(3);
		this.vat = VAT_DAO.getInstance().get(rs.getInt(4)).get();
		this.duration = getDurationFromLocalTime(rs.getTime(5).toLocalTime());
		this.products=TreatmentDAO.getInstance().getProductsOfTreatment(rs.getInt(1));
		this.isEnabled=rs.getBoolean(6);

	}

	private Treatment(Long id, String type, BigDecimal price, VAT vat, Duration duration, List<Product> products,
			boolean isEnabled) {
		this.id = id;
		this.type = type;
		this.price = price;
		this.vat = vat;

		this.duration = duration;
		this.products = products;
		this.isEnabled = isEnabled;
	}

	public Treatment(String type, BigDecimal price, VAT vat, Duration duration, List<Product> products,
			boolean isEnabled) {
		this(-1, type, price, vat, duration, products, isEnabled);
	}

	public Treatment(Long id, Treatment obj) {
		this(id, obj.type, obj.price, obj.vat, obj.duration, obj.products, obj.isEnabled);
	}

	@Override
	public Long getId() {
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

	public VAT get() {
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

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public LocalTime getLocalTimeFromDuration() {
		return LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(),
				duration.toSecondsPart());
	}

	public Duration getDurationFromLocalTime(LocalTime time) {
		return Duration.ofSeconds(time.toSecondOfDay());
	}

	public String durationToPattern(String pattern) { // ex HH:mm:ss
		LocalTime time = getLocalTimeFromDuration();
		return time.format(DateTimeFormatter.ofPattern(pattern));
	}

	@Override
	public String toString() {
		return this.type;
	}

	public List<LocalTime> getTreatmentTime() throws Exception {
		List<LocalTime> timeSlots = new ArrayList<>();

		try {

			LocalTime openingHour = BeautyCenterDAO.getInstance().getFirst().get().getOpeningHour();
			LocalTime closingHour = BeautyCenterDAO.getInstance().getFirst().get().getClosingHour();

			for (LocalTime time = openingHour; !time.plus(duration).isAfter(closingHour); time = time.plus(duration)) {
				timeSlots.add(time);
			}
		} 
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return timeSlots;
	}
	//"ID", "Nome trattamento", "Prezzo", "IVA%", "Durata"
	public Object[] toTableRow() {
		return new Object[] { id, type, price, vat, duration, isEnabled };
	}

	public void addProducts(Product... products) {
		for (Product product : products) {
			if (products != null) {
				this.products.add(product);
			}
		}
	}

	public void removeProducts(Product... products) {
		for (Product product : products) {
			if (products != null) {
				this.products.remove(product);
			}
		}
	}

}
