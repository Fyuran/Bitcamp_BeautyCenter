package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Product;

public abstract class ProductDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<Product> insertProduct(Product obj) {
		String query = "INSERT INTO beauty_centerdb.product("
				+ "name, amount, minimum, price, vat_id, type, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try(PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			stat.setString(1, obj.getName());
			stat.setInt(2, obj.getAmount());
			stat.setInt(3, obj.getMinStock());
			stat.setBigDecimal(4, obj.getPrice());
			stat.setInt(5, obj.getVat().getId());
			stat.setInt(6, obj.getType().toSQLOrdinal());
			stat.setBoolean(7, obj.isEnabled());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Product(id, obj));
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

	public final static Optional<Product> getProduct(int id) {
		String query = "SELECT * FROM beauty_centerdb.product WHERE id = ?";

		Optional<Product> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Product(rs));
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

	public final static boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.product LIMIT 1";

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

	public final static int updateData(int id, Product obj) {
		String query = "UPDATE beauty_centerdb.product "
				+ "SET name = ?, amount = ?, minimum = ?, "
				+ "price = ?, vat_id = ?, type = ?, "
				+ "is_enabled = ? "
				+ "WHERE id = ?";

		try (PreparedStatement stat = conn.prepareStatement(query)) {

			stat.setString(1, obj.getName());
			System.out.println(obj.getName());
			stat.setInt(2, obj.getAmount());
			stat.setInt(3, obj.getMinStock());
			stat.setBigDecimal(4, obj.getPrice());
			stat.setInt(5, obj.getVat().getId() );
			stat.setInt(6, obj.getType().toSQLOrdinal());
			stat.setBoolean(7, obj.isEnabled());

			stat.setInt(8, id); // WHERE id = ?

			int exec = stat.executeUpdate();
			conn.commit();

			return exec;
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
		return -1;
	}

	public final static int toggleEnabledProduct(Product obj) {
		String query = "UPDATE beauty_centerdb.product "
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
	public final static int toggleEnabledProduct(int id) {
		return toggleEnabledProduct(getProduct(id).get());
	}

	public final static int deleteProduct(int id) {
		String query = "DELETE FROM beauty_centerdb.product WHERE id = ?";

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

	public final static List<Product> getAllProducts() {
		List<Product> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.product";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new Product(rs));
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

	public final static List<Object[]> toTableRowAll() {
		List<Product> list = getAllProducts();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
