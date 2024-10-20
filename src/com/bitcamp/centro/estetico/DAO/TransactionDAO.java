package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Transaction;

public class TransactionDAO implements DAO<Transaction> {

	private TransactionDAO(){}
    private static class SingletonHelper {
        private static TransactionDAO INSTANCE = new TransactionDAO();
    }
	public static TransactionDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Transaction> insert(Transaction obj) {
		String query = "INSERT INTO beauty_centerdb.transaction("
				+ "price, payment_method, datetime,"
				+ "customer_id, vat_id, beauty_id, services, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setBigDecimal(1, obj.getPrice());
			stat.setInt(2, obj.getPaymentMethod().toSQLOrdinal());
			stat.setTimestamp(3, Timestamp.valueOf(obj.getDateTime())); //DATETIME and TIMESTAMP are almost equivalent
			stat.setInt(4, obj.getCustomer().getId());
			stat.setInt(5, obj.get().getId());
			stat.setInt(6, obj.getBeautyCenter().getId());
			stat.setString(7, obj.getServices());
			stat.setBoolean(8, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.of(new Transaction(id, obj));
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
	public List<Transaction> getAll() {
		List<Transaction> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.transaction";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new Transaction(rs));
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
		String query = "SELECT * FROM beauty_centerdb.transaction LIMIT 1";

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
	public Optional<Transaction> get(int id) {
		String query = "SELECT * FROM beauty_centerdb.transaction WHERE id = ?";

		Optional<Transaction> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			Transaction trans = null;
			if(rs.next()) {
				trans = new Transaction(rs);
				opt = Optional.ofNullable(trans);
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
	public int update(int id, Transaction obj) {
		String query = "UPDATE beauty_centerdb.transaction "
				+ " SET price = ?, payment_method = ?, datetime = ?,"
				+ "customer_id = ?, vat_id = ?, beauty_id = ?, services = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			if(obj.getCustomer().getId() <= 0) {
				throw new SQLException("invalid customer id: " + obj.getCustomer().getId());
			}
			if(obj.get().getId() <= 0) {
				throw new SQLException("invalid VAT id: " + obj.get().getId());
			}
			if(obj.getBeautyCenter().getId() <= 0) {
				throw new SQLException("invalid BeautyCenter id: " + obj.getBeautyCenter().getId());
			}

			stat.setBigDecimal(1, obj.getPrice());
			stat.setInt(2, obj.getPaymentMethod().toSQLOrdinal());
			stat.setTimestamp(3, Timestamp.valueOf(obj.getDateTime())); //DATETIME and TIMESTAMP are almost equivalent
			stat.setInt(4, obj.getCustomer().getId());
			stat.setInt(5, obj.get().getId());
			stat.setInt(6, obj.getBeautyCenter().getId());
			stat.setString(7, obj.getServices().toString());
			stat.setBoolean(8, obj.isEnabled());

			stat.setInt(9, id);  //WHERE id = ?

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
	public int toggle(Transaction obj) {
		String query = "UPDATE beauty_centerdb.transaction "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(obj.getId() <= 0) {
				throw new SQLException("invalid id: " + obj.getId());
			}

			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);
			stat.setBoolean(1, toggle);
			stat.setInt(2, obj.getId()); //WHERE id = ?
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
		String query = "DELETE FROM beauty_centerdb.transaction WHERE id = ?";

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
