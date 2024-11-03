package com.bitcamp.centro.estetico.models;

import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stock")
public class Stock implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "current_stock")
    int currentStock;

    @Column(name = "minimum_stock")
    int minimumStock;

    @Column(name = "is_enabled")
    @ColumnDefault(value = "true")
    boolean isEnabled;

    public Stock() {
        this.isEnabled = true;
    }

    public Stock(Map<String, Object> map) {
        this(
            (Long) map.get("ID"),
            (Product) map.get("Prodotto"),
            (int) map.get("Scorta"),
            (int) map.get("Richiesto"),
            (boolean) map.get("Abilitato") 
        );
    }

    public Stock(Product product, int currentStock, int minimumStock) {
        this(null, product, currentStock, minimumStock, true);
    }

    public Stock(Long id, Product product, int currentStock, int minimumStock, boolean isEnabled) {
        this.id = id;
        this.product = product;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
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
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + currentStock;
        result = prime * result + minimumStock;
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
        Stock other = (Stock) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (product == null) {
            if (other.product != null)
                return false;
        } else if (!product.equals(other.product))
            return false;
        if (currentStock != other.currentStock)
            return false;
        if (minimumStock != other.minimumStock)
            return false;
        if (isEnabled != other.isEnabled)
            return false;
        return true;
    }

    @Override
    public Map<String, Object> toTableRow() {
        return Map.ofEntries(
          Map.entry("ID", id),
          Map.entry("Prodotto", product),
          Map.entry("Scorta", currentStock),
          Map.entry("Richiesto", minimumStock),
          Map.entry("Abilitato", isEnabled)  
        );
    }
}
