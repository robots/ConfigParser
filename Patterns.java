
/** 
 * Trida nesouci regularni vyrazy pro parsovani konfiguracniho souboru
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public final class Patterns {
	
	/**
	 * Reularni vyraz popisujici identifikator v konfiguracnim souboru
	 */
	public static final String PATTERN_ID = 
		"[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*";
	
	/**
	 * Reularni vyraz striktne popisujici identifikator v konfiguracnim 
	 * souboru. Tzn, uspech pouze v pripade, ze je cely retezec 
	 * identifikatorem
	 */
	public static final String PATTERN_ID_STRICT = 
		"^[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*$";
	
	/**
	 * Regularni vyraz testujici, zda se v retezci vyskytuje reference
	 */
	public static final String PATTERN_REFER = 
		"\\$\\{" + PATTERN_ID + "\\#" + PATTERN_ID + "\\}";
	
	/**
	 * Regularni vyraz testujici, zda je retezec nazvem sekce
	 */
	public static final String PATTERN_SECTION = 
		"\\[[^\\]]*\\]";
	
	/**
	 * Regularni vyraz striktne testujici, zda je retezec nazvem sekce.
	 */
	public static final String PATTERN_SECTION_STRICT = 
		"\\[[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*\\]";
	
	/**
	 * Trida neni instanciovatelna
	 */
	private Patterns() {}
}
