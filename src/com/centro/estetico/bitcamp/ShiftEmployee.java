package com.centro.estetico.bitcamp;

public class ShiftEmployee {
	private int id;
	private Shift shift;
	private Employee employee;
	
	public ShiftEmployee(Shift shift, Employee employee) {		
		this.shift = shift;
		this.employee = employee;
	}
	
	public ShiftEmployee(int id, Shift shift, Employee employee) {
		this.id = id;
		this.shift = shift;
		this.employee = employee;
	}
	
	public ShiftEmployee() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Shift getShift() {
		return shift;
	}
	
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
