package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.bitcamp.centro.estetico.DAO.SubscriptionDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription")
public class Subscription implements Model {
	@Id
	@GeneratedValue
	private Long id;
	private SubPeriod subperiod;
	private LocalDate start;
	private LocalDate end;
	private BigDecimal price;
	private VAT vat;
	private double discount;
	private boolean isEnabled;

	

	// "ID", "Prezzo","IVA","Periodo", "Inizio", "Fine", "Sconto applicato",
	// "Cliente", "Abilitata"
	public Object[] toTableRow() {
		return new Object[] {
				id, price, vat, subperiod, start, end, discount,
				SubscriptionDAO.getInstance().getCustomerOfSubscription(id).orElse(null), isEnabled
		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subperiod == null) ? 0 : subperiod.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
		long temp;
		temp = Double.doubleToLongBits(discount);
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
		Subscription other = (Subscription) obj;
		if (subperiod != other.subperiod)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (vat == null) {
			if (other.vat != null)
				return false;
		} else if (!vat.equals(other.vat))
			return false;
		if (Double.doubleToLongBits(discount) != Double.doubleToLongBits(other.discount))
			return false;
		return true;
	}

}
