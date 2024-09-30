package com.centro.estetico.bitcamp;

import java.sql.*;
import java.util.NoSuchElementException;

public class Main {
	private static Connection conn;
	private static BeautyCenter bc;
	
	public Main(String url, String username, String password) {
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
			
			bc = BeautyCenter.getData(1).orElseThrow();
			
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

/*
CREATE TABLE `beauty_centerdb`.`beauty_center` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `operating_office` VARCHAR(100) NOT NULL
  `registered_office` VARCHAR(100) NOT NULL,
  `certified_mail` VARCHAR(100) NULL,
  `phone` VARCHAR(20) NULL,
  `mail` VARCHAR(100) NULL,
  `REA` VARCHAR(10) NOT NULL,
  `P_IVA` VARCHAR(12) NOT NULL,
  `opening_hour` TIME NULL,
  `closing_hour` TIME NULL,
  `is_enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `beauty_centerdb`.`vat` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `amount` FLOAT NOT NULL,
  `is_enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `beauty_centerdb`.`customer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `is_female` TINYINT NULL DEFAULT 0,
  `birthday` DATE NOT NULL,
  `birthplace` VARCHAR(200) NOT NULL,
  `eu_tin` VARCHAR(20) NOT NULL,
  `credentials_id` INT NOT NULL,
  `VAT` VARCHAR(12) NULL,
  `recipient_code` VARCHAR(8) NULL,
  `notes` TEXT NULL,
  `loyalty_points` INT NOT NULL DEFAULT 0,
  `is_enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `transaction` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `price` float NOT NULL,
  `date` datetime NOT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `vat_id` int unsigned DEFAULT NULL,
  `customer_id` int unsigned DEFAULT NULL,
  `beauty_id` int unsigned DEFAULT NULL,
  `services` text NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `transactionFK_idx` (`customer_id`),
  KEY `transactionFK2_idx` (`vat_id`),
  KEY `transactionFK3` (`beauty_id`),
  CONSTRAINT `transactionFK1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `transactionFK2` FOREIGN KEY (`vat_id`) REFERENCES `vat` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `transactionFK3` FOREIGN KEY (`beauty_id`) REFERENCES `beauty_center` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
)
*/