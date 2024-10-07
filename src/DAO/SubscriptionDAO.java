package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Subscription;

public abstract class SubscriptionDAO {
	private static Connection conn = Main.getConnection();
	
	public static Optional<Subscription> insertSubscription(Subscription obj) {
		String query = "INSERT INTO beauty_centerdb.subscription("
				+ "subperiod, price, vat_id, discount, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			stat.setInt(1, obj.getSubPeriod().toSQLOrdinal());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.getVat().getId());
			stat.setDouble(4, obj.getDiscount());
			stat.setBoolean(5, obj.isEnabled());
			
			stat.executeUpdate();
			conn.commit();
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Subscription(id, obj));
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
			}	;
		}
		return Optional.empty();
	}
	
	public static Optional<Subscription> getSubscription(int id) {
		String query = "SELECT * FROM beauty_centerdb.subscription WHERE id = ?";
		
		Optional<Subscription> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Subscription(rs));				
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
	
	//subperiod, price, vat_id, discount, is_enabled
	public static int updateSubscription(int id, Subscription obj) {
		String query = "UPDATE beauty_centerdb.subscription "
				+ "SET subperiod= ?, price = ?, vat_id = ?, discount = ?, is_enable = ?, "
				+ "WHERE id = ?";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			
			stat.setInt(1, obj.getSubPeriod().toSQLOrdinal());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.getVat().getId());
			stat.setDouble(4, obj.getDiscount());
			stat.setBoolean(5, obj.isEnabled());
			
			stat.setInt(6, id);  //WHERE id = ?
			
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

	public static int toggleEnabledSubscription(Subscription obj) {
		String query = "UPDATE beauty_centerdb.subscription "
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
	
	
	public static int deleteSubscription(int id) {
		String query = "DELETE FROM beauty_centerdb.subscription WHERE id = ?";
		
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
	
	public static List<Subscription> getAllSubscriptions() {
		List<Subscription> list = new ArrayList<>();
		
		String query = "SELECT * FROM beauty_centerdb.subscription";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new Subscription(rs));
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
	
	public static int addSubscriptionToCustomer(Customer customer, Subscription subscription, LocalDate start) {
		String query = "INSERT INTO beauty_centerdb.customersubscription(customer_id, subscription_id, start) "
				+ "VALUES (?, ?, ?)";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, subscription.getId());
			if(start != null) {
				stat.setDate(3, Date.valueOf(start));	//throws if null
			} else {
				stat.setDate(3, null);
			}
			
			int exec = stat.executeUpdate();
			conn.commit();
			
			if(customer.getSubscription() != null) { //replace and delete the old one from db
				removeSubscriptionFromCustomer(customer, customer.getSubscription());
			}
			subscription.setStart(start);
			customer.setSubscription(subscription);
			
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
	
	public static int removeSubscriptionFromCustomer(Customer customer, Subscription subscription) {
		if(customer.getSubscription() != subscription) return -1; //customer must contain this subscription
		String query = "DELETE FROM beauty_centerdb.customerprize WHERE customer_id = ? AND subscription_id = ?";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, subscription.getId());
			
			int exec = stat.executeUpdate();
			conn.commit();
			
			customer.setSubscription(null);
			
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
	
	public static Optional<Subscription> getSubscriptionOfCustomer(int id) {
		String query = "SELECT * FROM beauty_centerdb.customersubscription WHERE customer_id = ?";
		
		Optional<Subscription> subscription = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			conn.commit();
			
			if(rs.next()) {
				subscription = getSubscription(rs.getInt(3)); //id, customer_id, subscription_id, start
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
		return subscription;
	}
	public static Optional<Subscription> getSubscriptionOfCustomer(Customer customer) {
		return getSubscriptionOfCustomer(customer.getId());
	}
	
	public static Optional<Customer> getCustomerOfSubscription(int id) {
		String query = "SELECT * FROM beauty_centerdb.customersubscription WHERE subscription_id = ?";
		
		Optional<Customer> customer = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?
			
			ResultSet rs = stat.executeQuery();
			conn.commit();
			
			if(rs.next()) {
				customer = CustomerDAO.getCustomer(rs.getInt(2)); //id, customer_id, subscription_id, start
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
		return customer;
	}
	public static Optional<Customer> getCustomerOfSubscription(Subscription subscription) {
		return getCustomerOfSubscription(subscription.getId());
	}
	
	public static List<Object[]> toTableRowAll() {
		List<Subscription> list = getAllSubscriptions();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}
		
		return data;
	}

}
