package com.bitcamp.centro.estetico.wrappersForDisplayMember;
import com.bitcamp.centro.estetico.models.Customer;

public class CustomerWrapper {
    private Customer customer;    
    
    public CustomerWrapper(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return customer.getName() + " " + customer.getSurname();
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CustomerWrapper that = (CustomerWrapper) obj;
        return customer.equals(that.customer); // Assumendo che `Customer` abbia un `equals` appropriato
    }

    @Override
    public int hashCode() {
        return customer.hashCode();
    }
}

