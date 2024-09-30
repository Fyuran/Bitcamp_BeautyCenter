package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class Transaction {
	private int id;
	private BigDecimal price;
	private VAT vat;
	private LocalDateTime date;
	private PayMethod paymentMethod;
	private Customer customer;
	private BeautyCenter beautyCenter;
	private String services;
	private boolean isEnabled;
	
	private Transaction(
			int id, BigDecimal price, PayMethod paymentMethod, 
			LocalDateTime date, Customer customer,
			VAT vat, BeautyCenter beautyCenter, String services, boolean isEnabled
			) {
		this.id = id;
		this.price = price;
		this.paymentMethod = paymentMethod;
		this.date = date;
		this.customer = customer;
		this.vat = vat;
		this.beautyCenter = beautyCenter;
		this.services = services;
		this.isEnabled = isEnabled;
	}

	public Transaction(
			BigDecimal price, PayMethod paymentMethod, 
			LocalDateTime date, Customer customer, 
			VAT vat, BeautyCenter beautyCenter, String services
			) {
		this(-1, price, paymentMethod, date, customer, vat, beautyCenter, services, true);
	}
	public Transaction(
			BigDecimal price, PayMethod paymentMethod, 
			LocalDateTime date, Customer customer, 
			VAT vat, BeautyCenter beautyCenter
			) {
		this(-1, price, paymentMethod, date, customer, vat, beautyCenter, "", true);
	}


	public int getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public PayMethod getPaymentMethod() {
		return paymentMethod;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public VAT getVat() {
		return vat;
	}

	public BeautyCenter getBeautyCenter() {
		return beautyCenter;
	}

	public String getServices() {
		return services;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setPaymentMethod(PayMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setVat(VAT vat) {
		this.vat = vat;
	}

	public void setBeautyCenter(BeautyCenter beautyCenter) {
		this.beautyCenter = beautyCenter;
	}

	public void setServices(String services) {
		this.services = services;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	/*
	 * 		int id, BigDecimal price, PayMethod paymentMethod, 
			LocalDateTime date, Customer customer,
			VAT vat, BeautyCenter beautyCenter, boolean isEnabled
	 */
	public static int insertData(Transaction obj) {
		String query = "INSERT INTO beauty_centerdb.transaction("
				+ "price, payment_method, date,"
				+ "customer_id, vat_id, beauty_id, services, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setBigDecimal(1, obj.price);
			stat.setString(2, obj.paymentMethod.getType());
			stat.setTimestamp(3, Timestamp.valueOf(obj.date)); //DATETIME and TIMESTAMP are almost equivalent
			stat.setInt(4, obj.customer.getId());
			stat.setInt(5, obj.vat.getId());
			stat.setInt(6, obj.beautyCenter.getId());
			stat.setString(7, obj.services.toString());
			stat.setBoolean(8, obj.isEnabled);
			
			int insert = stat.executeUpdate();
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				obj.id = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Could not retrieve id");
			}
            
			conn.commit();
			
			return insert;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return -1;
	}

	/*		(
			"beauty_centerdb.transaction("
				+ "id, price, payment_method, date,"
				+ "customer_id, vat_id, beauty_id, services, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
			)
			(
			int id, BigDecimal price, PayMethod paymentMethod, 
			LocalDateTime date, Customer customer,
			VAT vat, BeautyCenter beautyCenter, boolean isEnabled
			)
	*/
	
	public static Optional<Transaction> getData(int id) {
		String query = "SELECT * FROM beauty_centerdb.transaction WHERE id = ?";
		Connection conn = Main.getConnection();
		Optional<Transaction> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			Transaction trans = null;
			if(rs.next()) {
				Customer customer = Customer.getData(rs.getInt(6)).orElseThrow();
				VAT vat = VAT.getData(rs.getInt(5)).orElseThrow();
				BeautyCenter beautyCenter = BeautyCenter.getData(rs.getInt(7)).orElseThrow();
				trans = new Transaction(
					rs.getInt(1), rs.getBigDecimal(2), PayMethod.toEnum(rs.getString(4)),
					rs.getTimestamp(3).toLocalDateTime(),
					customer, vat, beautyCenter, rs.getString(8), rs.getBoolean(9)	
				);
				opt = Optional.ofNullable(trans);				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}


	public static int updateData(int id, Transaction obj) {
		String query = "UPDATE beauty_centerdb.transaction "
				+ " SET price = ?, payment_method = ?, date = ?,"
				+ "customer_id = ?, vat_id = ?, beauty_id = ?, services = ?, is_enabled = ? "
				+ "WHERE id = ?";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			
			stat.setBigDecimal(1, obj.price);
			stat.setString(2, obj.paymentMethod.getType());
			stat.setTimestamp(3, Timestamp.valueOf(obj.date)); //DATETIME and TIMESTAMP are almost equivalent
			stat.setInt(4, obj.customer.getId());
			stat.setInt(5, obj.vat.getId());
			stat.setInt(6, obj.beautyCenter.getId());
			stat.setString(7, obj.services.toString());
			stat.setBoolean(8, obj.isEnabled);
			
			stat.setInt(9, id);  //WHERE id = ?
			
			int exec = stat.executeUpdate();
			conn.commit();
			
			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return -1;
	}


	public static int deleteData(int id) {
		String query = "DELETE FROM beauty_centerdb.transaction WHERE id = ?";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); //WHERE id = ?
			
			int exec = stat.executeUpdate();
			conn.commit();
			
			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return -1;
	}

}
