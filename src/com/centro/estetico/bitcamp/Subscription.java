package com.centro.estetico.bitcamp;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Subscription {
    private int id;
    private Duration duration;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal price;
    private double vat;
    private double discount;
    private boolean isEnabled;
   
  
    public enum Duration {
        Monthly, Quarterly, HalfYear, Year;
    }

    public Subscription(int id, Duration duration,LocalDate start, BigDecimal price, double vat, double discount, boolean isEnabled) {
        this.id = id;
        this.duration = duration;
        this.start=start;
        this.end = calculateDuration(start, duration);
        this.price = price;
        this.vat = vat;    
        this.discount = discount;
        this.isEnabled = isEnabled;
    }

    //Calcoliamo la durata
    private static LocalDate calculateDuration(LocalDate start, Duration duration) {

        switch (duration) {
            case Monthly:
                return start.plusMonths(1);
            case Quarterly:
                return start.plusMonths(3);
            case HalfYear:
                return start.plusMonths(6);
            case Year:
                return start.plusMonths(12);
            default:
                throw new IllegalArgumentException("Durata non valida: " + duration);
        }
    }
    
    
    
    // Getter e Setter

    public void setStart(LocalDate start) {
        this.start = start;
        //A questo punto cambia anche la fine
        this.end = calculateDuration(start, this.duration);
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


	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
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

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
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
		return "Subscription [id=" + id + ", duration=" + duration + ", start=" + start + ", end=" + end + ", price="
				+ price + ", vat=" + vat + ", discount=" + discount + ", isEnabled=" + isEnabled + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
   
    
    
    
    
    
    
    
}
