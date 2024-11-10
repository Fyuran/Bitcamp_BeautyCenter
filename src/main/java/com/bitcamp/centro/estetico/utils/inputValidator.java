package com.bitcamp.centro.estetico.utils;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.bitcamp.centro.estetico.models.Model;

import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;

public class InputValidator {
	public static abstract class InputValidatorException extends RuntimeException {
		public final JComponent errorComponent;

		InputValidatorException(String message, JComponent errorComponent) {
			super(message);
			this.errorComponent = errorComponent;
			errorComponent.setBorder(new LineBorder(Color.RED));
		}
	}

	public static class EmptyInputException extends InputValidatorException {
		public EmptyInputException(String message, JComponent errorComponent) {
			super(message, errorComponent);
		}
	}

	public static class InvalidInputException extends InputValidatorException {
		public InvalidInputException(String message, JComponent errorComponent) {
			super(message, errorComponent);
		}
	}

	public static class RegexNoMatchException extends InputValidatorException {
		public RegexNoMatchException(String message, JComponent errorComponent) {
			super(message, errorComponent);
		}
	}

	public static void validateName(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new EmptyInputException("Il nome non può essere vuoto.", textField);
		}
		String regex = RegexConst.TEXT.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException(
					"Il nome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateName(JSplitTxf textField) {
		validateName(textField.getJTextField());
	}

	public static void validateSurname(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new EmptyInputException("Il cognome non può essere vuoto.", textField);
		}
		String regex = RegexConst.TEXT.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException(
					"Il cognome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateSurname(JSplitTxf textField) {
		validateSurname(textField.getJTextField());
	}

