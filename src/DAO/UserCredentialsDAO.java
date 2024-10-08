package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Roles;
import com.centro.estetico.bitcamp.UserCredentials;

import at.favre.lib.crypto.bcrypt.BCrypt;


public abstract class UserCredentialsDAO {
	private static Connection conn = Main.getConnection();

	public static Optional<UserCredentials> insertUserCredentials(UserCredentials obj) {
		String query = "INSERT INTO `beauty_centerdb`.`user_credentials`(`username`, `password`, `mail`, `iban`, `phone`, `is_enabled`)"
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		
		try(PreparedStatement stat = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			//(String username, String password, String address, String iban, String phone, String mail)
			stat.setString(1, obj.getUsername());
			stat.setString(2, obj.getPassword());
			stat.setString(3, obj.getMail());
			stat.setString(4, obj.getIban());
			stat.setString(5, obj.getPhone());
			stat.setBoolean(6, obj.isEnabled());
			
			stat.executeUpdate();
			conn.commit();
			
			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new UserCredentials(id, obj));
			} else {
				throw new SQLException("Could not retrieve id");
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
	
	public static Optional<UserCredentials> getUserCredentials(int id) {
		String query = "SELECT * FROM beauty_centerdb.user_credentials WHERE id = ?";
		
		Optional<UserCredentials> opt = Optional.empty();
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

	public static List<UserCredentials> getAllUserCredentials() {
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
	
	public static int updateUserCredentials(int id, UserCredentials obj) {
		String query = "UPDATE `beauty_centerdb`.`user_credentials` SET `username` = ?, "
				+ "`password` = ?, `mail` = ?, `iban` = ?, `phone` = ?, `is_enabled` = ? "
				+ "WHERE `id` = ?;";
		
		try(PreparedStatement stat = conn.prepareStatement(query)) {
			
			stat.setString(1, obj.getUsername());
			stat.setString(2, obj.getPassword());
			stat.setString(3, obj.getAddress());
			stat.setString(4, obj.getIban());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
			
			stat.setInt(7, id);  //WHERE id = ?
			
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

	public static int toggleEnabledUserCredentials(UserCredentials obj) {
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
	public static int toggleEnabledUserCredentials(int id) {
		return toggleEnabledUserCredentials(getUserCredentials(id).get());
	}
	
	public static int deleteUserCredentials(int id) {
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
	
	public static List<Object[]> toTableRowAll() {
		List<UserCredentials> list = getAllUserCredentials();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}
		
		return data;
	}
	
    // Metodo per creare un nuovo account con i dati divisi nelle due tabelle
    public static boolean createAccount(String username, String password, Roles role) throws SQLException {
        String createAccountQuery = "INSERT INTO user_credentials (username, password, is_enabled) VALUES (?, ?, ?)";
        String createEmployeeQuery = "INSERT INTO employee (credentials_id, role, name, surname, is_enabled) VALUES (?, ?, ?, ?, ?)";
        boolean success = false;

        try (PreparedStatement createAccountStmt = conn.prepareStatement(createAccountQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement createEmployeeStmt = conn.prepareStatement(createEmployeeQuery)) {

            //  BCrypt
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

            // user_credentials
            createAccountStmt.setString(1, username);
            createAccountStmt.setString(2, bcryptHashString); //password crittografata
            createAccountStmt.setBoolean(3, true); // is_enabled
            int affectedRows = createAccountStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creazione account fallita, nessuna riga aggiunta.");
            }

            // Ottieni l'ID generato dell'account
            try (ResultSet generatedKeys = createAccountStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountId = generatedKeys.getInt(1);

                    //employee
                    createEmployeeStmt.setInt(1, accountId);
                    createEmployeeStmt.setString(2, role.name());
                    createEmployeeStmt.setString(3, "Nome"); // Valore predefinito per name (not null)
                    createEmployeeStmt.setString(4, "Cognome"); // Valore predefinito per surname (not null)
                    createEmployeeStmt.setBoolean(5, true); //is_enabled 

                    createEmployeeStmt.executeUpdate();
                    success = true;
                } else {
                    throw new SQLException("Creazione account fallita, nessun ID generato.");
                }
            }

            conn.commit(); 
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'account: " + e.getMessage());
            try {
                conn.rollback(); 
            } catch (SQLException rollbackEx) {
                System.err.println("Errore durante il rollback: " + rollbackEx.getMessage());
            }
            throw e;
        }
        
        return success;
    }

 // Metodo per aggiornare un account esistente
    public static boolean updateAccount(int accountId, String username, String password, Roles role) throws SQLException {
        String updateAccountQuery = "UPDATE user_credentials SET username = ?, password = ? WHERE id = ?";
        String updateEmployeeQuery = "UPDATE employee SET role = ? WHERE credentials_id = ?";

        try (PreparedStatement updateAccountStmt = conn.prepareStatement(updateAccountQuery);
             PreparedStatement updateEmployeeStmt = conn.prepareStatement(updateEmployeeQuery)) {

           //BCrypt
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

            // Aggiornare  user_credentials
            updateAccountStmt.setString(1, username);
            updateAccountStmt.setString(2, bcryptHashString); 
            updateAccountStmt.setInt(3, accountId);
            updateAccountStmt.executeUpdate();

            // Aggiornare  employee
            updateEmployeeStmt.setString(1, role.name());
            updateEmployeeStmt.setInt(2, accountId);
            updateEmployeeStmt.executeUpdate();

            conn.commit(); 
            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'account: " + e.getMessage());
            conn.rollback();
            throw e;
        }
    }

    
 // Metodo per disabilitare un account
    public static boolean disableAccount(int accountId) throws SQLException {
        String disableUserQuery = "UPDATE user_credentials SET is_enabled = 0 WHERE id = ?";
        String disableEmployeeQuery = "UPDATE employee SET is_enabled = 0 WHERE credentials_id = ?";

        try (PreparedStatement disableUserStmt = conn.prepareStatement(disableUserQuery);
             PreparedStatement disableEmployeeStmt = conn.prepareStatement(disableEmployeeQuery)) {
            
            // user_credentials
            disableUserStmt.setInt(1, accountId);
            int userResult = disableUserStmt.executeUpdate();

            // employee 
            disableEmployeeStmt.setInt(1, accountId);
            int employeeResult = disableEmployeeStmt.executeUpdate();

            
            if (userResult > 0 && employeeResult > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la disabilitazione dell'account: " + e.getMessage());
            conn.rollback();
            throw e;
        }
    }


 // Metodo per cercare tutti gli account attivi
    public static List<Object[]> searchActiveAccounts() throws SQLException {
        List<Object[]> accountData = new ArrayList<>();
        String query = "SELECT user_credentials.id, username, role " +
                       "FROM user_credentials " +
                       "JOIN employee ON user_credentials.id = employee.credentials_id " +
                       "WHERE user_credentials.is_enabled = 1";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("id");
                row[1] = resultSet.getString("username");
                row[2] = resultSet.getString("role");
                accountData.add(row);
            }
        }
        return accountData;
    }
    
 // Metodo per cercare account attivi in base all'username o al ruolo
    public static List<Object[]> searchAccounts(String searchText) throws SQLException {
        List<Object[]> accountData = new ArrayList<>();
        String query = "SELECT user_credentials.id, username, role FROM user_credentials " +
                       "JOIN employee ON user_credentials.id = employee.credentials_id " +
                       "WHERE (username LIKE ? OR role LIKE ?) AND user_credentials.is_enabled = 1";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] row = new Object[3];
                    row[0] = resultSet.getInt("id");
                    row[1] = resultSet.getString("username");
                    row[2] = resultSet.getString("role");
                    accountData.add(row);
                }
            }
        }

        return accountData;
    }

    // Metodo per verificare la password
    public static boolean verifyPassword(String username, String password) throws SQLException {
        String query = "SELECT password FROM user_credentials WHERE username = ? AND is_enabled = 1";
        
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    
                    // Verifica della password con BCrypt
                    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedPassword);
                    return result.verified;
                }
            }
        }
        return false; // Restituisce false se l'utente non viene trovato o se la verifica fallisce
    }
}

