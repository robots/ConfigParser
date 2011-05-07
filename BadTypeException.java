
/**
 * Vyjimka zpusobena spatnou praci s hodnotou volby metodami 
 * spatneho typu.
 * @author Vladimir Fiklik, Michal Demin 
 *
 */
public class BadTypeException extends IniException {
	
	/**
	 *  Serializacni promenna
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * 
	 * @param cause Pricina vyhozeni vyjimky
	 */
	public BadTypeException(String cause)
	{
		super(cause);
	}

}
