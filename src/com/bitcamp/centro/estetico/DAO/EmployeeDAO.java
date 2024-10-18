package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.Treatment;

public abstract class EmployeeDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<Employee> insertEmployee(Employee obj) {
		String query = "INSERT INTO beauty_centerdb.employee("
				+ "name, surname, is_female, birthday, birthplace, role, hired, "
				+ "termination, credentials_id, notes, is_enabled, serial, treatment_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			LocalDate terminationDate = obj.getTerminationDate();

			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.isFemale());
			stat.setDate(4, obj.getBoD() != null ? Date.valueOf(obj.getBoD()) : null);
			stat.setString(5, obj.getBirthplace());
			stat.setInt(6, obj.getRole().toSQLOrdinal());
			stat.setDate(7, Date.valueOf(obj.getHiredDate()));

			stat.setDate(8, terminationDate != null ? Date.valueOf(terminationDate) : null);

			stat.setInt(9, obj.getUserCredentials().getId());
			stat.setString(10, obj.getNotes());
			stat.setBoolean(11, obj.isEnabled());
			stat.setLong(12, obj.getEmployeeSerial());
			stat.setInt(13, obj.getTreatment().getId());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Employee(id, obj));
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

	public final static Optional<Employee> getEmployee(int id) {
		String query = "SELECT * FROM beauty_centerdb.employee WHERE id = ?";

		Optional<Employee> opt = Optional.empty();
		if (isEmpty())
			return opt;

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if (rs.next()) {
				opt = Optional.ofNullable(new Employee(rs));
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

	public final static List<Employee> getAllEmployees() {
		List<Employee> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.employee";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while (rs.next()) {
				list.add(new Employee(rs));
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
		String query = "SELECT * FROM beauty_centerdb.employee LIMIT 1";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			if (rs.next()) {
				return false;
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
		return true;
	}

	public final static List<Employee> filterEmployeesByRole(Roles role) {
		return filterEmployeesBy(e -> e.getRole().equals(role));
	}

	public final static List<Employee> filterEmployeesBy(Predicate<? super Employee> pred) {
		List<Employee> employees = getAllEmployees();
		if (!employees.isEmpty()) {
			return employees.stream().filter(pred).toList();
		}
		return Collections.emptyList();
	}

	public final static int updateEmployee(int id, Employee obj) {
		String query = "UPDATE `beauty_centerdb`.`employee` "
				+ "SET `name` = ?, `surname` = ?, "
				+ "`is_female` = ?, `birthday` = ?, `birthplace` = ?, "
				+ "`role` = ?, `hired` = ?, `termination` = ?, "
				+ "`credentials_id` = ?, `notes` = ?, `is_enabled` = ?, `serial` = ?, `treatment_id` = ? "
				+ "WHERE `id` = ?;";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			if (id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			int UserCredentialsId = obj.getUserCredentials().getId();
			if (UserCredentialsId <= 0) {
				throw new SQLException("invalid UserCredentials id: " + UserCredentialsId);
			}

			LocalDate terminationDate = obj.getTerminationDate();

			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.isFemale());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setInt(6, obj.getRole().toSQLOrdinal());
			stat.setDate(7, Date.valueOf(obj.getHiredDate()));
			stat.setDate(8, terminationDate != null ? Date.valueOf(terminationDate) : null);
			stat.setInt(9, UserCredentialsId);
			stat.setString(10, obj.getNotes());
			stat.setBoolean(11, obj.isEnabled());
			stat.setLong(12, obj.getEmployeeSerial());

			Treatment treatment = obj.getTreatment();
			if(treatment == null) stat.setNull(13,java.sql.Types.INTEGER);
			else stat.setInt(13, treatment.getId());

			stat.setInt(14, id); // WHERE id = ?

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

	public static int updateEmployee(Employee employee) {
		return updateEmployee(employee.getId(), employee);
	}

	public final static int toggleEnabledEmployee(Employee obj) {
		String query = "UPDATE beauty_centerdb.employee "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";
		UserCredentialsDAO.toggleEnabledUserCredentials(obj.getUserCredentials());
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			if (obj.getId() <= 0) {
				throw new SQLException("invalid id: " + obj.getId());
			}

			boolean toggle = !obj.isEnabled(); // toggle enable or disable state
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

	public final static int toggleEnabledEmployee(int id) {
		return toggleEnabledEmployee(getEmployee(id).get());
	}

	public final static int deleteEmployee(int id) {
		String query = "DELETE FROM beauty_centerdb.employee WHERE id = ?";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			if (id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

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

	public static final boolean isSerialUnique(long serial) {
		String query = "SELECT serial FROM beauty_centerdb.employee WHERE serial = ? LIMIT 1";

		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setLong(1, serial);
			ResultSet rs = stat.executeQuery();
			conn.commit();
			return !rs.next();
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
		return true;
	}

	public final static List<Object[]> toTableRowAll() {
		List<Employee> list = getAllEmployees();
		List<Object[]> data = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}

	public final static List<Object[]> toTableRowAll(Predicate<? super Employee> pred) {
		List<Employee> list = getAllEmployees().parallelStream().filter(pred).toList();
		List<Object[]> data = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
