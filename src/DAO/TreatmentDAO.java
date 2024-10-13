package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Product;
import com.centro.estetico.bitcamp.Treatment;

public abstract class TreatmentDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<Treatment> insertTreatment(Treatment obj) {
		String query = "INSERT INTO beauty_centerdb.treatment(type, price, vat_id, duration, is_enabled) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			stat.setString(1, obj.getType());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.getVat().getId());
			LocalTime localTime=LocalTime.MIDNIGHT.plus(obj.getDuration());
			java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);
			stat.setTime(4, sqlTime);
			stat.setBoolean(5, obj.isEnabled());

			//
//			The conversion back would be:
//
//			java.sql.Time sqlTime = ..;
//			LocalTime localTime = sqlTime.toLocalTime();
//			Duration duration = Duration.between(LocalTime.MIDNIGHT, localTime);

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Treatment(id, obj));
			}
			throw new SQLException("Could not retrieve id");
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
		return Optional.empty();
	}

	public final static Optional<Treatment> getTreatment(int id) {
		String query = "SELECT * FROM beauty_centerdb.treatment WHERE id = ?";

		Optional<Treatment> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if (rs.next()) {
				opt = Optional.ofNullable(new Treatment(rs));
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
		return opt;
	}

	public final static List<Treatment> getAllTreatments() {
		List<Treatment> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.treatment";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while (rs.next()) {
				list.add(new Treatment(rs));
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

	public final static boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.treatment LIMIT 1";

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

	public final static int addProductToTreatment(Treatment treatment, Product product) {
		String query = "INSERT INTO beauty_centerdb.producttreatment(product_id, treatment_id) "
				+ "VALUES (?, ?)";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, product.getId());
			stat.setInt(2, treatment.getId());

			int exec = stat.executeUpdate();
			conn.commit();

			treatment.addProducts(product);

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

	public final static int removeProductFromTreatment(Treatment treatment, Product product) {
		String query = "DELETE FROM beauty_centerdb.customerprize WHERE product_id = ? AND treatment_id = ?";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, product.getId());
			stat.setInt(2, treatment.getId());

			int exec = stat.executeUpdate();
			conn.commit();

			treatment.removeProducts(product);

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

	//get all rows where treatment_id == id then retrieve products' id from row for object retrieval function
	public final static List<Product> getProductsOfTreatment(int id) {
		String query = "SELECT * FROM beauty_centerdb.producttreatment WHERE treatment_id = ?";

		List<Product> list = new ArrayList<>();
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();

			while (rs.next()) {
				Product product = ProductDAO.getProduct(rs.getInt(2)).get(); // id, product_id, treatment_id
				list.add(product);
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
	public final static List<Product> getProductsOfTreatment(Treatment treatment) {
		return getProductsOfTreatment(treatment.getId());
	}

	//get all rows where product_id == id then retrieve treatments' id from row for object retrieval function
	public final static List<Treatment> getTreatmentsOfProduct(int id) {
		String query = "SELECT * FROM beauty_centerdb.producttreatment WHERE product_id = ?";

		List<Treatment> list = new ArrayList<>();
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();

			while (rs.next()) {
				Treatment treatment = getTreatment(rs.getInt(3)).get(); // id, product_id, treatment_id
				list.add(treatment);
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
	public final static List<Treatment> getTreatmentsOfProduct(Product product) {
		return getTreatmentsOfProduct(product.getId());
	}
	public final static int updateTreatment(int id, Treatment obj) {
		String query = "UPDATE beauty_centerdb.treatment "
				+ "SET type = ?, price = ?, vat_id = ?, duration = ?, is_enabled = ? " + "WHERE id = ?";

		try (PreparedStatement stat = conn.prepareStatement(query)) {

			stat.setString(1, obj.getType());
			stat.setBigDecimal(2, obj.getPrice());
			stat.setInt(3, obj.getVat().getId());
			stat.setInt(4, (int) obj.getDuration().toSeconds());
			stat.setBoolean(5, obj.isEnabled());

			stat.setInt(6, id); // WHERE id = ?

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

	public final static int toggleEnabledTreatment(Treatment obj) {
		String query = "UPDATE beauty_centerdb.treatment "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);
			stat.setBoolean(1, toggle);
			stat.setInt(2, obj.getId()); // WHERE id = ?
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
	public final static int toggleEnabledTreatment(int id) {
		return toggleEnabledTreatment(getTreatment(id).get());
	}

	public final static int deleteTreatment(int id) {
		String query = "DELETE FROM beauty_centerdb.treatment WHERE id = ?";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

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

	public final static List<Object[]> toTableRowAll() {
		List<Treatment> list = getAllTreatments();
		List<Object[]> data = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}

	// SELECT * FROM Table ORDER BY ID DESC LIMIT 1
	public static Treatment getLastTreatment() {
		String query = "SELECT * FROM treatment ORDER BY ID DESC LIMIT 1";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery(query);
			conn.commit();
			if (rs.next()) {
				return getTreatment(rs.getInt("id")).get();
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
