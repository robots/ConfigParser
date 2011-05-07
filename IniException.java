
/**
 * Predek vyjimek vyhazovanych behem prase s IniParserem
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public abstract class IniException extends Exception{
	
	/**
	 * serializacni konstanta
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor
	 * @param cause Pricina vyhozeni vyjimky
	 */
	public IniException(String cause)
	{
		super(cause);
	}

}
