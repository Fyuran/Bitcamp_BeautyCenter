package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centro.estetico.bitcamp.Employee.Roles;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AccountDAO {
    private Connection conn;

    // Costruttore per connessione
    public AccountDAO(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("La connessione al database non può essere null");
        }
        this.conn = conn;
    }

    // Metodo per creare un nuovo account con i dati divisi nelle due tabelle
    public boolean createAccount(String username, String password, Roles role) throws SQLException {
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
    public boolean updateAccount(int accountId, String username, String password, Roles role) throws SQLException {
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
    public boolean disableAccount(int accountId) throws SQLException {
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
    public List<Object[]> searchActiveAccounts() throws SQLException {
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
    public List<Object[]> searchAccounts(String searchText) throws SQLException {
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
    public boolean verifyPassword(String username, String password) throws SQLException {
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

