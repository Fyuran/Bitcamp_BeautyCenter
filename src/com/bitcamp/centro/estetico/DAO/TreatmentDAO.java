package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.Treatment;

public class TreatmentDAO implements DAO<Treatment> {

	private TreatmentDAO(){}
    private static class SingletonHelper {
        private static TreatmentDAO INSTANCE = new TreatmentDAO();
    }
	public static TreatmentDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Treatment> insert(Treatment obj) {
		String query = "INSERT INTO beauty_centerdb.treatment(type, price, vat_id, duration, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getType());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.get().getId());
			stat.setTime(4, Time.valueOf(obj.getLocalTimeFromDuration()));
			stat.setBoolean(5, obj.isEnabled());

			//
//			The conversion back would be:
//
//			java.sql.Time sqlTime = ..;
//			LocalTime localTime = sqlTime.toLocalTime();
//			Duration duration = Duration.between(LocalTime.MIDNIGHT, localTime);

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Treatment(id, obj));
			}
			throw new SQLException("Could not retrieve id");
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
		return Optional.empty();
	}

	@Override
	public Optional<Treatment> get(int id) {
		String query = "SELECT * FROM beauty_centerdb.treatment WHERE id = ?";

		Optional<Treatment> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try (PreparedStatement stat = getConnection().prepareStatement(query)) {

			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				opt = Optional.ofNullable(new Treatment(rs));
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
		return opt;
	}

	@Override
	public List<Treatment> getAll() {
		List<Treatment> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.treatment";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while (rs.next()) {
				list.add(new Treatment(rs));
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

	@Override
	public boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.treatment LIMIT 1";

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

	public int[] addProductsToTreatment(Treatment treatment, Product... products) {
		String query = "INSERT INTO beauty_centerdb.producttreatment(product_id, treatment_id) "
				+ "VALUES (?, ?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			
			for(Product product: products) {
				stat.setInt(1, product.getId());
				stat.setInt(2, treatment.getId());
				stat.addBatch();
			}

			int[] exec = stat.executeBatch();
			getConnection().commit();
			treatment.addProducts(products);

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
		return new int[]{};
	}
	public int[] addProductsToTreatment(Treatment treatment, List<Product> products) {
		return addProductsToTreatment(treatment, products.toArray(new Product[products.size()]));
	}

	public int removeProductFromTreatment(Treatment treatment, Product product) {
		String query = "DELETE FROM beauty_centerdb.customerprize WHERE product_id = ? AND treatment_id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, product.getId());
			stat.setInt(2, treatment.getId());

			int exec = stat.executeUpdate();
			getConnection().commit();

			treatment.removeProducts(product);

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

	//get all rows where treatment_id == id then retrieve products' id from row for object retrieval function
	public List<Product> getProductsOfTreatment(int id) {
		String query = "SELECT * FROM beauty_centerdb.producttreatment WHERE treatment_id = ?";

		List<Product> list = new ArrayList<>();
		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			while (rs.next()) {
				Product product = ProductDAO.getInstance().get(rs.getInt(2)).get(); // id, product_id, treatment_id
				list.add(product);
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
	public List<Product> getProductsOfTreatment(Treatment treatment) {
		return getProductsOfTreatment(treatment.getId());
	}

	//get all rows where product_id == id then retrieve treatments' id from row for object retrieval function
	public List<Treatment> getTreatmentsOfProduct(int id) {
		String query = "SELECT * FROM beauty_centerdb.producttreatment WHERE product_id = ?";

		List<Treatment> list = new ArrayList<>();
		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();

			while (rs.next()) {
				Treatment treatment = get(rs.getInt(3)).get(); // id, product_id, treatment_id
				list.add(treatment);
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
	public List<Treatment> getTreatmentsOfProduct(Product product) {
		return getTreatmentsOfProduct(product.getId());
	}

	@Override
	public int update(int id, Treatment obj) {
		String query = "UPDATE beauty_centerdb.treatment "
				+ "SET type = ?, price = ?, vat_id = ?, duration = ?, is_enabled = ? " + "WHERE id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setString(1, obj.getType());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.get().getId());
			stat.setTime(4, Time.valueOf(obj.getLocalTimeFromDuration()));
			stat.setBoolean(5, obj.isEnabled());

			stat.setInt(6, id); // WHERE id = ?

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
	public int toggle(Treatment obj) {
		String query = "UPDATE beauty_centerdb.treatment "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);
			stat.setBoolean(1, toggle);
			stat.setInt(2, obj.getId()); // WHERE id = ?
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
	public int toggle(int id) {
		return toggle(get(id).get());
	}

	@Override
	public int delete(int id) {
		String query = "DELETE FROM beauty_centerdb.treatment WHERE id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

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

	// SELECT * FROM Table ORDER BY ID DESC LIMIT 1
	public Treatment getLastTreatment() {
		String query = "SELECT * FROM treatment ORDER BY ID DESC LIMIT 1";
		try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery(query);
			getConnection().commit();
			if (rs.next()) {
				return get(rs.getInt("id")).get();
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isTreatmentNameUnique(String name) {
		String query = "SELECT type FROM beauty_centerdb.treatment WHERE type = ? LIMIT 1";
		try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			return !rs.next(); //row is valid? return NOT unique
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
