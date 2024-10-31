package com.bitcamp.centro.estetico.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.BeautyCenter;

public class BeautyCenterDAO implements DAO<BeautyCenter>{	

	private BeautyCenterDAO(){}
    private static class SingletonHelper {
        private static final BeautyCenterDAO INSTANCE = new BeautyCenterDAO();
    }
	public static BeautyCenterDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<BeautyCenter> insert(BeautyCenter obj) {
		String query = "INSERT INTO `beauty_centerdb`.`beauty_center` "
				+ "(`name`,"
				+ "`operating_office`,"
				+ "`registered_office`,"
				+ "`certified_mail`,"
				+ "`phone`,"
				+ "`mail`,"
				+ "`REA`,"
				+ "`P_IVA`,"
				+ "`opening_hour`,"
				+ "`closing_hour`,"
				+ "`is_enabled`) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getOperatingOffice());
			stat.setString(3, obj.getRegisteredOffice());
			stat.setString(4, obj.getCertifiedMail());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
			stat.setString(7, obj.getREA());
			stat.setString(8, obj.getP_IVA());
			stat.setTime(9, Time.valueOf(obj.getOpeningHour()));
			stat.setTime(10, Time.valueOf(obj.getClosingHour()));
			stat.setBoolean(11, obj.isEnabled());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				Long id = generatedKeys.getInt(1);
				return Optional.ofNullable(new BeautyCenter(id, obj));
			}
			throw new SQLException("Could not retrieve id");
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
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
	public boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.beauty_center LIMIT 1";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	public List<BeautyCenter> getAll() {
		List<BeautyCenter> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.beauty_center";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new BeautyCenter(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public Optional<BeautyCenter> get(Long id) {
		String query = "SELECT * FROM beauty_centerdb.beauty_center WHERE id = ?";

		Optional<BeautyCenter> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new BeautyCenter(rs));
			}

		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return opt;
	}
	
	
	public Optional<BeautyCenter> getFirst() {
		String query = "SELECT * FROM beauty_centerdb.beauty_center LIMIT 1";

		Optional<BeautyCenter> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new BeautyCenter(rs));
			}

		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
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
	public int update(Long id, BeautyCenter obj) {
		String query = "UPDATE `beauty_centerdb`.`beauty_center` SET "
				+ "`name` = ?,"
				+ "`operating_office` = ?,"
				+ "`registered_office` = ?,"
				+ "`certified_mail` = ?,"
				+ "`phone` = ?,"
				+ "`mail` = ?,"
				+ "`REA` = ?,"
				+ "`P_IVA` = ?,"
				+ "`opening_hour` = ?,"
				+ "`closing_hour` = ?,"
				+ "`is_enabled` = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			stat.setString(1, obj.getName());
			stat.setString(2, obj.getOperatingOffice());
			stat.setString(3, obj.getRegisteredOffice());
			stat.setString(4, obj.getCertifiedMail());
			stat.setString(5, obj.getPhone());
			stat.setString(6, obj.getMail());
			stat.setString(7, obj.getREA());
			stat.setString(8, obj.getP_IVA());
			stat.setTime(9, Time.valueOf(obj.getOpeningHour()));
			stat.setTime(10, Time.valueOf(obj.getClosingHour()));
			stat.setBoolean(11, obj.isEnabled());

			stat.setInt(12, id); //WHERE id = ?
			int exec = stat.executeUpdate();

			getConnection().commit();

			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
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
		String query = "UPDATE beauty_centerdb.beauty_center "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			BeautyCenter obj = get(id).get();
			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);

			stat.setBoolean(1, toggle);
			stat.setInt(2, id); //WHERE id = ?
			int exec = stat.executeUpdate();

			getConnection().commit();

			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
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
	public int toggle(BeautyCenter obj) {
		return toggle(obj.getId());
	}

	@Override
	public int delete(Long id) {
		String query = "DELETE FROM beauty_centerdb.beauty_center WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}
			stat.setInt(1, id); //WHERE id = ?

			int exec = stat.executeUpdate();
			getConnection().commit();

			return exec;
		} catch(SQLException e) {
			e.printStackTrace();
			if(getConnection() != null) {
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return -1;
	}
}
