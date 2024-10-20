package com.bitcamp.centro.estetico.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;

public class Main {
	private static Connection conn;
	private static BeautyCenter bc;
	
	public Main(String url, String username, String password) {
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
			
			bc = BeautyCenterDAO.getBeautyCenter(1).orElseThrow();
			
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
		if(conn == null)
			throw new NoSuchElementException("connection is null");
		return conn;
	}
	
	public static void main(String[] args) {
		Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
		
	}

	public static BeautyCenter getBeautyCenter() {
		return bc;
	}
}