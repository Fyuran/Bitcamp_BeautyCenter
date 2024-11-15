package com.bitcamp.centro.estetico.models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinTable(
		name = "stock_product",
		joinColumns = @JoinColumn(name = "stock_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	)
    private Product product;

    @Column(name = "current_stock")
    private int currentStock;

    @Column(name = "minimum_stock")
    private int minimumStock;

    @Column(name = "is_enabled")
    @ColumnDefault(value = "true")
    private boolean isEnabled;

    public Stock() {
        this.isEnabled = true;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
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
        Map<String, Object> map = new LinkedHashMap<>();

        JButton showProducts = new JButton("Prodotti");
        showProducts.addActionListener(l -> {
            ModelViewer<Product> picker = new ModelViewer<>("Prodotti",
                    ListSelectionModel.SINGLE_SELECTION, getProduct());
            picker.setVisible(true);
        });

        map.put("ID", id);
        map.put("Prodotto", product);
        map.put("Scorta", currentStock);
        map.put("Richiesto", minimumStock);
        map.put("Abilitato", isEnabled);

        return map;
    }
}
