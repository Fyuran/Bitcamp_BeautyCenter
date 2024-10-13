package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.VATDao;

public class Product {
	private final int id;
	private String name;
	private int amount;
	private int minStock;
	private BigDecimal price;
	private VAT vat;
	private ProductCat type;
	private boolean isEnabled;



	private Product(int id, String name, int amount, int minStock, BigDecimal price, VAT vat, ProductCat type,
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

	public Product(String name, int amount, int minStock, BigDecimal price, VAT vat, ProductCat type) {
		this(-1, name, amount, minStock, price, vat, type, true);
	}

	public Product(int id, Product obj) {
		this(id, obj.name, obj.amount, obj.minStock, obj.price, obj.vat, obj.type, obj.isEnabled);
	}

	public Product(ResultSet rs) throws SQLException {
		this(rs.getInt(1), rs.getString(2), rs.getInt(3),
			rs.getInt(4), rs.getBigDecimal(5), VATDao.getVAT(rs.getInt(6)).get(),
			ProductCat.valueOf(rs.getString(7)), rs.getBoolean(8)
		);
	}

	public int getId() {
		return id;
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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public static boolean isNameUnique(String productName) {
		String query="SELECT * FROM beauty_centerdb.product WHERE name=? LIMIT 1";
		String name="";
		Connection conn=Main.getConnection();
		try(PreparedStatement pstmt = conn.prepareStatement(query)){
			pstmt.setString(1, productName);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				name=rs.getString("name");

			}
			System.out.println("Nome cercato: "+productName);
			System.out.println("Nome trovato: "+name);
			return name.equals("");
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", amount=" + amount + ", minStock=" + minStock + ", price="
				+ price + ", vat=" + vat + ", type=" + type + ", isEnabled=" + isEnabled + "]";
	}

	public Object[] toTableRow() {
		return new Object[] {
				id, name, amount, minStock, price, vat, type, isEnabled
		};
	}
}
