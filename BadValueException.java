
/**
 * Vyjimka vyhazovana pri vkladani neplatne hodnoty
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class BadValueException extends IniException {
	
	/**
	 * Serializacni konstanta
	 */
	private static final long serialVersionUID = 4L;
	
	/**
	 * 
	 * @param cause pricina vyhozeni vyjimky
	 */
	public BadValueException(String cause) {
		super(cause);
	}

}
