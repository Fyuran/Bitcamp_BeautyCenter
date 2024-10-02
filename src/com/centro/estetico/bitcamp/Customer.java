package com.centro.estetico.bitcamp;

import java.sql.Connection;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Customer extends User{
	
	private String EU_TIN;//codice fiscale
	private Subscription subscription;
	private ArrayList<Prize> awards;
	private String VAT_ID;//partita iva 
	private String recipientCode;
	
	
	public Customer(int id, String name, String surname, String birthplace, boolean isFemale, LocalDate BoD,String notes,boolean isEnabled,String EU_TIN,Subscription subscription,ArrayList<Prize> awards,String VAT_ID,String recipientCode) {
		super(id, name, surname, birthplace, isFemale, BoD, notes, isEnabled);
		this.EU_TIN=EU_TIN;
		this.subscription=subscription;
		this.awards=new ArrayList<>();
		this.VAT_ID=VAT_ID;
		this.recipientCode=recipientCode;
	}
	
	
	//Metodi getter
	public String getEU_TIN() {
		return EU_TIN;
	}



	public void setEU_TIN(String EU_TIN) {
		this.EU_TIN = EU_TIN;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public ArrayList<Prize> getAwards() {
		return awards;
	}

	public void setAwards(ArrayList<Prize> awards) {
		this.awards = awards;
	}

	public String getVAT_ID() {
		return VAT_ID;
	}

	public void setVAT_ID(String vAT_ID) {
		VAT_ID = vAT_ID;
	}

	public String getRecipientCode() {
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}
	
	
	//Metodo ToString
	@Override
	public String toString() {
		return "Customer [EU_TIN=" + EU_TIN + ", VAT_ID=" + VAT_ID + ", recipientCode=" + recipientCode + ", getId()="
				+ getId() + ", getName()=" + getName() + ", getSurname()=" + getSurname() + ", getBirthplace()="
				+ getBirthplace() + ", GetIsFemale()=" + GetIsFemale() + ", getBoD()=" + getBoD() + ", getNotes()="
				+ getNotes() + ", GetIsEnabled()=" + GetIsEnabled() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}
	
	
	

	public static Optional<Customer> getData(int id) {
		String query = "SELECT * FROM beauty_centerdb.customer WHERE id = ?";
		Connection conn = Main.getConnection();
		Optional<Customer> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				Customer customer = new Customer(rs.getInt(1));
				opt = Optional.ofNullable(customer);				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
}
