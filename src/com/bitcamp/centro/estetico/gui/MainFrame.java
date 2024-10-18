package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;

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
	
	public MainFrame(Employee employee, BeautyCenter bc) {
		mainPane = new JTabbedPane();
		sessionUser = employee;
		mainFrame = this;
		
		setSize(1024, 768);
		setMinimumSize(new Dimension(1024, 768));
		setLocationRelativeTo(null);
		setTitle("Gestione " + (bc == null ? "Centro Estetico" : bc.getName()));

		//CompletableFuture.runAsync(() -> mainPane.add(new UserAccessPanel()));
		mainPane.add(new UserAccessPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new ReservationForm()));
		CompletableFuture.runAsync(() -> mainPane.add(new CustomerPanel()));

		if (employee.getRole() == Roles.ADMIN) {
			buildAdminFrame();
		}
		add(mainPane);
		setVisible(true);

		mainPane.addChangeListener(e -> {
			JPanel panel = (JPanel) mainPane.getSelectedComponent();
			if(panel instanceof BasePanel p) p.populateTable();
		});
	}

	public static void buildAdminFrame() {
		CompletableFuture.runAsync(() -> mainPane.add(new EmployeePanel()));
		//mainPane.add(new EmployeePanel());
		CompletableFuture.runAsync(() -> mainPane.add(new PrizePanel()));
		CompletableFuture.runAsync(() -> mainPane.add(new ProductPanel()));
		CompletableFuture.runAsync(() -> mainPane.add(new TreatmentPanel()));
		//mainPane.add(new TreatmentPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new TransactionPanel()));
		//mainPane.add(new TransactionPanel());
		CompletableFuture.runAsync(() -> mainPane.add(new SubscriptionPanel()));
		CompletableFuture.runAsync(() -> mainPane.add(new ShiftForm()));
	}

	public static JFrame getMainFrame() {
		return mainFrame;
	}

	public static Employee getSessionUser() {
		return sessionUser;
	}
}
