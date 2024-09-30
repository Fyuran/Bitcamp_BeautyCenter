package com.centro.estetico.bitcamp;

import java.sql.Connection;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Customer extends User{
	private int id;
	private String EU_TIN;//codice fiscale
	private Subscription subscription;
	private ArrayList<Prize> awards;
	private String VAT_ID;//partita iva
	private String recipientCode;
	
	
	//La questione id ci serve?
	public Customer(int id) {
		this.id = id;
		
	}
	
	public Customer(int id, String name, String surname, LocalDate BoD, UserCredentials credentials, String notes, String EU_TIN, Subscription subscription, String VAT_ID, String recipientCode) {
		super(id, name, surname, BoD, credentials, notes);
		this.EU_TIN=EU_TIN;
		this.subscription=subscription;
		this.awards=new ArrayList<>();
		this.VAT_ID=VAT_ID;
		this.recipientCode=recipientCode;
	}
	
	//costruttore con soltanto i dati not null nel database
	public Customer(String name, String surname) {
		super(name, surname);
	}

	public int getId() {
		return id;
	}
	
	

	public String getEU_TIN() {
		return EU_TIN;
	}

	public void setEU_TIN(String eU_TIN) {
		EU_TIN = eU_TIN;
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

	public void setId(int id) {
		this.id = id;
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
