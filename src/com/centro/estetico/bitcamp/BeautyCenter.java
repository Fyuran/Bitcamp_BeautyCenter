package com.centro.estetico.bitcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import DAO.VATDao;

public class BeautyCenter {
	private final int id;
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
		infoVat = VATDao.getAllVAT();
	}

	public BeautyCenter(String name, String phone, String certifiedMail,
			String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA,
			LocalTime openingHour, LocalTime closingHour
		) {
		this(
			-1, name, phone, certifiedMail, mail,
			registeredOffice, operatingOffice, REA, P_IVA, openingHour,
			closingHour, true
		);
	}

	public BeautyCenter(int id, BeautyCenter bc) {
		this(
			id, bc.name, bc.phone, bc.certifiedMail, bc.mail, bc.registeredOffice, bc.operatingOffice,
			bc.REA, bc.P_IVA, bc.openingHour, bc.closingHour, bc.isEnabled
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

	/*(String name, String phone, String certifiedMail, String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA,
			LocalTime openingHour, LocalTime closingHour)*/
	public Object[] toTableRow() {
		return new Object[] {
				id, name, phone, certifiedMail, mail, registeredOffice, operatingOffice, REA, P_IVA, openingHour, closingHour
		};
	}

}
