package com.bitcamp.centro.estetico.utils;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;

public class inputValidator {
	public static abstract class inputValidatorException extends RuntimeException {
		public final JComponent errorComponent;
		inputValidatorException(String message, JComponent errorComponent) {
			super(message);
			this.errorComponent = errorComponent;
			errorComponent.setBorder(new LineBorder(Color.RED));
		}
	}
	public static class emptyInputException extends inputValidatorException {
		public emptyInputException(String message, JComponent errorComponent) {
			super(message, errorComponent);
		}
	}
	public static class invalidInputException extends inputValidatorException {
		public invalidInputException(String message, JComponent errorComponent) {
			super(message, errorComponent);
		}
	}
	public static class regexNoMatchException extends inputValidatorException {
		public regexNoMatchException(String message, JComponent errorComponent) {
			super(message, errorComponent);
		}
	}

	// Metodo per validare nomi
	public static void validateName(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new emptyInputException("Il nome non può essere vuoto.", textField);
		}
		String regex = RegexConst.TEXT.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("Il nome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateName(JSplitLbTxf textField) {
		validateName(textField.getJTextField());
	}

	// Metodo per validare cognomi
	public static void validateSurname(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new emptyInputException("Il cognome non può essere vuoto.", textField);
		}
		String regex = RegexConst.TEXT.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("Il cognome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateSurname(JSplitLbTxf textField) {
		validateSurname(textField.getJTextField());
	}

	// Metodo per validare numeri di telefono
	public static void validatePhoneNumber(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new emptyInputException("Il numero di telefono non può essere vuoto.", textField);
		}
		String regex = RegexConst.PHONE.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("Il numero di telefono deve contenere solo cifre e può includere un prefisso internazionale.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validatePhoneNumber(JSplitLbTxf textField) {
		validatePhoneNumber(textField.getJTextField());
	}

	// Metodo per validare indirizzi email
	public static void validateEmail(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new emptyInputException("L'email non può essere vuota.", textField);
		}
		String regex = RegexConst.MAIL.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("Formato email non valido.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateEmail(JSplitLbTxf textField) {
		validateEmail(textField.getJTextField());
	}

	// Metodo per validare codice fiscale (Italiano, 16)
	public static void validateCodiceFiscale(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new emptyInputException("Il codice fiscale non può essere vuoto.", textField);
		}
		String regex = RegexConst.EU_TIN.toString();
		boolean result = Pattern.matches(regex, text.toUpperCase());
		if (!result) {
			throw new regexNoMatchException("Il codice fiscale deve essere composto da 16 caratteri alfanumerici.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateCodiceFiscale(JSplitLbTxf textField) {
		validateCodiceFiscale(textField.getJTextField());
	}

	// Metodo per validare partita IVA (11 numeri)
	public static void validatePIva(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new emptyInputException("La partita IVA non può essere vuota.", textField);
		}
		String regex = RegexConst.P_IVA.toString(); // Esattamente 11 cifre
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("La partita IVA deve essere composta da 11 cifre numeriche.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validatePIva(JSplitLbTxf textField) {
		validatePIva(textField.getJTextField());
	}

	// Metodo per validare l'aliquota (percentuale tra 0 e 100 con massimo 2
	// decimali)
	public static void validateVAT(JTextField textField) {
		String text = textField.getText().trim();
		if (text == null || text.isEmpty()) {
			throw new emptyInputException("L'aliquota non può essere vuota.", textField);
		}
		try {
			double value = Double.parseDouble(text);
			boolean result = value > 0 && value <= 100 && Pattern.matches(RegexConst.VAT.toString(), text);
			if (!result) {
				throw new regexNoMatchException("L'aliquota deve essere un numero tra 0 e 100 con massimo 2 decimali.", textField);
			}
		} catch (NumberFormatException e) {
			throw new invalidInputException( "L'aliquota deve essere un numero valido.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateVAT(JSplitLbTxf textField) {
		validateVAT(textField.getJTextField());
	}
	
	// Metodo per validare numeri interi
	public static void validateNumber(JTextField textField, int min, int max) {
		String text = textField.getText().trim();
		NumberFormat nf = NumberFormat.getInstance();
		Number number;
		try {
			number = nf.parse(text);
		} catch (ParseException e) {
			throw new invalidInputException( "Formato numero non valido", textField);
		}
		boolean result = number.doubleValue() >= min && number.doubleValue() <= max;
		if (!result) {
			throw new invalidInputException( "Il numero deve essere compreso tra " + min + " e " + max + ".", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateNumber(JSplitLbTxf textField, int min, int max) {
		validateNumber(textField.getJTextField(), min, max);
	}

	// Metodo per validare input alfanumerici generici
	public static void validateAlphanumeric(JTextField textField, String title) {
		String text = textField.getText().trim();
		if (title == null) {
			title = "input";
		}
		if (text == null || text.isEmpty()) {
			throw new emptyInputException(title + " non può essere vuoto.", textField);
		}
		String regex = RegexConst.ALPHANUMERIC.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException(title + " può contenere solo lettere, numeri, spazi e alcuni caratteri speciali.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateAlphanumeric(JSplitLbTxf textField, String title) {
		validateAlphanumeric(textField.getJTextField(), title);
	}

	// Metodo per validare password (min 8 caratteri, almeno 1 lettera, 1 numero e 1
	// carattere speciale)
	public static void validatePassword(JPasswordField textField) {
		char[] password = textField.getPassword();
		if (password == null || password.length == 0) {
			throw new emptyInputException("La password non può essere vuota.",textField);
		}
		String regex = RegexConst.PASSWORD.toString();
		boolean result = Pattern.matches(regex, String.valueOf(password));
		if (!result) {
			throw new regexNoMatchException("La password deve essere lunga almeno 8 caratteri e includere almeno 1 lettera, 1 numero e 1 carattere speciale.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validatePassword(JSplitLbPf passwordField) {
		validatePassword(passwordField.getJPasswordField());
	}

	// metodo per validare iban
	public static void validateIban(JTextField textField) {
		String text = textField.getText().trim();
		if (text.isBlank() || text.isEmpty()) {
			throw new emptyInputException("Il campo IBAN non può essere vuote", textField);
		}
		String regex = RegexConst.IBAN.toString();
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException( "L'IBAN deve essere lungo 27 caratteri. ex: IT60X0542811101000000123456", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateIban(JSplitLbTxf textField) {
		validateIban(textField.getJTextField());
	}

	public static void validateRea(JTextField textField) {
		String text = textField.getText().trim();
		if(text.isBlank() || text.isEmpty()) {
			throw new emptyInputException("Il campo REA non può essere vuoto.", textField);
		}
		String regex = RegexConst.REA.toString(); // 1 a 10 caratteri
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("Il codice REA deve essere composto da massimo 10 caratteri. ex: RM-123456", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void validateRea(JSplitLbTxf textField) {
		validateRea(textField.getJTextField());
	}

	public static void isValidCity(JTextField textField) {
		String text = textField.getText().trim();
		CityByName cities = CityProvider.ofDefault();
		try {
			cities.findByName(text);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new invalidInputException( "Città " + text + " non trovata.", textField);
		}
		textField.setBorder(UIManager.getBorder("TextField.border"));
	}
	public static void isValidCity(JSplitLbTxf textField) {
		isValidCity(textField.getJTextField());
	}
}
