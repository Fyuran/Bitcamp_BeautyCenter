package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedHashMap;
import java.util.Map;

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

	@Column(name = "expiration")
	private LocalDate expirationDate;

	private PrizeType type;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Prize() {
		this.isEnabled = true;
	}

	public Prize(String name, int threshold, LocalDate expirationDate, PrizeType type) {
		this(null, name, threshold, expirationDate, type, true);
	}

	public Prize(Long id, String name, int threshold, LocalDate expirationDate, PrizeType type,
			boolean isEnabled) {
		this.id = id;
		this.name = name;
		this.threshold = threshold;
		this.expirationDate = expirationDate;
		this.type = type;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + threshold;
		result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Prize other = (Prize) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (threshold != other.threshold)
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (type != other.type)
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		return "Prize [id=" + id + ", name=" + name + ", type=" + type.toString() + ", threshold=" + threshold +
				", expirationDate=" + expirationDate.format(dtf) + ", isEnabled=" + isEnabled + "]";
	}

	@Override
	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("ID", id);
		map.put("Nome", name);
		map.put("Punti Necessari", threshold);
		map.put("Tipo", threshold);
		map.put("Scadenza", expirationDate);
		map.put("Abilitato", isEnabled);

		return map;
	}
}
