package com.bitcamp.centro.estetico.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.bitcamp.centro.estetico.models.VAT;

public class VAT_DAO implements DAO<VAT>{

	private VAT_DAO(){}
    private static class SingletonHelper {
        private static VAT_DAO INSTANCE = new VAT_DAO();
    }
	public static VAT_DAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<VAT> insert(VAT obj) {
		String query = "INSERT INTO beauty_centerdb.vat(amount, is_enabled) VALUES (?, ?) ON DUPLICATE KEY UPDATE amount = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stat.setDouble(1, obj.getAmount());
			stat.setBoolean(2, obj.isEnabled());
			stat.setDouble(3, obj.getAmount());

			stat.executeUpdate();
			getConnection().commit();

			ResultSet generatedKeys = stat.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				return Optional.ofNullable(new VAT(id, obj));
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
	public Optional<VAT> get(int id) {
		String query = "SELECT * FROM beauty_centerdb.vat WHERE id = ?";

		Optional<VAT> opt = Optional.empty();
		if(isEmpty()) return opt;
		
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setInt(1, id);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new VAT(rs));
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
	public Optional<VAT> get(VAT vat) {
		return get(vat.getId());
	}

	public Optional<VAT> getVATByAmount(double amount) {
		String query = "SELECT * FROM beauty_centerdb.vat WHERE amount = ?";

		Optional<VAT> opt = Optional.empty();
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setDouble(1, amount);  //WHERE id = ?

			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			if(rs.next()) {
				opt = Optional.ofNullable(new VAT(rs));
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
	public List<VAT> getAll() {
		List<VAT> list = new ArrayList<>();

		String query = "SELECT * FROM beauty_centerdb.vat";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			ResultSet rs = stat.executeQuery();
			getConnection().commit();
			while(rs.next()) {
				list.add(new VAT(rs));
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
	public boolean isEmpty() {
		String query = "SELECT * FROM beauty_centerdb.vat LIMIT 1";

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

	public List<VAT> filterBy(Predicate<? super VAT> pred) {
		List<VAT> vats = getAll();
		if(!vats.isEmpty()) {
			return vats.stream().filter(pred).toList();
		}
		return Collections.emptyList();
	}

	@Override
	public int update(int id, VAT obj) {
		String query = "UPDATE beauty_centerdb.vat "
				+ "SET amount = ?, is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			if(id <= 0) {
				throw new SQLException("invalid id: " + id);
			}

			stat.setDouble(1, obj.getAmount());
			stat.setBoolean(2, obj.isEnabled());

			stat.setInt(3, id); //WHERE id = ?

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
	public int toggle(VAT obj) {
		String query = "UPDATE beauty_centerdb.vat "
				+ "SET is_enabled = ? "
				+ "WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			boolean toggle = !obj.isEnabled(); //toggle enable or disable state
			obj.setEnabled(toggle);
			stat.setBoolean(1, toggle);
			stat.setInt(2, obj.getId()); //WHERE id = ?
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
	public int toggle(int id) {
		return toggle(get(id).get());
	}

	@Override
	public int delete(int id) {
		String query = "DELETE FROM beauty_centerdb.vat WHERE id = ?";

		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
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

	public boolean existByAmount(double amount) {
		String query = "SELECT COUNT(*) FROM beauty_centerdb.vat WHERE ABS(vat.amount - ?) < 0.000001";
		try(PreparedStatement stat = getConnection().prepareStatement(query)) {
			stat.setDouble(1, amount);
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) == 0 ? false : true;
			}
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public List<Object[]> toTableRowAll() {
		List<VAT> list = getAll();
		List<Object[]> data = new ArrayList<>(list.size());
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i).toTableRow());
		}

		return data;
	}
}
