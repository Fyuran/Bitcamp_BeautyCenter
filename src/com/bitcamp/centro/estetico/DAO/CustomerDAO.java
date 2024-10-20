package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Customer;

public class CustomerDAO implements DAO<Customer>{

	private CustomerDAO(){}
    private static class SingletonHelper {
        private static final CustomerDAO INSTANCE = new CustomerDAO();
    }
	public static CustomerDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Customer> insert(Customer obj) {
		String query = "INSERT INTO beauty_centerdb.customer("
				+ "name, surname, is_female, birthday, birthplace, eu_tin, credentials_id, VAT, recipient_code, notes, loyalty_points, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			int UserCredentialsId = obj.getUserCredentials().getId();
			if(UserCredentialsId <= 0) {
				throw new SQLException("invalid UserCredentialsId: " + UserCredentialsId);
			}
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.getGender().toBoolean());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setString(6, obj.getEU_TIN().getValue());
			stat.setInt(7, UserCredentialsId);
			stat.setString(8, obj.getP_IVA());
			stat.setString(9, obj.getRecipientCode());
			stat.setString(10, obj.getNotes());
			stat.setInt(11, obj.getLoyaltyPoints());
			stat.setBoolean(12, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Customer(id, obj));
			}
			throw new SQLException("Could not retrieve id");
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Customer> get(int id) {
		String query = "SELECT * FROM beauty_centerdb.customer WHERE id = ?";

		Optional<Customer> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Customer(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return opt;
	}

	@Override
	public List<Customer> getAll() {
		List<Customer> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.customer";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new Customer(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.customer LIMIT 1";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	public int update(int id, Customer obj) {
		String query = "UPDATE beauty_centerdb.customer SET "
				+ "name = ?, surname = ?, is_female = ?, birthday = ?, birthplace = ?, "
				+ "eu_tin = ?, credentials_id = ?, VAT = ?, recipient_code = ?, "
				+ "notes = ?, loyalty_points = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			int UserCredentialsId = obj.getUserCredentials().getId();
			if(UserCredentialsId <= 0) {
				throw new SQLException("invalid UserCredentialsId: " + UserCredentialsId);
			}

			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.getGender().toBoolean());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setString(6, obj.getEU_TIN().getValue());
			stat.setInt(7, UserCredentialsId);
			stat.setString(8, obj.getP_IVA());
			stat.setString(9, obj.getRecipientCode());
			stat.setString(10, obj.getNotes());
			stat.setInt(11, obj.getLoyaltyPoints());
			stat.setBoolean(12, obj.isEnabled());

			stat.setInt(13, id);  //WHERE id = ?

			int exec = stat.executeUpdate();
			getConnection().commit();

			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return -1;
	}

	@Override
	public int toggle(Customer obj) {
		String query = "UPDATE beauty_centerdb.customer "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			int id = obj.getId();
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);
			stat.setBoolean(1, toggle);
			stat.setInt(2, id); //WHERE id = ?
			int exec = stat.executeUpdate();

			getConnection().commit();

			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return -1;
	}
	@Override
	public int toggle(int id) {
		return toggle(get(id).get());
	}

	@Override
	public int delete(int id) {
		String query = "DELETE FROM beauty_centerdb.customer WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setInt(1, id); //WHERE id = ?

			int exec = stat.executeUpdate();
			getConnection().commit();

			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return -1;
	}
}
