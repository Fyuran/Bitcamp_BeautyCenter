package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.VAT;

public abstract class VATDao {

	private static Connection conn = Main.getConnection();

	public final static Optional<VAT> insertVAT(VAT obj) {
		String query = "INSERT INTO beauty_centerdb.vat(amount, is_enabled) VALUES (?, ?) ON DUPLICATE KEY UPDATE amount = ?";

		try(PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setDouble(1, obj.getAmount());
			stat.setBoolean(2, obj.isEnabled());
			stat.setDouble(3, obj.getAmount());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new VAT(id, obj));
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

	public final static Optional<VAT> getVAT(int id) {
		String query = "SELECT * FROM beauty_centerdb.vat WHERE id = ?";

		Optional<VAT> opt = Optional.empty();
		if(isEmpty()) return opt;
		
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
	public final static Optional<VAT> getVAT(VAT vat) {
		return getVAT(vat.getId());
	}

	public final static Optional<VAT> getVATByAmount(double amount) {
		String query = "SELECT * FROM beauty_centerdb.vat WHERE amount = ?";

		Optional<VAT> opt = Optional.empty();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setDouble(1, amount);  //WHERE id = ?

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

	public final static List<VAT> getAllVAT() {
		List<VAT> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.vat";

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

	public final static boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.vat LIMIT 1";

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

	public final static List<VAT> filterVATby(Predicate<? super VAT> pred) {
		List<VAT> vats = getAllVAT();
		if(!vats.isEmpty()) {
			return vats.stream().filter(pred).toList();
		}
		return Collections.emptyList();
	}

	public final static int updateVAT(int id, VAT obj) {
		String query = "UPDATE beauty_centerdb.vat "
				+ "SET amount = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setDouble(1, obj.getAmount());
			stat.setBoolean(2, obj.isEnabled());

			stat.setInt(3, id); //WHERE id = ?

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

	public final static int toggleEnabledVAT(VAT obj) {
		String query = "UPDATE beauty_centerdb.vat "
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
	public final static int toggleEnabledVAT(int id) {
		return toggleEnabledVAT(getVAT(id).get());
	}

	public final static int deleteVAT(int id) {
		String query = "DELETE FROM beauty_centerdb.vat WHERE id = ?";

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

	public final static boolean existByAmount(double amount) {
		String query = "SELECT COUNT(*) FROM beauty_centerdb.vat WHERE ABS(vat.amount - ?) < 0.000001";
		Connection conn = Main.getConnection();
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setDouble(1, amount);
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) == 0 ? false : true;
			}
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public final static List<Object[]> toTableRowAll() {
		List<VAT> list = getAllVAT();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
