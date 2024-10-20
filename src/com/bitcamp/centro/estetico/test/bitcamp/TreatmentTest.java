package com.bitcamp.centro.estetico.test.bitcamp;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.VATDao;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Treatment;

public class TreatmentTest {
	public static void main(String[] args) {
		Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
		
		try (Statement stat = Main.getConnection().createStatement()) {
			
			stat.addBatch("DELETE FROM beauty_centerdb.treatment");
			stat.addBatch("ALTER TABLE beauty_centerdb.treatment AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Treatment(String type, BigDecimal price, VAT vat, Duration duration, List<Product> products, boolean isEnabled)
		Treatment treat1 = new Treatment(
			"Scoreggia", BigDecimal.valueOf(666), VATDao.getVAT(1).get(), Duration.ofMinutes(30), 
			List.of(ProductDAO.getProduct(1).get()), true
		);
		
		Treatment treat2 = new Treatment(
				"Merda", BigDecimal.valueOf(13), VATDao.getVAT(2).get(), Duration.ofMinutes(5), 
				List.of(ProductDAO.getProduct(1).get()), true
			);
		
		treat1 = TreatmentDAO.insertTreatment(treat1).get();
		treat2 = TreatmentDAO.insertTreatment(treat2).get();
		TreatmentDAO.toggleEnabledTreatment(treat2);
		
		Treatment treat_get = TreatmentDAO.getTreatment(treat1.getId()).get();
		treat_get.setPrice(BigDecimal.valueOf(1337));
		treat_get.setDuration(Duration.ofHours(16));
		
		TreatmentDAO.updateTreatment(treat1.getId(), treat_get);	
	}
}
