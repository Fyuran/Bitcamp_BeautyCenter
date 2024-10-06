package com.centro.estetico.test.bitcamp;

import java.sql.SQLException;
import java.sql.Statement;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.VAT;

import DAO.VATDao;

public class VATTest {

	public static void main(String[] args) {
		Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
		
		try (Statement stat = Main.getConnection().createStatement()) {
			
			stat.addBatch("DELETE FROM beauty_centerdb.vat");
			stat.addBatch("ALTER TABLE beauty_centerdb.vat AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		VAT vat = new VAT(22);
		VAT vat2 = new VAT(15);
		
		vat = VATDao.insertVAT(vat).get();
		vat2 = VATDao.insertVAT(vat2).get();
		VATDao.toggleEnabledVAT(vat2);
		
		VAT vat_get = VATDao.getVAT(vat.getId()).orElseThrow();
		vat_get.setAmount(5);
		
		VATDao.updateVAT(vat_get.getId(), vat_get);	
	}

}
