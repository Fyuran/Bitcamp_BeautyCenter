package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.bitcamp.centro.estetico.DAO.VATDao;


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
        this.end = start==null?null:plusSubPeriod(start, subperiod);
        this.price = price;
        this.vat = vat;    
        this.discount = discount;
        this.isEnabled = isEnabled;
    }
    
    public Subscription(SubPeriod subperiod, LocalDate start, BigDecimal price, VAT vat, double discount, boolean isEnabled) {
        this.id = -1;
        this.subperiod = subperiod;
        this.start=start;
        this.end = start==null?null:plusSubPeriod(start, subperiod);
        this.price = price;
        this.vat = vat;    
        this.discount = discount;
        this.isEnabled = isEnabled;
    }
    
    public Subscription(ResultSet rs) throws SQLException {
    	this(
			rs.getInt(1), 
			SubPeriod.valueOf(rs.getString(2)), 
			null,
			rs.getBigDecimal(3), 
			VATDao.getVAT(rs.getInt(4)).get(), 
			rs.getDouble(5),
			rs.getBoolean(6)
			//nel database manca un campo per lo startdate!
//			 `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
//			  `subperiod` ENUM('MONTHLY', 'QUARTERLY', 'HALF_YEAR', 'YEARLY') NOT NULL,
//			  `price` FLOAT NOT NULL,
//			  `vat_id` INT UNSIGNED NULL,
//			  `discount` FLOAT NOT NULL,
//			  `is_enabled` TINYINT NOT NULL DEFAULT 1,
		);
    }
    
    public Subscription(int id, Subscription obj) {
		this(id, obj.subperiod, obj.start, obj.price, obj.vat, obj.discount, obj.isEnabled);
	}

	//Calcoliamo la durata
    private static LocalDate plusSubPeriod(LocalDate start, SubPeriod subperiod) {
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
   
	public Object[] toTableRow() {
		return new Object[] {
				id, subperiod, start, end, price, vat, discount, isEnabled
		};
	}  
    
}
