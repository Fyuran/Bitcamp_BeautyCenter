package com.centro.estetico.bitcamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Customer {
	private int id;
	
	public Customer(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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
