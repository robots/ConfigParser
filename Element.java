import java.util.regex.*;

/**
 * Trida reprezentujici jednu hodnotu volby v .ini souboru
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class Element {

	/**
	 * Hodnota volby
	 */
	private String value;
	
	private String referencedSection;
	private String referencedOption;
	
	public Element() {}
	
	/**
	 * 
	 * @param value Hodnota volby
	 */
	public Element(String value)
	{
		this.value = value;
	}
	
	/**
	 * Urcuje, zda je hodnota referencovana, tj. zda se odkazuje na hodnotu
	 * jine volby.
	 * @return true, pokud je hodnota referencovana, false jinak
	 */
	public boolean isReference() {
		Pattern p = Pattern.compile(Patterns.PATTERN_REFER);
		Matcher m = p.matcher(this.value);

		if (m.find()) {
			//System.err.println(" ehe ");
			return true;
		}

		//System.err.println(" ee ");
		return false;
	}

	/**
	 * Nastaveni hodnoty
	 * @param value hodnota
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Zjisteni hodnoty
	 * @return hodnota 
	 */
	public String getValue() {
		return value;
		
	}
	
}
