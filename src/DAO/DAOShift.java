package DAO;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Shift;
import com.centro.estetico.bitcamp.ShiftType;

import java.time.*;

public class DAOShift {

	private static Connection connection = Main.getConnection();

	public Map<Integer, Shift> getEmployeesShifts() {
		String query = "SELECT s.id as shift_id, s.start AS start_shift, s.end AS end_shift, s.type AS typeOfShift, "
				+ "e.id as employee_id, e.name, e.surname FROM shift s JOIN shiftemployee se ON s.id = se.shift_id "
				+ "JOIN employee e ON e.id = se.employee_id;";

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

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return shifts;
	}

	public List<Employee> loadEmployeesWithShifts(int treatmentId) {
		// Mappa per evitare duplicati di Employee
		Map<Integer, Employee> employeeMap = new HashMap<>();

		// Esegui la query per ottenere gli employee
		String query = "SELECT e.id AS employee_id, e.name AS employee_name, e.surname AS employee_surname "
				+ "FROM employee e " + "JOIN treatment t ON t.id = e.treatment_id " + "WHERE t.id = ?";

		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, treatmentId);
			ResultSet rs = pstmt.executeQuery();

			// Itera sui risultati della query
			while (rs.next()) {
				int employeeId = rs.getInt("employee_id");

				Employee employee = employeeMap.get(employeeId);
				if (employee == null) {
					Optional<Employee> optionalEmployee = EmployeeDAO.getEmployee(employeeId);

					if (optionalEmployee.isPresent()) {
						Employee employeee = optionalEmployee.get();
						employeeMap.put(employeeId, employeee);
						// reservation.setEmployee(employeee);
					}
				}
			}

			// Chiama il DAOShift per caricare i turni degli estetisti
			// DAOShift daoShift = new DAOShift();
			for (Employee employee : employeeMap.values()) {
				List<Shift> shifts = loadShiftsForEmployee(employee.getId());
				employee.setShift(shifts);
			}
			// rs.close();
			// stmt.close();
			// conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Ritorna la lista di employee con i turni
		return new ArrayList<>(employeeMap.values());
	}

	public List<Shift> loadShiftsForEmployee(int employeeId){
		List<Shift> shifts = new ArrayList<>();

		String query = "SELECT s.start AS shift_start_time, s.end AS shift_end_time " + "FROM shiftemployee se "
				+ "JOIN shift s ON se.shift_id = s.id " + "WHERE se.employee_id = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
					
			pstmt.setInt(1, employeeId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				LocalDateTime shiftStart = rs.getTimestamp("shift_start_time").toLocalDateTime();
				LocalDateTime shiftEnd = rs.getTimestamp("shift_end_time").toLocalDateTime();
				Shift shift = new Shift();
				shift.setStart(shiftStart);
				shift.setEnd(shiftEnd);
	
				shifts.add(shift);
			}
		}
		catch(Exception ex) {
			
		}
		
		return shifts;
	}

	
	public void insert(Shift shift, Employee employee) {
		int id = -1;
		String query = "INSERT INTO shift(start, end, type) values(?, ?, ?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(shift.getStart()));
			pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(shift.getEnd()));
			pstmt.setString(3, shift.getType().name());
			
			pstmt.executeUpdate();
			
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {                
                id = generatedKeys.getInt(1);
            } 
			else {
				throw new Exception("Qualcosa è andato storto");
			}
			insertShiftEmployee(id, employee);
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private void insertShiftEmployee(int id, Employee employee) {
		String query = "INSERT INTO shiftemployee(shift_id, employee_id) VALUES(?,?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, id);
			pstmt.setInt(2, employee.getId());
			
			pstmt.executeUpdate();
		}
		
		catch(Exception ex) {
			
		}
	}

	public void update(Shift shift, Employee employee) {

	}

}
