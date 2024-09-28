package com.centro.estetico.bitcamp;

import java.sql.*;


public class Main {
	private static Connection conn;
	
	Main(String url, String username, String password) {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(!conn.isClosed()) {
				conn.close();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static void main(String[] args) {
		
	}
}
