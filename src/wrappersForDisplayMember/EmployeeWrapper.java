package wrappersForDisplayMember;

import com.centro.estetico.bitcamp.Employee;

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