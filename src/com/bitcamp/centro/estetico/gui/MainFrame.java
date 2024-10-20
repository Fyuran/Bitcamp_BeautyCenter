package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.bitcamp.centro.estetico.DAO.*;
import com.bitcamp.centro.estetico.models.*;

// EmployeePanel per aggiungere operatori -> Solo admin
// gestionePremi per creare premi -> Solo admin
// ProductPanel per creare prodotti -> Solo admin
// gestioneTurni per settare turni -> Solo admin
// reportVenditePDF non è un panel ma un frame, dobbiamo vedere come gestirlo
// TreatmentPanel  creazione trattamenti -> Solo admin

// UserAccessPanel area personale -> Disponibile a tutti
// ReservationForm  prenotazione appuntamenti - >Disponibile a tutti
// gestioneClienti per aggiungere clienti ->Disponibile a tutti

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTabbedPane mainPane;
	private static Employee sessionUser;
	private static JFrame mainFrame;
	
	static CompletableFuture<List<Treatment>> future1 = CompletableFuture.supplyAsync(() -> TreatmentDAO.getInstance().getAll());
	static CompletableFuture<List<Product>> future2 = CompletableFuture.supplyAsync(() -> ProductDAO.getInstance().getAll());
	static CompletableFuture<List<Customer>> future3 = CompletableFuture.supplyAsync(() -> CustomerDAO.getInstance().getAll());
	static CompletableFuture<List<Employee>> future4 = CompletableFuture.supplyAsync(() -> EmployeeDAO.getInstance().getAll());
	static CompletableFuture<List<Prize>> future5 = CompletableFuture.supplyAsync(() -> PrizeDAO.getInstance().getAll());
	static CompletableFuture<List<Subscription>> future6 = CompletableFuture.supplyAsync(() -> SubscriptionDAO.getInstance().getAll());
	static CompletableFuture<List<Transaction>> future7 = CompletableFuture.supplyAsync(() -> TransactionDAO.getInstance().getAll());
	static CompletableFuture<List<UserCredentials>> future8 = CompletableFuture.supplyAsync(() -> UserCredentialsDAO.getInstance().getAll());
	static CompletableFuture<List<VAT>> future9 = CompletableFuture.supplyAsync(() -> VAT_DAO.getInstance().getAll());

	static List<Treatment> treatments;
	static List<Product> products;
	static List<Customer> customers;
	static List<Employee> employees;
	static List<Prize> prizes;
	static List<Subscription> subscriptions;
	static List<Transaction> transactions;
	static List<UserCredentials> credentials;
	static List<VAT> vats;

	public MainFrame(Employee employee, BeautyCenter bc) {

		try {
			treatments = future1.get();
			products = future2.get();
			customers = future3.get();
			employees = future4.get();
			prizes = future5.get();
			subscriptions = future6.get();
			transactions = future7.get();
			credentials = future8.get();
			vats = future9.get();
		} catch (ExecutionException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Errore dati database",
					JOptionPane.ERROR_MESSAGE);
		}

		mainPane = new JTabbedPane();
		sessionUser = employee;
		mainFrame = this;
		
		setSize(1024, 768);
		setMinimumSize(new Dimension(1024, 768));
		setLocationRelativeTo(null);
		setTitle("Gestione " + (bc == null ? "Centro Estetico" : bc.getName()));

		CompletableFuture.runAsync(() -> mainPane.add(new UserAccessPanel()));
		//mainPane.add(new UserAccessPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new CustomerPanel()));
		//mainPane.add(new CustomerPanel());

		if (employee.getRole() == Roles.ADMIN) {
			buildAdminFrame();
		}
		add(mainPane);
		setVisible(true);

		mainPane.addChangeListener(e -> {
			JPanel panel = (JPanel) mainPane.getSelectedComponent();
			if(panel instanceof BasePanel p) {
				CompletableFuture.runAsync(() -> p.populateTable());
			}
		});
	}

	public static void buildAdminFrame() {
		CompletableFuture.runAsync(() -> mainPane.add(new EmployeePanel()));
		//mainPane.add(new EmployeePanel());
		CompletableFuture.runAsync(() -> mainPane.add(new PrizePanel()));
		//mainPane.add(new PrizePanel());
		CompletableFuture.runAsync(() -> mainPane.add(new ProductPanel()));
		//mainPane.add(new ProductPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new TreatmentPanel()));
		//mainPane.add(new TreatmentPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new TransactionPanel()));
		//mainPane.add(new TransactionPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new SubscriptionPanel()));
		//mainPane.add(new SubscriptionPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new VATPanel()));
	}

	public static JFrame getMainFrame() {
		return mainFrame;
	}

	public static Employee getSessionUser() {
		return sessionUser;
	}
}
