package com.centro.estetico.test.bitcamp;

import java.sql.SQLException;
import java.sql.Statement;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.VAT;

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
		
		VAT.insertData(vat);
		VAT.insertData(vat2);
		VAT.deleteData(vat2.getId());
		
		VAT vat_get = VAT.getData(vat.getId()).orElseThrow();
		vat_get.setAmount(5);
		
		VAT.updateData(vat_get.getId(), vat_get);	
	}

}
