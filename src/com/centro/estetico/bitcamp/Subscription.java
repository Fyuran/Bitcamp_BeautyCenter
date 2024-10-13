package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import DAO.SubscriptionDAO;
import DAO.VATDao;


public class Subscription {
    private final int id;
    private SubPeriod subperiod;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal price;
    private VAT vat;
    private double discount;
    private boolean isEnabled;

    private Subscription(int id, SubPeriod subperiod, LocalDate start, BigDecimal price, VAT vat, double discount, boolean isEnabled) {
        this.id = id;
        this.subperiod = subperiod;
        this.start=start;
        this.end = start == null ? null : plusSubPeriod(start, subperiod);
        this.price = price;
        this.vat = vat;
        this.discount = discount;
        this.isEnabled = isEnabled;
    }

    public Subscription(SubPeriod subperiod, LocalDate start, BigDecimal price, VAT vat, double discount, boolean isEnabled) {
        this(-1, subperiod, start, price, vat, discount, isEnabled);
    }

    public Subscription(int id, Subscription obj) {
		this(id, obj.subperiod, obj.start, obj.price, obj.vat, obj.discount, obj.isEnabled);
	}

	public Subscription(ResultSet rs, Optional<LocalDate> start) throws SQLException {
    	this(
			rs.getInt(1),
			SubPeriod.valueOf(rs.getString(2)),
			start.orElse(null),
			rs.getBigDecimal(3),
			VATDao.getVAT(rs.getInt(4)).get(),
			rs.getDouble(5),
			rs.getBoolean(6)
		);
	}

	//Calcoliamo la durata
    private static LocalDate plusSubPeriod(LocalDate start, SubPeriod subperiod) {
    	if(start == null) {
			return null;
		}
    	return start.plusMonths(subperiod.getMonths());
    }

    // Getter e Setter

    public void setStart(LocalDate start) {
        this.start = start;
        //A questo punto cambia anche la fine
        this.end = plusSubPeriod(start, subperiod);
    }

    public void setDiscount(double discount) {
        if (discount >= 0) {
            this.discount = discount;
        } else {
            throw new IllegalArgumentException("Discount cannot be negative.");
        }
    }

	public int getId() {
		return id;
	}


	public SubPeriod getSubPeriod() {
		return subperiod;
	}

	public void setSubPeriod(SubPeriod subperiod) {
		this.subperiod = subperiod;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public VAT getVat() {
		return vat;
	}

	public void setVat(VAT vat) {
		this.vat = vat;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public LocalDate getStart() {
		return start;
	}

	public double getDiscount() {
		return discount;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", subperiod=" + subperiod + ", start=" + start + ", end=" + end + ", price="
				+ price + ", vat=" + vat + ", discount=" + discount + ", isEnabled=" + isEnabled + "]";
	}
   //"ID", "Prezzo","IVA","Periodo", "Inizio", "Fine", "Sconto applicato", "Cliente", "Abilitata"
	public Object[] toTableRow() {
		return new Object[] {
				id, price, vat, subperiod, start, end, discount, SubscriptionDAO.getCustomerOfSubscription(id).orElse(null), isEnabled
		};
	}

}
