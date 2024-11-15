package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription")
public class Subscription implements Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private SubPeriod subperiod;

	private LocalDate start;

	private LocalDate end;

	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "vat_id", nullable = false)
	private VAT vat;

	private double discount;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(
		name = "subscription_customer",
		joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	)
	private Customer customer;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Subscription() {
		this.isEnabled = true;
	}

	public Subscription(SubPeriod subperiod, LocalDate start, LocalDate end, BigDecimal price, VAT vat,
			double discount, Customer customer) {
		this(null, subperiod, start, end, price, vat, discount, customer, true);
	}

	public Subscription(Long id, SubPeriod subperiod, LocalDate start, LocalDate end, BigDecimal price, VAT vat,
			double discount, Customer customer, boolean isEnabled) {
		this.id = id;
		this.subperiod = subperiod;
		this.start = start;
		this.end = end;
		this.price = price;
		this.vat = vat;
		this.discount = discount;
		this.customer = customer;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubPeriod getSubperiod() {
		return subperiod;
	}

	public void setSubperiod(SubPeriod subperiod) {
		this.subperiod = subperiod;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();

		JButton showCustomers = new JButton("Clienti");
		showCustomers.addActionListener(l -> {
			ModelViewer<Customer> picker = new ModelViewer<>("Clienti",
					ListSelectionModel.SINGLE_SELECTION, getCustomer());
			picker.setVisible(true);
		});

		map.put("ID", id);
		map.put("Prezzo", price);
		map.put("IVA", vat);
		map.put("Periodo", subperiod);
		map.put("Inizio", start);
		map.put("Scadenza", end);
		map.put("Sconto Applicato", discount);
		map.put("Cliente", showCustomers);
		map.put("Abilitato", isEnabled);
		
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subperiod == null) ? 0 : subperiod.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
		long temp;
		temp = Double.doubleToLongBits(discount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Subscription other = (Subscription) obj;
		if (subperiod != other.subperiod)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
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
		if (Double.doubleToLongBits(discount) != Double.doubleToLongBits(other.discount))
			return false;
		return true;
	}
}
