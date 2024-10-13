package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Transaction;

public abstract class TransactionDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<Transaction> insertTransaction(Transaction obj) {
		String query = "INSERT INTO beauty_centerdb.transaction("
				+ "price, payment_method, datetime,"
				+ "customer_id, vat_id, beauty_id, services, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try(PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			stat.setBigDecimal(1, obj.getPrice());
			stat.setInt(2, obj.getPaymentMethod().toSQLOrdinal());
			stat.setTimestamp(3, Timestamp.valueOf(obj.getDateTime())); //DATETIME and TIMESTAMP are almost equivalent
			stat.setInt(4, obj.getCustomer().getId());
			stat.setInt(5, obj.getVat().getId());
			stat.setInt(6, obj.getBeautyCenter().getId());
			stat.setString(7, obj.getServices());
			stat.setBoolean(8, obj.isEnabled());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.of(new Transaction(id, obj));
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

	/*		(
			"beauty_centerdb.transaction("
				+ "id, price, payment_method, dateTime,"
				+ "customer_id, vat_id, beauty_id, services, is_enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
			)
			(
			int id, BigDecimal price, PayMethod paymentMethod,
			LocalDateTime dateTime, Customer customer,
			VAT vat, BeautyCenter beautyCenter, boolean isEnabled
			)
	*/

	public final static List<Transaction> getAllTransactions() {
		List<Transaction> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.transaction";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new Transaction(rs));
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
		String query = "SELECT * FROM beauty_centerdb.transaction LIMIT 1";

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

	public final static Optional<Transaction> getTransaction(int id) {
		String query = "SELECT * FROM beauty_centerdb.transaction WHERE id = ?";

		Optional<Transaction> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			Transaction trans = null;
			if(rs.next()) {
				trans = new Transaction(rs);
				opt = Optional.ofNullable(trans);
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


	public final static int updateTransaction(int id, Transaction obj) {
		String query = "UPDATE beauty_centerdb.transaction "
				+ " SET price = ?, payment_method = ?, datetime = ?,"
				+ "customer_id = ?, vat_id = ?, beauty_id = ?, services = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {

			stat.setBigDecimal(1, obj.getPrice());
			stat.setInt(2, obj.getPaymentMethod().toSQLOrdinal());
			stat.setTimestamp(3, Timestamp.valueOf(obj.getDateTime())); //DATETIME and TIMESTAMP are almost equivalent
			stat.setInt(4, obj.getCustomer().getId());
			stat.setInt(5, obj.getVat().getId());
			stat.setInt(6, obj.getBeautyCenter().getId());
			stat.setString(7, obj.getServices().toString());
			stat.setBoolean(8, obj.isEnabled());

			stat.setInt(9, id);  //WHERE id = ?

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

	public final static int toggleEnabledTransaction(Transaction obj) {
		String query = "UPDATE beauty_centerdb.transaction "
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
	public final static int toggleEnabledTransaction(int id) {
		return toggleEnabledTransaction(getTransaction(id).get());
	}

	public final static int deleteTransaction(int id) {
		String query = "DELETE FROM beauty_centerdb.transaction WHERE id = ?";

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
		List<Transaction> list = getAllTransactions();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