	public static void validatePhoneNumber(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new EmptyInputException("Il numero di telefono non può essere vuoto.", textField);
		}
		String regex = RegexConst.PHONE.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException(
					"Il numero di telefono deve contenere solo cifre e può includere un prefisso internazionale.",
					textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validatePhoneNumber(JSplitTxf textField) {
		validatePhoneNumber(textField.getJTextField());
	}

	public static void validateEmail(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new EmptyInputException("L'email non può essere vuota.", textField);
		}
		String regex = RegexConst.MAIL.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException("Formato email non valido.", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateEmail(JSplitTxf textField) {
		validateEmail(textField.getJTextField());
	}

	public static void validateCodiceFiscale(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new EmptyInputException("Il codice fiscale non può essere vuoto.", textField);
		}
		String regex = RegexConst.EU_TIN.toString();
		boolean result = Pattern.matches(regex, text.toUpperCase());
		if (!result) {
			throw new RegexNoMatchException("Il codice fiscale deve essere composto da 16 caratteri alfanumerici.",
					textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateCodiceFiscale(JSplitTxf textField) {
		validateCodiceFiscale(textField.getJTextField());
	}

	public static void validatePIva(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new EmptyInputException("La partita IVA non può essere vuota.", textField);
		}
		String regex = RegexConst.P_IVA.toString(); // Esattamente 11 cifre
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException("La partita IVA deve essere composta da 11 cifre numeriche.", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validatePIva(JSplitTxf textField) {
		validatePIva(textField.getJTextField());
	}

	public static void validateVAT(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new EmptyInputException("L'aliquota non può essere vuota.", textField);
		}
		try {
			double value = Double.parseDouble(text);
			boolean result = value > 0 && value <= 100 && Pattern.matches(RegexConst.VAT.toString(), text);
			if (!result) {
				throw new RegexNoMatchException("L'aliquota deve essere un numero tra 0 e 100 con massimo 2 decimali.",
						textField);
			}
		} catch (NumberFormatException e) {
			throw new InvalidInputException("L'aliquota deve essere un numero valido.", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateVAT(JSplitTxf textField) {
		validateVAT(textField.getJTextField());
	}

	public static void validateNumber(JTextField textField) {
		String text = textField.getText().trim();
		NumberFormat nf = NumberFormat.getInstance();
		try {
			nf.parse(text);
		} catch (ParseException | InvalidInputException e) {
			throw new InvalidInputException("Formato numero non valido", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateNumber(JSplitTxf textField) {
		validateNumber(textField.getJTextField());
	}

	public static void validateNumber(JTextField textField, int min, int max) {
		String text = textField.getText().trim();
		NumberFormat nf = NumberFormat.getInstance();
		Number number;
		try {
			number = nf.parse(text);
		} catch (ParseException e) {
			throw new InvalidInputException("Formato numero non valido", textField);
		}
		boolean result = number.doubleValue() >= min && number.doubleValue() <= max;
		if (!result) {
			if(max == Integer.MAX_VALUE) {
				throw new InvalidInputException("Il numero deve essere positivo", textField);
			}
			else if(max == Integer.MIN_VALUE) {
				throw new InvalidInputException("Il numero deve essere negativo", textField);
			} else {
				throw new InvalidInputException("Il numero deve essere compreso tra " + min + " e " + max + ".", textField);
			}
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateNumber(JSplitTxf textField, int min, int max) {
		validateNumber(textField.getJTextField(), min, max);
	}

	public static void validateAlphanumeric(JTextField textField, String title) {
		String text = textField.getText().trim();
		if (title == null) {
			title = "input";
		}
		if (text == null || text.isEmpty()) {
			throw new EmptyInputException(title + " non può essere vuoto.", textField);
		}
		String regex = RegexConst.ALPHANUMERIC.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException(
					title + " può contenere solo lettere, numeri, spazi e alcuni caratteri speciali.", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateAlphanumeric(JSplitTxf textField, String title) {
		validateAlphanumeric(textField.getJTextField(), title);
	}

	// min 8 chars: min 1 word, 1 number and 1 special char
	public static void validatePassword(JPasswordField textField) {
		char[] password = textField.getPassword();
		if (password == null || password.length == 0) {
			throw new EmptyInputException("La password non può essere vuota.", textField);
		}
		String regex = RegexConst.PASSWORD.toString();
		boolean result = Pattern.matches(regex, String.valueOf(password));
		if (!result) {
			throw new RegexNoMatchException(
					"La password deve essere lunga almeno 8 caratteri e includere almeno 1 lettera, 1 numero e 1 carattere speciale.",
					textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validatePassword(JSplitPf passwordField) {
		validatePassword(passwordField.getJPasswordField());
	}

	public static void validateIban(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new EmptyInputException("Il campo IBAN non può essere vuote", textField);
		}
		String regex = RegexConst.IBAN.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException("L'IBAN deve essere lungo 27 caratteri. ex: IT60X0542811101000000123456",
					textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateIban(JSplitTxf textField) {
		validateIban(textField.getJTextField());
	}

	public static void validateRea(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new EmptyInputException("Il campo REA non può essere vuoto.", textField);
		}
		String regex = RegexConst.REA.toString(); // 1 a 10 caratteri
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new RegexNoMatchException("Il codice REA deve essere composto da massimo 10 caratteri. ex: RM-123456",
					textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void validateRea(JSplitTxf textField) {
		validateRea(textField.getJTextField());
	}

	public static void isValidCity(JTextField textField) {
		String text = textField.getText().trim();
		CityByName cities = CityProvider.ofDefault();
		try {
			cities.findByName(text);
		} catch (IllegalArgumentException e) {
			throw new InvalidInputException("Città non trovata", textField);
		}
		textField.setBorder(UIManager.getBorder("SplitPane.border"));
	}

	public static void isValidCity(JSplitTxf textField) {
		isValidCity(textField.getJTextField());
	}

	public static <T extends Model> void validateBtn(JSplitBtn jSplitBtn, List<T> returnElements) {
		if(returnElements == null || returnElements.isEmpty())
			throw new InvalidInputException("Dati non validi", jSplitBtn);
		jSplitBtn.setBorder(UIManager.getBorder("SplitPane.border"));
	}
}
