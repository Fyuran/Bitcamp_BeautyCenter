package com.centro.estetico.bitcamp;
import java.time.*;


public class Reservation {
	private int id;
	private Customer customer;
	private Treatment treatment;
	private Employee employee;
	private LocalDateTime dateTime;
	private boolean isPaid;
	private ReservationState state;
	private boolean isEnabled;
	
	public Reservation() {
		this.isPaid = false;
		this.isEnabled = true;
		this.state = ReservationState.CREATED;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	public boolean isPaid() {
		return isPaid;
	}


	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}


	public ReservationState getState() {
		return state;
	}


	public void setState(ReservationState state) {
		this.state = state;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
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


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee beautician) {
		this.employee = beautician;
	}


	public LocalDateTime getDateTime() {
		return dateTime;
	}


	public void setDateTime(LocalDateTime dateTime) {		
		this.dateTime = dateTime;
	}
	
		
}
