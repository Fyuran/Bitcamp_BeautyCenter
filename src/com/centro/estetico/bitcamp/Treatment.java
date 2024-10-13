package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.BeautyCenterDAO;
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

		this.id = rs.getInt(1);
		this.type = rs.getString(2);
		this.price = rs.getBigDecimal(3);
		this.vat = VATDao.getVAT(rs.getInt(4)).get();
		java.sql.Time sqlTime = rs.getTime(5);
		LocalTime localTime = sqlTime.toLocalTime();
		this.duration = Duration.between(LocalTime.MIDNIGHT, localTime);
		this.products=TreatmentDAO.getProductsOfTreatment(rs.getInt(1));
		this.isEnabled=rs.getBoolean(6);

	}

	private Treatment(int id, String type, BigDecimal price, VAT vat, Duration duration, List<Product> products,
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

	public LocalTime getLocalTimeFromDuration() {
		return LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(),
				duration.toSecondsPart());
	}

	public String durationToPattern(String pattern) { // ex HH:mm:ss
		LocalTime time = getLocalTimeFromDuration();
		return time.format(DateTimeFormatter.ofPattern(pattern));
	}

	@Override
	public String toString() {
		return "Treatments [id=" + id + ", type=" + type + ", price=" + price + ", vat=" + vat + ", duration="
				+ duration + ", products=" + products + ", isEnabled=" + isEnabled + "]";
	}

	public List<LocalTime> getTreatmentTime() throws Exception {
		List<LocalTime> timeSlots = new ArrayList<>();

		try {

			LocalTime openingHour = BeautyCenterDAO.getBeautyCenter(1).get().getOpeningHour();
			LocalTime closingHour = BeautyCenterDAO.getBeautyCenter(1).get().getClosingHour();

			for (LocalTime time = openingHour; !time.plus(duration).isAfter(closingHour); time = time.plus(duration)) {

				timeSlots.add(time);
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return timeSlots;
	}

	public Object[] toTableRow() {
		return new Object[] { id, type, price, vat, duration, products, isEnabled };
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
