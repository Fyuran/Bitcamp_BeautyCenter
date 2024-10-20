package com.bitcamp.centro.estetico.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.bitcamp.centro.estetico.DAO.BeautyCenterDAO;
import com.bitcamp.centro.estetico.gui.SetupWelcomeFrame;

public class Main {
	private static Connection conn;
	public static boolean _DEBUG_MODE_FULL = true;

	public static BeautyCenter getBeautyCenter() {
		return BeautyCenterDAO.getFirst().get();
	}

	public Main(String url, String username, String password) {
		try {
			if(conn == null) {
				conn = DriverManager.getConnection(url, username, password);
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if(conn == null)
			throw new NoSuchElementException("connection is null");
		return conn;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main("jdbc:mysql://204.216.214.56:1806", "bitcampUser", "newPassword");
				new SetupWelcomeFrame();
			}
		});

	}
}