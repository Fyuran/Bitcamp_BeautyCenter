package com.bitcamp.centro.estetico.models;

import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vat")
public class VAT implements Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double amount;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public VAT() {
		this.isEnabled = true;
	}

	public VAT(Long id, double amount, boolean isEnabled) {
		this.id = id;
		this.amount = amount;
		this.isEnabled = isEnabled;
	}

	public VAT(Map<String, Object> map) {
		this(
			(Long) map.get("ID"),
			(double) map.get("%"),
			(boolean) map.get("Abilitato")
		);
	}

	public VAT(double amount) {
		this(null, amount, true);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return amount + "%";
	}

	public Map<String, Object> toTableRow() {
		return Map.ofEntries(
			Map.entry("ID", id),
			Map.entry("%", amount),
			Map.entry("Abilitato", isEnabled)
		);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
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
		VAT other = (VAT) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		return true;
	}

}
