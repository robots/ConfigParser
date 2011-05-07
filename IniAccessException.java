
/**
 * Vyjimka vyhazovana pri nespravnem typy pristupu k hodnote volby. 
 * (Pristup k list-volbe metodami pro jednohodnotovou volbu a opacne.)
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class IniAccessException extends IniException {
	
	/**
	 * Serializacni konstanta
	 */
	private static final long serialVersionUID = 3L;


	/**
	 * Konstruktor 
	 * @param cause pricina vyhozeni vyjimky
	 */
	IniAccessException(String cause)
	{
		super(cause);
	}

}
