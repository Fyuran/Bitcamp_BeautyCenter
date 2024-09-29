package com.centro.estetico.test.bitcamp;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.PayMethod;
import com.centro.estetico.bitcamp.Transaction;
import com.centro.estetico.bitcamp.VAT;

public final class TransactionTest {

	public static void main(String[] args) {
		
		
		Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
		
		try (Statement stat = Main.getConnection().createStatement()) {
			
			stat.addBatch("DELETE FROM beauty_centerdb.transaction");
			stat.addBatch("ALTER TABLE beauty_centerdb.transaction AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BeautyCenter bc = BeautyCenter.getData(1);
		Customer c1 = Customer.getData(1);
		VAT v1 = VAT.getData(1);
		
		Customer c2 = Customer.getData(2);
		VAT v2 = VAT.getData(2);
		
		/*
		 * 
		 * (
			BigDecimal price, PayMethod paymentMethod, 
			LocalDateTime date, Customer customer, 
			VAT vat, BeautyCenter beautyCenter, *String services
			)
		 */
		Transaction trans1 = new Transaction(BigDecimal.valueOf(50), PayMethod.CURRENCY, LocalDateTime.now(), c1, v1, bc);
		
		Transaction trans2 = new Transaction(BigDecimal.valueOf(100), PayMethod.CURRENCY, LocalDateTime.now(), c2, v2, bc);
		
		Transaction.insertData(trans1);
		Transaction.insertData(trans2);
		Transaction.deleteData(trans2.getId());
		
		Transaction tr_get = Transaction.getData(trans1.getId());
		tr_get.setDate(LocalDateTime.of(2024, 9, 29, 15, 0));
		tr_get.setPrice(BigDecimal.valueOf(300));
		
		Transaction.updateData(tr_get.getId(), tr_get);	

	}

}
