package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.controller.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "treatment")
public class Treatment implements Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;

	private BigDecimal price;

	@OneToOne
	@JoinColumn(name = "vat_id", nullable = false)
	private VAT vat;

	private Duration duration;

	@OneToMany
	@JoinTable(
		name = "treatment_product",
		joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	)
	private List<Product> products;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Treatment() {
		this.isEnabled = true;
	}

	public Treatment(Map<String, Object> map) {
		this(
			(Long) map.get("ID"),
			(String) map.get("Tipo"),
			(BigDecimal) map.get("Prezzo"),
			(VAT) map.get("IVA"),
			(Duration) map.get("Durata"),
			(List<Product>) map.get("Prodotti"),
			(boolean) map.get("Abilitato")
		);
	}

	public Treatment(Long id, String type, BigDecimal price, VAT vat, Duration duration, List<Product> products) {
		this(id, type, price, vat, duration, products, true);
	}

	public Treatment(Long id, String type, BigDecimal price, VAT vat, Duration duration, List<Product> products,
			boolean isEnabled) {
		this.id = id;
		this.type = type;
		this.price = price;
		this.vat = vat;
		this.duration = duration;
		this.products = products;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void addProducts(Product... products) {
		for (Product product : products) {
			this.products.add(product);
		}
	}

	public void removeProducts(Product... products) {
		for (Product product : products) {
			this.products.remove(product);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
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
		Treatment other = (Treatment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
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
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		return Map.ofEntries(
			Map.entry("ID", id),
			Map.entry("Tipo", type),
			Map.entry("Prezzo", price),
			Map.entry("IVA", vat),
			Map.entry("Durata", duration),
			Map.entry("Abilitato", isEnabled)
		);
	}

}
