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
	
	public Element() {}
	
	/**
	 * Konstruktor dosazujici pocatecni hodnotu volby
	 * @param value hodnota volby
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
		Pattern referPattern = Pattern.compile(Patterns.PATTERN_REFER);
		Matcher referMatcher = referPattern.matcher(this.value);

		if (referMatcher.find()) {
			return true;
		}
		
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
