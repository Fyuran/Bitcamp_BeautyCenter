package template;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.Employee;
import com.centro.estetico.bitcamp.Roles;

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
		setLocationRelativeTo(null);
		setName("Gestione " + (bc == null ? "Centro Estetico" : bc.getName()));

		mainPane.add(new UserAccessPanel(employee));
		mainPane.add(new ReservationForm());
		mainPane.add(new gestioneClienti());

		if (employee.getRole() == Roles.ADMIN) {
			buildAdminFrame();
		}
		add(mainPane);
		setVisible(true);

	}

	public static void buildAdminFrame() {
		mainPane.add(new EmployeePanel());
		mainPane.add(new gestionePremi());
		mainPane.add(new ProductPanel());
		mainPane.add(new gestioneTurni());
		mainPane.add(new TreatmentPanel());
		mainPane.add(new TransactionPanel());
		mainPane.add(new SubscriptionPanel());
	}

	public static JFrame getMainFrame() {
		return mainFrame;
	}

	public static Employee getSessionUser() {
		return sessionUser;
	}
}
