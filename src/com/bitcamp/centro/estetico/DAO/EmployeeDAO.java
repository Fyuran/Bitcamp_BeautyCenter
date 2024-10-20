package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;

public class EmployeeDAO implements DAO<Employee>{

	private EmployeeDAO(){}
    private static class SingletonHelper {
        private static EmployeeDAO INSTANCE = new EmployeeDAO();
    }
	public static EmployeeDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Employee> insert(Employee obj) {
		String query = "INSERT INTO beauty_centerdb.employee("
				+ "name, surname, is_female, birthday, birthplace, role, hired, "
				+ "termination, credentials_id, notes, is_enabled, serial) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			LocalDate terminationDate = obj.getTerminationDate();

			stat.setString(1, obj.getName());
			stat.setString(2, obj.getSurname());
			stat.setBoolean(3, obj.getGender().toBoolean());
			stat.setDate(4, obj.getBoD() != null ? Date.valueOf(obj.getBoD()) : null);
			stat.setString(5, obj.getBirthplace());
			stat.setInt(6, obj.getRole().toSQLOrdinal());
			stat.setDate(7, Date.valueOf(obj.getHiredDate()));

			stat.setDate(8, terminationDate != null ? Date.valueOf(terminationDate) : null);

			stat.setInt(9, obj.getUserCredentials().getId());
			stat.setString(10, obj.getNotes());
			stat.setBoolean(11, obj.isEnabled());
			stat.setLong(12, obj.getEmployeeSerial());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new Employee(id, obj));
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
	public Optional<Employee> get(int id) {
		String query = "SELECT * FROM beauty_centerdb.employee WHERE id = ?";

		Optional<Employee> opt = Optional.empty();
		if (isEmpty())
			return opt;

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				opt = Optional.ofNullable(new Employee(rs));
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
	public List<Employee> getAll() {
		List<Employee> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.employee";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while (rs.next()) {
				list.add(new Employee(rs));
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
		String query = "SELECT * FROM beauty_centerdb.employee LIMIT 1";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				return false;
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
		return true;
	}

	public List<Employee> filterByRole(Roles role) {
		return filterBy(e -> e.getRole().equals(role));
	}

	public List<Employee> filterBy(Predicate<? super Employee> pred) {
		List<Employee> employees = getAll();
		if (!employees.isEmpty()) {
			return employees.stream().filter(pred).toList();
		}
		return Collections.emptyList();
	}

	@Override
	public int update(int id, Employee obj) {
		String query = "UPDATE `beauty_centerdb`.`employee` "
				+ "SET `name` = ?, `surname` = ?, "
				+ "`is_female` = ?, `birthday` = ?, `birthplace` = ?, "
				+ "`role` = ?, `hired` = ?, `termination` = ?, "
				+ "`credentials_id` = ?, `notes` = ?, `is_enabled` = ?, `serial` = ? "
				+ "WHERE `id` = ?;";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
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
			stat.setBoolean(3, obj.getGender().toBoolean());
			stat.setDate(4, Date.valueOf(obj.getBoD()));
			stat.setString(5, obj.getBirthplace());
			stat.setInt(6, obj.getRole().toSQLOrdinal());
			stat.setDate(7, Date.valueOf(obj.getHiredDate()));
			stat.setDate(8, terminationDate != null ? Date.valueOf(terminationDate) : null);
			stat.setInt(9, UserCredentialsId);
			stat.setString(10, obj.getNotes());
			stat.setBoolean(11, obj.isEnabled());
			stat.setLong(12, obj.getEmployeeSerial());

			stat.setInt(13, id); // WHERE id = ?

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

	public int update(Employee employee) {
		return update(employee.getId(), employee);
	}

	@Override
	public int toggle(Employee obj) {
		String query = "UPDATE beauty_centerdb.employee "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";
		UserCredentialsDAO.getInstance().toggle(obj.getUserCredentials());
		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			if (obj.getId() <= 0) {
				throw new SQLException("invalid id: " + obj.getId());
			}

			boolean toggle = !obj.isEnabled(); // toggle enable or disable state
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
		String query = "DELETE FROM beauty_centerdb.employee WHERE id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			if (id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

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

	public boolean isSerialUnique(long serial) {
		String query = "SELECT serial FROM beauty_centerdb.employee WHERE serial = ? LIMIT 1";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setLong(1, serial);
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			return !rs.next();
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
		return true;
	}
}
