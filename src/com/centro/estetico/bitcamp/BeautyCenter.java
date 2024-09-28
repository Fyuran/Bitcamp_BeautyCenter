package com.centro.estetico.bitcamp;

import java.sql.*;
import java.time.LocalTime;
import java.util.List;

public class BeautyCenter implements CRUDSQL {
	private int id = -1;
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
	@Override
	public int insertData() {
		String query = "INSERT INTO beauty_center.beauty_center("
				+ "name, "
				+ "phone, "
				+ "certified_mail, "
				+ "mail, "
				+ "registered_office"
				+ "operating_office"
				+ "REA"
				+ "P_IVA"
				+ "opening_hour"
				+ "closing_hour"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setString(1, name);
			stat.setString(2, phone);
			stat.setString(3, certifiedMail);
			stat.setString(4, mail);
			stat.setString(5, registeredOffice);
			stat.setString(6, registeredOffice);
	
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {			
			}
            
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public ResultSet getData() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int updateData() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int deleteData() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
