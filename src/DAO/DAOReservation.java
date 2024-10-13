package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.centro.estetico.bitcamp.Customer;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Reservation;
import com.centro.estetico.bitcamp.Treatment;

public class DAOReservation {
	private static Connection connection = Main.getConnection();

	public Map<Integer, Reservation> getAll() throws Exception {
		String query = "SELECT c.id as customer_id, c.name AS customer_name, c.surname AS customer_surname, "
				+ "r.id as reservation_id, r.date, e.id AS employee_id, e.name AS employee_name, "
				+ "e.surname AS employee_surname, t.id as treatment_id, t.type, t.duration " + "FROM beauty_centerdb.reservation r "
				+ "JOIN beauty_centerdb.customer c ON r.customer_id = c.id " + "JOIN beauty_centerdb.employee e ON r.employee_id = e.id "
				+ "JOIN beauty_centerdb.treatment t ON r.treatment_id = t.id " + "WHERE r.is_enabled = ? " + "ORDER BY r.date ASC;";

		Map<Integer, Reservation> reservations = new HashMap<>();

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, 1);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("reservation_id");

					Reservation reservation = new Reservation();
					reservation.setId(id);
					reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

					int idTreatment = rs.getInt("treatment_id");
					Optional<Treatment> optionalTreatment;

					optionalTreatment = TreatmentDAO.getTreatment(idTreatment);

					reservation.setTreatment(optionalTreatment.get());

					int idCustomer = rs.getInt("customer_id");
					Optional<Customer> optionalCustomer;
					optionalCustomer = CustomerDAO.getCustomer(idCustomer);

					if (optionalCustomer.isPresent()) {
						reservation.setCustomer(optionalCustomer.get());
					}

					int idEmployee = rs.getInt("employee_id");
					Optional<Employee> optionalEmployee;
					optionalEmployee = EmployeeDAO.getEmployee(idEmployee);

					if (optionalEmployee.isPresent()) {
						reservation.setEmployee(optionalEmployee.get());
					}

