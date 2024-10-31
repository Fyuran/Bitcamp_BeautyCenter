package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;

public class Transaction implements Model {
	private Long id;
	private BigDecimal price;
	private VAT vat;
	private LocalDateTime dateTime;
	private PayMethod paymentMethod;
	private Customer customer;
	private BeautyCenter beautyCenter;
	private String services;
	private boolean isEnabled;

	private Transaction(
			Long id, BigDecimal price, PayMethod paymentMethod,
			LocalDateTime dateTime, Customer customer,
			VAT vat, BeautyCenter beautyCenter, String services, boolean isEnabled
			) {
		this.id = id;
		this.price = price;
		this.paymentMethod = paymentMethod;
		this.dateTime = dateTime;
		this.customer = customer;
		this.vat = vat;
		this.beautyCenter = beautyCenter;
		this.services = services;
		this.isEnabled = isEnabled;
	}

	public Transaction(Long id, Transaction obj) {
		this(id, obj.price, obj.paymentMethod, obj.dateTime, obj.customer, obj.vat, obj.beautyCenter, obj.services, obj.isEnabled);
	}

	public Transaction(
			BigDecimal price, PayMethod paymentMethod,
			LocalDateTime dateTime, Customer customer,
			VAT vat, BeautyCenter beautyCenter, String services
			) {
		this(-1, price, paymentMethod, dateTime, customer, vat, beautyCenter, services, true);
	}
	public Transaction(
			BigDecimal price, PayMethod paymentMethod,
			LocalDateTime dateTime, Customer customer,
			VAT vat, BeautyCenter beautyCenter
			) {
		this(-1, price, paymentMethod, dateTime, customer, vat, beautyCenter, "", true);
	}

	public Transaction(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1),
			rs.getBigDecimal(2),
			PayMethod.toEnum(rs.getString(4)),
			rs.getTimestamp(3).toLocalDateTime(),
			CustomerDAO.getInstance().get(rs.getInt(6)).orElseThrow(),
			VAT_DAO.getInstance().get(rs.getInt(5)).orElseThrow(),
			BeautyCenterDAO.getInstance().get(rs.getInt(7)).orElseThrow(),
			rs.getString(8),
			rs.getBoolean(9)
		);
	}

	@Override
	public Long getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public PayMethod getPaymentMethod() {
		return paymentMethod;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public VAT get() {
		return vat;
	}

	public BeautyCenter getBeautyCenter() {
		return beautyCenter;
	}

	public String getServices() {
		return services;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setPaymentMethod(PayMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setVat(VAT vat) {
		this.vat = vat;
	}

	public void setBeautyCenter(BeautyCenter beautyCenter) {
		this.beautyCenter = beautyCenter;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Object[] toTableRow() {
		return new Object[] {
				id, price, dateTime, paymentMethod, vat, customer, services, isEnabled
		};
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return price + " "  + dateTime.format(dtf) + " " +
				paymentMethod.toString() + " " + vat.toString() + " " +
				customer.getFullName() + " " + services;
	}


}
