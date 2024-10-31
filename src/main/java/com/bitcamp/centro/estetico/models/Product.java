package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Model {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private int amount;

	@Column(name = "minimum")
	private int minStock;

	private BigDecimal price;

	@OneToOne
	@JoinColumn(name = "vat_id", nullable = false)
	private VAT vat;

	private ProductCat type;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "false")
	private boolean isEnabled;



	public Product(Long id, String name, int amount, int minStock, BigDecimal price, VAT vat, ProductCat type,
			boolean isEnabled) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.minStock = minStock;
		this.price = price;
		this.vat = vat;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMinStock() {
		return minStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
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

	public ProductCat getType() {
		return type;
	}

	public void setType(ProductCat type) {
		this.type = type;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", amount=" + amount + ", minStock=" + minStock + ", price="
				+ price + ", vat=" + vat + ", type=" + type + ", isEnabled=" + isEnabled + "]";
	}

	// "ID", "Prodotto", "Categoria", "Quantità", "Quantità minima", "Prezzo",
	// "IVA%"
	public Object[] toTableRow() {
		return new Object[] {
				id, name, type, amount, minStock, price, vat
		};
	}
}
