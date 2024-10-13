package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.centro.estetico.bitcamp.Main;

import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;

public class inputValidator {

	// Variabile per il messaggio di errore
	private static String errorMessage = "";

	// Metodo per ottenere il messaggio di errore
	public static String getErrorMessage() {
		return errorMessage;
	}

	// Metodo per validare nomi
	public static boolean validateName(String name) {
		name = name.trim();
		if (name.isBlank() || name.isEmpty()) {
			errorMessage = "Il nome non può essere vuoto.";
			return false;
		}
		String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$";
		boolean result = Pattern.matches(regex, name);
		if (!result) {
			errorMessage = "Il nome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.";
		}

		return result;
	}

	// Metodo per validare cognomi
	public static boolean validateSurname(String name) {
		name = name.trim();
		if (name.isBlank() || name.isEmpty()) {
			errorMessage = "Il cognome non può essere vuoto.";
			return false;
		}
		String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$";
		boolean result = Pattern.matches(regex, name);
		if (!result) {
			errorMessage = "Il cognome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.";
		}

		return result;
	}

	// Metodo per validare numeri di telefono
	public static boolean validatePhoneNumber(String phoneNumber) {
		phoneNumber = phoneNumber.trim();
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			errorMessage = "Il numero di telefono non può essere vuoto.";
			return false;
		}
		String regex = "^(\\+\\d{1,3}[- ]?)?\\d{8,15}$";
		boolean result = Pattern.matches(regex, phoneNumber);
		if (!result) {
			errorMessage = "Il numero di telefono deve contenere solo cifre e può includere un prefisso internazionale.";
		}
		return result;
	}

	// Metodo per validare indirizzi email
	public static boolean validateEmail(String email) {
		email = email.trim();
		if (email == null || email.isEmpty()) {
			errorMessage = "L'email non può essere vuota.";
			return false;
		}
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		boolean result = Pattern.matches(regex, email);
		if (!result) {
			errorMessage = "Formato email non valido.";
		}
		return result;
	}

	// Metodo per validare PEC (stesso formato delle email)
	public static boolean validatePEC(String pec) {
		return validateEmail(pec);
	}

	// Metodo per validare codice fiscale (Italiano, 16)
	public static boolean validateCodiceFiscale(String codiceFiscale) {
		codiceFiscale = codiceFiscale.trim();
		if (codiceFiscale == null || codiceFiscale.isEmpty()) {
			errorMessage = "Il codice fiscale non può essere vuoto.";
			return false;
		}
		String regex = "^[A-Z0-9]{16}$";
		boolean result = Pattern.matches(regex, codiceFiscale.toUpperCase());
		if (!result) {
			errorMessage = "Il codice fiscale deve essere composto da 16 caratteri alfanumerici.";
		}
		return result;
	}

	// Metodo per validare partita IVA (11 numeri)
	public static boolean validatePIva(String pIva) {
		pIva = pIva.trim();
		if (pIva == null || pIva.isEmpty()) {
			errorMessage = "La partita IVA non può essere vuota.";
			return false;
		}
		String regex = "^\\d{11}$"; // Esattamente 11 cifre
		boolean result = Pattern.matches(regex, pIva);
		if (!result) {
			errorMessage = "La partita IVA deve essere composta da 11 cifre numeriche.";
		}
		return result;
	}

	// Metodo per validare l'aliquota (percentuale tra 0 e 100 con massimo 2
	// decimali)
	public static boolean validateAliquota(String aliquota) {
		aliquota = aliquota.trim();
		if (aliquota == null || aliquota.isEmpty()) {
			errorMessage = "L'aliquota non può essere vuota.";
			return false;
		}
		try {
			double value = Double.parseDouble(aliquota);
			boolean result = value >= 0 && value <= 100 && Pattern.matches("^\\d{1,3}(\\.\\d{1,2})?$", aliquota);
			if (!result) {
				errorMessage = "L'aliquota deve essere un numero tra 0 e 100 con massimo 2 decimali.";
			}
			return result;
		} catch (NumberFormatException e) {
			errorMessage = "L'aliquota deve essere un numero valido.";
			return false;
		}
	}

	// Metodo per validare orari (HH:mm formato 24 ore)
	public static boolean validateTime(String time) {
		time = time.trim();
		if (time == null || time.isEmpty()) {
			errorMessage = "L'orario non può essere vuoto.";
			return false;
		}
		String regex = "^([01]?\\d|2[0-3]):[0-5]\\d$";
		boolean result = Pattern.matches(regex, time);
		if (!result) {
			errorMessage = "L'orario deve essere nel formato HH:mm (24 ore).";
		}
		return result;
	}

	// Metodo per validare date (formato dd/MM/yyyy)
	public static boolean validateDate(String date) {
		date = date.trim();
		if (date == null || date.isEmpty()) {
			errorMessage = "La data non può essere vuota.";
			return false;
		}
		String regex = "^\\d{2}/\\d{2}/\\d{4}$";
		boolean result = Pattern.matches(regex, date);
		if (!result) {
			errorMessage = "La data deve essere nel formato dd/MM/yyyy.";
		}
		return result;
	}

	// Metodo per validare numeri interi
	public static boolean validateInteger(int number, int min, int max) {
		boolean result = number >= min && number <= max;
		if (!result) {
			errorMessage = "Il numero deve essere compreso tra " + min + " e " + max + ".";
		}
		return result;
	}

	// Metodo per validare numeri decimali
	public static boolean validateDecimal(double number, double min, double max) {
		boolean result = number >= min && number <= max;
		if (!result) {
			errorMessage = "Il numero decimale deve essere compreso tra " + min + " e " + max + ".";
		}
		return result;
	}

	// Metodo per validare input alfanumerici generici
	public static boolean validateAlphanumeric(String input, String title) {
		input = input.trim();
		if (title == null) {
			title = "input";
		}
		if (input == null || input.isEmpty()) {
			errorMessage = title + " non può essere vuoto.";
			return false;
		}
		String regex = "^[A-Za-z0-9\\s,.'-]{2,100}$";
		boolean result = Pattern.matches(regex, input);
		if (!result) {
			errorMessage = title + " può contenere solo lettere, numeri, spazi e alcuni caratteri speciali.";
		}
		return result;
	}

	// Metodo per validare password (min 8 caratteri, almeno 1 lettera, 1 numero e 1
	// carattere speciale)
	public static boolean validatePassword(String password) {
		password = password.trim();
		if (password == null || password.isEmpty()) {
			errorMessage = "La password non può essere vuota.";
			return false;
		}
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		boolean result = Pattern.matches(regex, password);
		if (!result) {
			errorMessage = "La password deve essere lunga almeno 8 caratteri e includere almeno 1 lettera, 1 numero e 1 carattere speciale.";
		}
		return result;
	}
	public static boolean validatePassword(char[] password) {
		return validatePassword(String.valueOf(password));
	}

	// metodo per validare iban
	public static boolean validateIban(String iban, boolean isIbanMandatory) {
		iban = iban.trim();
		if (isIbanMandatory) {
			if (iban.isEmpty()) {
				errorMessage = "L'IBAN deve essere lungo 27 caratteri";
				return false;
			}
		}
		if (iban.length() > 27) {
			errorMessage = "L'IBAN deve essere lungo 27 caratteri";
			return false;
		}
		String regex = "IT\\d{2}[ ][a-zA-Z]\\d{3}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{3}|IT\\d{2}[a-zA-Z]\\d{22}";
		boolean result = Pattern.matches(regex, iban);
		if (!result) {
			errorMessage = "Formato IBAN non valido";
		}
		return result;
	}

	// controllo che lo username sia unico:
	public static boolean isUserUnique(String username) {
		String query = "SELECT * FROM beauty_centerdb.user_credentials WHERE username=? LIMIT 1";
		String name = "";
		Connection conn = Main.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString("username");

			}

			return name.equals("");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

  	public static boolean isTreatmentNameUnique(String name) {
		String query="SELECT * FROM beauty_centerdb.treatment WHERE type=? LIMIT 1";
		Connection conn=Main.getConnection();
		try(PreparedStatement pstmt = conn.prepareStatement(query)){
			pstmt.setString(1, name);
			ResultSet rs=pstmt.executeQuery();
			return !rs.next();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean validateRea(String text) {
		text = text.trim();
		String regex = "^\\S{1,10}"; // 1 a 10 caratteri
		if (text.matches(regex)) {
			return true;
		}
		errorMessage = "Il codice REA deve essere composto da massimo 10 caratteri. ex: RM-123456";

		return false;
	}

	public static boolean isValidCity(String city) {
		CityByName cities = CityProvider.ofDefault();
		try {
			cities.findByName(city);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			errorMessage = "Città " + city + " non trovata.";
			return false;
		}
		return true;
	}
}
