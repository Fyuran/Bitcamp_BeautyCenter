package com.bitcamp.centro.estetico.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Prize;

public class PrizeDAO implements DAO<Prize> {

	private PrizeDAO(){}
    private static class SingletonHelper {
        private static PrizeDAO INSTANCE = new PrizeDAO();
    }
	public static PrizeDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Prize> insert(Prize obj) {
		String query = "INSERT INTO beauty_centerdb.prize(name, threshold, type, amount, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getName());
			stat.setInt(2, obj.getThreshold());
			stat.setInt(3, obj.getType().toSQLOrdinal());
			stat.setDouble(4, obj.getAmount());
			stat.setBoolean(5, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				Long id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Prize(id, obj));
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
	public Optional<Prize> get(Long id) {
		String query = "SELECT * FROM beauty_centerdb.prize WHERE id = ?";

		Optional<Prize> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Prize(rs));
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
	public List<Prize> getAll() {
		List<Prize> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.prize";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new Prize(rs));
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
		String query = "SELECT * FROM beauty_centerdb.prize LIMIT 1";

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
	public int update(Long id, Prize obj) {
		String query = "UPDATE beauty_centerdb.prize "
				+ " SET name = ?, threshold = ?, type = ?, amount = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setString(1, obj.getName());
			stat.setInt(2, obj.getThreshold());
			stat.setInt(3, obj.getType().toSQLOrdinal());
			stat.setDouble(4, obj.getAmount());
			stat.setBoolean(5, obj.isEnabled());

			stat.setInt(6, id);  //WHERE id = ?

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
	public int toggle(Prize obj) {
		String query = "UPDATE beauty_centerdb.prize "
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
	public int toggle(Long id) {
		return toggle(get(id).get());
	}

	@Override
	public int delete(Long id) {
		String query = "DELETE FROM beauty_centerdb.prize WHERE id = ?";

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

	public Optional<LocalDate> getMatchingExpirationDate(int customer_id, int prize_id) {
		String query = "SELECT expiration FROM beauty_centerdb.customerprize WHERE customer_id = ? AND prize_id = ?";

		Optional<LocalDate> opt = Optional.empty();
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, customer_id);  //WHERE customer_id = ?
			stat.setInt(2, prize_id);  //WHERE customer_id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			if(rs.next()) {
				opt = Optional.ofNullable(rs.getDate(1).toLocalDate()); //customer_id, prize_id, expiration
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

	public int addPrizeToCustomer(Customer customer, Prize prize, LocalDate expiration) {
		String query = "INSERT INTO beauty_centerdb.customerprize(customer_id, prize_id, expiration) "
				+ "VALUES (?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, prize.getId());
			if(expiration != null) {
				stat.setDate(3, Date.valueOf(expiration));	//throws if null
			} else {
				stat.setDate(3, null);
			}
			int exec = stat.executeUpdate();
			getConnection().commit();

			customer.addPrizes(prize);

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

	public int removePrizeFromCustomer(Customer customer, Prize prize) {
		String query = "DELETE FROM beauty_centerdb.customerprize WHERE customer_id = ? AND prize_id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, prize.getId());

			int exec = stat.executeUpdate();
			getConnection().commit();

			customer.removePrizes(prize);

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


	//get all rows where customer_id == id then retrieve prizes' id from row for object retrieval function
	public List<Prize> getAllPrizesAssignedToCustomer(Long id) {
		String query = "SELECT * FROM beauty_centerdb.customerprize WHERE customer_id = ?";

		List<Prize> list = new ArrayList<>();
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			while(rs.next()) {
				Prize prize = get(rs.getInt(2)).get(); //customer_id, prize_id
				list.add(prize);
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
	public List<Prize> getAllPrizesAssignedToCustomer(Customer customer) {
		return getAllPrizesAssignedToCustomer(customer.getId());
	}

	public List<Customer> getAllCustomersAssignedToPrize(Long id) {
		String query = "SELECT customer_id FROM beauty_centerdb.customerprize WHERE prize_id = ?";

		List<Customer> list = new ArrayList<>();
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			while(rs.next()) {
				Customer customer = CustomerDAO.getInstance().get(rs.getInt(1)).get(); //customer_id, prize_id
				list.add(customer);
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
	public List<Customer> getAllCustomersAssignedToPrize(Customer customer) {
		return getAllCustomersAssignedToPrize(customer.getId());
	}

	public List<Prize> getEnabled() {
		List<Prize> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.prize WHERE is_enabled = true";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while (rs.next()) {
				list.add(new Prize(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
}
