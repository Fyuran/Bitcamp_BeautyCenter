package com.centro.estetico.bitcamp;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BeautyCenter {
	private int id;
	private String name;
	private String phone;
	private String certifiedMail;
	private String mail;
	private String registeredOffice;
	private String operatingOffice;
	private String REA;
	private String P_IVA;
	private List<VAT> infoVat;
	private LocalTime openingHour;
	private LocalTime closingHour;
	private boolean isEnabled;
	
	private BeautyCenter(
			int id, String name, String phone, String certifiedMail, 
			String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA, 
			LocalTime openingHour, LocalTime closingHour, boolean isEnabled
		) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.certifiedMail = certifiedMail;
		this.mail = mail;
		this.registeredOffice = registeredOffice;
		this.operatingOffice = operatingOffice;
		this.REA = REA;
		this.P_IVA = P_IVA;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
		this.isEnabled = isEnabled;
		infoVat = VAT.getAllData();
	}
	
	public BeautyCenter(String name, String phone, String certifiedMail, String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA, 
			LocalTime openingHour, LocalTime closingHour) {
		this(
			-1, name, phone, certifiedMail, mail, 
			registeredOffice, operatingOffice, 
			REA, P_IVA,
			openingHour, closingHour, true
			);
	}
	
	public BeautyCenter(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1), 
			rs.getString(2), 
			rs.getString(3),
			rs.getString(4), 
			rs.getString(5), 
			rs.getString(6),
			rs.getString(7), 
			rs.getString(8), 
			rs.getString(9),
			rs.getTime(10).toLocalTime(), 
			rs.getTime(11).toLocalTime(),
			rs.getBoolean(12)
		);		

	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getCertifiedMail() {
		return certifiedMail;
	}
	public String getMail() {
		return mail;
	}
	public String getRegisteredOffice() {
		return registeredOffice;
	}
	public String getOperatingOffice() {
		return operatingOffice;
	}
	public String getREA() {
		return REA;
	}
	public String getP_IVA() {
		return P_IVA;
	}
	public List<VAT> getInfoVat() {
		return infoVat;
	}
	public LocalTime getOpeningHour() {
		return openingHour;
	}
	public LocalTime getClosingHour() {
		return closingHour;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setCertifiedMail(String certifiedMail) {
		this.certifiedMail = certifiedMail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public void setRegisteredOffice(String registeredOffice) {
		this.registeredOffice = registeredOffice;
	}
	public void setOperatingOffice(String operatingOffice) {
		this.operatingOffice = operatingOffice;
	}
	public void setREA(String REA) {
		this.REA = REA;
	}
	public void setP_IVA(String P_IVA) {
		this.P_IVA = P_IVA;
	}
	public void setInfoVat(List<VAT> infoVat) {
		this.infoVat = infoVat;
	}
	public void setOpeningHour(LocalTime openingHour) {
		this.openingHour = openingHour;
	}
	public void setClosingHour(LocalTime closingHour) {
		this.closingHour = closingHour;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public static int insertData(BeautyCenter obj) {
		String query = "INSERT INTO beauty_centerdb.beauty_center("
				+ "name, phone, certified_mail,"
				+ "mail, registered_office, operating_office,"
				+ "REA, P_IVA, opening_hour, closing_hour, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setString(1, obj.name);
			stat.setString(2, obj.phone);
			stat.setString(3, obj.certifiedMail);
			stat.setString(4, obj.mail);
			stat.setString(5, obj.registeredOffice);
			stat.setString(6, obj.operatingOffice);
			stat.setString(7, obj.REA);
			stat.setString(8, obj.P_IVA);
			stat.setTime(9, Time.valueOf(obj.openingHour));
			stat.setTime(10, Time.valueOf(obj.closingHour));
			stat.setBoolean(11, obj.isEnabled);
			
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
	
	public static List<BeautyCenter> getAllData() {
		List<BeautyCenter> list = new ArrayList<>();
		
		String query = "SELECT * FROM beauty_centerdb.beauty_center";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				list.add(new BeautyCenter(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Optional<BeautyCenter> getData(int id) {
		String query = "SELECT * FROM beauty_centerdb.beauty_center WHERE id = ?";
		Connection conn = Main.getConnection();
		Optional<BeautyCenter> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				opt = Optional.ofNullable(new BeautyCenter(rs));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	public static int updateData(int id, BeautyCenter obj) {
		String query = "UPDATE beauty_centerdb.beauty_center "
				+ "SET name = ?, phone = ?,"
				+ "certified_mail = ?, mail = ?,"
				+ "registered_office = ?, operating_office = ?,"
				+ "REA = ?, P_IVA = ?,"
				+ "opening_hour = ?, closing_hour = ?, is_enabled = ? "
				+ "WHERE id = ?";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, obj.name);
			stat.setString(2, obj.phone);
			stat.setString(3, obj.certifiedMail);
			stat.setString(4, obj.mail);
			stat.setString(5, obj.registeredOffice);
			stat.setString(6, obj.operatingOffice);
			stat.setString(7, obj.REA);
			stat.setString(8, obj.P_IVA);
			stat.setTime(9, Time.valueOf(obj.openingHour));
			stat.setTime(10, Time.valueOf(obj.closingHour));
			stat.setBoolean(11, obj.isEnabled);
			
			stat.setInt(12, id); //WHERE id = ?
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
		String query = "DELETE FROM beauty_centerdb.beauty_center WHERE id = ?";
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
