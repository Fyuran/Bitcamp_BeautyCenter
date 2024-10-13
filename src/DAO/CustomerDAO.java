package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Main;

public abstract class CustomerDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<Customer> insertCustomer(Customer obj) {
		String query = "INSERT INTO beauty_centerdb.customer("
				+ "name, surname, is_female, birthday, birthplace, eu_tin, credentials_id, VAT, recipient_code, notes, loyalty_points, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try(PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.isFemale());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setString(6, obj.getEU_TIN().getValue());
			stat.setInt(7, obj.getUserCredentials().getId());
			stat.setString(8, obj.getP_IVA());
			stat.setString(9, obj.getRecipientCode());
			stat.setString(10, obj.getNotes());
			stat.setInt(11, obj.getLoyaltyPoints());
			stat.setBoolean(12, obj.isEnabled());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Customer(id, obj));
			}
			throw new SQLException("Could not retrieve id");
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

	public final static Optional<Customer> getCustomer(int id) {
		String query = "SELECT * FROM beauty_centerdb.customer WHERE id = ?";

		Optional<Customer> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Customer(rs));
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

	public final static List<Customer> getAllCustomers() {
		List<Customer> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.customer";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new Customer(rs));
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

	public final static boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.customer LIMIT 1";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				return false;
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
		return true;
	}

	public final static int updateCustomer(int id, Customer obj) {
		String query = "UPDATE beauty_centerdb.customer "
				+ " name = ?, surname = ?, is_female = ?, birthday = ?, birthplace = ?, "
				+ "eu_tin = ?, credentials_id = ?, VAT = ?, recipient_code = ?, "
				+ "notes = ?, loyalty_points = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {

			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.isFemale());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setString(6, obj.getEU_TIN().getValue());
			stat.setInt(7, obj.getUserCredentials().getId());
			stat.setString(8, obj.getP_IVA());
			stat.setString(9, obj.getRecipientCode());
			stat.setString(10, obj.getNotes());
			stat.setInt(11, obj.getLoyaltyPoints());
			stat.setBoolean(12, obj.isEnabled());

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

	public final static int toggleEnabledCustomer(Customer obj) {
		String query = "UPDATE beauty_centerdb.customer "
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
	public final static int toggleEnabledCustomer(int id) {
		return toggleEnabledCustomer(getCustomer(id).get());
	}

	public final static int deleteCustomer(int id) {
		String query = "DELETE FROM beauty_centerdb.customer WHERE id = ?";

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

	public final static List<Object[]> toTableRowAll() {
		List<Customer> list = getAllCustomers();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
