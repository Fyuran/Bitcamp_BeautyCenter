package com.bitcamp.centro.estetico.models;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.controller.DAO;

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

	/*		Map.entry("ID", id),
			Map.entry("Nome", name),
			Map.entry("Telefono", phone),
			Map.entry("Email", mail),
			Map.entry("PEC", certifiedMail),
			Map.entry("Sede legale", registeredOffice),
			Map.entry("Sede operativa", operatingOffice),
			Map.entry("REA", REA),
			Map.entry("P.IVA", P_IVA),
			Map.entry("Apertura", openingHour),
			Map.entry("Chiusura", closingHour),
			Map.entry("Abilitato", isEnabled) 
	*/
	public BeautyCenter(Map<String, Object> map) {
		this(
			(Long) map.get("ID"), 
			(String) map.get("Nome"),  
			(String) map.get("Telefono"),
			(String) map.get("PEC"), 
			(String) map.get("Email"), 
			(String) map.get("Sede legale"),
			(String) map.get("Sede operativa"), 
			(String) map.get("REA"), 
			(String) map.get("P.IVA"), 
			(LocalTime) map.get("Apertura"),
			(LocalTime) map.get("Chiusura"), 
			(List<VAT>) map.getOrDefault("IVA", DAO.getAll(VAT.class)), 
			(boolean) map.get("Abilitato")
		);
	}

	public BeautyCenter(
			String name, String phone, String certifiedMail,
			String mail, String registeredOffice,
			String operatingOffice, String REA, String P_IVA,
			LocalTime openingHour, LocalTime closingHour, List<VAT> infoVat) {
		this(null, name, phone, certifiedMail, mail, registeredOffice, operatingOffice, REA, P_IVA, openingHour,
				closingHour, infoVat, true);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((certifiedMail == null) ? 0 : certifiedMail.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((registeredOffice == null) ? 0 : registeredOffice.hashCode());
		result = prime * result + ((operatingOffice == null) ? 0 : operatingOffice.hashCode());
		result = prime * result + ((REA == null) ? 0 : REA.hashCode());
		result = prime * result + ((P_IVA == null) ? 0 : P_IVA.hashCode());
		result = prime * result + ((infoVat == null) ? 0 : infoVat.hashCode());
		result = prime * result + ((openingHour == null) ? 0 : openingHour.hashCode());
		result = prime * result + ((closingHour == null) ? 0 : closingHour.hashCode());
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
		BeautyCenter other = (BeautyCenter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (certifiedMail == null) {
			if (other.certifiedMail != null)
				return false;
		} else if (!certifiedMail.equals(other.certifiedMail))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (registeredOffice == null) {
			if (other.registeredOffice != null)
				return false;
		} else if (!registeredOffice.equals(other.registeredOffice))
			return false;
		if (operatingOffice == null) {
			if (other.operatingOffice != null)
				return false;
		} else if (!operatingOffice.equals(other.operatingOffice))
			return false;
		if (REA == null) {
			if (other.REA != null)
				return false;
		} else if (!REA.equals(other.REA))
			return false;
		if (P_IVA == null) {
			if (other.P_IVA != null)
				return false;
		} else if (!P_IVA.equals(other.P_IVA))
			return false;
		if (infoVat == null) {
			if (other.infoVat != null)
				return false;
		} else if (!infoVat.equals(other.infoVat))
			return false;
		if (openingHour == null) {
			if (other.openingHour != null)
				return false;
		} else if (!openingHour.equals(other.openingHour))
			return false;
		if (closingHour == null) {
			if (other.closingHour != null)
				return false;
		} else if (!closingHour.equals(other.closingHour))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		return Map.ofEntries(
			Map.entry("ID", id),
			Map.entry("Nome", name),
			Map.entry("Telefono", phone),
			Map.entry("Email", mail),
			Map.entry("PEC", certifiedMail),
			Map.entry("Sede legale", registeredOffice),
			Map.entry("Sede operativa", operatingOffice),
			Map.entry("REA", REA),
			Map.entry("P.IVA", P_IVA),
			Map.entry("Apertura", openingHour),
			Map.entry("Chiusura", closingHour),
			Map.entry("Abilitato", isEnabled)
		);
	}

}
