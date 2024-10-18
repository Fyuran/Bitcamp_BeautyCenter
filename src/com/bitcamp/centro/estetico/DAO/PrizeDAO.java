package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Prize;

public abstract class PrizeDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<Prize> insertPrize(Prize obj) {
		String query = "INSERT INTO beauty_centerdb.prize(name, threshold, type, amount, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try(PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getName());
			stat.setInt(2, obj.getThreshold());
			stat.setInt(3, obj.getType().toSQLOrdinal());
			stat.setDouble(4, obj.getAmount());
			stat.setBoolean(5, obj.isEnabled());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Prize(id, obj));
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

	public final static Optional<Prize> getPrize(int id) {
		String query = "SELECT * FROM beauty_centerdb.prize WHERE id = ?";

		Optional<Prize> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Prize(rs));
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

	public final static List<Prize> getAllPrizes() {
		List<Prize> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.prize";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new Prize(rs));
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
		String query = "SELECT * FROM beauty_centerdb.prize LIMIT 1";

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

	public final static int updatePrize(int id, Prize obj) {
		String query = "UPDATE beauty_centerdb.prize "
				+ " SET name = ?, threshold = ?, type = ?, amount = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
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

	public final static int toggleEnabledPrize(Prize obj) {
		String query = "UPDATE beauty_centerdb.prize "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			if(obj.getId() <= 0) {
				throw new SQLException("invalid id: " + obj.getId());
			}

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
	public final static int toggleEnabledPrize(int id) {
		return toggleEnabledPrize(getPrize(id).get());
	}

	public final static int deletePrize(int id) {
		String query = "DELETE FROM beauty_centerdb.prize WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

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

	public final static Optional<LocalDate> getMatchingExpirationDate(int customer_id, int prize_id) {
		String query = "SELECT * FROM beauty_centerdb.customerprize WHERE customer_id = ? AND prize_id = ?";

		Optional<LocalDate> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, customer_id);  //WHERE customer_id = ?
			stat.setInt(2, prize_id);  //WHERE customer_id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();

			if(rs.next()) {
				opt = Optional.ofNullable(rs.getDate(3).toLocalDate()); //customer_id, prize_id, expiration
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

	public final static int addPrizeToCustomer(Customer customer, Prize prize, LocalDate expiration) {
		String query = "INSERT INTO beauty_centerdb.customerprize(customer_id, prize_id, expiration) "
				+ "VALUES (?, ?, ?)";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, prize.getId());
			if(expiration != null) {
				stat.setDate(3, Date.valueOf(expiration));	//throws if null
			} else {
				stat.setDate(3, null);
			}
			int exec = stat.executeUpdate();
			conn.commit();

			customer.addPrizes(prize);

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

	public final static int removePrizeFromCustomer(Customer customer, Prize prize) {
		String query = "DELETE FROM beauty_centerdb.customerprize WHERE customer_id = ? AND prize_id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, customer.getId());
			stat.setInt(2, prize.getId());

			int exec = stat.executeUpdate();
			conn.commit();

			customer.removePrizes(prize);

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


	//get all rows where customer_id == id then retrieve prizes' id from row for object retrieval function
	public final static List<Prize> getAllPrizesAssignedToCustomer(int id) {
		String query = "SELECT * FROM beauty_centerdb.customerprize WHERE customer_id = ?";

		List<Prize> list = new ArrayList<>();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();

			while(rs.next()) {
				Prize prize = PrizeDAO.getPrize(rs.getInt(2)).get(); //customer_id, prize_id
				list.add(prize);
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
	public final static List<Prize> getAllPrizesAssignedToCustomer(Customer customer) {
		return getAllPrizesAssignedToCustomer(customer.getId());
	}

	//get all rows where customer_id == id then retrieve prizes' id from row for object retrieval function
	public final static List<Customer> getAllCustomersAssignedToPrize(int id) {
		String query = "SELECT * FROM beauty_centerdb.customerprize WHERE prize_id = ?";

		List<Customer> list = new ArrayList<>();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();

			while(rs.next()) {
				Customer customer = CustomerDAO.getCustomer(rs.getInt(1)).get(); //customer_id, prize_id
				list.add(customer);
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
	public final static List<Customer> getAllCustomersAssignedToPrize(Customer customer) {
		return getAllCustomersAssignedToPrize(customer.getId());
	}

	public static List<Prize> getAllActivePrizes() {
		List<Prize> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.prize WHERE is_enabled = true"; // Premi attivi

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while (rs.next()) {
				list.add(new Prize(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}

	public static List<Prize> searchActivePrizes(String searchText) {
	    List<Prize> list = new ArrayList<>();
	    String query = "SELECT * FROM beauty_centerdb.prize WHERE is_enabled = 1 AND (name LIKE ? OR threshold = ?)";

	    try (PreparedStatement stat = conn.prepareStatement(query)) {
	        // Imposta il parametro del nome con il formato LIKE
	        stat.setString(1, "%" + searchText + "%");

	        // Prova a interpretare l'input come numero, se non riesce imposta il threshold a -1 (valore che non esiste)
	        try {
	            int threshold = Integer.parseInt(searchText);
	            stat.setInt(2, threshold);
	        } catch (NumberFormatException e) {
	            stat.setInt(2, -1);  // Imposta un valore che sicuramente non corrisponde a nessuna soglia
	        }

	        ResultSet rs = stat.executeQuery();
	        conn.commit();

	        while (rs.next()) {
	            list.add(new Prize(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public final static List<Object[]> toTableRowAll() {
		List<Prize> list = getAllPrizes();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
