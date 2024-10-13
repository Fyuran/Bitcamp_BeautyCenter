package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Roles;
import com.centro.estetico.bitcamp.UserCredentials;

import at.favre.lib.crypto.bcrypt.BCrypt;


public abstract class UserCredentialsDAO {
	private static Connection conn = Main.getConnection();

	public final static Optional<UserCredentials> insertUserCredentials(UserCredentials obj) {
		String query = "INSERT INTO `beauty_centerdb`.`user_credentials`(`username`, `password`, `address`, `iban`, `phone`, `mail`, `is_enabled`)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?);";

		try(PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getUsername());
			stat.setString(2, obj.getPassword());
			stat.setString(3, obj.getAddress());
			stat.setString(4, obj.getIban());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
			stat.setBoolean(7, obj.isEnabled());

			stat.executeUpdate();
			conn.commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new UserCredentials(id, obj));
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

	public final static Optional<UserCredentials> getUserCredentials(int id) {
		String query = "SELECT * FROM beauty_centerdb.user_credentials WHERE id = ?";

		Optional<UserCredentials> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new UserCredentials(rs));
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
	public static Optional<UserCredentials> getUserCredentials(String username) {
		String query = "SELECT * FROM beauty_centerdb.user_credentials WHERE username = ? LIMIT 1";

		Optional<UserCredentials> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, username);

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new UserCredentials(rs));
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
		String query = "SELECT * FROM beauty_centerdb.user_credentials LIMIT 1";

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

	public final static List<UserCredentials> filterEmployeesBy(Predicate<? super UserCredentials> pred) {
		List<UserCredentials> userCredentials = getAllUserCredentials();
		if(!userCredentials.isEmpty()) {
			return userCredentials.stream().filter(pred).toList();
		}
		return Collections.emptyList();
	}

	public final static List<UserCredentials> getAllUserCredentials() {
		List<UserCredentials> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.user_credentials";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			conn.commit();
			while(rs.next()) {
				list.add(new UserCredentials(rs));
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

	public final static Optional<Employee> getEmployeeOfUsername(String username) {
		String query = "SELECT id FROM beauty_centerdb.user_credentials WHERE username = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, username);  //WHERE username = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				var employee = getEmployeeOfUserCredentials(rs.getInt(1));
				return employee;
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
		return Optional.empty();
	}

	public final static Optional<Employee> getEmployeeOfUserCredentials(int id) {
		String query = "SELECT * FROM beauty_centerdb.employee WHERE credentials_id = ?";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE credentials_id = ?

			ResultSet rs = stat.executeQuery();
			conn.commit();
			if(rs.next()) {
				return Optional.ofNullable(new Employee(rs));
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
		return Optional.empty();
	}
	public final static Optional<Employee> getEmployeeOfUserCredentials(UserCredentials obj) {
		return getEmployeeOfUserCredentials(obj.getId());
	}

	public final static int updateUserCredentials(int id, UserCredentials obj) {
		String query = "UPDATE `beauty_centerdb`.`user_credentials` SET `username` = ?, "
				+ "`password` = ?, `address` = ?, `iban` = ?, `phone` = ?, `mail` = ?, `is_enabled` = ? "
				+ "WHERE `id` = ?;";

		try(PreparedStatement stat = conn.prepareStatement(query)) {

			stat.setString(1, obj.getUsername());
			stat.setString(2, obj.getPassword());
			stat.setString(3, obj.getAddress());
			stat.setString(4, obj.getIban());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
			stat.setBoolean(7, obj.isEnabled());

			stat.setInt(8, id);  //WHERE id = ?

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
	public final static int updateUserCredentials(int id, String username, String password, Roles role) {
		String query = "UPDATE `beauty_centerdb`.`user_credentials` SET `username` = ?, `password` = ? "
				+ "WHERE `id` = ?;";

		try(PreparedStatement stat = conn.prepareStatement(query)) {
			password = UserCredentials.encryptPassword(password);
			
			stat.setString(1, username);
			stat.setString(2, password);

			stat.setInt(3, id);  //WHERE id = ?

			Employee employee = getEmployeeOfUserCredentials(id).get();
			employee.setRole(role);
			EmployeeDAO.updateEmployee(employee);
			
			int exec = stat.executeUpdate();
			conn.commit();

			return exec;
		} catch(Exception e) { //Use generic Exception handler
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
	public final static int updateUserCredentials(int id, String username, char[] password, Roles role) {
		return updateUserCredentials(id, username, String.valueOf(password), role);
	}
	
	public final static int toggleEnabledUserCredentials(UserCredentials obj) {
		String query = "UPDATE beauty_centerdb.user_credentials "
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
	public final static int toggleEnabledUserCredentials(int id) {
		return toggleEnabledUserCredentials(getUserCredentials(id).get());
	}

	public final static int deleteUserCredentials(int id) {
		String query = "DELETE FROM beauty_centerdb.user_credentials WHERE id = ?";

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
		List<UserCredentials> list = getAllUserCredentials();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}


 // Metodo per cercare account attivi in base all'username o al ruolo
    public final static List<UserCredentials> filterUserCredentials(String searchText) {
        List<UserCredentials> accounts = getAllUserCredentials();
        accounts = accounts.stream().filter(a -> a.getUsername().equalsIgnoreCase(searchText)).toList();
        return accounts;
    }

    public static List<UserCredentials> filterUserCredentials(Roles role) {
        List<Employee> employees = EmployeeDAO.filterEmployeesByRole(role);
        List<UserCredentials> credentials =
        		employees
        		.stream()
        		.map(c -> getUserCredentials(c.getUserCredentialsId()).get())
        		.toList();

        return credentials;
    }

    // Metodo per verificare la password
    public final static boolean verifyPassword(String username, String password) {
        String query = "SELECT password FROM beauty_centerdb.user_credentials WHERE username = ? AND is_enabled = 1";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            conn.commit();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                // Verifica della password con BCrypt
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedPassword);
                return result.verified;
            }
        } catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
        }
        return false; // Restituisce false se l'utente non viene trovato o se la verifica fallisce
    }

	//stesso metodo overloadato con char[]
	public static boolean verifyPassword(String username, char[] password) {
		return verifyPassword(username, String.valueOf(password));
	}
}

