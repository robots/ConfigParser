
/**
 * 
 * @author Vladimir Fiklik, Michal Demin 
 *
 */
public class BadTypeException extends Exception {
	
	/**
	 *  Serializacni promenna
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param cause Pricina vyhozeni vyjimky
	 */
	public BadTypeException(String cause)
	{
		super(cause);
	}

}
