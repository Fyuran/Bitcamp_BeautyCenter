package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "treatment")
public class Treatment implements Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;

	private BigDecimal price;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "vat_id", nullable = false)
	private VAT vat;

	private LocalTime duration;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
		products = new ArrayList<>();
	}

	public Treatment(String type, BigDecimal price, VAT vat, LocalTime duration, List<Product> products) {
		this(null, type, price, vat, duration, products, true);
	}

	public Treatment(Long id, String type, BigDecimal price, VAT vat, LocalTime duration, List<Product> products,
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

	public LocalTime getTime() {
		return duration;
	}

	public void setTime(LocalTime duration) {
		this.duration = duration;
	}

	public List<Product> getProducts() {
		if(products == null) return new ArrayList<>();
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
		Map<String, Object> map = new LinkedHashMap<>();

		JButton showProducts = new JButton("Prodotti");
        showProducts.addActionListener(l -> {
            ModelViewer<Product> picker = new ModelViewer<>("Prodotti",
                    ListSelectionModel.SINGLE_SELECTION, getProducts());
            picker.setVisible(true);
        });

		map.put("ID", id);
		map.put("Tipo", type);
		map.put("Prezzo", price);
		map.put("Prodotti", showProducts);
		map.put("IVA", vat);
		map.put("Durata", duration);
		map.put("Abilitato", isEnabled);
		
		return map;
	}

}
