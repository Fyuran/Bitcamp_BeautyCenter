package com.bitcamp.centro.estetico.test;

import java.sql.SQLException;
import java.sql.Statement;

import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.VAT;

public class VATTest {

	public static void main(String[] args) {
		new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");

		try (Statement stat = Main.getConnection().createStatement()) {

			stat.addBatch("DELETE FROM beauty_centerdb.vat");
			stat.addBatch("ALTER TABLE beauty_centerdb.vat AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		VAT vat = new VAT(22);
		VAT vat2 = new VAT(15);

		vat = VAT_DAO.getInstance().insert(vat).get();
		vat2 = VAT_DAO.getInstance().insert(vat2).get();
		VAT_DAO.getInstance().toggle(vat2);

		VAT vat_get = VAT_DAO.getInstance().get(vat.getId()).orElseThrow();
		vat_get.setAmount(5);

		VAT_DAO.getInstance().update(vat_get.getId(), vat_get);
	}

}
