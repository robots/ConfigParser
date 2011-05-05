
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
		// TODO: regexp  "${[^#]*#[^}]*}
		
		return false;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
