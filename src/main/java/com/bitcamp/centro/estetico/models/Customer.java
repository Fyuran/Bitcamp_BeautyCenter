package com.bitcamp.centro.estetico.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("C")
public class Customer extends User {

	@OneToOne(mappedBy = "customer", cascade = CascadeType.MERGE)
	private Subscription subscription;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
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
		prizes = new ArrayList<>();
	}

	public Customer(
			UserDetails details, UserCredentials userCredentials,
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		this(null, details, userCredentials, true, P_IVA, recipientCode, loyaltyPoints, subscription, prizes);
	}

	public Customer(
			Long id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		super(id, Roles.USER, details, userCredentials, isEnabled);
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
		if(prizes == null) return new ArrayList<>();
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
		int result = super.hashCode();
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
		if (!super.equals(obj))
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
		var superMap = super.toTableRow();

		JButton showPrizes = new JButton("Premi");
		showPrizes.addActionListener(l -> {
			ModelViewer<Prize> picker = new ModelViewer<>("Premi Acquisiti",
					ListSelectionModel.SINGLE_SELECTION, getPrizes());
			picker.setVisible(true);
		});

		JButton showSubscription = new JButton("Abbonamento");
		showSubscription.addActionListener(l -> {
			ModelViewer<Subscription> picker = new ModelViewer<>("Abbonamento",
					ListSelectionModel.SINGLE_SELECTION, getSubscription());
			picker.setVisible(true);
		});

		superMap.put("Abbonamento", showSubscription);
		superMap.put("P.IVA", p_iva);
		superMap.put("Codice Destinatario", recipientCode);
		superMap.put("Punti fedeltà", loyaltyPoints);
		superMap.put("Premi", showPrizes);


		return superMap;
	}

}
