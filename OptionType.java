
/**
 * Vyctovy typ moznych typu volby
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public enum OptionType {
	/**
	 * Nedefinovany typ - nemelo by nastat
	 */
	UNDEF,
	/**
	 * Konstanta pro boolean volbu
	 */
	BOOLEAN,
	
	/**
	 * Konstanta pro signed volbu
	 */
	SIGNED, 
	
	/**
	 * Konstanta pro unsigned volbu
	 */
	UNSIGNED, 
	
	/**
	 * Konstanta pro float volbu
	 */
	FLOAT, 
	
	/**
	 * Konstanta pro enum volbu
	 */
	ENUM, 
	
	/**
	 * Konstanta pro string volbu
	 */
	STRING 

}
