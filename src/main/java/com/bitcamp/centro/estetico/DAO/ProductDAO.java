package com.bitcamp.centro.estetico.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Product;

public class ProductDAO implements DAO<Product> {

	private ProductDAO(){}
    private static class SingletonHelper {
        private static ProductDAO INSTANCE = new ProductDAO();
    }
	public static ProductDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Product> insert(Product obj) {
		String query = "INSERT INTO beauty_centerdb.product("
				+ "name, amount, minimum, price, vat_id, type, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getName());
			stat.setInt(2, obj.getAmount());
			stat.setInt(3, obj.getMinStock());
			stat.setBigDecimal(4, obj.getPrice());
			stat.setInt(5, obj.get().getId());
			stat.setInt(6, obj.getType().toSQLOrdinal());
			stat.setBoolean(7, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				Long id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Product(id, obj));
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
	public Optional<Product> get(Long id) {
		String query = "SELECT * FROM beauty_centerdb.product WHERE id = ?";

		Optional<Product> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new Product(rs));
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
		String query = "SELECT * FROM beauty_centerdb.product LIMIT 1";

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
	public int update(Long id, Product obj) {
		String query = "UPDATE beauty_centerdb.product "
				+ "SET name = ?, amount = ?, minimum = ?, "
				+ "price = ?, vat_id = ?, type = ?, "
				+ "is_enabled = ? "
				+ "WHERE id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			if(obj.get().getId() <= 0) {
				throw new SQLException("invalid VAT id: " + obj.get().getId());
			}

			stat.setString(1, obj.getName());
			System.out.println(obj.getName());
			stat.setInt(2, obj.getAmount());
			stat.setInt(3, obj.getMinStock());
			stat.setBigDecimal(4, obj.getPrice());
			stat.setInt(5, obj.get().getId());
			stat.setInt(6, obj.getType().toSQLOrdinal());
			stat.setBoolean(7, obj.isEnabled());

			stat.setInt(8, id); // WHERE id = ?

			int exec = stat.executeUpdate();
			getConnection().commit();

			return exec;
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
		return -1;
	}

	@Override
	public int toggle(Product obj) {
		String query = "UPDATE beauty_centerdb.product "
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
		String query = "DELETE FROM beauty_centerdb.product WHERE id = ?";

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
	public List<Product> getAll() {
		List<Product> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.product";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new Product(rs));
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

	public boolean isNameUnique(String productName) {
		String query="SELECT * FROM beauty_centerdb.product WHERE name=? LIMIT 1";
		String name="";
		
		try(PreparedStatement pstmt = getConnection().prepareStatement(query)){
			pstmt.setString(1, productName);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				name=rs.getString("name");

			}
			System.out.println("Nome cercato: "+productName);
			System.out.println("Nome trovato: "+name);
			return name.equals("");
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
