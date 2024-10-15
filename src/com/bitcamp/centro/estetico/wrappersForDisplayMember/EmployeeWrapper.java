package com.bitcamp.centro.estetico.wrappersForDisplayMember;

import com.bitcamp.centro.estetico.models.Employee;

public class EmployeeWrapper {
    private Employee employee;

    public EmployeeWrapper(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String toString() {        
        return employee.getName() + " " + employee.getSurname();
    }
}