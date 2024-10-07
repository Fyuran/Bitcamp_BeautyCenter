package com.centro.estetico.bitcamp.repository;

import java.util.Map;

import javax.swing.JOptionPane;

import java.time.ZoneId;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import com.centro.estetico.bitcamp.*;
import java.time.LocalDate;
import java.time.Duration;

public class DAOReservation {
	private final String url = "jdbc:mysql://localhost:3306/beauty_centerdb";
	private final String username = "root";
	private final String password = "Bitcamp_0";

	public Map<Integer, Reservation> getAll() {// throws Exception{
		String query = "SELECT c.id as customer_id, c.name AS customer_name, c.surname AS customer_surname, r.id as reservation_id, r.date, "
				+ "e.id AS employee_id, e.name AS employee_name, e.surname AS employee_surname, t.id as treatment_id,"
				+ "t.type, t.duration " + "FROM reservation r JOIN customer c "
				+ "ON r.customer_id = c.id JOIN employee e "
				+ "ON r.employee_id = e.id JOIN treatment t ON r.treatment_id = t.id WHERE r.is_enabled = ?;";
		;

		Map<Integer, Reservation> reservations = new HashMap<Integer, Reservation>();

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, 1);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("reservation_id");

				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("reservation_id"));
				reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

				Treatment treatment = new Treatment();
				treatment.setId(rs.getInt("treatment_id"));
				treatment.setType(rs.getString("type"));

				// durata trattamento
				Time durationTime = rs.getTime("duration");
				Duration duration = Duration.ofHours(durationTime.toLocalTime().getHour())
						.plusMinutes(durationTime.toLocalTime().getMinute())
						.plusSeconds(durationTime.toLocalTime().getSecond());

				treatment.setDuration(duration);

				Customer customer = new Customer();
				customer.setId(rs.getInt("customer_id"));
				customer.setName(rs.getString("customer_name"));
				customer.setSurname(rs.getString("customer_surname"));

				Employee employee = new Employee();
				employee.setId(rs.getInt("employee_id"));
				employee.setName(rs.getString("employee_name"));
				employee.setSurname(rs.getString("employee_surname"));

				reservation.setCustomer(customer);
				reservation.setTreatment(treatment);
				reservation.setEmployee(employee);

				reservations.put(id, reservation);
			}

			pstmt.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return reservations;
	}

	// metodo per ottenere tutti gli appuntamenti già presi in quella specifica
	// data, per quel trattamento
	public List<Reservation> getAllBusyReservations(LocalDate date, Treatment treatment) {// throws Exception{

		String query = "SELECT c.name AS customer_name, c.surname AS customer_surname, r.id as reservation_id, "
				+ "r.date, r.treatment_id, "
				+ "e.id AS beautician_id, e.name AS beautician_name, e.surname AS beautician_surname, "
				+ "t.type, t.duration FROM reservation r " + "JOIN customer c ON r.customer_id = c.id "
				+ "JOIN employee e ON r.employee_id = e.id " + "JOIN treatment t ON r.treatment_id = t.id "
				+ "WHERE r.treatment_id = ? AND DATE(r.date) = ?;";
		List<Reservation> reservations = new ArrayList<Reservation>();

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = connection.prepareStatement(query);

			pstmt.setInt(1, treatment.getId());
			pstmt.setDate(2, Date.valueOf(date));
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("reservation_id"));
				reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

				Employee employee = new Employee();
				employee.setId(rs.getInt("beautician_id"));
				employee.setName(rs.getString("beautician_name"));
				employee.setSurname(rs.getString("beautician_surname"));

				reservation.setEmployee(employee);
				reservation.setTreatment(treatment);

				reservations.add(reservation);
			}
			pstmt.close();
			connection.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
			// throw new Exception(ex.getMessage());
		}

		return reservations;
	}

	public Reservation getReservation(int reservationId) {
		String query = "SELECT c.id as customer_id, c.name AS customer_name, c.surname AS customer_surname, r.id as reservation_id, r.date, "
				+ "e.id AS employee_id, e.name AS employee_name, e.surname AS employee_surname, t.id as treatment_id,"
				+ "t.type, t.duration " + "FROM reservation r JOIN customer c "
				+ "ON r.customer_id = c.id JOIN employee e "
				+ "ON r.employee_id = e.id JOIN treatment t ON r.treatment_id = t.id WHERE r.is_enabled = ? AND r.id = ?;";
		Reservation reservation = new Reservation();

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, reservationId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				// int id = rs.getInt("id");

				reservation.setId(rs.getInt("reservation_id"));
				reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

				Treatment treatment = new Treatment();
				treatment.setId(rs.getInt("treatment_id"));
				treatment.setType(rs.getString("type"));

				// durata trattamento
				Time durationTime = rs.getTime("duration");
				Duration duration = Duration.ofHours(durationTime.toLocalTime().getHour())
						.plusMinutes(durationTime.toLocalTime().getMinute())
						.plusSeconds(durationTime.toLocalTime().getSecond());

				treatment.setDuration(duration);

				Customer customer = new Customer();
				customer.setId(rs.getInt("customer_id"));
				customer.setName(rs.getString("customer_name"));
				customer.setSurname(rs.getString("customer_surname"));

				Employee employee = new Employee();
				employee.setId(rs.getInt("employee_id"));
				employee.setName(rs.getString("employee_name"));
				employee.setSurname(rs.getString("employee_surname"));

				reservation.setCustomer(customer);
				reservation.setTreatment(treatment);
				reservation.setEmployee(employee);
			}

			pstmt.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return reservation;
	}

	public void insert(Reservation reservation) {
		String query = "INSERT INTO reservation(date, is_paid, treatment_id, customer_id, employee_id, state)"
				+ "VALUES(?,?,?,?,?,?)";

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(reservation.getDateTime()));
			pstmt.setBoolean(2, reservation.isPaid());
			pstmt.setInt(3, reservation.getTreatment().getId());
			pstmt.setInt(4, reservation.getCustomer().getId());
			pstmt.setInt(5, reservation.getEmployee().getId());
			pstmt.setString(6, reservation.getState().name());

			pstmt.execute();

			pstmt.close();
			connection.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void update(Reservation reservation) {
		String query = "UPDATE reservation set date = ?, is_paid = ?, treatment_id = ?, customer_id = ?,"
				+ "employee_id = ?, state = ? WHERE id = ?";

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setTimestamp(1, Timestamp.valueOf(reservation.getDateTime()));
			pstmt.setBoolean(2, reservation.isPaid());
			pstmt.setInt(3, reservation.getTreatment().getId());
			pstmt.setInt(4, reservation.getCustomer().getId());
			pstmt.setInt(5, reservation.getEmployee().getId());
			pstmt.setString(6, reservation.getState().name());
			pstmt.setInt(7, reservation.getId());
			pstmt.execute();

			pstmt.close();
			connection.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Map<Integer, Reservation> getSearchedReservation(String textToSearch) {
		String query = "SELECT c.id AS customer_id, c.name AS customer_name, c.surname AS customer_surname, "
				+ "r.id AS reservation_id, r.date, e.id AS employee_id, e.name AS employee_name, "
				+ "e.surname AS employee_surname, t.id AS treatment_id, t.type, t.duration " + "FROM reservation r "
				+ "JOIN customer c ON r.customer_id = c.id JOIN employee e ON r.employee_id = e.id "
				+ "JOIN treatment t ON r.treatment_id = t.id WHERE r.is_enabled = 1 AND c.name LIKE ? OR "
				+ "c.surname LIKE ? OR t.type LIKE ?;";
		
		Map<Integer, Reservation> reservations = new HashMap<Integer, Reservation>();

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = connection.prepareStatement(query);

			pstmt.setString(1, "%" + textToSearch + "%");
			pstmt.setString(2, "%" + textToSearch + "%");
			pstmt.setString(3, "%" + textToSearch + "%");
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("reservation_id"));
				reservation.setDateTime(rs.getTimestamp("date").toLocalDateTime());

				Treatment treatment = new Treatment();
				treatment.setId(rs.getInt("treatment_id"));
				treatment.setType(rs.getString("type"));

				// durata trattamento
				Time durationTime = rs.getTime("duration");
				Duration duration = Duration.ofHours(durationTime.toLocalTime().getHour())
						.plusMinutes(durationTime.toLocalTime().getMinute())
						.plusSeconds(durationTime.toLocalTime().getSecond());

				treatment.setDuration(duration);

				Customer customer = new Customer();
				customer.setId(rs.getInt("customer_id"));
				customer.setName(rs.getString("customer_name"));
				customer.setSurname(rs.getString("customer_surname"));

				Employee employee = new Employee();
				employee.setId(rs.getInt("employee_id"));
				employee.setName(rs.getString("employee_name"));
				employee.setSurname(rs.getString("employee_surname"));

				reservation.setCustomer(customer);
				reservation.setTreatment(treatment);
				reservation.setEmployee(employee);
				
				reservations.put(reservation.getId(), reservation);
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
		return reservations;
	}
}
