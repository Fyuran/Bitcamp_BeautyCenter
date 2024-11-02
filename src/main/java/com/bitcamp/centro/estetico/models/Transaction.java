package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction implements Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal price;

	@OneToOne
	@JoinColumn(name = "vat_id")
	private VAT vat;

	@Column(name = "datetime")
	private LocalDateTime dateTime;

	@Column(name = "payment_method")
	private PayMethod paymentMethod;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "beauty_center_id", nullable = false)
	private BeautyCenter beautyCenter;

	private String services;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Transaction() {
		this.isEnabled = true;
	}

	public Transaction(Long id, BigDecimal price, VAT vat, LocalDateTime dateTime, PayMethod paymentMethod,
			Customer customer, BeautyCenter beautyCenter, String services) {
		this(id, price, vat, dateTime, paymentMethod, customer, beautyCenter, services, true);
	}

	public Transaction(BigDecimal price, VAT vat, LocalDateTime dateTime, PayMethod paymentMethod,
			Customer customer, BeautyCenter beautyCenter, String services) {
		this(null, price, vat, dateTime, paymentMethod, customer, beautyCenter, services);
	}

	public Transaction(Long id, BigDecimal price, VAT vat, LocalDateTime dateTime, PayMethod paymentMethod,
			Customer customer, BeautyCenter beautyCenter, String services, boolean isEnabled) {
		this.id = id;
		this.price = price;
		this.vat = vat;
		this.dateTime = dateTime;
		this.paymentMethod = paymentMethod;
		this.customer = customer;
		this.beautyCenter = beautyCenter;
		this.services = services;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public PayMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PayMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BeautyCenter getBeautyCenter() {
		return beautyCenter;
	}

	public void setBeautyCenter(BeautyCenter beautyCenter) {
		this.beautyCenter = beautyCenter;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
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
		return price + " " + dateTime.format(dtf) + " " +
				paymentMethod.toString() + " " + vat.toString() + " " +
				customer.getFullName() + " " + services;
	}

}
