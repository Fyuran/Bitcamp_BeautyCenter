package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Main;

public abstract class BeautyCenterDAO {
	private static Connection conn = Main.getConnection();
	
	public static Optional<BeautyCenter> insertBeautyCenter(BeautyCenter obj) {
		String query = "INSERT INTO beauty_centerdb.beauty_center("
				+ "name, phone, certified_mail,"
				+ "mail, registered_office, operating_office,"
				+ "REA, P_IVA, opening_hour, closing_hour, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getPhone());
			stat.setString(3, obj.getCertifiedMail());
			stat.setString(4, obj.getMail());
			stat.setString(5, obj.getRegisteredOffice());
			stat.setString(6, obj.getOperatingOffice());
			stat.setString(7, obj.getREA());
			stat.setString(8, obj.getP_IVA());
			stat.setTime(9, Time.valueOf(obj.getOpeningHour()));
			stat.setTime(10, Time.valueOf(obj.getClosingHour()));
			stat.setBoolean(11, obj.isEnabled());
			
			stat.executeUpdate();
			conn.commit();
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new BeautyCenter(id, obj));
			} else {
				throw new SQLException("Could not retrieve id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return Optional.empty();
	}
	
	public static List<BeautyCenter> getAllBeautyCenters() {
		List<BeautyCenter> list = new ArrayList<>();
		
		String query = "SELECT * FROM beauty_centerdb.beauty_center";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new BeautyCenter(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return list;
	}
	
	public static Optional<BeautyCenter> getBeautyCenter(int id) {
		String query = "SELECT * FROM beauty_centerdb.beauty_center WHERE id = ?";
		
		Optional<BeautyCenter> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new BeautyCenter(rs));
			}

		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return opt;
	}
	
	public static int updateBeautyCenter(int id, BeautyCenter obj) {
		String query = "UPDATE beauty_centerdb.beauty_center "
				+ "SET name = ?, phone = ?,"
				+ "certified_mail = ?, mail = ?,"
				+ "registered_office = ?, operating_office = ?,"
				+ "REA = ?, P_IVA = ?,"
				+ "opening_hour = ?, closing_hour = ?, is_enabled = ? "
				+ "WHERE id = ?";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getPhone());
			stat.setString(3, obj.getCertifiedMail());
			stat.setString(4, obj.getMail());
			stat.setString(5, obj.getRegisteredOffice());
			stat.setString(6, obj.getOperatingOffice());
			stat.setString(7, obj.getREA());
			stat.setString(8, obj.getP_IVA());
			stat.setTime(9, Time.valueOf(obj.getOpeningHour()));
			stat.setTime(10, Time.valueOf(obj.getClosingHour()));
			stat.setBoolean(11, obj.isEnabled());
			
			stat.setInt(12, id); //WHERE id = ?
			int exec = stat.executeUpdate();
			
			conn.commit();
			
			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return -1;
	}
	
	public static int toggleEnabledBeautyCenter(int id) {
		String query = "UPDATE beauty_centerdb.beauty_center "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			BeautyCenter obj = getBeautyCenter(id).get();
			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);
			stat.setBoolean(1, toggle); 
			stat.setInt(2, obj.getId()); //WHERE id = ?
			int exec = stat.executeUpdate();
			
			conn.commit();
			
			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return -1;
	}
	public static int toggleEnabledBeautyCenter(BeautyCenter obj) {
		return toggleEnabledBeautyCenter(obj.getId());
	}
	
	public static int deleteBeautyCenter(int id) {
		String query = "DELETE FROM beauty_centerdb.beauty_center WHERE id = ?";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); //WHERE id = ?
			
			int exec = stat.executeUpdate();
			conn.commit();
			
			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		}
		return -1;
	}
	
	public static List<Object[]> toTableRowAll() {
		List<BeautyCenter> list = getAllBeautyCenters();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}
		
		return data;
	}
}
