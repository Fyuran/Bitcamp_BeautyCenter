package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private BigDecimal price;

	@OneToOne
	@JoinColumn(name = "vat_id", nullable = false)
	private VAT vat;

	private ProductCat type;

	@OneToOne(mappedBy = "product", cascade = CascadeType.MERGE)
	private Stock stock;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Product() {
		this.isEnabled = true;
	}

	public Product(String name, BigDecimal price, VAT vat, ProductCat type) {
		this(null, name, price, vat, type, null, true);
	}

	public Product(Long id, String name, BigDecimal price, VAT vat, ProductCat type, Stock stock,
			boolean isEnabled) {
		this.id = id;
		this.name = name;
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
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
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price="
				+ price + ", vat=" + vat + ", type=" + type + ", isEnabled=" + isEnabled + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
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
		Product other = (Product) obj;
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
		if (type != other.type)
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();

		map.put("ID", id);
		map.put("Nome", name);
		map.put("Tipo", type);
		map.put("Prezzo", price);
		map.put("IVA", vat);
		map.put("Abilitato", isEnabled);

		return map;

	}

}
