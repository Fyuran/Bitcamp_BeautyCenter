package com.centro.estetico.bitcamp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.centro.estetico.bitcamp.Shift;
import java.time.*;
import java.sql.*;

public class DAOShift {

	private final String url = "jdbc:mysql://localhost:3306/beauty_centerdb";
	private final String username = "root";
	private final String password = "Bitcamp_0";

	public List<Shift> loadShiftsForEmployee(int employeeId) throws SQLException {
		List<Shift> shifts = new ArrayList<>();

		String query = "SELECT s.start AS shift_start_time, s.end AS shift_end_time " + "FROM shiftemployee se "
				+ "JOIN shift s ON se.shift_id = s.id " + "WHERE se.employee_id = ?";

		Connection conn = DriverManager.getConnection(url, username, password);
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, employeeId);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			LocalDateTime shiftStart = rs.getTimestamp("shift_start_time").toLocalDateTime();
			LocalDateTime shiftEnd = rs.getTimestamp("shift_end_time").toLocalDateTime();
			Shift shift = new Shift();
			shift.setStartDate(shiftStart);
			shift.setEndDate(shiftEnd);

			shifts.add(shift);
		}

		rs.close();
		stmt.close();
		conn.close();

		return shifts;
	}
}
