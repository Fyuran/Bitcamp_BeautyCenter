package template;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalTime;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.centro.estetico.bitcamp.BeautyCenter;
import com.centro.estetico.bitcamp.VAT;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import DAO.BeautyCenterDAO;
import DAO.EmployeeDAO;
import DAO.VATDao;
import utils.inputValidator;
import utils.placeholderHelper;

public class SetupBeautyCenterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTextField txfName;
	private static JTextField txfPhone;
	private static JTextField txfPEC;
	private static JTextField txfEmail;
	private static JTextField txfSedeLegale;
	private static JTextField txfSedeOperativa;
	private static JTextField txfRea;
	private static JTextField txfPIva;
	private static TimePicker aperturaPicker;
	private static TimePicker chiusuraPicker;
	private static TimePickerSettings timePickerSettings;
	private static JButton btnInserisci;
	private static JButton btnAnnulla;
	private static JButton btnNext;

	public SetupBeautyCenterFrame() {
		setTitle("Benvenuto nel Gestionale Centro Estetico");
		setName("Benvenuto nel Gestionale Centro Estetico");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1024, 570);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);

		JLabel titleDatiFiscali = new JLabel("INIZIALIZZA IL TUO CENTRO - INSERISCI  I DATI FISCALI");
		titleDatiFiscali.setHorizontalAlignment(SwingConstants.CENTER);
		titleDatiFiscali.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		titleDatiFiscali.setBounds(10, 11, 988, 32);
		add(titleDatiFiscali);

		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblName.setBounds(45, 184, 170, 14);
		add(lblName);

		txfName = new JTextField();
		txfName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		placeholderHelper.addPlaceholder(txfName, "*");
		txfName.setColumns(10);
		txfName.setBounds(219, 183, 220, 20);
		add(txfName);

		JLabel lblPhone = new JLabel("Contatto Telefonico:");
		lblPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPhone.setBounds(45, 226, 170, 14);
		add(lblPhone);

		txfPhone = new JTextField();
		txfPhone.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		txfPhone.setColumns(10);
		txfPhone.setBounds(219, 225, 220, 20);
		add(txfPhone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblEmail.setBounds(45, 310, 170, 14);
		add(lblEmail);

		txfEmail = new JTextField();
		txfEmail.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		txfEmail.setColumns(10);
		txfEmail.setBounds(219, 309, 220, 20);
		add(txfEmail);

		JLabel lblPEC = new JLabel("PEC:");
		lblPEC.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPEC.setBounds(45, 268, 170, 14);
		add(lblPEC);

		txfPEC = new JTextField();
		txfPEC.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		txfPEC.setColumns(10);
		txfPEC.setBounds(219, 267, 220, 20);
		add(txfPEC);

		JLabel lblSedeLegale = new JLabel("Sede Legale:");
		lblSedeLegale.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSedeLegale.setBounds(45, 352, 170, 14);
		add(lblSedeLegale);

		txfSedeLegale = new JTextField();
		txfSedeLegale.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		placeholderHelper.addPlaceholder(txfSedeLegale, "*");
		txfSedeLegale.setColumns(10);
		txfSedeLegale.setBounds(219, 351, 220, 20);
		add(txfSedeLegale);

		JLabel lblSedeOperativa = new JLabel("Sede Operativa:");
		lblSedeOperativa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblSedeOperativa.setBounds(45, 394, 170, 14);
		add(lblSedeOperativa);

		txfSedeOperativa = new JTextField();
		txfSedeOperativa.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		placeholderHelper.addPlaceholder(txfSedeOperativa, "*");
		txfSedeOperativa.setColumns(10);
		txfSedeOperativa.setBounds(219, 393, 220, 20);
		add(txfSedeOperativa);

		JLabel lblREA = new JLabel("REA:");
		lblREA.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblREA.setBounds(544, 184, 170, 14);
		add(lblREA);

		txfRea = new JTextField();
		txfRea.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		placeholderHelper.addPlaceholder(txfRea, "*");
		txfRea.setColumns(10);
		txfRea.setBounds(724, 183, 220, 20);
		add(txfRea);

		JLabel lblPIva = new JLabel("Partita IVA:");
		lblPIva.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblPIva.setBounds(544, 228, 170, 14);
		add(lblPIva);

		txfPIva = new JTextField();
		txfPIva.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		placeholderHelper.addPlaceholder(txfPIva, "*");
		txfPIva.setColumns(10);
		txfPIva.setBounds(724, 225, 220, 20);
		add(txfPIva);

		JLabel lblApertura = new JLabel("Orario Apertura:");
		lblApertura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblApertura.setBounds(544, 268, 170, 14);
		add(lblApertura);

		timePickerSettings = new TimePickerSettings();
		timePickerSettings.setAllowEmptyTimes(false);
		aperturaPicker = new TimePicker(timePickerSettings);
		aperturaPicker.setBounds(724, 267, 220, 25);
		add(aperturaPicker);

		JLabel lblChiusura = new JLabel("Orario Chiusura:");
		lblChiusura.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblChiusura.setBounds(544, 310, 170, 14);
		add(lblChiusura);

		chiusuraPicker = new TimePicker(timePickerSettings);
		chiusuraPicker.setBounds(724, 309, 220, 25);
		add(chiusuraPicker);

		btnNext = new JButton("AVANTI");
		btnNext.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		btnNext.setBackground(new Color(64, 64, 64)); // green new Color(0, 204, 102)
		btnNext.addActionListener(e -> {
			if (EmployeeDAO.isEmpty()) {
				new SetupFirstAccountFrame();
			} else {
				new LoginFrame();
			}
			dispose();
		});
		btnNext.setBounds(388, 490, 220, 23);
		btnNext.setEnabled(false);
		add(btnNext);

		btnInserisci = new JButton("Inserisci");
		btnInserisci.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		btnInserisci.setBounds(388, 450, 100, 30);
		add(btnInserisci);
		if (!BeautyCenterDAO.isEmpty()) {
			btnInserisci.setEnabled(false);
			btnNext.setEnabled(true);
		}

		btnInserisci.addActionListener(e -> {
			if (!validateInputs()) {
				JOptionPane.showMessageDialog(null, inputValidator.getErrorMessage());
				return;
			}

			try {
				// Estrai i dati dai campi di input
				String name = txfName.getText();
				String phone = txfPhone.getText();
				String certifiedMail = txfPEC.getText();
				String email = txfEmail.getText();
				String registeredOffice = txfSedeLegale.getText();
				String operatingOffice = txfSedeOperativa.getText();
				String rea = txfRea.getText().trim();
				String pIva = txfPIva.getText();
				LocalTime openingHour = aperturaPicker.getTime();
				LocalTime closingHour = chiusuraPicker.getTime();

				// Crea VAT di default e inseriscili nel database
				// https://www.agenziaentrate.gov.it/portale/web/guest/iva-regole-generali-aliquote-esenzioni-pagamento/norme-generali-e-aliquote
				VATDao.insertVAT(new VAT(4));
				VATDao.insertVAT(new VAT(5));
				VATDao.insertVAT(new VAT(10));
				VATDao.insertVAT(new VAT(22));

				// Crea un nuovo oggetto BeautyCenter
				/*
				 * String name, String phone, String certifiedMail, String mail, String
				 * registeredOffice, String operatingOffice, String REA, String P_IVA, LocalTime
				 * openingHour, LocalTime closingHour
				 *
				 */
				BeautyCenter beautyCenter = new BeautyCenter(name, phone, certifiedMail, email, registeredOffice,
						operatingOffice, rea, pIva, openingHour, closingHour);

				Optional<BeautyCenter> result = BeautyCenterDAO.insertBeautyCenter(beautyCenter);
				if (result.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dei dati.");
					return;
				}

				btnInserisci.setEnabled(false);
				btnAnnulla.setEnabled(false);
				enableInputs(false);
				btnNext.setEnabled(true);
				btnNext.setBackground(new Color(0, 204, 102));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Errore durante l'inserimento: " + ex.getMessage());
			}
		});

		btnAnnulla = new JButton("Annulla");
		btnAnnulla.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		btnAnnulla.setBounds(508, 450, 100, 30);
		add(btnAnnulla);

		// Listener per il pulsante "Annulla"
		btnAnnulla.addActionListener(e -> clearFields());
	}

	// Metodo per svuotare i campi di input
	private static void clearFields() {
		txfName.setText("");
		txfPhone.setText("");
		txfPEC.setText("");
		txfEmail.setText("");
		txfSedeLegale.setText("");
		txfSedeOperativa.setText("");
		txfRea.setText("");
		txfPIva.setText("");
		aperturaPicker.setTextFieldToValidStateIfNeeded();
		chiusuraPicker.setTextFieldToValidStateIfNeeded();
	}

	// Metodo per attivare/disattivare i campi di input
	private static void enableInputs(boolean toggle) {
		txfName.setEnabled(toggle);
		txfPhone.setEnabled(toggle);
		txfPEC.setEnabled(toggle);
		txfEmail.setEnabled(toggle);
		txfSedeLegale.setEnabled(toggle);
		txfSedeOperativa.setEnabled(toggle);
		txfRea.setEnabled(toggle);
		txfPIva.setEnabled(toggle);
		aperturaPicker.setEnabled(toggle);
		chiusuraPicker.setEnabled(toggle);
	}

	// Metodo per validare gli input
	private static boolean validateInputs() {
		return inputValidator.validateAlphanumeric(txfName.getText(), "Nome")
				&& inputValidator.validatePhoneNumber(txfPhone.getText())
				&& inputValidator.validatePEC(txfPEC.getText()) && inputValidator.validateEmail(txfEmail.getText())
				&& inputValidator.validateAlphanumeric(txfSedeLegale.getText(), "Sede legale")
				&& inputValidator.validateAlphanumeric(txfSedeOperativa.getText(), "Sede operativa")
				&& inputValidator.validateAlphanumeric(txfRea.getText(), "REA")
				&& inputValidator.validatePIva(txfPIva.getText()) && inputValidator.validateRea(txfRea.getText())
				&& aperturaPicker.isTextFieldValid() && chiusuraPicker.isTextFieldValid()
				&& aperturaPicker.getTime().compareTo(chiusuraPicker.getTime()) < 0; // apertura must come BEFORE
																						// chiusura
	}
}
