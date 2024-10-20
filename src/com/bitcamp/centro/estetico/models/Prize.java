package com.bitcamp.centro.estetico.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class Prize implements Model{
    private final int id;
    private String name;
    private int threshold;
    private Optional<LocalDate> expirationDate; //can be null
    private PrizeType type;
    private double amount;
    private boolean isEnabled;

    // Costruttore
    private Prize(int id, String name, int threshold, PrizeType type, Optional<LocalDate> expirationDate, double amount, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.threshold = threshold;
        this.type = type;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.isEnabled = isEnabled;
    }

    public Prize(String name, int threshold, PrizeType type, LocalDate expirationDate, double amount) {
    	this(-1, name, threshold, type, Optional.ofNullable(expirationDate), amount, true);
    }

    // Costruttore vuoto
    public Prize() {
        this(-1, "PLACEHOLDER", 0, PrizeType.DISCOUNT, Optional.empty(), 0, true);
    }

    public Prize(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1),
			rs.getString(2),
			rs.getInt(3),
			PrizeType.toEnum(rs.getString(4)),
			Optional.empty(),
			rs.getDouble(5),
			rs.getBoolean(6)
		);
    }

	public Prize(int id, Prize obj) {
		this(id, obj.name, obj.threshold, obj.type, obj.expirationDate, obj.amount, obj.isEnabled);
	}

	@Override
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public LocalDate getExpirationDate() {
		return expirationDate.orElse(null);
	}

	public PrizeType getType() {
		return type;
	}

	public void setType(PrizeType type) {
		this.type = type;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = Optional.of(expirationDate);
	}

	public void assignToCustomer(Customer customer) {
		customer.addPrizes(this);
	}

	public int addPointsToCustomer(Customer customer, int points) {
		customer.addLoyaltyPoints(points);
		return customer.getLoyaltyPoints();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		return "Prize [id=" + id + ", name=" + name + ", type=" + type.toString() + ", threshold=" + threshold +
				", expirationDate=" + expirationDate.orElse(null).format(dtf) +
				", amount=" + amount + ", isEnabled=" + isEnabled + "]";
	}

	//"ID", "Nome", "Punti Necessari", "Tipo", "€ in Buono", "Scadenza", "Abilitato"
	public Object[] toTableRow() {
		return new Object[] {
				id, name, threshold, type, amount, expirationDate, isEnabled
		};
	}
}
