package com.bitcamp.centro.estetico.test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Treatment;

public class TreatmentTest {
	public static void main(String[] args) {
		new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");

		try (Statement stat = Main.getConnection().createStatement()) {

			stat.addBatch("DELETE FROM beauty_centerdb.treatment");
			stat.addBatch("ALTER TABLE beauty_centerdb.treatment AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Treatment(String type, BigDecimal price, VAT vat, Duration duration, List<Product> products, boolean isEnabled)
		Treatment treat1 = new Treatment(
			"Scoreggia", BigDecimal.valueOf(666), VAT_DAO.getInstance().get(1).get(), Duration.ofMinutes(30),
			List.of(ProductDAO.getInstance().get(1).get()), true
		);

		Treatment treat2 = new Treatment(
				"Merda", BigDecimal.valueOf(13), VAT_DAO.getInstance().get(2).get(), Duration.ofMinutes(5),
				List.of(ProductDAO.getInstance().get(1).get()), true
			);

		treat1 = TreatmentDAO.getInstance().insert(treat1).get();
		treat2 = TreatmentDAO.getInstance().insert(treat2).get();
		TreatmentDAO.getInstance().toggle(treat2);

		Treatment treat_get = TreatmentDAO.getInstance().get(treat1.getId()).get();
		treat_get.setPrice(BigDecimal.valueOf(1337));
		treat_get.setDuration(Duration.ofHours(16));

		TreatmentDAO.getInstance().update(treat1.getId(), treat_get);
	}
}
