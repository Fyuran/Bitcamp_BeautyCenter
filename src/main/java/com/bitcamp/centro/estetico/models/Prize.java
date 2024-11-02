package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prize")
public class Prize implements Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private int threshold;

	private LocalDate expirationDate;

	private PrizeType type;

	private double amount;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Prize() {
		this.isEnabled = true;
	}

	public Prize(Long id, String name, int threshold, LocalDate expirationDate, PrizeType type, double amount) {
		this(id, name, threshold, expirationDate, type, amount, true);
	}

	public Prize(String name, int threshold, LocalDate expirationDate, PrizeType type, double amount) {
		this(null, name, threshold, expirationDate, type, amount);
	}

	public Prize(Long id, String name, int threshold, LocalDate expirationDate, PrizeType type, double amount,
			boolean isEnabled) {
		this.id = id;
		this.name = name;
		this.threshold = threshold;
		this.expirationDate = expirationDate;
		this.type = type;
		this.amount = amount;
		this.isEnabled = isEnabled;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public PrizeType getType() {
		return type;
	}

	public void setType(PrizeType type) {
		this.type = type;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void assignToCustomer(Customer customer) {
		customer.addPrizes(this);
	}

	public int addPointsToCustomer(Customer customer, int points) {
		customer.addLoyaltyPoints(points);
		return customer.getLoyaltyPoints();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + threshold;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Prize other = (Prize) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (threshold != other.threshold)
			return false;
		if (type != other.type)
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		return "Prize [id=" + id + ", name=" + name + ", type=" + type.toString() + ", threshold=" + threshold +
				", expirationDate=" + expirationDate.format(dtf) +
				", amount=" + amount + ", isEnabled=" + isEnabled + "]";
	}

	@Override
	public Object[] toTableRow() {
		return new Object[] {
				id, name, threshold, type, amount, expirationDate, isEnabled
		};
	}
}
