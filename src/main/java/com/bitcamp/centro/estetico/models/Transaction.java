package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.controller.DAO;

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

	public Transaction(Map<String, Object> map) {
		this(
			(Long) map.get("ID"),
			(BigDecimal) map.get("Prezzo"),
			(VAT) map.get("IVA"),
			(LocalDateTime) map.get("Data"),
			(PayMethod) map.get("Metodo di Pagamento"),
			(Customer) map.get("Cliente"),
			(BeautyCenter) map.getOrDefault("Sede", DAO.get(BeautyCenter.class, 1L)),
			(String) map.get("Servizi"),
			(boolean) map.get("Abilitato")
		);
	}

	public Transaction(BigDecimal price, VAT vat, LocalDateTime dateTime, PayMethod paymentMethod,
			Customer customer, BeautyCenter beautyCenter, String services) {
		this(null, price, vat, dateTime, paymentMethod, customer, beautyCenter, services, true);
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((beautyCenter == null) ? 0 : beautyCenter.hashCode());
		result = prime * result + ((services == null) ? 0 : services.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (vat == null) {
			if (other.vat != null)
				return false;
		} else if (!vat.equals(other.vat))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (paymentMethod != other.paymentMethod)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (beautyCenter == null) {
			if (other.beautyCenter != null)
				return false;
		} else if (!beautyCenter.equals(other.beautyCenter))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		return Map.ofEntries(
			Map.entry("ID", id),
			Map.entry("Prezzo", price),
			Map.entry("Data", dateTime),
			Map.entry("Metodo di Pagamento", paymentMethod),
			Map.entry("IVA", vat),
			Map.entry("Cliente", customer),
			Map.entry("Servizi", services),
			Map.entry("Abilitato", isEnabled)
		);
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return price + " " + dateTime.format(dtf) + " " +
				paymentMethod.toString() + " " + vat.toString() + " " +
				customer.getFullName() + " " + services;
	}

}
