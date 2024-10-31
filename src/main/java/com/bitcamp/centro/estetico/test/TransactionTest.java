package com.bitcamp.centro.estetico.test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.DAO.TransactionDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.models.*;

public final class TransactionTest {

	public static void main(String[] args) {


		new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");

		try (Statement stat = Main.getConnection().createStatement()) {

			stat.addBatch("DELETE FROM beauty_centerdb.transaction");
			stat.addBatch("ALTER TABLE beauty_centerdb.transaction AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		BeautyCenter bc = BeautyCenterDAO.getInstance().getFirst().orElseThrow();
		Customer c1 = CustomerDAO.getInstance().get(1).orElseThrow();
		VAT v1 = VAT_DAO.getInstance().get(1).orElseThrow();

		Customer c2 = CustomerDAO.getInstance().get(2).orElseThrow();
		VAT v2 = VAT_DAO.getInstance().get(2).orElseThrow();

		/*
		 *
		 * (
			BigDecimal price, PayMethod paymentMethod,
			LocalDateTime date, Customer customer,
			VAT vat, BeautyCenter beautyCenter, *String services
			)
		 */
		Transaction trans1 = new Transaction(BigDecimal.valueOf(50), PayMethod.CURRENCY, LocalDateTime.now(), c1, v1, bc);

		Transaction trans2 = new Transaction(BigDecimal.valueOf(100), PayMethod.CARD, LocalDateTime.now(), c2, v2, bc);

		trans1 = TransactionDAO.getInstance().insert(trans1).get();
		trans2 = TransactionDAO.getInstance().insert(trans2).get();
		TransactionDAO.getInstance().toggle(trans2);

		Transaction tr_get = TransactionDAO.getInstance().get(trans1.getId()).orElseThrow();
		tr_get.setDateTime(LocalDateTime.of(2024, 9, 29, 15, 0));
		tr_get.setPrice(BigDecimal.valueOf(300));
		tr_get.setPaymentMethod(PayMethod.CARD);

		TransactionDAO.getInstance().update(tr_get.getId(), tr_get);

	}

}
