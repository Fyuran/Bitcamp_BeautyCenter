package com.bitcamp.centro.estetico.models;

import java.util.Collection;
import java.util.List;

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

	public Customer(
			Long id, UserDetails details, UserCredentials userCredentials,
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		this(id, details, userCredentials, true, P_IVA, recipientCode, loyaltyPoints, subscription, prizes);
	}

	public Customer(
			UserDetails details, UserCredentials userCredentials,
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		this(null, details, userCredentials, P_IVA, recipientCode, loyaltyPoints, subscription, prizes);
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
	public Object[] toTableRow() {
		return new Object[] {
				getId(), getGender(), getName(), getSurname(), getPhone(), getMail(), getAddress(), getBoD(),
				getBirthplace(),
				getEu_tin(), p_iva, recipientCode, loyaltyPoints, getSubscription(), getNotes(), getIban(), isEnabled()
		};
	}

}
