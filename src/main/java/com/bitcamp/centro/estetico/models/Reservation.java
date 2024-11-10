package com.bitcamp.centro.estetico.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation implements Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "treatment_id", nullable = false)
	private Treatment treatment;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(
		name = "reservation_employee", 
		joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false), 
		inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
	)
	private List<Employee> employees;

	private LocalDateTime dateTime;

	private ReservationState state;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Reservation() {
		this.isEnabled = true;
		employees = new ArrayList<>();
	}

	public Reservation(Customer customer, Treatment treatment, List<Employee> employees, LocalDateTime dateTime,
			ReservationState state) {
		this(null, customer, treatment, employees, dateTime, state, true);
	}

	public Reservation(Long id, Customer customer, Treatment treatment, List<Employee> employees,
			LocalDateTime dateTime, ReservationState state, boolean isEnabled) {
		this.id = id;
		this.customer = customer;
		this.treatment = treatment;
		this.employees = employees;
		this.dateTime = dateTime;
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
		if (customer == null)
			return new Customer();
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Treatment getTreatment() {
		if (treatment == null)
			return new Treatment();
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public List<Employee> getEmployees() {
		if (employees == null)
			return new ArrayList<>();
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
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

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((treatment == null) ? 0 : treatment.hashCode());
		result = prime * result + ((employees == null) ? 0 : employees.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (treatment == null) {
			if (other.treatment != null)
				return false;
		} else if (!treatment.equals(other.treatment))
			return false;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (state != other.state)
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();

		JButton showCustomers = new JButton("Clienti");
		JButton showTreatments = new JButton("Trattamenti");
		JButton showEmployees = new JButton("Operatori");

		showCustomers.addActionListener(l -> {
			ModelViewer<Customer> picker = new ModelViewer<>("Clienti",
					ListSelectionModel.SINGLE_SELECTION, getCustomer());
			picker.setVisible(true);
		});
		showTreatments.addActionListener(l -> {
			ModelViewer<Treatment> picker = new ModelViewer<>("Trattamenti",
					ListSelectionModel.SINGLE_SELECTION, getTreatment());
			picker.setVisible(true);
		});
		showEmployees.addActionListener(l -> {
			ModelViewer<Employee> picker = new ModelViewer<>("Operatori",
					ListSelectionModel.SINGLE_SELECTION, getEmployees());
			picker.setVisible(true);
		});

		map.put("ID", id);
		map.put("Cliente", showCustomers);
		map.put("Trattamento", showTreatments);
		map.put("Operatori", showEmployees);
		map.put("Data E Orario", dateTime);
		map.put("Stato", state);
		map.put("Abilitato", isEnabled);

		return map;
	}

}
