package com.bitcamp.centro.estetico.models;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation implements Model {
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "treatment_id", nullable = false)
	private Treatment treatment;

	@OneToMany
	private List<Employee> employees;

	private LocalDate date;
	
	private LocalTime time;

	private ReservationState state;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "false")
	private boolean isEnabled;

	public Reservation() {
		this.id = -1L;
		this.customer = null;
		this.treatment = null;
		this.employees = null;
		this.date = null;
		this.time = null;
		this.state = null;
		this.isEnabled = false;
	}

	public Reservation(Long id, Customer customer, Treatment treatment, List<Employee> employees, LocalDate date,
			LocalTime time, ReservationState state, boolean isEnabled) {
		this.id = id;
		this.customer = customer;
		this.treatment = treatment;
		this.employees = employees;
		this.date = date;
		this.time = time;
		this.state = state;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public ReservationState getState() {
		return state;
	}

	public void setState(ReservationState state) {
		this.state = state;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
