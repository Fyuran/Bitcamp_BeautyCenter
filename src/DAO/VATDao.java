package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.VAT;

public class VATDao {
	public static int insertVAT(VAT obj) {
		String query = "INSERT INTO beauty_centerdb.vat(amount, is_enabled) VALUES (?, ?)";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setDouble(1, obj.getAmount());
			stat.setBoolean(2, obj.isEnabled());
			
			int insert = stat.executeUpdate();
			conn.commit();
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				VAT validObj = new VAT(id, obj);
				obj = validObj;
			} else {
				throw new SQLException("Could not retrieve id");
			}
            
			
			return insert;
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	;
		}
		return -1;
	}
	public static Optional<VAT> getVAT(int id) {
		String query = "SELECT * FROM beauty_centerdb.vat WHERE id = ?";
		Connection conn = Main.getConnection();
		Optional<VAT> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new VAT(rs));				
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
	
	public static List<VAT> getAllVAT() {
		List<VAT> list = new ArrayList<>();
		
		String query = "SELECT * FROM beauty_centerdb.vat";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new VAT(rs));
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

	public static int updateVAT(int id, VAT obj) {
		String query = "UPDATE beauty_centerdb.vat "
				+ "SET amount = ?, is_enabled = ? "
				+ "WHERE id = ?";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			
			stat.setDouble(1, obj.getAmount());
			stat.setBoolean(2, obj.isEnabled());
			
			stat.setInt(3, obj.getId()); //WHERE id = ?
			
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
	
	public static int toggleEnabledVAT(int id) {
		String query = "UPDATE beauty_centerdb.vat "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			VAT vat = getVAT(id).get();
			stat.setBoolean(1, !vat.isEnabled()); //toggle enable or disable state
			stat.setInt(2, id); //WHERE id = ?
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
	
	public static int deleteVAT(int id) {
		String query = "DELETE FROM beauty_centerdb.vat WHERE id = ?";
		Connection conn = Main.getConnection();
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
		List<VAT> list = getAllVAT();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}
		
		return data;
	}
}
