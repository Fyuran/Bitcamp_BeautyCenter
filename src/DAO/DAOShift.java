package DAO;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.HashMap;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Roles;
import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.ShiftEmployee;
import com.centro.estetico.bitcamp.ShiftType;

import java.time.*;

public class DAOShift {
	private static Connection connection = Main.getConnection();

	public Map<Integer, ShiftEmployee> getShifts() throws Exception {
		String query = "SELECT se.id AS shiftEmployee_id, s.start, s.end, s.type, s.id AS shift_id, e.name, e.surname, "
				+ "e.role, e.id AS employee_id FROM beauty_centerdb.shift s JOIN beauty_centerdb.shiftemployee se "
				+ "ON se.shift_id = s.id JOIN beauty_centerdb.employee e ON se.employee_id = e.id WHERE e.role = ? AND s.is_enabled = ?;";


		Map<Integer, ShiftEmployee> shifts = new HashMap<Integer, ShiftEmployee>();

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, Roles.PERSONNEL.name());
			pstmt.setInt(2, 1);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {

					ShiftEmployee shiftEmployee = new ShiftEmployee();

					Shift shift = new Shift();
					shift.setId(rs.getInt("shift_id"));
					shift.setStart(rs.getTimestamp("start").toLocalDateTime());
					shift.setEnd(rs.getTimestamp("end").toLocalDateTime());
					shift.setType(ShiftType.valueOf(rs.getString("type")));					
					shiftEmployee.setShift(shift);

					int idEmployee = rs.getInt("employee_id");
					Optional<Employee> optionalEmployee;
					optionalEmployee = EmployeeDAO.getEmployee(idEmployee);

					if (optionalEmployee.isPresent()) {
						shiftEmployee.setEmployee(optionalEmployee.get());
					}
					int shiftEmployee_id = rs.getInt("shiftEmployee_id");
					shifts.put(shiftEmployee_id, shiftEmployee);
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
		}
		return shifts;
	}

	public ShiftEmployee getShift(int id) throws Exception {
		String query = "SELECT se.id AS shift_employee_id, s.id AS shift_id, s.start, s.end, s.type, e.id AS employee_id, e.name, e.surname, e.is_female, e.birthday, e.role, e.hired, e.termination, e.credentials_id, e.notes, e.treatment_id, e.serial FROM beauty_centerdb.shift s JOIN beauty_centerdb.shiftemployee se ON s.id = se.shift_id JOIN beauty_centerdb.employee e ON e.id = se.employee_id WHERE se.id = ?;";


		ShiftEmployee se = new ShiftEmployee();
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Shift shift = new Shift(rs.getInt("shift_id"), rs.getTimestamp("start").toLocalDateTime(),
							rs.getTimestamp("end").toLocalDateTime(), ShiftType.valueOf(rs.getString("type")),
							rs.getString("notes"));
					Optional<Employee> employee = EmployeeDAO.getEmployee(rs.getInt("employee_id"));
					se = new ShiftEmployee(rs.getInt("shift_employee_id"), shift, employee.get());
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "qualcosa è andato storto");
			// throw new Exception("");
		}
		return se;
	}

	public Map<Integer, Shift> getEmployeesShifts() throws Exception {
		String query = "SELECT s.id AS shift_id, s.start AS start_shift, s.end AS end_shift, s.type AS typeOfShift, e.id AS employee_id, e.name, e.surname FROM beauty_centerdb.shift s JOIN beauty_centerdb.shiftemployee se ON s.id = se.shift_id JOIN beauty_centerdb.employee e ON e.id = se.employee_id;";

		Map<Integer, Shift> shifts = new HashMap<Integer, Shift>();

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			Shift shift = new Shift();

			shift.setId(rs.getInt(""));
			shift.setType(ShiftType.valueOf(rs.getString("typeOfShift")));
			shift.setStart(rs.getTimestamp("start_shift").toLocalDateTime());
			shift.setEnd(rs.getTimestamp("end_shift").toLocalDateTime());

			int idEmployee = rs.getInt("employee_id");
			Optional<Employee> optionalEmployee = EmployeeDAO.getEmployee(idEmployee);

			if (optionalEmployee.isPresent()) {
				Employee employee = optionalEmployee.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}
		return shifts;
	}

	public List<Employee> loadEmployeesWithShifts(int treatmentId) throws Exception {
		// Mappa per evitare duplicati di Employee
		Map<Integer, Employee> employeeMap = new HashMap<>();

		// Esegui la query per ottenere gli employee
		String query = "SELECT e.id AS employee_id, e.name AS employee_name, e.surname AS employee_surname "
				+ "FROM beauty_centerdb.employee e " + "JOIN beauty_centerdb.treatment t ON t.id = e.treatment_id " + "WHERE t.id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, treatmentId);

			try (ResultSet rs = pstmt.executeQuery()) {

				// Itera sui risultati della query
				while (rs.next()) {

					int employeeId = rs.getInt("employee_id");

					Optional<Employee> optionalEmployee = EmployeeDAO.getEmployee(employeeId);
					if (optionalEmployee.isPresent()) {
						Employee employeee = optionalEmployee.get();
						employeeMap.put(employeeId, employeee);
					}
				}

				for (Employee employee : employeeMap.values()) {
					List<Shift> shifts = loadShiftsForEmployee(employee.getId());
					employee.setShift(shifts);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}

		return new ArrayList<>(employeeMap.values());
	}

	public List<Shift> loadShiftsForEmployee(int employeeId) throws Exception {
		List<Shift> shifts = new ArrayList<>();

		String query = "SELECT s.id as shift_id, s.start AS shift_start_time, s.end AS shift_end_time " + "FROM beauty_centerdb.shiftemployee se "
				+ "JOIN beauty_centerdb.shift s ON se.shift_id = s.id " + "WHERE se.employee_id = ? AND s.type = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, employeeId);
			pstmt.setString(2, ShiftType.WORK.name());

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					LocalDateTime shiftStart = rs.getTimestamp("shift_start_time").toLocalDateTime();
					LocalDateTime shiftEnd = rs.getTimestamp("shift_end_time").toLocalDateTime();
					Shift shift = new Shift();
					shift.setId(rs.getInt("shift_id"));
					shift.setStart(shiftStart);
					shift.setEnd(shiftEnd);

					shifts.add(shift);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}

		return shifts;
	}
	
	public static List<Shift> loadShiftsForEmployeeD(int employeeId) {
		List<Shift> shifts = new ArrayList<>();

		String query = "SELECT s.start AS shift_start_time, s.end AS shift_end_time FROM beauty_centerdb.shiftemployee se JOIN beauty_centerdb.shift s ON se.shift_id = s.id WHERE se.employee_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, employeeId);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					LocalDateTime shiftStart = rs.getTimestamp("shift_start_time").toLocalDateTime();
					LocalDateTime shiftEnd = rs.getTimestamp("shift_end_time").toLocalDateTime();
					Shift shift = new Shift();
					shift.setStart(shiftStart);
					shift.setEnd(shiftEnd);

					shifts.add(shift);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nella preparazione della query: " + e.getMessage(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}

		return shifts;
	}

	public void insert(ShiftEmployee shiftEmployee) throws Exception {
		int id = -1;
		String query = "INSERT INTO beauty_centerdb.shift(start, end, type) values(?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(shiftEmployee.getShift().getStart()));
			pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(shiftEmployee.getShift().getEnd()));
			pstmt.setString(3, shiftEmployee.getShift().getType().name());

			pstmt.executeUpdate();

			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			}

			insertShiftEmployee(id, shiftEmployee.getEmployee());
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}
	}

	private void insertShiftEmployee(int id, Employee employee) throws Exception {
		String query = "INSERT INTO beauty_centerdb.shiftemployee(shift_id, employee_id) VALUES(?,?)";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.setInt(2, employee.getId());

			pstmt.executeUpdate();
		}

		catch (Exception e) {
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}
	}

	public void updateShiftEmployee(ShiftEmployee shiftEmployee) throws Exception {
		String query = "UPDATE beauty_centerdb.shiftemployee SET shift_id = ?, employee_id = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, shiftEmployee.getShift().getId());
			pstmt.setInt(2, shiftEmployee.getEmployee().getId());
			pstmt.setInt(3, shiftEmployee.getId());
			pstmt.executeUpdate();
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public void updateShift(Shift shift) throws Exception {
		String query = "UPDATE beauty_centerdb.shift SET start = ?, end = ?, type = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(shift.getStart()));
			pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(shift.getEnd()));
			pstmt.setString(3, shift.getType().name());
			pstmt.setInt(4, shift.getId());
			pstmt.executeUpdate();
		}

		catch (Exception e) {
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}
	}
	
	public void deleteShift(Shift shift)throws Exception {
		String query = "UPDATE beauty_centerdb.shift SET is_enabled = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, 0);
			pstmt.setInt(2, shift.getId());
			pstmt.executeUpdate();
		}

		catch (Exception e) {
			throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
		}
	}
	
	
	public Map<Integer, ShiftEmployee> getSearchedShifts(String text)throws Exception {
		String query = "SELECT se.id AS shiftEmployee_id, s.start, s.end, s.type, s.id AS shift_id, e.name, e.surname, "
				+ "e.role, e.id AS employee_id FROM beauty_centerdb.shift s JOIN beauty_centerdb.shiftemployee se "
				+ "ON se.shift_id = s.id JOIN beauty_centerdb.employee e ON se.employee_id = e.id WHERE e.role = ? AND s.is_enabled = ? "
				+ "AND (s.type LIKE ? OR e.name LIKE ? OR e.surname LIKE ?);";
		
		Map<Integer, ShiftEmployee> shifts = new HashMap<Integer, ShiftEmployee>();
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			String searchPattern = "%" + text + "%";
			pstmt.setString(1, Roles.PERSONNEL.name());
			pstmt.setInt(2, 1);
			pstmt.setString(3, searchPattern);
			pstmt.setString(4, searchPattern);
			pstmt.setString(5, searchPattern);			
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					ShiftEmployee shiftEmployee = new ShiftEmployee();

					Shift shift = new Shift();
					shift.setId(rs.getInt("shift_id"));
					shift.setStart(rs.getTimestamp("start").toLocalDateTime());
					shift.setEnd(rs.getTimestamp("end").toLocalDateTime());
					shift.setType(ShiftType.valueOf(rs.getString("type")));					
					shiftEmployee.setShift(shift);

					int idEmployee = rs.getInt("employee_id");
					Optional<Employee> optionalEmployee;
					optionalEmployee = EmployeeDAO.getEmployee(idEmployee);

					if (optionalEmployee.isPresent()) {
						shiftEmployee.setEmployee(optionalEmployee.get());
					}
					int shiftEmployee_id = rs.getInt("shiftEmployee_id");
					shifts.put(shiftEmployee_id, shiftEmployee);
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nell'esecuzione della query: " + e.getMessage(), e);
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nella preperazione della query: " + e.getMessage(), e);
		}
		
		return shifts;
	}

}
