package com.centro.estetico.bitcamp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VAT {
	private final int id;
	private double amount;
	private boolean isEnabled;

	private VAT(int id, double amount, boolean isEnabled) {
		this.id = id;
		this.amount = amount;
		this.isEnabled = isEnabled;
	}

	public VAT(int id, VAT obj) {
		this(id, obj.amount, obj.isEnabled);
	}

	public VAT(double amount, boolean isEnabled) {
		this(-1, amount, isEnabled);
	}

	public VAT(double amount) {
		this(-1, amount, true);
	}

	public VAT(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1),
			rs.getDouble(2),
			rs.getBoolean(3)
		);
	}
	public int getId() {
		return id;
	}
	public double getAmount() {
		return amount;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	// "#", "%"
	public Object[] toTableRow() {
		return new Object[] {
				id, amount
		};
	}

	@Override
	public String toString() {
		return amount + "%";
	}


}
