package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Product(String name, int amount, int minStock, BigDecimal price, double vat, ProductCat type,
			boolean isEnabled) {

		this.name = name;
		this.amount = amount;
		this.minStock = minStock;
		this.price = price;
		this.vat = vat;
		this.type = type;
		this.isEnabled = isEnabled;
	}

	private Product(int id, String name, int amount, int minStock, BigDecimal price, double vat, ProductCat type,
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

	public Product(ResultSet rs) throws SQLException {
		this.id=rs.getInt(1);
		this.name = rs.getString(2);
		this.amount = rs.getInt(3);
		this.minStock = rs.getInt(4);
		this.price = rs.getBigDecimal(5);
		setVatAmountFromId(rs.getInt(6));
		this.type = ProductCat.valueOf(rs.getString(7));
		this.isEnabled = rs.getBoolean(8);

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

	private int getVatId() {
		int vatId = -1;
		String query = "SELECT id FROM beauty_centerdb.vat WHERE amount=? LIMIT 1";
		Connection conn = Main.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setDouble(1, this.vat);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				vatId = rs.getInt("id");
				System.out.println(vatId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return vatId;

	}
	
	private void setVatAmountFromId(int id) {
		String query="SELECT amount FROM beauty_centerdb.vat WHERE id=? LIMIT 1";
		Connection conn = Main.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				 this.vat= rs.getDouble("amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static int insertData(Product product) {
		String query = "INSERT INTO beauty_centerdb.product(name,amount,minimum,price,vat_id,type,is_enabled) "
				+ "VALUES(?,?,?,?,?,?,?)";
		Connection conn = Main.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, product.name);
			pstmt.setInt(2, product.amount);
			pstmt.setInt(3, product.minStock);
			pstmt.setBigDecimal(4, product.price);
			pstmt.setInt(5, product.getVatId());
			//pstmt.setInt(5, 3);
			pstmt.setString(6, product.type.name());
			pstmt.setBoolean(7, product.isEnabled);

			int insert = pstmt.executeUpdate();
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				product.id = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Could not retrieve id");
			}

			conn.commit();

			return insert;
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		}
		return -1;
	}

	public static Optional<Product> getData(int id) {
		String query = "SELECT * FROM beauty_centerdb.product WHERE id = ?";
		Connection conn = Main.getConnection();
		Optional<Product> opt = Optional.empty();
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				opt = Optional.ofNullable(new Product(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	public static List<Product> getAllData() {
		List<Product> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.product";
		Connection conn = Main.getConnection();
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				list.add(new Product(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int updateData(int id, Product product) {
		String query = "UPDATE beauty_centerdb.product "
				+ "SET name = ?, amount = ? , minimum=?,price=?,vat_id=?,type=?,is_enabled=?" + "WHERE id = ?";
		Connection conn = Main.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, product.name);
			pstmt.setInt(2, product.amount);
			pstmt.setInt(3, product.minStock);
			pstmt.setBigDecimal(4, product.price);
			pstmt.setInt(5, product.getVatId() );
			pstmt.setString(6, product.type.getDescription());
			pstmt.setBoolean(7, product.isEnabled);

			pstmt.setInt(8, product.id); // WHERE id = ?

			int exec = pstmt.executeUpdate();
			conn.commit();

			return exec;
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return -1;
	}

	public static int toggleEnabledData(int id) {
		String query = "UPDATE beauty_centerdb.product " + "SET is_enabled = ? " + "WHERE id = ?";
		Connection conn = Main.getConnection();
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			Product product = getData(id).get();
			stat.setBoolean(1, !product.isEnabled); // toggle enable or disable state
			stat.setInt(2, id); // WHERE id = ?
			int exec = stat.executeUpdate();

			conn.commit();

			return exec;
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", amount=" + amount + ", minStock=" + minStock + ", price="
				+ price + ", vat=" + vat + ", type=" + type + ", isEnabled=" + isEnabled + "]";
	}

}
