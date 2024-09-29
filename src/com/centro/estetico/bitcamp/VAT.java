package com.centro.estetico.bitcamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VAT {
	private int id;
	private double amount;
	private boolean isEnabled;
	
	private VAT(int id, double amount, boolean isEnabled) {
		this.id = id;
		this.amount = amount;
		this.isEnabled = isEnabled;
	}
	
	public VAT(double amount, boolean isEnabled) {
		this(-1, amount, isEnabled);
	}
	
	public VAT(double amount) {
		this(-1, amount, true);
	}

	public int getId() {
		return id;
	}
	public double getAmount() {
		return amount;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public static int insertData(VAT obj) {
		String query = "INSERT INTO beauty_centerdb.vat(amount, is_enabled) VALUES (?, ?)";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setDouble(1, obj.amount);
			stat.setBoolean(2, obj.isEnabled);
			
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
			}	;
		}
		return -1;
	}
	public static VAT getData(int id) {
		String query = "SELECT * FROM beauty_centerdb.vat WHERE id = ?";
		Connection conn = Main.getConnection();
		Optional<VAT> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				VAT vat = new VAT(
						rs.getInt(1), rs.getDouble(2), rs.getBoolean(3)
						);
				opt = Optional.ofNullable(vat);				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return opt.orElseThrow();
	}
	
	public static List<VAT> getAllVAT() {
		List<VAT> list = new ArrayList<>();
		
		String query = "SELECT * FROM beauty_centerdb.vat";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				list.add(new VAT(
						rs.getInt(1), rs.getDouble(2), rs.getBoolean(3)
				));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int updateData(int id, VAT obj) {
		String query = "UPDATE beauty_centerdb.vat "
				+ "SET amount = ?, is_enabled = ? "
				+ "WHERE id = ?";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			
			stat.setDouble(1, obj.amount);
			stat.setBoolean(2, obj.isEnabled);
			
			stat.setInt(3, obj.id); //WHERE id = ?
			
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
		String query = "DELETE FROM beauty_centerdb.vat WHERE id = ?";
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
