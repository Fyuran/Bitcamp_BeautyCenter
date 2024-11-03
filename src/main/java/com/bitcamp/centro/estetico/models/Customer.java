package com.bitcamp.centro.estetico.models;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bitcamp.centro.estetico.controller.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("C")
public class Customer extends User {

	@OneToOne(mappedBy = "customer", optional = true)
	private Subscription subscription;

	@OneToMany
	@JoinTable(
		name = "customer_prize",
		joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "prize_id", referencedColumnName = "id", nullable = false)
	)
	private List<Prize> prizes;

	private String p_iva;

	@Column(name = "recipient_code")
	private String recipientCode;

	@Column(name = "loyalty_points")
	private int loyaltyPoints;

	public Customer() {
		super();
	}

	public Customer(Map<String, Object> map) {
		this(
			(Long) map.get("ID"), 
			(UserDetails) map.get("Dettagli"), 
			(UserCredentials) map.get("Credenziali"), 
			(boolean) map.get("Abilitato"),
			(String) map.get("P.IVA"), 
			(String) map.get("Codice Destinatario"), 
			(int) map.get("Punti fedeltà"), 
			(Subscription) map.get("Abbonamento"),
			(List<Prize>) map.get("Premi")
		);
	}

	public Customer(
			UserDetails details, UserCredentials userCredentials,
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		this(null, details, userCredentials, true, P_IVA, recipientCode, loyaltyPoints, subscription, prizes);
	}

	public Customer(
			Long id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		super(id, details, userCredentials, isEnabled);
		this.subscription = subscription;
		this.prizes = prizes;
		this.p_iva = P_IVA;
		this.recipientCode = recipientCode;

		this.loyaltyPoints = loyaltyPoints;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public List<Prize> getPrizes() {
		return prizes;
	}

	public void addPrizes(Prize... prizes) {
		for (Prize prize : prizes) {
			this.prizes.add(prize);
		}
	}

	public void addPrizes(Collection<Prize> c) {
		addPrizes(c.toArray(new Prize[c.size()]));
	}

	public void removePrizes(Prize... prizes) {
		for (Prize prize : prizes) {
			this.prizes.remove(prize);
		}
	}

	public void setPrizes(List<Prize> prizes) {
		this.prizes = prizes;
	}

	public String getP_iva() {
		return p_iva;
	}

	public void setP_iva(String P_IVA) {
		this.p_iva = P_IVA;
	}

	public String getRecipientCode() {
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void addLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints += loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	@Override
	public String toString() {
		return getFullName();
	}

	public String getFullName() {
		return getName() + " " + getSurname();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subscription == null) ? 0 : subscription.hashCode());
		result = prime * result + ((prizes == null) ? 0 : prizes.hashCode());
		result = prime * result + ((p_iva == null) ? 0 : p_iva.hashCode());
		result = prime * result + ((recipientCode == null) ? 0 : recipientCode.hashCode());
		result = prime * result + loyaltyPoints;
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
		Customer other = (Customer) obj;
		if (subscription == null) {
			if (other.subscription != null)
				return false;
		} else if (!subscription.equals(other.subscription))
			return false;
		if (prizes == null) {
			if (other.prizes != null)
				return false;
		} else if (!prizes.equals(other.prizes))
			return false;
		if (p_iva == null) {
			if (other.p_iva != null)
				return false;
		} else if (!p_iva.equals(other.p_iva))
			return false;
		if (recipientCode == null) {
			if (other.recipientCode != null)
				return false;
		} else if (!recipientCode.equals(other.recipientCode))
			return false;
		if (loyaltyPoints != other.loyaltyPoints)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		var map = super.toTableRow();
		map.putAll(Map.ofEntries(
			Map.entry("Abbonamento", subscription),
			Map.entry("P.IVA", p_iva),
			Map.entry("Codice Destinatario", recipientCode),
			Map.entry("Punti fedeltà", loyaltyPoints)
		));

		return map;
	}

}
