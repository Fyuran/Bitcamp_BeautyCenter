package com.bitcamp.centro.estetico.models;

import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "beauty_center")
public class BeautyCenter implements Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String phone;

	@Column(name = "certified_mail")
	private String certifiedMail;

	private String mail;

	@Column(name = "registered_office")
	private String registeredOffice;

	@Column(name = "operating_office")
	private String operatingOffice;

	private String REA;

	private String P_IVA;

	@OneToMany
	@JoinTable(
		name = "beauty_center_vat",
		joinColumns = @JoinColumn(name = "beauty_center_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "vat_id", referencedColumnName = "id", nullable = false)
	)
	private List<VAT> infoVat;

	@Column(name = "opening_hour")
	private LocalTime openingHour;

	@Column(name = "closing_hour")
	private LocalTime closingHour;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public BeautyCenter() {
		this.isEnabled = true;
	}

	public BeautyCenter(
			Long id, String name, String phone, String certifiedMail,
			String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA,
			LocalTime openingHour, LocalTime closingHour, List<VAT> infoVat) {
		this(id, name, phone, certifiedMail, mail, registeredOffice, operatingOffice, REA, P_IVA, openingHour,
				closingHour, infoVat, true);
	}

	public BeautyCenter(
			String name, String phone, String certifiedMail,
			String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA,
			LocalTime openingHour, LocalTime closingHour, List<VAT> infoVat) {
		this(null, name, phone, certifiedMail, mail, registeredOffice, operatingOffice, REA, P_IVA, openingHour,
				closingHour, infoVat);
	}

	public BeautyCenter(
			Long id, String name, String phone, String certifiedMail,
			String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA,
			LocalTime openingHour, LocalTime closingHour, List<VAT> infoVat, boolean isEnabled) {
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
		this.infoVat = infoVat;
		this.isEnabled = isEnabled;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public Object[] toTableRow() {
		return new Object[] {
				id, name, phone, certifiedMail, mail, registeredOffice, operatingOffice, REA, P_IVA, openingHour,
				closingHour, isEnabled
		};
	}

}
