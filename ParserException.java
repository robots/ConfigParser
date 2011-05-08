
/**
 * Vyjimka vyhazovana parserom pri chybe parsovania
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class ParserException extends IniException {
	
	/**
	 * Serializacni konstanta
	 */
	private static final long serialVersionUID = 5L;
	
	/**
	 * 
	 * @param cause pricina vyhozeni vyjimky
	 */
	public ParserException(String cause) {
		super(cause);
	}

}
