package com.centro.estetico.test.bitcamp;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Main;

public final class BeautyCenterTest {
	
	public static void main(String[] args) {
		Main main = new Main("jdbc:mysql://localhost:1806", "root", "bitcampPassword");
		
		try (Statement stat = Main.getConnection().createStatement()) {
			
			stat.addBatch("DELETE FROM beauty_centerdb.beauty_center");
			stat.addBatch("ALTER TABLE beauty_centerdb.beauty_center AUTO_INCREMENT = 1");
			stat.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BeautyCenter bc = new BeautyCenter(
				"Test", "044-568912", "testCE@test.com", "test@mail.com", 
				"Via dei Test, 48", "Piazza Test, 666", "3333", "1234424556", LocalTime.of(8, 0), LocalTime.of(20, 0));
		
		BeautyCenter bc2 = new BeautyCenter(
				"DELETEME", "DELETE", "DELETE", "DELETE", 
				"DELETE", "DELETE", "DELETE", "DELETE", LocalTime.of(8, 0), LocalTime.of(20, 0));
		
		BeautyCenter.insertData(bc);
		BeautyCenter.insertData(bc2);
		BeautyCenter.deleteData(bc2.getId());
		
		BeautyCenter bc_get = BeautyCenter.getData(bc.getId()).orElseThrow();
		bc_get.setName("get_Test");
		bc_get.setOpeningHour(LocalTime.of(9, 0));
		
		BeautyCenter.updateData(bc.getId(), bc_get);	
		
	}

}
