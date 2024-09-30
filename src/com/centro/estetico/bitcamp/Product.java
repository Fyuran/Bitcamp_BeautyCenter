package com.centro.estetico.bitcamp;

import java.math.BigDecimal;

public class Product {
	private int id;
	private String name;
	private int amount;
	private int minStock;
	private BigDecimal price;
	private double vat;
	private ProductCat type;
	private boolean isEnabled;
	
	
	
	public Product() {
		
	}

	public Product(int id, String name, int amount, int minStock, BigDecimal price, double vat, ProductCat type,
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	public double getVat() {
		return vat;
	}
	
	public void setVat(double vat) {
		this.vat = vat;
	}
	
	public ProductCat getType() {
		return type;
	}
	
	public void setType(ProductCat type) {
		this.type = type;
	}
	
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
	
}
