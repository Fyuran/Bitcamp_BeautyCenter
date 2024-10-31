package com.bitcamp.centro.estetico.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.models.UserCredentials;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserCredentialsDAO implements DAO<UserCredentials> {

	private UserCredentialsDAO(){}
    private static class SingletonHelper {
        private static UserCredentialsDAO INSTANCE = new UserCredentialsDAO();
    }
	public static UserCredentialsDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<UserCredentials> insert(UserCredentials obj) {
		String query = "INSERT INTO `beauty_centerdb`.`user_credentials`(`username`, `password`, `address`, `iban`, `phone`, `mail`, `is_enabled`)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?);";

		try (PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getUsername());
			stat.setString(2, String.copyValueOf(obj.getPassword()));
			stat.setString(3, obj.getAddress());
			stat.setString(4, obj.getIban());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
			stat.setBoolean(7, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if (generatedKeys.next()) {
				Long id = generatedKeys.getInt(1);
				return Optional.ofNullable(new UserCredentials(id, obj));
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
	public Optional<UserCredentials> get(Long id) {
		String query = "SELECT * FROM beauty_centerdb.user_credentials WHERE id = ?";

		Optional<UserCredentials> opt = Optional.empty();
		if (isEmpty())
			return opt;

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id); // WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				opt = Optional.ofNullable(new UserCredentials(rs));
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

	public Optional<UserCredentials> get(String username) {
		String query = "SELECT * FROM beauty_centerdb.user_credentials WHERE username = ? LIMIT 1";

		Optional<UserCredentials> opt = Optional.empty();
		if (isEmpty())
			return opt;

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setString(1, username);

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				opt = Optional.ofNullable(new UserCredentials(rs));
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
	public boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.user_credentials LIMIT 1";

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

	public List<UserCredentials> filterUserCredentialsBy(Predicate<? super UserCredentials> pred) {
		List<UserCredentials> userCredentials = getAll();
		if (!userCredentials.isEmpty()) {
			return userCredentials.stream().filter(pred).toList();
		}
		return Collections.emptyList();
	}

	@Override
	public List<UserCredentials> getAll() {
		List<UserCredentials> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.user_credentials";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while (rs.next()) {
				list.add(new UserCredentials(rs));
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

	public Optional<Employee> getEmployeeOfUsername(String username) {
		String query = "SELECT id FROM beauty_centerdb.user_credentials WHERE username = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setString(1, username); // WHERE username = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				var employee = getEmployeeOfUserCredentials(rs.getInt(1));
				return employee;
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
		return Optional.empty();
	}

	public Optional<Employee> getEmployeeOfUserCredentials(Long id) {
		String query = "SELECT * FROM beauty_centerdb.employee WHERE credentials_id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id); // WHERE credentials_id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if (rs.next()) {
				return Optional.ofNullable(new Employee(rs));
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
		return Optional.empty();
	}

	public Optional<Employee> getEmployeeOfUserCredentials(UserCredentials obj) {
		return getEmployeeOfUserCredentials(obj.getId());
	}

	@Override
	public int update(Long id, UserCredentials obj) {
		String query = "UPDATE `beauty_centerdb`.`user_credentials` SET `username` = ?, "
				+ "`password` = ?, `address` = ?, `iban` = ?, `phone` = ?, `mail` = ?, `is_enabled` = ? "
				+ "WHERE `id` = ?;";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			if (id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setString(1, obj.getUsername());
			stat.setString(2, obj.getPassword().toString());
			stat.setString(3, obj.getAddress());
			stat.setString(4, obj.getIban());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
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
	
	public int update(Employee obj) {
		UserCredentials uc = obj.getUserCredentials();
		if (obj.getId() != -1)
			return update(uc.getId(), uc);
		return -1;
	}

	public int updatePassword(Long id, char[] password) {
		String query = "UPDATE `beauty_centerdb`.`user_credentials` SET `password` = ? WHERE `id` = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			if (id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			stat.setString(1, String.copyValueOf(password));

			stat.setInt(2, id); // WHERE id = ?

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
	public int updatePassword(UserCredentials obj, char[] password) {
		return updatePassword(obj.getId(), password);
	}
	public int updatePassword(User obj, char[] password) {
		return updatePassword(obj.getUserCredentialsId(), password);
	}

	@Override
	public int toggle(UserCredentials obj) {
		String query = "UPDATE beauty_centerdb.user_credentials "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
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
	public int toggle(Long id) {
		return toggle(get(id).get());
	}

	@Override
	public int delete(Long id) {
		String query = "DELETE FROM beauty_centerdb.user_credentials WHERE id = ?";

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

	public List<UserCredentials> filterUserCredentials(String searchText) {
		List<UserCredentials> accounts = getAll();
		accounts = accounts.stream().filter(a -> a.getUsername().equalsIgnoreCase(searchText)).toList();
		return accounts;
	}

	public List<UserCredentials> filterUserCredentials(Roles role) {
		List<Employee> employees = EmployeeDAO.getInstance().filterByRole(role);
		List<UserCredentials> credentials = employees
				.stream()
				.map(c -> get(c.getUserCredentialsId()).get())
				.toList();

		return credentials;
	}

	// Metodo per verificare la password
	public boolean isValidPassword(String username, char[] password) {
		String query = "SELECT password FROM beauty_centerdb.user_credentials WHERE username = ? AND is_enabled = 1";

		try (PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setString(1, username);

			ResultSet resultSet = stat.executeQuery();
			getConnection().commit();
			if (resultSet.next()) {
				String storedPassword = resultSet.getString("password");

				// Verifica della password con BCrypt
				BCrypt.Result result = BCrypt.verifyer().verify(password, storedPassword);
				return result.verified;
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
		return false; // Restituisce false se l'utente non viene trovato o se la verifica fallisce
	}

	public boolean isUsernameUnique(String username) {
		String query = "SELECT username FROM beauty_centerdb.user_credentials WHERE username = ? LIMIT 1";

		try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			getConnection().commit();
			return !rs.next(); // row is valid? return NOT unique
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
		return false;
	}
}
