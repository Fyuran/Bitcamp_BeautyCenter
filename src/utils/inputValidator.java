package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.centro.estetico.bitcamp.Main;

public class inputValidator {

    // Variabile per il messaggio di errore
    private static String errorMessage = "";

    // Metodo per ottenere il messaggio di errore
    public static String getErrorMessage() {
        return errorMessage;
    }

    // Metodo per validare nomi 
    public static boolean validateName(String name) {
        if (name.isBlank() || name.trim().isEmpty()) {
            errorMessage = "Il nome non può essere vuoto.";
            return false;
        }
        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$"; 
        boolean result = Pattern.matches(regex, name.trim());
        if (!result) {
            errorMessage = "Il nome deve contenere solo lettere e spazi, lunghezza tra 2 e 50 caratteri.";
        }
        System.out.println("result: "+result);
        return result;
    }

    // Metodo per validare numeri di telefono 
    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            errorMessage = "Il numero di telefono non può essere vuoto.";
            return false;
        }
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{8,15}$"; 
        boolean result = Pattern.matches(regex, phoneNumber.trim());
        if (!result) {
            errorMessage = "Il numero di telefono deve contenere solo cifre e può includere un prefisso internazionale.";
        }
        return result;
    }

    // Metodo per validare indirizzi email
    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            errorMessage = "L'email non può essere vuota.";
            return false;
        }
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        boolean result = Pattern.matches(regex, email.trim());
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
        if (codiceFiscale == null || codiceFiscale.trim().isEmpty()) {
            errorMessage = "Il codice fiscale non può essere vuoto.";
            return false;
        }
        String regex = "^[A-Z0-9]{16}$";
        boolean result = Pattern.matches(regex, codiceFiscale.trim().toUpperCase());
        if (!result) {
            errorMessage = "Il codice fiscale deve essere composto da 16 caratteri alfanumerici.";
        }
        return result;
    }

    // Metodo per validare partita IVA (11 numeri)
    public static boolean validatePIva(String pIva) {
        if (pIva == null || pIva.trim().isEmpty()) {
            errorMessage = "La partita IVA non può essere vuota.";
            return false;
        }
        String regex = "^\\d{11}$"; // Esattamente 11 cifre
        boolean result = Pattern.matches(regex, pIva.trim());
        if (!result) {
            errorMessage = "La partita IVA deve essere composta da 11 cifre numeriche.";
        }
        return result;
    }

    // Metodo per validare l'aliquota (percentuale tra 0 e 100 con massimo 2 decimali)
    public static boolean validateAliquota(String aliquota) {
        if (aliquota == null || aliquota.trim().isEmpty()) {
            errorMessage = "L'aliquota non può essere vuota.";
            return false;
        }
        try {
            double value = Double.parseDouble(aliquota.trim());
            boolean result = value >= 0 && value <= 100 && Pattern.matches("^\\d{1,3}(\\.\\d{1,2})?$", aliquota.trim());
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
        if (time == null || time.trim().isEmpty()) {
            errorMessage = "L'orario non può essere vuoto.";
            return false;
        }
        String regex = "^([01]?\\d|2[0-3]):[0-5]\\d$";
        boolean result = Pattern.matches(regex, time.trim());
        if (!result) {
            errorMessage = "L'orario deve essere nel formato HH:mm (24 ore).";
        }
        return result;
    }

    // Metodo per validare date (formato dd/MM/yyyy)
    public static boolean validateDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            errorMessage = "La data non può essere vuota.";
            return false;
        }
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        boolean result = Pattern.matches(regex, date.trim());
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
    public static boolean validateAlphanumeric(String input) {
        if (input == null || input.trim().isEmpty()) {
            errorMessage = "L'input alfanumerico non può essere vuoto.";
            return false;
        }
        String regex = "^[A-Za-z0-9\\s,.'-]{2,100}$";
        boolean result = Pattern.matches(regex, input.trim());
        if (!result) {
            errorMessage = "L'input alfanumerico può contenere solo lettere, numeri, spazi e alcuni caratteri speciali.";
        }
        return result;
    }

    // Metodo per validare password (min 8 caratteri, almeno 1 lettera, 1 numero e 1 carattere speciale)
    public static boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            errorMessage = "La password non può essere vuota.";
            return false;
        }
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        boolean result = Pattern.matches(regex, password.trim());
        if (!result) {
            errorMessage = "La password deve essere lunga almeno 8 caratteri e includere almeno 1 lettera, 1 numero e 1 carattere speciale.";
        }
        return result;
    }
  //metodo per validare iban
    public static boolean validateIban(String iban, boolean isIbanMandatory) {
//    	if(isIbanMandatory) {
//    		if(iban.trim().isEmpty()||iban==null) {
//    			errorMessage="L'IBAN deve essere lungo 27 caratteri";
//        		return false;
//    		}
//    	}
//    	if(iban.trim().length()!=27||iban.trim().length()!=0) {
//    		errorMessage="L'IBAN deve essere lungo 27 caratteri";
//    		return false;
//    	}
//    	String regex="IT\\d{2}[ ][a-zA-Z]\\d{3}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{3}|IT\\d{2}[a-zA-Z]\\d{22}\n";
//    	boolean result=Pattern.matches(regex,iban.trim());
//    	if(!result) {
//    		errorMessage="Formato IBAN non valido";
//    	}
    	return true;
    }
  //controllo che lo username sia unico:
  	public static boolean isUserUnique(String username) {
  		String query="SELECT * FROM beauty_centerdb.user_credentials WHERE username=? LIMIT 1";
  		String name="";
  		Connection conn=Main.getConnection();
  		try(PreparedStatement pstmt = conn.prepareStatement(query)){
  			pstmt.setString(1, username);
  			ResultSet rs=pstmt.executeQuery();
  			if(rs.next()) {
  				name=rs.getString("username");
  				
  			}
  			
  			return name.equals("");
  		}catch(SQLException e) {
  			e.printStackTrace();
  			return false;
  		}
  	}
}
