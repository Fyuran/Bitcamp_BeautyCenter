package com.bitcamp.centro.estetico.models;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bitcamp.centro.estetico.DAO.PrizeDAO;
import com.bitcamp.centro.estetico.DAO.SubscriptionDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;

public class Customer extends User {
	private Subscription subscription; //can be null
	private List<Prize> prizes; //can be null
	private String P_IVA;
	private String recipientCode;
	private int loyaltyPoints;
	
	//User(int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled)
	//UserDetails(String name, String surname, boolean isFemale, LocalDate BoD, String birthplace, String notes)
	public Customer(
			int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,
			
			String P_IVA, String recipientCode, int loyaltyPoints, Subscription subscription, List<Prize> prizes  
		) {
		super(id, details, userCredentials, isEnabled);
		this.subscription = subscription;
		this.prizes = prizes;
		this.P_IVA = P_IVA;
		this.recipientCode = recipientCode;
		
		this.loyaltyPoints = loyaltyPoints;
	}

	public Customer(ResultSet rs) throws SQLException {
		this(
				rs.getInt(1),
				new UserDetails(
					rs.getString(2), rs.getString(3),
					rs.getBoolean(4), rs.getDate(5).toLocalDate(), 
					rs.getString(6), rs.getString(11)
				),
				UserCredentialsDAO.getUserCredentials(rs.getInt(8)).get(),
				rs.getBoolean(13),
				rs.getString(9),
				rs.getString(10),
				rs.getInt(12),
				SubscriptionDAO.getSubscriptionOfCustomer(rs.getInt(1)).orElse(null),
				PrizeDAO.getAllPrizesAssignedToCustomer(rs.getInt(1))
			);
	}
	
	public Customer(UserDetails details, UserCredentials userCredentials, String P_IVA, String recipientCode,
			int loyaltyPoints, Subscription subscription, List<Prize> prizes) {
		this(-1, details, userCredentials, true, P_IVA, recipientCode, loyaltyPoints, subscription, prizes);
	}
	
	public Customer(int id, Customer obj) {
		this(obj.getId(), obj.getDetails(), obj.getUserCredentials(), obj.isEnabled(), obj.P_IVA, obj.recipientCode, obj.loyaltyPoints,
			obj.subscription, obj.prizes
		);
	}

	//Getter e setter
	public Subscription getSubscription() {
		return subscription;
	}
	
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public List<Prize> getPrizes() {
		return prizes;
	}

	public void addPrizes(Prize...prizes) {
		for(Prize prize : prizes) {
			if(prize != null) {
				this.prizes.add(prize);				
			}
		}
	}

	public void removePrizes(Prize...prizes) {
		for(Prize prize : prizes) {
			if(prize != null) {
				this.prizes.remove(prize);
			}
		}
	}
	
	public String getP_IVA() {
		return P_IVA;
	}

	public void setP_IVA(String P_IVA) {
		this.P_IVA = P_IVA;
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

	@Override
	public String toString() {
		return "Customer [subscription=" + subscription + ", prizes=" + prizes + ", P_IVA=" + P_IVA + ", recipientCode="
				+ recipientCode + ", loyaltyPoints=" + loyaltyPoints + ", " + super.toString() + "]";
	}

	public String getFullName() {
		return getName() + " " + getSurname();
	}
	
	public Object[] toTableRow() {
		return new Object[] {
				getId(), isFemale(), getName(), getSurname(), getPhone(), getMail(), getAddress(), getBoD(), getBirthplace(),
				getEU_TIN(), P_IVA, recipientCode, loyaltyPoints, getSubscription(), getNotes(), getIban(), isEnabled()
		};
	}

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
	
}
