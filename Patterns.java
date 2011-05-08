
public final class Patterns {
	
	public static final String PATTERN_ID = "[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*";
	public static final String PATTERN_ID_STRICT = "^[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*$";
	public static final String PATTERN_REFER = "\\$\\{" + PATTERN_ID + "\\#" + PATTERN_ID + "\\}";
	public static final String PATTERN_SECTION = "\\[[^\\]]*\\]"; 
	public static final String PATTERN_SECTION_STRICT = "\\[[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*\\]";
}
