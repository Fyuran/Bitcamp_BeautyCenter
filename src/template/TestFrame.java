package template;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	TreatmentPanel panelT;
	EmployeePanel panelE;
	ProductPanel panelP;
	UserAccessPanel panelU;
	gestioneTurni panelG;
	public TestFrame() {
		panelU=new UserAccessPanel();
		panelT=new TreatmentPanel();
		panelE=new EmployeePanel();
		panelP=new ProductPanel();
		panelG=new gestioneTurni();
		
		setSize(1075, 768);
		JTabbedPane mainPane=new JTabbedPane();
		mainPane.add(panelU);
		mainPane.add(panelT);
		mainPane.add(panelE);
		mainPane.add(panelP);
		mainPane.add(panelG);
		add(mainPane);
		
		setName("Test Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new TestFrame();
//		new benvenutoOperatori();
		//new ReportVenditePDF();
	}

}
