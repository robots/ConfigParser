


import java.math.BigInteger;
import java.util.List;

/**
 * Interface reprezentujici jednu sekci v ini souboru
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public interface IniSection {
	
	/**
	 * Zjisteni identifikatoru sekce
	 * @return nazev sekce
	 */
	public String getName();

	/**
	 * Definice existence volby typu string v ramci sekce
	 * @param option nazev volby
	 * @return vytvorena volba
	 */
	public IniOption defineOptString(String option);
	
	/**
	 * Definice existence volby typu string v ramci sekce a nastaveni jeji 
	 * defaultni hodnoty
	 * @param option nazev volby
	 * @param defaultValue defaultni hodnota
	 * @return vytvorena volba
	 */
	public IniOption defineOptString(String option, String defaultValue);
	
	
	/**
	 * Definice existence volby typu boolean v ramci sekce
	 * @param option nazev volby
	 * @return vytvorena volba
	 */
	public IniOption defineOptBoolean(String option);
	
	/**
	 * Definice existence volby typu boolean v ramci sekce a nastaveni jeji 
	 * defaultni hodnoty
	 * @param option nazev volby
	 * @param defaultValue defaultni hodnota
	 * @return vytvorena volba
	 */
	public IniOption defineOptBoolean(String option, boolean defaultValue);
	
	/**
	 * Definice existence volby typu float v ramci sekce
	 * @param option nazev volby
	 * @return vytvorena volba
	 */
	public IniOption defineOptFloat(String option);
	
	/**
	 * Definice existence volby typu float v ramci sekce a nastaveni jeji 
	 * defaultni hodnoty
	 * @param option nazev volby
	 * @param defaultValue defaultni hodnota
	 * @return vytvorena volba
	 */
	public IniOption defineOptFloat(String option, float defaultValue);
	
	/**
	 * Definice existence volby typu signed v ramci sekce
	 * @param option nazev volby
	 * @return vytvorena volba
	 */
	public IniOption defineOptSigned(String option);
	
	/**
	 * Definice existence volby typu signed v ramci sekce a nastaveni jeji 
	 * defaultni hodnoty
	 * @param option nazev volby
	 * @param defaultValue defaultni hodnota
	 * @return vytvorena volba
	 */
	public IniOption defineOptSigned(String option, BigInteger defaultValue);
	
	/**
	 * Definice existence volby typu unsigned v ramci sekce
	 * @param option nazev volby
	 * @return vytvorena volba
	 */
	public IniOption defineOptUnsigned(String option);
	
	/**
	 * Definice existence volby typu unsigned v ramci sekce a nastaveni jeji 
	 * defaultni hodnoty
	 * @param option nazev volby
	 * @param defaultValue defaultni hodnota
	 * @return vytvorena volba
	 */
	public IniOption defineOptUnsigned(String option, BigInteger defaultValue);
	
	/**
	 * Definice existence volby typu enum v ramci sekce
	 * @param option nazev volby
	 * @param enumName Nazev vyctoveho typu, do nehoz patri hodnoty volby
	 * @return vytvorena volba
	 */
	public IniOption defineOptEnum(String option, String enumName);
	
	/**
	 * Definice existence volby typu enum v ramci sekce a nastaveni jeji 
	 * defaultni hodnoty
	 * @param option nazev volby
	 * @param enumName Nazev vyctoveho typu, do nehoz patri hodnoty volby
	 * @param defaultValue defaultni hodnota
	 * @return vytvorena volba
	 */
	public IniOption defineOptEnum(String option, String enumName, 
			String defaultValue) throws BadValueException, IniAccessException;

	/**
	 * Definice existence list-volby typu string v ramci sekce
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @return vytvorena volba
	 */
	public IniOption defineOptListString(String option, char delimiter);
	
	/**
	 * Definice existence list-volby typu string v ramci sekce a nastaveni 
	 * jeji defaultni hodnoty
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @param defaultValue defaultni hodnoty
	 * @return vytvorena volba
	 */
	public IniOption defineOptListString(String option, char delimiter, 
			List<String> defaultValue);
	
	/**
	 * Definice existence list-volby typu boolean v ramci sekce
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @return vytvorena volba
	 */
	public IniOption defineOptListBoolean(String option, char delimiter);
	
	/**
	 * Definice existence list-volby typu boolean v ramci sekce a nastaveni 
	 * jeji defaultni hodnoty
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @param defaultValue defaultni hodnoty
	 * @return vytvorena volba
	 */
	public IniOption defineOptListBoolean(String option, char delimiter, 
			List<Boolean> defaultValue);
	
	/**
	 * Definice existence list-volby typu float v ramci sekce
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @return vytvorena volba
	 */
	public IniOption defineOptListFloat(String option, char delimiter);
	
	/**
	 * Definice existence list-volby typu float v ramci sekce a nastaveni 
	 * jeji defaultni hodnoty
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @param defaultValue defaultni hodnoty
	 * @return vytvorena volba
	 */
	public IniOption defineOptListFloat(String option, char delimiter, 
			List<Float> defaultValue);
	
	/**
	 * Definice existence list-volby typu signed v ramci sekce
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @return vytvorena volba
	 */
	public IniOption defineOptListSigned(String option, char delimiter);
	
	/**
	 * Definice existence list-volby typu signed v ramci sekce a nastaveni 
	 * jeji defaultni hodnoty
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @param defaultValue defaultni hodnoty
	 * @return vytvorena volba
	 */
	public IniOption defineOptListSigned(String option, char delimiter, 
			List<BigInteger> defaultValue);
	
	/**
	 * Definice existence list-volby typu unsigned v ramci sekce
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @return vytvorena volba
	 */
	public IniOption defineOptListUnsigned(String option, char delimiter);
	
	/**
	 * Definice existence list-volby typu unsigned v ramci sekce a nastaveni 
	 * jeji defaultni hodnoty
	 * @param option nazev volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @param defaultValue defaultni hodnoty
	 * @return vytvorena volba
	 */
	public IniOption defineOptListUnsigned(String option, char delimiter, 
			List<BigInteger> defaultValue);
	
	/**
	 * Definice existence list-volby typu enum v ramci sekce
	 * @param option nazev volby
	 * @param enumName Nazev vyctoveho typu, do nehoz patri hodnoty volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @return vytvorena volba
	 */
	public IniOption defineOptListEnum(String option, String enumName, 
			char delimiter);
	
	/**
	 * Definice existence list-volby typu enum v ramci sekce a nastaveni 
	 * jeji defaultni hodnoty
	 * @param option nazev volby
	 * @param enumName Nazev vyctoveho typu, do nehoz patri hodnoty volby
	 * @param delimiter oddelovac jednotlivych prvku seznamu - pripustne 
	 * hodnoty ',' a ':'
	 * @param defaultValue defaultni hodnoty
	 * @return vytvorena volba
	 */
	public IniOption defineOptListEnum(String option, String enumName, 
			char delimiter, List<String> defaultValue) 
			throws BadValueException;

	/**
	 * Nalezeni volby podle zadaneho jmena
	 * @param option nazev volby
	 * @return hledana volba, nebo null pokud neexistuje
	 */
	public IniOption getOption(String option);
	
	/**
	 * Nastaveni radkoveho komentare k sekci
	 * @param inlineComment radkovy komentar
	 */
	public void setInlineComment(String inlineComment);
	
	/**
	 * Zjisteni radkoveho komentare prislusejiciho sekci
	 * @return Radkovy komentar, nebo null pokud neexistuje
	 */
	public String getInlineComment();
	
	/**
	 * Nastaveni seznamu komentaru predchazejicich sekci.
	 * Kazda radka je ulozena v samostatnem stringu.
	 * @param priorComments komentare predchazejici sekci
	 */
	public void setPriorComments(List<String> priorComments);
	
	/**
	 * Zjisteni seznamu komentaru predchazejicich sekci.
	 * Kazda radka je ulozena v samostatnem stringu.
	 * @return seznam komentaru predchazejicich sekci
	 */
	public List<String> getPriorComments();
	
	/**
	 * Vstupni metoda pro visitora (Visitor design pattern)
	 * @param visitor instance visitora
	 */
	public void accept (IniVisitor visitor);

}
