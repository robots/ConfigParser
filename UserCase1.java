import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;


public class UserCase1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IniParser iniParser = new IniParserImpl();
		IniSection section = null;
		
		// Definice struktury konfiguracniho souboru.
		try{
			// Definice sekce
			section = iniParser.addSection("Database");

			// Definice povinnych voleb
			section.defineOptString("user");
			section.defineOptString("pass");
			
			// Definice volby s defaultni hodnotou
			section.defineOptBoolean("secure", true);

		} catch( Exception e) {
			System.out.println(e.toString());
		}


		// Nacteni konfigurace ze souboru
		try {
			iniParser.readFile("database.ini");
		} catch (IOException e) {
			// catch any io exception
			e.printStackTrace();
		} catch (ParserException e) {
			// catch any parser exception
			e.printStackTrace();
		}


		try {
			// Pristup ke konfiguraci
			
			// Primy pristup pres IniParser
			String userName = 
				iniParser.getOption("Database","user").getValueString();
			
			// Pristup pres sekci
			IniSection databaseSection = iniParser.getSection("Database");
			
			String password = 
				databaseSection.getOption("pass").getValueString();
			
			// Typovany pristup k volbe typu boolean
			boolean secureAccess = 
				databaseSection.getOption("secure").getValueBool();
			
			System.out.println("User: " + userName);
			System.out.println("Pasword: " + password);
			System.out.println("Secure access: " + secureAccess);
			
			
		} catch (IniException e) {
			System.err.println(e.toString());
		}
		
	}

}
