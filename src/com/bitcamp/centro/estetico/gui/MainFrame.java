package com.bitcamp.centro.estetico.gui;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTabbedPane mainPane;
	//crea account è un frame, bisogna correggerlo
	private creaAccount creaAccount; //pan per creare account -> Solo admin
	private EmployeePanel employeePanel;//pan per aggiungere operatori -> Solo admin
	private gestionePremi gestionePremi;//pan per creare premi -> Solo admin
	private ProductPanel productPanel;//pan per creare prodotti -> Solo admin
	private gestioneTurni gestioneTurni;//pan per settare turni ->Solo admin
	private ReportVenditePDF reportVenditePDF;//non è un panel ma un frame, dobbiamo vedere come gestirlo
	private TreatmentPanel treatmentPanel;//creazione trattamenti ->Solo admin
	
	private UserAccessPanel userAccessPanel;//area personale ->Disponibile a tutti
	private ReservationForm reservationForm;//prenotazione appuntamenti ->Disponibile a tutti
	private gestioneClienti gestioneClienti;//pan per aggiungere clienti ->Disponibile a tutti
	
	public MainFrame(Employee employee, BeautyCenter bc) {
		this.mainPane=new JTabbedPane();
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setName("Gestione "+(bc==null?"Centro Estetico":bc.getName()));
		userAccessPanel=new UserAccessPanel(employee);
		reservationForm=new ReservationForm();
		gestioneClienti=new gestioneClienti();
		
		mainPane.add(userAccessPanel);
		mainPane.add(reservationForm);
		mainPane.add(gestioneClienti);
		
		if(employee.getRole() == Roles.ADMIN){
			buildAdminFrame(employee);
		}
			add(mainPane);
			setVisible(true);
		
		


	}
	
	public void buildAdminFrame(Employee admin) {
		//creaAccount=new creaAccount();
		employeePanel=new EmployeePanel();
		gestionePremi=new gestionePremi();
		productPanel=new ProductPanel();
		//gestioneTransazioniEReport=new gestioneTransazioniEReport();
		gestioneTurni=new gestioneTurni();
		//reportVenditePDF=new ReportVenditePDF();
		treatmentPanel=new TreatmentPanel();
		//aggiungere nomi alle tab appuntamenti, clienti, premi e turni
		
		//mainPane.add(creaAccount);
		mainPane.add(employeePanel);
		mainPane.add(gestionePremi);
		mainPane.add(productPanel);
		//mainPane.add(gestioneTransazioniEReport);
		mainPane.add(gestioneTurni);
		mainPane.add(treatmentPanel);
	}
}
