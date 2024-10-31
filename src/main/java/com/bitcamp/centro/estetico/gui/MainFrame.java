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

public class MainFrame {
	private static JTabbedPane mainPane;
	private static Employee sessionUser;
	private static JFrame mainFrame;
	
	private static CompletableFuture<List<Treatment>> future1 = CompletableFuture.supplyAsync(() -> TreatmentDAO.getInstance().getAll());
	private static CompletableFuture<List<Product>> future2 = CompletableFuture.supplyAsync(() -> ProductDAO.getInstance().getAll());
	private static CompletableFuture<List<Customer>> future3 = CompletableFuture.supplyAsync(() -> CustomerDAO.getInstance().getAll());
	private static CompletableFuture<List<Employee>> future4 = CompletableFuture.supplyAsync(() -> EmployeeDAO.getInstance().getAll());
	private static CompletableFuture<List<Prize>> future5 = CompletableFuture.supplyAsync(() -> PrizeDAO.getInstance().getAll());
	private static CompletableFuture<List<Subscription>> future6 = CompletableFuture.supplyAsync(() -> SubscriptionDAO.getInstance().getAll());
	private static CompletableFuture<List<Transaction>> future7 = CompletableFuture.supplyAsync(() -> TransactionDAO.getInstance().getAll());
	private static CompletableFuture<List<UserCredentials>> future8 = CompletableFuture.supplyAsync(() -> UserCredentialsDAO.getInstance().getAll());
	private static CompletableFuture<List<VAT>> future9 = CompletableFuture.supplyAsync(() -> VAT_DAO.getInstance().getAll());

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
		mainFrame = new JFrame("Centro Estetico Manager");
		
		mainFrame.setSize(1024, 768);
		mainFrame.setMinimumSize(new Dimension(1024, 768));
		mainFrame.setLocationRelativeTo(null);

		CompletableFuture.runAsync(() -> mainPane.add(new UserAccessPanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new CustomerPanel())).exceptionally(t -> {t.printStackTrace(); return null;});

		if (employee.getRole() == Roles.ADMIN) {
			buildAdminFrame();
		}
		mainFrame.add(mainPane);
		

		mainPane.addChangeListener(e -> {
			JPanel panel = (JPanel) mainPane.getSelectedComponent();
			if(panel instanceof BasePanel p) {
				p.refreshTable();
			}
		});

		BasePanel.parent = mainFrame;
		mainFrame.setVisible(true);
	}

	public static void buildAdminFrame() {
		CompletableFuture.runAsync(() -> mainPane.add(new EmployeePanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new PrizePanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new ProductPanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new TreatmentPanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new TransactionPanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new SubscriptionPanel())).exceptionally(t -> {t.printStackTrace(); return null;});
		CompletableFuture.runAsync(() -> mainPane.add(new VATPanel())).exceptionally(t -> {t.printStackTrace(); return null;});
	}

	public static JFrame getMainFrame() {
		return mainFrame;
	}

	public static Employee getSessionUser() {
		return sessionUser;
	}
}
