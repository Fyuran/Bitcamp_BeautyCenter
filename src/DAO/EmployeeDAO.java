package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;

public abstract class EmployeeDAO {
	private static Connection conn = Main.getConnection();
	
	public static Optional<Employee> insertEmployee(Employee obj) {
		String query = "INSERT INTO beauty_centerdb.employee("
				+ "name, surname, is_female, birthday, birthplace, role, hired, termination, credentials_id, notes, is_enabled, serial) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.isFemale());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setInt(6, obj.getRole().toSQLOrdinal());
			stat.setDate(7, Date.valueOf(obj.getHiredDate()));
			stat.setDate(8, Date.valueOf(obj.getTerminationDate()));
			stat.setInt(9, obj.getUserCredentials().getId());
			stat.setString(10, obj.getNotes());
			stat.setBoolean(11, obj.isEnabled());
			stat.setLong(12, obj.getEmployeeSerial());
			
			stat.executeUpdate();
			conn.commit();
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Employee(id, obj));
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
	
	public static Optional<Employee> getEmployee(int id) {
		String query = "SELECT * FROM beauty_centerdb.employee WHERE id = ?";
		
		Optional<Employee> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Employee(rs));				
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

	public static List<Employee> getAllEmployees() {
		List<Employee> list = new ArrayList<>();
		
		String query = "SELECT * FROM beauty_centerdb.employee";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new Employee(rs));
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
	
	public static int updateEmployee(int id, Employee obj) {
		String query = "UPDATE `beauty_centerdb`.`employee` "
				+ "SET `name` = ?, `surname` = ?, "
				+ "`is_female` = ?, `birthday` = ?, `birthplace` = ?, "
				+ "`role` = ?, `hired` = ?, `termination` = ?, "
				+ "`credentials_id` = ?, `notes` = ?, `is_enabled` = ?, `serial` = ? "
				+ "WHERE `id` = ?;";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.isFemale());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setInt(6, obj.getRole().toSQLOrdinal());
			stat.setDate(7, Date.valueOf(obj.getHiredDate()));
			stat.setDate(8, Date.valueOf(obj.getTerminationDate()));
			stat.setInt(9, obj.getUserCredentials().getId());
			stat.setString(10, obj.getNotes());
			stat.setBoolean(11, obj.isEnabled());
			stat.setLong(12, obj.getEmployeeSerial());
			
			stat.setInt(13, id);  //WHERE id = ?
			
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

	public static int toggleEnabledEmployee(Employee obj) {
		String query = "UPDATE beauty_centerdb.employee "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
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
	
	public static int toggleEnabledEmployee(int id) {
		return toggleEnabledEmployee(getEmployee(id).get());
	}
	
	
	public static int deleteEmployee(int id) {
		String query = "DELETE FROM beauty_centerdb.employee WHERE id = ?";
		
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
		List<Employee> list = getAllEmployees();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}
		
		return data;
	}
}
