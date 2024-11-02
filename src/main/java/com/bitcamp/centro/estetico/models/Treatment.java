package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

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

	public Treatment(String type, BigDecimal price, VAT vat, Duration duration, List<Product> products) {
		this(null, type, price, vat, duration, products);
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

	public Object[] toTableRow() {
		return new Object[] { id, type, price, vat, duration, isEnabled };
	}

}