					reservations.put(id, reservation);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
		}
		return reservations;
	}

	// metodo per ottenere tutti gli appuntamenti già presi in quella specifica
	// data, per quel trattamento
	// Metodo per ottenere tutti gli appuntamenti già presi in quella specifica
	// data, per quel trattamento
	public List<Reservation> getAllBusyReservations(LocalDate date, Treatment treatment) throws Exception {
		String query = "SELECT r.id as reservation_id, r.date, r.treatment_id, " + "e.id AS beautician_id "
				+ "FROM beauty_centerdb.reservation r " + "JOIN employee e ON r.employee_id = e.id "
				+ "WHERE r.treatment_id = ? AND DATE(r.date) = ?;";

		List<Reservation> reservations = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, treatment.getId());
			pstmt.setDate(2, Date.valueOf(date));

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(rs.getInt("reservation_id"));
					reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

					int idEmployee = rs.getInt("beautician_id");
					Optional<Employee> optionalEmployee = EmployeeDAO.getEmployee(idEmployee);

					if (optionalEmployee.isPresent()) {
						Employee employee = optionalEmployee.get();
						reservation.setEmployee(employee);
					} else {
						// Gestisci il caso in cui l'Employee non sia presente, se necessario
					}

					reservation.setTreatment(treatment);

					reservations.add(reservation);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
		}
		return reservations;
	}

	public Reservation getReservation(int reservationId)throws Exception  {
		String query = "SELECT c.id as customer_id, c.name AS customer_name, c.surname AS customer_surname, "
				+ "r.id as reservation_id, r.date, "
				+ "e.id AS employee_id, e.name AS employee_name, e.surname AS employee_surname, "
				+ "t.id as treatment_id, t.type, t.duration " + "FROM beauty_centerdb.reservation r "
				+ "JOIN beauty_centerdb.customer c ON r.customer_id = c.id " + "JOIN employee e ON r.employee_id = e.id "
				+ "JOIN beauty_centerdb.treatment t ON r.treatment_id = t.id " + "WHERE r.is_enabled = ? AND r.id = ?;";

		Reservation reservation = new Reservation();

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, 1);
			pstmt.setInt(2, reservationId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					reservation.setId(rs.getInt("reservation_id"));
					reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

					int idTreatment = rs.getInt("treatment_id");
					Optional<Treatment> optionalTreatment = TreatmentDAO.getTreatment(idTreatment);

					if (optionalTreatment.isPresent()) {
						Treatment treatment = optionalTreatment.get();
						reservation.setTreatment(treatment);
					}

					int idCustomer = rs.getInt("customer_id");
					Optional<Customer> optionalCustomer = CustomerDAO.getCustomer(idCustomer);

					// Correzione: Controllo di optionalCustomer invece di optionalTreatment
					if (optionalCustomer.isPresent()) {
						Customer customer = optionalCustomer.get();
						reservation.setCustomer(customer);
					}

					int idEmployee = rs.getInt("employee_id");
					Optional<Employee> optionalEmployee = EmployeeDAO.getEmployee(idEmployee);

					if (optionalEmployee.isPresent()) {
						Employee employee = optionalEmployee.get();
						reservation.setEmployee(employee);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
		}
		return reservation;
	}

	public void insert(Reservation reservation) throws Exception{
		String query = "INSERT INTO beauty_centerdb.reservation(date, is_paid, treatment_id, customer_id, employee_id, state)"
				+ "VALUES(?,?,?,?,?,?)";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(reservation.getDateTime()));
			pstmt.setBoolean(2, reservation.isPaid());
			pstmt.setInt(3, reservation.getTreatment().getId());
			pstmt.setInt(4, reservation.getCustomer().getId());
			pstmt.setInt(5, reservation.getEmployee().getId());
			pstmt.setString(6, reservation.getState().name());

			pstmt.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}
	}

	public void update(Reservation reservation)throws Exception {
		String query = "UPDATE beauty_centerdb.reservation SET date = ?, is_paid = ?, treatment_id = ?, customer_id = ?, "
				+ "employee_id = ?, state = ? WHERE id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setTimestamp(1, Timestamp.valueOf(reservation.getDateTime()));
			pstmt.setBoolean(2, reservation.isPaid());
			pstmt.setInt(3, reservation.getTreatment().getId());
			pstmt.setInt(4, reservation.getCustomer().getId());
			pstmt.setInt(5, reservation.getEmployee().getId());
			pstmt.setString(6, reservation.getState().name());
			pstmt.setInt(7, reservation.getId());

			pstmt.executeUpdate(); // Consigliato utilizzare executeUpdate per operazioni di UPDATE

		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
			// Puoi gestire l'eccezione in modo più specifico se necessario
		}
	}

	public Map<Integer, Reservation> getSearchedReservation(String textToSearch)throws Exception {
		String query = "SELECT c.id AS customer_id, c.name AS customer_name, c.surname AS customer_surname, "
				+ "r.id AS reservation_id, r.date, e.id AS employee_id, e.name AS employee_name, "
				+ "e.surname AS employee_surname, t.id AS treatment_id, t.type, t.duration " + "FROM beauty_centerdb.reservation r "
				+ "JOIN beauty_centerdb.customer c ON r.customer_id = c.id " + "JOIN beauty_centerdb.employee e ON r.employee_id = e.id "
				+ "JOIN beauty_centerdb.treatment t ON r.treatment_id = t.id "
				+ "WHERE r.is_enabled = 1 AND (c.name LIKE ? OR c.surname LIKE ? OR t.type LIKE ?);";

		Map<Integer, Reservation> reservations = new HashMap<>();

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			String searchPattern = "%" + textToSearch + "%";
			pstmt.setString(1, searchPattern);
			pstmt.setString(2, searchPattern);
			pstmt.setString(3, searchPattern);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(rs.getInt("reservation_id"));
					reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

					// Recupero e impostazione del Treatment
					int idTreatment = rs.getInt("treatment_id");
					Optional<Treatment> optionalTreatment = TreatmentDAO.getTreatment(idTreatment);
					if (optionalTreatment.isPresent()) {
						Treatment treatment = optionalTreatment.get();
						reservation.setTreatment(treatment);
					}

					// Recupero e impostazione del Customer
					int idCustomer = rs.getInt("customer_id");
					Optional<Customer> optionalCustomer = CustomerDAO.getCustomer(idCustomer);
					// Correzione: Controllo di optionalCustomer invece di optionalTreatment
					if (optionalCustomer.isPresent()) {
						Customer customer = optionalCustomer.get();
						reservation.setCustomer(customer);
					}

					// Recupero e impostazione dell'Employee
					int idEmployee = rs.getInt("employee_id");
					Optional<Employee> optionalEmployee = EmployeeDAO.getEmployee(idEmployee);
					if (optionalEmployee.isPresent()) {
						Employee employee = optionalEmployee.get();
						reservation.setEmployee(employee);
					}

					reservations.put(reservation.getId(), reservation);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}

		return reservations;
	}

	public void disable(Reservation reservation)throws Exception {
		String query = "UPDATE beauty_centerdb.reservation SET is_enabled = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, 0);
			pstmt.setInt(2, reservation.getId());

			pstmt.executeUpdate();

		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);

			// Puoi gestire l'eccezione in modo più specifico o loggarla utilizzando un
			// framework di logging
		}
	}

}
