package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Subscription;

public class SubscriptionDAO implements DAO<Subscription> {

	private SubscriptionDAO(){}
    private static class SingletonHelper {
        private static SubscriptionDAO INSTANCE = new SubscriptionDAO();
    }
	public static SubscriptionDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Subscription> insert(Subscription obj) {
		String query = "INSERT INTO beauty_centerdb.subscription("
				+ "subperiod, price, vat_id, discount, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setInt(1, obj.getSubPeriod().toSQLOrdinal());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.get().getId());
			stat.setDouble(4, obj.getDiscount());
			stat.setBoolean(5, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Subscription(id, obj));
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
	public Optional<Subscription> get(int id) {
		String query = "SELECT * FROM beauty_centerdb.subscription WHERE id = ?";

		Optional<Subscription> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Subscription(rs, getStartOfSubscription(id)));
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
	public boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.subscription LIMIT 1";

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
	public int update(int id, Subscription obj) {
		String query = "UPDATE beauty_centerdb.subscription "
				+ "SET subperiod = ?, price = ?, vat_id = ?, discount = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setInt(1, obj.getSubPeriod().toSQLOrdinal());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.get().getId());
			stat.setDouble(4, obj.getDiscount());
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
	public int toggle(Subscription obj) {
		String query = "UPDATE beauty_centerdb.subscription "
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
		String query = "DELETE FROM beauty_centerdb.subscription WHERE id = ?";

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

	@Override
	public List<Subscription> getAll() {
		List<Subscription> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.subscription";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new Subscription(rs, getStartOfSubscription(rs.getInt(1))));
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

	public int addSubscriptionToCustomer(Customer customer, Subscription subscription, LocalDate start) {
		String query = "INSERT INTO beauty_centerdb.customersubscription(customer_id, subscription_id, start) "
				+ "VALUES (?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, subscription.getId());
			if(start != null) {
				stat.setDate(3, Date.valueOf(start));	//throws if null
			} else {
				stat.setDate(3, null);
			}

			int exec = stat.executeUpdate();
			getConnection().commit();

			if(customer.getSubscription() != null) { //replace and delete the old one from db
				removeSubscriptionFromCustomer(customer, customer.getSubscription());
			}
			subscription.setStart(start);
			customer.setSubscription(subscription);

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

	public int updateCustomerSubscription(Customer customer, Subscription subscription, LocalDate start) {
		String query = "UPDATE beauty_centerdb.customersubscription SET customer_id = ?, subscription_id = ?, start = ?, "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, subscription.getId());
			if(start != null) {
				stat.setDate(3, Date.valueOf(start));	//throws if null
			} else {
				stat.setDate(3, null);
			}

			int exec = stat.executeUpdate();
			getConnection().commit();

			subscription.setStart(start);
			customer.setSubscription(subscription);

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

	public int removeSubscriptionFromCustomer(Customer customer, Subscription subscription) {
		if(customer.getSubscription() != subscription)
		 {
			return -1; //customer must contain this subscription
		}
		String query = "DELETE FROM beauty_centerdb.customersubscription WHERE customer_id = ? AND subscription_id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, subscription.getId());

			int exec = stat.executeUpdate();
			getConnection().commit();

			customer.setSubscription(null);

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
	public int removeSubscriptionFromCustomer(int customer_id, int subscription_id) {
		return removeSubscriptionFromCustomer(CustomerDAO.getInstance().get(customer_id).get(), SubscriptionDAO.getInstance().get(subscription_id).get());
	}

	public Optional<Subscription> getSubscriptionOfCustomer(int id) {
		String query = "SELECT * FROM beauty_centerdb.customersubscription WHERE customer_id = ?";

		Optional<Subscription> subscription = Optional.empty();
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			if(rs.next()) {
				subscription = get(rs.getInt(3)); //id, customer_id, subscription_id, start
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
		return subscription;
	}
	public Optional<Subscription> getSubscriptionOfCustomer(Customer customer) {
		return getSubscriptionOfCustomer(customer.getId());
	}

	public Optional<Customer> getCustomerOfSubscription(int id) {
		String query = "SELECT * FROM beauty_centerdb.customersubscription WHERE subscription_id = ?";

		Optional<Customer> customer = Optional.empty();
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			if(rs.next()) {
				customer = CustomerDAO.getInstance().get(rs.getInt(2)); //id, customer_id, subscription_id, start
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
		return customer;
	}
	public Optional<Customer> getCustomerOfSubscription(Subscription subscription) {
		return getCustomerOfSubscription(subscription.getId());
	}

	public Optional<LocalDate> getStartOfSubscription(int id) {
		Optional<LocalDate> start = Optional.empty();
		if(id <= 0) {
			return start;
		}

		String query = "SELECT start FROM beauty_centerdb.customersubscription WHERE subscription_id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			if(rs.next()) {
				start = Optional.ofNullable(rs.getDate(1).toLocalDate()); // start
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
		return start;

	}
	public Optional<LocalDate> getStartOfSubscription(Subscription subscription) {
		return getStartOfSubscription(subscription.getId());
	}


}
