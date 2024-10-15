package com.bitcamp.centro.estetico.utils;

import java.util.regex.Pattern;

import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;

public class inputValidator {

	public abstract static class inputValidatorException extends Exception {
		inputValidatorException(String message) {
			super(message);
		}
	}
	public static class emptyInputException extends inputValidatorException {
		emptyInputException(String message) {
			super(message);
		}
	}
	public static class invalidInputException extends inputValidatorException {
		invalidInputException(String message) {
			super(message);
		}
	}
	public static class regexNoMatchException extends inputValidatorException {
		regexNoMatchException(String message) {
			super(message);
		}
	}

	// Metodo per validare nomi
	public static void validateName(String name) throws inputValidatorException {
		name = name.trim();
		if (name.isBlank() || name.isEmpty()) {
			throw new emptyInputException("Il nome non può essere vuoto.");
		}
		String regex = RegexConst.TEXT.toString();
		boolean result = Pattern.matches(regex, name);
		if (!result) {
			throw new regexNoMatchException("Il nome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.");
		}
	}

	// Metodo per validare cognomi
	public static void validateSurname(String name) throws inputValidatorException {
		name = name.trim();
		if (name.isBlank() || name.isEmpty()) {
			throw new emptyInputException("Il cognome non può essere vuoto.");
		}
		String regex = RegexConst.TEXT.toString();
		boolean result = Pattern.matches(regex, name);
		if (!result) {
			throw new regexNoMatchException("Il cognome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.");
		}
	}

	// Metodo per validare numeri di telefono
	public static void validatePhoneNumber(String phoneNumber) throws inputValidatorException {
		phoneNumber = phoneNumber.trim();
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			throw new emptyInputException("Il numero di telefono non può essere vuoto.");
		}
		String regex = RegexConst.PHONE.toString();
		boolean result = Pattern.matches(regex, phoneNumber);
		if (!result) {
			throw new regexNoMatchException("Il numero di telefono deve contenere solo cifre e può includere un prefisso internazionale.");
		}
	}

	// Metodo per validare indirizzi email
	public static void validateEmail(String email) throws inputValidatorException {
		email = email.trim();
		if (email == null || email.isEmpty()) {
			throw new emptyInputException("L'email non può essere vuota.");
		}
		String regex = RegexConst.MAIL.toString();
		boolean result = Pattern.matches(regex, email);
		if (!result) {
			throw new regexNoMatchException("Formato email non valido.");
		}
	}

	// Metodo per validare codice fiscale (Italiano, 16)
	public static void validateCodiceFiscale(String codiceFiscale) throws inputValidatorException {
		codiceFiscale = codiceFiscale.trim();
		if (codiceFiscale == null || codiceFiscale.isEmpty()) {
			throw new emptyInputException("Il codice fiscale non può essere vuoto.");
		}
		String regex = RegexConst.EU_TIN.toString();
		boolean result = Pattern.matches(regex, codiceFiscale.toUpperCase());
		if (!result) {
			throw new regexNoMatchException("Il codice fiscale deve essere composto da 16 caratteri alfanumerici.");
		}
	}

	// Metodo per validare partita IVA (11 numeri)
	public static void validatePIva(String pIva) throws inputValidatorException {
		pIva = pIva.trim();
		if (pIva == null || pIva.isEmpty()) {
			throw new emptyInputException("La partita IVA non può essere vuota.");
		}
		String regex = RegexConst.P_IVA.toString(); // Esattamente 11 cifre
		boolean result = Pattern.matches(regex, pIva);
		if (!result) {
			throw new regexNoMatchException("La partita IVA deve essere composta da 11 cifre numeriche.");
		}
	}

	// Metodo per validare l'aliquota (percentuale tra 0 e 100 con massimo 2
	// decimali)
	public static void validateAliquota(String aliquota) throws inputValidatorException {
		aliquota = aliquota.trim();
		if (aliquota == null || aliquota.isEmpty()) {
			throw new emptyInputException("L'aliquota non può essere vuota.");
		}
		try {
			double value = Double.parseDouble(aliquota);
			boolean result = value >= 0 && value <= 100 && Pattern.matches(RegexConst.VAT.toString(), aliquota);
			if (!result) {
				throw new regexNoMatchException("L'aliquota deve essere un numero tra 0 e 100 con massimo 2 decimali.");
			}
		} catch (NumberFormatException e) {
			throw new invalidInputException( "L'aliquota deve essere un numero valido.");
		}
	}
	
	// Metodo per validare numeri interi
	public static void validateNumber(Number number, int min, int max) throws inputValidatorException {
		boolean result = number.doubleValue() >= min && number.doubleValue() <= max;
		if (!result) {
			throw new invalidInputException( "Il numero deve essere compreso tra " + min + " e " + max + ".");
		}
	}

	// Metodo per validare input alfanumerici generici
	public static void validateAlphanumeric(String input, String title) throws inputValidatorException {
		input = input.trim();
		if (title == null) {
			title = "input";
		}
		if (input == null || input.isEmpty()) {
			throw new emptyInputException(title + " non può essere vuoto.");
		}
		String regex = RegexConst.ALPHANUMERIC.toString();
		boolean result = Pattern.matches(regex, input);
		if (!result) {
			throw new regexNoMatchException(title + " può contenere solo lettere, numeri, spazi e alcuni caratteri speciali.");
		}
	}

	// Metodo per validare password (min 8 caratteri, almeno 1 lettera, 1 numero e 1
	// carattere speciale)
	public static void validatePassword(char[] password) throws inputValidatorException {
		if (password == null || password.length == 0) {
			throw new emptyInputException("La password non può essere vuota.");
		}
		String regex = RegexConst.PASSWORD.toString();
		boolean result = Pattern.matches(regex, String.valueOf(password));
		if (!result) {
			throw new regexNoMatchException("La password deve essere lunga almeno 8 caratteri e includere almeno 1 lettera, 1 numero e 1 carattere speciale.");
		}
	}

	// metodo per validare iban
	public static void validateIban(String iban) throws inputValidatorException {
		iban = iban.trim();
		if (iban.isBlank() || iban.isEmpty()) {
			throw new emptyInputException("Il campo IBAN non può essere vuote");
		}
		String regex = RegexConst.IBAN.toString();
		boolean result = Pattern.matches(regex, iban);
		if (!result) {
			throw new regexNoMatchException( "L'IBAN deve essere lungo 27 caratteri. ex: IT60X0542811101000000123456");
		}
	}

	public static void validateRea(String text) throws inputValidatorException {
		text = text.trim();
		if(text.isBlank() || text.isEmpty()) {
			throw new emptyInputException("Il campo REA non può essere vuoto.");
		}
		String regex = RegexConst.REA.toString(); // 1 a 10 caratteri
		boolean result = Pattern.matches(regex, text);
		if (!result) {
			throw new regexNoMatchException("Il codice REA deve essere composto da massimo 10 caratteri. ex: RM-123456");
		}
	}

	public static void isValidCity(String city) throws inputValidatorException {
		CityByName cities = CityProvider.ofDefault();
		try {
			cities.findByName(city);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new invalidInputException( "Città " + city + " non trovata.");
		}
	}
}
