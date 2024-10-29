package com.bitcamp.centro.estetico.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VAT implements Model {
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

	// "id", "IVA%", "STATO"
	public Object[] toTableRow() {
		return new Object[] {
				id, amount, isEnabled
		};
	}

	@Override
	public String toString() {
		return amount + "%";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VAT other = (VAT) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		return true;
	}


}
